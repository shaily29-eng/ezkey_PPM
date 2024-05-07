import Keycloak from "keycloak-js";

/*
  Init Options
*/
let initOptions = {
  url: "https://ezkeyauth.azurewebsites.net/",
  realm: "ezkey",
  clientId: "ezkey-fe",
};

let keycloak = new Keycloak(initOptions);

export default keycloak;
