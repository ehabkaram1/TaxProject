import React, { useState, useContext } from 'react';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const VerifyAcademic = () => {
  const { formData, setFormData, setCurrentStep } = useContext(TaxFormContext);
  const [validated, setValidated] = useState(false);
  const [isInstitutionEmployer, setIsInstitutionEmployer] = useState(false);

  // Initialize local state with academic info
  const [localData, setLocalData] = useState({
    academicInstitutionName: formData.personalInfo.academicInstitutionName || '',
    academicInstitutionAddress: formData.personalInfo.academicInstitutionAddress || '',
    academicInstitutionPhone: formData.personalInfo.academicInstitutionPhone || ''
  });

  const handleInputChange = (e) => {
    const { name, value,checked } = e.target;

    if (name === 'isInstitutionEmployer') {
      setIsInstitutionEmployer(checked);
      if (checked) {
        setLocalData(prev => ({
          ...prev,
          academicInstitutionName: formData.w2Data.employerName,
          academicInstitutionAddress: formData.w2Data.employerAddress
        }));
      }
      return;
    }

    setLocalData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleBack = () => {
    setCurrentStep(STEPS.VERIFY_IMMIGRATION);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
    if (!form.checkValidity()) {
      event.stopPropagation();
      setValidated(true);
    } else {
      // Update the main formData with academic information
      setFormData(prev => ({
        ...prev,
        personalInfo: {
          ...prev.personalInfo,
          ...localData
        }
      }));
      // Move to review step
      setCurrentStep(STEPS.REVIEW);
    }
  };

  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Academic Information</Card.Title>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Check
                type="checkbox"
                label="My academic institution is my employer"
                name="isInstitutionEmployer"
                checked={isInstitutionEmployer}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Institution Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="academicInstitutionName"
                value={localData.academicInstitutionName}
                onChange={handleInputChange}
                disabled={isInstitutionEmployer}
              />
              {isInstitutionEmployer && (
                <Form.Text className="text-muted">
                  Using employer name from W-2
                </Form.Text>
              )}
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Institution Phone</Form.Label>
              <Form.Control
                required
                type="tel"
                name="academicInstitutionPhone"
                value={localData.academicInstitutionPhone}
                onChange={handleInputChange}
                placeholder="123-456-7890"
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>Institution Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="academicInstitutionAddress"
                value={localData.academicInstitutionAddress}
                onChange={handleInputChange}
                disabled={isInstitutionEmployer}
              />
              {isInstitutionEmployer && (
                <Form.Text className="text-muted">
                  Using employer address from W-2
                </Form.Text>
              )}
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Academic Director Name</Form.Label>
                <Form.Control
                  required
                  type="text"
                  name="academicDirectorName"
                  value={localData.academicDirectorName}
                  onChange={handleInputChange}
                />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Director's Phone Number</Form.Label>
                <Form.Control
                  required
                  type="tel"
                  name="academicDirectorPhone"
                  value={localData.academicDirectorPhone}
                  onChange={handleInputChange}
                  placeholder="123-456-7890"
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
            Review All Information
          </Button>
        </div>
      </Form>
    </Card>
  );
};

export default VerifyAcademic;