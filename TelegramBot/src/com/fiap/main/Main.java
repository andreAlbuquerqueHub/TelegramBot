package com.fiap.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fiap.chatControl.ChatControl;
import com.fiap.comandos.Ajuda;
import com.fiap.comandos.CEP;
import com.fiap.comandos.Love;
import com.fiap.comandos.Tempo;
import com.fiap.utils.UtilsBot;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Main {

	// Criação do objeto bot com as informações de acesso
	// link para chat do bot criado https://t.me/FIAP36SCJBot
	private static final TelegramBot bot = new TelegramBot("1353188459:AAGIfGmq7m_RR7pBceGQ7Yr5leNiC1HzMJY");
	static String mensagem;
	static Long chatId;
	static List<ChatControl> listChatControl = new ArrayList<ChatControl>();
	static ChatControl chatControl;

	public static void main(String[] args) {

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
			
			
//			Controla mensagens antigas, remove mensagens com mais de 40 segundos
			if(listChatControl.isEmpty() == false) {
				listChatControl = ChatControl.remove(listChatControl);
			}
			

			if (updates == null) {
				continue;
			}
			

			// análise de cada ação da mensagem
			for (Update update : updates) {

				// atualiza��o do off-set
				m = update.updateId() + 1;

				mensagem = update.message().text();
				chatId = update.message().chat().id();

				// envio de "Escrevendo" antes de enviar a resposta
				baseResponse = bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));

				verificaMensagem(mensagem, sendResponse, bot, update);

			}

		}

	}

	private static void verificaUltimaMensagem(SendResponse sendResponse, TelegramBot bot2, Update update) {
		// TODO Auto-generated method stub

		// Seleciona na lista a ultima mensagem para o ID em processamento
		int index = listChatControl.indexOf(chatControl);
		String ultimaMensagem = chatControl.getUltima_mensagem();

		if (ultimaMensagem == null) {
			UtilsBot.bemVindo(sendResponse, bot, update);
			return;
		}

		switch (ultimaMensagem) {

			case Ajuda.tempo:
				mensagem = Tempo.solicitarLocalizacao(sendResponse, bot, update, ultimaMensagem);
				break;
	
			case Ajuda.previsao:
				mensagem = Tempo.solicitarLocalizacao(sendResponse, bot, update, ultimaMensagem);
				break;
	
			case Ajuda.combNomes:
				mensagem = Love.obterPorcentagem(sendResponse, bot, update);
				break;
				
			case Ajuda.cep:
				mensagem = CEP.obterEndereco(sendResponse, bot, update);
				break;
				
			default:
				UtilsBot.bemVindo(sendResponse, bot, update);
		}

//		Atualiza chat ID com nova mensagem
		chatControl = new ChatControl(chatId, mensagem);
		listChatControl.set(index, chatControl);

	}
	
	/**
	 * Verifica mensagem enviada
	 * 
	 * @param mensagem
	 * @param sendResponse
	 * @param bot
	 * @param update
	 */
	private static void verificaMensagem(String mensagem, SendResponse sendResponse, TelegramBot bot, Update update) {
		final String regex = "^\\/+";
		Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
		List<ChatControl> filter;

//		Controla mensagens enviadas do usuario
//		Valida se o registro j� existe
		filter = listChatControl.stream().filter(item -> item.getChatId().equals(update.message().chat().id()))
				.collect(Collectors.toList());
		if (filter.isEmpty() == false) {
			chatControl = filter.get(0);
		}

//		regular expressions para identificar um comando
		if (mensagem != null && pattern.matcher(mensagem).find()) {

//			Quando executa um comando nao precisa verificar a ultima mensagem
//			portantano basta atualizar a lista de controle
			if (chatControl != null) {
				listChatControl.set(listChatControl.indexOf(chatControl), new ChatControl(chatId, mensagem));
			} else {
				listChatControl.add(new ChatControl(chatId, mensagem));
			}

//			Verifca o comando informado
			switch (mensagem) {

				case Ajuda.ajuda:
					Ajuda.listarComandos(sendResponse, bot, update);
					break;
	
				case Ajuda.tempo:
					Tempo.solicitarLocalizacao(sendResponse, bot, update, mensagem);
					break;
	
				case Ajuda.previsao:
					Tempo.solicitarLocalizacao(sendResponse, bot, update, mensagem);
					break;
					
				case Ajuda.combNomes:
					Love.solicitarNomes(sendResponse, bot, update, mensagem);
					break;
					
				case Ajuda.cep:
					CEP.solicitarCEP(sendResponse, bot, update, mensagem);
					break;
	
				default:
					Ajuda.comandoInvalido(sendResponse, bot, update);
					break;
			}

		} else {

			if (filter.isEmpty() == false) {

				verificaUltimaMensagem(sendResponse, bot, update);

			} else {

				listChatControl.add(new ChatControl(chatId, mensagem));
				UtilsBot.bemVindo(sendResponse, bot, update);

			}

		}

	}

}