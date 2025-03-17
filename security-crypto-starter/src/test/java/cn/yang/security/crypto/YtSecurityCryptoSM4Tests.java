package cn.yang.security.crypto;

import cn.yang.security.crypto.symmetric.SymmetricCryptoFacade;
import cn.yang.security.crypto.symmetric.SymmetricCryptoSM4;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author 未见清海
 */
class YtSecurityCryptoSM4Tests {

    @Test
    void encrypt() {

        String message = "测试Data123!#";
        String key = SymmetricCryptoSM4.generateKey();
        Assertions.assertNotNull(key, "key generation failed.");

        SymmetricCryptoFacade symmetricCrypto = new SymmetricCryptoSM4(key);
        String encrypt = symmetricCrypto.encryptToHexStr(message);
        Assertions.assertNotNull(encrypt, "sm4 encrypt failed.");
        Assertions.assertFalse(encrypt.isBlank(), "sm4 encrypt failed.");
    }

    @Test
    void decrypt() {

        String key = "d46cd4f8f07efdd27fa21a614e9b2a3c";
        String message = "测试Data123!#";
        String messageEncrypt = "385788076898d7eb7742a0239a2d05da61258bd0754ac8864aa29823ce727c25";
        SymmetricCryptoFacade symmetricCrypto = new SymmetricCryptoSM4(key);
        String decrypt = symmetricCrypto.decryptHexToStr(messageEncrypt);
        Assertions.assertEquals(message, decrypt, "sm4 decrypt failed.");
    }

    @Test
    void streamEncrypt() {

        String key = SymmetricCryptoSM4.generateKey();
        Assertions.assertNotNull(key, "key generation failed.");

        String message = "在那遥远的数字海岸，World轻轻对夜空诉说!星空下，汉字与字母共舞，\"故事\"与\"Story\"交织着梦的绸缪。" +
                "春水初生，春林初盛，@春风十里，不如你。数字编织的序曲中，新世界的门缓缓开启。爱，在心间轻声细语，爱心如密码，" +
                "解锁灵魂深处的秘密。穿过%时空隧道%，与你相遇，在平行宇宙的第7街角。夜深了，星光伴月，\"晚安\"（Good Night）" +
                "轻拂过静谧的窗棂。梦里，我们手牵手，跳过ASCII码的河流，寻觅未完的诗行。数学与诗意，在此交融，∞（无穷大）的想象，" +
                "跨越界限的飞翔。不论汉字、英文，大小写变换，在爱与梦想的领域，一切皆有可能，就像这首融合万物的歌，唱响于无垠的" +
                "宇宙之中，让心灵得以自由翱翔。";
        try (InputStream inputStream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            SymmetricCryptoFacade symmetricCrypto = new SymmetricCryptoSM4(key);
            symmetricCrypto.encrypt(inputStream, outputStream);

            String encrypt = new String(Hex.encode(outputStream.toByteArray()), StandardCharsets.UTF_8);
            Assertions.assertNotNull(encrypt, "sm4 stream encrypt failed.");
            Assertions.assertFalse(encrypt.isBlank(), "sm4 stream encrypt failed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void streamDecrypt() {

        String key = "11bb3a2a41fa54e7797b4f99168c7f98";
        String message = "在那遥远的数字海岸，World轻轻对夜空诉说!星空下，汉字与字母共舞，\"故事\"与\"Story\"交织着梦的绸缪。" +
                "春水初生，春林初盛，@春风十里，不如你。数字编织的序曲中，新世界的门缓缓开启。爱，在心间轻声细语，爱心如密码，" +
                "解锁灵魂深处的秘密。穿过%时空隧道%，与你相遇，在平行宇宙的第7街角。夜深了，星光伴月，\"晚安\"（Good Night）" +
                "轻拂过静谧的窗棂。梦里，我们手牵手，跳过ASCII码的河流，寻觅未完的诗行。数学与诗意，在此交融，∞（无穷大）的想象，" +
                "跨越界限的飞翔。不论汉字、英文，大小写变换，在爱与梦想的领域，一切皆有可能，就像这首融合万物的歌，唱响于无垠的" +
                "宇宙之中，让心灵得以自由翱翔。";
        String messageEncrypt = "725bbafe4bc47690d2760d1e4176bebdaaaffe255f2abe1df4bdc6af4eb9efbd3e7f3415d4b" +
                "ea143b83f2bc5c6afa8deafb9c4f98a54b72ffc91d0910ced3b73a9e3a71b4ab1b44fef551455a3c897ce57822d" +
                "2c6c0d1f374aab3c78f81bb6742a36086ccf026e61acdf71208e4af8d28827687d14476676c0a0f68f1fff92057" +
                "180c25de4a14f9124f4074c33abb8d21478f2ee433e0a4d8344a89a31c8296b53877fd14fae89d03b56e113b835" +
                "33337ccbaf5f8cb4abf7e3e9b6a3ffb5c392c5eee9899478182763f2db7047eb39e1edb9d0a4788d16426f975f5" +
                "215be8057a61453561c81a0a1f8b21bec2fb0c3d7130c8bf45ecbccc7b86e037974e74ff8cfc020971cd6151b9d" +
                "636efefbe0d89994b291c3b74e75c14364d666f053c1b9b0ba5263af71e468f1a9585c746f7328a32668ce947fe" +
                "ada48fbb79afc560f2b9c2cdd2d9b58c3d4036a0370a57c1609cf053209e7f55a812f6d02e33e5617497762893a" +
                "868e01cd1aea41dd5d3b05425114d65c4970520f213073d0e5c2bff2a6640bb75620f076936822e0081452050c6" +
                "773d1dee988d8dd4ed6a2759e7e0fffa175a2632479124a5691415a4a0681593264fdec005f870056d0695a2f97" +
                "90e47eed247b6ac2f76e6d5a413d58c0e3d5b4d00ed87b343951f47506799217c8736c28dd963e9d2b44c0e8420" +
                "8b1fddc6e16e334789b965b741ccc59dbfe5da96caff9af04fa083ae0c89c94c3fb54d78394ae86f3f8e5453e4d" +
                "e63b733cf6f08bae87b725d8ad23c19c883e08a3532ebab4c74b1d0352ea173ecf77d2f5d32216dec4712491118" +
                "0e26a483ff595833b8a2f8aff9dd91db817dcf452462ab9b52e4e18aad88a6f82fe753b9f95fdd5e845aa86ad75" +
                "63f4b6b0f06254a116c6ed8346aa3f39aae8ea136a6972367e44762e730e9f61cf25b73f2f39f5766db50841051" +
                "8f8b5d605fc585ef2778d340988db3dcd46d910e1d7140a8aea85a83c30f708b237c7093dc030bdebd813dc382f" +
                "f648ed617befa5fd3927fb878469ba3306220c0ab9f972361e09a66787085a7919a84cdb1dbfce05a9939546943" +
                "711b7c4b2511583c420ddbc725d7c0f1e2423a91768cfde12420fa9e066f052fdff3a8418ec02c29bb16e78a867" +
                "39ca7d642c34bf22f27ac5d669f5f36ddf7596ea840ea264b6aa55f235ab3b8ef77105d0a6";

        try (InputStream inputStream = new ByteArrayInputStream(Hex.decode(messageEncrypt.getBytes(StandardCharsets.UTF_8)));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            SymmetricCryptoFacade symmetricCrypto = new SymmetricCryptoSM4(key);
            symmetricCrypto.decrypt(inputStream, outputStream);
            String decrypt = outputStream.toString(StandardCharsets.UTF_8);
            Assertions.assertEquals(message, decrypt, "sm4 stream decrypt failed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
