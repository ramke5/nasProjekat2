package ba.nalaz.model.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String username;                    // required
    @Column(nullable = false)
    @XmlTransient
    private String password;                    // required
    @Column(nullable = false, length = 50)
    private String firstName;                   // required
    @NotEmpty
    @Column(nullable = false, length = 50)
    private String lastName;                    // required
    @Column(nullable = false)
    private String email;                       // required
    private String phoneNumber;
    private String mobileNumber;
    private String subLab;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "UserRole",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();
    private Boolean enabled;
    @Column(nullable = false)
    private Boolean accountExpired = false;
    @Column(nullable = false)
    private Boolean accountLocked = false;
    @Column(nullable = false)
    private Boolean credentialsExpired = false;
    @ManyToOne
    private Lab lab;
    @ManyToOne
    private UserType userType;
    private Boolean agreed;
    private String activationKey;
    private String company;
    private Date createdDate;
    private Date activationDate;

    public User() {
    }

    /**
     * Create a new instance and set the username.
     *
     * @param username login name for user.
     */
    public User(final String username) {
        this.username = username;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate != null ? (Date)createdDate.clone() : null;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate != null ? (Date)createdDate.clone() : null;
    }
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getActivationDate() {
        return activationDate != null ? (Date)activationDate.clone() : null;
    }
    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate != null ? (Date)activationDate.clone() : null;
    }
	/**
     * Returns the full name.
     *
     * @return firstName + ' ' + lastName
     */
    @Transient
    public String getFullName() {
        return firstName + ' ' + lastName;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    /**
     * Adds a role for the user
     *
     * @param role the fully instantiated role
     */
    public void addRole(ba.nalaz.model.core.Role role) {
        getRoles().add(role);
    }
    
    public void removeRoles() {
        getRoles().removeAll(getRoles());
    }
    
    /**
     * @return GrantedAuthority[] an array of roles.
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.addAll(roles);
        return authorities;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isAccountExpired() {
        return accountExpired;
    }
    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }
    /**
     * @return true if account is still active
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     */
    @Transient
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }
    public boolean isAccountLocked() {
        return accountLocked;
    }
    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }
    /**
     * @return false if account is locked
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     */
    @Transient
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }
    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }
    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }
    public Lab getLab() {
        return lab;
    }
    public void setLab(Lab lab) {
        this.lab = lab;
    }
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    /**
     * @return true if credentials haven't expired
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     */
    @Transient
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User user = (User) o;

        return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);
    }
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
                .append("id", this.id)
                .append("firstName", this.firstName)
                .append("lastName", this.lastName)
                .append("username", this.username)
                .append("enabled", this.enabled)
                .append("accountExpired", this.accountExpired)
                .append("company", this.company)
                .append("credentialsExpired", this.credentialsExpired)
                .append("accountLocked", this.accountLocked)
                .append("createdDate", this.createdDate)
                .append("activationDate", this.activationDate);        		

        if (roles != null) {
            sb.append("Granted Authorities: ");

            int i = 0;
            for (ba.nalaz.model.core.Role role : roles) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(role.toString());
                i++;
            }
        } else {
            sb.append("No Granted Authorities");
        }
        return sb.toString();
    }
    public String getSubLab() {
        return subLab;
    }
    public void setSubLab(String subLab) {
        this.subLab = subLab;
    }
    public Boolean getAgreed() {
        return agreed;
    }
    public void setAgreed(Boolean agreed) {
        this.agreed = agreed;
    }
	public String getActivationKey() {
		return activationKey;
	}
	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@Transient
	public List<String> toList() {
        List<String> list = new ArrayList<String>();
        list.add("" + id);
        list.add("" + accountExpired);
        list.add("" + accountLocked);
        list.add(subLab);
        list.add("" + credentialsExpired);
        list.add(email);
        list.add("" + enabled);
        list.add(firstName);
        list.add(lastName);
        list.add(mobileNumber);
        list.add(password);
        list.add(phoneNumber);
        list.add(username);
        list.add("" + lab.getId());
        list.add(userType.getName());
        list.add("" + agreed);
        list.add(activationKey);
        list.add(company);
        list.add("" + createdDate);
        list.add("" + activationDate);
        return list;
    }
}