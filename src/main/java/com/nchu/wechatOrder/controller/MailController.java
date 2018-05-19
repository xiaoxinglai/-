package com.nchu.wechatOrder.controller;

import com.oracle.tools.packager.Log;
import jxl.read.biff.BiffException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Controller
public class MailController {

    private static void   sendEmail(){
        Properties props = System.getProperties();
        // 创建信件服务器
        props.put("mail.smtp.host", "smtp.qq.com");//主机host，跟邮件发送者必须一致
        props.put("mail.smtp.auth", "true"); // 通过验证
        props.put("mail.smtp.port", "465");//加密服务端口465
        props.put("mail.transport.protocol", "smtp");//方式为smtp
        props.put("mail.smtp.ssl.enable", "true");//加密


        //创建一个会话把属性properties放进去：
        Session session = Session.getDefaultInstance(props, null);

        //创建一个消息把会话放进去：
       // MimeMessage msg = new MimeMessage(session);
        MimeMessage msg=new MimeMessage(session);
        //然后把昵称转编码加上发送邮箱，设置from：
       try {
           msg.setFrom(new InternetAddress(MimeUtility.encodeText("发送人：") + "<291826425@qq.com>"));

      //新建接受方的地址to：
           InternetAddress address1 = new InternetAddress();
           address1.setAddress("xingchen@maihaoche.com");
           InternetAddress[] address = new InternetAddress[]{address1};



    //将地址添加到指定的接受类型：
          msg.addRecipients(Message.RecipientType.TO,address);

           //设置邮件主题subject：
           msg.setSubject("java测试邮件");

           //设置消息头的日期，更新所有并保存：
           msg.setText("点到系统邮件发送");
           msg.setSentDate(new Date());
           msg.saveChanges();

           //最后，连接并发送，关闭连接：
           Transport transport = session.getTransport("smtp");
           transport.connect("291826425@qq.com", "bunwamujdemnbgef");
           transport.sendMessage(msg,msg.getRecipients(Message.RecipientType.TO));
           transport.close();
           System.out.println("发送成功");

       }catch (Exception e){
           Log.info(e.getMessage());
       }


    };



    private  void send(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("291826425@qq.com");
        javaMailSender.setPassword("bunwamujdemnbgef");
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        javaMailSender.setJavaMailProperties(properties);
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
            helper.setFrom("291826425@qq.com");// 设置发件人
            helper.setTo("xingchen@maihaoche.com");// 设置收件人
//            helper.setCc(cc);// 设置抄送
            helper.setSubject("java测试邮件发送");// 设置主题
            helper.setText("测试邮件发送");// 邮件体
            javaMailSender.send(mailMessage);// 发送邮件
            Log.info("邮件发送成功...");
        } catch (Exception e) {
            Log.info("邮件发送发生异常:" + e.getMessage());

        }



    }



    public static void main(String args[]) throws BiffException, IOException {
//        String[] count={"xingchen@maihaoche.com"};
//        sendSimpleMail(count,"测试java发送邮件","java发送邮件测试");

        //sendEmail();
        MailController mailController=new MailController();

        mailController.send();


    }

}
