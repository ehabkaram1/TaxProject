import React, { useContext, useEffect, useState } from 'react';
import { Card, Row, Col, Button, Table } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';
import axios from 'axios';

const ReviewForm = () => {
  const { formData, setCurrentStep } = useContext(TaxFormContext);
  const { w2Data, personalInfo } = formData;

  const [taxCalculations, setTaxCalculations] = useState(null);
  const [loading, setLoading] = useState(true);
  const [calculationError, setCalculationError] = useState(null);

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  useEffect(() => {
    calculateTaxes();
  }, [w2Data, personalInfo]); // Run once when component mounts
  
  const calculateTaxes = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/tax/calculate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          w2Data: w2Data,
          personalInfo: personalInfo
        })
      });
  
      if (!response.ok) {
        throw new Error('Failed to calculate taxes');
      }
  
      const data = await response.json();
      setTaxCalculations(data);
    } catch (err) {
      setCalculationError('Error calculating taxes: ' + err.message);
    } finally {
      setLoading(false);
    }
  };


  const handleProceedToDownload = async () => {
    // Include tax calculation results in the data sent to the tax-filing endpoint
    try {
      const fullData = {
        ...formData,
        taxCalculations: taxCalculations.calculations // assuming this structure based on your setup
      };

      const response = await axios.post('http://localhost:8080/api/tax-filing', fullData);
      if (response.status === 200) {
        // Handle success, such as redirecting to a download page or triggering a file download
        console.log('Form submitted successfully:', response.data);
        setCurrentStep(STEPS.DOWNLOAD);
      } else {
        throw new Error('Failed to submit tax filing');
      }
    } catch (error) {
      console.error('Error submitting tax filing:', error);
      // Optionally update state to show an error message
    }
  };



  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Review Your Information</Card.Title>
      
      {/* W-2 Information Section */}
      <h5 className="border-bottom pb-2 mb-3">W-2 Information</h5>
      <Row className="mb-4">
        <Col md={6}>
          <h6 className="mb-3">Employer Information</h6>
          <Table borderless size="sm">
            <tbody>
              <tr>
                <td className="text-muted">Name:</td>
                <td>{w2Data.employerName}</td>
              </tr>
              <tr>
                <td className="text-muted">Address:</td>
                <td>{w2Data.employerAddress}</td>
              </tr>
              <tr>
                <td className="text-muted">EIN:</td>
                <td>{w2Data.employerEIN}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
        <Col md={6}>
          <h6 className="mb-3">Employee Information</h6>
          <Table borderless size="sm">
            <tbody>
              <tr>
                <td className="text-muted">Name:</td>
                <td>{w2Data.employeeName}</td>
              </tr>
              <tr>
                <td className="text-muted">Address:</td>
                <td>{w2Data.employeeAddress}</td>
              </tr>
              <tr>
                <td className="text-muted">SSN:</td>
                <td>{w2Data.employeeSSN}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>

      <Row className="mb-4">
        <Col md={12}>
          <h6 className="mb-3">Income and Tax Information</h6>
          <Table striped bordered>
            <thead>
              <tr>
                <th>Description</th>
                <th className="text-end">Amount</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>Wages (Box 1)</td>
                <td className="text-end">{formatCurrency(w2Data.wages)}</td>
              </tr>
              <tr>
                <td>Federal Tax Withheld</td>
                <td className="text-end">{formatCurrency(w2Data.federalTaxWithheld)}</td>
              </tr>
              <tr>
                <td>State Wages ({w2Data.state})</td>
                <td className="text-end">{formatCurrency(w2Data.stateWages)}</td>
              </tr>
              <tr>
                <td>State Tax Withheld</td>
                <td className="text-end">{formatCurrency(w2Data.stateTaxWithheld)}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>

      {/* Additional Tax Information Section */}
      <h5 className="border-bottom pb-2 mb-3">Additional Tax Information</h5>
      <Row className="mb-4">
        <Col md={6}>
          <Table borderless size="sm">
            <tbody>
              <tr>
                <td className="text-muted">Filing Status:</td>
                <td>{personalInfo.filingStatus}</td>
              </tr>
              <tr>
                <td className="text-muted">Latest US Arrival:</td>
                <td>{new Date(personalInfo.arrivalDate).toLocaleDateString()}</td>
              </tr>
              <tr>
                <td className="text-muted">Days in US (2023):</td>
                <td>{personalInfo.daysInUS} days</td>
              </tr>
              <tr>
                <td className="text-muted">Visa Type:</td>
                <td>{personalInfo.visaType}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
        <Col md={6}>
          <Table borderless size="sm">
            <tbody>
              <tr>
                <td className="text-muted">Scholarship Amount:</td>
                <td>{formatCurrency(personalInfo.scholarshipAmount || 0)}</td>
              </tr>
              <tr>
                <td className="text-muted">Can be claimed as dependent:</td>
                <td>{personalInfo.canBeClaimed ? 'Yes' : 'No'}</td>
              </tr>
              <tr>
                <td className="text-muted">Prior US Income:</td>
                <td>{personalInfo.hadUSIncomePriorYears ? 'Yes' : 'No'}</td>
              </tr>
            </tbody>
          </Table>
        </Col>
      </Row>

      {/* Estimated Tax Calculations Section */}
      <h5 className="border-bottom pb-2 mb-3">Estimated Tax Calculations</h5>
      <Row className="mb-4">
      <Col md={12}>
        <div className="bg-light p-3 rounded">
          {loading ? (
              <p className="text-muted mb-2">Calculating taxes...</p>
            ) : calculationError ? (
              <p className="text-danger mb-2">{calculationError}</p>
            ) : !taxCalculations ? (
              <p className="text-muted mb-2">No tax calculations available.</p>
            ) : (
              <Table striped bordered className="mb-0">
                <thead>
                  <tr>
                    <th>Description</th>
                    <th className="text-end">Amount</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Taxable Income</td>
                    <td className="text-end">{formatCurrency(taxCalculations.calculations.taxableIncome)}</td>
                  </tr>
                  <tr>
                    <td>Calculated Federal Tax</td>
                    <td className="text-end">{formatCurrency(taxCalculations.calculations.calculatedFederalTax)}</td>
                  </tr>
                  <tr>
                    <td>Federal Tax Withheld</td>
                    <td className="text-end">{formatCurrency(taxCalculations.calculations.federalTaxWithheld)}</td>
                  </tr>
                  <tr>
                    <td>Federal Refund/Amount Owed</td>
                    <td className={`text-end ${taxCalculations.calculations.federalRefundOrOwe >= 0 ? 'text-success' : 'text-danger'}`}>
                      {formatCurrency(taxCalculations.calculations.federalRefundOrOwe)}
                      {taxCalculations.calculations.federalRefundOrOwe >= 0 ? ' (Refund)' : ' (Owe)'}
                    </td>
                  </tr>
                  <tr>
                    <td>Calculated State Tax</td>
                    <td className="text-end">{formatCurrency(taxCalculations.calculations.calculatedStateTax)}</td>
                  </tr>
                  <tr>
                    <td>State Tax Withheld</td>
                    <td className="text-end">{formatCurrency(taxCalculations.calculations.stateTaxWithheld)}</td>
                  </tr>
                  <tr>
                    <td>State Refund/Amount Owed</td>
                    <td className={`text-end ${taxCalculations.calculations.stateRefundOrOwe >= 0 ? 'text-success' : 'text-danger'}`}>
                      {formatCurrency(taxCalculations.calculations.stateRefundOrOwe)}
                      {taxCalculations.calculations.stateRefundOrOwe >= 0 ? ' (Refund)' : ' (Owe)'}
                    </td>
                  </tr>
                  <tr className="fw-bold">
                    <td>Total Refund/Amount Owed</td>
                    <td className={`text-end ${taxCalculations.calculations.totalRefundOrOwe >= 0 ? 'text-success' : 'text-danger'}`}>
                      {formatCurrency(taxCalculations.calculations.totalRefundOrOwe ||0)}
                      {taxCalculations.calculations.totalRefundOrOwe >= 0 ? ' (Refund)' : ' (Owe)'}
                    </td>
                  </tr>
                </tbody>
              </Table>
            )}
          </div>
        </Col>
      </Row>

      {/* Navigation Buttons */}
      <div className="d-flex justify-content-between mt-4">
        <Button 
          variant="secondary" 
          onClick={() => setCurrentStep(STEPS.VERIFY)}
        >
          Back to Edit
        </Button>
        <Button 
          variant="primary" 
          onClick={() => setCurrentStep(STEPS.DOWNLOAD)}
        >
          Proceed to Download Forms
        </Button>
      </div>
    </Card>
  );
};

export default ReviewForm;