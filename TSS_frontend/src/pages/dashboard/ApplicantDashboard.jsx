import React, { useState, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';

const ApplicantDashboard = () => {
  const [data, setData] = useState([]);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    circularId: '',
    applicantId: '' 
  });
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    const applicantIdFromStorage = localStorage.getItem('userId');  
    if (applicantIdFromStorage) {
      setFormData((prevFormData) => ({ ...prevFormData, applicantId: parseInt(applicantIdFromStorage) }));
    } else {
      // Handle the case when applicantId is not available in Local Storage
      console.error('Applicant ID not found in Local Storage');
    }
  
    fetchJobCirculars();
  }, []);

  const fetchJobCirculars = () => {
    fetch('http://localhost:8082/job_circular')
      .then(response => response.json())
      .then(jsonData => {
        setData(jsonData);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  // Styling for the warning card
  const warningCardStyle = {
    border: '2px solid red',
    borderRadius: '8px',
    backgroundColor: 'pink',
    marginBottom: '20px',
    padding: '16px',
  };

  const handleApply = (circularId) => {
    // Update circularId in formData
    setFormData((prevFormData) => ({ ...prevFormData, circularId }));
  
    // Perform the POST request using formData
    fetch('http://localhost:8082/applications', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        circularId,
        applicantId: formData.applicantId // Use applicantId from formData
      })
    })
    .then(response => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Already applied!!!');
      }
    })
    .then(jsonData => {
      console.log('POST request successful:', jsonData);
      setSuccessMessage('Application submitted successfully!'); // Set success message
      // Update the list of job circulars by fetching them again (optional, in case the API response updates the data)
      fetchJobCirculars();
    })
    .catch(error => {
      console.error('Error performing POST request:', error);
      setErrorMessage(error.message); // Set error message
    });
  };

  return (
    <div>
      {successMessage && (
        <Modal open={Boolean(successMessage)} onClose={() => setSuccessMessage('')}>
          <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
            <Typography variant="h6" component="div" sx={{ marginBottom: '20px' }}>
              {successMessage}
            </Typography>
            <Button variant="contained" onClick={() => setSuccessMessage('')}>
              Close
            </Button>
          </Box>
        </Modal>
      )}

      {/* Warning Card */}
      {errorMessage && (
        <Modal open={Boolean(errorMessage)} onClose={() => setErrorMessage('')}>
          <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
            <Card style={warningCardStyle}>
              <Typography variant="h6" component="div" sx={{ marginBottom: '20px' }}>
                {errorMessage}
              </Typography>
              <Button variant="contained" onClick={() => setErrorMessage('')} style={{ backgroundColor: 'red', color: 'white' }}>
                Close
              </Button>
            </Card>
          </Box>
        </Modal>
      )}

      <Box sx={{ display: 'flex', flexWrap: 'wrap', marginTop: '10px' }}>
        {data.map(circular => (
          <Card key={circular.circularId} sx={{ width: 300, marginBottom: '20px', marginRight: '20px' }}>
            <CardContent>
              <Typography variant="h6" component="div">
                {circular.circularName}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {circular.description}
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ marginTop: '10px' }}>
                Application Deadline: {circular.applicationDeadline}
              </Typography>
            </CardContent>
            <CardActions>
              {/* Pass circularId to handleApply */}
              <Button size="small" onClick={() => handleApply(circular.circularId)}>Apply</Button>
            </CardActions>
          </Card>
        ))}
      </Box>
    </div>
  );
};

export default ApplicantDashboard;
