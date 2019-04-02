/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mine
 */
@Entity
@Table(name = "voter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Voter.findAll", query = "SELECT v FROM Voter v"),
    @NamedQuery(name = "Voter.findByIdvoter", query = "SELECT v FROM Voter v WHERE v.idvoter = :idvoter"),
    @NamedQuery(name = "Voter.findByFirstname", query = "SELECT v FROM Voter v WHERE v.firstname = :firstname"),
    @NamedQuery(name = "Voter.findByRegnum", query = "SELECT v FROM Voter v WHERE v.regnum = :regnum"),
    @NamedQuery(name = "Voter.findByDob", query = "SELECT v FROM Voter v WHERE v.dob = :dob"),
    @NamedQuery(name = "Voter.findByNationalid", query = "SELECT v FROM Voter v WHERE v.nationalid = :nationalid"),
    @NamedQuery(name = "Voter.findByYearsvoted", query = "SELECT v FROM Voter v WHERE v.yearsvoted = :yearsvoted"),
    @NamedQuery(name = "Voter.findByCreationDate", query = "SELECT v FROM Voter v WHERE v.creationDate = :creationDate"),
    @NamedQuery(name = "Voter.findBySecondname", query = "SELECT v FROM Voter v WHERE v.secondname = :secondname"),
    @NamedQuery(name = "Voter.findByThirdname", query = "SELECT v FROM Voter v WHERE v.thirdname = :thirdname"),
    @NamedQuery(name = "Voter.findByRegistrationyear", query = "SELECT v FROM Voter v WHERE v.registrationyear = :registrationyear"),
    @NamedQuery(name = "Voter.findByEmailadd", query = "SELECT v FROM Voter v WHERE v.emailadd = :emailadd"),
    @NamedQuery(name = "Voter.findByPhone", query = "SELECT v FROM Voter v WHERE v.phone = :phone"),
    @NamedQuery(name = "Voter.findByGender", query = "SELECT v FROM Voter v WHERE v.gender = :gender"),
    @NamedQuery(name = "Voter.findByPassword", query = "SELECT v FROM Voter v WHERE v.password = :password")})
public class Voter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvoter")
    private Integer idvoter;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "regnum")
    private String regnum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nationalid")
    private String nationalid;
    @Column(name = "yearsvoted")
    private Integer yearsvoted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "secondname")
    private String secondname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "thirdname")
    private String thirdname;
    @Column(name = "registrationyear")
    private Integer registrationyear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "emailadd")
    private String emailadd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "phone")
    private int phone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voterid")
    private Collection<Result> resultCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voterid")
    private Collection<Candidate> candidateCollection;
    @JoinColumn(name = "staffid", referencedColumnName = "adminId")
    @ManyToOne(optional = false)
    private Admins staffid;
    @JoinColumn(name = "status", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "department", referencedColumnName = "iddepartment")
    @ManyToOne(optional = false)
    private Department department;

    public Voter() {
    }

    public Voter(Integer idvoter) {
        this.idvoter = idvoter;
    }

    public Voter(Integer idvoter, String firstname, String regnum, Date dob, String nationalid, Date creationDate, String secondname, String thirdname, String emailadd, int phone, String gender, String password) {
        this.idvoter = idvoter;
        this.firstname = firstname;
        this.regnum = regnum;
        this.dob = dob;
        this.nationalid = nationalid;
        this.creationDate = creationDate;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.emailadd = emailadd;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
    }

    public Integer getIdvoter() {
        return idvoter;
    }

    public void setIdvoter(Integer idvoter) {
        this.idvoter = idvoter;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationalid() {
        return nationalid;
    }

    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    public Integer getYearsvoted() {
        return yearsvoted;
    }

    public void setYearsvoted(Integer yearsvoted) {
        this.yearsvoted = yearsvoted;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public Integer getRegistrationyear() {
        return registrationyear;
    }

    public void setRegistrationyear(Integer registrationyear) {
        this.registrationyear = registrationyear;
    }

    public String getEmailadd() {
        return emailadd;
    }

    public void setEmailadd(String emailadd) {
        this.emailadd = emailadd;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Result> getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(Collection<Result> resultCollection) {
        this.resultCollection = resultCollection;
    }

    @XmlTransient
    public Collection<Candidate> getCandidateCollection() {
        return candidateCollection;
    }

    public void setCandidateCollection(Collection<Candidate> candidateCollection) {
        this.candidateCollection = candidateCollection;
    }

    public Admins getStaffid() {
        return staffid;
    }

    public void setStaffid(Admins staffid) {
        this.staffid = staffid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idvoter != null ? idvoter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voter)) {
            return false;
        }
        Voter other = (Voter) object;
        if ((this.idvoter == null && other.idvoter != null) || (this.idvoter != null && !this.idvoter.equals(other.idvoter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Voter[ idvoter=" + idvoter + " ]";
    }
    
}
