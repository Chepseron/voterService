/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import db.Blockchain;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;


/**
 *
 * @author mscproject
 */
@WebService(serviceName = "blockchainVotesNode1")
public class blockchainVotes {

    List<String> node = new ArrayList();
    Blockchain bc = new Blockchain();
    @PersistenceContext(name = "muvotePU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "VoteNode")
    public List<String> getVoteNode() {
        StringBuilder br = new StringBuilder();
        List<Blockchain> BlockchainList = new ArrayList();
        BlockchainList = em.createQuery("select b from Blockchain b").getResultList();
        for (Blockchain bn : BlockchainList) {
            node.add("Current Hash~" + bn.getBlockchainPK().getCurrenthash() + "~Original Data~" + bn.getOriginaldata() + "~Previous Hash~" + bn.getPrevioushash() + "~TimeStamp~" + bn.getTimeStamp() + "~Current ID~" + bn.getBlockchainPK().getIdblockchain() + "~EOF");
        }
        return node;
    }

}
