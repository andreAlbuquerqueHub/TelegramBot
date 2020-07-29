package com.fiap.chatControl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Data;

@Data
public class ChatControl {

	private Long chatId;
	private String ultima_mensagem;
	private LocalDateTime data_mensagem;

	/**
	 * @param chatId
	 * @param ultima_mensagem
	 */
	public ChatControl(Long chatId, String ultima_mensagem) {
		super();
		this.chatId = chatId;
		this.ultima_mensagem = ultima_mensagem;
		this.data_mensagem = LocalDateTime.now();
	}

	/**
	 * Controla mensagens antigas, remove mensagens com mais de 40 segundos
	 * 
	 * @param listChatControl
	 * @return
	 */
	public static List<ChatControl> remove(List<ChatControl> listChatControl) {

		listChatControl.removeIf(
				item -> item.getData_mensagem().until(LocalDateTime.now(), ChronoUnit.SECONDS) > 40
		);

		return listChatControl;
	}

}
