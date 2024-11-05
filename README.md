# NovyXtreme

**NovyXtreme** is a re-make of the classic WormholeXtreme plugin, designed for use on Novylen—the oldest Minecraft server. This plugin allows players to create and use obsidian "Stargates" to teleport between locations across the server.

## Features

- **Obsidian Stargates**: Players can build Stargates from obsidian structures, activate them, and use them to teleport between different Stargate locations.
- **Vault Integration**: Connects to Vault to manage in-game currency for Stargate usage.
- **Customizable Options**: Set Stargate creation costs, activation timeouts, and other configurable options via the config file.

## Commands

- `/nxcomplete <stargate-name>`  
  Completes a new Stargate and deducts the configurable cost from the player's account on successful creation (requires an active gate).

- `/dial <stargate-name>`  
  Creates a portal from the activated Stargate to a different Stargate.

- `/nxgo <stargate-name>`  
  Teleports the player to the specified Stargate (does not require a gate to be activated).

- `/nxremove <stargate-name>`  
  Removes the specified Stargate.

- `/nxlist <player-name> (optional) -v (optional)`  
  Lists all Stargates, with optional filtering by player name and a verbose output flag.

- `/nxreload`  
  Reloads the NovyXtreme plugin.

## Stargate Creation Guide
