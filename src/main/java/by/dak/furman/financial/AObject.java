package by.dak.furman.financial;

import javax.persistence.*;
import java.util.Date;

/**
 * User: akoyro
 * Date: 4/22/13
 * Time: 11:01 AM
 */

@MappedSuperclass
public abstract class AObject
{
    public static final String PROPERTY_id = "id";
    public static final String PROPERTY_created = "created";
    public static final String PROPERTY_modified = "modified";
    public static final String PROPERTY_deleted = "deleted";
    public static final String PROPERTY_name = "name";

    @Id
    @SequenceGenerator(name = "IDGenerator", sequenceName = "HIBERNATE_SEQUENCE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "IDGenerator")
    @Column(nullable = false, insertable = true, updatable = false, unique = true)
    private Long id;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date modified;

    @Column(nullable = false)
    private Boolean deleted;

    @Column(nullable = false)
    private String name;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getModified()
    {
        return modified;
    }

    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    public Boolean getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
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
