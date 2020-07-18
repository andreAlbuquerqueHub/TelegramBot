package com.fiap.comandos;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * Classe responsavel por tratar a lista de comandos
 *
 */
public class Ajuda {

	/**
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	public static void listarComandos(SendResponse sendResponse ,  TelegramBot bot, Update update) {
		
		sendResponse = bot
				.execute(new SendMessage(update.message().chat().id(),
						"Digite um dos comandos: \n"
						+ "Temperatura Atual /tempo \n"
						+ "Previsão 5 dias  /previsao \n"		
					    + "Listar comandos /ajuda"));
		
	}
	
	/**
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	public static void comandoInvalido(SendResponse sendResponse ,  TelegramBot bot, Update update) {
		
		sendResponse = bot
				.execute(new SendMessage(update.message().chat().id(),
						"Comando inválido \n"+
						"digite /ajuda para obter a lista de comandos conhecidos." ));
		
	}
	
}
