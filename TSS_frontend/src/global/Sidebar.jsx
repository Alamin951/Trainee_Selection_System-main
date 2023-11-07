import React, { useEffect, useState } from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import { Link } from 'react-router-dom';
import Dashboard from '../pages/dashboard/Dashboard';

const SidebarWrapper = styled(Box)(({ theme }) => ({
  backgroundColor: theme.palette.primary.main,
  color: theme.palette.primary.contrastText,
  width: 240,
  paddingTop: theme.spacing(2),
  height: '100%',
  minHeight: '90vh',
}));

const SidebarItem = styled(ListItem)(({ theme }) => ({
  '&:hover': {
    backgroundColor: theme.palette.primary.light,
    color: theme.palette.primary.dark,
  },
}));

const ContentWrapper = styled(Box)({
  flexGrow: 1,
  marginLeft: 240,
  padding: '16px', // Adjust the padding as needed
});

const Sidebar = () => {
  // State to hold the user role
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
    // Retrieve user role from Local Storage on app load
    const roleFromStorage = localStorage.getItem('role');
    setUserRole(roleFromStorage);
  }, []);
  const handleLogout = () => {
    // Clear all stored data from local storage
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('role');
    // Redirect to the login page
    window.location.href = '/login';
  };

  return (
    <Box sx={{ display: 'flex' }}>
      <SidebarWrapper>
        <List component="nav">
          {/* Conditional rendering based on userRole */}
          {userRole === 'applicant' && (
            <>
              <SidebarItem button component={Link} to="/applicant-dashboard">
                <ListItemText primary="Dashboard" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/application-candidate">
                <ListItemText primary="Applications" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/approval">
                <ListItemText primary="Approval" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/admit-card">
                <ListItemText primary="Admit Card" />
              </SidebarItem>
              <SidebarItem button onClick={handleLogout}>
            <ListItemText primary="Logout" />
          </SidebarItem>
            </>
          )}

          {userRole === 'admin' && (
            <>
              <SidebarItem button component={Link} to="/job-circular">
                <ListItemText primary="Circular" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/application">
                <ListItemText primary="Applications" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/evaluator">
                <ListItemText primary="Evaluator" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/assign-evaluator">
                <ListItemText primary="Assign Evaluator" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/upload-marks">
                <ListItemText primary="Upload Marks" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/final-candidate">
                <ListItemText primary="Selected Candidate" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/notice">
                <ListItemText primary="Notice" />
              </SidebarItem>
              <SidebarItem button onClick={handleLogout}>
            <ListItemText primary="Logout" />
          </SidebarItem>
            </>
          )}

            {userRole === 'evaluator' && (
            <>
              <SidebarItem button component={Link} to="/">
                <ListItemText primary="Dashboard" />
              </SidebarItem>
              <SidebarItem button component={Link} to="/written-mark">
                <ListItemText primary="Upload Mark" />
              </SidebarItem>
              <SidebarItem button onClick={handleLogout}>
            <ListItemText primary="Logout" />
          </SidebarItem>
            </>
          )}

          {/* Always show Login link */}
          {/* <SidebarItem button component={Link} to="/login">
            <ListItemText primary="Login" />
          </SidebarItem> */}
          
        </List>
      </SidebarWrapper>
    </Box>
  );
};

export default Sidebar;
