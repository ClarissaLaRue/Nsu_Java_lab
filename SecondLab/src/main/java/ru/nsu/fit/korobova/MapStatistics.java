package ru.nsu.fit.korobova;

import java.util.HashMap;
import java.util.Map;

public class MapStatistics {
    private final Map<String, Integer> userEditAmount = new HashMap<>();
    private final Map<String, Integer> tagKeyNodeAmount = new HashMap<>();

    void addUser(String userName) {
        userEditAmount.merge(userName, 1, Integer::sum);
    }

    void addTagKey(String tagKey) {
        tagKeyNodeAmount.merge(tagKey, 1, Integer::sum);
    }

    void printStatistics() {
        userEditAmount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(userAmount ->
                        System.out.println("User Name " + userAmount.getKey() + " Change count " + userAmount.getValue()));

        tagKeyNodeAmount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(tagKeyAmount ->
                        System.out.println("Tags " + tagKeyAmount.getKey() + " Tag Count " + tagKeyAmount.getValue()));
    }
}
