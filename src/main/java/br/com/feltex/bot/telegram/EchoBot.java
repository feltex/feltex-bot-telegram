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
            var textoMensagem = update.getMessage().getText();
            var chatId = update.getMessage().getChatId().toString();

            var resposta = "\uD83E\uDD16 Eu sou um bot: " + textoMensagem.toUpperCase() + getData();

            var mensagem = SendMessage
                    .builder()
                    .text(resposta)
                    .chatId(chatId)
                    .build();
            try {
                execute(mensagem);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String getData() {
        var formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        return formatter.format(new Date());
    }

}
