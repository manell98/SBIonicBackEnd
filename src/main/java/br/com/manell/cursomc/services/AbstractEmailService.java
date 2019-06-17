package br.com.manell.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.manell.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;

	@Override
	public void senOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());  // destinatario
		sm.setFrom(sender);						// remetente
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); // assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); // data
		sm.setText(obj.toString()); // texto do email
		return sm;
	}
}
