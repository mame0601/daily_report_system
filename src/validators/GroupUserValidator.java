package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.GroupUser;
import utils.DBUtil;

public class GroupUserValidator
{
    public static List<String> validate(GroupUser g, Boolean idDuplicateCheckFlag)
    {
        List<String> errors = new ArrayList<String>();

        String id_error = validateId(g.getEmployee().getId(), idDuplicateCheckFlag);
        if(!id_error.equals(""))
        {
            errors.add(id_error);
        }

        return errors;
    }

    // グループID
    private static String validateId(Integer id, Boolean idDuplicateCheckFlag)
    {
        // 必須入力チェック
        if(id == null || id.equals(""))
        {
            return "グループIDを入力してください。";
        }

        // すでに登録されているグループIDとの重複チェック
        if(idDuplicateCheckFlag)
        {
            EntityManager em = DBUtil.createEntityManager();
            long groups_count = (long)em.createNamedQuery("checkRegisteredUserId", Long.class).setParameter("id", id).getSingleResult();
            em.close();
            if(groups_count > 0)
            {
                return "入力されたグループIDの情報はすでに存在しています。";
            }
        }

        return "";
    }
}
