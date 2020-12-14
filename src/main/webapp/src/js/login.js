
const onClick = function (event) {
    event.preventDefault();

    const userNameEle = document.getElementById('username');
    const userPasswordEle = document.getElementById('password');

    if (userNameEle.value) {
        const userObj = {
            username: userNameEle.value,
            password: userPasswordEle.value
        }

        var test = document.cookie.indexOf('cookie_name=');;
        console.log(test);
        const xhr = new XMLHttpRequest();
        
        xhr.onreadystatechange = function () {
            console.log("readyState: " + this.readyState + "this.status" + this.status);
            if (this.readyState == 4 && this.status == 200) {
                console.log("status: " + this.status);
                let msg = xhr.responseText
                alert("Login Successfully" );
                
                window.location.href = 'index.html';

            } else if (this.readyState == 4) {
                alert("Please try again.");
                window.location.href = 'Login.html';
            }
        }

        xhr.open("POST", "http://18.222.140.73:8080/APIv2-0.0.1/LoginServlet");
        xhr.send(JSON.stringify(userObj));
        
    } else {
        alert('Please enter a nonblank value for pirate name!');
    }

}

const submit = document.getElementById('user-submit');

// Event listener
submit.addEventListener('click', onClick)