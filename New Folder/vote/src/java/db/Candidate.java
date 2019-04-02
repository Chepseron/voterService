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
@Table(name = "candidate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Candidate.findAll", query = "SELECT c FROM Candidate c"),
    @NamedQuery(name = "Candidate.findByIdcandidate", query = "SELECT c FROM Candidate c WHERE c.idcandidate = :idcandidate"),
    @NamedQuery(name = "Candidate.findByCreationdate", query = "SELECT c FROM Candidate c WHERE c.creationdate = :creationdate"),
    @NamedQuery(name = "Candidate.findByPolicies", query = "SELECT c FROM Candidate c WHERE c.policies = :policies"),
    @NamedQuery(name = "Candidate.findByAlias", query = "SELECT c FROM Candidate c WHERE c.alias = :alias"),
    @NamedQuery(name = "Candidate.findByPhoto", query = "SELECT c FROM Candidate c WHERE c.photo = :photo")})
public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcandidate")
    private Integer idcandidate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "policies")
    private String policies;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "alias")
    private String alias;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "photo")
    private String photo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateid")
    private Collection<Result> resultCollection;
    @JoinColumn(name = "positionid", referencedColumnName = "idposition")
    @ManyToOne(optional = false)
    private Position positionid;
    @JoinColumn(name = "voterid", referencedColumnName = "idvoter")
    @ManyToOne(optional = false)
    private Voter voterid;
    @JoinColumn(name = "status", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "staffid", referencedColumnName = "adminId")
    @ManyToOne
    private Admins staffid;

    public Candidate() {
    }

    public Candidate(Integer idcandidate) {
        this.idcandidate = idcandidate;
    }

    public Candidate(Integer idcandidate, Date creationdate, String policies, String alias, String photo) {
        this.idcandidate = idcandidate;
        this.creationdate = creationdate;
        this.policies = policies;
        this.alias = alias;
        this.photo = photo;
    }

    public Integer getIdcandidate() {
        return idcandidate;
    }

    public void setIdcandidate(Integer idcandidate) {
        this.idcandidate = idcandidate;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @XmlTransient
    public Collection<Result> getResultCollection() {
        return resultCollection;
    }

    public void setResultCollection(Collection<Result> resultCollection) {
        this.resultCollection = resultCollection;
    }

    public Position getPositionid() {
        return positionid;
    }

    public void setPositionid(Position positionid) {
        this.positionid = positionid;
    }

    public Voter getVoterid() {
        return voterid;
    }

    public void setVoterid(Voter voterid) {
        this.voterid = voterid;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Admins getStaffid() {
        return staffid;
    }

    public void setStaffid(Admins staffid) {
        this.staffid = staffid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcandidate != null ? idcandidate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidate)) {
            return false;
        }
        Candidate other = (Candidate) object;
        if ((this.idcandidate == null && other.idcandidate != null) || (this.idcandidate != null && !this.idcandidate.equals(other.idcandidate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Candidate[ idcandidate=" + idcandidate + " ]";
    }
    
}
