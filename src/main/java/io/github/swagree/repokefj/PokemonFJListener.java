package io.github.swagree.repokefj;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.storage.StoragePosition;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.StatsType;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import io.github.swagree.repokefj.holder.guiholder.GuiMainHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public class PokemonFJListener implements Listener {

    public static final EnumSpecies[] UltraALL = new EnumSpecies[]{
            EnumSpecies.Buzzwole, EnumSpecies.Pheromosa, EnumSpecies.Xurkitree,
            EnumSpecies.Celesteela, EnumSpecies.Guzzlord, EnumSpecies.Kartana,
            EnumSpecies.Blacephalon, EnumSpecies.Poipole, EnumSpecies.Naganadel,
            EnumSpecies.Stakataka
    };



    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        InventoryHolder holder = event.getInventory().getHolder();

        if (!(event.getInventory().getHolder() instanceof GuiMainHolder)) {
            return;
        }
        event.setCancelled(true);
        GuiMainHolder guiMainHolder = (GuiMainHolder) holder;
        Player player = (Player) event.getWhoClicked();
        Pokemon pokemon = guiMainHolder.mapSlotToPokemon.get(event.getRawSlot());

        fjPokemon(player, pokemon);
        GuiMain.openGui(player);

    }

    private void fjPokemon(Player player, Pokemon pokemon) {
        Double totalPrice = calculatePokemonPrice(pokemon);
        String playerName = player.getName();

        List<String> commandList = Main.getPlugin().getConfig().getStringList("Command");
        for (String command : commandList) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", playerName).replace("%price%", totalPrice.toString()));
        }
        Pixelmon.storageManager.getParty(player.getUniqueId()).set(pokemon.getPosition(),null);
    }

    public Double calculatePokemonPrice(Pokemon pokemon) {
        String mainPath = "price";
        double totalPrice = 0;
        double basePrice = getPriceFromConfigByAttribute(mainPath + ".basePrice");
        double legendaryPrice = getPriceFromConfigByAttribute(mainPath + ".legendaryPrice");
        double ultraPrice = getPriceFromConfigByAttribute(mainPath + ".ultraPrice");
        double ivsPrice = getPriceFromConfigByAttribute(mainPath + ".ivsPrice");
        double evsPrice = getPriceFromConfigByAttribute(mainPath + ".evsPrice");
        double shinyPrice = getPriceFromConfigByAttribute(mainPath + ".shinyPrice");

        totalPrice = basePrice;

        if (pokemon.isLegendary()) {
            totalPrice += legendaryPrice;
        }
        for (EnumSpecies enumSpecies : UltraALL) {
            if (enumSpecies.getLocalizedName().equals(pokemon.getLocalizedName())) {
                totalPrice += ultraPrice;
                break;
            }
        }
        int totalIvs = getTotalV(pokemon);
        totalPrice += totalIvs * ivsPrice;

        int totalEvs = getTotalEvs(pokemon);
        totalPrice += totalEvs * evsPrice;

        if(pokemon.isShiny()){
            totalPrice += shinyPrice;
        }
        return totalPrice;


    }

    private double getPriceFromConfigByAttribute(String path) {
        return Main.getPlugin().getConfig().getDouble(path);
    }

    private int getTotalV(Pokemon pokemon) {
        int totalV = 0;
        if (pokemon.getIVs().getStat(StatsType.HP) == 31) totalV++;
        if (pokemon.getIVs().getStat(StatsType.Attack) == 31) totalV++;
        if (pokemon.getIVs().getStat(StatsType.SpecialAttack) == 31) totalV++;
        if (pokemon.getIVs().getStat(StatsType.Defence) == 31) totalV++;
        if (pokemon.getIVs().getStat(StatsType.SpecialDefence) == 31) totalV++;
        if (pokemon.getIVs().getStat(StatsType.Speed) == 31) totalV++;
        return totalV;
    }

    private int getTotalEvs(Pokemon pokemon) {
        int totalEvs = 0;
        if (pokemon.getEVs().getStat(StatsType.HP) == 252) totalEvs++;
        if (pokemon.getEVs().getStat(StatsType.Attack) == 252) totalEvs++;
        if (pokemon.getEVs().getStat(StatsType.SpecialAttack) == 252) totalEvs++;
        if (pokemon.getEVs().getStat(StatsType.Defence) == 252) totalEvs++;
        if (pokemon.getEVs().getStat(StatsType.SpecialDefence) == 252) totalEvs++;
        if (pokemon.getEVs().getStat(StatsType.Speed) == 252) totalEvs++;
        return totalEvs;
    }


}