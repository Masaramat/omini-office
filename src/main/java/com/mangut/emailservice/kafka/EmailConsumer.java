package com.mangut.emailservice.kafka;

import com.mangut.basedomain.dtos.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EmailConsumer {
    private static final String AES_ALGORITHM = "AES";
    private static final String SECRET_KEY = "7D+jDrNuzuTZQfix";
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
    private final JavaMailSender mailSender;

    public EmailConsumer(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(EmailEvent event) {

        LOGGER.info(String.format("Kafka event received in email service => %s", event.toString()));
        sendMessage(event);


    }

    private void sendMessage(EmailEvent event){
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(event.getReceiver().getEmail());
            mailMessage.setSubject("Welcome to Light OminiOffice");
            mailMessage.setText(
                    "Hello "
                            + event.getReceiver().getName()
                            + "\n\nWelcome to OmniOffice Pro. Please find your Initial login credentials "
                            + "\nemail: " + event.getReceiver().getEmail()
                            + "\nPassword: " + decrypt(event.getReceiver().getPassword())
                            + "\n\nPlease ensure you change your password."
                            + "\n\nWelcome on board"
            );

            System.out.println(mailMessage);

            mailSender.send(mailMessage);

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private static String decrypt(String encryptedPassword) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes);
    }
}
