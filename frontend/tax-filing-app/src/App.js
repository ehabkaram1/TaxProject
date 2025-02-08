// src/App.js
import React, { createContext, useState } from 'react';
import { Container, Alert } from 'react-bootstrap';
// Import the components
import ProgressBarComponent from './components/ProgressBar';
import UploadForm from './components/UploadForm';
//import VerifyData from './components/VerifyData';
import ReviewForm from './components/ReviewForm';
import DownloadForms from './components/DownloadForms';
// Import new components for split verification steps
import VerifyW2 from './components/VerifyW2';
import VerifyPersonal from './components/VerifyPersonal';
import VerifyImmigration from './components/VerifyImmigration';
import VerifyAcademic from './components/VerifyAcademic';

// Create the context
export const TaxFormContext = createContext();

// Define form steps (already defined in your code)
export const STEPS = {
  UPLOAD: 'UPLOAD',
  VERIFY_W2: 'VERIFY_W2',
  VERIFY_PERSONAL: 'VERIFY_PERSONAL',
  VERIFY_IMMIGRATION: 'VERIFY_IMMIGRATION',
  VERIFY_ACADEMIC: 'VERIFY_ACADEMIC',
  REVIEW: 'REVIEW',
  DOWNLOAD: 'DOWNLOAD'
};


function App() {
  const [currentStep, setCurrentStep] = useState(STEPS.UPLOAD);
  const [formData, setFormData] = useState({
    w2Data: {
      employerName: '',
      employerAddress: '',
      employeeName: '',
      employeeAddress: '',
      employerEIN: '',
      employeeSSN: '',
      wages: 0,
      federalTaxWithheld: 0,
      stateTaxWithheld: 0,
      stateWages: 0,
      state: ''
    },
    personalInfo: {
      firstName: '',
      lastName: '',
      ssn: '',
      foreignAddress: '',  // Changed from foreigAddress
      usAddress: '',
      filingStatus: '',
      visaType: 'F1',
      countryOfCitizenship: '',
      passportCountry: '',  // Changed from passportcountry
      passportNumber: '',   // Changed from passportnumber
      arrivalDate: '',
      daysInUS2023: '',
      daysInUS2022: '',
      daysInUS2021: '',
      academicInstitutionName: '',     // Changed from academicInstitutionname
      academicInstitutionAddress: '',   // Changed from academicinstituionaddress
      academicInstitutionPhone: '',     // Changed from acadmicinstituionphonenumber
      phoneNumber: '',        // Added
      email: '',             // Added
      canBeClaimed: false,
      scholarshipAmount: '',
      hadUSIncomePriorYears: false
    }
  });
  
  const [error, setError] = useState(null);

  const handleFileUpload = async (file) => {
    try {
      const formData = new FormData();
      formData.append('w2', file);
      
      console.log('Attempting to upload file:', file.name);
      
      const response = await fetch('http://localhost:8080/api/w2/upload', {
        method: 'POST',
        body: formData,
      });

      console.log('Response status:', response.status);

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Upload failed');
      }
      
      const data = await response.json();
      console.log('Received data:', data);
      
      setFormData(prev => ({
        ...prev,
        w2Data: data
      }));
      setCurrentStep(STEPS.VERIFY_W2);
    } catch (err) {
      console.error('Upload error:', err);
      setError(`Error uploading file: ${err.message}`);
    }
  };
  return (
    <TaxFormContext.Provider 
      value={{
        currentStep,
        setCurrentStep,
        formData,
        setFormData,
        error,
        setError,
        handleFileUpload,
        STEPS
      }}
    >
      <Container className="py-5">
        <Header />
        <ProgressBarComponent />
        <MainContent />
      </Container>
    </TaxFormContext.Provider>
  );
}

// Header Component
const Header = () => (
  <div className="text-center mb-4">
    <h1>IRS Tax Filing Assistant</h1>
    <p className="text-muted">For F-1 International Students</p>
  </div>
);

// MainContent Component
const MainContent = () => {
  const { currentStep, error } = React.useContext(TaxFormContext);

  return (
    <div className="bg-white p-4 rounded shadow">
      {error && (
        <Alert variant="danger" className="mb-4">
          {error}
        </Alert>
      )}
      
      {currentStep === STEPS.UPLOAD && <UploadForm />}
      {currentStep === STEPS.VERIFY_W2 && <VerifyW2 />}
      {currentStep === STEPS.VERIFY_PERSONAL && <VerifyPersonal />}
      {currentStep === STEPS.VERIFY_IMMIGRATION && <VerifyImmigration />}
      {currentStep === STEPS.VERIFY_ACADEMIC && <VerifyAcademic />}
      {currentStep === STEPS.REVIEW && <ReviewForm />}
      {currentStep === STEPS.DOWNLOAD && <DownloadForms />}
    </div>
  );
};

export default App;