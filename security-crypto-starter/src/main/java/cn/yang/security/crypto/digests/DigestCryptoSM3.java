package cn.yang.security.crypto.digests;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.io.InputStream;
import java.security.Security;

/**
 * @author 未见清海
 */
public class DigestCryptoSM3 implements DigestCryptoFacade {

    static {
        // 防止内存中出现多次BouncyCastleProvider的实例
        if (null == Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 数据摘要计算
     *
     * @param data 数据字节数组
     * @return 摘要计算结果的字节数组
     */
    @Override
    public byte[] digest(byte[] data) {

        SM3Digest digest = new SM3Digest();
        digest.update(data, 0, data.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 摘要计算，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data    输入流
     * @return 摘要计算结果字节数组
     */
    @Override
    public byte[] digest(InputStream data) {
        SM3Digest digest = new SM3Digest();

        try {
            byte[] bytes = new byte[2048];
            // 记录每次读取的字节的个数
            int len;
            while ((len = data.read(bytes)) != -1) {
                if (len < 2048) {
                    byte[] finalBytes = new byte[len];
                    System.arraycopy(bytes, 0, finalBytes, 0, len);
                    digest.update(finalBytes, 0, finalBytes.length);
                } else {
                    digest.update(bytes, 0, bytes.length);
                }
            }
            byte[] hash = new byte[digest.getDigestSize()];
            digest.doFinal(hash, 0);

            return hash;

        } catch (IOException e) {
            throw new DigestCryptoException("data digest failed.", e);
        }
    }
}
