package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.OrdemDeServico;
import com.projetobetha.dev.dto.OrdemDeServicoDTO;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.recipient}")
    private String recipient;
    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendConfirmationEmail(OrdemDeServico obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromOrdemDeServico(obj);
        sendEmail(sm);
    }

    @Override
    public void sendConfirmationHtmlEmail(OrdemDeServico obj, OrdemDeServicoDTO objDto) {
        try {
            MimeMessage mm = prepareMimeMessageFromOrdemDeServico(obj, objDto);
            sendHtmlEmail(mm);
        } catch (MessagingException ex) {
            sendConfirmationEmail(obj);
        }
    }

    @Override
    public void sendConclusionHtmlEmail(OrdemDeServico obj) {
        try {
            MimeMessage mm = prepareMimeMessageConclusionFromOrdemDeServico(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException ex) {
            sendConfirmationEmail(obj);
        }
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrdemDeServico(OrdemDeServico obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(recipient);  //Later it will be obj.getCliente().getEmail()
        sm.setFrom(sender);
        sm.setSubject("Confirme sua Ordem de Serviço! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplateOrdemDeServico(OrdemDeServico obj, OrdemDeServicoDTO objDto) {
        Context context = new Context();

        context.setVariable("ordem", obj);
        context.setVariable("ordemDto", objDto);

        context.setVariable("url", "http://localhost:8080/ordemdeservicos/ordemaprovada/" + obj.getId());
        context.setVariable("url2", "http://localhost:8080/ordemdeservicos/ordemrecusada/" + obj.getId());

        System.out.println(templateEngine.process("email/confirmarOrdemDeServico", context));
        return templateEngine.process("email/confirmarOrdemDeServico", context);
    }

    protected MimeMessage prepareMimeMessageFromOrdemDeServico(OrdemDeServico obj, OrdemDeServicoDTO objDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom("ServiçosDeReparosDeMentirinha@email.com");
        mmh.setSubject("Confirme sua Ordem de Serviço! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplateOrdemDeServico(obj, objDto), true);
        return mimeMessage;
    }

    protected String htmlConclusionOrderFromTemplateOrdemDeServico(OrdemDeServico obj) {
        Context context = new Context();
        context.setVariable("ordem", obj);
        System.out.println(templateEngine.process("email/finalizadaOrdemDeServico", context));
        return templateEngine.process("email/finalizadaOrdemDeServico", context);
    }

    protected MimeMessage prepareMimeMessageConclusionFromOrdemDeServico(OrdemDeServico obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(recipient); //Later it will be obj.getCliente().getEmail()
        mmh.setFrom(sender);
        mmh.setSubject("Confirme sua Ordem de Serviço! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlConclusionOrderFromTemplateOrdemDeServico(obj), true);
        return mimeMessage;
    }
}
