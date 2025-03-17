package cn.yang.security.crypto;

import cn.yang.security.crypto.password.Pbkdf2PasswordEncoder;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @author 未见清海
 */
class YtSecurityCryptoPbkdf2PasswordTests {

    @Test
    void encode() {

        byte[] key = KeyGenerators.secureRandom(20).generateKey();
        String secret = new String(Hex.encode(key), StandardCharsets.UTF_8);
        String rawPassword = "SDN9=N3y;R3#|F=G%D6*";
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
        String encode = passwordEncoder.encode(rawPassword);
        Assertions.assertNotNull(encode);
        Assertions.assertFalse(encode.isBlank());
    }

    @Test
    void matches() {

        String secret = "c7a8d0222b1f6e7d28377569430197e24ec2ecac";
        String rawPassword = "SDN9=N3y;R3#|F=G%D6*";
        String encodedPassword = "ae7bce4dea264286f82433db1fd11d7f65d4ce11e4230fa8418f1ef8bb40a1ca8ddc3b4fa0088891bd7ff051824d9bc6";
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secret);
        boolean matchesResult = passwordEncoder.matches(rawPassword, encodedPassword);
        Assertions.assertTrue(matchesResult);
    }
}
