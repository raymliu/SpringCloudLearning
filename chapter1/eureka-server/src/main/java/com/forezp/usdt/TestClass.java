package com.forezp.usdt;

import java.math.BigDecimal;

/**
 * Created by Ray Ma on 2019/12/12.
 */
public class TestClass {

    public static void  main(String[] args) throws Exception {
        String toAddress = "mzbMnLDXWk2fy6eQ93gZLriXB3puQ7kXEi";
        String fromAddress  = "miyxpjZsfsvhWEiVDpKzu3qY8BTX8SsLWt";
        String changeAddress = "miyxpjZsfsvhWEiVDpKzu3qY8BTX8SsLWt";
        BigDecimal amount = new BigDecimal(0.1);

        UsdtService usdtService = new UsdtService();
        usdtService.rawSignAndSend(fromAddress,toAddress,changeAddress,20000000L);




    }
}
