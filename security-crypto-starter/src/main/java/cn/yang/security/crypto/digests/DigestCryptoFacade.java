package cn.yang.security.crypto.digests;

import org.springframework.security.crypto.codec.Hex;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 未见清海
 */
public interface DigestCryptoFacade {

    /**
     * 数据摘要计算
     *
     * @param data 数据字节数组
     * @return 摘要计算结果的字节数组
     */
    byte[] digest(byte[] data);

    /**
     * 字符串摘要计算
     *
     * @param data    需摘要计算的字符串
     * @param charset 用于字符串编码的字符集
     * @return 摘要计算结果的字节数组
     */
    default byte[] digest(String data, Charset charset) {
        return digest(data.getBytes(charset));
    }

    /**
     * 字符串摘要计算，使用UTF_8字符集
     *
     * @param data 需摘要计算的字符串
     * @return 摘要计算结果的字节数组
     */
    default byte[] digest(String data) {
        return digest(data, StandardCharsets.UTF_8);
    }

    /**
     * 数据摘要计算，结果为Hex（16进制）的字符串表示形式
     *
     * @param data 数据字节数组
     * @return 摘要计算结果的Hex（16进制）字符串表示形式
     */
    default String digestToHexStr(byte[] data) {

        return String.valueOf(Hex.encode(digest(data)));
    }

    /**
     * 字符串摘要计算
     *
     * @param data    需摘要计算的字符串
     * @param charset 用于字符串编码的字符集
     * @return 摘要计算结果的Hex（16进制）字符串表示形式
     */
    default String digestToHexStr(String data, Charset charset) {
        return String.valueOf(Hex.encode(digest(data, charset)));
    }

    /**
     * 字符串摘要计算，使用UTF-8编码
     *
     * @param data 需摘要计算的字符串
     * @return 摘要计算结果的Hex（16进制）字符串表示形式
     */
    default String digestToHexStr(String data) {
        return digestToHexStr(data, StandardCharsets.UTF_8);
    }


    /**
     * 摘要计算
     *
     * @param data 数据字节数组
     * @return 摘要计算结果的Base64的字符串表示形式
     */
    default String digestToBase64Str(byte[] data) {
        return Base64.getEncoder().encodeToString(digest(data));
    }

    /**
     * 字符串摘要计算
     *
     * @param data    需摘要计算的字符串
     * @param charset 用于字符串编码的字符集
     * @return 摘要计算结果的Base64的字符串表示形式
     */
    default String digestToBase64Str(String data, Charset charset) {
        return Base64.getEncoder().encodeToString(digest(data, charset));
    }

    /**
     * 字符串摘要计算，使用UTF-8编码
     *
     * @param data 需摘要计算的字符串
     * @return 摘要计算结果的Base64的字符串表示形式
     */
    default String digestToBase64Str(String data) {
        return digestToBase64Str(data, StandardCharsets.UTF_8);
    }

    /**
     * 摘要计算，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data    输入流
     * @return 摘要计算结果字节数组
     */
    byte[] digest(InputStream data);

    /**
     * 摘要计算，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data 输入流
     * @return 摘要计算结果的Base64的字符串表示形式
     */
    default String digestToBase64Str(InputStream data) {
        return Base64.getEncoder().encodeToString(digest(data));
    }

    /**
     * 摘要计算，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data 输入流
     * @return 摘要计算结果的Hex（16进制）的字符串表示形式
     */
    default String digestToHexStr(InputStream data) {

        return String.valueOf(Hex.encode(digest(data)));
    }
}
