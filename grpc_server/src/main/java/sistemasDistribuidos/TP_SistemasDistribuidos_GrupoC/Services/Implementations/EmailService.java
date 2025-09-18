package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String nombreUsuario, String emailDestino, String clave) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(emailDestino);
        helper.setSubject("Bienvenido a Empuje Comunitario");

        String htmlMsg = "<h1>Hola " + nombreUsuario + ", " +
                "¡Gracias por registrarte en Empuje Comunitario! 🏡</h1>" +
                "<h3>Estamos muy contentos de tenerte con nosotros.</h3>" +
                "<h3>🔑 Tu cuenta ya está activa.</h3>" +
                "<h3>👉 Está es tu clave: " + clave + ". No la compartas con nadie.\n\n</h3>" +
                "<h4>¡Bienvenido a la comunidad de Empuje Comunitario!</h4>";
        helper.setText(htmlMsg, true);
        javaMailSender.send(mimeMessage);
    }
}
