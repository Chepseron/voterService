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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author mscproject
 */
@Entity
@Table(name = "blockchain")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Blockchain.findAll", query = "SELECT b FROM Blockchain b")
    , @NamedQuery(name = "Blockchain.findByIdblockchain", query = "SELECT b FROM Blockchain b WHERE b.blockchainPK.idblockchain = :idblockchain")
    , @NamedQuery(name = "Blockchain.findByCurrenthash", query = "SELECT b FROM Blockchain b WHERE b.blockchainPK.currenthash = :currenthash")
    , @NamedQuery(name = "Blockchain.findByOriginaldata", query = "SELECT b FROM Blockchain b WHERE b.originaldata = :originaldata")
    , @NamedQuery(name = "Blockchain.findByTimeStamp", query = "SELECT b FROM Blockchain b WHERE b.timeStamp = :timeStamp")
    , @NamedQuery(name = "Blockchain.findByPrevioushash", query = "SELECT b FROM Blockchain b WHERE b.previoushash = :previoushash")})
public class Blockchain implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BlockchainPK blockchainPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "originaldata")
    private String originaldata;
    @Basic(optional = false)
    @NotNull
    @Column(name = "timeStamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "previoushash")
    private String previoushash;

    public Blockchain() {
    }

    public Blockchain(BlockchainPK blockchainPK) {
        this.blockchainPK = blockchainPK;
    }

    public Blockchain(BlockchainPK blockchainPK, String originaldata, Date timeStamp, String previoushash) {
        this.blockchainPK = blockchainPK;
        this.originaldata = originaldata;
        this.timeStamp = timeStamp;
        this.previoushash = previoushash;
    }

    public Blockchain(int idblockchain, String currenthash) {
        this.blockchainPK = new BlockchainPK(idblockchain, currenthash);
    }

    public BlockchainPK getBlockchainPK() {
        return blockchainPK;
    }

    public void setBlockchainPK(BlockchainPK blockchainPK) {
        this.blockchainPK = blockchainPK;
    }

    public String getOriginaldata() {
        return originaldata;
    }

    public void setOriginaldata(String originaldata) {
        this.originaldata = originaldata;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPrevioushash() {
        return previoushash;
    }

    public void setPrevioushash(String previoushash) {
        this.previoushash = previoushash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (blockchainPK != null ? blockchainPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Blockchain)) {
            return false;
        }
        Blockchain other = (Blockchain) object;
        if ((this.blockchainPK == null && other.blockchainPK != null) || (this.blockchainPK != null && !this.blockchainPK.equals(other.blockchainPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.Blockchain[ blockchainPK=" + blockchainPK + " ]";
    }
    
}
