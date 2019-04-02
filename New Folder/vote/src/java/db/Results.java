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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amon.Sabul
 */
@Entity
@Table(name = "results")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Results.findAll", query = "SELECT r FROM Results r")
    , @NamedQuery(name = "Results.findByVotes", query = "SELECT r FROM Results r WHERE r.votes = :votes")
    , @NamedQuery(name = "Results.findByCandidateid", query = "SELECT r FROM Results r WHERE r.candidateid = :candidateid")
    , @NamedQuery(name = "Results.findByYear", query = "SELECT r FROM Results r WHERE r.year = :year")
    , @NamedQuery(name = "Results.findByDateVoted", query = "SELECT r FROM Results r WHERE r.dateVoted = :dateVoted")
    , @NamedQuery(name = "Results.findByPosition", query = "SELECT r FROM Results r WHERE r.position = :position")})
public class Results implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "votes")
    private long votes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "candidateid")
    private int candidateid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateVoted")
    @Temporal(TemporalType.TIMESTAMP)
    @Id
    private Date dateVoted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "position")
    private int position;

    public Results() {
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public int getCandidateid() {
        return candidateid;
    }

    public void setCandidateid(int candidateid) {
        this.candidateid = candidateid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDateVoted() {
        return dateVoted;
    }

    public void setDateVoted(Date dateVoted) {
        this.dateVoted = dateVoted;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    
}
