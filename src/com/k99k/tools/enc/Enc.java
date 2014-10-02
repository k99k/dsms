package com.k99k.tools.enc;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.k99k.tools.RandomUtil;


/**
 * 可兼容C的AES+BASE64算法,CBC方式加全0向量
 * @author Keel
 *
 */
public class Enc {

	public Enc() {
	}
	
    public static byte[] rootkey = {79, 13, 33, -66, -58, 103, 3, -34, -45, 53, 9, 45, 28, -124, 50, -2};
    private static byte[] ivk = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    
    private static final char encChar(char in,int key){
    	int c = in ^ key;
    	//48-57,65-90,97-122为a-zA-Z0-9区间
//    	if ((c>=48 && c<=57)||(c>=65 && c<=90) ||(c>=97 && c<=122)) {
    	if ((c>=48 && c<=112)) {
    	    return (char)c;
		}else{
			c = 113+Integer.parseInt(String.valueOf(in));
		}
    	return (char)c;
    }
    
    private static final char decChar(char in,int key){
    	if (in > 112 && in <123) {
    		int n = in - 113;
    		return String.valueOf(n).charAt(0);
		}
    	return (char)(in ^ key);
    }
    
    public static void main(String[] args) {
		long num = 999234124323L;
		int keyPo = 2;
		int len = 20;
		char key = (char)RandomUtil.getRandomInt(48, 120);
		try {
			String enc = numEnc(num, len, keyPo, key);
			System.out.println("enc:"+enc+"           key:"+key);
			long dec = numDec(enc,keyPo);
			System.out.println("dec:"+dec);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * 解密,如果失败返回-1
     * @param enc 密文
     * @param keyPo key在密文中的位置
     * @return 原文int值,错误返回-1
     */
    public static final long numDec(String enc,int keyPo){
    	char[] arr = enc.toCharArray();
    	int len = arr.length;
    	if (keyPo>=len || keyPo<0) {
			return -1;
		}
    	int decLen = len - 1;
    	//反转数组
//    	char[] arr = new char[len];
//    	for (int i = 0; i < len; i++) {
//			arr[decLen-i] = encArr[i];
//		}
    	
    	char key = arr[keyPo];
    	char[] decArr = new char[decLen];
    	for (int i = 0; i < keyPo; i++) {
    		key = (char) (key+i);
			char c = decChar(arr[i], key);
			if (c<48 || c>57) {
				return -1;
			}
			decArr[i] = c;
		}
    	key = (char) (key+keyPo);
    	for (int i = keyPo+1; i < len; i++) {
    		key = (char) (key+i);
    		char c = decChar(arr[i], key);
    		if (c<48 || c>57) {
				return -1;
			}
    		decArr[i-1] = c;
		}
    	String s = String.valueOf(decArr);
    	return Long.parseLong(s);
    }
    
    /**
     * 加密一个数字为ascii码可见字符串，必须注意前两个参数要求
     * @param org 原文int,其位数长度0<=org<len
     * @param len 原文长度,必须大于org的倍数，如果len远大于org则密文重合部分较高，建议len取适当小的值
     * @param keyPo key位置(>0且<=len)
     * @param key 解密的key ,建议使用48-122内的随机数:(char)RandomUtil.getRandomInt(48, 120)
     * @return 返回密文,如参数不对返回null
     */
    public static final String numEnc(long org,int len,int keyPo,char key){
    	String src = String.valueOf(org);
    	int sLen = src.length();
    	if (sLen> len) {
    		System.out.println("sLen> len:"+sLen+" > "+ len);
			return null;
		}
    	if (keyPo<0 || keyPo>len || key<48 || key>122) {
    		System.out.println("keyPo error: keyPo:"+keyPo+" key:"+ key);
			return null;
		}
    	int noZeroCharStart = len - sLen;
    	char[] arr = src.toCharArray();
    	int outLen = len+1;
		char[] outArr = new char[outLen];
		//不足len的前面全部以0补齐,并加密
		//输出的string由noZeroCharStart,keyPo分成4段,其中key独占一段,需要确定各分段的位置
		char orgKey = key;
		if (noZeroCharStart < keyPo) {
			//key的位置在noZeroCharStart或其后
			for (int i = 0; i < noZeroCharStart; i++) {
				char c = '0';
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
			//noZeroCharStart到keyPo
			for (int i = noZeroCharStart; i < keyPo; i++) {
				char c = arr[i-noZeroCharStart];
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
			//key
			outArr[keyPo] = orgKey;
			key = (char) (key+keyPo);
			//keyPo到最后
			for (int i = keyPo+1; i < outLen; i++) {
				char c = arr[i-1-noZeroCharStart];
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
		}else{
			//key的位置在noZeroCharStart之前
			for (int i = 0; i < keyPo; i++) {
				char c = '0';
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
			//key
			outArr[keyPo] = orgKey;
			key = (char) (key+keyPo);
			noZeroCharStart++;
			for (int i = keyPo+1; i < noZeroCharStart; i++) {
				char c = '0';
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
			//最后
			for (int i = noZeroCharStart; i < outLen; i++) {
				char c = arr[i-noZeroCharStart];
				key = (char) (key+i);
				outArr[i] = encChar(c,key);
			}
		}
    	
    	return String.valueOf(outArr);
    }
    

	// 加密
	  	public static final String encrypt(String sSrc,byte[] key) {
	  		try {
				byte[] srcBytes = sSrc.getBytes("utf-8");
				Cipher cipher = null;
				srcBytes = zeroPadding(sSrc);
//  		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher = Cipher.getInstance("AES/CBC/NoPadding");
				
				SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
				IvParameterSpec iv =  new IvParameterSpec(ivk);//new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
				byte[] encrypted = cipher.doFinal(srcBytes);
				return Base64Coder.encode(encrypted);
			} catch (Exception e) {
				e.printStackTrace();
			}
	  		return null;
	  	}

	public static final String bytePrint(byte[] in){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < in.length; i++) {
			sb.append(in[i]);
		}
		return sb.toString();
	}

	private static final byte[] zeroPadding(String in){
		try {
			byte[] bs = in.getBytes("utf-8");
			int len = bs.length;
			int padding = len % 16;
			byte[] nbs;
			if (padding  != 0) {
				int nlen = len+(16-padding);
				nbs = new byte[nlen];
				System.arraycopy(bs, 0, nbs, 0, len);
				return nbs;
			}else{
				return bs;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static final byte[] clearPadding(byte[] in){
		int len = in.length;
		int nlen = len;
		for (int i = len-1; i >0; i--) {
			if(in[i] == 0){
				nlen--;
			}else{
				break;
			}
		}
		if (nlen == len) {
			return in;
		}
		byte[] re = new byte[nlen];
		System.arraycopy(in, 0, re, 0, nlen);
		return re;
		
	}

	// 解密
	  	public static final String decrypt(String sSrc,byte[] key){
	  		try {
	  			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	//  			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	  			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	  			
	  			IvParameterSpec iv = new IvParameterSpec(ivk);//new IvParameterSpec(ivParameter.getBytes());
	  			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	  			byte[] encrypted1 = Base64Coder.decode(sSrc);// 先用base64解密
	  			byte[] original = cipher.doFinal(encrypted1);
	  			
	  			String originalString = new String(clearPadding(original), "utf-8");
	//  			String originalString = new String(original, "utf-8");
	  			
	  			return originalString;
	  		} catch (Exception ex) {
	  			ex.printStackTrace();
	  			return null;
	  		}
	  	}

}
