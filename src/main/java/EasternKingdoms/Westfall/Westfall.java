package EasternKingdoms.Westfall;

import EasternKingdoms.Location.ElwynnForest.ForestSpider;
import EasternKingdoms.Location.ElwynnForest.YoungWolf;
import Game.Location;
import Game.NPC;

import java.util.ArrayList;
import java.util.List;

public class Westfall extends Location {
    int requiredLevel;
    String locationName = "Западный край";
    List<NPC> westfallNPCList = new ArrayList<>();
    {
        westfallNPCList.add(new YoungWolf());
        westfallNPCList.add(new ForestSpider());
    }

    @Override
    public String getLocationName() {
        return locationName;
    }

    @Override
    public List<NPC> getNpcList() {
        return westfallNPCList;
    }





    public Westfall(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    @Override
    public int getRequiredLevel() {
        return requiredLevel;
    }
}
