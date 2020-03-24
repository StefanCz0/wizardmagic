package cz.stefan.minecraft.bukkit.plugin.magic.listeners;


import java.util.ArrayList;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.ZombieStriker.PluginConstructionAPI.HotbarMessager;

import cz.stefan.minecraft.bukkit.plugin.magic.objects.Spell;
import cz.stefan.minecraft.bukkit.plugin.magic.objects.Wand;

public abstract class WandListener implements Listener{
	protected Wand wand;
	public WandListener(Wand wand){
		this.wand=wand;
	}
	@EventHandler
	public void click(PlayerInteractEvent e) {
		if(e.hasItem()) {
			if(e.getPlayer().equals(wand.getOwner().getAsPlayer())) {
				if(e.getItem().equals(wand.asItemStack())) {
					if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
						if(!e.getPlayer().isSneaking()) {
							onRightClick( e.getPlayer());
							rightClick(e.getPlayer());
						}
					}
					if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
						if(e.getPlayer().isSneaking()) {
							shiftRight(e.getPlayer());
							shiftRightClick(e.getPlayer());
						}
					}
					if(e.getAction().equals(Action.LEFT_CLICK_AIR)) {
						onLeftClick();
						leftClick(e.getPlayer());
					}
				}
			}
		}
	}
	public void onLeftClick() {
		if(current==null) {
			wand.getOwner().getAsPlayer().sendMessage("You don't have spell selected");
		}else{
			current.run(wand.getOwner());
		}
	}
	public Spell current;
	public void onRightClick(Player p) {
		ArrayList<Spell> spells = wand.getOwnerSpells();
		HotbarMessager messager = new HotbarMessager();
		Spell spell = null;
		if(current==null) {
			current=spells.get(0);
		}
		for(int i = 0; i<spells.size();i++) {
			if(current.equals(spells.get(i))) {
				try {
					spell = spells.get(i+1);
					current = spell;
					break;
				}catch(IndexOutOfBoundsException exception) {
					current=spells.get(0);
				}
			}
		}
		
		
		try {
			messager.sendHotBarMessage(p, " ");
			messager.sendHotBarMessage(p, current.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void shiftRight(Player p) {
		Inventory inv= Bukkit.createInventory(null, 36, ChatColor.YELLOW+"Sellect spell");
		ArrayList<ItemStack> items = wand.spellsAsItem();
		for(int i = 0; i<items.size();i++) {
			inv.setItem(i, items.get(i));
		}
		p.openInventory(inv);
	}
	public abstract void rightClick(Player clicker);
	public abstract void leftClick(Player clicker);
	public abstract void shiftRightClick(Player clicker);
}
