package top.yzljc.flyBoomBed.listener;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import top.yzljc.flyBoomBed.FlyBoomBed;
import top.yzljc.flyBoomBed.util.BedMissileUtil;

public class ProjectileListener implements Listener {

    private final FlyBoomBed plugin;

    public ProjectileListener(FlyBoomBed plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;
        Snowball projectile = (Snowball) event.getEntity();

        if (!projectile.hasMetadata(BedMissileUtil.MISSILE_KEY)) return;

        World world = projectile.getWorld();

        Entity hitEntity = event.getHitEntity();
        world.createExplosion(projectile.getLocation(), 5.0F, true, true);

        world.spawnParticle(Particle.EXPLOSION_EMITTER, projectile.getLocation(), 1);
        world.playSound(projectile.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 4.0f, 1.0f);

    }

    public FlyBoomBed getPlugin() {
        return plugin;
    }
}