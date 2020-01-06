//package com.forezp.usdt;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
//import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import com.mashape.unirest.http.utils.Base64Coder;
//import org.apache.commons.codec.binary.Base64;
//import org.apache.commons.lang.StringUtils;
//import org.bitcoinj.core.Address;
//import org.bitcoinj.core.Coin;
//import org.bitcoinj.core.DumpedPrivateKey;
//import org.bitcoinj.core.ECKey;
//import org.bitcoinj.core.NetworkParameters;
//import org.bitcoinj.core.Sha256Hash;
//import org.bitcoinj.core.Transaction;
//import org.bitcoinj.core.TransactionInput;
//import org.bitcoinj.core.UTXO;
//import org.bitcoinj.core.Utils;
//import org.bitcoinj.crypto.TransactionSignature;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.script.Script;
//import org.bitcoinj.script.ScriptBuilder;
//import org.bouncycastle.util.encoders.Hex;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.JSONArray;
//import com.google.common.collect.Lists;
//
///**
// * Created by Ray Ma on 2019/12/11.
// */
//public class UsdtOneDemo {
//
//    private static final Logger logger = LoggerFactory.getLogger(UsdtOneDemo.class);
//    private static final String mainAddress = "xxx";//手续费地址
//    String username ="";
//    String password = "";
//    String url = "";
//
//    private final static String METHOD_GET_TRANSACTION = "omni_gettransaction";
//    //正式网络usdt=31，测试网络可以用2
//    private static final int propertyid = 2;
//    //广播交易的方法
//    private final static String DUMP_PRIVATE = "dumpprivkey";
//    private final static String RESULT = "result";
//    private final static String METHOD_GET_LISTUNSPENT = "listunspent";
//    //是否主網（default：true）
//    public Boolean isMainNet = false;
//
//    public void setIsMainNet(Boolean isMainNet) {
//        this.isMainNet = isMainNet;
//    }
//
//    private boolean isError(JSONObject json) {
//        if (json == null || (StringUtils.isNotEmpty(json.getString("error")) && json.get("error") != "null")) {
//            return true;
//        }
//        return false;
//    }
//
//    private JSONObject doRequest(String method, Object... params) {
//        JSONObject param = new JSONObject();
//        param.put("id", System.currentTimeMillis() + "");
//        param.put("jsonrpc", "2.0");
//        param.put("method", method);
//        if (params != null) {
//            param.put("params", params);
//        }
//        String creb = Base64.encodeBase64String((username + ":" + password).getBytes());
//        Map<String, String> headers = new HashMap<>(2);
//        headers.put("Authorization", "Basic " + creb);
//        String resp = "";
//        if (METHOD_GET_TRANSACTION.equals(method)) {
//            try {
//                resp = HttpUtil.jsonPost(url, headers, param.toJSONString());
//            } catch (Exception e) {
//                if (e instanceof IOException) {
//                    resp = "{}";
//                }
//            }
//        } else {
//            resp = HttpUtil.jsonPost(url, headers, param.toJSONString());
//        }
//        return JSON.parseObject(resp);
//    }
//
//    /**
//     * BTC获取私钥
//     */
//    public String getPrivateAddress(String address) {
//
//        String prikey = "b6e3afb68f75e6098c491078e5d13d4ac852ad87076488087fa5e76cc116c0b5";
////        JSONObject json = doRequest(DUMP_PRIVATE, address);
////        if (isError(json)) {
////            logger.error("获取USDT地址失败:{}" + json.get("error"));
////            return "";
////        }
////        return json.getString(RESULT);
//        return prikey;
//
//    }
//
//    /**
//     * BTC获取私钥
//     */
//    public String getAddress(String address) {
//        JSONObject json = doRequest(DUMP_PRIVATE, address);
//        if (isError(json)) {
//            logger.error("获取USDT地址失败:{}" + json.get("error"));
//            return "";
//        }
//        return json.getString(RESULT);
//
//    }
//
//    /**
//     * usdt 离线签名
//     *
//     * @param ：私钥
//     * @param toAddress：接收地址
//     * @param amount:转账金额
//     * @return
//     */
//    public String rawSignAndSend(String fromAddress, String toAddress, String changeAddress, Long amount) throws Exception {
//        List<UTXO> utxos = new ArrayList<UTXO>();
//        List<UTXO> utxoss = new ArrayList<UTXO>();
//        if (mainAddress.equals(fromAddress)) {
//            utxos = this.getUnspents(fromAddress);
//
//        } else {
//            utxos = this.getUnspents(fromAddress);
//            utxoss = this.getUnspents(toAddress);
//            for (int i = 0; i < utxoss.size(); i++) {
//                utxos.add(utxoss.get(i));
//            }
//        }
//        System.out.println(utxos);
//// 获取手续费
//        Long fee = this.getOmniFee(utxos);
////判断是主链试试测试链
//        NetworkParameters networkParameters = isMainNet ? MainNetParams.get() : TestNet3Params.get();
////NetworkParameters networkParameters = TestNet3Params.get();
//        Transaction tran = new Transaction(networkParameters);
//        if (utxos == null || utxos.size() == 0) {
//            throw new Exception("utxo为空");
//        }
////这是比特币的限制最小转账金额，所以很多usdt转账会收到一笔0.00000546的btc
//        Long miniBtc = 546L;
//        tran.addOutput(Coin.valueOf(miniBtc), Address.fromBase58(networkParameters, toAddress));//构建usdt的输出脚本 注意这里的金额是要乘10的8次方
//        String usdtHex = "6a146f6d6e69" + String.format("%016x", propertyid) + String.format("%016x", amount);
//        tran.addOutput(Coin.valueOf(0L), new Script(Utils.HEX.decode(usdtHex)));
//        Long changeAmount = 0L;
//        Long utxoAmount = 0L;
//        List<UTXO> needUtxo = new ArrayList<>();
////过滤掉多的uxto
//        for (UTXO utxo : utxos) {
//            if (utxoAmount > (fee + miniBtc)) {
//                break;
//            } else {
//                needUtxo.add(utxo);
//                utxoAmount += utxo.getValue().value;
//            }
//        }
//        changeAmount = utxoAmount - (fee + miniBtc);
////余额判断
//        if (changeAmount < 0) {
//            throw new Exception("utxo余额不足");
//        }
//        if (changeAmount > 0) {
//            tran.addOutput(Coin.valueOf(changeAmount), Address.fromBase58(networkParameters, changeAddress));
//        }//先添加未签名的输入，也就是utxo
//        for (UTXO utxo : needUtxo) {
//            tran.addInput(utxo.getHash(), utxo.getIndex(), utxo.getScript()).setSequenceNumber(TransactionInput.NO_SEQUENCE - 2);
//        }
////下面就是签名
//        for (int i = 0; i < needUtxo.size(); i++) {
////这里获取地址
//            String addr = needUtxo.get(i).getAddress();
//            String privateKeys = this.getPrivateAddress(addr);
//
//            ECKey ecKey = DumpedPrivateKey.fromBase58(networkParameters, privateKeys).getKey();
//            TransactionInput transactionInput = tran.getInput(i);
//            Script scriptPubKey = ScriptBuilder.createOutputScript(Address.fromBase58(networkParameters, addr));
//            Sha256Hash hash = tran.hashForSignature(i, scriptPubKey, Transaction.SigHash.ALL, false);
//            ECKey.ECDSASignature ecSig = ecKey.sign(hash);
//            TransactionSignature txSig = new TransactionSignature(ecSig, Transaction.SigHash.ALL, false);
//            transactionInput.setScriptSig(ScriptBuilder.createInputScript(txSig, ecKey));
//        }//这是签名之后的原始交易，直接去广播就行了
//        String signedHex = Hex.toHexString(tran.bitcoinSerialize());
//        logger.info("签名之后的原始交易:{}" + signedHex);
////这是交易的hash
//        String txHash = Hex.toHexString(Utils.reverseBytes(Sha256Hash.hash(Sha256Hash.hash(tran.bitcoinSerialize()))));
//        logger.info("fee:{},utxoAmount:{},changeAmount:{}", fee, utxoAmount, changeAmount, txHash);
//
//        JSONObject json = doRequest("sendrawtransaction", signedHex);
//        if (isError(json)) {
//            logger.error("发送交易失败");
//            return null;
//        } else {
//            String result = json.getString("result");
//            logger.info("发送成功 hash:{}", result);
//            return result;
//        }
//
//    }
//
//    /**
//     * 获取矿工费用
//     *
//     * @param utxos
//     * @return
//     */
//    public Long getOmniFee(List<UTXO> utxos) {
//        Long miniBtc = 546L;
//        Long feeRate = getFeeRate();
//        Long utxoAmount = 0L;
//        Long fee = 0L;
//        Long utxoSize = 0L;
//        for (UTXO output : utxos) {
//            utxoSize++;
//            if (utxoAmount > (fee + miniBtc)) {
//                break;
//            } else {
//                utxoAmount += output.getValue().value;
//                fee = (utxoSize * 148 + 34 * 2 + 10) * feeRate;
//            }
//        }
//        return fee;
//    }
//
//    public  List<UTXO> getUnspents(String... address) {
//        List<UTXO> utxos = Lists.newArrayList();
//
//        try {
//            JSONObject jsonObject = doRequest(METHOD_GET_LISTUNSPENT, 0, 99999999, address);
//            JSONArray outputs = jsonObject.getJSONArray("result");
//            if (outputs == null || outputs.size() == 0) {
//                System.out.println("交易异常，余额不足");
//            }
//            for (int i = 0; i < outputs.size(); i++) {
//                JSONObject outputsMap = outputs.getJSONObject(i);
//                String txid = outputsMap.get("txid").toString();
//                String vout = outputsMap.get("vout").toString();
//                String addr = outputsMap.get("address").toString();
//                String script = outputsMap.get("scriptPubKey").toString();
//                String amount = outputsMap.get("amount").toString();
//                BigDecimal bigDecimal = new BigDecimal(amount);
//                bigDecimal = bigDecimal.multiply(new BigDecimal(100000000));
//// String confirmations = outputsMap.get("confirmations").toString();
//                UTXO utxo = new UTXO(Sha256Hash.wrap(txid), Long.valueOf(vout), Coin.valueOf(bigDecimal.longValue()),
//                        0, false, new Script(Hex.decode(script)), addr);
//                System.out.println(utxo.getAddress());
//                utxos.add(utxo);
//            }
//            return utxos;
//        } catch (Exception e) {
//            logger.error("【BTC获取未消费列表】失败，", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取btc费率
//     *
//     * @return
//     */
//    public Long getFeeRate() {
//        try {
//            String httpGet1 = HttpUtil.get("https://bitcoinfees.earn.com/api/v1/fees/recommended");
//            Map map = JSON.parseObject(httpGet1, Map.class);
//            Long fastestFee = Long.valueOf(map.get("fastestFee").toString());
//            return fastestFee;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0L;
//        }
//    }
//
//    /**
//     * 给给是否是公链还是测试链
//     *
//     * @param
//     */
//    public boolean isMainNet(boolean b) {
//        if (b) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    public static void main(String[] args) {
////        UsdtOneDemo testDemo = new UsdtOneDemo();
////        testDemo.isMainNet(false);
////        String fromAddress = "XXX"; //转入地址
////        String toAddress = "XXX"; //转出地址
////        Long amount = 1000000000L;//这里要乘以10*8次方，才是正式的USDT个数
////        String txid = null;
////        try {
////            txid = testDemo.rawSignAndSend(fromAddress, toAddress, mainAddress, amount);
////        } catch (Exception e) {
////// TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////        System.out.println(txid);
//
//        List<UTXO> unspents = new UsdtOneDemo().getUnspents("mzbMnLDXWk2fy6eQ93gZLriXB3puQ7kXEi");
//
//
//    }
//
//
//
//    @Test
//    public void JsonrpcClient() throws MalformedURLException, UnirestException {
//        String url = "http://localhost:18332";
//        String username = "bj-root";
//        String password="888888";
//
//        String method = "omin_listunspent";
//
//        JsonRpcHttpClient jsonRpcHttpClient = new JsonRpcHttpClient(new URL(url));
//
//        String creb = Base64.encodeBase64String((username+":"+password).getBytes());
//        Map<String,String> headers = new HashMap<>(2);
//        headers.put("Authorization","Basic "+creb);
//        jsonRpcHttpClient.setHeaders(headers);
//
//
//        JSONObject request = new JSONObject();
//        request.put("id","1");
//        request.put("jsonrpc","2.0");
//        request.put("method",method);
//
//        this.invoke(url,method,headers);
//
//
////        jsonRpcHttpClient.
////        System.out.print(jsonObject);
//
//
//
//
//    }
//
//
//
//
//
//
////    public Object invoke(String url,String method,Map<String,String> headers, Object ... args) throws UnirestException {
////        JSONObject request = new JSONObject();
////        request.put("id","1");
////        request.put("jsonrpc","2.0");
////        request.put("method",method);
////        JSONArray params = new JSONArray();
////        for(int i=0;i<args.length;i++){
////            params.add(args[i]);
////        }
////        request.put("params",params);
////        HttpResponse<String> result =  Unirest.post(url)
////                .headers(headers)
////                .body(request.toJSONString())
////                .asString();
////
////        if(result.getStatus() != 200){
////            throw new RuntimeException("访问RPC服务失败");
////        }
////        String raw = result.getBody();
////        return JSON.parseObject(raw).get("result");
////    }
//
//
//
//
//
//}
