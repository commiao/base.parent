package base.arch.component.cloudStorage.qiniu;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {
	private static final Logger logger = LoggerFactory.getLogger(QiniuUtil.class);
	
	// 要上传的空间
	private static String bucketname = "commiao"; // 对应要上传到七牛上 你的那个路径（自己建文件夹 注意设置公开）  
	// 七牛访问前缀
	private static String url = "http://ohnj2yzyd.bkt.clouddn.com/";

	// 设置好账号的ACCESS_KEY和SECRET_KEY
	private static String accesskey = "aOn_aFElbDwp8UoymHSBAiY8e8oSi0DNuaDxDfjg";
	private static String secretkey = "oy3MzCLSWOUjYKGhwSznlOC4NnKIHF89ZNe2_2A5";

	public static String getUrl() {
        return url;
    }
    
    //第一种方式: 指定具体的要上传的zone
    //注：该具体指定的方式和以下自动识别的方式选择其一即可
    //要上传的空间(bucket)的存储区域为华东时
    // Zone z = Zone.zone0();
    //要上传的空间(bucket)的存储区域为华北时
    // Zone z = Zone.zone1();
    //要上传的空间(bucket)的存储区域为华南时
    // Zone z = Zone.zone2();
    
    //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
    static Zone zone = Zone.autoZone();
    
    static Configuration config = new Configuration(zone);
    // 创建上传对象
    UploadManager uploadManager = new UploadManager(config);
	
	private static Auth authInstance() {
        // 密钥配置
        Auth auth = Auth.create(accesskey, secretkey);
        return auth;
    }

    /**
     * 普通上传(简单上传，使用默认策略，只需要设置上传的空间名就可以了)
     * @return
     */
    private static String getUpToken() {
        return authInstance().uploadToken(bucketname);
    }

    /**
     * 覆盖上传
     * @param key
     * @return
     */
    private static String getUpTokenUpdate(String key) {
        return authInstance().uploadToken(bucketname, key);
    }
	

	// 普通上传
	/**
	 * @param filePath 上传文件的路径 "attached/image/20140304/1393901989351_1.jpg"
	 * @param key 上传到七牛后保存的文件名 "D:\\jshop\\attached\\image\\20140304\\1393901989351_1.jpg"
	 * @throws IOException
	 */
	public String uploadByPath(String filePath, String key, boolean isOverride) throws IOException {
		String upToken = isOverride?getUpTokenUpdate(key):getUpToken();
		try {
			// 调用put方法上传
			Response res = uploadManager.put(filePath, key, upToken);
			// 打印返回的信息
			return res.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	public String uploadByByte(byte[] data, String key, boolean isOverride) throws IOException {
		String upToken = isOverride?getUpTokenUpdate(key):getUpToken();
		try {
			// 调用put方法上传
			Response res = uploadManager.put(data, key, upToken);
			// 打印返回的信息
			return res.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	public String uploadByFile(File file, String key, boolean isOverride) throws IOException {
		String upToken = isOverride?getUpTokenUpdate(key):getUpToken();
		try {
			// 调用put方法上传
			Response res = uploadManager.put(file, key, upToken);
			// 打印返回的信息
			return res.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
		return null;
	}
	
	/**
     * 删除一个文件
     * @param key
     */
    public static void deleteImg(String key) {
        // 实例化一个 bucketManage
        BucketManager bucketManager = new BucketManager(authInstance(),config);
        try {
            bucketManager.delete(bucketname, key);
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println(r.toString());
        }
    }
	
	//批处理删除文件,大家可以自己写一个批处理更新,或者添加的,原理差不多
    public static boolean BatchDeleteImg(List<String> imgPaths) {
        BucketManager bucketManager = new BucketManager(authInstance(),config);
        BucketManager.Batch operations = new BucketManager.Batch();
        for (String url : imgPaths) {
            operations.delete(bucketname, url);
        }
        try {
            bucketManager.batch(operations);
            return true;
        } catch (QiniuException e) {
            Response r = e.response;
            logger.error("文件删除错误", r.toString());
            e.printStackTrace();
            return false;
        }
    }
	
	public static void main(String[] args) throws IOException {
		
//		// 上传到七牛后保存的文件名
//		String key = "attached/image/20171010/timg1.jpg";
//		// 上传文件的路径  
//		String filePath = "D:\\timg.jpg"; // 本地要上传文件路径   
//		
//		new QiniuUtil().uploadByPath(filePath,key,false);
		String hello = "Hello, World!";    
        logger.debug("DEBUG: " + hello);
        logger.info("INFO: " + hello);
        logger.warn("WARN: " + hello);
        logger.error("ERROR: " + hello);
	}

}
