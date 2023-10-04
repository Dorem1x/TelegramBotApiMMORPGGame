package EasternKingdoms.Location.ElwynnForest;

import Game.NPC;

public class KoboldWorker extends NPC {
    String name = "Кобольд-рабочий";
    int damageDone = 20;
    int health = 150;
    int experience = 100;

    int currentHealth = health;

    int coin = 20;

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
        return new KoboldWorker();
    }
}
