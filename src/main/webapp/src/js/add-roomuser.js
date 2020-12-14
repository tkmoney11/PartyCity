
const onClick = function(event) {
    event.preventDefault();
    const roomID = document.getElementById('roomID');
    var roomiD = parseInt(roomID.value, 10);
    const yourID = document.getElementById('yourID');
    var youriD = parseInt(yourID.value, 10);
    if (roomID.value && yourID.value) {
        const objid = {
            roomId : roomiD,
            userId : youriD

        };
        
        const xhr = new XMLHttpRequest();

        xhr.withCredentials = true;
        
        xhr.withCredentials=true;
        console.log("credentials: " + xhr.withCredentials);

        xhr.onreadystatechange = function () {
            console.log("readyState: " + this.readyState + "this.status" + this.status);
            if (this.readyState == 4 && this.status == 200) {
                console.log("Room onload!");
                
            } else if (this.readyState == 4) {
                alert('Failed');
            }
        }
        xhr.open("PUT", "http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/addUser");
        xhr.onload = () => {
            console.log(xhr.response);
        };
        xhr.send(JSON.stringify(objid));
    }else {
        alert('Please enter a nonblank value for pirate name!');
    }
} 
const submit = document.getElementById('pirate-submit');

// Event listener
submit.addEventListener('click', onClick)