# PartyCity
API for Revature

## Endpoints
### UserServlet/*

- /add - adds user to DB <br>
  - *__firstName__*: first name for user <br>
  - *__lastName__*: last name for user <br>
  - *__email__*: email for user <br>
  - *__username__*: username for user <br>
  - *__password__*: password for user <br> <br>
- /all - returns JSON mapping<String, User>. Unique userIds - Users<br> <br>
- /${id} - returns single JSON representation of a User<br>
  - *__id__*: userId to fetch <br> <br>
- /edit - edits specified user's password <br>
  - *__id__*: userId to edit <br>
  - *__newPassword__*: newPassword to enter <br><br>
- /delete - deletes user by id<br>
  - *__id__*: userId to delete <br><br>
  
###  RoomServlet/*
- /add - Adds room<br>
  - *__hostId__*: user id of user that wants to become host <br>
  - *__gameType__*: Name of the type of game you wish to play <br> <br>
- /all - returns JSON mapping<String, Room>. Unique roomIds - Rooms<br> <br>
- /${id} - returns single JSON representation of a Room <br>
  - *__roomId__*: userId to fetch <br> <br>
- /showByName - returns JSON mapping<String, Room> filtered by name.
  - *__name__*: *name* of user to filter rooms by <br> <br>
- /edit - edits host <br>
  - *__roomId__*: roomId to edit <br>
  - *__newHost__*: newHost to be <br><br>
- /delete <br>
  - *__roomId__*: roomId to delete <br>
  
