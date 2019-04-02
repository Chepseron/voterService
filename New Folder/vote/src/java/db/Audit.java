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
 * @author mine
 */
@Entity
@Table(name = "audit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audit.findAll", query = "SELECT a FROM Audit a"),
    @NamedQuery(name = "Audit.findByIdaudit", query = "SELECT a FROM Audit a WHERE a.idaudit = :idaudit"),
    @NamedQuery(name = "Audit.findByActionperformed", query = "SELECT a FROM Audit a WHERE a.actionperformed = :actionperformed"),
    @NamedQuery(name = "Audit.findByDateperformed", query = "SELECT a FROM Audit a WHERE a.dateperformed = :dateperformed"),
    @NamedQuery(name = "Audit.findByUser", query = "SELECT a FROM Audit a WHERE a.user = :user")})
public class Audit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idaudit")
    private Integer idaudit;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "actionperformed")
    private String actionperformed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateperformed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateperformed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user")
    private int user;

    public Audit() {
    }

    public Audit(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public Audit(Integer idaudit, String actionperformed, Date dateperformed, int user) {
        this.idaudit = idaudit;
        this.actionperformed = actionperformed;
        this.dateperformed = dateperformed;
        this.user = user;
    }

    public Integer getIdaudit() {
        return idaudit;
    }

    public void setIdaudit(Integer idaudit) {
        this.idaudit = idaudit;
    }

    public String getActionperformed() {
        return actionperformed;
    }

    public void setActionperformed(String actionperformed) {
        this.actionperformed = actionperformed;
    }

    public Date getDateperformed() {
        return dateperformed;
    }

    public void setDateperformed(Date dateperformed) {
        this.dateperformed = dateperformed;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idaudit != null ? idaudit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audit)) {
            return false;
        }
        Audit other = (Audit) object;
        if ((this.idaudit == null && other.idaudit != null) || (this.idaudit != null && !this.idaudit.equals(other.idaudit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Audit[ idaudit=" + idaudit + " ]";
    }
    
}
