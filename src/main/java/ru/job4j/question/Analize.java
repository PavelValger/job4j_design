package ru.job4j.question;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Info rsl = new Info(0, 0, 0);
        var mapCurrent = current.stream().
                collect(Collectors.toMap(User::getId, User::getName));
        var mapPrevious = previous.stream().
                collect(Collectors.toMap(User::getId, User::getName));
        var itPrevious = previous.iterator();
        for (User userCurrent : current) {
            if (!previous.contains(userCurrent)) {
                rsl.setAdded(rsl.getAdded() + 1);
            }
        }
        while (itPrevious.hasNext()) {
            User userPrevious = itPrevious.next();
            if (!current.contains(userPrevious)) {
                rsl.setDeleted(rsl.getDeleted() + 1);
            }
        }
        for (Map.Entry<Integer, String> entry : mapCurrent.entrySet()) {
          if (mapPrevious.containsKey(entry.getKey())
                  && !mapPrevious.containsValue(entry.getValue())) {
              rsl.setChanged(rsl.getChanged() + 1);
              rsl.setAdded(rsl.getAdded() - 1);
              rsl.setDeleted(rsl.getDeleted() - 1);
            }
        }
        return rsl;
    }
}
