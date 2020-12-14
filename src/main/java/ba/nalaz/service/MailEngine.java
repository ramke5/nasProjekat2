package ba.nalaz.service;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import ba.nalaz.model.core.ProductConstants;

@Component
public class MailEngine {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailEngine.class);
	
    private MailSender mailSender;
    private VelocityEngine velocityEngine;
    private String defaultFrom;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }
    public MailSender getMailSender() {
        return mailSender;
    }
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    public void setFrom(String from) {
        this.defaultFrom = from;
    }

    @Async
    public void send(SimpleMailMessage msg) throws MailException {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            LOGGER.error(ex.getMessage());
            throw ex;
        }
    }

    @Async
    public void sendMessage(String[] recipients, String sender,
                            ClassPathResource resource, String bodyText,
                            String subject, String attachmentName)
            throws MessagingException {
        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipients);
        // use the default sending if no sender specified
        if (sender == null) {
            helper.setFrom(defaultFrom);
        } else {
            helper.setFrom(sender);
        }
        helper.setText(bodyText);
        helper.setSubject(subject);

        ((JavaMailSenderImpl) mailSender).send(message);
    }
    
    @Async
    public void sendMessage(final String recipient, final String sender, final String bodyText,
                            final String subject, final String attachmentName, final File attachment, 
                            final String templateName, final Map model)
            throws MessagingException {
        
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(recipient);
                message.setSubject(subject);
                message.addAttachment(attachmentName, attachment);
                if (sender == null) {
                    message.setFrom(defaultFrom);
                } else {
                    message.setFrom(sender);
                }
                final String fullTemplateName = "velocity/" + templateName;
                String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, fullTemplateName, "UTF-8", model);
                message.setText(body,true);
            }
        };

        ((JavaMailSenderImpl) mailSender).send(preparator);
    }

    @Async
    public void sendMessage(final String recipient, final String sender, final String subject, final String templateName, final Map model) throws MessagingException {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(recipient);
                message.setSubject(subject);
                if (sender == null) {
                    message.setFrom(defaultFrom);
                } else {
                    message.setFrom(sender);
                }
                final String fullTemplateName = "velocity/" + templateName;
                String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, fullTemplateName, "UTF-8", model);
                message.setText(body, true);
            }
        };
        ((JavaMailSenderImpl) mailSender).send(preparator);
    }
    
    @Async
    public void sendMessage(final String[] recipient, final String sender, final String subject, final String templateName, final Map model) throws MessagingException {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
                message.setTo(recipient);
                message.setSubject(subject);
                if (sender == null) {
                    message.setFrom(defaultFrom);
                } else {
                    message.setFrom(sender);
                }
                final String fullTemplateName = "velocity/" + templateName;
                String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, fullTemplateName, "UTF-8", model);
                message.setText(body, true);
            }
        };
        ((JavaMailSenderImpl) mailSender).send(preparator);
    }

    @Async
    public void sendMessage(SimpleMailMessage msg, String templateName, Map model) {
        String result = null;
        final String fullTemplateName = "velocity/" + templateName;
        try {
            result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, fullTemplateName, "UTF-8", model);
        } catch (VelocityException e) {
			LOGGER.error(ProductConstants.EXCEPTION_OCCURRED,e);
        }
        msg.setText(result);
        send(msg);
    }
}