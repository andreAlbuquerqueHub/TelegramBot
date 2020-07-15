package com.fiap.comandos;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardHide;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageReplyMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * Classe responsavel por tratar solitações de previsão do tempo
 */
public class Tempo {

	/**
	 * Solicita a localização em tempo real do usuario
	 * 
	 * 
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	public static void solicitarLocalizacao(SendResponse sendResponse, TelegramBot bot, Update update) {
		
	
		if (update.message().location() == null) {
			
			sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Compartilhar localização?")
					.replyMarkup(new ReplyKeyboardMarkup(
							new KeyboardButton[] { new KeyboardButton("localização").requestLocation(true) })
							.resizeKeyboard(true).selective(true).oneTimeKeyboard(true)));
		} else {
			sendResponse = bot.execute(new SendMessage(update.message().chat().id(), mostrarPrevisao(update)));
		}

	}

	private static String mostrarPrevisao(Update update) {
		// TODO Auto-generated method stub

		return "Latitude: " + update.message().location().latitude() + "\n" + "Longitude: "
				+ update.message().location().longitude();
	}

}
