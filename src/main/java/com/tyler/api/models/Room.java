package com.tyler.api.models;

public class Room {
    private int id;
    private int hostId;
    private String gameType;

    public Room(int id, int hostId, String gameType) {
        this.id = id;
        this.hostId = hostId;
        this.gameType = gameType;
    }

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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", gameType='" + gameType + '\'' +
                '}';
    }
}
