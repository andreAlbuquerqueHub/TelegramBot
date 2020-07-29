package com.fiap.comandos;

import java.io.IOException;

import com.fiap.bean.Combinacao;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Love {

	/**
	 * Calcula a porcentagem de combinação de dois nomes atraves de uma api 
	 * @param seuNome
	 * @param outroNome
	 * @return
	 * @throws IOException
	 */
	public static Combinacao compararNomes(String seuNome, String outroNome) throws IOException{

		Combinacao loveDto = new Combinacao();

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("https://love-calculator.p.rapidapi.com/getPercentage?fname="+seuNome+"&sname="+outroNome)
				.get()
				.addHeader("x-rapidapi-host", "love-calculator.p.rapidapi.com")
				.addHeader("x-rapidapi-key", "d6eb7ca8ccmshc5137be31f70201p1e09bbjsn922c15636cf7")
				.build();

		Response response = client.newCall(request).execute();

		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(response.body().string());


		loveDto.setSname(json.get("fname").toString());
		loveDto.setFname(json.get("sname").toString());
		loveDto.setPercentage(json.get("percentage").toString());

		return loveDto;

	}


	/**
	 * Solicita os nomes ao bot
	 * @param sendResponse
	 * @param bot
	 * @param update
	 * @param comando
	 * @return
	 */
	public static String solicitarNomes(SendResponse sendResponse ,  TelegramBot bot, Update update, String comando) {

		sendResponse = bot
				.execute(new SendMessage(update.message().chat().id(),
						"Digite o nome da pessoa amada"));
		return comando;

	}

	/**
	 * Compara os nomes e retorna a porcentagem
	 * @param sendResponse
	 * @param bot
	 * @param update
	 * @return
	 */
	public static String obterPorcentagem(SendResponse sendResponse ,  TelegramBot bot, Update update) {
		Combinacao combinacaoDto = null;
		if(update.message().text() != null) {
			try {
				combinacaoDto = compararNomes(update.message().from().firstName(), update.message().text());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String msg = update.message().from().firstName() + " e " + update.message().text()
				+ " tem uma combinação de " + combinacaoDto.getPercentage()+"%";

		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),msg));

		return msg;
	}


}
