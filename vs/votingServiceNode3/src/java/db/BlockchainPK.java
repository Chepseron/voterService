/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mscproject
 */
@Embeddable
public class BlockchainPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idblockchain")
    private int idblockchain;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "currenthash")
    private String currenthash;

    public BlockchainPK() {
    }

    public BlockchainPK(int idblockchain, String currenthash) {
        this.idblockchain = idblockchain;
        this.currenthash = currenthash;
    }

    public int getIdblockchain() {
        return idblockchain;
    }

    public void setIdblockchain(int idblockchain) {
        this.idblockchain = idblockchain;
    }

    public String getCurrenthash() {
        return currenthash;
    }

    public void setCurrenthash(String currenthash) {
        this.currenthash = currenthash;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idblockchain;
        hash += (currenthash != null ? currenthash.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlockchainPK)) {
            return false;
        }
        BlockchainPK other = (BlockchainPK) object;
        if (this.idblockchain != other.idblockchain) {
            return false;
        }
        if ((this.currenthash == null && other.currenthash != null) || (this.currenthash != null && !this.currenthash.equals(other.currenthash))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "db.BlockchainPK[ idblockchain=" + idblockchain + ", currenthash=" + currenthash + " ]";
    }
    
}
