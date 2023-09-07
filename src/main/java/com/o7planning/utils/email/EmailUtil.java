package com.o7planning.utils.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmailSetPassword(String email , String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setFrom("longlieuliny@gmail.com");
        mimeMessageHelper.setSubject("Verify OTP");
        mimeMessageHelper.setText("""
         <div> <h2>OTP :%s   </h2> </div>
         <div> <h4>"Do you want to change Password? , click link here :  </h4> </div>
        <div>
          <a href="http://localhost:8888/otp/set-password?email=%s" target="_blank">click link to verify</a>
        </div>
        """.formatted(otp,email), true);

        javaMailSender.send(mimeMessage);
    }
}
