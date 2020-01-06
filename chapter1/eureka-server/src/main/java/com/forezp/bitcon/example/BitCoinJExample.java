package com.forezp.bitcon.example;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.junit.Test;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ray Ma on 2019/10/25.
 */
public class BitCoinJExample {

    String LOCAL_WALLET_PATH = "temp/wallet";


    public void keyOperation() {


    }


    @Test
    public void getBalance() throws BlockStoreException {

        String localTestWallet = "apiTest.wallet";

        NetworkParameters networkParameters = getTestParams();

        //通过本地地址获取钱包
        Wallet wallet = this.getWalletFromFile(localTestWallet);
        List<Address> watchedAddresses = wallet.getWatchedAddresses();

        //同步本地区块头信息文件
        File blockFile = new File("/tmp/bitcoin-blocks");
        BlockStore blockStore = new SPVBlockStore(networkParameters, blockFile);
        BlockChain blockChain = new BlockChain(networkParameters, wallet, blockStore);
        PeerGroup peerGroup = new PeerGroup(networkParameters, blockChain);
        peerGroup.addWallet(wallet);
        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParameters));

//        wallet.addAndActivateHDChain();


        System.out.println("Start peer group");
        peerGroup.start();

        System.out.println("Downloading block chain");
        peerGroup.downloadBlockChain();
//        wallet.autosaveToFile(new File("/temp/wallet/apiTest.wallet"),0,,null);

        //获取余额信息
        String balance = wallet.getBalance().toFriendlyString();
        System.out.println("wallet with balance " + balance);
    }


    /**
     * 发送测试比特币
     */
    @Test
    public void sendCoins() throws UnreadableWalletException, BlockStoreException, ExecutionException, InterruptedException, InsufficientMoneyException {
        NetworkParameters params = getTestParams();
        String amount = "0.001";
        Wallet wallet = Wallet.loadFromFile(new File("apiTest.wallet"));
        String toAddress = "2N7HF1PLTnZHpYsgGkYkiwzVVsdnDPVtvcz";
        Address targetAddress = Address.fromBase58(params, toAddress);
        // Do the send of 1 BTC in the background. This could throw InsufficientMoneyException.
        SPVBlockStore blockStore = null;
        try {
            blockStore = new SPVBlockStore(params, new File("/tmp/bitcoin-blocks"));
        } catch (BlockStoreException e) {
            e.printStackTrace();
        }
        BlockChain chain = null;
        chain = new BlockChain(params, wallet, blockStore);
        PeerGroup peerGroup = new PeerGroup(params, chain);

        Wallet.SendResult result = wallet.sendCoins(peerGroup, targetAddress, Coin.parseCoin(amount));
        Transaction transaction = result.broadcastComplete.get();
        System.out.print(transaction.getHashAsString());

    }

    /**
     * 持久化钱包
     *
     * @throws IOException
     */
    @Test
    public void persistentWallet() throws IOException {
        ECKey ecKey = this.getECKeyFromPriKey("b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5");
        new Address(getTestParams(), "b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5");
        Wallet wallet = new Wallet(getTestParams());
        wallet.importKey(ecKey);
        File file = new File("apiTest.wallet");
        wallet.saveToFile(file);
    }


    /**
     * 通过私钥获取EcKey
     *
     * @param priKey
     * @return
     */

    public static ECKey getECKeyFromPriKey(String priKey) {
        ECKey ecKey = ECKey.fromPrivate(Numeric.toBigInt(priKey));
        return ecKey;
    }


    /**
     * 通过本地文件获取Wallet
     */
    public Wallet getWalletFromFile(String filePath) {
        try {
            return Wallet.loadFromFile(new File(filePath));
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取测试网络
     *
     * @return
     */
    public static NetworkParameters getTestParams() {
        return TestNet3Params.get();
    }
}
