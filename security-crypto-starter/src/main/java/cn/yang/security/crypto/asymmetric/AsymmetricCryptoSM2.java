package cn.yang.security.crypto.asymmetric;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

/**
 * @author 未见清海
 */
public class AsymmetricCryptoSM2 implements AsymmetricCryptoFacade {

    static {
        // 防止内存中出现多次BouncyCastleProvider的实例
        if (null == Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 使用的算法名称
     */
    private static final String ALGORITHM = "EC";
    /**
     * EC域参数的标准名称
     */
    private static final String STD_NAME = "sm2p256v1";

    /**
     * 初始化签名实例时使用的ID,国密的要求,ID默认值:1234567812345678
     */
    private static final String SM2_ALGORITHM_ID = "1234567812345678";


    // 公钥点
    ECPoint publicKeyPoint;
    // 私钥值D
    BigInteger privateKeyD;


    public AsymmetricCryptoSM2(KeyPairHex keyPair) {
        // 获取一条SM2曲线参数
        String publicKey = keyPair.getPublicKey();
        if (null != publicKey && !publicKey.isEmpty()) {
            X9ECParameters sm2ECParameters = GMNamedCurves.getByName(STD_NAME);
            this.publicKeyPoint = sm2ECParameters.getCurve().decodePoint(Hex.decode(publicKey));
        }
        String privateKey = keyPair.getPrivateKey();
        if (null != privateKey && !privateKey.isEmpty()) {
            this.privateKeyD = new BigInteger(privateKey, 16);
        }
    }


    /**
     * 生成密钥对，公钥不压缩
     *
     * @return 密钥对
     */
    public static KeyPairHex generateKeyPair() {

        return generateKeyPair(false);
    }

    /**
     * 生成密钥对
     *
     * @param compressed 是否压缩公钥
     * @return 密钥对
     */
    public static KeyPairHex generateKeyPair(boolean compressed) {

        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, BouncyCastleProvider.PROVIDER_NAME);
            // 使用SM2曲线
            keyPairGen.initialize(new ECGenParameterSpec(STD_NAME));
            KeyPair keyPair = keyPairGen.generateKeyPair();

            ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();

            byte[] publicKeyBytes = publicKey.getQ().getEncoded(compressed);
            String publicKeyHex = Hex.toHexString(publicKeyBytes);
            String privateKeyHex = privateKey.getD().toString(16);
            return new KeyPairHex(privateKeyHex, publicKeyHex);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new AsymmetricCryptoException("key pair generation failed.", e);
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

        if (null == publicKeyPoint) {
            throw new AsymmetricCryptoException("PublicKey does not exist.");
        }

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName(STD_NAME);
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(publicKeyPoint, domainParameters);
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(true, new ParametersWithRandom(publicKeyParameters, new SecureRandom()));

        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new AsymmetricCryptoException("SM2 algorithm encrypts data abnormally.", e);
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
        if (null == privateKeyD) {
            throw new AsymmetricCryptoException("PrivateKey does not exist.");
        }
        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName(STD_NAME);
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(false, privateKeyParameters);

        try {
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (InvalidCipherTextException e) {
            throw new AsymmetricCryptoException("SM2 algorithm decryption data exception.", e);
        }
    }

    /**
     * 数据签名
     *
     * @param content 待签名内容
     * @return 签名结果，Hex形式
     */
    @Override
    public String sign(byte[] content) {

        if (null == privateKeyD) {
            throw new AsymmetricCryptoException("PrivateKey does not exist.");
        }

        //获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName(STD_NAME);
        //构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());

        ECPrivateKeyParameters privateKeyParameters = new ECPrivateKeyParameters(privateKeyD, domainParameters);

        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();

        //初始化签名实例,带上ID,国密的要求,ID默认值:1234567812345678
        try {
            sm2Signer.init(true, new ParametersWithID(new ParametersWithRandom(privateKeyParameters, SecureRandom.getInstance("SHA1PRNG")), Strings.toByteArray(SM2_ALGORITHM_ID)));
        } catch (NoSuchAlgorithmException e) {
            throw new AsymmetricCryptoException("SM2Signer init failed.", e);
        }
        sm2Signer.update(content, 0, content.length);
        //生成签名,签名分为两部分r和s,分别对应索引0和1的数组
        try {
            byte[] signBytes = sm2Signer.generateSignature();
            return Hex.toHexString(signBytes);
        } catch (CryptoException e) {
            throw new AsymmetricCryptoException("SM2Signer generate signature failed.", e);
        }
    }


    /**
     * 验证签名
     *
     * @param content 待签名内容
     * @param sign    签名值
     * @return boolean true/false
     */
    @Override
    public boolean verify(byte[] content, String sign) {

        if (null == publicKeyPoint) {
            throw new AsymmetricCryptoException("PublicKey does not exist.");
        }

        byte[] signData = Hex.decode(sign);

        // 获取一条SM2曲线参数
        X9ECParameters sm2ECParameters = GMNamedCurves.getByName(STD_NAME);
        // 构造domain参数
        ECDomainParameters domainParameters = new ECDomainParameters(sm2ECParameters.getCurve(), sm2ECParameters.getG(), sm2ECParameters.getN());
        // 公钥前面的02或者03表示是压缩公钥，04表示未压缩公钥, 04的时候，可以去掉前面的04
        ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(publicKeyPoint, domainParameters);
        //创建签名实例
        SM2Signer sm2Signer = new SM2Signer();
        ParametersWithID parametersWithID = new ParametersWithID(publicKeyParameters, Strings.toByteArray(SM2_ALGORITHM_ID));
        sm2Signer.init(false, parametersWithID);
        sm2Signer.update(content, 0, content.length);
        //验证签名结果
        return sm2Signer.verifySignature(signData);
    }
}
