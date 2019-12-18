package vnj.vnjems;

import vnj.vnjems.api.IUser;

import java.util.UUID;

public class User implements IUser {

    private String name;
    private int id;
    private UUID uuid;
    private long balance;

    public User(String name, UUID uuid, long balance, int id) {
        this.name = name;
        this.uuid = uuid;
        this.balance = balance;
        this.id = id;
    }

    public User(String name, UUID uuid, long balance) {
        this.name = name;
        this.uuid = uuid;
        this.balance = balance;
    }

    public void decreaseBalance(long amount) {
        balance -= amount;
        if (balance < 0 ) balance = 0;
    }

    public void increaseBalance(long amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return String.format(
                "User info:\n" +
                "name: {%s}, id {%d}, uuid {%s}, balance {%d}",
                name, id, uuid, balance);
    }

    public int getUserId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Long getBalance() {
        return null;
    }

    @Override
    public UUID getUuid() {
        return null;
    }
}
