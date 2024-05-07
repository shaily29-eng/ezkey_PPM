import React, { useState } from "react";
import "./Dashboardpage.css";
import axios from "axios";
import { useKeycloak } from "@react-keycloak/web";
import { Link } from "react-router-dom";

const colors = [
  "#ff7675",
  "#fab1a0",
  "#ffeaa7",
  "#00b894",
  "#00cec9",
  "#0984e3",
  "#6c5ce7",
  "#fd79a8",
];

const Dashboardpage = () => {
  const { keycloak } = useKeycloak();

  const authHeaders = {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  };

  const [projects, setProjects] = useState([]);

  const fetchProjects = async () => {
    try {
      const response = await axios.get(
        "http://20.120.234.137:8080/api/projects/list/id",
        authHeaders
      );
      if (response.data && response.data.payload) {
        const projectsData = response.data.payload;

        const projectData = projectsData.map((suggestion) => ({
          projectId: suggestion.projectId,
          projectName: suggestion.projectName,
          client: suggestion.client,
          clientName: suggestion.client.name,
          startDate: suggestion.startDate,
        }));
        setProjects(projectData);
      } else {
        setProjects([]);
      }
    } catch (error) {
      console.error("Error fetching projects:", error);
    }
  };

  fetchProjects();

  return (
    <div>
      <h1 className="dashboard">Welcome to the Dashboard</h1>
      <div className="projects">
        <h2 className="projtitle">Projects</h2>
        <div className="allprojects">
          {projects.map((suggestion, index) => (
            <div
              className="card"
              style={{ backgroundColor: colors[index % 8] }}
            >
              <div className="card-body">
                <h3>Project {index + 1}</h3>
                <h1 className="card-title">{suggestion.projectName}</h1>
                <p className="card-subtitle mb-2 text-muted">
                  Client: {suggestion.clientName}
                </p>
                {suggestion.startDate && (
                  <p className="card-subtitle mb-2 text-muted">
                    Start Date: {suggestion.startDate.slice(0, 10)}
                  </p>
                )}
                <Link
                  to={`/projects/${suggestion.projectId}`}
                  className="card-link"
                >
                  Open Project
                </Link>
              </div>
            </div>
          ))}
          <div class="card addcard">
            <div class="card-body">
              <Link to="/ProjectForm" className="addplus">
                <p>+</p>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Dashboardpage;
