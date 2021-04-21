package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "groupuser")
@Entity
public class GroupUser
{
    @Id
    @Column(name = "group_id", nullable =false)
    private String group_id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer user_id;

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }


}
