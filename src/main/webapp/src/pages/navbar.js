// Create a Navbar component template for injection

export const NavbarComponent = () =>`
<header>
    <nav id="navbar">
        <div class="my-container">
            <h1 class="logo"><img src="img/logo.png" alt="logo"><a href="index.html" ></a></h1>
            <ul>
                <li><a href="index.html" class="current">Home</a></li>
                <li><a href="create-room.html" >CreateRoom</a></li>
                <li><a href="view-room.html">ViewRoom</a></li>
                <li><a href="view-user.html">ViewUser</a></li>
                <li><a href="Login.html">Login</a></li>
                <li><a href="Logout.html">Logout</a></li>
            </ul>
        </div>
    </nav>
</header>`;

// var navbarHtml =
// `
//     <header>
//         <nav id="navbar">
//             <div class="my-container">
//                 <h1 class="logo"><img src="img/logo.png" alt="logo"><a href="index.html" ></a></h1>
//                 <ul>
//                     <li><a href="index.html" class="current">Home</a></li>
//                     <li><a href="create-room.html" >CreateRoom</a></li>
//                     <li><a href="view-room.html">ViewRoom</a></li>
//                     <li><a href="view-user.html">ViewUser</a></li>
//                     <li><a href="Login.html">Login</a></li>
//                     <li><a href="Logout.html">Logout</a></li>
//                 </ul>
//             </div>
//         </nav>
//     </header>`;
//     document.getElementById("navbar-container").innerHTML = navbarHtml;