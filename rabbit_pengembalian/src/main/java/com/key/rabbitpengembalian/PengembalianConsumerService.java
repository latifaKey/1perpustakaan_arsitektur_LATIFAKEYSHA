package com.key.rabbitpengembalian;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.key.rabbitpengembalian.vo.Anggota;
import com.key.rabbitpengembalian.vo.Pengembalian;
import com.key.rabbitpengembalian.vo.ResponseTemplate;

@Service
public class PengembalianConsumerService {

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private RestTemplate restTemplate;

  @Value("${app.mail.from}")
  private String from;

  @Value("${app.pengembalian.service.url:http://localhost:8085}")
  private String pengembalianServiceUrl;

  @RabbitListener(queues = "${app.rabbitmq-pengembalian.queue}")
  @Transactional
  public void receiveOrder(@Payload Pengembalian pengembalian){
    System.out.println("Sending receive to email: " + pengembalian.getId());

    try{
      ResponseTemplate[] response = restTemplate.getForObject(pengembalianServiceUrl + "/api/pengembalian/" + pengembalian.getId() + "/detail", ResponseTemplate[].class);
      ResponseTemplate dataPengembalian = response[0];
      Anggota anggota = dataPengembalian.getAnggota();
      String email = anggota.getEmail();

      System.out.println("Sending notification to email:" + email);
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setFrom(from);
      mailMessage.setTo(email);
      mailMessage.setText(dataPengembalian.sendMailMessage());
      mailMessage.setSubject("Konfirmasi Pengembalian Buku Berhasil");
      javaMailSender.send(mailMessage);
    }catch (Exception e){
      System.out.println(e.toString());
    }
  }
}
