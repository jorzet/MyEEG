package com.pt.myeeg.security;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jorge Zepeda Tinoco on 15/08/17.
 */

public class Hash {
    private byte[] digest = null;
    private String stringHash;

    public byte[] getHash(byte[] password){
        try{
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            sha256.update(password);
            digest = sha256.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.e("AlgorithmException: ", e.getLocalizedMessage());
        }
        return digest;
    }

    public String toString(){
        stringHash =new String(digest);
        return stringHash;
    }

    public boolean compareHash(String withHash){
        return stringHash.equals(withHash);
    }
}
