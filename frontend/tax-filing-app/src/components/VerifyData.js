import React, { useState, useContext } from 'react';
import { Form, Button, Card, Row, Col } from 'react-bootstrap';
import { TaxFormContext, STEPS } from '../App';
import Select from 'react-select';



// Add this array at the top of your file
const countries = [
    { value: 'AF', label: 'Afghanistan' },
    { value: 'AX', label: 'Åland Islands' },
    { value: 'AL', label: 'Albania' },
    { value: 'DZ', label: 'Algeria' },
    { value: 'AS', label: 'American Samoa' },
    { value: 'AD', label: 'Andorra' },
    { value: 'AO', label: 'Angola' },
    { value: 'AI', label: 'Anguilla' },
    { value: 'AQ', label: 'Antarctica' },
    { value: 'AG', label: 'Antigua and Barbuda' },
    { value: 'AR', label: 'Argentina' },
    { value: 'AM', label: 'Armenia' },
    { value: 'AW', label: 'Aruba' },
    { value: 'AU', label: 'Australia' },
    { value: 'AT', label: 'Austria' },
    { value: 'AZ', label: 'Azerbaijan' },
    { value: 'BS', label: 'Bahamas' },
    { value: 'BH', label: 'Bahrain' },
    { value: 'BD', label: 'Bangladesh' },
    { value: 'BB', label: 'Barbados' },
    { value: 'BY', label: 'Belarus' },
    { value: 'BE', label: 'Belgium' },
    { value: 'BZ', label: 'Belize' },
    { value: 'BJ', label: 'Benin' },
    { value: 'BM', label: 'Bermuda' },
    { value: 'BT', label: 'Bhutan' },
    { value: 'BO', label: 'Bolivia' },
    { value: 'BQ', label: 'Bonaire, Sint Eustatius and Saba' },
    { value: 'BA', label: 'Bosnia and Herzegovina' },
    { value: 'BW', label: 'Botswana' },
    { value: 'BV', label: 'Bouvet Island' },
    { value: 'BR', label: 'Brazil' },
    { value: 'IO', label: 'British Indian Ocean Territory' },
    { value: 'BN', label: 'Brunei Darussalam' },
    { value: 'BG', label: 'Bulgaria' },
    { value: 'BF', label: 'Burkina Faso' },
    { value: 'BI', label: 'Burundi' },
    { value: 'CV', label: 'Cabo Verde' },
    { value: 'KH', label: 'Cambodia' },
    { value: 'CM', label: 'Cameroon' },
    { value: 'CA', label: 'Canada' },
    { value: 'KY', label: 'Cayman Islands' },
    { value: 'CF', label: 'Central African Republic' },
    { value: 'TD', label: 'Chad' },
    { value: 'CL', label: 'Chile' },
    { value: 'CN', label: 'China' },
    { value: 'CX', label: 'Christmas Island' },
    { value: 'CC', label: 'Cocos (Keeling) Islands' },
    { value: 'CO', label: 'Colombia' },
    { value: 'KM', label: 'Comoros' },
    { value: 'CD', label: 'Congo (the Democratic Republic of the)' },
    { value: 'CG', label: 'Congo' },
    { value: 'CK', label: 'Cook Islands' },
    { value: 'CR', label: 'Costa Rica' },
    { value: 'HR', label: 'Croatia' },
    { value: 'CU', label: 'Cuba' },
    { value: 'CW', label: 'Curaçao' },
    { value: 'CY', label: 'Cyprus' },
    { value: 'CZ', label: 'Czechia' },
    { value: 'CI', label: 'Côte d\'Ivoire' },
    { value: 'DK', label: 'Denmark' },
    { value: 'DJ', label: 'Djibouti' },
    { value: 'DM', label: 'Dominica' },
    { value: 'DO', label: 'Dominican Republic' },
    { value: 'EC', label: 'Ecuador' },
    { value: 'EG', label: 'Egypt' },
    { value: 'SV', label: 'El Salvador' },
    { value: 'GQ', label: 'Equatorial Guinea' },
    { value: 'ER', label: 'Eritrea' },
    { value: 'EE', label: 'Estonia' },
    { value: 'SZ', label: 'Eswatini' },
    { value: 'ET', label: 'Ethiopia' },
    { value: 'FK', label: 'Falkland Islands [Malvinas]' },
    { value: 'FO', label: 'Faroe Islands' },
    { value: 'FJ', label: 'Fiji' },
    { value: 'FI', label: 'Finland' },
    { value: 'FR', label: 'France' },
    { value: 'GF', label: 'French Guiana' },
    { value: 'PF', label: 'French Polynesia' },
    { value: 'TF', label: 'French Southern Territories' },
    { value: 'GA', label: 'Gabon' },
    { value: 'GM', label: 'Gambia' },
    { value: 'GE', label: 'Georgia' },
    { value: 'DE', label: 'Germany' },
    { value: 'GH', label: 'Ghana' },
    { value: 'GI', label: 'Gibraltar' },
    { value: 'GR', label: 'Greece' },
    { value: 'GL', label: 'Greenland' },
    { value: 'GD', label: 'Grenada' },
    { value: 'GP', label: 'Guadeloupe' },
    { value: 'GU', label: 'Guam' },
    { value: 'GT', label: 'Guatemala' },
    { value: 'GG', label: 'Guernsey' },
    { value: 'GN', label: 'Guinea' },
    { value: 'GW', label: 'Guinea-Bissau' },
    { value: 'GY', label: 'Guyana' },
    { value: 'HT', label: 'Haiti' },
    { value: 'HM', label: 'Heard Island and McDonald Islands' },
    { value: 'VA', label: 'Holy See' },
    { value: 'HN', label: 'Honduras' },
    { value: 'HK', label: 'Hong Kong' },
    { value: 'HU', label: 'Hungary' },
    { value: 'IS', label: 'Iceland' },
    { value: 'IN', label: 'India' },
    { value: 'ID', label: 'Indonesia' },
    { value: 'IR', label: 'Iran' },
    { value: 'IQ', label: 'Iraq' },
    { value: 'IE', label: 'Ireland' },
    { value: 'IM', label: 'Isle of Man' },
    { value: 'IL', label: 'Israel' },
    { value: 'IT', label: 'Italy' },
    { value: 'JM', label: 'Jamaica' },
    { value: 'JP', label: 'Japan' },
    { value: 'JE', label: 'Jersey' },
    { value: 'JO', label: 'Jordan' },
    { value: 'KZ', label: 'Kazakhstan' },
    { value: 'KE', label: 'Kenya' },
    { value: 'KI', label: 'Kiribati' },
    { value: 'KP', label: 'Korea (Democratic People\'s Republic of)' },
    { value: 'KR', label: 'Korea, Republic of' },
    { value: 'KW', label: 'Kuwait' },
    { value: 'KG', label: 'Kyrgyzstan' },
    { value: 'LA', label: 'Lao People\'s Democratic Republic' },
    { value: 'LV', label: 'Latvia' },
    { value: 'LB', label: 'Lebanon' },
    { value: 'LS', label: 'Lesotho' },
    { value: 'LR', label: 'Liberia' },
    { value: 'LY', label: 'Libya' },
    { value: 'LI', label: 'Liechtenstein' },
    { value: 'LT', label: 'Lithuania' },
    { value: 'LU', label: 'Luxembourg' },
    { value: 'MO', label: 'Macao' },
    { value: 'MG', label: 'Madagascar' },
    { value: 'MW', label: 'Malawi' },
    { value: 'MY', label: 'Malaysia' },
    { value: 'MV', label: 'Maldives' },
    { value: 'ML', label: 'Mali' },
    { value: 'MT', label: 'Malta' },
    { value: 'MH', label: 'Marshall Islands' },
    { value: 'MQ', label: 'Martinique' },
    { value: 'MR', label: 'Mauritania' },
    { value: 'MU', label: 'Mauritius' },
    { value: 'YT', label: 'Mayotte' },
    { value: 'MX', label: 'Mexico' },
    { value: 'FM', label: 'Micronesia (Federated States of)' },
    { value: 'MD', label: 'Moldova, Republic of' },
    { value: 'MC', label: 'Monaco' },
    { value: 'MN', label: 'Mongolia' },
    { value: 'ME', label: 'Montenegro' },
    { value: 'MS', label: 'Montserrat' },
    { value: 'MA', label: 'Morocco' },
    { value: 'MZ', label: 'Mozambique' },
    { value: 'MM', label: 'Myanmar' },
    { value: 'NA', label: 'Namibia' },
    { value: 'NR', label: 'Nauru' },
    { value: 'NP', label: 'Nepal' },
    { value: 'NL', label: 'Netherlands' },
    { value: 'NC', label: 'New Caledonia' },
    { value: 'NZ', label: 'New Zealand' },
    { value: 'NI', label: 'Nicaragua' },
    { value: 'NE', label: 'Niger' },
    { value: 'NG', label: 'Nigeria' },
    { value: 'NU', label: 'Niue' },
    { value: 'NF', label: 'Norfolk Island' },
    { value: 'MP', label: 'Northern Mariana Islands' },
    { value: 'NO', label: 'Norway' },
    { value: 'OM', label: 'Oman' },
    { value: 'PK', label: 'Pakistan' },
    { value: 'PW', label: 'Palau' },
    { value: 'PS', label: 'Palestine, State of' },
    { value: 'PA', label: 'Panama' },
    { value: 'PG', label: 'Papua New Guinea' },
    { value: 'PY', label: 'Paraguay' },
    { value: 'PE', label: 'Peru' },
    { value: 'PH', label: 'Philippines' },
    { value: 'PN', label: 'Pitcairn' },
    { value: 'PL', label: 'Poland' },
    { value: 'PT', label: 'Portugal' },
    { value: 'PR', label: 'Puerto Rico' },
    { value: 'QA', label: 'Qatar' },
    { value: 'MK', label: 'Republic of North Macedonia' },
    { value: 'RO', label: 'Romania' },
    { value: 'RU', label: 'Russian Federation' },
    { value: 'RW', label: 'Rwanda' },
    { value: 'RE', label: 'Réunion' },
    { value: 'BL', label: 'Saint Barthélemy' },
    { value: 'SH', label: 'Saint Helena, Ascension and Tristan da Cunha' },
    { value: 'KN', label: 'Saint Kitts and Nevis' },
    { value: 'LC', label: 'Saint Lucia' },
    { value: 'MF', label: 'Saint Martin (French part)' },
    { value: 'PM', label: 'Saint Pierre and Miquelon' },
    { value: 'VC', label: 'Saint Vincent and the Grenadines' },
    { value: 'WS', label: 'Samoa' },
    { value: 'SM', label: 'San Marino' },
    { value: 'ST', label: 'Sao Tome and Principe' },
    { value: 'SA', label: 'Saudi Arabia' },
    { value: 'SN', label: 'Senegal' },
    { value: 'RS', label: 'Serbia' },
    { value: 'SC', label: 'Seychelles' },
    { value: 'SL', label: 'Sierra Leone' },
    { value: 'SG', label: 'Singapore' },
    { value: 'SX', label: 'Sint Maarten (Dutch part)' },
    { value: 'SK', label: 'Slovakia' },
    { value: 'SI', label: 'Slovenia' },
    { value: 'SB', label: 'Solomon Islands' },
    { value: 'SO', label: 'Somalia' },
    { value: 'ZA', label: 'South Africa' },
    { value: 'GS', label: 'South Georgia and the South Sandwich Islands' },
    { value: 'SS', label: 'South Sudan' },
    { value: 'ES', label: 'Spain' },
    { value: 'LK', label: 'Sri Lanka' },
    { value: 'SD', label: 'Sudan' },
    { value: 'SR', label: 'Suriname' },
    { value: 'SJ', label: 'Svalbard and Jan Mayen' },
    { value: 'SE', label: 'Sweden' },
    { value: 'CH', label: 'Switzerland' },
    { value: 'SY', label: 'Syrian Arab Republic' },
    { value: 'TW', label: 'Taiwan, Province of China' },
    { value: 'TJ', label: 'Tajikistan' },
    { value: 'TZ', label: 'Tanzania, United Republic of' },
    { value: 'TH', label: 'Thailand' },
    { value: 'TL', label: 'Timor-Leste' },
    { value: 'TG', label: 'Togo' },
    { value: 'TK', label: 'Tokelau' },
    { value: 'TO', label: 'Tonga' },
    { value: 'TT', label: 'Trinidad and Tobago' },
    { value: 'TN', label: 'Tunisia' },
    { value: 'TR', label: 'Turkey' },
    { value: 'TM', label: 'Turkmenistan' },
    { value: 'TC', label: 'Turks and Caicos Islands' },
    { value: 'TV', label: 'Tuvalu' },
    { value: 'UG', label: 'Uganda' },
    { value: 'UA', label: 'Ukraine' },
    { value: 'AE', label: 'United Arab Emirates' },
    { value: 'GB', label: 'United Kingdom of Great Britain and Northern Ireland' },
    { value: 'UM', label: 'United States Minor Outlying Islands' },
    { value: 'US', label: 'United States of America' },
    { value: 'UY', label: 'Uruguay' },
    { value: 'UZ', label: 'Uzbekistan' },
    { value: 'VU', label: 'Vanuatu' },
    { value: 'VE', label: 'Venezuela (Bolivarian Republic of)' },
    { value: 'VN', label: 'Viet Nam' },
    { value: 'VG', label: 'Virgin Islands (British)' },
    { value: 'VI', label: 'Virgin Islands (U.S.)' },
    { value: 'WF', label: 'Wallis and Futuna' },
    { value: 'EH', label: 'Western Sahara' },
    { value: 'YE', label: 'Yemen' },
    { value: 'ZM', label: 'Zambia' },
    { value: 'ZW', label: 'Zimbabwe' }
  ].sort((a, b) => a.label.localeCompare(b.label)); // Sort alphabetically


