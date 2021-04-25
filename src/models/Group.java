package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "groups")
@NamedQueries({
    @NamedQuery(
        name = "getAllGroups",
        query = "SELECT g FROM Group AS g ORDER BY g.id DESC"
    ),
    @NamedQuery(
            name = "getGroupsCount",
            query = "SELECT COUNT(g) FROM Group AS g"
    ),
    @NamedQuery(
            name = "checkRegisteredId",
            query = "SELECT COUNT(g) FROM Group AS g WHERE g.id = :id"
    )
})
@Entity
public class Group
{
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


}
