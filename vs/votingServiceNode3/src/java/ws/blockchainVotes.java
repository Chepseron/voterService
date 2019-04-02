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
import node.VotingServiceNode3;

/**
 *
 * @author mscproject
 */
@WebService(serviceName = "blockchainVotesNode3")
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

    @WebMethod(operationName = "AddVotesFromNodes")
    public String AddVotesFromNodes() {
        //obtain the list of the previous node 

        VotingServiceNode3 node = new VotingServiceNode3();
        
        for (String nodse : node.voteNode()) {
            try {

                bc = new Blockchain(Integer.parseInt(nodse.split("~")[8]), nodse.split("~")[1]);
                Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(nodse.split("~")[7]);  
                bc.setOriginaldata(nodse.split("~")[3]);
                bc.setTimeStamp(date1);
                bc.setPrevioushash(nodse.split("~")[5]);

                System.out.println("Previous hash " + nodse.split("~")[5] + " timestamp " + date1 + " Original data " + nodse.split("~")[3] + " Current hash " + nodse.split("~")[1]);
                utx.begin();
                em.persist(bc);
                utx.commit();

            } catch (Exception ex) {
                return ex.getMessage();
            }
        }
        return "Replicated blockchain";

    }
}
