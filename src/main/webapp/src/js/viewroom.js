
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

const xhr = new XMLHttpRequest();
console.log("credentials: " + xhr.withCredentials);
xhr.withCredentials = true;
const getRoom = function () {
console.log(sessionStorage.key(0));
    
    // axios.get('http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/all',{withCredentials: true})
    // .then(function(res){console.log(res)})
   
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            const data = JSON.parse(xhr.responseText);
            
            console.log("3 " + Object.keys(data).length);
            const keyLength = Object.keys(data).length -1;
            console.log(typeof keyLength);
            
            
            length = data[Object.keys(data).pop()].id
            console.log(length);
            for (let i = 1 ; i <= length;i++) {
                if((data[i]) != null ){
                    placeRoom(data[i].id, data[i].hostId, data[i].game, data[i].users);   
                }      
            }
           

            console.log("Room information received");
        }

    }
    xhr.open('GET', 'http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/all');
    
    console.log("credentials: " + xhr.withCredentials);
   
    xhr.send();
    
}
getRoom();

// let thetable = document.getElementById('tableMain').getElementsByTagName('tbody');
// console.log("this is from table.row: " +thetable.rows.length)
// for (let i = 0; i < thetable.rows.length; i++) {
//     thetable.rows[i].onclick = function () {
//         TableRowClick(this);
//     };
// }

// function TableRowClick(therow) {
//     console.log(therow.cells[1].innerHTML);
    
//     window.location.href = 'find-room-by-username.html';
// };
