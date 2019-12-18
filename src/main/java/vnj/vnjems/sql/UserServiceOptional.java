package vnj.vnjems.sql;

import vnj.vnjems.Vnjeconomymod;
import vnj.vnjems.User;
import vnj.vnjems.api.IUser;

import java.util.Optional;
import java.util.UUID;

public class UserServiceOptional {

    public static Optional<IUser> getUserByName(String user_name) {
        User user = UserSqlRequests.findByName(user_name);
        if (user == null){
            Vnjeconomymod.logger.warn("User not found by name. Name: " + user_name);
            return Optional.empty();
        }
        Vnjeconomymod.logger.debug("User found. Name: " + user.getName());
        return Optional.of(user);
    }

    public static Optional<IUser> getUserByUUID(UUID user_uuid) {
        User user = UserSqlRequests.findByUUID(user_uuid.toString());
        if (user == null){
            Vnjeconomymod.logger.warn("User not found by UUID. UUID: " + user_uuid);
            return Optional.empty();
        }
        Vnjeconomymod.logger.debug("User found. Name: " + user.getName() + ", UUID: " + user.getUuid());
        return Optional.of(user);
    }

    public static boolean updateUser(User user, String user_name) {
        boolean saveResult = UserSqlRequests.updateUserByName(user, user_name);
        if (saveResult)
            Vnjeconomymod.logger.debug("User was updated successfully by user name: " + user_name);
        else
            Vnjeconomymod.logger.debug("User update failed by user name: " + user_name);
        return saveResult;
    }

    public static boolean addNewUser(User newUser) {
        boolean addingResult = UserSqlRequests.save(newUser);
        if (addingResult) {
            Vnjeconomymod.logger.debug("New user was added. User: " + newUser.toString());
        } else {
            Vnjeconomymod.logger.warn("User was not added. User: " + newUser.toString());
        }
        return addingResult;
    }

    public static boolean removeUserByName(String user_name) {
        boolean deletingResult = UserSqlRequests.removeByName(user_name);
        if (deletingResult) {
            Vnjeconomymod.logger.debug("User was deleted by name: " + user_name);
        } else {
            Vnjeconomymod.logger.warn("User was not deleted by name: "  + user_name);
        }
        return deletingResult;
    }

    public static boolean exists(String user_name) {
        User user = UserSqlRequests.findByName(user_name);
        if (user == null){
            Vnjeconomymod.logger.warn("User was not found by name: ".concat(user_name));
            return false;
        }
        Vnjeconomymod.logger.debug("User found. Name: " + user.getName());
        return true;
    }
}
