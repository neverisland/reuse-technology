package cn.yang.security.crypto;

import cn.yang.security.crypto.digests.DigestCryptoFacade;
import cn.yang.security.crypto.digests.DigestCryptoSM3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class YtSecurityCryptoSM3Tests {

    @Test
    void digest() {

        String message = "测试Data123!#";
        String messageDigest = "87705b768b7cc007b8c2b2dc778eb2566ba64d3b64b9a8f73815b62ab99c8711";
        DigestCryptoFacade digestCrypto = new DigestCryptoSM3();
        String digest = digestCrypto.digestToHexStr(message);
        Assertions.assertEquals(messageDigest, digest);
    }

    @Test
    void inputStreamDigest() {

        String message = "在那遥远的数字海岸，World轻轻对夜空诉说!星空下，汉字与字母共舞，\"故事\"与\"Story\"交织着梦的绸缪。" +
                "春水初生，春林初盛，@春风十里，不如你。数字编织的序曲中，新世界的门缓缓开启。爱，在心间轻声细语，爱心如密码，" +
                "解锁灵魂深处的秘密。穿过%时空隧道%，与你相遇，在平行宇宙的第7街角。夜深了，星光伴月，\"晚安\"（Good Night）" +
                "轻拂过静谧的窗棂。梦里，我们手牵手，跳过ASCII码的河流，寻觅未完的诗行。数学与诗意，在此交融，∞（无穷大）的想象，" +
                "跨越界限的飞翔。不论汉字、英文，大小写变换，在爱与梦想的领域，一切皆有可能，就像这首融合万物的歌，唱响于无垠的" +
                "宇宙之中，让心灵得以自由翱翔。";
        String messageDigest = "43472df1ccb019fbfe006341086a78d3dab6621de152c02c60d12cc8a08d22b5";
        try (InputStream inputStream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8))) {
            DigestCryptoFacade digestCrypto = new DigestCryptoSM3();
            String digest = digestCrypto.digestToHexStr(inputStream);
            Assertions.assertEquals(messageDigest, digest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
