package com.simplekc;

import net.runelite.client.config.*;

@ConfigGroup("kc")
public interface KcConfig extends Config
{
	@ConfigItem(
			position = 0,
			keyName = "npcIds",
			name = "NPC IDs",
			description = "Comma-separated list of NPC IDs to track"
	)
	default String npcIds()
	{
		return "";
	}

	@ConfigItem(
			position = 1,
			keyName = "npcNames",
			name = "NPC Names",
			description = "Comma-separated list of NPC names (case-insensitive) to track"
	)
	default String npcNames()
	{
		return "";
	}
}
