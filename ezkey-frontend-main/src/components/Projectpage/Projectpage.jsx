import React, { useState } from "react";
import "./Projectpage.css";
import { useParams } from "react-router-dom";
import { useKeycloak } from "@react-keycloak/web";
import axios from "axios";

const Projectpage = () => {
  const { projectId } = useParams();
  console.log(projectId);

  const { keycloak } = useKeycloak();

  const authHeaders = {
    headers: {
      Authorization: `Bearer ${keycloak.token}`,
    },
  };

  const [project, setProject] = useState([]);
  const [client, setClient] = useState([]);
  const [industry, setIndustry] = useState([]);
  const [location, setLocation] = useState([]);
  const [billingType, setBillingType] = useState([]);
  const [businessUnit, setBusinessUnit] = useState([]);
  // const [projectManager, setProjectManager] = useState([]);
  const [serviceLines, setServiceLines] = useState([]);
  // const [phases, setPhases] = useState([]);

  const fetchProject = async () => {
    try {
      const response = await axios.get(
        `http://20.120.234.137:8080/api/projects/details?projectId=${projectId}`,
        authHeaders
      );
      if (response.data && response.data.payload) {
        const projectData = response.data.payload;
        const clientData = projectData.client;
        const industryData = projectData.industry;
        const locationData = projectData.location;
        const billingTypeData = projectData.billingType;
        const businessUnitData = projectData.businessUnit;
        // const projectManagerData = projectData.projectManager;
        const serviceLinesData = projectData.serviceLines;
        setProject(projectData);
        setClient(clientData);
        setIndustry(industryData);
        setLocation(locationData);
        setBillingType(billingTypeData);
        setBusinessUnit(businessUnitData);
        // setProjectManager(projectManagerData);
        setServiceLines(serviceLinesData);
      } else {
        setProject([]);
        setClient([]);
        setIndustry([]);
        setLocation([]);
        setBillingType([]);
        setBusinessUnit([]);
        // setProjectManager([]);
        setServiceLines([]);
      }
    } catch (error) {
      console.error("Error fetching projects:", error);
    }
  };

  fetchProject();
  console.log(project);

  return (
    <div className="whole-form">
      <h1>Project: {project.projectName}</h1>
      <p>Industry: {industry.name}</p>
      {project.startDate && <p>Start Date: {project.startDate.slice(0, 10)}</p>}
      {/* {project.endDate !== null && (
        <p>End Date: {project.endDate.slice(0, 10)}</p>
      )} */}
      <p>Location: {location.name}</p>
      <p>Service Lines: {serviceLines.name}</p>
      <ul className="servicelines">
        {serviceLines.map((serviceLine, index) => (
          <li>{serviceLine.name}</li>
        ))}
      </ul>
      <p>Business Unit: {businessUnit.name}</p>
      <p>Billing Type: {billingType.name}</p>
      <p>Client: {client.name}</p>
      <p>Square Footage: {project.squareFootage}</p>
      <p>Head Count: {project.headCount}</p>
    </div>
  );
};

export default Projectpage;
