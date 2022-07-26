package org.codekatha.trigram.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class TrigramsMap {

    private HashMap<String, ArrayList<String>> trigrams; // key - "I wish" , value = "I" , "I"
    private HashMap<String, Integer> counters;

    public TrigramsMap() {
        trigrams = new HashMap<>();
        counters = new HashMap<>();
    }

    public int size() {
        return trigrams.size();
    }

    public void put(String key, String value) {
        ArrayList<String> valueList = trigrams.get(key);
        if (valueList == null) {
            valueList = new ArrayList<>();
            trigrams.put(key, valueList);
        }
        valueList.add(value);
    }

    public String get(String key) {
        String result = null;
        if (trigrams.containsKey(key)) {
            Integer valueListCounter = counters.get(key);
            if (valueListCounter == null) {
                valueListCounter = 0;
            }
            ArrayList<String> valueList = trigrams.get(key);
            int index = valueListCounter.intValue() % valueList.size();
            result = valueList.get(index);
            counters.put(key, valueListCounter + 1);
        }

        return result;
    }

    public String getRandomKey() {
        String result = null;
        Collection<String> keySet = trigrams.keySet();
        if (keySet.size() > 0) {
            ArrayList<String> keys = new ArrayList<>();
            keys.addAll(keySet);
            Random random = new Random();
            result = keys.get(random.nextInt(keys.size()));
        }
        return result;
    }

}

