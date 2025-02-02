// src/components/ProgressBar.js
import React, { useContext } from 'react';
import { ProgressBar as BootstrapProgress } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const ProgressBarComponent = () => {
  const { currentStep } = useContext(TaxFormContext);
  
  const steps = [
    { id: STEPS.UPLOAD, label: 'Upload W-2' },
    { id: STEPS.VERIFY, label: 'Verify Data' },
    { id: STEPS.REVIEW, label: 'Review' },
    { id: STEPS.DOWNLOAD, label: 'Download Forms' }
  ];

  // Calculate progress percentage
  const currentIndex = steps.findIndex(step => step.id === currentStep);
  const progressPercentage = ((currentIndex + 1) / steps.length) * 100;

  return (
    <div className="mb-4">
      <div className="d-flex justify-content-between mb-2">
        {steps.map((step, index) => {
          const isActive = currentStep === step.id;
          const isPast = steps.findIndex(s => s.id === currentStep) > index;
          
          return (
            <div 
              key={step.id} 
              className={`text-center ${isActive ? 'text-primary' : 
                isPast ? 'text-success' : 'text-muted'}`}
              style={{ width: '25%' }}
            >
              <div 
                className={`rounded-circle mx-auto mb-2 d-flex align-items-center justify-content-center`}
                style={{
                  width: '40px',
                  height: '40px',
                  border: '2px solid',
                  borderColor: isActive ? '#007bff' : 
                    isPast ? '#28a745' : '#dee2e6',
                  backgroundColor: isPast ? '#28a745' : 'white',
                  color: isPast ? 'white' : 'inherit'
                }}
              >
                {isPast ? '✓' : index + 1}
              </div>
              <small>{step.label}</small>
            </div>
          );
        })}
      </div>
      <BootstrapProgress now={progressPercentage} />
    </div>
  );
};

export default ProgressBarComponent;