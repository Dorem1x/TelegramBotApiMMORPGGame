package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class RiverpawRunt extends NPC {
    String name = "Недоросток из стаи Речной Лапы";
    int damageDone = 50;
    int health = 300;
    int experience = 250;

    int currentHealth = health;

    int coin = 100;

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
        return new RiverpawRunt();
    }
}
