const onClick = function (event) {
    event.preventDefault();
    const hostId = document.getElementById('hostId');
    const gameType = document.getElementById('gametype');
    
    if (hostId.value) {
        const obj = {
            hostId: hostId.value,
            game: gameType.value
        };
        console.log(hostId+gameType);
        const xhr = new XMLHttpRequest();
       
        

        xhr.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                console.log("Room onload!");
                alert("Success");
            } else if (this.readyState == 4) {
                alert('Encountered an error when attempting to add a pirate!');
            }
        }
        xhr.open("POST", "http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/create",true);
        xhr.onload = () => {
            console.log(xhr.response);
        };
        xhr.send(JSON.stringify(obj));
    } else {
        alert('Please enter a nonblank value for pirate name!');
    }
}
const submit = document.getElementById('pirate-submit');

// Event listener
submit.addEventListener('click', onClick);