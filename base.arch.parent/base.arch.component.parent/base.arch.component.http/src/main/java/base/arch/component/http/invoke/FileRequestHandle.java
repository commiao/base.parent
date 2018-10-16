package base.arch.component.http.invoke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import base.arch.component.http.exception.HttpProcessException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.arch.component.http.client.HttpClientUtil;
import base.arch.component.http.common.HttpConfig;

/**
 * CreateDate:2016年12月15日上午9:30:35
 * @Description: 文件上传下载
 * @author:jannal
 * @version V1.0
 */
public class FileRequestHandle {
    private static final Logger logger = LoggerFactory.getLogger(FileRequestHandle.class);

    /**
     * @Description:测试流是否关闭，如果流关闭再写入即会报错
     * @author jannal
     * @param out
     * @return
     */
    protected static boolean testOutputStreamIsClose(OutputStream out) {
        try {
            out.write(1);
        } catch (IOException e) {
            return true;
        }
        return false;
    }

    /**
     * @Description:文件下载 <br/>
     * @author jannal
     * @param fileUrl
     *            文件url
     * @param targetDir
     *            保存的本地目录
     */
    public static void download(String fileUrl, String targetDir) {
        if (StringUtils.isEmpty(fileUrl) || StringUtils.isEmpty(targetDir)) {
            throw new RuntimeException("下载fileUrl或者存储目录targetDir不能为空");
        }
        File targetDirFile = new File(targetDir);
        if (!targetDirFile.isDirectory()) {
            throw new RuntimeException("targetDir必须要是目录");
        }

        if (!targetDirFile.exists()) {
            targetDirFile.mkdir();
        }

        String fileName = FilenameUtils.getName(fileUrl);
        String targetFilePath = targetDir + File.separator + fileName;
        File file = new File(targetFilePath);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            HttpClientUtil.down(HttpConfig.custom().url(fileUrl).out(fileOutputStream));
            if (fileOutputStream != null) {
                fileOutputStream.flush();
            }
            if (file.exists()) {
                logger.debug("{}文件下载成功,文件大小{},存放到{}", fileName, FileUtils.sizeOf(file), targetFilePath);
            } else {
                logger.error("{}文件下载失败,文件大小{},存放到{}", fileName, FileUtils.sizeOf(file), targetFilePath);
            }
        } catch (HttpProcessException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error("文件输出流fileOutputStream关闭发生异常");
                }
            }
            //必须加上，防止ThreadLocal线程泄露
            HttpConfig.outs.remove();
        }

    }
    
    /**
     * @author jannal
     * @param map       
     *             文件上传其他参数
     * @param fileInputName
     *              文件file的input 的name值
     * @param filePaths
     *              文件路径
     * @param encode
     *              文件编码 
     * 
     * @return 响应信息
     */
    public static String upload(HashMap<String, Object> map,String fileInputName,String[] filePaths,String encode){
        HttpConfig httpConfig =HttpConfig.custom();
        httpConfig.encoding(StringUtils.isEmpty(encode)?"UTF-8":encode);
        httpConfig.files(filePaths,fileInputName, true);
        httpConfig.map(map);
        return HttpClientUtil.upload(httpConfig);
    }
    
    
    
}
