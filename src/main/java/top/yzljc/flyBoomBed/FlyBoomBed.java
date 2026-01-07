package top.yzljc.flyBoomBed;

import org.bukkit.plugin.java.JavaPlugin;
import top.yzljc.flyBoomBed.listener.BedInteractListener;
import top.yzljc.flyBoomBed.listener.ProjectileListener;
import top.yzljc.flyBoomBed.manager.PlayerModeManager;

public final class FlyBoomBed extends JavaPlugin {

    private static FlyBoomBed instance;
    private PlayerModeManager modeManager;

    @Override
    public void onEnable() {
        instance = this;
        this.modeManager = new PlayerModeManager();

        getServer().getPluginManager().registerEvents(new BedInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new ProjectileListener(this), this);

        getLogger().info("FlyBoomBed 插件已加载！BadBed导弹准备就绪。");
    }

    @Override
    public void onDisable() {
        // 卸载，不写这个IDEA给我爆黄线强迫症
    }

    public static FlyBoomBed getInstance() {
        return instance;
    }

    public PlayerModeManager getModeManager() {
        return modeManager;
    }
}