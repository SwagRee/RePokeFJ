package io.github.swagree.repokefj.holder.guiholder;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import io.github.swagree.repokefj.GuiMain;
import io.github.swagree.repokefj.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiMainHolder implements InventoryHolder {
    public Inventory inv;
    public Player player;
    public Map<Integer, Pokemon> mapSlotToPokemon ;

    public GuiMainHolder(Player player) {
        this.player = player;
        setupInv(player);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void setupInv(Player player) {
        String title = getConfig().getString("Gui.mainGui.title").replace("&","§");
        List<String> guiSlots = getConfig().getStringList("Gui.mainGui.slot");

        int guiSize = guiSlots.size() * 9;
        this.inv = Bukkit.createInventory(this, guiSize, title);

        mapSlotToPokemon = new HashMap<>();

        for (int i = 0; i < guiSlots.size(); i++) {
            byte[] bytes = guiSlots.get(i).getBytes();
            for (int i1 = 0; i1 < bytes.length; i1++) {
                for(int j = 1;j<=6;j++){
                    if(bytes[i1] == Character.forDigit(j, 10)){
                        Pokemon pokemon = Pixelmon.storageManager.getParty(player.getUniqueId()).get(j - 1);
                        int invSlot = (i) * 9 + i1;
                        GuiMain.SpriteInGui(inv, player, pokemon, invSlot);
                        mapSlotToPokemon.put(invSlot, pokemon);
                    }
                }
            }
        }
    }

    private static FileConfiguration getConfig() {
        return Main.getPlugin().getConfig();
    }

}