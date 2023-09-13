package org.chun.classify.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CryptoType {

	AES_CBC_NOPADDING("AES", "AES/CBC/NoPadding", true, true),
	AES_CBC_PKCS5PADDING("AES", "AES/CBC/PKCS5Padding", true, true),
	AES_ECB_NOPADDING("AES", "AES/ECB/NoPadding", false, true),
	AES_ECB_PKCS5PADDING("AES", "AES/ECB/PKCS5Padding", false, false),
	AES_GCM_NOPADDING("AES", "AES/GCM/NoPadding", true, true),
	DESEDE_CBC_NOPADDING("DESede", "DESede/CBC/NoPadding", true, true),
	DESEDE_CBC_PKCS5PADDING("DESede", "DESede/CBC/PKCS5Padding", true, true),
	DESEDE_ECB_NOPADDING("DESede", "DESede/ECB/NoPadding", true, true),
	DESEDE_ECB_PKCS5PADDING("DESede", "DESede/ECB/PKCS5Padding", true, true),
	RSA_ECB_PKCS1PADDING("RSA", "RSA/ECB/PKCS1Padding", true, true),
	;

	private final String algorithm;
	private final String transformation;
	private final boolean ivRequired;
	private final boolean aadRequired;

}
