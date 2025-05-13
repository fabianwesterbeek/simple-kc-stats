package com.simplekc;

import com.google.inject.Provides;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

@PluginDescriptor(
		name = "Kill Tracker"
)
public class KcPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private KcConfig config;
	@Inject private OverlayManager overlayManager;
	@Inject private KcOverlay overlay;

	private Set<Integer> trackedIds;
	private Set<String> trackedNames;
	private int kills;
	private long startTime;

	@Override
	protected void startUp() throws Exception
	{
		kills = 0;
		startTime = System.currentTimeMillis();
		buildTracked();
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged ev)
	{
		if (!ev.getGroup().equals("kc"))
		{
			return;
		}
		buildTracked();
	}

	private void buildTracked()
	{
		// parse IDs
		trackedIds = Text.fromCSV(config.npcIds())
				.stream()
				.map(String::trim)
				.filter(s -> !s.isEmpty())
				.map(Integer::valueOf)
				.collect(Collectors.toSet());

		// parse names
		trackedNames = Text.fromCSV(config.npcNames())
				.stream()
				.map(String::trim)
				.map(String::toLowerCase)
				.filter(s -> !s.isEmpty())
				.collect(Collectors.toSet());
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		NPC npc = event.getNpc();
		if (!npc.isDead())
		{
			return;
		}

		boolean idMatch = trackedIds.contains(npc.getId());
		String name = npc.getName();
		boolean nameMatch = name != null && trackedNames.contains(name.toLowerCase());

		if (idMatch || nameMatch)
		{
			if (kills++ == 0)
			{
				startTime = System.currentTimeMillis();
			}
			// overlay will update on next frame
		}
	}

	public int getKills()
	{
		return kills;
	}

	public long getStartTime()
	{
		return startTime;
	}

	@Provides
	KcConfig provideConfig(ConfigManager manager)
	{
		return manager.getConfig(KcConfig.class);
	}
}
