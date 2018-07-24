package at.aau.softwaredynamics.diffws.util;

import at.aau.softwaredynamics.diffws.MyersDiff;
import at.aau.softwaredynamics.diffws.domain.Differ;

import java.util.HashMap;

public class MatcherRegistry {
    HashMap<Integer, Class<? extends Differ>> matcherMap;

    public MatcherRegistry() {
        this.matcherMap = new HashMap<>();

        // TODO (Thomas): init from config
        // TODO Insert differs here
        // matcherMap.put(1, MyDiffer.class);
    }

    public HashMap<Integer, Class<? extends Differ>> getMatcherMap() {
        return matcherMap;
    }

    public Class<? extends Differ> getMatcherTypeFor(int id) {
        return matcherMap.getOrDefault(id, null);
    }
}
