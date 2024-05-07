import React from "react";
import { useKeycloak } from "@react-keycloak/web";
import Sidebar from "./Sidebar/Sidebar";
import "./Nav.css";

const Nav = () => {
  //   const { keycloak, initialized } = useKeycloak();
  const { keycloak } = useKeycloak();

  return (
    <div>
      <div className="top-0 w-full flex flex-wrap">
        <section className="x-auto">
          <nav className="flex justify-between bg-gray-200 text-blue-800 w-screen">
            <div className="px-5 xl:px-12 py-6 flex w-full items-center">
              <ul className="hidden md:flex px-4 mx-auto font-semibold font-heading space-x-12"></ul>
              <div className="hidden xl:flex items-center space-x-5">
                <div className="hover:text-gray-200">
                  {!keycloak.authenticated && (
                    <div className="topbar">
                      <Sidebar className="foldablemenu" />
                      <p className="hellouser">Hello, guest</p>
                      <button
                        type="button"
                        className="login"
                        onClick={() => keycloak.login()}
                      >
                        Login
                      </button>
                    </div>
                  )}

                  {!!keycloak.authenticated && (
                    <div className="topbar">
                      <Sidebar className="foldablemenu" />
                      <p className="hellouser">
                        Hello,{" "}
                        {keycloak.tokenParsed.preferred_username
                          .charAt(0)
                          .toUpperCase() +
                          keycloak.tokenParsed.preferred_username.slice(1)}
                      </p>
                      <button
                        type="button"
                        className="login"
                        onClick={() => keycloak.logout()}
                      >
                        Logout
                      </button>
                    </div>
                  )}
                </div>
              </div>
            </div>
          </nav>
        </section>
      </div>
    </div>
  );
};

export default Nav;