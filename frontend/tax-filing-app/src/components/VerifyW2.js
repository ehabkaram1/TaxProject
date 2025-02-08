import React, { useState, useContext } from 'react';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const VerifyW2 = () => {
  const { formData, setFormData, setCurrentStep } = useContext(TaxFormContext);
  const [validated, setValidated] = useState(false);

  // Initialize local state with W2 data
  const [localData, setLocalData] = useState({
    employerName: formData.w2Data.employerName || '',
    employerAddress: formData.w2Data.employerAddress || '',
    employeeName: formData.w2Data.employeeName || '',
    employeeAddress: formData.w2Data.employeeAddress || '',
    employerEIN: formData.w2Data.employerEIN || '',
    employeeSSN: formData.w2Data.employeeSSN || '',
    wages: formData.w2Data.wages || 0,
    federalTaxWithheld: formData.w2Data.federalTaxWithheld || 0,
    stateTaxWithheld: formData.w2Data.stateTaxWithheld || 0,
    stateWages: formData.w2Data.stateWages || 0,
    state: formData.w2Data.state || ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setLocalData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
    if (!form.checkValidity()) {
      event.stopPropagation();
      setValidated(true);
    } else {
      // Update the main formData with W2 information
      setFormData(prev => ({
        ...prev,
        w2Data: localData
      }));
      // Move to next step
      setCurrentStep(STEPS.VERIFY_PERSONAL);
    }
  };

  const handleBack = () => {
    setCurrentStep(STEPS.UPLOAD);
  };

  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Verify W-2 Information</Card.Title>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        {/* Employer Information */}
        <h5 className="border-bottom pb-2 mb-3">Employer Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Employer Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="employerName"
                value={localData.employerName}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Employer EIN</Form.Label>
              <Form.Control
                required
                type="text"
                name="employerEIN"
                value={localData.employerEIN}
                onChange={handleInputChange}
                pattern="\d{2}-\d{7}"
                placeholder="XX-XXXXXXX"
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>Employer Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="employerAddress"
                value={localData.employerAddress}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Employee Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Employee Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Employee Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="employeeName"
                value={localData.employeeName}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Employee SSN</Form.Label>
              <Form.Control
                required
                type="text"
                name="employeeSSN"
                value={localData.employeeSSN}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>Employee Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="employeeAddress"
                value={localData.employeeAddress}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Income Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Income Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Wages (Box 1)</Form.Label>
              <Form.Control
                required
                type="number"
                name="wages"
                value={localData.wages}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Federal Tax Withheld (Box 2)</Form.Label>
              <Form.Control
                required
                type="number"
                name="federalTaxWithheld"
                value={localData.federalTaxWithheld}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>State Tax Withheld</Form.Label>
              <Form.Control
                required
                type="number"
                name="stateTaxWithheld"
                value={localData.stateTaxWithheld}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>State Wages</Form.Label>
              <Form.Control
                required
                type="number"
                name="stateWages"
                value={localData.stateWages}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Navigation Buttons */}
        <div className="d-flex justify-content-between mt-4">
          <Button variant="secondary" onClick={handleBack}>
            Back
          </Button>
          <Button variant="primary" type="submit">
            Next
          </Button>
        </div>
      </Form>
    </Card>
  );
};

export default VerifyW2;