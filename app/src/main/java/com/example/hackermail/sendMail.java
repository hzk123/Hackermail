package com.example.hackermail;

public class sendMail {

    private String mail_account;
    private String mail_sendto;

    private String port;
    private String Context;
    sendMail(String _mail_account , String _mail_sendto , String _port , String _Context)
    {
        mail_account = _mail_account;
        mail_sendto = _mail_sendto;
        port = _port;
        Context = _Context;
    };

    public void send()
    {
        // send mail service Using POP3 or balablabla
    }
}
