package com.cashsale.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Enumeration;

public class KeytoolUtil {

    /** 密钥库密码 */
    private static final String password = "cashsale";

    /**
     * 查看密钥库所有条目
     */
    public static void ShowAlias(String keystoreName){
        FileInputStream in = null;
        try {
            in = new FileInputStream(keystoreName);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, password.toCharArray());
            Enumeration e = ks.aliases();
            while (e.hasMoreElements()) {
                System.out.println(e.nextElement());
            }
        } catch (Exception el) {
            el.printStackTrace();
        }
    }

    //修改密钥库口令
    public static void SetStorePass(String keystoreName, String oldPass, String newPass){
        char[ ] oldpass = oldPass.toCharArray();
        char[ ] newpass = newPass.toCharArray();
        try {
            FileInputStream in = new FileInputStream(keystoreName);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, oldpass);
            in.close();
            FileOutputStream output = new FileOutputStream(keystoreName);
            ks.store(output, newpass);
            output.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //添加新条目
    public static void addNew(String keystoreName, String aliasName, PrivateKey privateKey){
        try {
            //密钥库密码
            char[] storepass = password.toCharArray();
            //每个条目的密码
            char[] pass = password.toCharArray();
            // 获取密钥库.keystore的KeyStore对象,并加载密钥库
            FileInputStream in = new FileInputStream(keystoreName);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, storepass);
            // 获取别名对应的条目的证书链
            Certificate[] cchain = ks.getCertificateChain("myweb");

            // 向密钥库中添加新的条目
            ks.setKeyEntry(aliasName, privateKey, pass, cchain);
            in.close();

            // 将KeyStore对象内容写入新文件
            FileOutputStream output = new FileOutputStream(keystoreName);
            ks.store(output, storepass);
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取私钥
    public static RSAPrivateKey getPrivate(String aliasName, String keystoreName){
        try{
            //密钥库密码
            char[] storepass = password.toCharArray();
            //每个条目的密码
            char[] pass = password.toCharArray();
            // 获取密钥库.keystore的KeyStore对象,并加载密钥库
            FileInputStream in = new FileInputStream(keystoreName);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, storepass);
            return (RSAPrivateKey)ks.getKey(aliasName, pass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检验别名及删除条目
     * @param aliasName
     * @param keystoreName
     */
    public static void DeleteAlias(String aliasName, String keystoreName){
        try{
            FileInputStream in=new FileInputStream(keystoreName);
            KeyStore ks=KeyStore.getInstance("JKS");
            ks.load(in,password.toCharArray());
            if (ks.containsAlias(aliasName)){
                ks.deleteEntry(aliasName);
                FileOutputStream output=new FileOutputStream(keystoreName);
                ks.store(output,password.toCharArray());
                System.out.println("Alias "+aliasName+" deleted");
            }else{
                System.out.println("Alias not exist");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
