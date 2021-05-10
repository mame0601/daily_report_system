package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Employee;
import models.GroupUser;
import utils.DBUtil;

public class GroupUserValidator
{
    public static List<String> validate(GroupUser g, Boolean idDuplicateCheckFlag)
    {
        List<String> errors = new ArrayList<String>();

        String id_error = validateId(g.getEmployee(), g.getGroup_id(), idDuplicateCheckFlag);
        if(!id_error.equals(""))
        {
            errors.add(id_error);
        }

        return errors;
    }

    // ユーザーID
    private static String validateId(Employee employee, String group_id, Boolean idDuplicateCheckFlag)
    {
        // 必須入力チェック
        if(employee == null)
        {
            return "ユーザーを入力してください。";
        }

        // すでに登録されているユーザーIDとの重複チェック
        if(idDuplicateCheckFlag)
        {
            EntityManager em = DBUtil.createEntityManager();

            GroupUser groupusers = null;

            try{
                groupusers = (GroupUser)em.createNamedQuery("checkRegisteredUserId", GroupUser.class)
                        .setParameter("employee", employee)
                        .getSingleResult();
            }
            catch(Exception e) {}

            em.close();
            if(groupusers != null)
            {
                if(groupusers.getGroup_id().equals(group_id))
                {
                    return "入力されたユーザーはすでに登録されています。";
                }
                else
                {
                    return "入力されたユーザーは別のグループに登録されています。";
                }
            }
        }

        return "";
    }
}
