import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');


  const handleLogin = () => {
    // Create the request body
    const requestBody = {
      email: email,
      password: password,
    };

    // Send the login request to the API
    fetch('http://localhost:8082/user/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(requestBody),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Login successful:', data);
        // Handle the successful login response
        // Store the JWT token in the local storage
        localStorage.setItem('token', data.token);
        localStorage.setItem('userId', data.userId); // Corrected key to 'userId'
        localStorage.setItem('role', data.role);

        // Redirect based on user role
        switch (data.role) {
          case 'admin':
            window.location.href = '/dashboard'; // Redirect to admin dashboard
            break;
          case 'applicant':
            window.location.href = '/applicant-dashboard'; // Redirect to applicant dashboard
            break;
          case 'evaluator':
            window.location.href = '/written-mark'; // Redirect to evaluator dashboard (change to the desired route)
            break;
          default:
            window.location.href = '/'; // Redirect to a default page if the role is not recognized
        }
      })
      .catch((error) => {
        console.error('Error logging in:', error);
        // Handle the login error
      });
  };

  return (
    <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
      <Box sx={{ width: 400, p: 4, boxShadow: 1, borderRadius: 4 }}>
        <Typography variant="h5" align="center" gutterBottom>
          Login
        </Typography>
        <form>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Email"
              type="email"
              variant="outlined"
              fullWidth
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </Box>
          <Box sx={{ mb: 2 }}>
            <TextField
              label="Password"
              type="password"
              variant="outlined"
              fullWidth
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </Box>
          <Button variant="contained" color="primary" fullWidth onClick={handleLogin}>
            Login
          </Button>
        </form>
        <p>
          Don't have an account? <Link to="/register">Register here</Link>.
        </p>
      </Box>
    </Box>
  );
};

export default Login;
