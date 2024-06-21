package com.example.consumer.telegram;

import com.example.consumer.model.TelegramUser;
import com.example.consumer.repository.TelegramUserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * consumer
 * Author: Vasylenko Oleksii
 * Date: 15.06.2024
 */
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Bot extends TelegramLongPollingBot{
    TelegramProperties telegramProperties;
    TelegramUserRepository telegramUserRepository;

    @Autowired
    public Bot(TelegramProperties telegramProperties, TelegramUserRepository telegramUserRepository) {
        super(telegramProperties.getToken());
        this.telegramProperties = telegramProperties;
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public String getBotUsername() {
        return telegramProperties.getUsername();
    }

    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error sending message to Telegram: {}", e.getMessage());
        }
    }

    public void sendMessageToAllUsers(String message) {
        telegramUserRepository
                .findAll()
                .forEach(user -> sendMessage(user.getChatId(), message));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String receivedMessage = update.getMessage().getText();

            if ("/start".equals(receivedMessage)) {
                // Save user to the database
                TelegramUser user = new TelegramUser();
                user.setChatId(chatId);  // Assuming chatId is stored as String in your TelegramUser entity

                telegramUserRepository.save(user);

                // Send a response to the user
                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText("Hello! You have started the bot.");

                try {
                    execute(message);  // Sending the message to Telegram
                } catch (TelegramApiException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }
}
