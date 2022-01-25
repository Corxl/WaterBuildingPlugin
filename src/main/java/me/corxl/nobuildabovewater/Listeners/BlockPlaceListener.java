package me.corxl.nobuildabovewater.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.HashSet;

public class BlockPlaceListener implements Listener {

    private final HashSet<Material> waterMats = new HashSet<>();

    public BlockPlaceListener() {
        waterMats.addAll(Arrays.asList(Material.KELP_PLANT, Material.WATER, Material.KELP, Material.SEAGRASS, Material.TALL_SEAGRASS));
    }

    @EventHandler
    public void onReplaceWater(PlayerInteractEvent event) {
        if (event.getPlayer().hasPermission("water.build")) return;
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
        if (event.getClickedBlock()==null) return;
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        if (block.getType()==Material.WATER) {
            BlockData water = block.getState().getBlockData();
            if (((Levelled) water).getLevel()>0) return;
            event.setCancelled(true);
            errorMessage(event.getPlayer());
            return;
        }
        if (waterMats.contains(event.getClickedBlock().getType())) {
            event.setCancelled(true);
            errorMessage(event.getPlayer());
            return;
        }
        if (waterMats.contains(block.getType())) {
            event.setCancelled(true);
            errorMessage(event.getPlayer());
            return;
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().hasPermission("water.build")) return;
        Block block = event.getBlock();
        Location location = block.getLocation();
        World world = location.getWorld();
        while (location.getY()>0) {

            location.setY(location.getY()-1);
            if (world.getBlockAt(location).getType()==Material.AIR)
                continue;
            if (waterMats.contains(world.getBlockAt(location).getType())) {
                errorMessage(event.getPlayer());
                event.setCancelled(true);
                break;
            }
            break;
        }


    }
    private void errorMessage(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou cannot place above or in water!"));
    }
}
