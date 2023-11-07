import React from "react";
import { useState, useEffect } from "react";
import "./App.css";

import { BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import Navbar from "./global/Navbar";
import Sidebar from "./global/Sidebar";
import Dashboard from "./pages/dashboard/Dashboard";
import ApplicantDashboard from "./pages/dashboard/ApplicantDashboard";
import Application from "./pages/application/Application";
import ApplicantApplication from "./pages/application/ApplicantApplication";

import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';

import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Login from "./pages/auth/Login";
import JobCirculars from "./pages/job-circular/JobCircular";
import Evaluator from "./pages/Evaluator/Evaluator";
import Approval from "./pages/approval/Approval";
import AssignEvaluator from "./pages/Evaluator/AssignEvaluator";
import Written from "./pages/marking/Written"
import Marks from "./pages/marking/Marks";
import Register from "./pages/auth/Register";
import FinalCandidate from "./pages/final_candidate/FinalCandidate";
import Notification from "./pages/notification/Notification"
import AdmitCard from "./pages/admitCard/AdmitCard";

function App() {
  const [userRole, setUserRole] = useState('');
  const [userLoggedIn, setUserLoggedIn] = useState(false);
  

  useEffect(() => {
    // Check if the user is logged in
    const token = localStorage.getItem('token');
    if (token) {
      setUserLoggedIn(true);
    }
    // Retrieve user role from Local Storage on app load
    const roleFromStorage = localStorage.getItem('role');
    setUserRole(roleFromStorage);
  }, []);

  // Custom wrapper component for protected routes
  const ProtectedRoute = ({ element, allowedRoles }) => {
    if (allowedRoles.includes(userRole)) {
      return element;
    } else {
      // Redirect to some other page (e.g., login page) if the user role is not allowed
      return <Navigate to="/login" />;
    }
  };
  return (
    <>
      {!userLoggedIn && (
        <Routes>
          {/* Public route accessible to all */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      )}

      {userLoggedIn && (
    <>
      
      <Navbar />
      <Box sx={{ display: 'flex' }}>
        
        <Sidebar />
        
        <Box component="main" sx={{ flexGrow: 1, bgcolor: 'background.default' }}>
          <Container maxWidth="xl">
          <Routes>
              {/* Protected routes with allowedRoles */}
              <Route
                path="/"
                element={<ProtectedRoute element={<Dashboard />} allowedRoles={['admin', 'applicant', 'evaluator']} />}
              />

              {/* admin */}
              
              <Route
                path="/dashboard"
                element={<ProtectedRoute element={<Dashboard />} allowedRoles={['admin']} />}
              />
              <Route
                path="/job-circular"
                element={<ProtectedRoute element={<JobCirculars />} allowedRoles={['admin']} />}
              />
              <Route
                path="/evaluator"
                element={<ProtectedRoute element={<Evaluator />} allowedRoles={['admin']} />}
              />
              <Route
                path="/application"
                element={<ProtectedRoute element={<Application />} allowedRoles={['admin']} />}
              />
              <Route
                path="/assign-evaluator"
                element={<ProtectedRoute element={<AssignEvaluator />} allowedRoles={['admin']} />}
              />
              <Route
                path="/upload-marks"
                element={<ProtectedRoute element={<Marks />} allowedRoles={['admin']} />}
              />
              <Route
                path="/final-candidate"
                element={<ProtectedRoute element={<FinalCandidate />} allowedRoles={['admin']} />}
              />
              <Route
                path="/notice"
                element={<ProtectedRoute element={<Notification />} allowedRoles={['admin']} />}
              />


              {/* Applicant */}
              <Route
                path="/applicant-dashboard"
                element={<ProtectedRoute element={<ApplicantDashboard />} allowedRoles={['applicant']} />}
              />              
              <Route
                path="/application-candidate"
                element={<ProtectedRoute element={<ApplicantApplication />} allowedRoles={['applicant']} />}
              />
              <Route
                path="/approval"
                element={<ProtectedRoute element={<Approval />} allowedRoles={['applicant']} />}
              />
              <Route
                path="/admit-card"
                element={<ProtectedRoute element={<AdmitCard />} allowedRoles={['applicant']} />}
              />

              {/* Evaluator */}
              <Route
                path="/written-mark"
                element={<ProtectedRoute element={<Written />} allowedRoles={['evaluator']} />}
              />
              
              {/* Public route accessible to all */}
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              </Routes>
          </Container>
        </Box>
      </Box>
      </>
      )}
    </>
  );
  
}

export default App;
