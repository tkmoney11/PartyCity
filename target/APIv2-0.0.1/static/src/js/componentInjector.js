import { NavbarComponent } from "../pages/navbar.js";
import { ShowcaseComponent } from "../pages/ShowcaseComponent.js";
import { FooterComponent } from "../pages/FooterComponent.js";
import { setRoutes } from "./router.js";

// component is the html string that should be injected, target is the id of the intended parent element
const injectHtml = (component, target = "container") => {
    document.getElementById(target).innerHTML += component;
    // +=
    // x = x + y
}

//this function will swap the first component for the second component
// note if we were to change the implementation for injectHtml function
// we could probably do this in way that performed better
export const swapComponent = (newComponent, target ="container") =>{
    document.getElementById(target).innerHTML = newComponent;
}


// this function is meant to render the view of the user as they visit the page for the first time
const renderLanding = () =>{
    injectHtml(NavbarComponent(),"navbar-container");
    injectHtml(ShowcaseComponent());
    injectHtml(FooterComponent(),"footer-container");
}


setRoutes();
renderLanding();
