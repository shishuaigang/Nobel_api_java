package com.nobel.apitest.common;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

/**
 * 发送带html报告的附件
 */
public class SendMail {

    //发件人邮箱和密码
    private static String EmailAccount = "sgshi@in-road.com";
    private static String EmailPassword = "Shishuaigang123!";
    //private static String EmailPassword = "TZW123zxcvbnm";
    //smtp服务器
    private static String EmailSMTPHost = "smtp.exmail.qq.com";
    //单个收件人邮箱
    //private static String[] Receiver = {"sgshi@in-road.com"};
    private String folder_name;
    private String form;

    public SendMail(String t, String test_type) {
        this.folder_name = t;
        this.form = test_type;
    }

    public void sendMail() throws Exception {
        // 收件人列表
        String tt1 = "sgshi@in-road.com";
        String tt2 = "jjxia@in-road.com";
        String tt3 = "bhu@in-road.com";
        String tt4 = "jmyao@in-road.com";
        String tt5 = "hchen@in-road.com";
        InternetAddress to1 = new InternetAddress(tt1, "施帅钢", "UTF-8");
        InternetAddress to2 = new InternetAddress(tt2, "夏建俊", "UTF-8");
        InternetAddress to3 = new InternetAddress(tt3, "胡博", "UTF-8");
        InternetAddress to4 = new InternetAddress(tt4, "姚建明", "UTF-8");
        InternetAddress to5 = new InternetAddress(tt5, "陈浩", "UTF-8");
        InternetAddress[] toall = new InternetAddress[]{to1, to2, to3, to4, to5};


        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", EmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(form, session, EmailAccount, toall, folder_name);

        // 也可以保持到本地查看
        // message.writeTo(file_out_put_stream);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器
        //    这里认证的邮箱必须与 message 中的发件人邮箱一致，否则报错
        transport.connect(EmailAccount, EmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();

    }


    private static MimeMessage createMimeMessage(String form, Session session, String sendMail, InternetAddress[] receiveMail, String t_snap) throws Exception {
        /*创建一封邮件*/
        MimeMessage message = new MimeMessage(session);

        /*设置发件人(邮箱地址，发件人昵称，昵称字符集)*/
        message.setFrom(new InternetAddress(sendMail, "施帅钢", "UTF-8"));

        /*设置单个收件人(邮箱地址，收件人昵称，昵称字符集)*/
        //message.setRecipients(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "工之道研发TEAM", "UTF-8"));
        /*设置收件人列表*/
        message.setRecipients(MimeMessage.RecipientType.TO, receiveMail);

        /*邮件主题*/
        message.setSubject("Nobel API " + form + " Test Report", "UTF-8");

        /*添加邮件正文（可以使用html标签）*/
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("Hi all,<br>&nbsp&nbspThis is the test report for nobel api " + form + ".",
                "text/html;charset=UTF-8");

        /*添加附件*/
        MimeBodyPart attach = new MimeBodyPart();

        DataHandler dh;
        if (System.getProperty("os.name").contains("Windows")) {
            dh = new DataHandler(new FileDataSource("C:\\testResults\\Nobel\\" + form + "\\" + t_snap + "\\result.html"));
        } else {
            dh = new DataHandler(new FileDataSource("/Users/shishuaigang/testResults/Nobel/" + form + "/" + t_snap + "/result.html"));
        }
        attach.setDataHandler(dh);                                             // 将附件数据添加到“节点”
        attach.setFileName(MimeUtility.encodeText(dh.getName()));

        /*邮件添加内容*/
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(attach);
        mm.addBodyPart(text);

        message.setContent(mm);

        /*设置发件时间*/
        message.setSentDate(new Date());

        /*保存设置*/
        message.saveChanges();

        return message;
    }
}