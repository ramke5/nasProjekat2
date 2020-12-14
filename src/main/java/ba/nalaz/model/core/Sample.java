package ba.nalaz.model.core;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
@Entity
public class Sample implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String sampleCode;
//	@ManyToOne(fetch = FetchType.LAZY)
//    @LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(fetch = FetchType.EAGER)
    private User createdUser;
//    @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
    private User modifiedUser;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date")
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_date")
    private Date modifiedDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="completed_date")
    private Date completedDate;
//    @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
//    @LazyCollection(LazyCollectionOption.TRUE)
    private Lab lab;
	@Column@NotNull
    private String patientName;
	@Column@NotNull
    private String patientSurname;
	@Column
    private String address;
	@Column
    private String city;
	@Column
    private String phoneNumber;
	@Column
    private String email;
	@Column
    private String gender;
	@Column
    private String birthDate;
	@Column
    private String sampleType;
	@Column
    private String analysisType;
	@Column
    private String method;
	@Column
    private String analysisPurpose;
	@Column
    private String eGenResult;
	@Column
    private String nGenResult;
	@Column
    private String overallResult;
	@Column
    private String protocolNumber;
    @Column
    private Integer overallStatus;
    @Column
    private String analysisReason;
    @Column
    private String remark;
    @Column
    private Boolean deleted;
    @Column
    private String note;
    @Column
    private String barCodeLocation;
    @Column
    private byte[] barCodeImage;
    @Column
    private Blob image;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public User getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}
	public User getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(User modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public Lab getLab() {
		return lab;
	}
	public void setLab(Lab lab) {
		this.lab = lab;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientSurname() {
		return patientSurname;
	}
	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getAnalysisType() {
		return analysisType;
	}
	public void setAnalysisType(String analysisType) {
		this.analysisType = analysisType;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getAnalysisPurpose() {
		return analysisPurpose;
	}
	public void setAnaysisPurpose(String analysisPurpose) {
		this.analysisPurpose = analysisPurpose;
	}
	public String geteGenResult() {
		return eGenResult;
	}
	public void seteGenResult(String eGenResult) {
		this.eGenResult = eGenResult;
	}
	public String getnGenResult() {
		return nGenResult;
	}
	public void setnGenResult(String nGenResult) {
		this.nGenResult = nGenResult;
	}
	public String getOverallResult() {
		return overallResult;
	}
	public void setOverallResult(String overallResult) {
		this.overallResult = overallResult;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getOverallStatus() {
		return overallStatus;
	}
	public void setOverallStatus(Integer overallStatus) {
		this.overallStatus = overallStatus;
	}
	public String getProtocolNumber() {
		return protocolNumber;
	}
	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getAnalysisReason() {
		return analysisReason;
	}
	public void setAnalysisReason(String analysisReason) {
		this.analysisReason = analysisReason;
	}
	public void setAnalysisPurpose(String analysisPurpose) {
		this.analysisPurpose = analysisPurpose;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getBarCodeLocation() {
		return barCodeLocation;
	}
	public void setBarCodeLocation(String barCodeLocation) {
		this.barCodeLocation = barCodeLocation;
	}
	public byte[] getBarCodeImage() {
		return barCodeImage;
	}
	public void setBarCodeImage(byte[] barCodeImage) {
		this.barCodeImage = barCodeImage;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	@Override
	public String toString() {
		return "Sample [id=" + id + ", sampleCode=" + sampleCode + ", createdUser=" + createdUser + ", modifiedUser="
				+ modifiedUser + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", completedDate="
				+ completedDate + ", lab=" + lab + ", patientName=" + patientName + ", patientSurname=" + patientSurname
				+ ", address=" + address + ", city=" + city + ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", gender=" + gender + ", birthDate=" + birthDate + ", sampleType=" + sampleType + ", analysisType="
				+ analysisType + ", method=" + method + ", analysisPurpose=" + analysisPurpose + ", eGenResult="
				+ eGenResult + ", nGenResult=" + nGenResult + ", overallResult=" + overallResult + ", protocolNumber="
				+ protocolNumber + ", overallStatus=" + overallStatus + ", analysisReason=" + analysisReason
				+ ", remark=" + remark + ", deleted=" + deleted + ", note=" + note + ", barCodeLocation="
				+ barCodeLocation + ", barCodeImage=" + Arrays.toString(barCodeImage) + "]";
	}
}