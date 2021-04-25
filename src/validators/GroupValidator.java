package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Group;
import utils.DBUtil;

public class GroupValidator
{
    public static List<String> validate(Group g, Boolean idDuplicateCheckFlag)
    {
        List<String> errors = new ArrayList<String>();

        String id_error = validateId(g.getId(), idDuplicateCheckFlag);
        if(!id_error.equals(""))
        {
            errors.add(id_error);
        }

        String name_error = validateName(g.getName());
        if(!name_error.equals(""))
        {
            errors.add(name_error);
        }

        return errors;
    }

    // グループID
    private static String validateId(String id, Boolean idDuplicateCheckFlag)
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
            long groups_count = (long)em.createNamedQuery("checkRegisteredId", Long.class).setParameter("id", id).getSingleResult();
            em.close();
            if(groups_count > 0)
            {
                return "入力されたグループIDの情報はすでに存在しています。";
            }
        }

        return "";
    }

    // グループ名の必須入力チェック
    private static String validateName(String name)
    {
        // 必須入力チェック
        if(name == null || name.equals(""))
        {
            return "グループ名を入力してください。";
        }

        return "";
    }
}
