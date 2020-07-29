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

	public static final String previsao = "/previsao";
	public static final String tempo = "/tempo";
	public static final String ajuda = "/ajuda";
	public static final String combNomes = "/love";
	public static final String cep = "/cep";
	
	/**
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	public static void listarComandos(SendResponse sendResponse ,  TelegramBot bot, Update update) {
		
		sendResponse = bot
				.execute(new SendMessage(update.message().chat().id(),
						"Digite um dos comandos: \n"
						+ "Listar comandos "    + ajuda + "\n"
						+ "Temperatura Atual "  + tempo + "\n"
						+ "Previsão para 8 dias " + previsao + "\n"	
						+ "Calculadora do amor " + combNomes + "\n"
						+ "Endereço completo pelo CEP " + cep + "\n"	
					    ));
		
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
