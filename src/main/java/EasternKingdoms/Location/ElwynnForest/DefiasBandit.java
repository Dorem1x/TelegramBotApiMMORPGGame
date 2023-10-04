package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class DefiasBandit extends NPC {
    String name = "Бандит из Братства Справедливости";
    int damageDone = 25;
    int health = 200;
    int experience = 150;

    int currentHealth = health;

    int coin = 30;

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
        return new DefiasBandit();
    }
}
