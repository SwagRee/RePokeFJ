package io.github.swagree.repokefj.holder.guiholder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.List;

public class GuiConfirmHolder implements InventoryHolder {
    public Inventory inv;
    public Player player;
    public GuiConfirmHolder(Player player) {
        this.player = player;

    }

    public void setupInv(Player player) {
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
