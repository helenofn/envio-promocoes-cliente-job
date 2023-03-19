package br.com.hfn.enviopromocoescliente.processor;

import java.text.NumberFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import br.com.hfn.enviopromocoescliente.dominio.InteresseProdutoCliente;

@Component
public class ProcessarEmailProdutoClienteProcessor implements ItemProcessor<InteresseProdutoCliente, SimpleMailMessage>{

	@Override
	public SimpleMailMessage process(InteresseProdutoCliente item) throws Exception {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("xpto@no-reply.com");
		mail.setTo(item.getCliente().getEmail());
		mail.setSubject("Promoção imperdível!!");
		mail.setText(gerarTextoFormatado(item));
		
		Thread.sleep(2000);//Para o servidor de e-mail não barrar
		
		return mail;
	}

	private String gerarTextoFormatado(InteresseProdutoCliente item) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Olá, %s!\n\n", item.getCliente().getNome()));
		sb.append("Esta promosção pode ser do seu interesse: \n\n");
		sb.append(String.format("%s - %s\n\n", item.getProduto().getNome(),item.getProduto().getDescricao()));
		sb.append(String.format("Por apenas: %s!", NumberFormat.getCurrencyInstance().format(item.getProduto().getPreco())));
		return sb.toString();
	}

}
