# Simple KC Stats
A RuneLite plugin that tracks NPC kill counts and provides timing statistics.

## Features
- Track kills for specific NPCs by ID or name
- Real-time kill count display
- Kills per hour calculation
- Average time per kill statistics
- Configurable NPC tracking through both IDs and names

## Configuration
The plugin can be configured through the RuneLite settings panel:

- **NPC IDs**: Enter a comma-separated list of NPC IDs to track
- **NPC Names**: Enter a comma-separated list of NPC names to track (case-insensitive)

## Display
The overlay shows:
- Total kill count
- Kills per hour
- Average time per kill (in MM:SS format)

## Example Usage
To track specific bosses or monsters, either:
1. Add their NPC IDs to the configuration (e.g., "4277" for the western Draynor jail guard)
2. Add their names to the configuration (e.g., "Zulrah,Vorkath")

The overlay will automatically update as you defeat the specified NPCs.

## Note
- Kill tracking starts from your first kill after enabling the plugin
- Statistics reset when the plugin is disabled or RuneLite is restarted