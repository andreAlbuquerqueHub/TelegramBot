package com.fiap.chatControl;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChatControl {
	
	@Getter@Setter
	private Long chatId;
	@Getter@Setter
	private String ultima_mensagem;
	@Getter@Setter
	private LocalDate data_mensagem;
	
	
}
