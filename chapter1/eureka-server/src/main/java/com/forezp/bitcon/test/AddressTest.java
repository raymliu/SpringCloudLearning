package com.forezp.bitcon.test;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ray Ma on 2019/12/15.
 */
public class AddressTest {

    @Test
    public void createWallet() throws Exception{
        String passPhrase = "123456";   // 钱包密码
        String filePath = "D:\\work";

        // TestNet3Params.get();  测试网地址
        NetworkParameters networkParameters = TestNet3Params.get();
        DeterministicSeed seed = new DeterministicSeed(
                new SecureRandom(), DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS, passPhrase, Utils.currentTimeSeconds());

        Wallet wallet = Wallet.fromSeed(networkParameters, seed);
        //私钥
        String privateKey = wallet.currentReceiveKey().getPrivateKeyAsWiF(networkParameters);
        //助记词
        String mnemonics = wallet.getKeyChainSeed().getMnemonicCode().toString();
        String publicKey = Hex.toHexString(ECKey.publicKeyFromPrivate(wallet.currentReceiveKey().getPrivKey(), true));
        //地址
        String address = wallet.currentReceiveAddress().toBase58();

        System.out.println("助记词: "+mnemonics);
        System.out.println("私钥: "+privateKey);
        System.out.println("公钥: "+publicKey);
        System.out.println("地址: "+address);
        System.out.println("地址: "+wallet.getKeyChainSeed().getMnemonicCode());

        List<String> mnemonicCode = wallet.getKeyChainSeed().getMnemonicCode();
        List<String> strings = Arrays.asList(mnemonics);

        byte[] seed2 = MnemonicCode.toSeed(mnemonicCode, passPhrase);
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed2);
        DeterministicKey deterministicKey = HDKeyDerivation.deriveChildKey(masterPrivateKey, ChildNumber.HARDENED_BIT);
        Wallet wallet2 = Wallet.fromSeed(networkParameters, seed);

    }
}
