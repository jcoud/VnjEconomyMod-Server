package vnj.vnjems.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandManager extends CommandBase {
    @Override
    public String getName() {
        return "null";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "null";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        return;
    }
}
