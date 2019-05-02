package me.michau.weather;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.michau.weather.utils.ChatUtil;

public class Main extends JavaPlugin implements Listener {
	
	public HashMap<String, Integer>mapka;
	String burza2;
	String slonce2;
	String deszcz2;
	boolean burza3 = false;
	boolean deszcz3 = false;
	boolean slonce3 = false;
	String pogoda;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[M-Weather] " + ChatColor.RED + "Plugin Uruchomiono pomyslnie! " + ChatColor.DARK_GREEN + "Autor: vMichau_");
	}
	
	@EventHandler
	public void PadajKurwo(WeatherChangeEvent e) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
			@Override
			public void run() {
				if(!burza3) {
					if(e.getWorld().isThundering()) {
						int burza1 = new Random().nextInt(getConfig().getInt("burza1")) + (getConfig().getInt("burza2"));
						burza2 = String.valueOf(burza1);
						burza3 = true;
						slonce3 = false;
						deszcz3 = false;
						pogoda = "&9Burza";
					}
				}
				if(!slonce3) {
					if(e.getWorld().isThundering() == false) {
						int slonce1 = new Random().nextInt(getConfig().getInt("slonce1")) + (getConfig().getInt("slonce2"));
						slonce2 = String.valueOf(slonce1);
						burza3 = false;
						slonce3 = true;
						deszcz3 = false;
						pogoda = "&eSlonecznie";
					} else if (e.getWorld().hasStorm()) {
						return;
					}
				}
				if(!deszcz3) {
					if(e.getWorld().hasStorm()) {
						int deszcz1 = new Random().nextInt(getConfig().getInt("deszcz1")) + (getConfig().getInt("deszcz2"));
						deszcz2 = String.valueOf(deszcz1);
						burza3 = false;
						slonce3 = false;
						deszcz3 = true;
						pogoda = "&bDeszczowo";
					} else if (e.getWorld().hasStorm() == false) {
						return;
					}
				}
			}
			
		}, 1);
	}
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("temperatura")) {
			if(burza3) {
				cs.sendMessage(ChatUtil.fixColor("&eObecna temperatura: ") + burza2);
				cs.sendMessage(ChatUtil.fixColor("&6Obecna pogoda: ") + ChatUtil.fixColor(pogoda));
				return true;
			}
			if(slonce3) {
				cs.sendMessage(ChatUtil.fixColor("&eObecna temperatura: ") + slonce2);
				cs.sendMessage(ChatUtil.fixColor("&6Obecna pogoda: ") + ChatUtil.fixColor(pogoda));
				return true;
			}
			if(deszcz3) {
				cs.sendMessage(ChatUtil.fixColor("&eObecna temperatura: ") + deszcz2);
				cs.sendMessage(ChatUtil.fixColor("&6Obecna pogoda: ") + ChatUtil.fixColor(pogoda));
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
