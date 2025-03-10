package cn.yang.foundational.capability.sliding.verification.code.utils;

import cn.yang.foundational.capability.sliding.verification.code.exception.SlidingVerificationCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * 滑动验证码工具类
 */
public class CaptchaUtils {

    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtils.class);

    /**
     * 声明一个静态的 SecureRandom 对象
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private CaptchaUtils() {
    }

    /**
     * 获取指定范围内的随机数
     **/
    protected static int getNonceByRange(int start, int end) {
        // 使用预创建并保存的 SecureRandom 对象生成随机数
        return SECURE_RANDOM.nextInt(end - start + 1) + start;
    }

    /**
     * 获取验证码资源图
     **/
    protected static BufferedImage getBufferedImage() throws SlidingVerificationCodeException {
        try {
            //获取本地图片
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("static/captcha/*.jpg");
            Resource resource = resources[SECURE_RANDOM.nextInt(resources.length)];
            InputStream is = resource.getInputStream();
            return ImageIO.read(is);
        } catch (IOException e) {
            logger.error("获取拼图资源失败:", e);
            throw new SlidingVerificationCodeException("获取拼图资源失败");
        }
    }

    /**
     * 调整图片大小
     **/
    protected static BufferedImage imageResize(BufferedImage bufferedImage, int width, int height) {
        Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resultImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();
        return resultImage;
    }

    /**
     * 抠图，并生成阻塞块
     **/
    protected static void cutByTemplate(BufferedImage canvasImage, BufferedImage blockImage, int blockWidth, int blockHeight, int blockRadius, int blockX, int blockY) throws SlidingVerificationCodeException {
        BufferedImage waterImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        //阻塞块的轮廓图
        int[][] blockData = getBlockData(blockWidth, blockHeight, blockRadius);
        //创建阻塞块具体形状
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                try {
                    //原图中对应位置变色处理
                    if (blockData[i][j] == 1) {
                        //背景设置为黑色
                        waterImage.setRGB(i, j, Color.BLACK.getRGB());
                        blockImage.setRGB(i, j, canvasImage.getRGB(blockX + i, blockY + j));
                        //轮廓设置为白色，取带像素和无像素的界点，判断该点是不是临界轮廓点
                        if (blockData[i + 1][j] == 0 || blockData[i][j + 1] == 0 || blockData[i - 1][j] == 0 || blockData[i][j - 1] == 0) {
                            blockImage.setRGB(i, j, Color.WHITE.getRGB());
                            waterImage.setRGB(i, j, Color.WHITE.getRGB());
                        }
                    }
                    //这里把背景设为透明
                    else {
                        blockImage.setRGB(i, j, Transparency.TRANSLUCENT);
                        waterImage.setRGB(i, j, Transparency.TRANSLUCENT);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //防止数组下标越界异常
                }
            }
        }
        //在画布上添加阻塞块水印
        addBlockWatermark(canvasImage, waterImage, blockX, blockY);
    }

    /**
     * 构建拼图轮廓轨迹
     **/
    protected static int[][] getBlockData(int blockWidth, int blockHeight, int blockRadius) throws SlidingVerificationCodeException {
        int[][] data = new int[blockWidth][blockHeight];
        double po = Math.pow(blockRadius, 2);
        //随机生成两个圆的坐标，在4个方向上 随机找到2个方向添加凸/凹
        //凸/凹1
        int face1 = SECURE_RANDOM.nextInt(4);
        //凸/凹2
        int face2;
        //保证两个凸/凹不在同一位置
        do {
            face2 = SECURE_RANDOM.nextInt(4);
        } while (face1 == face2);
        //获取凸/凹起位置坐标
        int[] circle1 = getCircleCoords(face1, blockWidth, blockHeight, blockRadius);
        int[] circle2 = getCircleCoords(face2, blockWidth, blockHeight, blockRadius);
        //随机凸/凹类型
        int shape = getNonceByRange(0, 1);
        //圆的标准方程 (x-a)²+(y-b)²=r²,标识圆心（a,b）,半径为r的圆
        //计算需要的小图轮廓，用二维数组来表示，二维数组有两张值，0和1，其中0表示没有颜色，1有颜色
        for (int i = 0; i < blockWidth; i++) {
            for (int j = 0; j < blockHeight; j++) {
                data[i][j] = 0;
                //创建中间的方形区域
                if ((i >= blockRadius && i <= blockWidth - blockRadius && j >= blockRadius && j <= blockHeight - blockRadius)) {
                    data[i][j] = 1;
                }
                double d1 = Math.pow((i - Objects.requireNonNull(circle1)[0]), 2) + Math.pow((j - circle1[1]), 2);
                double d2 = Math.pow((i - Objects.requireNonNull(circle2)[0]), 2) + Math.pow((j - circle2[1]), 2);
                //创建两个凸/凹
                if (d1 <= po || d2 <= po) {
                    data[i][j] = shape;
                }
            }
        }
        return data;
    }

    /**
     * 根据朝向获取圆心坐标
     */
    protected static int[] getCircleCoords(int face, int blockWidth, int blockHeight, int blockRadius) throws SlidingVerificationCodeException {
        //上
        if (0 == face) {
            return new int[]{blockWidth / 2 - 1, blockRadius};
        }
        //左
        else if (1 == face) {
            return new int[]{blockRadius, blockHeight / 2 - 1};
        }
        //下
        else if (2 == face) {
            return new int[]{blockWidth / 2 - 1, blockHeight - blockRadius - 1};
        }
        //右
        else if (3 == face) {
            return new int[]{blockWidth - blockRadius - 1, blockHeight / 2 - 1};
        }
        logger.error("根据朝向获取圆心坐标失败");
        throw new SlidingVerificationCodeException("根据朝向获取圆心坐标失败");
    }

    /**
     * 在画布上添加阻塞块水印
     */
    protected static void addBlockWatermark(BufferedImage canvasImage, BufferedImage blockImage, int x, int y) {
        Graphics2D graphics2D = canvasImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
        graphics2D.drawImage(blockImage, x, y, null);
        graphics2D.dispose();
    }

    /**
     * BufferedImage转BASE64
     */
    protected static String toBase64(BufferedImage bufferedImage, String type) throws SlidingVerificationCodeException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, type, byteArrayOutputStream);
            String base64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return String.format("data:image/%s;base64,%s", type, base64);
        } catch (IOException e) {
            logger.error("图片资源转换BASE64失败:", e);
            throw new SlidingVerificationCodeException("图片资源转换BASE64失败");
        }
    }
}
