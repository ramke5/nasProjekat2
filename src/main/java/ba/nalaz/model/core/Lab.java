package ba.nalaz.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Lab implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Long id;
    private Contact contact;
    private String name;
    private Boolean enabled = Boolean.TRUE;
    private Boolean deleted = Boolean.FALSE;
    private User createdUser;
    private User modifiedUser;
    private Date createdDate;
    private Date modifiedDate;

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Lab [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (enabled != null)
			builder.append("enabled=").append(enabled).append(", ");
		if (deleted != null)
			builder.append("deleted=").append(deleted);
		builder.append("]");
		return builder.toString();
	}

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate != null ? (Date)createdDate.clone() : null;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? (Date)createdDate.clone() : null;
    }
    public Boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Column(nullable = false)
    public Boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    @OneToOne(targetEntity = Contact.class)
    @JoinColumn(updatable = true, insertable = true)
    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
    @Column(length = 100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @ManyToOne
    public User getCreatedUser() {
        return createdUser;
    }
    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }
    @ManyToOne
    public User getModifiedUser() {
        return modifiedUser;
    }
    public void setModifiedUser(User modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
    public Date getModifiedDate() {
        return this.modifiedDate != null ? (Date)this.modifiedDate.clone() : null;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate != null ? (Date)modifiedDate.clone() : null;
    }    
}