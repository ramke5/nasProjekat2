package ba.nalaz.web.form;


import ba.nalaz.model.core.User;

public class UserForm{

    private User user;
    private String confirmPassword;
    private Boolean systemGeneratedPassword;
    private Boolean changePassword;
    private Boolean welcomeEmail;
    private Integer userType;
    private Long labId;

    private UserForm() {
    }

    public UserForm(User user) {
        this.user = user;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getSystemGeneratedPassword() {
        return systemGeneratedPassword;
    }

    public void setSystemGeneratedPassword(Boolean systemGeneratedPassword) {
        this.systemGeneratedPassword = systemGeneratedPassword;
    }

    public Boolean getWelcomeEmail() {
        return welcomeEmail;
    }

    public void setWelcomeEmail(Boolean welcomeEmail) {
        this.welcomeEmail = welcomeEmail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserForm [user=").append(user)
		.append(", confirmPassword=").append(confirmPassword)
		.append(", labId=").append(labId)
				.append(", systemGeneratedPassword=")
				.append(systemGeneratedPassword).append(", changePassword=")
				.append(changePassword).append(", welcomeEmail=")
				.append(welcomeEmail).append(", userType=").append(userType)
				.append("]");
		return builder.toString();
	}
}