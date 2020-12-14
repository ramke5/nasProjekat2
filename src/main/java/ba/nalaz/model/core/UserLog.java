package ba.nalaz.model.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class UserLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
    private User user;
    private Lab lab;
    private Date loggedInTime;
    private Date loggedOutTime;
    private String requestedIP;

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserLog [id=").append(id).append(", user=")
				.append(user).append(", lab=").append(lab)
				.append(", loggedInTime=").append(loggedInTime)
				.append(", loggedOutTime=").append(loggedOutTime)
				.append(", requestedIP=").append(requestedIP).append("]");
		return builder.toString();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoggedOutTime() {
        return loggedOutTime != null ? (Date)loggedOutTime.clone() : null;
    }

    public void setLoggedOutTime(Date loggedOutTime) {
        this.loggedOutTime = loggedOutTime != null ? (Date)loggedOutTime.clone() : null;
    }

    public String getRequestedIP() {
        return requestedIP;
    }

    public void setRequestedIP(String requestedIP) {
        this.requestedIP = requestedIP;
    }

    public Date getLoggedInTime() {
        return loggedInTime != null ? (Date)loggedInTime.clone() : null;
    }

    public void setLoggedInTime(Date loggedInTime) {
        this.loggedInTime = loggedInTime != null ? (Date)loggedInTime.clone() : null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Lab getLab() {
        return lab;
    }

    public void setLab(Lab lab) {
        this.lab = lab;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}