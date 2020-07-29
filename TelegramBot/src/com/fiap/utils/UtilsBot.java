package com.fiap.utils;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

public class UtilsBot {
	
	
	/**
	 * Envia mensagem default de bem vindo
	 * 
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	public static void bemVindo(SendResponse sendResponse, TelegramBot bot, Update update) {
		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
				"Bem vindo " + update.message().from().firstName() + " " + update.message().from().lastName()));

		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
				"digite /ajuda para obter a lista de comandos conhecidos."));

	}

}
