package top.yzljc.flyBoomBed.manager;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerModeManager {
    private final Set<UUID> enabledPlayers = new HashSet<>();

    public boolean toggleMode(Player player) {
        if (enabledPlayers.contains(player.getUniqueId())) {
            enabledPlayers.remove(player.getUniqueId());
            return false;
        } else {
            enabledPlayers.add(player.getUniqueId());
            return true;
        }
    }

    public boolean isModeEnabled(Player player) {
        return enabledPlayers.contains(player.getUniqueId());
    }
}