import React, { useContext, useState } from 'react';
import { Card, Button, Row, Col, Alert, ListGroup } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';

const DownloadForms = () => {
  const { formData, setCurrentStep } = useContext(TaxFormContext);
  const [downloading, setDownloading] = useState(false);
  const [error, setError] = useState(null);

  const handleDownload = async (formType) => {
    setDownloading(true);
    setError(null);
    
    try {
      const response = await fetch(`http://localhost:8080/api/generate/${formType}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
      });

      if (!response.ok) {
        throw new Error('Failed to generate form');
      }

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `${formType}.pdf`;
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    } catch (err) {
      setError(`Error generating ${formType}: ${err.message}`);
    } finally {
      setDownloading(false);
    }
  };

  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Download Your Tax Forms</Card.Title>

      {error && (
        <Alert variant="danger" className="mb-4">
          {error}
        </Alert>
      )}

      <Row className="mb-4">
        <Col md={6}>
          <Card className="h-100">
            <Card.Body>
              <h5 className="mb-3">Available Forms</h5>
              <ListGroup>
                <ListGroup.Item className="d-flex justify-content-between align-items-center">
                  Form 1040-NR
                  <Button 
                    variant="primary" 
                    onClick={() => handleDownload('1040nr')}
                    disabled={downloading}
                  >
                    {downloading ? 'Generating...' : 'Download'}
                  </Button>
                </ListGroup.Item>
                <ListGroup.Item className="d-flex justify-content-between align-items-center">
                  Form 8843
                  <Button 
                    variant="primary" 
                    onClick={() => handleDownload('8843')}
                    disabled={downloading}
                  >
                    {downloading ? 'Generating...' : 'Download'}
                  </Button>
                </ListGroup.Item>
                <ListGroup.Item className="d-flex justify-content-between align-items-center">
                  Form IL-1040
                  <Button 
                    variant="primary" 
                    onClick={() => handleDownload('il1040')}
                    disabled={downloading}
                  >
                    {downloading ? 'Generating...' : 'Download'}
                  </Button>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6}>
          <Card className="h-100">
            <Card.Body>
              <h5 className="mb-3">Next Steps</h5>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  1. Review all downloaded forms for accuracy
                </ListGroup.Item>
                <ListGroup.Item>
                  2. Sign and date Form 1040-NR on page 2
                </ListGroup.Item>
                <ListGroup.Item>
                  3. Sign and date Form 8843
                </ListGroup.Item>
                <ListGroup.Item>
                  4. Attach your W-2 form(s)
                </ListGroup.Item>
                <ListGroup.Item>
                  5. Make copies of all documents for your records
                </ListGroup.Item>
                <ListGroup.Item>
                  6. Mail forms to the appropriate IRS address by April 15, 2024
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row className="mb-4">
        <Col>
          <Card className="bg-light">
            <Card.Body>
              <h5 className="mb-3">Important Notes</h5>
              <ul className="mb-0">
                <li>Keep copies of all submitted forms for your records</li>
                <li>Include all required supporting documents</li>
                <li>Double-check all information before mailing</li>
                <li>Consider using certified mail for tracking purposes</li>
                <li>Contact a tax professional if you need additional assistance</li>
              </ul>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <div className="d-flex justify-content-between">
        <Button 
          variant="secondary" 
          onClick={() => setCurrentStep(STEPS.REVIEW)}
        >
          Back to Review
        </Button>
        <Button 
          variant="success"
          onClick={() => alert('Thank you for using our tax filing assistant!')}
        >
          Finish
        </Button>
      </div>
    </Card>
  );
};

export default DownloadForms;