package com.dl.mao.util;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author xingguanghui
 * @create 2018-03-21 16:41
 **/
@SuppressWarnings({ "restriction" })
public class ThreeDES {
    private static final String IV = "1234567-";
    public static final String KEY = "123456781234567812345678";

    /**
     * DESCBC加密
     *
     * @param src
     *            数据源
     * @param key
     *            密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    public String encryptDESCBC(final String src, final String key) throws Exception {

        // --生成key,同时制定是des还是DESede,两者的key长度要求不同
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --加密向量
        final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));

        // --通过Chipher执行加密得到的是一个byte的数组,Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.ENCRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        final byte[] b = cipher.doFinal(src.getBytes("UTF-8"));

        // --通过base64,将加密数组转换成字符串
        String encode = Base64.encode(b);
        return encode;
    }

    /**
     * DESCBC解密
     *
     * @param src
     *            数据源
     * @param key
     *            密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public static String decryptDESCBC(final String src, final String key) throws Exception {
        // --通过base64,将字符串转成byte数组
        byte[] bytesrc = Base64.decode(src);

        // --解密的key
        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // --向量
        final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        // --Chipher对象解密Cipher.getInstance("DES")就是采用ECB模式,cipher.init(Cipher.DECRYPT_MODE,
        // secretKey)就可以了.
        final Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);

    }




    // 3DESECB加密,key必须是长度大于等于 3*8 = 24 位哈
    public static String encryptThreeDESECB(final String src, final String key) throws Exception {
//        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final DESedeKeySpec dks = new DESedeKeySpec(hex2byte(key));


        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);
        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, securekey);
        final byte[] b = cipher.doFinal(hex2byte(src));
//        final byte[] b = cipher.doFinal(src.getBytes());
        String s = byte2hex(b);
        System.out.println("-->"+s);
        String encode = Base64.encode(b);
        return encode.replaceAll("\r", "").replaceAll("\n", "");
    }

    public static void main(String[] args) {
        try {
            String s = encryptThreeDESECB("3132333435360000", "209DD68C90A2E89032908F0909821343209DD68C90A2E890");
//            String s = encryptThreeDESECB("3132333435360000", "209DD68C90A2E89032908F0909821343209DD68C90A2E890");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位哈
    public String decryptThreeDESECB(final String src, final String key) throws Exception {
        // --通过base64,将字符串转成byte数组
        final BASE64Decoder decoder = new BASE64Decoder();
        final byte[] bytesrc = decoder.decodeBuffer(src);
        // --解密的key
        final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        final SecretKey securekey = keyFactory.generateSecret(dks);

        // --Chipher对象解密
        final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, securekey);
        final byte[] retByte = cipher.doFinal(bytesrc);

        return new String(retByte);
    }


    /**
     * 字节数组转换成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {// 二行制转字符串
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = byteHEX(b[n]);
            hs = hs + stmp;
        }
        return hs;
    }

    static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
    /**
     * 16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}
