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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mine
 */
@Entity
@Table(name = "result")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Result.findAll", query = "SELECT r FROM Result r"),
    @NamedQuery(name = "Result.findByIdresults", query = "SELECT r FROM Result r WHERE r.idresults = :idresults"),
    @NamedQuery(name = "Result.findByResult", query = "SELECT r FROM Result r WHERE r.result = :result"),
    @NamedQuery(name = "Result.findByCreationate", query = "SELECT r FROM Result r WHERE r.creationate = :creationate"),
    @NamedQuery(name = "Result.findByRegistrationyear", query = "SELECT r FROM Result r WHERE r.registrationyear = :registrationyear")})
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idresults")
    private Integer idresults;
    @Basic(optional = false)
    @NotNull
    @Column(name = "result")
    private int result;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creationate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registrationyear")
    private int registrationyear;
    @JoinColumn(name = "candidateid", referencedColumnName = "idcandidate")
    @ManyToOne(optional = false)
    private Candidate candidateid;
    @JoinColumn(name = "voterid", referencedColumnName = "idvoter")
    @ManyToOne(optional = false)
    private Voter voterid;
    @JoinColumn(name = "status", referencedColumnName = "idstatus")
    @ManyToOne(optional = false)
    private Status status;
    @JoinColumn(name = "positionid", referencedColumnName = "idposition")
    @ManyToOne(optional = false)
    private Position positionid;

    public Result() {
    }

    public Result(Integer idresults) {
        this.idresults = idresults;
    }

    public Result(Integer idresults, int result, Date creationate, int registrationyear) {
        this.idresults = idresults;
        this.result = result;
        this.creationate = creationate;
        this.registrationyear = registrationyear;
    }

    public Integer getIdresults() {
        return idresults;
    }

    public void setIdresults(Integer idresults) {
        this.idresults = idresults;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getCreationate() {
        return creationate;
    }

    public void setCreationate(Date creationate) {
        this.creationate = creationate;
    }

    public int getRegistrationyear() {
        return registrationyear;
    }

    public void setRegistrationyear(int registrationyear) {
        this.registrationyear = registrationyear;
    }

    public Candidate getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(Candidate candidateid) {
        this.candidateid = candidateid;
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

    public Position getPositionid() {
        return positionid;
    }

    public void setPositionid(Position positionid) {
        this.positionid = positionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idresults != null ? idresults.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Result)) {
            return false;
        }
        Result other = (Result) object;
        if ((this.idresults == null && other.idresults != null) || (this.idresults != null && !this.idresults.equals(other.idresults))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Result[ idresults=" + idresults + " ]";
    }
    
}
