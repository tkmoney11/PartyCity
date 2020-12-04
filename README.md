# PartyCity
API for Revature
http://18.222.140.73:8080/APIv2-0.0.1/

## Endpoints
### LoginServlet/*
## POST
  - *__username__*: username <br>
  - *__password__*: password <br> <br>

#### Possible Return Messages:
```
YOU LOGGED IN as a User
```
Status Code: 200
```
INCORRECT LOGIN
```
Status Code: 500
<br>
  

### UserServlet/*
## GET
- /${id} - __returns User__ (JSON)<br>
  - *__id__*: PARAMETER: userId to fetch <br> <br>

#### Succesful Example Return Value:
```
    {
      "id": 2,
      "firstName": "Taylor",
      "lastName": "IsABoss",
      "email": "test1@gmail.com",
      "username": "tko",
      "password": "password1",
      "administrator": true
    }
```
- /all - __returns Map of "id":Users__ (JSON)
<br> <br>

## POST
- /add - BODY: adds user to DB.  __returns User__ (JSON)<br>
  - *__firstName__*: first name for user <br>
  - *__lastName__*: last name for user <br>
  - *__email__*: email for user <br>
  - *__username__*: username for user <br>
  - *__password__*: password for user <br> <br>


## PUT
- /editPass - edits specified user's password. __returns User__ (JSON) <br>
  - *__id__*: userId to edit <br>
  - *__newPassword__*: newPassword to enter <br>
```
note: will not authorize editing unless user has administrator status or is the current logged in user.
```
- /flipAdmin - flips specified user's administration privileges. __returns User__ (JSON) <br>
  - *__id__*: userId to edit <br><br>
  
## DELETE
- /delete - deletes user by id __returns success message__<br>
  - *__id__*: userId to delete <br><br>
  
###  RoomServlet/*  
## GET
- /${id} - __returns Room__ (JSON) <br>
  - *__id__*: roomId to fetch <br> <br>
#### Succesful Example Return Value:
```
    {
      "id": 2,
      "firstName": "Taylor",
      "lastName": "IsABoss",
      "email": "test1@gmail.com",
      "username": "tko",
      "password": "password1",
      "administrator": true
    }
```
- /showByUsername - __returns Room__ (JSON)
  - *__username__*: username to fetch <br> <br>
- /all - __returns Map of "id":Room__ (JSON)<br> <br>
## POST
- /create - Adds room __returns Room__<br>
  - *__hostId__*: user id of user that will become host <br>
  - *__game__*: Name of the type of game you wish to play <br> <br>

## PUT
- /editHost - edits host __returns Room__ <br>
  - *__roomId__*: roomId to edit <br>
  - *__newHostId__*: userId of new host to be <br><br>
- /addUser - Adds user to a room __returns Room__<br>
  - *__roomId__*: roomId <br>
  - *__userId__*: userId that will join the room <br> <br>

## DELETE
- /deleteRoom - deletes room. __returns success or failure message__
  - *__roomId__*: roomId to delete <br>
- /removeUser - edits host __returns Room__ <br>
  - *__roomId__*: roomId of user you want to remove<br>
  - *__userId__*: user to remove
  ```
  note: currently will return success remove user even if user was never present in room to begin with.
  ```
  
  
  this is a test