const VerifyData = () => {
  const { formData, setFormData, setCurrentStep } = useContext(TaxFormContext);
  const [validated, setValidated] = useState(false);
  const [isInstitutionEmployer, setIsInstitutionEmployer] = useState(false);
  
  // Initialize local state with both W2 and personal info
  const [localData, setLocalData] = useState({
    // W2 Data
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
    state: formData.w2Data.state || '',
    
    // Personal Info
    personalInfo: {
      firstName: '',
      lastName: '',
      ssn: formData.w2Data.employeeSSN ||'',
      usAddress: formData.w2Data.employeeAddress || '',  // This is the key change
      foreignAddress: '',
      citizenshipCountry: 'IN',
      passportCountry: 'IN',
      passportNumber: '',
      phoneNumber: '',
      email: '',
      visaType: 'F1',
      arrivalDate: '',
      daysInUS2023: '',
      daysInUS2022: '',
      daysInUS2021: '',
      academicInstitutionName: '',
      academicInstitutionAddress: '',
      academicInstitutionPhone: '',
      academicDirectorName: '',
      studyProgram: '',
      filingStatus: '',
      canBeClaimed: false,
      hadUSIncomePriorYears: false,
      scholarshipAmount: '',
      isFirstTimeStudent: true
    }
  });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    
    if (name === 'isInstitutionEmployer') {
      setIsInstitutionEmployer(checked);
      if (checked) {
        setLocalData(prev => ({
          ...prev,
          personalInfo: {
            ...prev.personalInfo,
            academicInstitutionName: prev.employerName,
            academicInstitutionAddress: prev.employerAddress
          }
        }));
      }
      return;
    }

    if (name === 'usAddress') {
      setLocalData(prev => ({
        ...prev,
        employeeAddress: value,
        personalInfo: {
          ...prev.personalInfo,
          usAddress: value
        }
      }));
      return;
    }

    if (name.startsWith('personal.')) {
      const personalField = name.split('.')[1];
      setLocalData(prev => ({
        ...prev,
        personalInfo: {
          ...prev.personalInfo,
          [personalField]: type === 'checkbox' ? checked : value
        }
      }));
    } else {
      setLocalData(prev => ({
        ...prev,
        [name]: value
      }));
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    
    if (form.checkValidity() === false) {
      event.stopPropagation();
      setValidated(true);
      return;
    }

    try {
      // Update global form data
      setFormData(prev => ({
        ...prev,
        w2Data: {
          employerName: localData.employerName,
          employerAddress: localData.employerAddress,
          employeeName: localData.employeeName,
          employeeAddress: localData.employeeAddress,
          employerEIN: localData.employerEIN,
          employeeSSN: localData.employeeSSN,
          wages: localData.wages,
          federalTaxWithheld: localData.federalTaxWithheld,
          stateTaxWithheld: localData.stateTaxWithheld,
          stateWages: localData.stateWages,
          state: localData.state
        },
        personalInfo: localData.personalInfo
      }));
      
      setCurrentStep(STEPS.REVIEW);
    } catch (error) {
      console.error('Error saving information:', error);
    }
  };

  return (
    <Card className="p-4">
      <Card.Title className="mb-4">Verify Information</Card.Title>
      <Form noValidate validated={validated} onSubmit={handleSubmit}>
        {/* W-2 Information Section */}
        <h5 className="border-bottom pb-2 mb-3">W-2 Information</h5>
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

{/* Employee Information from W-2 */}
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




        {/* General Personal Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Personal Information</h5>
        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>First Name</Form.Label>
              <Form.Control
                required
                type="text"
                name="personal.firstName"
                value={localData.personalInfo.firstName}
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
                name="personal.lastName"
                value={localData.personalInfo.lastName}
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
                name="personal.ssn"
                value={localData.personalInfo.ssn}
                onChange={handleInputChange}
                placeholder="XXX-XX-XXXX"
              />
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Phone Number</Form.Label>
              <Form.Control
                required
                type="tel"
                name="personal.phoneNumber"
                value={localData.personalInfo.phoneNumber}
                onChange={handleInputChange}
                placeholder="123-456-7890"
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Address Information */}
        <h6 className="mb-2">Addresses</h6>
        <Row>
          <Col md={12}>
            <Form.Group className="mb-3">
              <Form.Label>U.S. Address</Form.Label>
              <Form.Control
                required
                type="text"
                name="usAddress"
                value={localData.personalInfo.usAddress}
                onChange={handleInputChange}
              />
              {/* <Form.Text className="text-muted">
                This address will also be used as your employee address
              </Form.Text> */}
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
                name="personal.foreignAddress"
                value={localData.personalInfo.foreignAddress}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Immigration Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Immigration Information</h5>
        <Row>
        <Col md={6}>
    <Form.Group className="mb-3">
      <Form.Label>Of what country or countries were you a citizen during the tax year?</Form.Label>
      <Select
        value={countries.find(country => country.value === localData.personalInfo.citizenshipCountry)}
        onChange={(selectedOption) => {
          handleInputChange({
            target: {
              name: 'personal.citizenshipCountry',
              value: selectedOption.value
            }
          });
        }}
        options={countries}
        isSearchable={true}
        placeholder="Select country..."
        className="basic-single"
      />
    </Form.Group>
  </Col>
  <Col md={6}>
    <Form.Group className="mb-3">
      <Form.Label>What country or countries issued you a passport?</Form.Label>
      <Select
        value={countries.find(country => country.value === localData.personalInfo.passportCountry)}
        onChange={(selectedOption) => {
          handleInputChange({
            target: {
              name: 'personal.passportCountry',
              value: selectedOption.value
            }
          });
        }}
        options={countries}
        isSearchable={true}
        placeholder="Select country..."
        className="basic-single"
      />
    </Form.Group>
  </Col>
          <Col md={4}>
            <Form.Group className="mb-3">
              <Form.Label>Passport Number</Form.Label>
              <Form.Control
                required
                type="text"
                name="personal.passportNumber"
                value={localData.personalInfo.passportNumber}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Visa Type</Form.Label>
              <Form.Select
                required
                name="personal.visaType"
                value={localData.personalInfo.visaType}
                onChange={handleInputChange}
              >
                <option value="">Select Visa Type</option>
                <option value="F1">F-1 Student</option>
                <option value="J1">J-1 Exchange Visitor</option>
              </Form.Select>
            </Form.Group>
          </Col>
          <Col md={6}>
            <Form.Group className="mb-3">
              <Form.Label>Latest Arrival Date</Form.Label>
              <Form.Control
                required
                type="date"
                name="personal.arrivalDate"
                value={localData.personalInfo.arrivalDate}
                onChange={handleInputChange}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col md={4}>
            <Form.Group className="mb-3">
              <Form.Label>Days in US (2023)</Form.Label>
              <Form.Control
                required
                type="number"
                name="personal.daysInUS2023"
                value={localData.personalInfo.daysInUS2023}
                onChange={handleInputChange}
                min="0"
                max="365"
              />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group className="mb-3">
              <Form.Label>Days in US (2022)</Form.Label>
              <Form.Control
                required
                type="number"
                name="personal.daysInUS2022"
                value={localData.personalInfo.daysInUS2022}
                onChange={handleInputChange}
                min="0"
                max="365"
              />
            </Form.Group>
          </Col>
          <Col md={4}>
            <Form.Group className="mb-3">
              <Form.Label>Days in US (2021)</Form.Label>
              <Form.Control
                required
                type="number"
                name="personal.daysInUS2021"
                value={localData.personalInfo.daysInUS2021}
                onChange={handleInputChange}
                min="0"
                max="365"
              />
            </Form.Group>
          </Col>
        </Row>

        {/* Academic Information */}
        <h5 className="border-bottom pb-2 mb-3 mt-4">Academic Information</h5>
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
                name="personal.academicInstitutionName"
                value={localData.personalInfo.academicInstitutionName}
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
              <Form.Label>NAME- DIRECTOR OF ACADMIC PROGRAM</Form.Label>
              <Form.Control
                required
                type="text"
                name="personal.studyProgram"
                value={localData.personalInfo.studyProgram}
                onChange={handleInputChange}
                placeholder="e.g., Masters in Computer Science"
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
                name="personal.academicInstitutionAddress"
                value={localData.personalInfo.academicInstitutionAddress}
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

        

{/* Tax Filing Information */}
<h5 className="border-bottom pb-2 mb-3 mt-4">Tax Filing Information</h5>
<Row>
  <Col md={6}>
    <Form.Group className="mb-3">
      <Form.Label>Filing Status</Form.Label>
      <Form.Select
        required
        name="personal.filingStatus"
        value={localData.personalInfo.filingStatus}
        onChange={handleInputChange}
      >
        <option value="">Select Filing Status</option>
        <option value="single">Single</option>
        <option value="marriedSeparately">Married Filing Separately</option>
      </Form.Select>
    </Form.Group>
  </Col>
  <Col md={6}>
    <Form.Group className="mb-3">
      <Form.Label>Scholarship Amount (if any)</Form.Label>
      <Form.Control
        type="number"
        name="personal.scholarshipAmount"
        value={localData.personalInfo.scholarshipAmount}
        onChange={handleInputChange}
      />
    </Form.Group>
  </Col>
</Row>

<Row>
  <Col md={12}>
    <Form.Group className="mb-3">
      <Form.Check
        type="checkbox"
        label="Someone else can claim me as a dependent"
        name="personal.canBeClaimed"
        checked={localData.personalInfo.canBeClaimed}
        onChange={handleInputChange}
      />
    </Form.Group>
    <Form.Group className="mb-3">
      <Form.Check
        type="checkbox"
        label="I had US source income in previous years"
        name="personal.hadUSIncomePriorYears"
        checked={localData.personalInfo.hadUSIncomePriorYears}
        onChange={handleInputChange}
      />
    </Form.Group>
  </Col>
</Row>
        <div className="d-flex justify-content-between mt-4">
          <Button 
            variant="secondary" 
            onClick={() => setCurrentStep(STEPS.UPLOAD)}
          >
            Back
          </Button>
          <Button variant="primary" type="submit">
            Continue to Review
          </Button>
        </div>
      </Form>
    </Card>
  );
};

export default VerifyData;