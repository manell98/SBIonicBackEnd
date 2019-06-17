package br.com.manell.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.manell.cursomc.domain.Pedido;

public interface EmailService {

	void senOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
