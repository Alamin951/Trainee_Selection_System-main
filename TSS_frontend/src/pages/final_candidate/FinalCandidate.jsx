import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';

const FinalCandidate = () => {
  const [candidateData, setCandidateData] = useState([]);

  useEffect(() => {
    // Fetch data from the API
    fetch('http://localhost:8082/applications/final-candidate')
      .then((response) => response.json())
      .then((data) => {
        // Add unique 'id' property to each row
        const rowsWithIds = data.map((row, index) => ({ ...row, id: index + 1 }));
        setCandidateData(rowsWithIds);
      })
      .catch((error) => {
        console.error('Error fetching candidate data:', error);
      });
  }, []);

  // Columns configuration for the Data Grid
  const columns = [
    { field: 'id', headerName: 'Application ID', width: 100 },
    {
      field: 'name',
      headerName: 'Name',
      width: 200,
      valueGetter: (params) => `${params.row.applicants.firstName} ${params.row.applicants.lastName}`,
    },
    { field: 'email', headerName: 'Email', width: 200, valueGetter: (params) => params.row.applicants.email },
    { field: 'degreeName', headerName: 'Degree', width: 150, valueGetter: (params) => params.row.applicants.degreeName },
    { field: 'circularName', headerName: 'Circular Name', width: 200, valueGetter: (params) => params.row.jobCircular.circularName },
    { field: 'status', headerName: 'Status', width: 150,valueGetter: (params) => params.row.status.status},
    { field: 'marks', headerName: 'Marks', width: 150,valueGetter: (params) => params.row.marks},
  ];

  return (
    <div style={{ height: 400, width: '100%' }}>
      <DataGrid rows={candidateData} columns={columns} pageSize={5} />
    </div>
  );
};

export default FinalCandidate;
