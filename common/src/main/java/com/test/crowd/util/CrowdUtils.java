package com.test.crowd.util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import com.aiyun.api.gateway.demo.util.HttpUtils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


public class CrowdUtils {
	/**
	 * 生成文件名
	 * @param originalFileName 原始文件名
	 * @return
	 */
	public static String generateFileName(String originalFileName) {

		// 截取扩展名部分
		String extensibleName = "";

		if(originalFileName.contains(".")) {
			extensibleName = originalFileName.substring(originalFileName.lastIndexOf("."));
		}

		return UUID.randomUUID().toString().replaceAll("-", "")+extensibleName;
	}

	/**
	 * 根据日期生成目录名称
	 * @param ossProjectParentFolder
	 * @return
	 */
	public static String generateFolderNameByDate(String ossProjectParentFolder) {

		return ossProjectParentFolder + "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	//上传head文件
	public static void uploadSingleFile(String endpoint, String accessKeyId, String accessKeySecret, String fileName,
										String folderName, String bucketName, InputStream inputStream) {
		try {

			// 创建OSSClient实例。
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

			// 存入对象的名称=目录名称+"/"+文件名
			String objectName = folderName + "/" + fileName;

			ossClient.putObject(bucketName, objectName, inputStream);

			// 关闭OSSClient。
			ossClient.shutdown();
		} catch (OSSException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
	}



	/**
	 * 根据不同前缀生成Redis中保存数据的key
	 *
	 * @param prefix
	 * @return
	 */
	public static String generateRedisKeyByPrefix(String prefix) {
		return prefix + UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成用户登录成功后使用的token
	 *
	 * @return
	 * @author 封捷
	 */
	public static String generateToken() {

		return CrowdConstant.REDIS_MEMBER_SING_TOKEN_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 验证集合是否有效
	 *
	 * @param c 待验证集合
	 * @return 验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static <E> boolean collectionEffectiveCheck(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}

	/**
	 * 验证字符串是否有效
	 *
	 * @param source 待验证字符串
	 * @return 验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static boolean strEffectiveCheck(String source) {
		return (source != null) && (source.length() > 0);
	}

	/**
	 * 生成随机验证码
	 *
	 * @param length 验证码长度
	 * @return 生成的验证码
	 * @throws RuntimeException 验证码长度必须大于0
	 * @author 封捷
	 */
	public static String randomCode(int length) {

		if (length <= 0) {
			throw new RuntimeException(CrowdConstant.MESSAGE_RANDOM_CODE_LENGTH_INVALID);
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {

			// 1.生成随机数
			double doubleRandom = Math.random();

			// 2.调整
			int integerRandom = (int) (doubleRandom * 10);

			// 3.拼接
			builder.append(integerRandom);
		}

		return builder.toString();
	}

	/**
	 * 发送验证码短信
	 *
	 * @param appcode    阿里云市场中调用API时识别身份的appCode
	 * @param randomCode 验证码值
	 * @param phoneNum   接收验证码短信的手机号
	 */
	public static void sendShortMessage(String appcode, String randomCode, String phoneNum) {
		String host = "http://dingxin.market.alicloudapi.com";
		String path = "/dx/sendSms";
		String method = "POST";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", phoneNum);
		querys.put("param", "code:" + randomCode);
		querys.put("tpl_id", "TP1711063");
		Map<String, String> bodys = new HashMap<String, String>();

		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
