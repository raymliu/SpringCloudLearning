package com.forezp.ertherumn.example;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ray Ma on 2019/10/25.
 */
public class Web3Example {

    String ropstenURL = "https://ropsten.infura.io";
    String fromPri = "2CA90C2047521864708068CAF04421BBA0A3A99E8D9616F78E2620B221BEE43F";


    BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);


    /**
     * 获取web3j客户端版本号
     * @throws IOException
     */
    @Test
    public void getVersion() throws IOException {
        Web3j web3j = getWeb3j();
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String version = web3ClientVersion.getWeb3ClientVersion();
        System.out.print("web3j client version :"+version);

    }

    /**
     *  地址间发送以太币
     */
    @Test
    public void send() throws IOException, ExecutionException, InterruptedException {

        Web3j web3j = getWeb3j();
        String fromAddress = "0x2ce90a9de8b7dB155C402344AeAd6B3FB670e9d0";
        String toAddress = "0xF7294a25B7CC18151e801c45a3923f219B9052e3";
        String bigDecimalValue = "0.5";

        //转账人私钥
        Credentials credentials = Credentials.create(fromPri);

        BigInteger nonce = getNonce(web3j, fromAddress);

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

        close(web3j);
        Assert.assertNotNull("fail",transactionHash);
//        if(transactionHash != null){
//            return "peding";//交易进行中
//        }else{
//            return "fail";//交易失败
//        }

    }

    /**
     * 获取某地址以太币余额
     */
    @Test
    public void getBalance( ) throws IOException {
            String address = "0x2ce90a9de8b7dB155C402344AeAd6B3FB670e9d0";
        BigInteger balance = getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();

        System.out.print("current address with balance : "+ balance);
    }


    /**
     *
     * @return
     */
    private Web3j getWeb3j(){
        return Web3j.build(new HttpService(ropstenURL));
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
