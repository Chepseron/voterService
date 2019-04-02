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
@Table(name = "position")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Position.findAll", query = "SELECT p FROM Position p"),
    @NamedQuery(name = "Position.findByIdposition", query = "SELECT p FROM Position p WHERE p.idposition = :idposition"),
    @NamedQuery(name = "Position.findByName", query = "SELECT p FROM Position p WHERE p.name = :name"),
    @NamedQuery(name = "Position.findByDescription", query = "SELECT p FROM Position p WHERE p.description = :description"),
    @NamedQuery(name = "Position.findByCreationdate", query = "SELECT p FROM Position p WHERE p.creationdate = :creationdate"),
    @NamedQuery(name = "Position.findByAcademicyear", query = "SELECT p FROM Position p WHERE p.academicyear = :academicyear"),
    @NamedQuery(name = "Position.findByRoles", query = "SELECT p FROM Position p WHERE p.roles = :roles")})
public class Position implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idposition")
    private Integer idposition;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "academicyear")
    private int academicyear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "roles")
    private String roles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "positionid")
    private Collection<Result> resultCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "positionid")
    private Collection<Candidate> candidateCollection;
    @JoinColumn(name = "staffid", referencedColumnName = "adminId")
    @ManyToOne
    private Admins staffid;
    @JoinColumn(name = "deptid", referencedColumnName = "iddepartment")
    @ManyToOne(optional = false)
    private Department deptid;

    public Position() {
    }

    public Position(Integer idposition) {
        this.idposition = idposition;
    }

    public Position(Integer idposition, String name, String description, Date creationdate, int academicyear, String roles) {
        this.idposition = idposition;
        this.name = name;
        this.description = description;
        this.creationdate = creationdate;
        this.academicyear = academicyear;
        this.roles = roles;
    }

    public Integer getIdposition() {
        return idposition;
    }

    public void setIdposition(Integer idposition) {
        this.idposition = idposition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public int getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(int academicyear) {
        this.academicyear = academicyear;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
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

    public Department getDeptid() {
        return deptid;
    }

    public void setDeptid(Department deptid) {
        this.deptid = deptid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idposition != null ? idposition.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
        if ((this.idposition == null && other.idposition != null) || (this.idposition != null && !this.idposition.equals(other.idposition))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Position[ idposition=" + idposition + " ]";
    }
    
}
