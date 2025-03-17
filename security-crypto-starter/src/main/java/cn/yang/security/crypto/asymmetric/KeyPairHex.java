package cn.yang.security.crypto.asymmetric;

/**
 * @author 未见清海
 */
public class KeyPairHex {

    /**
     * 私钥，Hex格式
     */
    private final String privateKey;

    /**
     * 公钥，Hex格式
     */
    private final String publicKey;

    public KeyPairHex(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }


    @Override
    public String toString() {
        return "KeyPairHex{" +
                "privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
