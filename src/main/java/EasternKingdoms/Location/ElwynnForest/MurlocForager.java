package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class MurlocForager extends NPC {
    String name = "Мурлок-добытчик";
    int damageDone = 35;
    int health = 250;
    int experience = 200;

    int currentHealth = health;

    int coin = 50;

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
        return new MurlocForager();
    }
}
