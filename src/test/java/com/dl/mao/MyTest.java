package com.dl.mao;

import com.dl.mao.util.RSAUtils;
import com.dl.mao.util.ThreeDES;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xingguanghui
 * @create 2018-05-30 18:43
 **/
public class MyTest {
String aa = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAuvaoZX+ScrxEUncH/VSzDM2FXqKKHM0GW/keaQh5s3caZHbRqmy96foepoW/HFgsIA2vyuBakDBfTg50uOmwvvsLZMpZW0AD2X5WINgzLfbVRZ82+wQiIZyDcD3hnkJNUColMYOSZaAawMX4z97r2Uj3psjIrrmCTVf9KwBcGwIDAQAB";
String ab = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMC69qhlf5JyvERSdwf9VLMMzYVeoooczQZb+R5pCHmzdxpkdtGqbL3p+h6mhb8cWCwgDa/K4FqQMF9ODnS46bC++wtkyllbQAPZflYg2DMt9tVFnzb7BCIhnINwPeGeQk1QKiUxg5JloBrAxfjP3uvZSPemyMiuuYJNV/0rAFwbAgMBAAECgYAS0W6KDLcDFp7ZUO/8YLjnbqWWkyHtuFgwecR+OqUwNNy5P/v2BtztQi5A1eOTQHI/hLWxZ262iQOgGqAAt5lhk9+DwO3XyPnir82gBmMPpe6tHompQvzwrjYhAbtHjuBAx/zTuQUSJn3bLlE8AKzvZ+31INNEolZbkPHc0RYrIQJBAPs33orNF+NwUFwIz5Yl6wwrAL/44SJWFBMZNuJBv5F7+W4yMYbELVfpw0I0wZMpoibhZCvIiJBxjQZD76ME/4sCQQDEZhh4eF3Mt8A5edpk95I5TpGLBPVJDKSYGh0nHgv10ZVIhS97gi2pk4yn4IkNtAtfYIheD4O0YoawMpZQiKexAkEAzjB26XmYJ8ahTki1CMmqd9w7y1Tcg7Ae3eYOE/pe32vft8Bewfv2M3fcGVTvpHfBbSQFcaIv5E4ur3vHDggmLQJAD27ucVkUFehCpMGcld6WkQZJjaEgHVNvA7zZmZ9U1UZY8s/0gUIjkHXV0cNlQbxqQF53mNyjPuOXLfVrtnxmoQJBAM5B3BIQOnH8OANnFoOrg2dY5T04g0ngtbxuXRsraKBooCk+KrJ9H7aZM2DOHLDj2YbrWz51HQL99mdMrJboCFI=";
    @Test
    public void contextLoads() {
        System.out.println("sersd");
        Map<String,Object> req = new HashMap<>();
        String a = "{\"pass\":\"123456\",\"userName\":\"mao\"}";

        String encrypt = RSAUtils.encrypt(a, aa);
//        String sign = RSAUtils.sign(a, ab);




        String encrypt1 = RSAUtilss.encrypt(a, aa);
        String sign = RSAUtilss.sign(a, ab);
//        String decrypt = RSAUtilss.decrypt(encrypt1, ab);
        req.put("data",encrypt);

        try {
            String decrypt1 = RSAUtils.decrypt(encrypt1, ab);
            System.out.println(decrypt1);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("encrypt1:"+encrypt1);
//        System.out.println("decrypt:"+decrypt);
        System.out.println("sign:"+sign);
//        req.put("sign",)
    }








    @Test
    public void sdfsdf() {
        Map<String,Object> req = new HashMap<>();
        String a = "{\"pass\":\"123456\",\"userName\":\"mao\"}";
        try {
            String s = ThreeDES.encryptThreeDESECB(a,"209DD68C90A2E89032908F0909821343209DD68C90A2E890");
            System.out.println("---"+s);
            String s1 = ThreeDES.decryptDESCBC(s, "209DD68C90A2E89032908F0909821343209DD68C90A2E890");
            System.out.println(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
