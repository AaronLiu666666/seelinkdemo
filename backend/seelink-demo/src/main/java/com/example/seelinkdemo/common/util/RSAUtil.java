package com.example.seelinkdemo.common.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RSAUtil {

	//Base64编码转字节数组
	public static byte[] base642Byte(String base64Key) throws IOException{
		return Base64.decode(base64Key);
	}

	public static String rsaDec(String key, String hexStr) {
		RSA rsa = new RSA(key, null);
		byte[] bytes;
		try {
			bytes = HexUtil.decodeHex(hexStr);
		} catch (Exception e) {
			System.out.println("天翼云眼: getStreamUrlV1----->十六进制解码失败:");
			throw e;
		}

		byte[] decrypt;
		try {
			decrypt = rsa.decrypt(bytes, KeyType.PrivateKey);
		} catch (Exception e) {
			System.out.println("天翼云眼: getStreamUrlV1----->RSA解码失败:");
			throw e;
		}

		return StrUtil.str(decrypt, StandardCharsets.UTF_8);
	}

}
