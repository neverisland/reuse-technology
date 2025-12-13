package cn.yang.foundational.capability.file;

import cn.yang.foundational.capability.file.exception.FileHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件处理工具类
 *
 * @author : 未见清海
 */
@SuppressWarnings("unused")
public class LocalFileUtils {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileUtils.class);

    // 定义格式
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 获取文件保存相对路径
     *
     * @param fileName 文件名称
     * @return 文件保存相对路径
     */
    public static String getPath(String fileName) {
        // 获取当前时间
        String formattedDate = sdf.format(new Date());
        return File.separator + formattedDate + File.separator + fileName;
    }

    /**
     * 保存文件至本地
     *
     * @param fileUrl  文件网络路径
     * @param savePath 文件本地保存路径
     * @throws FileHandlerException 文件处理异常
     */
    public static void downloadFile(String fileUrl, String savePath) throws FileHandlerException {
        try {
            // 创建URL对象
            URL url = URI.create(fileUrl).toURL();
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");

            int responseCode = httpConn.getResponseCode();

            // 检查响应码是否是200 OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 新建文件夹
                mkdirFolder(savePath);
                // 读取输入流
                InputStream inputStream = httpConn.getInputStream();
                // 创建文件输出流
                FileOutputStream outputStream = new FileOutputStream(savePath);

                int bytesRead;
                byte[] buffer = new byte[4096];

                // 写入文件
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                // 关闭流
                outputStream.close();
                inputStream.close();

                logger.info("文件已下载并保存至:{}", savePath);
            } else {
                logger.warn("无法连接到文件 URL，响应码:{}", responseCode);
                throw new FileHandlerException("无法连接到文件 URL，响应码" + responseCode);
            }
            httpConn.disconnect();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new FileHandlerException("文件处理异常..");
        }
    }

    /**
     * 创建上一级别的文件夹
     *
     * @param path 文件路径
     */
    private static void mkdirFolder(String path) throws FileHandlerException {
        Path filePath = Paths.get(path);
        // 获取父目录
        Path parentDir = filePath.getParent();

        if (parentDir != null) {
            try {
                // 创建父目录及其所有不存在的上级目录
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                logger.error("创建上一级的文件夹失败", e);
                throw new FileHandlerException("创建上一级的文件夹失败");
            }
        }
    }
}