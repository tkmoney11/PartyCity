
const onClick = function (event) {
    event.preventDefault();

    
        var test = document.cookie.indexOf('cookie_name=');;
        console.log(test);
        const xhr = new XMLHttpRequest();
        
        xhr.onreadystatechange = function () {
            console.log("readyState: " + this.readyState + "this.status" + this.status);
            if (this.readyState == 4 && this.status == 200) {
                console.log("status: " + this.status);
                let msg = xhr.responseText
                alert("Logout Successfully" );
                
                window.location.href = 'index.html';

            } else if (this.readyState == 4) {
                alert("Please try again.");
                window.location.href = 'Logout.html';
            }
        }

        xhr.open("POST", "http://18.222.140.73:8080/APIv2-0.0.1/LogoutServlet");
        xhr.send();
        
    

}

const submit = document.getElementById('user-submit');

// Event listener
submit.addEventListener('click', onClick)