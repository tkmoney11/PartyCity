
const placeRoom = function (id, hostId, gameType, user) {
    const tableBody = document.getElementById('cart-table-data');

    const entry = document.createElement('tr');

    entry.innerHTML =
        `<td>${id}</td>
        <td>${hostId}</td>
        <td>${gameType}</td>
        <td>${user}</td>`;


    tableBody.appendChild(entry);
}
export const getRoom = function () {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) {
            const data = JSON.parse(xhr.responseText);
            for (let i = 0; i < data.length; i++) {
                placeRoom(data[i].id, data[i].hostId, data[i].gameType, data[i].get(data[i].id).addUser(data[i].hostId));
            }  
        }  
    }
    xhr.open('GET', 'http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/all');
    xhr.onload = () => {
        console.log(xhr.response);
    };
    console.log("credentials: " + xhr.withCredentials);

    xhr.send(true);
}
getRoom();