package com.spc.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091300503210";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "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";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyoMWAzfQQEDd0wNMqHwD+GLbsRe/tkc1y8pn0gNbznaL8jQMJx/z6DrJpUYweIi7XM8BzsszHWaNHHLHSjY/o1P3+501rQr1+flraSyZRt2i4G4/cS4XVrkJWf+2J6sWF/Plh276nnL0n4sKDxcxAjuH8fkQLokLWqGEEJn2AI1w2IVaprw62J3e1VmHjvYNEI4FNrDKoVL2JG/WcGlK3ZBlhjBCyIQQAYjHNf/ZJxVifzxO5yXQof5Wou4QGzsbLGwDCTrdtKRlsb5uaD/T/0r4HXxU/cS3mA3Fmi49s6KAFTKEZPpCRq3UWIX8JjXVySyIJLv6T/eqrvD6IKURawIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://127.0.0.1:80/notifyUrl";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://127.0.0.1:80/returnUrl";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关, 沙箱网关地址https://openapi.alipaydev.com/gateway.do
	//public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关 这个不用管
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

