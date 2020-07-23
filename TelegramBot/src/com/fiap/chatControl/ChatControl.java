package com.fiap.chatControl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
