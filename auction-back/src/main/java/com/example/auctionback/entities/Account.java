package com.example.auctionback.entities;

public class Account {
    private long currentMoney;
    private long reservedMoney;

    public boolean reserveMoney() {
        return true;
    }

    public boolean addMoney() {
        return true;
    }

    public boolean transferMoney() {
        return true;
    }

    public void setCurrentMoney(long currentMoney) {
        this.currentMoney = currentMoney;
    }

    public void setReservedMoney(long reservedMoney) {
        this.reservedMoney = reservedMoney;
    }

    public long getCurrentMoney() {
        return currentMoney;
    }

    public long getReservedMoney() {
        return reservedMoney;
    }
}