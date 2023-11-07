import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    gender: '',
    dateOfBirth: '',
    email: '',
    contactNumber: '',
    degreeName: '',
    educationalInstitute: '',
    cgpa: '',
    passingYear: '',
    presentAddress: '',
  });

  const [photoFile, setPhotoFile] = useState(null);
  const [resumeFile, setResumeFile] = useState(null);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  };

  const handlePhotoChange = (event) => {
    const file = event.target.files[0];
    setPhotoFile(file);
  };

  const handleResumeChange = (event) => {
    const file = event.target.files[0];
    setResumeFile(file);
  };

  const handleSubmit = () => {
    const formPayload = {
      ...formData,
      photoUrl: photoFile,
      resumeUrl: resumeFile,
      password: '1234', // You can handle password input separately if needed
    };

    // Prepare the form data for file upload
    const formData = new FormData();
    formData.append('firstName', formPayload.firstName);
    formData.append('lastName', formPayload.lastName);
    formData.append('gender', formPayload.gender);
    formData.append('dateOfBirth', formPayload.dateOfBirth);
    formData.append('email', formPayload.email);
    formData.append('contactNumber', formPayload.contactNumber);
    formData.append('degreeName', formPayload.degreeName);
    formData.append('educationalInstitute', formPayload.educationalInstitute);
    formData.append('cgpa', formPayload.cgpa);
    formData.append('passingYear', formPayload.passingYear);
    formData.append('presentAddress', formPayload.presentAddress);
    formData.append('photo', photoFile);
    formData.append('resume', resumeFile);
    formData.append('password', formPayload.password);

    // Send the register request to the API
    fetch('http://localhost:8082/applicants', {
      method: 'POST',
      body: formData,
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Registration successful:', data);
        // You can handle successful registration here (e.g., show success message, redirect to login page, etc.)
      })
      .catch((error) => {
        console.error('Error registering:', error);
        // Handle the registration error
      });
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Box sx={{ width: 400, p: 4, boxShadow: 1, borderRadius: 4 }}>
        <Typography variant="h5" align="center" gutterBottom>
          Register
        </Typography>
        <form>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="First Name"
              variant="outlined"
              fullWidth
              name="firstName"
              value={formData.firstName}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Last Name"
              variant="outlined"
              fullWidth
              name="lastName"
              value={formData.lastName}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Gender"
              variant="outlined"
              fullWidth
              name="gender"
              value={formData.gender}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Email"
              variant="outlined"
              fullWidth
              name="email"
              value={formData.email}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Date Of Birth"
              variant="outlined"
              fullWidth
              name="dateOfBirth"
              value={formData.dateOfBirth}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Contact Number"
              variant="outlined"
              fullWidth
              name="contactNumber"
              value={formData.contactNumber}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Degree Name"
              variant="outlined"
              fullWidth
              name="degreeName"
              value={formData.degreeName}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Educational Institute"
              variant="outlined"
              fullWidth
              name="educationalInstitute"
              value={formData.educationalInstitute}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="cgpa"
              variant="outlined"
              fullWidth
              name="cgpa"
              value={formData.cgpa}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Passing Year"
              variant="outlined"
              fullWidth
              name="passingYear"
              value={formData.passingYear}
              onChange={handleInputChange}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Present Address"
              variant="outlined"
              fullWidth
              name="presentAddress"
              value={formData.presentAddress}
              onChange={handleInputChange}
            />
          </Box>
          {/* Add more input fields for other form fields */}
          {/* ... */}
          <Box sx={{ mb: 2 }}>
            <input type="file" accept="image/*" onChange={handlePhotoChange} />
          </Box>
          <Box sx={{ mb: 2 }}>
            <input type="file" accept=".pdf" onChange={handleResumeChange} />
          </Box>
          <Button variant="contained" color="primary" fullWidth onClick={handleSubmit}>
            Register
          </Button>
        </form>
        <p>Don't have an account? <Link to="/login">Login here</Link>.</p>
      </Box>
    </Box>
  );
};

export default Register;
