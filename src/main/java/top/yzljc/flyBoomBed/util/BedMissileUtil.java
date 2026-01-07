package top.yzljc.flyBoomBed.util;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import top.yzljc.flyBoomBed.FlyBoomBed;

public class BedMissileUtil {

    public static final String MISSILE_KEY = "YZ_LJC_FLY_BOOM_BED_MISSILE";

    public static void shootBedMissile(Player player, ItemStack bedItem) {
        World world = player.getWorld();

        Snowball projectile = player.launchProjectile(Snowball.class);
        projectile.setShooter(player);
        projectile.setMetadata(MISSILE_KEY, new FixedMetadataValue(FlyBoomBed.getInstance(), true));

        projectile.setItem(new ItemStack(Material.AIR));
        projectile.setSilent(true);

        Material bedMaterial = bedItem.getType();
        BlockData blockData = bedMaterial.createBlockData();

        BlockDisplay display = (BlockDisplay) world.spawnEntity(projectile.getLocation(), EntityType.BLOCK_DISPLAY);
        display.setBlock(blockData);

        Transformation transformation = display.getTransformation();
        transformation.getScale().set(1.0f, 1.0f, 1.0f);
        transformation.getTranslation().set(-0.5f, -0.25f, -0.5f);
        display.setTransformation(transformation);

        new BukkitRunnable() {
            float angle = 0.0f;

            @Override
            public void run() {
                if (projectile.isDead() || !projectile.isValid() || display.isDead()) {
                    display.remove();

                    if (!projectile.isDead()) {
                        projectile.remove();
                    }

                    this.cancel();
                    return;
                }

                Location loc = projectile.getLocation();
                display.teleport(loc);

                angle += 0.2f;

                Transformation currentTrans = display.getTransformation();
                AxisAngle4f rotation = new AxisAngle4f(angle, new Vector3f(1.0f, 1.0f, 0.0f).normalize());

                currentTrans.getLeftRotation().set(rotation);

                display.setInterpolationDelay(0);
                display.setInterpolationDuration(1);
                display.setTransformation(currentTrans);

                world.spawnParticle(Particle.END_ROD, loc, 1, 0.1, 0.1, 0.1, 0.01);
            }
        }.runTaskTimer(FlyBoomBed.getInstance(), 0L, 1L);

        world.playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.5f, 1.2f);
        world.playSound(player.getLocation(), Sound.BLOCK_WOOL_BREAK, 1.0f, 0.5f);

        if (player.getGameMode() != GameMode.CREATIVE) {
            bedItem.setAmount(bedItem.getAmount() - 1);
        }
    }
}