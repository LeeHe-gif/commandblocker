package he.m346.cmdblock;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    private final CommandBlocker plugin;

    public CommandListener(CommandBlocker plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        // 检查插件是否启用
        if (!plugin.isPluginEnabled()) {
            return;
        }

        String command = event.getMessage().split(" ")[0].substring(1); // 去掉前面的斜杠
        if (plugin.getConfig().getStringList("blocked-commands").contains(command)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "未知的命令。请键入 \"/help\" 来获得帮助。");
        }
    }
}