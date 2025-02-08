import React, { useState, useContext,useEffect } from 'react';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const VerifyPersonal = () => {
  const { formData, setFormData, setCurrentStep } = useContext(TaxFormContext);
  const [validated, setValidated] = useState(false);

  // Initialize local state with personal info
  const [localData, setLocalData] = useState({
    firstName: formData.personalInfo.firstName || '',
    lastName: formData.personalInfo.lastName || '',
    ssn: formData.personalInfo.ssn || '',
    // Use employee address from W2 data if US address is empty
    usAddress: formData.personalInfo.usAddress || formData.w2Data.employeeAddress || '',
    foreignAddress: formData.personalInfo.foreignAddress || '',
    filingStatus: formData.personalInfo.filingStatus || '',
    canBeClaimed: formData.personalInfo.canBeClaimed || false,
    scholarshipAmount: formData.personalInfo.scholarshipAmount || '',
    phoneNumber: formData.personalInfo.phoneNumber || '',
    email: formData.personalInfo.email || ''
  });
  
  // Add useEffect to sync address changes
  useEffect(() => {
    if (!localData.usAddress && formData.w2Data.employeeAddress) {
      setLocalData(prev => ({
        ...prev,
        usAddress: formData.w2Data.employeeAddress
      }));
    }
  }, [formData.w2Data.employeeAddress, localData.usAddress]);

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setLocalData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleBack = () => {
    setCurrentStep(STEPS.VERIFY_W2);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
    if (!form.checkValidity()) {
      event.stopPropagation();
      setValidated(true);
    } else {
      // Update the main formData with personal information
      setFormData(prev => ({
        ...prev,
        personalInfo: {
          ...prev.personalInfo,
          ...localData
        }
      }));
      // Move to next step
      setCurrentStep(STEPS.VERIFY_IMMIGRATION);
    }
  };

  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Personal Information</Card.Title>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        {/* Basic Information */}
        <h5 className="border-bottom pb-2 mb-3">Basic Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="firstName"
                value={localData.firstName}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Last Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="lastName"
                value={localData.lastName}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>SSN/ITIN</Form.Label>
              <Form.Control
                required
                type="text"
                name="ssn"
                value={localData.ssn}
                onChange={handleInputChange}
                placeholder="XXX-XX-XXXX"
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Filing Status</Form.Label>
              <Form.Select
                required
                name="filingStatus"
                value={localData.filingStatus}
                onChange={handleInputChange}
              >
                <option value="">Select Filing Status</option>
                <option value="single">Single</option>
                <option value="marriedSeparately">Married Filing Separately</option>
              </Form.Select>
            </Form.Group>
          </Col>
        </Row>

        {/* Contact Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Contact Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Phone Number</Form.Label>
              <Form.Control
                required
                type="tel"
                name="phoneNumber"
                value={localData.phoneNumber}
                onChange={handleInputChange}
                placeholder="123-456-7890"
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control
                required
                type="email"
                name="email"
                value={localData.email}
                onChange={handleInputChange}
                placeholder="example@email.com"
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Address Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Address Information</h5>
        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>U.S. Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="usAddress"
                value={localData.usAddress}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>Foreign Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="foreignAddress"
                value={localData.foreignAddress}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Additional Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Additional Information</h5>
        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                label="Someone else can claim me as a dependent"
                name="canBeClaimed"
                checked={localData.canBeClaimed}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Scholarship Amount (if any)</Form.Label>
              <Form.Control
                type="number"
                name="scholarshipAmount"
                value={localData.scholarshipAmount}
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

export default VerifyPersonal;