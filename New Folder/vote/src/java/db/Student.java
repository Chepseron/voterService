/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amon.Sabul
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findByIdstudent", query = "SELECT s FROM Student s WHERE s.idstudent = :idstudent")
    , @NamedQuery(name = "Student.findByFirstname", query = "SELECT s FROM Student s WHERE s.firstname = :firstname")
    , @NamedQuery(name = "Student.findByRegnum", query = "SELECT s FROM Student s WHERE s.regnum = :regnum")
    , @NamedQuery(name = "Student.findByDob", query = "SELECT s FROM Student s WHERE s.dob = :dob")
    , @NamedQuery(name = "Student.findByNationalid", query = "SELECT s FROM Student s WHERE s.nationalid = :nationalid")
    , @NamedQuery(name = "Student.findBySponsor", query = "SELECT s FROM Student s WHERE s.sponsor = :sponsor")
    , @NamedQuery(name = "Student.findByYearofstudy", query = "SELECT s FROM Student s WHERE s.yearofstudy = :yearofstudy")
    , @NamedQuery(name = "Student.findByCreationDate", query = "SELECT s FROM Student s WHERE s.creationDate = :creationDate")
    , @NamedQuery(name = "Student.findBySecondname", query = "SELECT s FROM Student s WHERE s.secondname = :secondname")
    , @NamedQuery(name = "Student.findByThirdname", query = "SELECT s FROM Student s WHERE s.thirdname = :thirdname")
    , @NamedQuery(name = "Student.findByAcademicyear", query = "SELECT s FROM Student s WHERE s.academicyear = :academicyear")
    , @NamedQuery(name = "Student.findByEmailadd", query = "SELECT s FROM Student s WHERE s.emailadd = :emailadd")
    , @NamedQuery(name = "Student.findByPhone", query = "SELECT s FROM Student s WHERE s.phone = :phone")
    , @NamedQuery(name = "Student.findByGender", query = "SELECT s FROM Student s WHERE s.gender = :gender")
    , @NamedQuery(name = "Student.findByPassword", query = "SELECT s FROM Student s WHERE s.password = :password")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idstudent")
    private Integer idstudent;
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "sponsor")
    private String sponsor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "yearofstudy")
    private int yearofstudy;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "academicyear")
    private int academicyear;
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
    @JoinColumn(name = "staffid", referencedColumnName = "adminId")
    @ManyToOne(optional = false)
    private Admins staffid;
    @JoinColumn(name = "status", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "department", referencedColumnName = "iddepartment")
    @ManyToOne(optional = false)
    private Department department;

    public Student() {
    }

    public Student(Integer idstudent) {
        this.idstudent = idstudent;
    }

    public Student(Integer idstudent, String firstname, String regnum, Date dob, String nationalid, String sponsor, int yearofstudy, Date creationDate, String secondname, String thirdname, int academicyear, String emailadd, int phone, String gender, String password) {
        this.idstudent = idstudent;
        this.firstname = firstname;
        this.regnum = regnum;
        this.dob = dob;
        this.nationalid = nationalid;
        this.sponsor = sponsor;
        this.yearofstudy = yearofstudy;
        this.creationDate = creationDate;
        this.secondname = secondname;
        this.thirdname = thirdname;
        this.academicyear = academicyear;
        this.emailadd = emailadd;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
    }

    public Integer getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(Integer idstudent) {
        this.idstudent = idstudent;
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

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getYearofstudy() {
        return yearofstudy;
    }

    public void setYearofstudy(int yearofstudy) {
        this.yearofstudy = yearofstudy;
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

    public int getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(int academicyear) {
        this.academicyear = academicyear;
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
        hash += (idstudent != null ? idstudent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.idstudent == null && other.idstudent != null) || (this.idstudent != null && !this.idstudent.equals(other.idstudent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Student[ idstudent=" + idstudent + " ]";
    }
    
}
