package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class YoungWolf extends NPC {
    String name = "Молодой волк";

    @Override
    public NPC createNewNPC() {
        return new YoungWolf();
    }

    int damageDone = 10;
    int health = 100;
    int experience = 50;
    int currentHealth = health;

    int coin = 5;

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


}
