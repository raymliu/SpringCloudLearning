package com.forezp.bitcon.test;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.tomcat.util.buf.HexUtils;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.protocols.channels.StoredPaymentChannelClientStates;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.*;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletExtension;
import org.bitcoinj.wallet.WalletTransaction;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Ray Ma on 2019/10/22.
 */
public class BItcoinJ1 {

    public static void main(String[] args) throws BlockStoreException, IOException, UnreadableWalletException {

        NetworkParameters networkParameters = TestNet3Params.get();
//        System.out.print(networkParameters);
//
        ECKey ecKeypri = ECKey.fromPrivate(Numeric.toBigInt("b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5"));
//        ECKey ecKey = new ECKey();


        ECKey ecKeyPub = ECKey.fromPublicOnly(Numeric.toBigInt("0467515b6947da527f06f8c61aaa7a4614de055bb2429667ad4a39672697b8e105546efee26c0a893be7fe0ac95459f9005fd3e6ec19f9ef9fbc2728943de2e13c").toByteArray());

//
//         Address address = Address.g(networkParameters, ecKey, Script.ScriptType.P2PKH);
//         System.out.println("On the " + networkParameters+ " network, we can use this address:\n" + ecKey.getPublicKeyAsHex());




         //address mwbR7J8cDv9xdSPmcKbHwjinrpcw8QzUNq
        // 2N7HF1PLTnZHpYsgGkYkiwzVVsdnDPVtvcz
         // pri b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5
        //pub 0467515b6947da527f06f8c61aaa7a4614de055bb2429667ad4a39672697b8e105546efee26c0a893be7fe0ac95459f9005fd3e6ec19f9ef9fbc2728943de2e13c

        Wallet wallet =new  Wallet(networkParameters);
//        Wallet wallet = Wallet.loadFromFile(new File("test1.wallet"));
        wallet.importKey(ecKeypri);
        wallet.importKey(ecKeyPub);

        File blockFile = new File("/tmp/bitcoin-blocks");
//        if(!blockFile.exists()){
//            blockFile.createNewFile();
//        }
        BlockStore blockStore = new SPVBlockStore(networkParameters, blockFile);

        BlockChain blockChain = new BlockChain(networkParameters, wallet, blockStore);
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addWallet(wallet);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));

        System.out.println("Start peer group");
        peerGroup.start();

        System.out.println("Downloading block chain");
        peerGroup.downloadBlockChain();



        Coin balance = wallet.getBalance();
        long value = balance.getValue();
        System.out.print(value+"--------------");



    }

}
