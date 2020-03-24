package cz.stefan.minecraft.bukkit.plugin.magic.objects;

import java.util.ArrayList;
import java.util.LinkedList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import cz.stefan.minecraft.bukkit.plugin.magic.listeners.DefaultWandListener;
import cz.stefan.minecraft.bukkit.plugin.magic.listeners.WandListener;
import cz.stefan.minecraft.bukkit.plugin.magic.magician.Magician;
import cz.stefan.minecraft.bukkit.plugin.magic.main.APImain;

public class Wand {
	protected APImain main;
	protected Magician owner;
	protected ItemStack item;
	public DefaultWandListener listener = new DefaultWandListener(this);
	public Wand(Magician owner, ItemStack item, APImain main) {
		this.owner=owner;
		this.item=item;
		this.main=main;
		main.getServer().getPluginManager().registerEvents(this.listener, main);
	}
	
	public void runSpell(Spell spell) {
		spell.run(owner);
	}
	public Magician getOwner() {
		return owner;
	}
	public ArrayList<Spell> getOwnerSpells() {
		return owner.getSpells();
	}
	public ArrayList<ItemStack> spellsAsItem() {
		ArrayList<ItemStack> sfa = new ArrayList<ItemStack>();
		for(int i = 0; i<getOwnerSpells().size();i++) {
			if(getOwnerSpells().get(i)!=null) {
				sfa.add(getOwnerSpells().get(i).asItem());
			}
		}
		return sfa;

	}
	public ItemStack asItemStack() {
		return item;
	}
	public void registerCostumeListener(WandListener wl) {
		WandListener lis = wl;
		main.getServer().getPluginManager().registerEvents(lis, main);
	}


}
