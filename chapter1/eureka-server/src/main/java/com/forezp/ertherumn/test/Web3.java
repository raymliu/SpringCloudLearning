package com.forezp.ertherumn.test;

import org.bitcoinj.wallet.*;
import org.bitcoinj.wallet.Wallet;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ray Ma on 2019/10/17.
 */
public class Web3 {

//    public static void main(String[] args) throws Exception {
//
//        Admin web3j = Admin.build(new HttpService());
//        Credentials credentials = WalletUtils.loadCredentials("2CA90C2047521864708068CAF04421BBA0A3A99E8D9616F78E2620B221BEE43F", "0x2ce90a9de8b7dB155C402344AeAd6B3FB670e9d0");
//
//        TransactionReceipt transactionReceipt = Transfer.sendFunds(
//                web3j, credentials, "0xF7294a25B7CC18151e801c45a3923f219B9052e3",
//                BigDecimal.valueOf(0.001), Convert.Unit.ETHER)
//                .send();
//
//    }


    public static void main(String[] args) throws Exception {
//        String sendTest = sendTest("https://ropsten.infura.io","0x2ce90a9de8b7dB155C402344AeAd6B3FB670e9d0",
//                "0xF7294a25B7CC18151e801c45a3923f219B9052e3",  "2CA90C2047521864708068CAF04421BBA0A3A99E8D9616F78E2620B221BEE43F", "0.1");
//        System.out.println(sendTest);

//        String sendERC20 = sendERC20(StaticValue.myRopstenService, StaticValue.account2, StaticValue.account1, StaticValue.MttContractAddress, StaticValue.account2Pri, "100000");
//        System.out.println(sendERC20);

        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger("2ca90c2047521864708068caf04421bba0a3a99e8d9616f78e2620b221bee43f", 16));

        String s = WalletUtils.generateWalletFile("123456", ecKeyPair, new File("temp/"), false);


    }




    public static String sendTest(String netUrl,String ownAddress,String toAddress,String fromPri,String bigDecimalValue) throws InterruptedException, ExecutionException, IOException{
        //设置需要的矿工费
        BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

        //调用的是kovan测试环境，这里使用的是infura这个客户端   https://kovan.infura.io/<your-token>
        Web3j web3j = getWeb3j(netUrl);

        //获得余额
//        String ethBanlance = web3j.ethGetBalance(ownAddress,null);
//        String checkMoney = checkMoney(bigDecimalValue, ethBanlance);
//        if(!checkMoney.contentEquals("")){
//            return checkMoney;
//        }

//        Web3j web3j = Web3j.build(new HttpService(StaticValue.chongtiService)); //test
//        Web3j web3j = Web3j.build(new HttpService(chongtiService)); //test
        //转账人账户地址
//        String ownAddress = StaticValue.account1;
        //被转人账户地址
//        String toAddress = StaticValue.account2;
//        String toAddress = StaticValue.testk1234;
//        String toAddress = testk1234;
        //转账人私钥
        Credentials credentials = Credentials.create(fromPri);

        BigInteger nonce = getNonce(web3j, ownAddress);

        //创建交易，这里是转0.5个以太币
        BigInteger value = Convert.toWei(bigDecimalValue, Convert.Unit.ETHER).toBigInteger();
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, toAddress, value);

        //签名Transaction，这里要对交易做签名
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        //发送交易
        EthSendTransaction ethSendTransaction =
                web3j.ethSendRawTransaction(hexValue).send();
        String transactionHash = ethSendTransaction.getTransactionHash();


        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
        printTransaction(rawTransaction);
        System.out.println("交易id:"+transactionHash);

        Request<?, EthGetBalance> ethGetBalanceRequest = web3j.ethGetBalance(ownAddress, null);


        close(web3j);
        if(transactionHash != null){
            return "peding";//交易进行中
        }else{
            return "fail";//交易失败
        }

    }



    /**
     * 连接网络
     * @param netUrl
     * @return
     */
    public static Web3j getWeb3j(String netUrl){
        Admin web3j = Admin.build(new HttpService(netUrl));
        return web3j;
    }

    /**
     * 关闭网络
     * @param web3j
     */
    public static void close(Web3j web3j){
        web3j.shutdown();
    }

    /**
     * 获得交易笔数
     * @param web3j
     * @param ownAddress
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public static BigInteger getNonce(Web3j web3j,String ownAddress) throws InterruptedException, ExecutionException, IOException{
        //getNonce（这里的Nonce我也不是很明白，大概是交易的笔数吧）
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                ownAddress, DefaultBlockParameterName.LATEST).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }

    /**
     * 钱包地址余额是否足够转账校验
     * @param bigDecimalValue
     * @param addressBalance
     * @return
     */
    public static String checkMoney(String bigDecimalValue,String addressBalance){
        if(new BigDecimal(addressBalance).subtract(new BigDecimal(bigDecimalValue)).compareTo(new BigDecimal("0")) <= 0){
            return  "转账金额大于钱包地址余额";
        }else{
            return "";
        }

    }

    /**
     * 打印事务对象的一些信息
     * @param rawTransaction
     */
    private static void printTransaction(RawTransaction rawTransaction){
        System.out.println("交易时间"+rawTransaction.getData());
        System.out.println("转入地址:"+rawTransaction.getTo());
        System.out.println("gaslimit:"+rawTransaction.getGasLimit());
        System.out.println("gasPrice:"+rawTransaction.getGasPrice());
        System.out.println("nonce:"+rawTransaction.getNonce());
        System.out.println("value:"+rawTransaction.getValue());
    }

}
