package vnj.vnjems;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import vnj.vnjems.api.IUser;
import vnj.vnjems.commands.CommandManager;
import vnj.vnjems.sql.SqlExecutor;
import vnj.vnjems.sql.UserServiceOptional;

import java.util.Map;
import java.util.Optional;

@Mod(
        modid = Vnjeconomymod.MOD_ID,
        name = Vnjeconomymod.MOD_NAME,
        version = Vnjeconomymod.VERSION,
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class Vnjeconomymod {

    public static final String MOD_ID = "vnjems";
    public static final String MOD_NAME = "VnjEconomyMod Server";
    public static final String VERSION = "2019.2-1.3.1";
    public MinecraftServer server;
    public static Logger logger;

    @Mod.Instance(MOD_ID)
    public static Vnjeconomymod INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(new VnjEvents());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        new SqlExecutor();
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

    @NetworkCheckHandler
    public boolean checkRemote(Map<String, String> name, Side side) {
        return true;
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandManager());
        server = event.getServer();
    }

    static class VnjEvents {
        @SubscribeEvent
        public void onPlayerJoined(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.player.isServerWorld()) return;
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            if (UserServiceOptional.exists(player.getName())) {
                Optional<IUser> uo = UserServiceOptional.getUserByUUID(player.getUniqueID());
                if (!uo.isPresent()) {
                    logger.warn("User data request is null. Something went wrong!");
                    return;
                }
                IUser user = uo.get();
                logger.info("Player joined with: name {}, uuid {}, balance {}", user.getName(), user.getUuid(), user.getBalance());
            }
            else {
                UserServiceOptional.addNewUser(new User(player.getName(), player.getUniqueID(), 0L));
                logger.info("Added new user with: name {}, uuid {}, balance {}", player.getName(), player.getUniqueID(), 0L);
            }
        }
    }
}
