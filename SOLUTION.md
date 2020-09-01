#Messaging exercise

https://git.cs.bham.ac.uk/dcu642/SWW-2017-2018/tree/master/Assignments/Assessed1

# SOLUTION

* When the client is started, it reads input from the user through the command line. The amount of lines it reads depends on the command entered

* When the client thread is started, an extra helper thread called ClientReceiver is started which gets messages from other clients via the server

* If the command is "register", it takes a further 2 inputs, the username and the password. If the user name is available, it registers the user by
adding the username to an arraylist storing only registered users, to a hashmap mapping the username to password and to another map that maps him to 
an arraylist where his messages will be stored. It also creates an action queue for the user, this queue can hold 2 types of commands, a "SEND_MESSAGE" 
command and a "LOGOUT" COMMAND which are defined in ServerSenderAction class. I decided to implement the password with password encryption as my 
optional feature, so when the password is registered, it is hashed before it is stored in the map.

* When a user is registered, he is NOT automatically logged in

* When the command is "login", a further 2 inputs are required, a password and a username. If the username has been registered, and the password 
matches the username, it logs the user in by adding the username to an arraylist that stores users that are currently logged in, it also 
displays the user's current message. Once a user is logged in on one machine, he will not be allowed to login on another machine until he logs out.

* When the command is "logout", it checks if the user is logged in first, if he is, the username is removed from the arraylist containing logged in users
and the "LOGOUT" command is added to the user's action queue. The threads serving this client are terminated. 

* When the command is "send", a further 2 inputs are required, the recipient's name and the message to be sent. If the recipient is a registered user, the message 
is  added to his arraylist of messages and this automatically makes this his current message. A "SEND MESSAGE" action is added to his action queue that
prints the message when he logs in. 

* When the command is "previous", a method is called in the "UserMesssages" class that attempts to reduce the user's current message index by 1
if possible, and returns a boolean indicating if it was successful or not. If the successfully reduced index remains within the user's message list's limits,
it displays the new current message to the user's screen (which was the previous message).

* When the command is "next", a method is called in the "UserMesssages" class that attempts to increase the user's current message index by 1
if possible, and returns a boolean indicating if it was successful or not. If the successfully increased index remains within the user's message list's limits,
it displays the new current message to the user's screen.

* When the command is "delete" a method is called in the "UserMessages" class that attempts to delete the user's current message from the message
list (if the message list is not empty). If this is successful, it makes the next message (if any) the current message. 
