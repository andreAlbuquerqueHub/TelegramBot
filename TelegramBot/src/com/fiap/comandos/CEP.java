package com.fiap.comandos;

import java.io.IOException;

import com.fiap.bean.EnderecoCompleto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CEP {

	/**
	 * Solicita o CEP
	 * 
	 * @param sendResponse
	 * @param bot
	 * @param update
	 * @param comando
	 * @return
	 */
	public static String solicitarCEP(SendResponse sendResponse, TelegramBot bot, Update update, String comando) {

		sendResponse = bot
				.execute(new SendMessage(update.message().chat().id(),
						"Digite um CEP para o endereço completo"));
		return comando;

	}
	
	/**
	 * Obtem o endereço completo a partir do CEP.
	 * 
	 * @param sendResponse
	 * @param bot
	 * @param update
	 * @return
	 */
	public static String obterEndereco(SendResponse sendResponse, TelegramBot bot, Update update) {
		EnderecoCompleto endereco = null;
		
		if ( update.message().text() != null ) {
			try {
				endereco = CEP.obterResposta(update.message().text());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String msg = endereco.toString();
		
		sendResponse = bot.execute(new SendMessage(update.message().chat().id(), msg));

		return msg;
	}
	
	/**
	 * Realiza o consumo da api VIACEP. 
	 * 
	 * @param cep
	 * 
	 * @return
	 */
	private static EnderecoCompleto obterResposta (String cep) throws IOException {
		
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://viacep.com.br/ws/" + CEP.limpaEntrada(cep) + "/json/")
				.get()
				.build();

		Response response = client.newCall(request).execute();

		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(response.body().string());
		
		return new EnderecoCompleto(json);
	}
	
	/**
	 * Limpa a entrada do CEP, removendo o caracter - e espaços.
	 *
	 * @return
	 */
	private static String limpaEntrada (String cep) {
		return cep.replaceAll("[^0-9]", "");
	}
	
}
