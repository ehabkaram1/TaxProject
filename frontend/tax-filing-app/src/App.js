// src/App.js
import React, { createContext, useState } from 'react';
import { Container, Alert } from 'react-bootstrap';
// Import the components
import ProgressBarComponent from './components/ProgressBar';
import UploadForm from './components/UploadForm';
import VerifyData from './components/VerifyData';
import ReviewForm from './components/ReviewForm';  // Import ReviewForm component
import DownloadForms from './components/DownloadForms';
// Create the context
export const TaxFormContext = createContext();

// Define form steps
export const STEPS = {
  UPLOAD: 'upload',
  VERIFY: 'verify',
  REVIEW: 'review',
  DOWNLOAD: 'download'
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
      foreigAddress: '',
      usAddress: '',
      filingStatus: '',
      visaType: 'F1',
      countryOfCitizenship: '',
      passportcountry: '',
      passportnumber: '',

      arrivalDate: '',
      daysInUS2023: '',
      daysInUS2022: '',
      daysInUS2021: '',
      academicInstitutionname: '',
      academicinstituionaddress: '',
      acadmicinstituionphonenumber: '',
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
      setCurrentStep(STEPS.VERIFY);
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
      {currentStep === STEPS.VERIFY && <VerifyData />}
      {currentStep === STEPS.REVIEW && <ReviewForm />}
      {currentStep === STEPS.DOWNLOAD && <DownloadForms />}
    </div>
  );
};

// Placeholder component for the remaining step
//const DownloadForms = () => <div>Download Forms</div>;

export default App;