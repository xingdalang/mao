package com.dl.mao.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
	  /** 指定加密算法为RSA */
    private static final String ALGORITHM = "RSA";

    private static String RSAC = "RSA/ECB/PKCS1Padding";


    /** 密钥长度，用来初始化 */
    private static final int KEYSIZE = 1024;
    /** 指定密钥对存放文件 */
    private static Map<Integer,KeyPair> KeyList = new HashMap<Integer,KeyPair>();

    private static Cipher cipher;
    /**
     * 生成密钥对
     * @param index 密钥索引
     * @throws Exception
     */
    private static void generateKeyPair(Integer index) throws Exception {

        /** 为RSA算法创建一个KeyPairGenerator对象 */
    	KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSAC);
        
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
    	keyPairGenerator.initialize(KEYSIZE);
        
        /** 生成密钥对 */
    	KeyPair keyPair = keyPairGenerator.generateKeyPair();
        System.out.println(JSON.toJSONString(keyPair.getPrivate()));
        System.out.println(JSON.toJSONString(keyPair.getPublic()));
        KeyList.put(index, keyPair);
    }

    public static void main(String[] args) {
        try {
            generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();

            System.out.println(JSON.toJSONString(keyPair.getPrivate()));
            System.out.println(JSON.toJSONString(keyPair.getPublic()));

//            saveKeyPair(keyPair);
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    
    private static String getModulus(Integer index){
    	RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyList.get(index).getPublic();
    	return new String(Hex.encodeHex(rsaPublicKey.getModulus().toByteArray()));
    }
    
    private static String getPublicExponent(Integer index){
    	RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyList.get(index).getPublic();
    	return new String(Hex.encodeHex(rsaPublicKey.getPublicExponent().toByteArray()));
    }
    
    /**
     * 获得公钥
     * @param index 公钥索引 如果没有则生成
     * @return 数组 数组第一位为Modulus 第二位为 PublicExponent
     */
    public static String[] getPublicKeys(Integer index){
    	String[] result = new String[2];
    	if(!KeyList.containsKey(index)){
    		try {
				generateKeyPair(index);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	result[0] = getModulus(index).substring(2);
    	result[1] = getPublicExponent(index).substring(1);
    	return result;
    	
    }


    /**
     * 加密方法
     * @param source 源数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String source, Key publicKey, String charsetName) throws Exception {
        
    	 /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        if(cipher == null){
        	cipher = Cipher.getInstance(RSAC, new BouncyCastleProvider());
        }
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] b;
        if(StringUtils.isEmptyOrNull(charsetName)){
        	b = source.getBytes("utf-8");
        }else{
        	b = source.getBytes();
        }
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
       
        return Base64.encode(b1);
    }
    public static String encrypt(String source, String publicKeyStr){
        try {
            PublicKey publicKey = getPublicKey(publicKeyStr);
            String encrypt = encrypt(source, publicKey, null);
            return encrypt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    /**
     * 解密算法
     * @param cryptograph    密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph,Key privateKey) throws Exception {
        
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        if(cipher == null){
        	cipher = Cipher.getInstance(RSAC, new BouncyCastleProvider());
        }
        try{
        	 cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] b1 = Base64.decode(cryptograph);

             /** 执行解密操作 */
             byte[] b = cipher.doFinal(b1);
             return new String(b);
        }catch(Exception e){
        	//一旦发生RSA解密错误，重置cipher
        	cipher = null;
        	e.printStackTrace();
        	throw e;
        }
    }
    
    /** 
     * RSA签名 
     * @param content 待签名数据 
     * @param privateKey 商户私钥 
     * @param encode 字符集编码 
     * @return 签名值 
     */  
     public static String sign(String content, PrivateKey privateKey, String encode, String algorithm)
     {  
         try   
         {
             Signature signature = Signature.getInstance(algorithm);  
             signature.initSign(privateKey);  
             signature.update( content.getBytes(encode));  
             byte[] signed = signature.sign();  
             return Base64.encode(signed);
         }  
         catch (Exception e)   
         {  
             e.printStackTrace();  
         }  
         return "";  
     }
    public static String sign(String content, String privateKeyStr){
        try {
            PrivateKey privateKey = getPrivateKey(content);
            String sign = sign(content, privateKey, "utf-8", ALGORITHM);
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean doCheck(String content, String sign, String publicKeyStr){
        try {
            PublicKey publicKey = getPublicKey(publicKeyStr);
            boolean b = doCheck(content, sign, publicKey,ALGORITHM);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
      * 验签
      * @param content
      * @param sign
      * @param publicKey
      * @return
      */
     public static boolean doCheck(String content, String sign, PublicKey publicKey, String algorithm)
     {  
         try   
         {
             Signature signature = Signature.getInstance(algorithm);  
             signature.initVerify(publicKey);  
             signature.update(content.getBytes());  
             boolean bverify = signature.verify(Base64.decode(sign));
             return bverify;  
         }   
         catch (Exception e)   
         {  
             e.printStackTrace();  
         }  
         return false;  
     }


    public static PublicKey getPublicKey(String key)throws Exception{
        byte[] keyBytes;
        keyBytes = Base64.decode(key);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    public static PrivateKey getPrivateKey(String key)throws Exception{
        byte[] keyBytes;
        keyBytes = Base64.decode(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 解密
     * @param cryptograph
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph,String key) throws Exception {

        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        if(cipher == null){
            cipher = Cipher.getInstance(RSAC, new BouncyCastleProvider());
        }
        try{
            PrivateKey privateKey = getPrivateKey(key);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] b1 = Base64.decode(cryptograph);

            /** 执行解密操作 */
            byte[] b = cipher.doFinal(b1);
            return new String(b);
        }catch(Exception e){
            //一旦发生RSA解密错误，重置cipher
            cipher = null;
            e.printStackTrace();
            throw e;
        }
    }


}
