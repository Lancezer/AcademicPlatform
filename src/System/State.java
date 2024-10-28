package System;

import User.*;

import java.util.ArrayList;

public class State {
    private static User curOnlineUser = null;
    private static ArrayList<User> onlineUserList = new ArrayList<>();

    private State() {
    }

    public static User getCurOnlineUser() {
        return curOnlineUser;
    }

    public static void setCurOnlineUser(User user) {
        curOnlineUser = user;
    }

    public static void addOnlineUser(User user) {
        onlineUserList.add(user);
    }

    public static User removeOnlineUserByUserType(User user) {
        int userIndex = onlineUserList.indexOf(user);
        if (userIndex == -1) {
            return null;
        }
        return onlineUserList.remove(userIndex);
    }

    public static User removeOnlineUserByIndex(int index) {
        return onlineUserList.remove(index);
    }

    public static boolean isOnline(String id) {
        for (User user : onlineUserList) {
            if (user.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOnlineListEmpty() {
        return onlineUserList.isEmpty();
    }
}