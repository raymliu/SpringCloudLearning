//package com.forezp.bitcon.example;
//
//import org.bitcoinj.core.*;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.store.BlockStore;
//import org.bitcoinj.store.BlockStoreException;
//import org.bitcoinj.store.MemoryBlockStore;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
///**
// * Created by Ray Ma on 2019/10/27.
// */
//public class Test {
//
//    public static void main(String[] args) {
//        // work with testnet
//        final NetworkParameters netParams = TestNet3Params.get();
//
//        // data structure for block chain storage
//        BlockStore blockStore = new MemoryBlockStore(netParams);
//
//        // declare object to store and understand block chain
//        BlockChain chain;
//
//        try {
//
//            // initialize BlockChain object
//            chain = new BlockChain(netParams, blockStore);
//
//            // instantiate Peer object to handle connections
//            final Peer peer = new Peer(netParams,new VersionMessage(netParams,chain.getBestChainHeight()),chain,new PeerAddress(netParams,InetAddress.getLocalHost()));
//
//            // connect to peer node on localhost
//            peer.connect();
//
//            // run Peer's message handling loop in a thread
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        peer.run();
//                    } catch (PeerException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }).start();
//
//            // we found the hash of the genesis block on Bitcoin Block Explorer
//            Sha256Hash blockHash = new Sha256Hash("00000007199508e34a9ff81e6ec0c477a4cccff2a4767a8eee39c11db367b008");
//
//            // ask the node to which we're connected for the block
//            // and wait for a response
//            Future<Block> future = peer.getBlock(blockHash);
//            System.out.println("Waiting for node to send us the requested block: " + blockHash);
//
//            // get and use the Block's toString() to output the genesis block
//            Block block = future.get();
//            System.out.println("Here is the genesis block:\n" + block);
//
//            // we're done; disconnect from the peer node
//            peer.disconnect();
//
//            // handle the various exceptions; this needs more work
//        } catch (BlockStoreException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (PeerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
