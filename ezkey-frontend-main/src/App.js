import React from "react";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./Keycloak";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Nav from "./components/Nav";
import HomePage from "./components/Homepage/Homepage";
import DashboardPage from "./components/Dashboardpage/Dashboardpage";
import PrivateRoute from "./components/helpers/PrivateRoute";
import ProjectForm from "./components/ProjectForm/ProjectForm";
import Projectpage from "./components/Projectpage/Projectpage";
import "./App.css";

function App() {
  return (
    <div>
      <ReactKeycloakProvider authClient={keycloak}>
        <BrowserRouter>
          <Nav />
          <Routes>
            <Route exact path="/" element={<HomePage />} />
            <Route
              path="/dashboard"
              element={
                <PrivateRoute>
                  <DashboardPage />
                </PrivateRoute>
              }
            />
            <Route
              path="/projects/:projectId"
              element={
                <PrivateRoute>
                  <Projectpage />
                </PrivateRoute>
              }
            />
            <Route
              path="/ProjectForm"
              element={
                <PrivateRoute>
                  <ProjectForm />
                </PrivateRoute>
              }
            />
          </Routes>
        </BrowserRouter>
        <footer>&copy; Copyright 2024 PPM</footer>
      </ReactKeycloakProvider>
    </div>
  );
}

export default App;