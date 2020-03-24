package cz.stefan.minecraft.bukkit.plugin.magic.objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import cz.stefan.minecraft.bukkit.plugin.magic.enums.SpellBlockEffect;
import cz.stefan.minecraft.bukkit.plugin.magic.enums.SpellEffect;
import cz.stefan.minecraft.bukkit.plugin.magic.enums.SpellEntityEffect;
import cz.stefan.minecraft.bukkit.plugin.magic.magician.Magician;


public class Spell {
	protected Entity interacted;
	protected SpellEffect effect;
	protected SpellEntityEffect eneffect;
	protected SpellBlockEffect boeffect;
	protected Block block;
	protected Entity entity;
	protected int mana_cost;
	protected int value = 0;
	protected String name;
	protected SpellEffect effect2;
	protected Particle particle;
	protected Location loc;
	protected Material material;
	public Spell(String name/**@param name*/, SpellEntityEffect eneffect/**@param If you use entity let this on*/,Entity entity/**@param Entity that do effect*/,int mana_cost,Particle particle) {
		this.name=name;
		this.eneffect=eneffect;
		this.mana_cost=mana_cost;
		this.entity=entity;
		this.particle=particle;
	}
	public Spell(String name, SpellBlockEffect boeffect,Block block,int mana_cost,Particle particle) {
		this.name=name;
		this.boeffect=boeffect;
		this.mana_cost=mana_cost;
		this.block=block;
		this.block=block;
		this.particle=particle;
	}

	public Spell(String name, SpellBlockEffect boeffect,Location loc,int mana_cost,Particle particle) {
		this.name=name;
		this.boeffect=boeffect;
		this.mana_cost=mana_cost;
		this.loc=loc;
		this.particle=particle;
	}

	public Spell(String name,Material material, SpellEffect effect, int mana_cost,Particle particle) {
		this.name=name;
		this.material=material;
		this.effect=effect;
		this.mana_cost=mana_cost;
		this.particle=particle;
	}

	public Spell(String name,Entity interacted, SpellEffect effect,int value, int mana_cost,Particle particle) {
		this.name=name;
		this.interacted=interacted;
		this.effect=effect;
		this.mana_cost=mana_cost;
		this.particle=particle;
	}
	public Spell(String name,Entity interacted, SpellEffect effect,int mana_cost, SpellEffect effect2,Particle particle) {
		this.name=name;
		this.interacted=interacted;
		this.effect=effect;
		this.mana_cost=mana_cost;
		this.effect2=effect2;
		this.particle=particle;
	}
	public Spell(String name,Entity interacted,int mana_cost, Particle particle) {
		this.name=name;
		this.interacted=interacted;
		this.mana_cost=mana_cost;
		this.particle=particle;
	}

	public void run(Magician mage)/** @param runs spell*/ {
		mage.getAsPlayer().sendMessage(ChatColor.LIGHT_PURPLE+"Used spell: "+ChatColor.RESET+name);
		if(mage.getMana()<mana_cost) {
			mage.getAsPlayer().sendMessage("Not enough mana");
		} else {
			if(isNotNull(interacted)) {

				LivingEntity en = (LivingEntity) interacted;
				if(mana_cost>100) {
					mana_cost=100;
				}

				//classic effect
				if(isNotNull(effect)) {
					if(effect.equals(SpellEffect.heal)) {
						en.resetMaxHealth();
						en.getWorld().spawnParticle(particle, en.getLocation(), 10);
					}
					if(effect.equals(SpellEffect.fire)) {
						if(isNotNull(value)) {
							en.setFireTicks(value);
						} else {
							en.setFireTicks(400);
						}
					}
					if(effect.equals(SpellEffect.lightning)) {
						en.resetMaxHealth();
					}
					if(effect.equals(SpellEffect.demage)) {
						if(isNotNull(value)) {
							if(en.getHealth()<value) {
								en.setHealth(0);
							} else {
								en.setHealth(en.getHealth()-value);
							}
						}
					}
				}
			}
			if(isNotNull(entity)) {
				//entity effect
				LivingEntity en = (LivingEntity)entity;
				if(isNotNull(eneffect)) {
					if(eneffect.equals(SpellEntityEffect.demage)) {
						if(isNotNull(value)) {
							if(en.getHealth()<value) {
								en.setHealth(0);
							} else {
								en.setHealth(en.getHealth()-value);
							}
						}

					}
					if(eneffect.equals(SpellEntityEffect.heal)) {
						en.resetMaxHealth();
					}
					if(eneffect.equals(SpellEntityEffect.kill)) {
						en.setHealth(0);
					}
				}
			}


			//block effect
			if(isNotNull(boeffect)) {
				if(boeffect.equals(SpellBlockEffect.place)) {
					Block block = loc.getWorld().getBlockAt(loc);
					block.setType(material);
				}
				if(boeffect.equals(SpellBlockEffect.destroy)) {
					loc.getBlock().breakNaturally();
				}
			}

		}
	}

	protected boolean isNotNull(Object obj) {
		if(obj==null) {
			return false;
		} else {
			return true;
		}
	}
	public int getManaCost() {
		return mana_cost;
	}
	public String getName() {
		return name;
	}
	public ItemStack asItem() {
		ItemStack stack = new ItemStack(Material.PAPER);
		ItemMeta meta =stack.getItemMeta();
		meta.setDisplayName(ChatColor.DARK_PURPLE+"Spell");
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.LIGHT_PURPLE+name);
		meta.setLore(list);
		stack.setItemMeta(meta);
		return stack;
	}
}
