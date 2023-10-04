package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class Hogger extends NPC {
    String name = "Дробитель";
    int damageDone = 100;
    int health = 500;
    int experience = 500;

    int currentHealth = health;

    int coin = 200;

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
        return new Hogger();
    }
}
