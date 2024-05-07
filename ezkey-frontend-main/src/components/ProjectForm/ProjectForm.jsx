
import React, { useState, useEffect, useRef, useMemo } from 'react';
import './ProjectForm.css';
import { useKeycloak } from "@react-keycloak/web";
function getCurrentDateTime() {
  return new Date().toISOString();
}
function convertToLocalTime(serverDate) {
  var dt = new Date(Date.parse(serverDate));
  var localOffset = dt.getTimezoneOffset();
  var localDate = new Date(dt.getTime() - localOffset * 60000);
  var formattedDate = localDate.toISOString(); 
  return formattedDate;
}
const ProjectForm = () => {
  const [formData, setFormData] = useState({ industryId: 0, locationId: 0, serviceLineIds: [], jobTitleId: 0, businessUnitId: 0, billingTypeId: 0, clientId: 0, clientName: '', projectAddress:'',projectName:'',squareFootage: 0, headCount: 0 ,projectManagerId: 0, projectManagerFirstName: '', projectManagerLastName: '', startDate: '', endDate: '', createdOn: getCurrentDateTime(), modifiedOn: getCurrentDateTime(), });
  const [industryName, setIndustryName] = useState('');
  const [name, setLocationName] = useState('');
  const [serviceLineName, setServiceLineName] = useState('');
  const [jobTitleName, setJobTitleName] = useState('');
  const [businessUnitName, setBusinessUnitName] = useState('');
  const [billingTypeName, setBillingTypeName] = useState('');
  const [clientName, setClientName] = useState('');
  const [industrySuggestions, setIndustrySuggestions] = useState([]);
  const [projectManagerName, setProjectManagerName] = useState('');
  const [locationSuggestions, setLocationSuggestions] = useState([]);
  const [serviceLineSuggestions, setServiceLineSuggestions] = useState([]);
  const [jobTitleSuggestions, setJobTitleSuggestions] = useState([]);
  const [businessUnitSuggestions, setBusinessUnitSuggestions] = useState([]);
  const [billingTypeSuggestions, setBillingTypeSuggestions] = useState([]);
  const [clientSuggestions, setClientSuggestions] = useState([]);
  const [projectManagerSuggestions, setProjectManagerSuggestions] = useState([]);
  const inputRef = useRef(null);
    const { keycloak } = useKeycloak();
  const authHeaders = useMemo(() => ({
    Authorization: `Bearer ${keycloak.token}`,
    'Content-Type': 'application/json'
  }), [keycloak.token]);
  useEffect(() => {
    const fetchSuggestions = async (endpoint, query, setter) => {
      try {
        const response = await fetch(endpoint + query, { headers: authHeaders });
        const data = await response.json();
        setter(data.payload);
      } catch (error) {
        console.error(`Failed to fetch ${query} suggestions:`, error);
        setter([]);
      }
    };
    if (industryName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/projects/industries/suggestions?query=',
        industryName,
        setIndustrySuggestions
      );
    } else {
      setIndustrySuggestions([]);
    }
    if (name.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/v1/users/location/suggestions?query=',
        name,
        setLocationSuggestions
      );
    } else {
      setLocationSuggestions([]);
    }
    if (serviceLineName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/projects/servicelines/suggestions?query=',
        serviceLineName,
        setServiceLineSuggestions
      );
    } else {
      setServiceLineSuggestions([]);
    }
    if (jobTitleName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/v1/users/jobs/suggestions?query=',
        jobTitleName,
        setJobTitleSuggestions
      );
    } else {
      setJobTitleSuggestions([]);
    }
    if (businessUnitName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/v1/users/businessunit/suggestions?query=',
        businessUnitName,
        setBusinessUnitSuggestions
      );
    } else {
      setBusinessUnitSuggestions([]);
    }
    if (billingTypeName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/projects/billingtypes/suggestions?query=',
        billingTypeName,
        setBillingTypeSuggestions
      );
    } else {
      setBillingTypeSuggestions([]);
    }
    if (clientName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/projects/clients/suggestions?query=',
        clientName,
        setClientSuggestions
      );
    } else {
      setClientSuggestions([]);
    }
    if (projectManagerName.length > 0) {
      fetchSuggestions(
        'http://20.120.234.137:8080/api/v1/users/name/suggestions?query=',
        projectManagerName,
        setProjectManagerSuggestions
      );
    } else {
      setProjectManagerSuggestions([]);
    }
  }, [industryName, name, serviceLineName, jobTitleName, businessUnitName, billingTypeName, clientName, projectManagerName, authHeaders]);
  const handleIndustryChange = (e) => {
    setIndustryName(e.target.value);
  };
  const handleLocationChange = (e) => {
    setLocationName(e.target.value);
  };
  const handleServiceLineChange = (e) => {
    setServiceLineName(e.target.value);
  };
  const handleJobTitleChange = (e) => {
    setJobTitleName(e.target.value);
  };
  const handleBusinessUnitChange = (e) => {
    setBusinessUnitName(e.target.value);
  };
  const handleBillingTypeChange = (e) => {
    setBillingTypeName(e.target.value);
  };
  const handleProjectManagerChange = (e) => {
    setProjectManagerName(e.target.value);
  };
  const handleClientChange = (e) => {
    setClientName(e.target.value);
  };
  const handleProjectNameChange = (e) => {
    setFormData({ ...formData, projectName: e.target.value });
  };
  const handleProjectAddressChange = (e) => {
    setFormData({ ...formData, projectAddress: e.target.value });
  };
  const handleSquareFootageChange = (e) => {
    setFormData({ ...formData, squareFootage: parseInt(e.target.value) });
  };
  const handleHeadCountChange = (e) => {
    setFormData({ ...formData, headCount: parseInt(e.target.value) });
  };
  const selectItem = (fieldName, id, itemName) => {
    setFormData(prevFormData => ({
      ...prevFormData,
      [`${fieldName}Id`]: id,
      serviceLineIds: fieldName === 'serviceLine' ? [...prevFormData.serviceLineIds, id] : prevFormData.serviceLineIds,
      jobTitleId: fieldName === 'jobTitle' ? id : prevFormData.jobTitleId,
      businessUnitId: fieldName === 'businessUnit' ? id : prevFormData.businessUnitId,
      billingTypeId: fieldName === 'billingType' ? id : prevFormData.billingTypeId,
      clientId: fieldName === 'client' ? id : prevFormData.clientId,
      clientName: fieldName === 'client' ? itemName : prevFormData.clientName,
      projectManagerId: fieldName === 'projectManager' ? id : prevFormData.projectManagerId,
      projectManagerFirstName: fieldName === 'projectManager' ? itemName.firstName : prevFormData.projectManagerFirstName,
      projectManagerLastName: fieldName === 'projectManager' ? itemName.lastName : prevFormData.projectManagerLastName,
    }));
    switch (fieldName) {
      case 'industry':
        setIndustryName(itemName);
        break;
      case 'location':
        setLocationName(itemName);
        break;
      case 'serviceLine':
        setServiceLineName(itemName);
        break;
      case 'jobTitle':
        setJobTitleName(itemName);
        break;
      case 'businessUnit':
        setBusinessUnitName(itemName);
        break;
      case 'billingType':
        setBillingTypeName(itemName);
        break;
      case 'projectManager':
        setProjectManagerName(`${itemName.firstName} ${itemName.lastName}`);
        break;
      case 'client':
        setClientName(itemName);
        break;
      default:
        break;
    }
    if (inputRef.current) {
      inputRef.current.focus();
    }
  };
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const formattedStartDate = convertToLocalTime(formData.startDate);
      const formattedEndDate = convertToLocalTime(formData.endDate);
      const response = await fetch('http://20.120.234.137:8080/api/projects/add', {
        method: 'POST',
        headers: authHeaders,
        body: JSON.stringify({
          ...formData,
          startDate: formattedStartDate,
          endDate: formattedEndDate,
        })
      });
      const data = await response.json();
      alert('Project submitted successfully!');
      console.log('Success:', data);
    } catch (error) {
      console.error('Error:', error);
      alert('Failed to submit project.');
    }
  };
  return (
    <form onSubmit={handleSubmit}>
      <div className="ProjectForm">
        <h1>Project Form</h1>
        <div className="form-grid">
         
            <div className="input-group">
              <label>Project Name:</label>
              <input 
                type="text" 
                value={formData.projectName} 
                onChange={handleProjectNameChange} 
                autoComplete="off" 
              />
            </div>
            <div className="input-group">
              <label>Project Address:</label>
              <input 
                type="text" 
                value={formData.projectAddress} 
                onChange={handleProjectAddressChange} 
                autoComplete="off" 
              />
            </div>
            <div className="input-group">
              <label>Start Date:</label>
              <input 
                type="datetime-local" 
                value={formData.startDate} 
                onChange={(e) => setFormData({ ...formData, startDate: e.target.value })} 
              />
            </div>
            <div className="input-group">
              <label>End Date:</label>
              <input 
                type="datetime-local" 
                value={formData.endDate} 
                onChange={(e) => setFormData({ ...formData, endDate: e.target.value })} 
              />
            </div>
            <div className="input-group">
  <label>Industry:</label>
  {industryName.length === 0 && ( // Show input field only when industryName has some text
    <input 
      type="text" 
      value={industryName} 
      onChange={handleIndustryChange} 
      autoComplete="off" 
    />
  )}
  {industrySuggestions.length > 0 && industryName.length > 0 && ( // Show suggestions only when the input field is focused or has some text
    <ul>
      {industrySuggestions.map(industry => (
        <li key={industry.industryId} onClick={() => selectItem('industry', industry.industryId, industry.industryName)}>
          {industry.industryName}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
  <label>Location:</label>
  {name.length === 0 && ( // Show input field only when name has some text
    <input 
      type="text" 
      value={name} 
      onChange={handleLocationChange} 
      autoComplete="off" 
    />
  )}
  {locationSuggestions.length > 0 && name.length > 0 && ( // Show suggestions only when the input field is focused or has some text
    <ul>
      {locationSuggestions.map(location => (
        <li key={location.locationId} onClick={() => selectItem('location', location.locationId, location.name)}>
          {location.name}
        </li>
      ))}
    </ul>
  )}
</div>

<div className="input-group">
  <label>Service Line:</label>
  {serviceLineName.length === 0 && (
    <input 
      type="text" 
      value={serviceLineName} 
      onChange={handleServiceLineChange} 
      autoComplete="off" 
    />
  )}
  {serviceLineSuggestions.length > 0 && serviceLineName.length > 0 && (
    <ul>
      {serviceLineSuggestions.map(serviceLine => (
        <li key={serviceLine.serviceLineId} onClick={() => selectItem('serviceLine', serviceLine.serviceLineId, serviceLine.serviceLineName)}>
          {serviceLine.serviceLineName}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
  <label>Job Title:</label>
  {jobTitleName.length ===0 && (
    <input 
      type="text" 
      value={jobTitleName} 
      onChange={handleJobTitleChange} 
      autoComplete="off" 
    />
  )}
  {jobTitleSuggestions.length > 0 && jobTitleName.length > 0 && (
    <ul>
      {jobTitleSuggestions.map(jobTitle => (
        <li key={jobTitle.jobTitleId} onClick={() => selectItem('jobTitle', jobTitle.jobTitleId, jobTitle.jobTitle)}>
          {jobTitle.jobTitle}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
  <label>Business Unit:</label>
  {businessUnitName.length === 0 && (
    <input 
      type="text" 
      value={businessUnitName} 
      onChange={handleBusinessUnitChange} 
      autoComplete="off" 
    />
  )}
  {businessUnitSuggestions.length > 0 && businessUnitName.length > 0 && (
    <ul>
      {businessUnitSuggestions.map(businessUnit => (
        <li key={businessUnit.businessUnitId} onClick={() => selectItem('businessUnit', businessUnit.businessUnitId, businessUnit.businessUnitName)}>
          {businessUnit.businessUnitName}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
  <label>Billing Type:</label>
  {billingTypeName.length === 0 && (
    <input 
      type="text" 
      value={billingTypeName} 
      onChange={handleBillingTypeChange} 
      autoComplete="off" 
    />
  )}
  {billingTypeSuggestions.length > 0 && billingTypeName.length > 0 && (
    <ul>
      {billingTypeSuggestions.map(billingType => (
        <li key={billingType.billingTypeId} onClick={() => selectItem('billingType', billingType.billingTypeId, billingType.billingTypeName)}>
          {billingType.billingTypeName}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
  <label>Client Name:</label>
  {clientName.length === 0 && (
    <input 
      type="text" 
      value={clientName} 
      onChange={handleClientChange} 
      autoComplete="off" 
    />
  )}
  {clientSuggestions.length > 0 && clientName.length > 0 && (
    <ul>
      {clientSuggestions.map(client => (
        <li key={client.clientId} onClick={() => selectItem('client', client.clientId, client.clientName)}>
          {client.clientName}
        </li>
      ))}
    </ul>
  )}
</div>
<div className="input-group">
              <label>Project Manager:</label>
              <input 
                type="text" 
                value={projectManagerName} 
                onChange={handleProjectManagerChange} 
                autoComplete="off" 
              />
              {projectManagerSuggestions.length > 0 && (
                <ul>
                  {projectManagerSuggestions.map(manager => (
                    <li key={manager.managerId} onClick={() => selectItem('projectManager', manager.managerId, { firstName: manager.firstName, lastName: manager.lastName })}>
                      {`${manager.firstName} ${manager.lastName}`}
                    </li>
                  ))}
                </ul>
              )}
              </div>
<div className="input-group">
  <label>Square Footage:</label>
  <input 
    type="number" 
    value={formData.squareFootage} 
    onChange={handleSquareFootageChange} 
  />
</div>
<div className="input-group">
  <label>Head Count:</label>
  <input 
    type="number" 
    value={formData.headCount} 
    onChange={handleHeadCountChange} 
  />
</div>
          </div>
        </div>
        <button type="submit">Submit</button>
    </form>
  );
};
export default ProjectForm;
