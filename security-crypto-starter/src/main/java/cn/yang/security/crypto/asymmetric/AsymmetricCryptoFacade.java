package cn.yang.security.crypto.asymmetric;


import org.springframework.security.crypto.codec.Hex;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 非对称加密能力定义
 *
 * @author 未见清海
 */
public interface AsymmetricCryptoFacade {

    /**
     * 加密
     *
     * @param data 被加密的字节数组
     * @return 加密后的字节数组
     */
    byte[] encrypt(byte[] data);

    /**
     * 加密字符串
     *
     * @param data    被加密的字符串
     * @param charset 用于字符串编码的字符集
     * @return 加密后的字节数组
     */
    default byte[] encrypt(String data, Charset charset) {
        return encrypt(data.getBytes(charset));
    }

    /**
     * 加密字符串，使用UTF_8字符集
     *
     * @param data 被加密的字符串
     * @return 加密后的字节数组
     */
    default byte[] encrypt(String data) {
        return encrypt(data, StandardCharsets.UTF_8);
    }

    /**
     * 加密数据，结果为Hex（16进制）的字符串表示形式
     *
     * @param data 数据
     * @return 加密后的Hex（16进制）的字符串表示形式
     */
    default String encryptToHexStr(byte[] data) {

        return String.valueOf(Hex.encode(encrypt(data)));
    }

    /**
     * 加密字符串
     *
     * @param data    被加密的字符串
     * @param charset 用于字符串编码的字符集
     * @return 加密后的Hex（16进制）的字符串表示形式
     */
    default String encryptToHexStr(String data, Charset charset) {
        return String.valueOf(Hex.encode(encrypt(data, charset)));
    }

    /**
     * 加密字符串，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的Hex（16进制）的字符串表示形式
     */
    default String encryptToHexStr(String data) {
        return encryptToHexStr(data, StandardCharsets.UTF_8);
    }


    /**
     * 加密
     *
     * @param data 数据
     * @return 加密后的Base64的字符串表示形式
     */
    default String encryptToBase64Str(byte[] data) {
        return Base64.getEncoder().encodeToString(encrypt(data));
    }

    /**
     * 加密字符串
     *
     * @param data    被加密的字符串
     * @param charset 用于字符串编码的字符集
     * @return 加密后的Base64的字符串表示形式
     */
    default String encryptToBase64Str(String data, Charset charset) {
        return Base64.getEncoder().encodeToString(encrypt(data, charset));
    }

    /**
     * 加密字符串，使用UTF-8编码
     *
     * @param data 被加密的字符串
     * @return 加密后的Base64的字符串表示形式
     */
    default String encryptToBase64Str(String data) {
        return encryptToBase64Str(data, StandardCharsets.UTF_8);
    }


    /**
     * 解密
     *
     * @param data 被解密的字节数组
     * @return 解密后的字节数组
     */
    byte[] decrypt(byte[] data);


    /**
     * 解密为字符串
     *
     * @param bytes   被解密的字节数组
     * @param charset 用于解密数据字节数组的编码字符集
     * @return 解密后的String
     */
    default String decryptToStr(byte[] bytes, Charset charset) {

        return new String(decrypt(bytes), charset);
    }

    /**
     * 解密为字符串，, 默认使用UTF_8字符集
     *
     * @param bytes 被解密的字节数组
     * @return 解密后的String
     */
    default String decryptToStr(byte[] bytes) {

        return decryptToStr(bytes, StandardCharsets.UTF_8);
    }


    /**
     * 解密Hex（16进制）的字符串表示形式
     *
     * @param data 被解密Hex（16进制）的字符串表示形式
     * @return 解密后的字节数组
     */
    default byte[] decryptHex(String data) {

        return decrypt(Hex.decode(data));
    }

    /**
     * 解密Hex（16进制）的字符串表示形式
     *
     * @param data    被解密Hex（16进制）的字符串表示形式
     * @param charset 用于解密数据字节数组的编码字符集
     * @return 解密后的字符串
     */
    default String decryptHexToStr(String data, Charset charset) {

        return decryptToStr(Hex.decode(data), charset);

    }

    /**
     * 解密Hex（16进制）的字符串表示形式, 默认使用UTF_8字符集
     *
     * @param data 被解密Hex（16进制）的字符串表示形式
     * @return 解密后的字符串
     */
    default String decryptHexToStr(String data) {
        return decryptHexToStr(data, StandardCharsets.UTF_8);
    }

    /**
     * 解密Base64表示的字符串
     *
     * @param data 解密Base64表示的字符串
     * @return 解密后的字节数组
     */
    default byte[] decryptBase64(String data) {
        return decrypt(Base64.getDecoder().decode(data));
    }

    /**
     * 解密Base64表示的字符串
     *
     * @param data    被解密Base64的字符串表示形式
     * @param charset 用于解密数据字节数组的编码字符集
     * @return 解密后的字符串
     */
    default String decryptBase64ToStr(String data, Charset charset) {
        return decryptToStr(Base64.getDecoder().decode(data), charset);
    }


    /**
     * 解密Hase64表示的字符串, 默认使用UTF_8字符集
     *
     * @param data Hase64表示的字符串
     * @return 解密后的字符串
     */
    default String decryptBase64ToStr(String data) {
        return decryptBase64ToStr(data, StandardCharsets.UTF_8);
    }

    /**
     * 数据签名
     *
     * @param content 待签名内容
     * @return 签名结果，Hex形式
     */
    String sign(byte[] content);

    /**
     * 数据签名
     *
     * @param content 待签名内容
     * @return 签名结果，Hex形式
     */
    default String sign(String content) {
        return sign(content, StandardCharsets.UTF_8);
    }

    /**
     * 数据签名
     *
     * @param content 待签名内容
     * @param charset 待签名内容的编码字符集
     * @return 签名结果，Hex形式
     */
    default String sign(String content, Charset charset) {
        return sign(content.getBytes(charset));
    }

    /**
     * 验证签名
     *
     * @param content 待签名内容
     * @param sign    签名结果
     * @return 验签结果，true为内容与签名匹配，false为内容与签名不匹配
     */
    boolean verify(byte[] content, String sign);

    /**
     * 验证签名
     *
     * @param content 待签名内容
     * @param sign    签名结果
     * @return 验签结果，true为内容与签名匹配，false为内容与签名不匹配
     */
    default boolean verify(String content, String sign) {
        return verify(content, StandardCharsets.UTF_8, sign);
    }

    /**
     * 验证签名
     *
     * @param content 待签名内容
     * @param charset 待签名内容的编码字符集
     * @param sign    签名结果
     * @return 验签结果，true为内容与签名匹配，false为内容与签名不匹配
     */
    default boolean verify(String content, Charset charset, String sign) {
        return verify(content.getBytes(charset), sign);
    }
}
