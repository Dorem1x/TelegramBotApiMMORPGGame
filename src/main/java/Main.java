import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        String botToken = "6458613606:AAFDRpwjOSzdHC9Tjm5kFRgR0-7DA4nSrTc";


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new TelegramBot(botToken));


        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
