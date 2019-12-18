package vnj.vnjems.api;

import vnj.vnjems.sql.UserServiceOptional;

import java.util.Optional;
import java.util.UUID;

public class VnjDataLib {
    public static Optional<IUser> getUserDataByName(String user_name) {
        return UserServiceOptional.getUserByName(user_name);
    }

    public static Optional<IUser> getUserDataByUUID(UUID user_uuid) {
        return UserServiceOptional.getUserByUUID(user_uuid);
    }

    public static Optional<IUser> getUserDataByUUID(String user_uuid) {
        return UserServiceOptional.getUserByUUID(UUID.fromString(user_uuid));
    }
}
