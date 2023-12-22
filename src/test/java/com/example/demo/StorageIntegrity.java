package com.example.demo;

import com.example.demo.utils.FMSM2;
import com.example.demo.utils.FMSYS;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;


public class StorageIntegrity {
    //    存储完整性
    @Test
    public  void  test(){
        String s="输入数据";
        byte [] sm3=HMAC(s.getBytes());
        System.out.println(Base64.getEncoder().encodeToString(sm3));
    }


    //    存储完整性
    public   byte[] HMAC(byte[] indata){
        FMSYS sys = new FMSYS();
        SecretKey key = sys.ExportInternalKey(100);
        byte[] out = new byte[128];
        try {
            Mac mac = Mac.getInstance("HMac/SM3", "FishermanJCE");
            mac.init(key);
            mac.reset();
            mac.update(indata, 0, indata.length);
            out = mac.doFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

//存储机密性
    @Test
    public  void test2 () throws UnsupportedEncodingException {
        FMSYS sm4=new FMSYS();  //此工具类在接口资料的jcedemo中
        String s="1234567812345678"; //待加密数据
        byte [] indata=s.getBytes();
        byte [] encdata=sm4.InternalSM4Enc(10, "ECB", true, indata, null); //加密方法
        String str=Base64.getEncoder().encodeToString(encdata); //密文转base64储存
        System.out.println(str);
        byte [] data=Base64.getDecoder().decode(str);//读取密文解base64
        byte [] decdata=sm4.InternalSM4Dec(10, "ECB", true, encdata, null); //解密方法
        String res=new String(decdata,"utf-8"); //明文转字符串输出
        System.out.println(res);
    }

//   生成随机数
    @Test
    public void  test3 () {
        int size = 32;
        String keyid = "RandomSM2";
        keyid += 1;
        SecureRandom secureRandom;
        byte [] randcm;
        try {
            secureRandom = SecureRandom.getInstance(keyid,"FishermanJCE");
            randcm = secureRandom.generateSeed(size);
            System.out.println(Base64.getEncoder().encodeToString(randcm));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    校验签名接口
    @Test
    public void test4(){
        boolean rv;
        FMSM2 sm2 = new FMSM2();
        String certificate = "MIICsTCCAhmgAwIBAgIJAKS2a3bK9DANBgkqhkiG9w0BAQUFADCBkDELMAkGA1UE" +
                "BhMCVVMxEzARBgNVBAgTCkNhbGlmb3JuaWExEjAQBgNVBAcTCVNhbiBKb3NlMRMw" +
                "EQYDVQQKEwpPcGVuQSBBSTEMMAoGA1UECxMDRW50ZXIxGTAXBgNVBAMTEG15LmV4" +
                "YW1wbGUuY29tMRkwFwYJKoZIhvcNAQkBFgpib3R0b20wHhcNMTEwNjE1MTIwNDU1" +
                "WhcNMjEwNjE1MTIwNDU1WjCBkDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCkNhbGlm" +
                "b3JuaWExEjAQBgNVBAcTCVNhbiBKb3NlMRMwEQYDVQQKEwpPcGVuQSBBSTEMMAoG" +
                "A1UECxMDRW50ZXIxGTAXBgNVBAMTEG15LmV4YW1wbGUuY29tMRkwFwYJKoZIhvcN" +
                "AQkBFgpib3R0b20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMV/vgRfQX3d" +
                "B/nyvTZ19/6URq9N8G09Y7jgKcb3y7sXWunFp9pmxrDBiRb79SjueG4hLnpbNx/G" +
                "XfHr9cRpFMsNSUn6PL/nd2Q4euk8wAKvYUqBZLL22S8sPCDb4yjx4eBTAG29NNGe" +
                "uAe3QmczyhPjS9EnR2sJot9tVwVtAgMBAAGjge4wgeswDAYDVR0TAQH/BAIwADAN" +
                "BgkqhkiG9w0BAQUFAAOBgQCTfzoetVTQ9ebv0jD/xnt0asMvi4lj5RsB6kbfJNeZ" +
                "gA7SvaAxPQG8XG4tO0o1rQ+awAGhrwjvcvFc9Z1zASLZFnK6amZnvW5+J8yqGixO" +
                "1Ry/m6pIcz4hnSmcjzMz5kQknl+gGTPRAxnU+21WVJ6msSmeL2YtRT8cMMHZiZbF" +
                "dA==";
        byte[] certbyte = Base64.getDecoder().decode(certificate);
        CertificateFactory certFactory;
        X509Certificate cert = null;
        try {
            certFactory = CertificateFactory.getInstance( "X.509", "FishermanJCE");
            InputStream in = new ByteArrayInputStream(certbyte);
            cert = (X509Certificate) certFactory.generateCertificate(in);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }

        String s1="PXzHWb5A0jL7Djk5F4GCRK+onX7h/HvmRdDDm1QURcE=";
        byte[] indata32 = Base64.getDecoder().decode(s1);
        String s2="MEUCIDwAxfCmAfcuadcTMkv9Ai+Q2xo25BtU8W+PA7U/ybqLAiEAoZIILPyNppPXCD0hzbjjVl5i2VIrNKjiDVFURCCUAjQ=";
        byte[] sign = Base64.getDecoder().decode(s2);
        rv=sm2.ExternalSM2Verify("SM3withSM2",cert.getPublicKey(),indata32,sign);
        if (rv){
            System.out.println("验证通过");
        }else{
            System.out.println("验证失败" + rv);
        }

    }






}
