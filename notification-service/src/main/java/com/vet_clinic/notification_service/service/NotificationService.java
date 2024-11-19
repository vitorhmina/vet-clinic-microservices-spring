package com.vet_clinic.notification_service.service;

import com.vet_clinic.notification_service.event.AppointmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private static final String APPOINTMENT_CREATED_TOPIC = "APPOINTMENT_CREATED";

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = APPOINTMENT_CREATED_TOPIC)
    public void listen(AppointmentCreatedEvent appointmentCreatedEvent){
        log.info("Got Message from appointment-created topic {}", appointmentCreatedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springvetclinic@email.com");
            messageHelper.setTo(appointmentCreatedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Appointment with id %s is scheduled successfully", appointmentCreatedEvent.getAppointmentId()));
            messageHelper.setText(String.format("""
                            Hi,

                            Your appointment with id %s is now scheduled successfully.
                            
                            Best Regards
                            Spring Vet Clinic
                            """,
                    appointmentCreatedEvent.getAppointmentId()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Appointment Notification email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springvetclinic@email.com", e);
        }

    }
}
