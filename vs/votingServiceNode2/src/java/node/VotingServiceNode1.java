/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package node;

import javax.jws.WebService;

/**
 *
 * @author mine
 */
@WebService(serviceName = "blockchainVotesNode1", portName = "blockchainVotesPort", endpointInterface = "ws.BlockchainVotes", targetNamespace = "http://ws/", wsdlLocation = "WEB-INF/wsdl/votingServiceNode2/localhost_8080/votingServiceNode1/blockchainVotesNode1.wsdl")
public class VotingServiceNode1 {

    public java.util.List<java.lang.String> voteNode() {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
