package he.m346.cmdblock;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandBlocker extends JavaPlugin {

    private boolean pluginEnabled = true; // 插件启用状态

    @Override
    public void onEnable() {
        // 加载配置文件
        this.saveDefaultConfig();

        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

        // 注册命令执行器
        this.getCommand("commandblocker").setExecutor(this);

        // 从配置文件中加载启用状态
        this.pluginEnabled = this.getConfig().getBoolean("enabled", true);
    }

    @Override
    public void onDisable() {
        // 插件禁用时的逻辑（可以留空）
    }

    // 处理命令
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("commandblocker")) {
            if (args.length == 0) {
                sender.sendMessage("§c用法: /commandblocker <on|off|reload>");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "on":
                    return enablePlugin(sender);
                case "off":
                    return disablePlugin(sender);
                case "reload":
                    return reloadConfig(sender);
                default:
                    sender.sendMessage("§c用法: /commandblocker <on|off|reload>");
                    return true;
            }
        }
        return false;
    }

    // 启用插件
    private boolean enablePlugin(CommandSender sender) {
        if (!sender.hasPermission("commandblocker.toggle")) {
            sender.sendMessage("§c你没有权限执行此命令。");
            return true;
        }

        if (this.pluginEnabled) {
            sender.sendMessage("§a插件已经是启用状态。");
            return true;
        }

        this.pluginEnabled = true;
        this.getConfig().set("enabled", true);
        this.saveConfig();
        sender.sendMessage("§a插件已启用！");
        return true;
    }

    // 禁用插件
    private boolean disablePlugin(CommandSender sender) {
        if (!sender.hasPermission("commandblocker.toggle")) {
            sender.sendMessage("§c你没有权限执行此命令。");
            return true;
        }

        if (!this.pluginEnabled) {
            sender.sendMessage("§a插件已经是禁用状态。");
            return true;
        }

        this.pluginEnabled = false;
        this.getConfig().set("enabled", false);
        this.saveConfig();
        sender.sendMessage("§a插件已禁用！");
        return true;
    }

    // 重载配置文件
    private boolean reloadConfig(CommandSender sender) {
        if (!sender.hasPermission("commandblocker.reload")) {
            sender.sendMessage("§c你没有权限执行此命令。");
            return true;
        }

        this.reloadConfig();
        this.pluginEnabled = this.getConfig().getBoolean("enabled", true);
        sender.sendMessage("§a配置文件已重载！");
        return true;
    }

    // 获取插件启用状态
    public boolean isPluginEnabled() {
        return this.pluginEnabled;
    }
}