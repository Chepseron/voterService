package bean;

import com.google.gson.GsonBuilder;
import db.Blockchain;
import db.Result;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

public class VotingChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    Blockchain Blockchain = new Blockchain();
    public static int difficulty = 0;
    @PersistenceContext(name = "muvotePU")
    private EntityManager em;
    @Resource
    private UserTransaction utx;
    List<Result> ResultList = new ArrayList<Result>();

    public String push() {
        try {
            ResultList = em.createQuery("select r from Result r").getResultList();
            for (int i = 0; i < 10; i++) {
                if (blockchain.size() - 1 < 0) {
                    blockchain.add(new Block(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname(), "0"));
                    System.out.println("Trying to Mine block " + ResultList.get(i).getIdresults() + "... ");
                    blockchain.get(0).mineBlock(difficulty);
                    utx.begin();
                    Blockchain.setCurrenthash(blockchain.get(0).hash);
                    Blockchain.setOriginaldata(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname());
                    Blockchain.setPrevioushash(blockchain.get(0).previousHash);
                    Blockchain.setTimeStamp(new java.util.Date());
                    em.persist(Blockchain);
                    utx.commit();
                } else {
                    blockchain.add(ResultList.get(i).getIdresults(), new Block(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname(), blockchain.get(ResultList.get(i).getIdresults()-1).hash));
                    System.out.println("Trying to Mine block " + ResultList.get(i).getIdresults() + "... ");
                    blockchain.get(ResultList.get(i).getIdresults()).mineBlock(difficulty);
                    utx.begin();
                    Blockchain.setCurrenthash(blockchain.get(ResultList.get(i).getIdresults()).hash);
                    Blockchain.setOriginaldata(ResultList.get(i).getCandidateid() + ":" + ResultList.get(i).getCreationate() + ":" + ResultList.get(i).getPositionid().getName() + ":" + ResultList.get(i).getResult() + ":" + ResultList.get(i).getVoterid().getFirstname());
                    Blockchain.setPrevioushash(blockchain.get(ResultList.get(i).getIdresults()).previousHash);
                    Blockchain.setTimeStamp(new java.util.Date());
                    em.persist(Blockchain);
                    utx.commit();
                }
            }
            System.out.println("\nBlockchain is Valid: " + isChainValid());
            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
            System.out.println("\nThe block chain: ");
            System.out.println(blockchainJson);
            return "Posted";
        } catch (Exception ex) {

            Logger.getLogger(VotingChain.class.getName()).log(Level.SEVERE, null, ex);
            return "fail";
        }

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current Hashes not equal");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
