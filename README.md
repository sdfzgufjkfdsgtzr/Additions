# Additions 0.4.5

## Commands
#### _Maintenance_
__Usage__: ```/service <on|off>```\
__Description__: Toggles the service mode for the server. When no argument was given the current status will be displayed.\
__Notes__: When the Maintenance mode is active the servers slot value will be set to ```0``` and just players with the ```add.server.service``` permission will be allowed to join. Everyone else will just be denied joining in.\
The Server will stay in maintenance mode as long as the value of ```startup.maintenance``` is ```true``` in the ```config.yml```. 

#### _Home_
__Usage__: ```/home <home>```\
__Description__: Teleports the player to his home location when it was set before\
__Notes__: If no argument is given, it will use the default home once it is set

#### _Sethome_
__Usage__: ```/sethome <home>```\
__Description__: Set the player's home. If an argument is given, it'll become the home's name\
__Notes__: The home location is editable in the plugin folder ```homes.yml``` once it is set

#### _SlimeNotifier_
__Usage__: ```/slimecheck```\
__Description__: Shows a message in chat whenever the player enters a chunk that allows Slimes to spawn naturally\
__Notes__: The default value is ```false``` and will be set everytime the player joins the server

#### _ChatAppearance_
__Usage__: ```/color <format_code> <format_code>```Example: ```/color a a```\
__Description__: Sets the display name and the chat color to the specified color codes.\
__Notes__: The first argument specifies the color code for the player's name, the second argument specifies the format code for the text messages the player will send.\
When no argument is given, the command will restore the default values specified in the ```config.yml```. The player's name is specified by the format code set at ```startup.prefix_color```, the messages format is specified by the format code set at ```startup.chat_color```.

## Events
#### _Sleep_
__Shoots when__: 1/3 of the server's players is laying in the bed\
__Description__: The time will be set to sunrise in the overworld\
__Notes__: Do not lay down in the nether ;)

#### _Chat_
__Shoots when__: A player is sending a message to chat\
__Description__: It is a custom chat format\
__Format__: ```{PREFIX_COLOR}{Playername}:{CHAT_COLOR}{Message}```

#### _ServerJoin_
__Shoots when__: A player is joining the server\
__Description__: It is a custom join message\
__Format__: ```{Playername} is now {LIGHT_GREEN}online```\
__Notes__: The format is fully customizable in the ```languages.yml``` 

#### _ServerLeave_
__Shoots when__: A player is leaving the server\
__Description__: It is a custom join message\
__Format__: ```{Playername} is now {LIGHT_RED}offline```\
__Notes__: The format is fully customizable in the ```languages.yml``` 

## Permissions

#### **Everything according to the server**
##### _Maintenance_
* add.server.service

#### **Everything according to the player**
##### _Home_
* add.player.home.teleport
* add.player.home.set
* add.player.home.set.multiple
##### _SlimeNotifier_
* add.player.chunkNotifier
##### _ChatAppearance_
* add.player.chat.appearance

#### **Everything according to the world**


## Translations
Feel free to add your own translations to the ```languages.yml``` or just ask me to add them to next version of it\
If you want to do it by yourself: copy the structure provided for German translations and replace ```de``` to your country code. Then exchange the country code in the ```config.yml``` and you'll be ready to go.

### Current available translations
* German
* English

### Translations to be added
* If you want your language to be added, let me know about it


## Todo
- [ ] Expand Home's functionality
  - [X] Multiple homes
  - [X] Delete homes
  - [X] optimize config file
  - [ ] add Home limitations
  - [ ] add Logger for changes
- [ ] make Sleep event configurable
- [ ] Add permissions system
- [ ] Add ticket systen
- [ ] Add Economy system
  - [ ] provide API for access by other developers
- [ ] optimize code
  - [ ] remove unnecessary fragments
  - [ ] add javadocs and comments
- [ ] add version checker
- [X] keep this page up-to-date
  
## Disclaimer
Please ensure you know that there are unforeseeable risks you may encounter resulting due to the plugin being a work-in-progress\
To avoid any inconvenience please backup your server files before using this plugin.\
If you encounter these special circumstances and you would like to avoid them the next time, please point out (by messaging me) as precisely as possible what you were doing and what other plugins you may have installed, which Spigot version you run etc.

I am not responsible for any inconvenience resulting due to the use of this plugin. Use on your own risk.  
  
## Want to support me?
[Click here](http://wwf.panda.org/)  
