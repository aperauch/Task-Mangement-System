/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

/**
 *
 * @author AAperauch
 */

public class Crypto {
    private static KeyGenerator keygenerator;
    private static SecretKey myDesKey;
    private static Cipher desCipher;

    public static void genKeyWithPassphrase(String passphrase)
    {
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            myDesKey = keygenerator.generateKey();
            desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                        
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void loadKey(String file)
    {
        FileInputStream in = null;
 
        try {
            if (file == null || file.isEmpty())
                in = new FileInputStream(".//Exports//secret.key");
            else
                in = new FileInputStream(file);
            
            byte[] key = new byte[in.available()];
            in.read(key);
            myDesKey = new SecretKeySpec(key, "DES");
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void saveKey(String file)
    {    
        FileOutputStream out = null;
        try {
            byte dataToWrite[] = myDesKey.getEncoded();
            
            if (file == null || file.isEmpty())
                out = new FileOutputStream(".//Exports//secret.key");
            else
                out = new FileOutputStream(file);
            
            out.write(dataToWrite);
            out.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void generateKey()
    {
        try {
            keygenerator = KeyGenerator.getInstance("DES");
            myDesKey = keygenerator.generateKey();
            desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchPaddingException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static byte[] encrypt(String plainText)
    {
        try {
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] textEncrypted = desCipher.doFinal(plainText.getBytes());
            System.out.println("Plain Text : " + plainText);
            System.out.println("Text Encryted : " +  new String(textEncrypted));
            
            return textEncrypted;
        } catch (InvalidKeyException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalBlockSizeException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BadPaddingException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
    
    public static String decrypt(byte[] encryptedData)
    {
        try {
            if (desCipher == null)
                desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey, desCipher.getParameters());
            byte[] textDecrypted = desCipher.doFinal(encryptedData);
            System.out.println("Text Decrypted : " + new String(textDecrypted));
            
            return new String(textDecrypted);      
        } catch (InvalidKeyException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalBlockSizeException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BadPaddingException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidAlgorithmParameterException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NoSuchPaddingException ex) {
            //Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "An error occured!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return null;
    }
}
