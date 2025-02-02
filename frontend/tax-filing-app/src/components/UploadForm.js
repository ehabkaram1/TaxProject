// src/components/UploadForm.js
import React, { useState, useContext } from 'react';
import { Card, Form, Button } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const UploadForm = () => {
    const { handleFileUpload, setFormData, setCurrentStep } = useContext(TaxFormContext);  // Add these
    const [dragActive, setDragActive] = useState(false);
    const [selectedFile, setSelectedFile] = useState(null);
  

  const handleSkip = () => {
    // Initialize empty W2 data structure
    setFormData(prev => ({
      ...prev,
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
      }
    }));
    // Move to verify step
    setCurrentStep(STEPS.VERIFY);
  };



  const handleDrag = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.type === "dragenter" || e.type === "dragover") {
      setDragActive(true);
    } else if (e.type === "dragleave") {
      setDragActive(false);
    }
  };

  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    setDragActive(false);
    
    if (e.dataTransfer.files && e.dataTransfer.files[0]) {
      const file = e.dataTransfer.files[0];
      setSelectedFile(file);
      handleFileUpload(file);
    }
  };

  const handleFileSelect = (e) => {
    if (e.target.files && e.target.files[0]) {
      const file = e.target.files[0];
      setSelectedFile(file);
      handleFileUpload(file);
    }
  };

  return (
    <Card className="p-4">
      <Card.Body>
        <div
          className={`text-center p-5 border rounded ${
            dragActive ? 'border-primary bg-light' : 'border-dashed'
          }`}
          onDragEnter={handleDrag}
          onDragLeave={handleDrag}
          onDragOver={handleDrag}
          onDrop={handleDrop}
        >
          <h4>Upload your W-2 Form</h4>
          <p className="text-muted">
            {selectedFile 
              ? `Selected file: ${selectedFile.name}`
              : 'Drag and drop your W-2 PDF here, or click to select file'}
          </p>
          
          <Form.Group controlId="formFile" className="mb-3">
            <Form.Control
              type="file"
              accept=".pdf"
              onChange={handleFileSelect}
              className="d-none"
            />
            <Button 
              variant="primary" 
              onClick={() => document.getElementById('formFile').click()}
            >
              Select File
            </Button>
          </Form.Group>
        </div>

        <div className="text-center">
          <p className="text-muted mb-3">- OR -</p>
          <Button 
            variant="secondary" 
            onClick={handleSkip}
          >
            Enter Information Manually
          </Button>
        </div>


        <div className="mt-4">
          <h5>Instructions:</h5>
          <ul className="text-muted">
            <li>Upload your W-2 form in PDF format</li>
            <li>Make sure all information is clearly visible</li>
            <li>File size should be less than 10MB</li>
            <li>Only upload official W-2 forms from your employer</li>
          </ul>
        </div>

        {selectedFile && (
          <div className="mt-3 text-center">
            <Button variant="success" onClick={() => handleFileUpload(selectedFile)}>
              Continue
            </Button>
          </div>
        )}
      </Card.Body>
    </Card>
  );
};

export default UploadForm;