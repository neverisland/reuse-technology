package cn.yang.security.crypto;

import cn.yang.security.crypto.asymmetric.AsymmetricCryptoFacade;
import cn.yang.security.crypto.asymmetric.AsymmetricCryptoSM2;
import cn.yang.security.crypto.asymmetric.KeyPairHex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author 未见清海
 */
class YtSecurityCryptoSM2Tests {

    @Test
    void encrypt() {

        String message = "测试Data123!#";
        KeyPairHex keyPair = AsymmetricCryptoSM2.generateKeyPair();
        AsymmetricCryptoFacade asymmetricCrypto = new AsymmetricCryptoSM2(keyPair);
        String encrypt = asymmetricCrypto.encryptToHexStr(message);
        Assertions.assertNotNull(encrypt);
        Assertions.assertFalse(encrypt.isBlank());
    }

    @Test
    void decrypt() {

        String message = "测试Data123!#";
        String encrypt = "0440152f8be10fc1f7188db0068327c6d2e8a81e5d00057e8c168813bad42f6c6839f88e23b8052d2243d8f87d28" +
                "f57513b34f6b237379ec8e0758eee336048bacf7773254a2d6c501106edccbaf9ce87ba460757e47c70ca1b420abe3129bb86" +
                "f6364dd914c8564b25cf17eb0527092";
        KeyPairHex keyPair = new KeyPairHex("4c7bd59c53be2c462dc56faef9400cb9c851e4de27eb471e10c1d904518bc3f4", "");
        AsymmetricCryptoFacade asymmetricCrypto = new AsymmetricCryptoSM2(keyPair);
        byte[] decrypt = asymmetricCrypto.decryptHex(encrypt);
        Assertions.assertEquals(message, new String(decrypt, StandardCharsets.UTF_8));
    }

    @Test
    void sign() {

        String message = "测试Data123!#";
        KeyPairHex keyPair = new KeyPairHex("ab8eab2b6e89432c754d89c4bc1d2d433fc2b53bf3ba56c4bc041600d8fb0cb", "");
        AsymmetricCryptoFacade asymmetricCrypto = new AsymmetricCryptoSM2(keyPair);
        String sign = asymmetricCrypto.sign(message);
        Assertions.assertNotNull(sign);
        Assertions.assertFalse(sign.isBlank());
    }

    @Test
    void verify() {

        String message = "测试Data123!#";
        String sign = "304402204d539c7d9560ba5a17a6381394cb09e8811480c1a436ff8533c2c8e82db5e0b702207c8b66be35045ad91c5" +
                "679a0acf34cdcb7a18490b7b7ea0551eee7daef992b6c";
        KeyPairHex keyPair = new KeyPairHex("", "04cf1a95e56d5fbe8a68fb60366965c788aa224b90ac23e0796" +
                "526a1ea1691e9c26e8fd914785e59ca1669ee952971df2bab75134344c3e615c84f2bc0787496cd");
        AsymmetricCryptoFacade asymmetricCrypto = new AsymmetricCryptoSM2(keyPair);
        boolean verify = asymmetricCrypto.verify(message, sign);
        Assertions.assertTrue(verify);
    }
}
