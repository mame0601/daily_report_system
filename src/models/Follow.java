package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({
    @NamedQuery(
        name = "getAllFollows",
        query = "SELECT f FROM Follow AS f ORDER BY f.id DESC"
    ),
    @NamedQuery(
            name = "getFollowsCount",
            query = "SELECT COUNT(f) FROM Follow AS f"
    ),
    @NamedQuery(
            name = "getOneMyFollows",
            query = "SELECT f FROM Follow As f WHERE f.my_code = :my_code AND f.follows_code = :follows_code"
            ),
    @NamedQuery(
            name = "getMyFollows",
            query = "SELECT f FROM Follow As f WHERE f.my_code = :my_code"
            )
})

@Entity
public class Follow
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="my_code", nullable = false)
    private String my_code;

    @Column(name = "follows_code", nullable = false)
    private String follows_code;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getMy_code()
    {
        return my_code;
    }

    public void setMy_code(String my_code)
    {
        this.my_code = my_code;
    }

    public String getFollows_code()
    {
        return follows_code;
    }

    public void setFollows_code(String follows_code)
    {
        this.follows_code = follows_code;
    }


}
