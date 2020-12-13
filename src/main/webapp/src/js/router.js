import {swapComponent} from "./componentInjector.js";
import {ShowcaseComponent} from "../pages/ShowcaseComponent.js";
import {ViewRoomComponent} from "../pages/ViewRoomComponent.js";
import {CreateRoomComponent} from "../pages/CreateRoom.js";
import {LoginComponent} from "../pages/LoginComponent.js";

// find a button with id = "home-button". 
// when this button is clicked,
// it will triger a call back function to inject showcase component
// Go back to ComponentInjector.js 
// when you vist other page, Navbar and footer won't change. 
// Andthe only thing changes will be the middle container because we swap new component to inject

export const setRoutes = () => {
    document.getElementById("home-button").addEventListener("click", () => {
        console.log("home button clicked");
        swapComponent(ShowcaseComponent());
    });
    document.getElementById("viewroom-button").addEventListener("click", () => {
        console.log("viewroom button clicked");
        swapComponent(ViewRoomComponent());
    })
    document.getElementById("createRoom-button").addEventListener("click", () => {
        swapComponent(CreateRoomComponent());
    })
    document.getElementById("login-button").addEventListener("click", ()=> {
        swapComponent(LoginComponent());
    })
};