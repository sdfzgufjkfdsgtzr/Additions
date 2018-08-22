# Additions

## Commands
#### _Maintenance_
__Usage__: ```/service <on|off>```\
__Description__: Toggles the service mode for the server. When no argument was given the current status will be displayed.
__Notes__: When the Maintenance mode is active the servers slot value will be set to ```0``` and just players with the ```add.server.service``` permission will be allowed to join. Everyone else will just be denied joining in.\
The Server will stay in maintenance mode as long as the value of ```startup.maintenance``` is ```true``` in the ```config.yml```. 

#### _Home_
__Usage__: ```/home <set>```\
__Description__: Teleports the player to his home location when it was set before\
__Notes__: The home location is editable in the plugin folder ```homes.yml``` once they are set

#### _SlimeNotifier_
__Usage__: ```/slimecheck```\
__Description__: Shows a message in chat whenever the player enters a chunk that allows Slimes to spawn naturally\
__Notes__: The default value is ```false``` and will be set everytime the player joins the server

#### _ChatAppearance_
__Usage__: ```/color <color_code> <color_code>```\
__Description__: Sets the display name and the chat color to the specified color codes.\
__Notes__: The first argument specifies the color code for the player's name, the second argument specifies the format code for the text messagees the player will send.\When no argument is given, the command will restore the default values specified in the ```config.yml```. The player's name is specified by the format code set at ```startup.prefix_color```, the messages format is specified by the format code set at ```startup.chat_color```.

## Permissions

#### **Everything according to the server**
##### _Maintenance_
* add.server.service

#### **Everything according to the player**
##### _Home_
* add.player.home
##### _SlimeNotifier_
* add.player.chunkNotifier
##### _ChatAppearance_
* add.player.chat.appearance

#### **Everything according to the world**


## Translations
Feel free to add your own translations to the ```languages.yml``` or just ask me to add them to next version of it\
When you do it by yourself: copy the structure provides for German translations and replace ```de``` to your country code. Then exchange the country code in the ```config.yml``` and you'll be ready to go.

### Current available translations
* German

### Translations that will be added
* English


## Todo
- [ ] Expand Home's functionality
  - [ ] Multiple homes
  - [ ] Delete homes
- [ ] Add permissions system
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
