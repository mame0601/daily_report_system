package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "groupuser")
@NamedQueries({
    @NamedQuery(
        name = "getMyAllGroupUsers",
        query = "SELECT g FROM GroupUser AS g WHERE g.group_id = :group_id ORDER BY g.group_id DESC"
    ),
    @NamedQuery(
            name = "getMyGroupUsersCount",
            query = "SELECT COUNT(g) FROM GroupUser AS g WHERE g.group_id = :group_id"
    ),
    @NamedQuery(
            name = "checkRegisteredUserId",
            query = "SELECT COUNT(g) FROM GroupUser AS g WHERE g.employee.id = :id"
    )
})
@Entity
public class GroupUser
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group_id", nullable =false)
    private String group_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Employee employee;


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public Employee getEmployee()
    {
        return employee;
    }

    public void setEmployee(Employee employee)
    {
        this.employee = employee;
    }


}
