package com.fiap.chatControl;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatControl {

	@Getter
	@Setter
	private Long chatId;
	@Getter
	@Setter
	private String ultima_mensagem;
	@Getter
	@Setter
	private LocalDate data_mensagem;

	public ChatControl(Long chatId, String ultima_mensagem) {
		super();
		this.chatId = chatId;
		this.ultima_mensagem = ultima_mensagem;
		this.data_mensagem = LocalDate.now();
	}

}
