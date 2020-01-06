package com.forezp.ertherumn.test;

import com.forezp.ertherumn.test.HelloWorld;
import com.forezp.ertherumn.test.Web3;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * Created by Ray Ma on 2019/10/21.
 */
public class Web3HelloWorld {

    public static void main(String[] args) throws Exception {

        String ropstenURL = "https://ropsten.infura.io";
        String fromPri = "2CA90C2047521864708068CAF04421BBA0A3A99E8D9616F78E2620B221BEE43F";


        BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);


        Web3j web3j = Web3j.build(new HttpService(ropstenURL));


        Credentials credentials = Credentials.create(fromPri);
        Web3 web3 = new Web3();

        HelloWorld helloWorld =  HelloWorld.deploy(web3j, credentials,GAS_PRICE,GAS_LIMIT).send();
        helloWorld.setInfo("ray",new BigInteger("28"));
        String contractAddress = helloWorld.getContractAddress();
        System.out.print(contractAddress);
    }
}
