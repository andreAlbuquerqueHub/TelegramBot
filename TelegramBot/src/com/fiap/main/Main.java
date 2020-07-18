package com.fiap.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fiap.chatControl.ChatControl;
import com.fiap.comandos.Ajuda;
import com.fiap.comandos.Tempo;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Main {

	// Criação do objeto bot com as informações de acesso
	// link para chat do bot criado https://t.me/FIAP36SCJBot
	private static TelegramBot bot = new TelegramBot("836335411:AAFAvWdyjL0c6_Su2ASzyQCksZMByVOiPek");
	static String mensagem;
	static List<ChatControl> listChatControl = new ArrayList<ChatControl>();
	static ChatControl chatControl = new ChatControl();


	public static void main(String[] args) {
		int index;
		List<ChatControl> filter;
		
		// objeto responsável por receber as mensagens
		GetUpdatesResponse updatesResponse;
		// objeto responsável por gerenciar o envio de respostas
		SendResponse sendResponse = null;
		// objeto responsável por gerenciar o envio de ações do chat
		BaseResponse baseResponse;

		// controle de off-set, isto é, a partir deste ID será lido as mensagens
		// pendentes na fila
		int m = 0;

		// loop infinito pode ser alterado por algum timer de intervalo curto
		while (true) {

			// executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial)
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// lista de mensagens
			List<Update> updates = updatesResponse.updates();

			if (updates == null) {
				continue;
			}

			// análise de cada ação da mensagem
			for (Update update : updates) {

				// atualiza��o do off-set
				m = update.updateId() + 1;

				mensagem = update.message().text();

//				Controla mensagens enviadas do usuario
//				Valida se o registro j� existe
				filter = listChatControl.stream().filter(item -> item.getChatId().equals(update.message().chat().id()))
						.collect(Collectors.toList());

				if (filter.isEmpty() == false) {
					chatControl = filter.get(0);
					index = listChatControl.indexOf(chatControl);
					System.out.println(chatControl.getUltima_mensagem());
					chatControl.setData_mensagem(LocalDate.now());
					chatControl.setUltima_mensagem(mensagem);
					listChatControl.set(index, chatControl);
				} else {
					chatControl.setChatId(update.message().chat().id());
					chatControl.setData_mensagem(LocalDate.now());
					chatControl.setUltima_mensagem(mensagem);
					listChatControl.add(chatControl);
				}

				// envio de "Escrevendo" antes de enviar a resposta
				baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

				if (mensagem != null) {
					verificaMensagem(mensagem, sendResponse, bot, update);

				} else {

//					Caso o usuario compartilhe a localizacao o retorno ser� a temperatura do local
					if (update.message().location() == null) {
						bemVindo(sendResponse, bot, update);
					} else {
						Tempo.solicitarLocalizacao(sendResponse, bot, update);
					}

				}

			}

		}

	}

	private static void bemVindo(SendResponse sendResponse, TelegramBot bot, Update update) {
		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
				"Bem vindo " + update.message().from().firstName() + " " + update.message().from().lastName()));

		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
				"digite /ajuda para obter a lista de comandos conhecidos."));

	}

	private static void verificaMensagem(String mensagem, SendResponse sendResponse, TelegramBot bot, Update update) {
		final String regex = "^\\/+";
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

//		regular expressions para identificar um comando
		if (pattern.matcher(mensagem).find()) {

//			Verifca o comando informado
			switch (mensagem) {

			case "/ajuda":
				Ajuda.listarComandos(sendResponse, bot, update);
				break;

			case "/tempo":
				Tempo.solicitarLocalizacao(sendResponse, bot, update);
				break;

			case "/previsao":
				Tempo.solicitarLocalizacao(sendResponse, bot, update);
				break;

			default:
				Ajuda.comandoInvalido(sendResponse, bot, update);
				break;
			}

		} else {
			bemVindo(sendResponse, bot, update);
		}

	}

}