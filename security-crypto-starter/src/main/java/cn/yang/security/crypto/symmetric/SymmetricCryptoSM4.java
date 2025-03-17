package cn.yang.security.crypto.symmetric;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.crypto.util.EncodingUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

public class SymmetricCryptoSM4 implements SymmetricCryptoFacade {

    static {
        // 防止内存中出现多次BouncyCastleProvider的实例
        if (null == Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 算法使用SM4
     */
    private static final String ALGORITHM = "SM4";

    private static final int DEFAULT_KEY_BIT_SIZE = 128;

    private final KeyGenerator kg;

    /**
     * 加密密码
     */
    private final byte[] secret;


    /**
     * 分组模式
     */
    private String cryptoModel = SymmetricCryptoSM4Mode.SM4_CBC_PKCS7_PADDING.name;


    /**
     * @param secret 密钥，长度应为16字节
     */
    public SymmetricCryptoSM4(String secret) {

        this(secret, true);
    }

    /**
     * @param secret            密钥，长度应为16字节
     * @param encodeSecretAsHex 是否以Hex编码解析密钥。
     */
    public SymmetricCryptoSM4(String secret, boolean encodeSecretAsHex) {

        byte[] secretBytes;
        if (encodeSecretAsHex) {
            secretBytes = Hex.decode(secret);
        } else {
            secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        }

        if (secretBytes.length != 16) {
            throw new SymmetricCryptoException("the key length should be 16 bytes.");
        }
        this.secret = secretBytes;

        try {
            kg = KeyGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(DEFAULT_KEY_BIT_SIZE, new SecureRandom());

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new SymmetricCryptoException("the iv KeyGenerator instance failed.", e);
        }
    }

    /**
     * 设置要使用的加密模式.
     *
     * @param cryptoSM4Mode 要使用的加密模式
     */
    public void setCryptoModel(SymmetricCryptoSM4Mode cryptoSM4Mode) {
        if (cryptoSM4Mode == null) {
            throw new SymmetricCryptoException("symmetricCryptoSM4Mode cannot be null.");
        }
        this.cryptoModel = cryptoSM4Mode.name();
    }


    /**
     * 生成随机密钥
     *
     * @return 16位Hex密钥
     */
    public static String generateKey() {

        try {
            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            kg.init(DEFAULT_KEY_BIT_SIZE, new SecureRandom());
            byte[] secret = kg.generateKey().getEncoded();
            return Hex.toHexString(secret);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new SymmetricCryptoException("keyGenerator instance failed.", e);
        }
    }


    /**
     * 加密
     *
     * @param data 被加密的字节数组
     * @return 加密后的字节数组
     */
    @Override
    public byte[] encrypt(byte[] data) {

        byte[] iv = kg.generateKey().getEncoded();
        return EncodingUtils.concatenate(iv, encrypt(data, iv));

    }

    /**
     * 加密
     *
     * @param data 被加密的字节数组
     * @param iv   初始化向量
     * @return 加密后的字节数组
     */
    private byte[] encrypt(byte[] data, byte[] iv) {

        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);

        try {
            SecretKey secretKey = new SecretKeySpec(this.secret, ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.cryptoModel);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new SymmetricCryptoException("data encryption failed.", e);
        }
    }

    /**
     * 加密，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data    输入流
     * @param out     输出流
     */
    @Override
    public void encrypt(InputStream data, OutputStream out) {

        byte[] iv = kg.generateKey().getEncoded();
        try {
            out.write(iv);
            byte[] bytes = new byte[2048];
            // 记录每次读取的字节的个数
            int len;
            while ((len = data.read(bytes)) != -1) {
                byte[] result;
                if (len < 2048) {
                    byte[] finalBytes = new byte[len];
                    System.arraycopy(bytes, 0, finalBytes, 0, len);
                    result = encrypt(finalBytes, iv);
                } else {
                    result = encrypt(bytes, iv);
                }
                out.write(result);
            }
            out.flush();

        } catch (IOException e) {
            throw new SymmetricCryptoException("data encryption failed.", e);
        }
    }

    /**
     * 解密
     *
     * @param data 被解密的字节数组
     * @return 解密后的字节数组
     */
    @Override
    public byte[] decrypt(byte[] data) {

        byte[] iv = EncodingUtils.subArray(data, 0, 16);
        byte[] dataBytes = EncodingUtils.subArray(data, 16, data.length);
        return decrypt(dataBytes, iv);
    }

    /**
     * 解密
     *
     * @param data 被解密的字节数组
     * @param iv   初始化向量
     * @return 解密后的字节数组
     */
    private byte[] decrypt(byte[] data, byte[] iv) {

        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
        try {
            SecretKey secretKey = new SecretKeySpec(this.secret, ALGORITHM);
            Cipher cipher = Cipher.getInstance(this.cryptoModel);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            throw new SymmetricCryptoException("data decryption failed.", e);
        }
    }

    /**
     * 解密，针对大数据量，结束后不关闭流，调用方需自行处理流的关闭
     *
     * @param data    输入流
     * @param out     输出流
     */
    @Override
    public void decrypt(InputStream data, OutputStream out) {

        boolean ivRead = true;

        try {
            // 读取数据
            byte[] bytes = new byte[2064];
            byte[] iv = new byte[16];

            // 记录每次读取的字节的个数
            int len;
            while (true) {
                if (ivRead) {
                    len = data.read(iv);
                    if (len != 16) {
                        throw new SymmetricCryptoException("failed to read iv data from input stream.");
                    }
                    ivRead = false;
                } else if ((len = data.read(bytes)) != -1) {
                    byte[] result;
                    if (len < 2064) {
                        byte[] finalBytes = new byte[len];
                        System.arraycopy(bytes, 0, finalBytes, 0, len);
                        result = decrypt(finalBytes, iv);
                    } else {
                        result = decrypt(bytes, iv);
                    }
                    out.write(result);
                } else {
                    break;
                }
            }
            out.flush();

        } catch (IOException e) {
            throw new SymmetricCryptoException("data decryption failed.", e);
        }
    }

    public enum SymmetricCryptoSM4Mode {

        /**
         * 使用CBC分组操作模式，不使用填充。明文或密文的长度必须是16字节的整数倍。
         */
        SM4_CBC_NO_PADDING("SM4/CBC/NoPadding"),
        /**
         * 使用CBC分组操作模式，且使用PKCS#7填充。明文或密文的长度可以不是16字节的整数倍。
         */
        SM4_CBC_PKCS7_PADDING("SM4/CBC/PKCS7Padding"),
        /**
         * 使用CTR分组操作模式，不使用填充。明文或密文的长度可以不是16字节的整数倍。
         */
        SM4_CTR_NO_PADDING("SM4/CTR/NoPadding"),
        /**
         * 使用ECB分组操作模式，不使用填充。明文或密文的长度必须是16字节的整数倍。
         */
        SM4_ECB_NO_PADDING("SM4/ECB/NoPadding"),
        /**
         * 使用ECB分组操作模式，且使用PKCS#7填充。明文或密文的长度可以不是16字节的整数倍。
         */
        SM4_ECB_PKCS7_PADDING("SM4/ECB/PKCS7Padding"),
        /**
         * 使用GCM分组操作模式，不使用填充。明文或密文的长度可以不是16字节的整数倍。
         */
        SM4_GCM_NO_PADDING("SM4/GCM/NoPadding");

        private final String name;

        SymmetricCryptoSM4Mode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
