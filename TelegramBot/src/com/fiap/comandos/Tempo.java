package com.fiap.comandos;

import org.glassfish.jersey.client.ClientConfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardHide;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

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
			
//			Enviar mensagem para esconder o teclado
//			Necessario efetuar esse comando para exibir o botao de localização
			sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "")
					.replyMarkup(new ReplyKeyboardHide() ) );
			
			sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Compartilhar localização?")
					.replyMarkup(new ReplyKeyboardMarkup(
							new KeyboardButton[] { new KeyboardButton("localização").requestLocation(true) })
									.resizeKeyboard(true).selective(true).oneTimeKeyboard(true)));
		} else {
			sendResponse = bot.execute(new SendMessage(update.message().chat().id(), mostrarPrevisao(update)));
		}

	}

	/**
	 * Busca temperatura atual atraves de uma API os dados de longitude e latitude
	 * devem ser compartilhados pelo usuario
	 * 
	 * @param update
	 * @return
	 */
	private static String mostrarPrevisao(Update update) {

//		Chama API para buscar a temperatura atual de acordo com as coordenadas
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target("https://api.openweathermap.org/data/2.5/onecall")
				.queryParam("lat", update.message().location().latitude())
				.queryParam("lon", update.message().location().longitude()).queryParam("units", "metric")
				.queryParam("appid", "fa74962721a7d31feb9acf98ff23d2b6");

		String responseString = target.request(MediaType.APPLICATION_JSON).get(String.class);

//		Verifica retorno da chamada da API
		if (responseString != null) {
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(responseString);

			String localizacao = json.get("timezone").toString().split("/")[1];
			String temperatura = json.getAsJsonObject("current").get("temp").toString();

//			Formata nome da cidade
			localizacao = localizacao.replace("_", " ");
			localizacao = localizacao.replace("\"", "");

			return "Temperatura em " + localizacao +
				   " de  " + temperatura + "°C";
		} else {

			return "Não foi possivel determinar a temperatura";

		}

	}

}
