package com.five.user.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Created by MyComputer on 2017/6/9.
 */
public class MailUtil implements Runnable {
    private String email;
    private String subject;
    private String content;
//    private String code; // 激活码

    public MailUtil(String email, String subject, String content) {
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public void init(String email, String subject, String content) {
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    public void run() {
        // 配置
        Properties prop=new Properties();
        // 设置邮件服务器主机名，这里是163
        prop.put("mail.host","smtp.163.com" );
        // 发送邮件协议名称
        prop.put("mail.transport.protocol", "smtp");
        // 是否认证
        prop.put("mail.smtp.auth", true);

        try {
            // SSL加密
            MailSSLSocketFactory sf = null;
            sf = new MailSSLSocketFactory();
            // 设置信任所有的主机
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            // 创建会话对象
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                // 认证信息，需要提供"用户账号","授权码"
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("18819253626@163.com", "123456abc");
                }
            });
            // 是否打印出debug信息
            session.setDebug(true);

            // 创建邮件
            Message message = new MimeMessage(session);
            // 邮件发送者
            message.setFrom(new InternetAddress("18819253626@163.com"));
            // 邮件接受者
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            // 邮件主题
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            // Transport.send(message);
            // 邮件发送
            Transport transport = session.getTransport();
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}