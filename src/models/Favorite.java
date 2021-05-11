package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "favorites")
@NamedQueries({
    @NamedQuery(
        name = "getMyReportsFavoritesCount",
        query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.report_id = :report"
    ),
    @NamedQuery(
            name = "getMyFavorites",
            query = "SELECT f FROM Favorite AS f WHERE f.employee_id = :employee_id AND f.report_id = :report_id"
    )
})
@Entity
public class Favorite
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer employee_id;

    @Column(name = "report_id", nullable = false)
    private Integer report_id;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getEmployee_id()
    {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id)
    {
        this.employee_id = employee_id;
    }

    public Integer getReport_id()
    {
        return report_id;
    }

    public void setReport_id(Integer report_id)
    {
        this.report_id = report_id;
    }

}
