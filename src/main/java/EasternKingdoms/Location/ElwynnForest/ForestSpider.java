package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class ForestSpider extends NPC {
    String name = "Лесной паук";
    int damageDone = 15;
    int health = 125;
    int experience = 75;

    int currentHealth = health;

    int coin = 10;

    @Override
    public int getCoin() {
        return coin;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDamageDone() {
        return damageDone;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public NPC createNewNPC() {
        return new ForestSpider();
    }
}
