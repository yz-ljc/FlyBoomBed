package top.yzljc.flyBoomBed.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import top.yzljc.flyBoomBed.FlyBoomBed;
import top.yzljc.flyBoomBed.util.BedMissileUtil;

public class BedInteractListener implements Listener {

    private final FlyBoomBed plugin;

    public BedInteractListener(FlyBoomBed plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // 亿点点防止爆炸的逻辑
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.getType().name().endsWith("_BED")) {
            return;
        }

        World.Environment env = player.getWorld().getEnvironment();
        boolean isValidWorld = (env == World.Environment.NETHER || env == World.Environment.THE_END);

        if (!isValidWorld) return;

        if (player.isSneaking() && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            boolean newMode = plugin.getModeManager().toggleMode(player);
            if (newMode) {
                player.sendMessage(Component.text("已开启床铺导弹模式！右键空气发射！", NamedTextColor.GREEN));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 2f);
            } else {
                player.sendMessage(Component.text("已切换回普通放置模式！", NamedTextColor.YELLOW));
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 0.5f);
            }
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (plugin.getModeManager().isModeEnabled(player)) {
                BedMissileUtil.shootBedMissile(player, item);
                event.setCancelled(true);
            }
        }
    }
}