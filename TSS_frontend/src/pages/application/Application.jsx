import React, { useEffect, useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const columns = [
  { field: 'applicationId', headerName: 'Application Id', width: 120 },
  { field: 'circularName', headerName: 'Circular Name', width: 200 },
  {
    field: 'name',
    headerName: 'Name',
    width: 150,
    renderCell: (params) => {
      const fullName = `${params.row.firstName} ${params.row.lastName}`;
      return <div>{fullName}</div>;
    },
  },
  { field: 'gender', headerName: 'Gender', width: 140 },
  { field: 'email', headerName: 'Email', width: 180 },
  { field: 'degreeName', headerName: 'Degree Name', width: 140 },
  { field: 'educationalInstitute', headerName: 'Educational Institute', width: 200 },
  { field: 'cgpa', headerName: 'CGPA', width: 140 },
  { field: 'passingYear', headerName: 'Passing Year', width: 145 },
  { field: 'appliedDate', headerName: 'Applied Date', width: 110 },
  {
    field: 'status',
    headerName: 'Status',
    width: 150,
    renderCell: (params) => {
      const [anchorEl, setAnchorEl] = useState(null);
      const [statusOptions, setStatusOptions] = useState([]);
      const [selectedStatus, setSelectedStatus] = useState(params.value);

      useEffect(() => {
        // Fetch the status options from the API
        fetch('http://localhost:8082/status')
          .then((response) => response.json())
          .then((data) => {
            setStatusOptions(data.map((statusObj) => statusObj.status));
          })
          .catch((error) => console.error('Error fetching status options:', error));
      }, []);

      const handleStatusSelection = (status) => {
        setSelectedStatus(status); // Update the selectedStatus with the newly selected status
        handleStatusChange(params.row.applicationId, status); // Call the handleStatusChange function when a new status is selected
        handleClose();
      };

      const handleStatusChange = (applicationId, selectedStatus) => {
        // Perform the PATCH request to update the status in the database
        const payload = {
          applicationId: applicationId,
          status: selectedStatus,
        };
        console.log(payload);
        fetch('http://localhost:8082/applications/update-status', {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(payload),
        })
          .then((response) => {
            if (!response.ok) {
              throw new Error('Failed to update status');
            }
            // Update the local state with the new status if PATCH request is successful
            const updatedData = [...data];
            const rowIndex = updatedData.findIndex((row) => row.applicationId === applicationId);
            if (rowIndex > -1) {
              updatedData[rowIndex].status = selectedStatus;
              setData(updatedData);
            }
            // Show success toast
            toast.success('Status updated successfully.', {
              position: 'top-right',
              autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
            });
          })
          .catch((error) => {
            // Handle error in PATCH request
            // Show error toast
            toast.error('Failed to update status. Please try again.', {
              position: 'top-right',
              autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
            });
          })
          .finally(() => {
            handleClose();
          });
      };

      const handleClick = (event) => {
        setAnchorEl(event.currentTarget);
      };

      const handleClose = () => {
        setAnchorEl(null);
      };

      return (
        <div>
          <Button onClick={handleClick}>{selectedStatus}</Button>
          <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleClose}>
            {statusOptions.map((status, index) => (
              <MenuItem key={index} onClick={() => handleStatusSelection(status)}>
                {status}
              </MenuItem>
            ))}
          </Menu>
        </div>
      );
    },
  },
];

const theme = createTheme({
  components: {
    MuiDataGrid: {
      styleOverrides: {
        root: {
          backgroundColor: '#f7f7f7',
          fontFamily: 'Arial, sans-serif',
        },
        columnHeader: {
          backgroundColor: '#e0e0e0',
          fontWeight: 'bold',
        },
      },
    },
  },
});

const Application = () => {
  const [data, setData] = useState([]);
  const [newSelectedStatus, setNewSelectedStatus] = useState('');

  useEffect(() => {
    fetch('http://localhost:8082/applications/all')
      .then((response) => response.json())
      .then((jsonData) => {
        const updatedData = jsonData.map((item, index) => {
          const appliedDate = new Date(item.appliedDate); // Create a Date object from the appliedDate string
          const formattedDate = `${appliedDate.getDate()}-${appliedDate.toLocaleString('default', { month: 'long' })}`; // Format the date as "day-month"

          return {
            id: index + 1,
            applicationId: item.applicationId,
            firstName: item.applicants.firstName,
            lastName: item.applicants.lastName,
            gender: item.applicants.gender,
            email: item.applicants.email,
            degreeName: item.applicants.degreeName,
            educationalInstitute: item.applicants.educationalInstitute,
            cgpa: item.applicants.cgpa,
            passingYear: item.applicants.passingYear,
            circularName: item.jobCircular.circularName,
            appliedDate: formattedDate, // Use the formatted date
            status: item.status.status,
          };
        });
        setData(updatedData);
      })
      .catch((error) => console.error('Error fetching data:', error));
  }, []);

  return (
    <div style={{ width: '100%', maxWidth: '1000px', margin: '0 auto', marginTop: '10px' }}>
      <ThemeProvider theme={theme}>
        <div style={{ height: '500px', width: '100%', overflow: 'auto' }}>
          <DataGrid
            rows={data}
            columns={columns}
            autoHeight={true}
            disableExtendRowFullWidth={true}
            disableColumnMenu={true}
            scrollbarSize={10}
            components={{
              Toolbar: GridToolbar,
            }}
          />
        </div>
      </ThemeProvider>
      <ToastContainer />
    </div>
  );
};

export default Application;
