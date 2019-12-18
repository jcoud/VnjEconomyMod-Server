package vnj.vnjems.api;

import java.util.UUID;

public interface IUser {
    String getName();

    Long getBalance();

    UUID getUuid();
}
