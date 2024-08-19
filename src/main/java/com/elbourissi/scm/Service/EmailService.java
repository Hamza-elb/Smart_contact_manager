package com.elbourissi.scm.Service;

public interface EmailService {


    void sendEmail(String to, String subject, String body);

    //
    void sendEmailWithHtml();

    //
    void sendEmailWithAttachment();
}
