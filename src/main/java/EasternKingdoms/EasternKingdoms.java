package EasternKingdoms;

import EasternKingdoms.Location.ElwynnForest.ElwynnForest;
import EasternKingdoms.RedridgeMountains.RedridgeMountains;
import EasternKingdoms.Westfall.Westfall;
import Game.Location;

import java.util.HashMap;

public class EasternKingdoms {
    HashMap<String, Location> locationHashMap = new HashMap<>();
    {
        locationHashMap.put("Элвинский лес", new ElwynnForest(1));
        locationHashMap.put("Западный край", new Westfall(5));
        locationHashMap.put("Красногорье", new RedridgeMountains(10));
    }

    public HashMap<String, Location> getLocationHashMap() {
        return locationHashMap;
    }


}
