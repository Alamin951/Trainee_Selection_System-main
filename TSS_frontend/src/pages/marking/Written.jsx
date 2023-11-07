import React, { useEffect, useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Written = () => {
  const [data, setData] = useState([]);
  const [selectedRow, setSelectedRow] = useState(null);
  const userId = localStorage.getItem('userId');
  const [open, setOpen] = useState(false);
  const [mark, setMark] = useState('');
  const [comment, setComment] = useState('');

  useEffect(() => {
    fetch(`http://localhost:8082/answerSheet/${userId}`)
      .then((response) => response.json())
      .then((jsonData) => {
        // Add a unique id to each row
        const dataWithIds = jsonData.map((row, index) => ({ ...row, id: index + 1 }));
        setData(dataWithIds);
      })
      .catch((error) => console.error('Error fetching data:', error));
  }, [userId]);

  const handleUpdate = (row) => {
    setSelectedRow(row);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = () => {
    // Create the payload with the data to be submitted
    const payload = {
      participantCode: selectedRow.participantCode,
      evaluatorId: selectedRow.evaluatorId,
      mark: parseFloat(mark),
      comment: comment,
    };

    // Make the POST request to the API
    fetch('http://localhost:8082/written_mark', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.statusCode === "BAD_REQUEST") {
            // Show error message using react-toastify
            toast.error(data.body || 'Failed to submit mark');
          } else {
            // Show success message using react-toastify
            toast.success('Mark submitted successfully');
          }
      })
      .catch((error) => {
        // Handle error in POST request
        console.error('Failed to submit data:', error);
      });
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'evaluatorId', headerName: 'Evaluator ID', width: 150 },
    { field: 'participantCode', headerName: 'Participant Code', width: 200 },
    {
      field: 'action',
      headerName: 'Action',
      width: 120,
      renderCell: (params) => (
        <Button variant="contained" color="primary" size="small" onClick={() => handleUpdate(params.row)}>
          Update
        </Button>
      ),
    },
  ];

  return (
    <div style={{ width: '100%', maxWidth: '1000px', margin: '0 auto', marginTop: '10px' }}>
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
      {selectedRow && (
        <Modal open={open} onClose={handleClose}>
          <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
            <h2>Evaluator ID: {selectedRow.evaluatorId}</h2>
            <h3>Participant Code: {selectedRow.participantCode}</h3>
            <TextField label="Mark" variant="outlined" value={mark} onChange={(e) => setMark(e.target.value)} fullWidth sx={{ my: 2 }} />
            <TextField label="Comment" variant="outlined" value={comment} onChange={(e) => setComment(e.target.value)} fullWidth sx={{ mb: 2 }} />
            <Button variant="contained" color="primary" onClick={handleSubmit}>
              Submit
            </Button>
          </Box>
        </Modal>
      )}
      <ToastContainer />
    </div>
  );
};

export default Written;
