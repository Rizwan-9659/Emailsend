import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Task22{

    public static void main(String[] args) {
        String pdfPath = "D://netflix/Netflix_Styled_Output.pdf";

        try {
            // 1. Create PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();

            // Fonts
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.RED);
            Font underlineFont = new Font(Font.FontFamily.HELVETICA, 16, Font.UNDERLINE, BaseColor.GREEN);
            Font italicFont = new Font(Font.FontFamily.HELVETICA, 16, Font.ITALIC, BaseColor.BLUE);

            // Add text
            document.add(new Paragraph("Netflix", boldFont));
            document.add(new Paragraph("Dear Subscribers, Enjoy the videos!", underlineFont));
            document.add(new Paragraph("Have a great time watching!", italicFont));

            document.close();
            System.out.println("✅ PDF created successfully at: " + pdfPath);

            // 2. Send email with PDF attachment
            sendEmailWithAttachment(pdfPath);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendEmailWithAttachment(String filePath) {
        // Sender and receiver details
        String host = "smtp.gmail.com";  // Gmail SMTP
        String port = "587";
        String user = "rizwanmohamed042@gmail.com";   // replace with your email
        String pass = "mrhe keas tnht pkaj";      // use App Password (not normal password)
        String to = "shivame2011@gmail.com"; // recipient

        // Setup mail properties
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Netflix PDF Attachment");

            // Create message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hi,\n\nPlease find the attached Netflix PDF.\n\nRegards,\nNetflix Team");

            // Attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            // Combine parts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Add content to message
            message.setContent(multipart);

            // Send mail
            Transport.send(message);
            System.out.println("✅ Email sent successfully with PDF attachment.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
