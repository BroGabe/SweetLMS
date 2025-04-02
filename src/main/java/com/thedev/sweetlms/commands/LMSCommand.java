package com.thedev.sweetlms.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.thedev.sweetlms.SweetLMS;
import com.thedev.sweetlms.modules.LMSManager;
import com.thedev.sweetlms.utils.ColorUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("lms|lastmanstanding")
public class LMSCommand extends BaseCommand {

    private final SweetLMS plugin;

    private final LMSManager lmsManager;

    public LMSCommand(SweetLMS plugin) {
        this.plugin = plugin;

        lmsManager = plugin.getLmsManager();
    }

    @Subcommand("start")
    @CommandPermission("sweetlms.admin")
    @Description("starts the LMS event")
    public void onStart(CommandSender sender) {
        if(!lmsManager.canGameStart()) {
            sender.sendMessage(ColorUtil.color("&5&lLMS &fThe Game cannot be started!"));
            return;
        }

        lmsManager.startGame();

        sender.sendMessage(ColorUtil.color("&5&lLMS &fYou have started the LMS event!"));
    }

    @Subcommand("setkit")
    @CommandPermission("sweetlms.admin")
    @Description("sets the kit to your inventory")
    public void onSetKit(Player player) {
        lmsManager.setKitContents(player);

        player.sendMessage(ColorUtil.color("&5&lLMS &fYou have created the default kit!"));
    }
}
