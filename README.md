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
__Notes__: The home location is editable in the plugin folder ```/Additions/homes.yml``` once they are set

#### _SlimeNotifier_
__Usage__: ```/slimecheck```\
__Description__: Shows a message in chat whenever the player enters a chunk that allows Slimes to spawn naturally\
__Notes__: The default value is ```false``` and will be set everytime the player joins the server

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
Feel free to add your own translations to the ```languages.yml``` or just ask me to add them to next version of it

### Current available translations
* German

### Translations that will be added
* English


## Todo
* Expand Home's functionality
  * Multiple homes
  * Delete homes
* Add permissions system
* Add Economy system
  * provide API for access by other developers
* optimize code
  * remove unnecessary fragments
  * add javadocs and comments
