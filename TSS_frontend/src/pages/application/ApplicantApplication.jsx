import React, { useState, useEffect } from 'react';
import Typography from '@mui/material/Typography';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import TableSortLabel from '@mui/material/TableSortLabel';

const ApplicantApplication = () => {
  const [applications, setApplications] = useState([]);
  const [sortBy, setSortBy] = useState('appliedDate');
  const [sortOrder, setSortOrder] = useState('asc');
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    if (userId) {
      fetchApplications(userId);
    } else {
      console.error('User ID not found in Local Storage');
    }
  }, [userId]);

  const fetchApplications = (userId) => {
    fetch(`http://localhost:8082/applications/applicant/${userId}`)
      .then((response) => response.json())
      .then((data) => {
        setApplications(data);
      })
      .catch((error) => {
        console.error('Error fetching applications:', error);
      });
  };

  const handleSort = (property) => {
    const isAsc = sortBy === property && sortOrder === 'asc';
    setSortBy(property);
    setSortOrder(isAsc ? 'desc' : 'asc');
    const sortedApplications = [...applications].sort((a, b) => {
      if (isAsc) {
        return a[property] > b[property] ? 1 : -1;
      } else {
        return a[property] < b[property] ? 1 : -1;
      }
    });
    setApplications(sortedApplications);
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom align="center" fontWeight="bold" sx={{ marginTop: '20px' }}>
        Your Applications
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <TableSortLabel
                  active={sortBy === 'circularName'}
                  direction={sortOrder}
                  onClick={() => handleSort('circularName')}
                >
                  Circular Name
                </TableSortLabel>
              </TableCell>
              <TableCell>
                <TableSortLabel
                  active={sortBy === 'description'}
                  direction={sortOrder}
                  onClick={() => handleSort('description')}
                >
                  Description
                </TableSortLabel>
              </TableCell>
              <TableCell>
                <TableSortLabel
                  active={sortBy === 'appliedDate'}
                  direction={sortOrder}
                  onClick={() => handleSort('appliedDate')}
                >
                  Applied Date
                </TableSortLabel>
              </TableCell>
              <TableCell>
                <TableSortLabel
                  active={sortBy === 'status'}
                  direction={sortOrder}
                  onClick={() => handleSort('status')}
                >
                  Status
                </TableSortLabel>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {applications.map((application) => (
              <TableRow key={application.applicationId}>
                <TableCell>{application.jobCircular.circularName}</TableCell>
                <TableCell>{application.jobCircular.description}</TableCell>
                <TableCell>{application.appliedDate}</TableCell>
                <TableCell>{application.status.status}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default ApplicantApplication;
