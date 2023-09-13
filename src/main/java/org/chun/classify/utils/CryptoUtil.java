package org.chun.classify.utils;

import org.chun.classify.enums.CryptoType;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CryptoUtil {
	private static final int GCM_IV_LEN = 12;
	private static final int GCM_SPEC_LEN = 128;
	private final CryptoType cryptoType;

	CryptoUtil(CryptoType cryptoType) {
		this.cryptoType = cryptoType;
	}

	public static CryptoUtil type(CryptoType cryptoType) {
		return new CryptoUtil(cryptoType);
	}

	public byte[] encrypt(byte[] plainText, byte[] key, byte[] iv, byte[] aad) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec keySpec = new SecretKeySpec(key, cryptoType.getAlgorithm());
		Cipher cipher = Cipher.getInstance(cryptoType.getTransformation());

		if(cryptoType.isIvRequired()){

			if(iv== null){
				iv = genSecureByte(GCM_IV_LEN);
			}

			GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_SPEC_LEN, iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		}else{
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		}

		if(cryptoType.isAadRequired()){
			cipher.updateAAD(aad);
		}

		return cipher.doFinal(plainText);
	}

	public  byte[] decrypt(byte[] plainText, byte[] key, byte[] iv, byte[] aad) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException {

		SecretKeySpec keySpec = new SecretKeySpec(key, cryptoType.getAlgorithm());
		Cipher cipher = Cipher.getInstance(cryptoType.getTransformation());

		if(cryptoType.isIvRequired()){

			if(iv== null){
				iv = genSecureByte(GCM_IV_LEN);
			}

			GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_SPEC_LEN, iv);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		}else{
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		}

		if(cryptoType.isAadRequired()){
			cipher.updateAAD(aad);
		}

		return cipher.doFinal(plainText);
	}

	private static byte[] genSecureByte(int len){
		byte[] bytes = new byte[len];
		new SecureRandom().nextBytes(bytes);
		return bytes;
	}
}
