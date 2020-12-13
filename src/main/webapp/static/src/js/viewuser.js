const placeRoom = function (id, firstName, lastName, email, username, password,administrator) {
    const tableBody = document.getElementById('cart-table-data');

    const entry = document.createElement('tr');

    entry.innerHTML =
        `<td>${id}</td>
        <td>${firstName}</td>
        <td>${lastName}</td>
        <td>${email}</td>
        <td>${username}</td>
        <td>${password}</td>
        <td>${administrator}</td>`;


    tableBody.appendChild(entry);
}


const getUser = function () {

    const xhr = new XMLHttpRequest();
    // axios.get('http://18.222.140.73:8080/APIv2-0.0.1/RoomServlet/all',{withCredentials: true})
    // .then(function(res){console.log(res)})
    
    xhr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {

            const data = JSON.parse(xhr.responseText);
            console.log("hi"+Object.keys(data).length);
            for (let i = 1; i < Object.keys(data).length; i++) {
                // console.log("hi");
                // console.log(data[i].id, data[i].firstName, data[i].lastName, data[i].email, data[i].username, data[i].password, data[i].administrator);
                placeRoom(data[i].id, data[i].firstName, data[i].lastName, data[i].email, data[i].username, data[i].password, data[i].administrator);
            }

            console.log("Room information received");
        }

    }
    xhr.open('GET', 'http://18.222.140.73:8080/APIv2-0.0.1/UserServlet/all');
    
    console.log("credentials: " + xhr.withCredentials);
    xhr.withCredentials = true;
    xhr.send();
    
}
getUser();