package com.tyler.api.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Room {
    private int id;
    private int hostId;
    private String game;
    private HashSet<Integer> userIds;

    public Room(int id, int hostId, String game) {
        this.id = id;
        this.hostId = hostId;
        this.game = game;
        this.userIds = new HashSet<Integer>();
        userIds.add(hostId);
    }

    public Room() {
        this.id = -1;
        this.hostId = -1;
        this.game = "DB Error";
        this.userIds = new HashSet<>();
    }

//    public Room(int id, int hostId, String game, ArrayList<User> users) {
//        this.id = id;
//        this.hostId = hostId;
//        this.game = game;
//    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int hostId) {
        this.hostId = hostId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public HashSet<Integer> getUsers() { return userIds; }

    public void setUsers(HashSet users) { this.userIds = users; }

    public void addUser(int uid) { this.userIds.add(uid); }

    public void removeUser(int uid) { this.userIds.remove(uid); }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", game='" + game + '\'' +
                ", users=" + userIds +
                '}';
    }
}
