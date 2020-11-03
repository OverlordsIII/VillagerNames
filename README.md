# VillagerNames
You can find the latest releases of this mod [here]https://www.curseforge.com/minecraft/mc-mods/villager-names-for-fabric/
## Overview
When we are playing Minecraft, we are presented to a very large amount of Mobs. Most of them are either passive or aggressive. But only two can be considered your allies. The tamed wolves and the Villagers (No, cats doesn't count. They really don't care about you).

Villagers are very useful, if not a bit dumb sometimes. We rely on them for their trades, for our automated crops and Iron Farms. But they don't have the recognition they deserve. Of course, you can name them with nametags, but is it REALISTIC? Is it IMMERSIVE?? NO!

The scope of this mod is to bring more life into your game. Every villager that spawn in your world would be given a name randomly, from a list of over 6000 names. And you can change the names to be whatever you want.
This way, every one of those big noses will be a person, not a thing. And you will feel bad when shoving them in lava because you don't like their trades. 

 

This eventually will be part of a bigger groups of Mods, designed to work both together and individually. So, if you like this mod, keep your eyes open for more stuff coming in the future.

**Please note that the config requires a restart on the server to apply changes in it.**

## Showcase Video:
[![VillagerNames](https://res.cloudinary.com/marcomontalbano/image/upload/v1604353086/video_to_markdown/images/youtube--wcvzFe5mqag-c05b58ac6eb4c4700831b2b3070cd403.jpg)](https://www.youtube.com/watch?v=wcvzFe5mqag&feature=youtu.be "VillagerNames")
## Features:
 * Adds names to villagers when your server boots up for the first time
 * Adds the villager's profession to the villager's names when they get a profession (config enabled)
 * Adds a " the Child" tag to children (config enabled)
 * Adds robot names to iron golems (config enabled)
 * Allows you to format the villager names into different text styles (config enabled)
 * Allows you to customize the added villager text for a nitwit (we know they are useless. Config enabled)
 * Provides a command system for dedicated servers and a modmenu screen for integrated (singleplayer) ones
 * Removes the profession name from a villager when it turns into a zombie and gives them the Zombie tag
 * When converting back from a zombie it will change back to a normal villager + normal villager text
 * Shuts the console up when a villager dies so you can do whatever you want to them without mc screaming at you (config enabled)
 * Allows you to customize the text for a wandering trader (config enabled)
## Command Tree
This mod also allows you to use commands to customize it
the root command is `/villagername`
 * toggle
    * professionNames - Toggles villagers having added professions in their names. Default true
    * golemNames - Toggles names for golems. Default true
    * needsOP - Enables commands only for OP's. Default false
    * childNames - Toggles children villagers having the added  "the Child" tag in their name. Default false
    * turnOffConsoleSpam - Toggles console spam off when you kill a villager. Default true
    * wanderingTraderNames - Toggles names added for the wanderingTrader
 * add
    * villagerNames (name) - Adds a name to the villagerNames list
    * golemNames (name) - Adds a name to the golemNames list
 * remove
    * villagerNames (name) - Removes a name from the villagerNames list
    * golemNames (name) - Removes a name from the golemNames list
 * set
    * nitwitText (text) - Sets the added text for a nitwit villager. Defaults to 'the Nitwit'. Will always be prefixed by 'the'
    * wanderingTraderText (text) - Sets the added text for a Wandering Trader. Defaults to 'the Wandering Trader'. Will always be prefixed by 'the'
    * villagerTextFormatting (formatting) - Sets the villager text to the following formatting. Defaults to WHITE. Specific formattings can be found [here](https://github.com/OverlordsIII/VillagerNames/blob/master/src/main/java/io/github/overlordsiii/villagernames/config/FormattingDummy.java) or can be found by the suggestions in the commands.
 * info
    - sends the player all the current config values
    
**Please note that some of these commands require a server restart to apply**

