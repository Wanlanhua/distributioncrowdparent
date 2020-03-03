package com.test.crowd.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */
@Configuration
public class AlipayConfig  {


//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id="2016102100729922";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQChiUm4gE67IX3FcZvZV6O8hln0JU8GbjCTIDLCufvbIx5bQ5EGB3I6UqDkZa0c6atbWZvx0vJkhvUISfzT4UFyb7RoO2D8/vni+rRrQIbdo3yPC6q4sNc8aZpcZEMYUKWoeeIgEnzcGKcbKBe7+n54aH2ma92BCzgcNttArH7RqiMKPvzH9hVQaQgFb/wddrZkDqPxLLcvV9bPdcRO3lykDtzm1mSpv3BwRTESDp/gAnQHXxUnnvYspwgAk8yMkAH4EVZuTy5ODtkY19S8EVectQdtEJY1ql2/lf4+RJSht0yhJg9uM8e+KuP/BNa5xvM+xzOW73XAFo+5pu7fkAAzAgMBAAECggEAMCy6cKzLTiGx2aWFHi915G6I+zf5Nmi8UdRjbxF6XCNAFWW0JYEo/t3Pn+32z82cThUOXfNUWMiq4Pr3zNS76P4JZqB2z1zXd2rfTbEjx4vPRPXsx0HuHtU7tNwt6l162iDnYDtDQ3ySsZWmZmhROWWoO26l06td44Q0pTgMZkiFUjX5cd+bAjeL9tf4f5+xxh/49kp3KzrljUZ7Sq2HuGNpxamRHiO8ZZsDzadSY3iLw3kP6ig4/EEj8CHDSkMBx5D1kfh4G8MKFVc+arjFv4y+bJ0YfkRaTSxQZwa8/pH6A1+XaYSQL37ffmseMM8m1xQZant/f/BphJYHyAwcqQKBgQDvz66o7UrsqxkbTkWZ2S8p3Ko3KuV5iMtk9LT0YDlPsIBGvUswy+k1WLOYO3HOK2KLziUEzsoukPboVHR6ICW/hJhq6pgrkLyMJyqFs5cmKF7e4uKySsz5E8g5i1CDBeK8du6ETX2s8IrJo0B56FL3rXj+eKnGqN7MDwKwHqerDQKBgQCscOPsYdNzH4kHjFKrM5ptgIuc5/lJ6g4DR3PuyfkEOq1guLacuD9f1yx2UP+ag23yx7+KUABXe7N5aogWUQlSJeV8wkiN7cGBIm+9ARMRDxUt+9zncvsk9//wbcFC7oFRXl19uiQydRhrz5xTwV0/yr2ti+qghR/Go8wY86aIPwKBgQCzCioLYsYadg9TjJd4KmcE6QOIFpWmrBO2duAWxzRio5LRvYfCkaq2Mlg7Yr4vv8JeQHTw5vaZ148Pm+YuO4wTNYqZ4DXl6LRsKt2bXp6NVXynCVtY43Gu0C8fjzx35dAHBwBCdJxMnnXv3ttHaMVwtunriuiuMevNC7PlC64AjQKBgG4NdklMfcjBXZDlq3T78BjmqzOukNjteZf6KDFD0ZszX6eU+2a16CxghU3Vj+uq6abCeKnrOOycVASvSr8DZPF6oiWnUOrTzGV/9tQnNWXzvE6mqWtFZKdJZ5yoT2lKElo94Wj2PVe+h6F21IxXog2udLOn0kwz6f26V+2swqG9AoGBAKQlF/4CuZz1s9z7qvSfVMauTSX2jcklqSrLLMyO+N4Ph9t75d8WZuLLrzjvSnSGO+YXtAVNFsswEZ42p5Zc1bVOOLL9dcOh5wWQ46VBy4WOlCVB82IXb/OiYKSj2z3XBj1NxTW37L9QcL3IXiZ6gmqJ0PgnXW9r+qZcouye6GEi";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    // 沙箱公钥地址：https://openhome.alipay.com/platform/appDaily.htm
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAk6tPQykI5f1Zo3MORG0My1JhWdBoIWqWlQPEDZszMZcdHBioKEiANZeIiC95B31xr9CUC7kIlbWBKSNs3W1YemrgbcmR+/SubLEHoB/iWDPmwwaId9ewUmQ7obsXkN98AspeFslaGZXLbrbSZ4PyENzrmDJ18buezDLVk3UGKPbbCLpPF3shelQhYwFFSq9C+jG37MtrO9fiNUvwRLes9Uz7esDbS8lRBmhKwAqg6GMgHdJzYcCvsxBAlULk+r7PGn0LIz/2FYTHMBbUee+vH8pWukMjm7R/ygB54OvlQBEANur6gWbdN0ZA8JK3rh00Pemw1TAQ3taRr4Ir9AIQWQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://ua7rnz.natappfree.cc/pay/notify.html";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://ua7rnz.natappfree.cc/pay/return.html";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	}



