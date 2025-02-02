import React, { useContext } from 'react';
import { Card, Row, Col, Button, Table } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const ReviewForm = () => {
  const { formData, setCurrentStep } = useContext(TaxFormContext);
  const { w2Data, personalInfo } = formData;

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
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

      {/* Estimated Tax Calculations Section (Placeholder) */}
      <h5 className="border-bottom pb-2 mb-3">Estimated Tax Calculations</h5>
      <Row className="mb-4">
        <Col md={12}>
          <div className="bg-light p-3 rounded">
            <p className="text-muted mb-2">Tax calculations will be available here after backend implementation.</p>
            <Table striped bordered className="mb-0">
              <thead>
                <tr>
                  <th>Description</th>
                  <th className="text-end">Amount</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>Estimated Taxable Income</td>
                  <td className="text-end">Pending</td>
                </tr>
                <tr>
                  <td>Estimated Federal Tax</td>
                  <td className="text-end">Pending</td>
                </tr>
                <tr>
                  <td>Estimated State Tax</td>
                  <td className="text-end">Pending</td>
                </tr>
                <tr>
                  <td>Estimated Refund/Payment Due</td>
                  <td className="text-end">Pending</td>
                </tr>
              </tbody>
            </Table>
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