import EasternKingdoms.EasternKingdoms;
import Game.NPC;
import Game.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.event.ActionListener;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class TelegramBot extends TelegramLongPollingBot {


    String locationName;
    EasternKingdoms easternKingdoms = new EasternKingdoms();
    //    NPC target;
    public HashMap<String, Player> playerHashMap = new HashMap<>();
    public HashMap<String, LocalDateTime> beforeTavern = new HashMap<>();
    public HashMap<String, LocalDateTime> afterTavern = new HashMap<>();

    boolean isFight = false;

    public void putToBeforeTavern(String playerName){
        LocalDateTime localDateTime = LocalDateTime.now();
        beforeTavern.put(playerName, localDateTime);
    }
    public void putToAfterTavern(String playerName){
        LocalDateTime localDateTime = LocalDateTime.now();
        afterTavern.put(playerName, localDateTime);
    }
    public int getEnergy(String playerName){
        int addEnergy = 0;
        LocalDateTime before = beforeTavern.get(playerName);
        LocalDateTime after = afterTavern.get(playerName);
//        Duration duration = Duration.between(before,after).toMinutes();
//        long seconds = Math.abs(duration.getSeconds());
        long minutes = Duration.between(before,after).toSeconds();
        addEnergy = (int) minutes;

        return addEnergy;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            String playerName = update.getMessage().getChat().getUserName();

            long chatId = update.getMessage().getChatId();


//                    while (playerHashMap.get(playerName).getEnergy() <= playerHashMap.get(playerName).getMaxEnergy() + 1)
//                    playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getEnergy() + 1);
//                    if(playerHashMap.get(playerName).getEnergy() == playerHashMap.get(playerName).getMaxEnergy() + 1) {
//                        playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getMaxEnergy());
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
            //Если персонаж уже создан
            if (playerHashMap.containsKey(playerName)) {
                //Если персонаж в бою
                if (playerHashMap.get(playerName).isFight()) {
                    switch (command) {
                        case "Атаковать!":
                            int energyLess = 10;
                            playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getEnergy() - energyLess);
                            while (getTargetNPCForPlayer(playerName).getCurrentHealth() > 0) {
                                NPC targetNPC = getTargetNPCForPlayer(playerName);
                                Player player = playerHashMap.get(playerName);
                                targetNPC.setCurrentHealth(targetNPC.getCurrentHealth() - player.getDamage());
                                player.setHealth(player.getHealth() - targetNPC.getDamageDone());
                            }
                            //Условие победы
                            if (playerHashMap.get(playerName).getHealth() > 0) {
                                playerHashMap.get(playerName).setHealth(playerHashMap.get(playerName).getMaxHealth());
                                playerHashMap.get(playerName).setExperience(playerHashMap.get(playerName).getExperience() +
                                        getTargetNPCForPlayer(playerName).getExperience());
                                playerHashMap.get(playerName).setCoin(playerHashMap.get(playerName).getCoin() +
                                        getTargetNPCForPlayer(playerName).getCoin());
                                //Level UP!
                                if (playerHashMap.get(playerName).getExperience() >=
                                        playerHashMap.get(playerName).getExperienceToUpLevel()) {
                                    int energyUp = 15;
                                    int damageUp = 10;
                                    int maxHealthUp = 25;
                                    Player player = playerHashMap.get(playerName);
                                    player.setLevel(player.getLevel() + 1);
                                    player.setExperienceToUpLevel(player.getExperienceToUpLevel() * 2);
                                    player.setExperience(0);
                                    player.setMaxEnergy(player.getMaxEnergy() + energyUp);
                                    player.setEnergy(player.getMaxEnergy());
                                    player.setDamage(player.getDamage() + damageUp);
                                    player.setMaxHealth(player.getMaxHealth() + maxHealthUp);
                                    player.setHealth(player.getMaxHealth());
                                    sendPhoto(chatId, "", "src/main/resources/Уровень повышен.JPG");
                                    sendMessage(chatId,
                                            "ПОЗДРАВЛЯЕМ! УРОВЕНЬ ПОВЫШЕН! ТЕПЕРЬ ВАШ ПЕРСОНАЖ " +
                                                    playerHashMap.get(playerName).getLevel() + " УРОВНЯ!" +
                                                    "\nУрон увеличен на " + damageUp +
                                                    "\nМаксимальная энергия увеличена на " + energyUp +
                                                    "\nМаксимальный уровень здоровья повышен на " + maxHealthUp,
                                            "locationMenu");

                                } else {

                                    sendPhoto(chatId, "", "src/main/resources/Победа.JPG");
                                    sendMessage(chatId,
                                            "Победа над: " + getTargetNPCForPlayer(playerName).getName() +
                                                    "\nОпыта получено: " +
                                                    getTargetNPCForPlayer(playerName).getExperience() +
                                                    "\nМонет заработано: " + getTargetNPCForPlayer(playerName).getCoin() +
                                                    "\nЭнергии потрачено: " + energyLess,
                                            "locationMenu");
                                }

                            } else {
                                int coinLost = 10;
                                playerHashMap.get(playerName).setHealth(playerHashMap.get(playerName).getMaxHealth());
                                playerHashMap.get(playerName).setCoin(playerHashMap.get(playerName).getCoin() - coinLost);
                                if (playerHashMap.get(playerName).getCoin() < 0) {
                                    playerHashMap.get(playerName).setCoin(0);
                                }
                                sendPhoto(chatId, "", "src/main/resources/Поражение.JPG");
                                sendMessage(chatId,
                                        "Поражение от: " + getTargetNPCForPlayer(playerName).getName() +
                                                "\nМонет потеряно: " + coinLost +
                                                "\nЭнергии потрачено: " + energyLess,
                                        "locationMenu");

                            }

                            playerHashMap.get(playerName).setFight(false);
                            break;
                        case "Бежать!":
                            int coinLost = 5;
                            int energyLost = 5;
                            playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getEnergy() - energyLost);

                            playerHashMap.get(playerName).setCoin(playerHashMap.get(playerName).getCoin() - coinLost);
                            if (playerHashMap.get(playerName).getCoin() < 0) {
                                playerHashMap.get(playerName).setCoin(0);
                            }
                            sendPhoto(chatId, "", "src/main/resources/Бежать!.JPG");
                            sendMessage(chatId,
                                    "Убежали от: " + getTargetNPCForPlayer(playerName).getName() +
                                            "\nЭнергии потрачено: " + energyLost +
                                            "\nМонет потеряно: " + coinLost,
                                    "locationMenu");
                            playerHashMap.get(playerName).setFight(false);

                            break;
                        default:
                            sendMessage(chatId,
                                    "Твой противник: " + getTargetNPCForPlayer(playerName).getName() +
                                            "\nУрон: " + getTargetNPCForPlayer(playerName).getDamageDone() +
                                            "\nЗдоровье: " +
                                            getTargetNPCForPlayer(playerName).getCurrentHealth() +
                                            "/" + getTargetNPCForPlayer(playerName).getHealth() +
                                            "\nБудет получено опыта за победу: " +
                                            getTargetNPCForPlayer(playerName).getExperience() +
                                            "\nБудет получено монет за победу: " +
                                            getTargetNPCForPlayer(playerName).getCoin(),
                                    "locationMenuAttack");
                    }
                } else if(playerHashMap.get(playerName).isRest()){
                    switch (command){
                        case "Выйти из таверны":

                            putToAfterTavern(playerName);
                            int energyUpp = getEnergy(playerName);
                            playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getEnergy() + energyUpp);
                            if(playerHashMap.get(playerName).getEnergy() > playerHashMap.get(playerName).getMaxEnergy()){
                                playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getMaxEnergy());
                            }
                            playerHashMap.get(playerName).setRest(false);
                            sendMessage(chatId, "Приходите еще!" +
                                    "\n Энергии восстановлено: " + energyUpp, "locationMenu");
                            break;
                        default:
                            sendMessage(chatId, "Нужно выйти из таверны(", "tavernMenu");
                            break;
                    }
                }else {
                    switch (command) {

                        case "Найти противника":
                            if (playerHashMap.get(playerName).getEnergy() >= 10) {
                                playerHashMap.get(playerName).setTargetNPC(
                                        easternKingdoms.getLocationHashMap().get(locationName).getNpcList().get(getRandomNPC())
                                                .createNewNPC());
                                sendPhoto(chatId, "", "src/main/resources/" +
                                        getTargetNPCForPlayer(playerName).getName() + ".jpg");
                                sendMessage(chatId,
                                        "Твой противник: " +
                                                getTargetNPCForPlayer(playerName).getName() +
                                                "\nУрон: " +
                                                getTargetNPCForPlayer(playerName).getDamageDone() +
                                                "\nЗдоровье: " +
                                                getTargetNPCForPlayer(playerName).getCurrentHealth() +
                                                "/" + getTargetNPCForPlayer(playerName).getHealth() +
                                                "\nБудет получено опыта за победу: " +
                                                getTargetNPCForPlayer(playerName).getExperience() +
                                                "\nБудет получено монет за победу: " + getTargetNPCForPlayer(playerName).getCoin(),
                                        "locationMenuAttack");

                                playerHashMap.get(playerName).setFight(true);
                            } else {
                                sendMessage(chatId,
                                        "Недостаточно энергии! Зайдите в Таверну, отдохните!",
                                        "locationMenu");
                            }
                            break;
                        case "Информация о персонаже":
                            sendPhoto(chatId, "", "src/main/resources/Персонаж.JPG");
                            sendMessage(chatId, "Информация о персонаже:" +
                                            "\nЗдоровье: " + playerHashMap.get(playerName).getHealth() + "/" +
                                            playerHashMap.get(playerName).getMaxHealth() +
                                            "\nУрон: " + playerHashMap.get(playerName).getDamage() +
                                            "\nУровень: " + playerHashMap.get(playerName).getLevel() +
                                            "\nОпыт: " + playerHashMap.get(playerName).getExperience() + "/" +
                                            playerHashMap.get(playerName).getExperienceToUpLevel() +
                                            "\nЭнергия: " + playerHashMap.get(playerName).getEnergy() + "/" +
                                            playerHashMap.get(playerName).getMaxEnergy() +
                                            "\nКоличество монет: " + playerHashMap.get(playerName).getCoin(),
                                    "characterMenu");

                            break;
                        case "Выбрать локацию":
                            sendMessage(chatId, "Выбираем локацию...",
                                    "chooseLocation");
                            break;
                        case "Зайти в таверну":
                            sendPhoto(chatId, "", "src/main/resources/Зайти в таверну.JPG");
//                            int energyApp = 10;
//                            playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getEnergy() + energyApp);
//                            if (playerHashMap.get(playerName).getEnergy() > playerHashMap.get(playerName).getMaxEnergy()) {
//                                playerHashMap.get(playerName).setEnergy(playerHashMap.get(playerName).getMaxEnergy());
//                            }
                            putToBeforeTavern(playerName);
                            sendMessage(chatId, "Добро пожаловать в таверну!" +
                                            "\nЭнергия будет накапливаться каждую секунду, просто подождите и " +
                                            "не забудьте выйти!",
                                    "tavernMenu");
                            playerHashMap.get(playerName).setRest(true);
                            break;
                        case "Сменить локацию":
                            sendMessage(chatId, "Поменять на: ",
                                    "chooseLocation");
                            break;
                        case "Элвинский лес":
                            setLocationName("Элвинский лес");
                            sendPhoto(chatId, "", "src/main/resources/" +
                                    getLocationName() + ".jpg");
                            sendMessage(chatId, "Элвиннский лес — один из экономически важных районов штормградского " +
                                            "королевства, исправно снабжающий столицу строевым лесом, " +
                                            "продуктами сельского хозяйства, рыбой и полезными ископаемыми. " +
                                            "Элвиннский лес или просто Элвинн — спокойная и прекрасная местность, " +
                                            "покрытая густыми лесами и богатая плодородными землями.",
                                    "locationMenu");

                            break;
                        case "Западный край":
                            if (playerHashMap.get(playerName).getLevel() <
                                    easternKingdoms.getLocationHashMap().get("Западный край").getRequiredLevel()) {
                                sendMessage(chatId, "Недостаточно высокий уровень," +
                                                "\nТребуется уровень: " +
                                                easternKingdoms.getLocationHashMap().get("Западный край").
                                                        getRequiredLevel(),
                                        "chooseLocation");

                            } else {
                                setLocationName("Западный край");
                                sendPhoto(chatId, "", "src/main/resources/" +
                                        getLocationName() + ".jpg");
                                sendMessage(chatId, " Западный Край заполонили бандиты из Братства Справедливости. " +
                                                "Штормград считает эти земли своей собственностью, но королевству " +
                                                "недостаточно средств, чтобы установить в этих землях полный порядок",
                                        "locationMenu");

                            }
                            break;
                        case "Красногорье":
                            if (playerHashMap.get(playerName).getLevel() <
                                    easternKingdoms.getLocationHashMap().get("Красногорье").getRequiredLevel()) {
                                sendMessage(chatId, "Недостаточно высокий уровень," +
                                                "\nТребуется уровень: " +
                                                easternKingdoms.getLocationHashMap().get("Красногорье").getRequiredLevel(),
                                        "chooseLocation");
                            } else {
                                setLocationName("Красногорье");
                                sendPhoto(chatId, "", "src/main/resources/" +
                                        getLocationName() + ".jpg");
                                sendMessage(chatId, "В основном локация внешне похожа на Элвиннский лес: " +
                                                "защищенные башни, фермы, вместо лесов холмы. Люди здесь довольные и " +
                                                "спокойные. Население занимается промыслом рыбы и поставкой " +
                                                "лесозаготовок в Штормград.",
                                        "locationMenu");

                            }
                            break;
                        case "Баланс":
                            sendMessage(chatId, "\nКоличество монет сейчас: " + playerHashMap.get(playerName).getCoin(),
                                    "shopMenu");
                            break;
                        case "Персонаж":
                            sendMessage(chatId, "Персонаж:",
                                    "characterMenu");
                            break;
                        case "Главное меню":
                            sendMessage(chatId, "Главное меню:",
                                    "mainMenu");
                            break;
                        case "Магазин":
                            sendMessage(chatId, "Посмотрим, что тут новенького...",
                                    "shopMenu");
                            break;
                        case "Список товаров":
                            sendMessage(chatId, "Список товаров пуст.",
                                    "shopMenu");
                            break;
                    }
                }
            } else {
                switch (command) {
                    case "/start":
                        sendPhoto(chatId, "", "src/main/resources/Logo.jpg");
                        sendMessage(chatId, "Да начнется новое приключение!",
                                "mainMenu");

                        break;
                    case "Создать персонажа":
                        Player player = new Player(playerName);
                        playerHashMap.put(playerName, player);
                        sendMessage(chatId, "Персонаж с именем " + player.getName() + " создан",
                                "characterMenu");
                        break;
                    case "Главное меню":
                        sendMessage(chatId, "Главное меню:",
                                "mainMenu");
                        break;
                    case "Персонаж":
                        sendMessage(chatId, "Персонаж:",
                                "characterMenu");
                        break;
                    default:
                        sendMessage(chatId, "Для этого сначала нужно создать персонажа!",
                                "characterMenu");
                }
            }
        }
    }

    public void sendPhoto(long chatId, String imageCaption, String imagePath) {
//   File image = new File();
//    SendPhoto sendPhoto = new SendPhoto().setPhoto(image);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(new File(imagePath)));
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(imageCaption);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId, String textToSend, String keyboardRowName) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);


        switch (keyboardRowName) {
            case ("mainMenu"):
                message.setReplyMarkup(getMainMenu());
                break;
            case ("locationMenu"):
                message.setReplyMarkup(getLocationMenu());
                break;
            case ("locationMenuAttack"):
                message.setReplyMarkup(getLocationMenuAttack());
                break;
            case ("shopMenu"):
                message.setReplyMarkup(getShopMenu());
                break;
            case ("characterMenu"):
                message.setReplyMarkup(getCharacterMenu());
                break;
            case ("chooseLocation"):
                message.setReplyMarkup(getChooseLocationMenu());
                break;
            case("tavernMenu"):
                message.setReplyMarkup(getTavernMenu());

        }


        try {
            execute(message);
        } catch (TelegramApiException telegramApiException) {

        }
    }

    public ReplyKeyboardMarkup getMainMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();

        keyboardRow.add("Выбрать локацию");
        keyboardRow2.add("Персонаж");
        keyboardRow3.add("Магазин");

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getTavernMenu(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();


        keyboardRow.add("Выйти из таверны");


        keyboardRows.add(keyboardRow);


        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getLocationMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();

        keyboardRow.add("Найти противника");
        keyboardRow1.add("Сменить локацию");
        keyboardRow1.add("Зайти в таверну");
        keyboardRow2.add("Магазин");
        keyboardRow3.add("Персонаж");
        keyboardRow4.add("Главное меню");

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getLocationMenuAttack() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();

        keyboardRow.add("Атаковать!");
        keyboardRow2.add("Бежать!");

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getShopMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();

        keyboardRow.add("Список товаров");
        keyboardRow2.add("Баланс");
        keyboardRow3.add("Персонаж");
        keyboardRow4.add("Главное меню");

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getCharacterMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();


        keyboardRow.add("Информация о персонаже");
        keyboardRow1.add("Создать персонажа");
        keyboardRow2.add("Магазин");
        keyboardRow3.add("Главное меню");


        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow1);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);


        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup getChooseLocationMenu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardRow keyboardRow3 = new KeyboardRow();
        KeyboardRow keyboardRow4 = new KeyboardRow();

        keyboardRow.add("Элвинский лес");
        keyboardRow2.add("Западный край");
        keyboardRow3.add("Красногорье");
        keyboardRow4.add("Главное меню");

        keyboardRows.add(keyboardRow);
        keyboardRows.add(keyboardRow2);
        keyboardRows.add(keyboardRow3);
        keyboardRows.add(keyboardRow4);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    int getRandomNPC() {
        int cubic = (int) (Math.random() * 205 + 1);
        int npcNumber = 0;
        if ((cubic <= 70) && (cubic > 0)) {
            npcNumber = 0;
        } else if ((cubic <= 120) && (cubic > 70)) {
            npcNumber = 1;
        } else if (((cubic <= 150) && (cubic > 120))) {
            npcNumber = 2;
        } else if (((cubic <= 175) && (cubic > 150))) {
            npcNumber = 3;
        } else if (((cubic <= 190) && (cubic > 175))) {
            npcNumber = 4;
        } else if (((cubic <= 200) && (cubic > 190))) {
            npcNumber = 5;
        } else if (((cubic <= 205) && (cubic > 200))) {
            npcNumber = 6;
        }
        return npcNumber;
//        int countNPC = easternKingdoms.getLocationHashMap().get(locationName).getNpcList().size();
//        return (int) (Math.random() * countNPC);
    }

    NPC getTargetNPCForPlayer(String playerName) {
        return playerHashMap.get(playerName).getTargetNPC();
    }


    @Override
    public String getBotUsername() {
        return "TheLegendOfDorem1xBot";
    }

    public TelegramBot(String botToken) {
        super(botToken);
    }


}