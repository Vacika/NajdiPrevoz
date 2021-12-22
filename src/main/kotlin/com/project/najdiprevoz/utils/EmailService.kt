package com.project.najdiprevoz.utils

import com.project.najdiprevoz.domain.Mail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.nio.charset.StandardCharsets
import javax.mail.internet.MimeMessage


@Service
class EmailService(private val emailSender: JavaMailSender,
                   private val templateEngine: SpringTemplateEngine) {
    val logger: Logger = LoggerFactory.getLogger(EmailService::class.java)

    fun sendForgetPasswordMail(mail: Mail) {
        try {
            logger.debug("[EMAIL SERVICE] Sending [Forget Password] Mail to ${mail.to}")
            sendMail(mail)
        } catch (e: Exception) {
            logger.error("[EMAIL SERVICE] Mail for ${mail.to} was not sent!")
            throw RuntimeException(e)
        }
    }

    fun sendUserActivationMail(mail: Mail) {
        try {
            logger.debug("[EMAIL SERVICE] Sending [Activate User] Mail to ${mail.to}")
            sendMail(mail)
        } catch (e: Exception) {
            logger.error("[EMAIL SERVICE] Mail for ${mail.to} was not sent!")
            throw RuntimeException(e)
        }
    }


    private fun sendMail(mail: Mail) {
        val message: MimeMessage = emailSender.createMimeMessage()
        val helper = MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name())
        val context = Context()
        context.setVariables(mail.model)
        val html: String = templateEngine.process(mail.template, context)
        helper.setTo(mail.to!!)
        helper.setText(html, true)
        helper.setSubject(mail.subject!!)
        helper.setFrom(mail.from!!)
        emailSender.send(message)
        logger.debug("[EMAIL SERVICE] Mail sent successfully!")
    }

}
