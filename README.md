# NovyXtreme

**NovyXtreme** is a re-make of the classic WormholeXtreme plugin, designed for use on Novylen - the oldest Minecraft server. This plugin allows players to create and use obsidian "Stargates" to teleport between locations across the server.

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
  ## Stargate Creation
  Creating a stargate is easy, first, create this structure in your world
  - (Note: There is a two block gap between the stargate and the lever pedestal).

  ![stargate-structure](Screenshots/Stargate-Structure-Default.png)

  Next, Pull the lever, you should recieve this message in chat:
  - (Note: The stargate creation cost is configurable; 0 by default)

  ![stargate-create-message](Screenshots/Stargate-Create-Message.png)

Use the command /nxcomplete [gatename] to create the stargate.
- If successful, you should see a sign appear on the stargate and receieve this message in chat
  ![stargate-create-success-message](Screenshots/Stargate-Create-Success-Message.png)

Congratulations! You've made your first stargate.

## Using Stargates
To activate a stargate, pull the lever, the stargate should "activate":
- (Note: Activated stargates have a 60 second timeout by default)

![active-stargate](Screenshots/Active-Stargate.png)

use /dial [gatename] to create a portal between this gate and the provided gate.

![connected-stargate](Screenshots/Connected-Stargate.png)



  ### Planned Features
  - [ ] Configurable custom gate shapes
  - [ ] /nxnear find nearby gate(s)
  - [ ] Ability to "Lock" stargates
  - [ ] Stargate Networks
  
  
