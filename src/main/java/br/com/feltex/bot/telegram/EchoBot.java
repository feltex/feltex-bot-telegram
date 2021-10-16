package br.com.feltex.bot.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return DadosBot.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return DadosBot.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var mensagem = responder(update);
            try {
                execute(mensagem);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage responder(Update update) {
        var textoMensagem = update.getMessage().getText().toLowerCase();
        var chatId = update.getMessage().getChatId().toString();

        var resposta = "";

        if ("data".equals(textoMensagem)) {
            resposta = getData();
        } else if (textoMensagem.startsWith("hora")) {
            resposta = getHora();
        } else if (textoMensagem.startsWith("ola") || textoMensagem.startsWith("olá") || textoMensagem.startsWith("oi")) {
            resposta = "\uD83E\uDD16 Olá, vejo que você entende muito sobre BOTS!";
        } else if (textoMensagem.startsWith("quem é você") || textoMensagem.startsWith("quem e voce")) {
            resposta = "\uD83E\uDD16 Eu sou um bot";
        } else if (textoMensagem.startsWith("/help")) {
            resposta = "Utilize um dos comandos:\nolá\ndata\nhora\nquem é você?";
        } else {
            resposta = "Não entendi!\nDigite /help para ver os comandos disponíveis.";
        }

        return SendMessage.builder()
                .text(resposta)
                .chatId(chatId)
                .build();
    }


    private String getData() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "A data atual é: " + formatter.format(new Date());
    }

    private String getHora() {
        var formatter = new SimpleDateFormat("HH:mm:ss");
        return "A hora atual é: " + formatter.format(new Date());
    }

}
