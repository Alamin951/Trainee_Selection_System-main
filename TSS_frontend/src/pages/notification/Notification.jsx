import React, { useState, useEffect } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import axios from 'axios';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import TextField from '@mui/material/TextField';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const columns = [
  { field: 'noticeId', headerName: 'Notice ID', width: 150 },
  { field: 'message', headerName: 'Message', width: 300 },
  { field: 'applicationId', headerName: 'Application ID', width: 150 },
  { field: 'applicantId', headerName: 'Applicant ID', width: 150 },
  { field: 'date', headerName: 'Date', width: 200 },
];

const Notification = () => {
  const [notifications, setNotifications] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newMessage, setNewMessage] = useState('');

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const response = await axios.get('http://localhost:8082/notices');
      setNotifications(response.data);
    } catch (error) {
      console.error('Error fetching notifications:', error);
    }
  };

  const handleAddButtonClick = () => {
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setNewMessage('');
  };

  const handleNewMessageChange = (e) => {
    setNewMessage(e.target.value);
  };

  const handleAddNewNotification = async () => {
    try {
      const response = await axios.post('http://localhost:8082/notices/global', {
        message: newMessage,
      });
      console.log('Notification added:', response.data);
      toast.success('Notification added successfully.', {
        position: 'top-right',
        autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
      });
      fetchNotifications(); // Re-fetch the notifications to update the table
      handleModalClose(); // Close the modal after successful addition
    } catch (error) {
      console.error('Error adding notification:', error);
      toast.error('Failed to add notification. Please try again.', {
        position: 'top-right',
        autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
      });
    }
  };

  const rows = notifications.map((notification) => ({
    id: notification.noticeId,
    noticeId: notification.noticeId,
    message: notification.message,
    applicationId: notification.applications?.applicationId || '',
    applicantId: notification.applications?.applicantId || '',
    date: new Date(notification.date).toLocaleString(),
  }));

  return (
    <div style={{ height: 500, width: '100%', maxWidth: '1000px', margin: '0 auto', marginTop: '10px' }}>
        {/* Page Title */}
        <Box display="flex" justifyContent="center" alignItems="center" flexDirection="column" mb={3}>
        {/* Page Title */}
        <Typography variant="h4" gutterBottom>
          Notification Center
        </Typography>
        </Box>
      <Button variant="contained" color="primary" onClick={handleAddButtonClick}>
        Add
      </Button>
      <DataGrid
        rows={rows}
        columns={columns}
        autoHeight={true}
        disableColumnMenu={true}
        components={{
          Toolbar: GridToolbar,
        }}
      />
      <ToastContainer />
      {/* Modal to add new notification */}
      <Modal open={isModalOpen} onClose={handleModalClose}>
        <Box
          sx={{
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)',
            width: 400,
            bgcolor: 'background.paper',
            boxShadow: 24,
            p: 4,
          }}
        >
          <Typography variant="h6" gutterBottom>
            Add New Notification
          </Typography>
          <TextField
            label="Message"
            multiline
            rows={4}
            variant="outlined"
            fullWidth
            value={newMessage}
            onChange={handleNewMessageChange}
          />
          <Button variant="contained" color="primary" onClick={handleAddNewNotification} style={{ marginTop: '10px' }}>
            Add Notification
          </Button>
          <Button variant="outlined" color="secondary" onClick={handleModalClose} style={{ marginLeft: '10px' }}>
            Cancel
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

export default Notification;
