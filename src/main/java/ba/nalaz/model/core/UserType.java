package ba.nalaz.model.core;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Entity
public class UserType implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private Role mainRole;

    public UserType() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 20)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    public Role getMainRole() {
        return mainRole;
    }

    public void setMainRole(Role mainRole) {
        this.mainRole = mainRole;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("type",this.name)
                .append("role", this.mainRole)
                .toString();
    }
}