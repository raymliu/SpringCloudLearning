//package com.forezp.bitcon.test;
//
//import com.google.common.util.concurrent.FutureCallback;
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.MoreExecutors;
//import org.bitcoinj.core.*;
//import org.bitcoinj.crypto.KeyCrypterException;
//import org.bitcoinj.kits.WalletAppKit;
//import org.bitcoinj.net.discovery.DnsDiscovery;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.RegTestParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.script.Script;
//import org.bitcoinj.store.BlockStoreException;
//import org.bitcoinj.store.SPVBlockStore;
//import org.bitcoinj.utils.BriefLogFormatter;
//import org.bitcoinj.wallet.SendRequest;
//import org.bitcoinj.wallet.Wallet;
//import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
//import org.web3j.utils.Numeric;
//
//import java.io.File;
//
//import static com.google.common.base.Preconditions.checkNotNull;
//
//
///**
// * Created by Ray Ma on 2019/10/17.
// */
//public class BitCoinJExample implements WalletCoinsReceivedEventListener {
//
//    public static void main(String[] args) throws Exception {
//        new BitCoinJExample().run();
//
//
//    }
//
//    public void run() throws BlockStoreException, InsufficientMoneyException {
//        this.init();
//    }
//
//    private void init() throws BlockStoreException, InsufficientMoneyException {
//        TestNet3Params networkParam = TestNet3Params.get();
//        ECKey ecKey = ECKey.fromPrivate(Numeric.toBigInt("b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5"));        System.out.print("create new key :"+ecKey);
//        Coin  coin =  Coin.parseCoin("0.1") ;
//
//        Address addressFromKey = Address.fromKey(networkParam,ecKey, Script.ScriptType.P2PKH);
//        System.out.println("Public Address generated: " + addressFromKey);
//
//        Wallet wallet =  Wallet.createBasic(networkParam);
//        wallet.importKey(ecKey);
//
//
//        File file = new File("/tmp/bitcoin-blocks");
////        if(!file.exists()){
////            file = new File("/temp");
////        }
//        SPVBlockStore spvBlockStore = new SPVBlockStore(networkParam, file);
//
//        BlockChain blockChain = new BlockChain(networkParam, wallet, spvBlockStore);
//        PeerGroup peerGroup = new PeerGroup(networkParam, blockChain);
//        peerGroup.addPeerDiscovery(new DnsDiscovery(networkParam));
//        peerGroup.addWallet(wallet);
//
//        System.out.print("start peer Group");
//        peerGroup.start();
//
//        System.out.println("Downloading block chain");
//        peerGroup.downloadBlockChain();
//        System.out.print("block chain Downloaded");
//
//        wallet.addCoinsReceivedEventListener(this);
//
//
//
//        //send coin
////        Coin coin = Coin.valueOf(10, 0);
//        Address.fromString(TestNet3Params.get(),"2N7HF1PLTnZHpYsgGkYkiwzVVsdnDPVtvcz");
//
//        final Wallet.SendResult sendResult = wallet.sendCoins(peerGroup, addressFromKey, coin);
//
//        sendResult.broadcastComplete.addListener(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Coins Sent! Transaction hash is " + sendResult.tx.getHashAsString());
//            }
//        }, MoreExecutors.newDirectExecutorService());
//
//    }
//
//
//    @Override
//    public void onCoinsReceived(Wallet wallet, Transaction transaction, Coin prevBalance, Coin newBalance) {
//        Coin coin = transaction.getValueSentFromMe(wallet);
//
//        System.out.println("Received tx for " + coin.toFriendlyString() + ": " + transaction);
//
//        System.out.println("Previous balance is " + prevBalance.toFriendlyString());
//
//        System.out.println("New estimated balance is " + newBalance.toFriendlyString());
//
//        System.out.println("Coin received, wallet balance is :" + wallet.getBalance());
//
//        Futures.addCallback(transaction.getConfidence().getDepthFuture(1),new FutureCallback<TransactionConfidence>() {
//            public void onSuccess(TransactionConfidence result) {
//                System.out.println("Transaction confirmed, wallet balance is :" + wallet.getBalance());
//            }
//
//            public void onFailure(Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }
//
//    /**
//     * Created by Ray Ma on 2019/10/16.
//     */
//    public static class BitCoinTest {
//
//
//
//            private static Address forwardingAddress;
//            private static WalletAppKit kit;
//
//        public static void main(String[] args) throws Exception {
//            // This line makes the log output more compact and easily read, especially when using the JDK log adapter.
//            BriefLogFormatter.init();
//            if (args.length < 1) {
//                System.err.println("Usage: address-to-send-back-to [regtest|testnet]");
//                return;
//            }
//
//            // Figure out which network we should connect to. Each one gets its own set of files.
//            NetworkParameters params;
//            String filePrefix;
//            if (args.length > 1 && args[1].equals("testnet")) {
//                params = TestNet3Params.get();
//                filePrefix = "forwarding-service-testnet";
//            } else if (args.length > 1 && args[1].equals("regtest")) {
//                params = RegTestParams.get();
//                filePrefix = "forwarding-service-regtest";
//            } else {
//                params = MainNetParams.get();
//                filePrefix = "forwarding-service";
//            }
//            // Parse the address given as the first parameter.
//            forwardingAddress =null;// LegacyAddress.fromBase58(params, args[0]);
//
//            System.out.println("Network: " + params.getId());
//            System.out.println("Forwarding address: " + forwardingAddress);
//
//            // Start up a basic app using a class that automates some boilerplate.
//            kit = new WalletAppKit(params, new File("."), filePrefix);
//
//            if (params == RegTestParams.get()) {
//                // Regression test mode is designed for testing and development only, so there's no public network for it.
//                // If you pick this mode, you're expected to be running a local "bitcoind -regtest" instance.
//                kit.connectToLocalHost();
//            }
//
//            // Download the block chain and wait until it's done.
//            kit.startAsync();
//            kit.awaitRunning();
//
//            // We want to know when we receive money.
//            kit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
//                @Override
//                public void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
//                    // Runs in the dedicated "user thread" (see bitcoinj docs for more info on this).
//                    //
//                    // The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).
//                    Coin value = tx.getValueSentToMe(w);
//                    System.out.println("Received tx for " + value.toFriendlyString() + ": " + tx);
//                    System.out.println("Transaction will be forwarded after it confirms.");
//                    // Wait until it's made it into the block chain (may run immediately if it's already there).
//                    //
//                    // For this dummy app of course, we could just forward the unconfirmed transaction. If it were
//                    // to be double spent, no harm done. Wallet.allowSpendingUnconfirmedTransactions() would have to
//                    // be called in onSetupCompleted() above. But we don't do that here to demonstrate the more common
//                    // case of waiting for a block.
//                    Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
//                        @Override
//                        public void onSuccess(TransactionConfidence result) {
//                            System.out.println("Confirmation received.");
//                            forwardCoins(tx);
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t) {
//                            // This kind of future can't fail, just rethrow in case something weird happens.
//                            throw new RuntimeException(t);
//                        }
//                    }, MoreExecutors.directExecutor());
//                }
//            });
//
//            Address sendToAddress =null;// Address.fromBase58(params, kit.wallet().currentReceiveKey());
//            System.out.println("Send coins to: " + sendToAddress);
//            System.out.println("Waiting for coins to arrive. Press Ctrl-C to quit.");
//
//            try {
//                Thread.sleep(Long.MAX_VALUE);
//            } catch (InterruptedException ignored) {
//            }
//        }
//
//        private static void forwardCoins(Transaction tx) {
//            try {
//                // Now send the coins onwards.
//                SendRequest sendRequest = SendRequest.emptyWallet(forwardingAddress);
//                Wallet.SendResult sendResult = kit.wallet().sendCoins(sendRequest);
//                checkNotNull(sendResult);  // We should never try to send more coins than we have!
//                System.out.println("Sending ...");
//                // Register a callback that is invoked when the transaction has propagated across the network.
//                // This shows a second style of registering ListenableFuture callbacks, it works when you don't
//                // need access to the object the future returns.
//                sendResult.broadcastComplete.addListener(new Runnable() {
//                    @Override
//                    public void run() {
//                        // The wallet has changed now, it'll get auto saved shortly or when the app shuts down.
//                        System.out.println("Sent coins onwards! Transaction hash is " + sendResult.tx);
//                    }
//                }, MoreExecutors.directExecutor());
//            } catch (KeyCrypterException | InsufficientMoneyException e) {
//                // We don't use encrypted wallets in this example - can never happen.
//                throw new RuntimeException(e);
//            }
//        }
//
//    }
//}
