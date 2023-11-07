import React, { useEffect, useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios from 'axios';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import TextField from '@mui/material/TextField';

const AssignEvaluator = () => {
  const [data, setData] = useState([]);
  const [evaluators, setEvaluators] = useState([]);
  const [selectedEvaluator, setSelectedEvaluator] = useState('');
  const [openModal, setOpenModal] = useState(false);
  const [selectedApplicationId, setSelectedApplicationId] = useState('');
  const [selectedParticipantCode, setSelectedParticipantCode] = useState('');

  const userId = localStorage.getItem('userId');

  useEffect(() => {
    fetchWrittenExamCandidates();
    fetchEvaluators();
  }, []);

  const fetchWrittenExamCandidates = () => {
    axios
      .get('http://localhost:8082/answerSheet')
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  };

  const fetchEvaluators = () => {
    axios
      .get('http://localhost:8082/evaluator')
      .then((response) => {
        setEvaluators(response.data);
      })
      .catch((error) => {
        console.error('Error fetching evaluators:', error);
      });
  };

  const handleUpdateEvaluator = (applicationId, participantCode) => {
    setOpenModal(true);
    setSelectedApplicationId(applicationId);
    setSelectedParticipantCode(participantCode);
  };

  const handleCloseModal = () => {
    setOpenModal(false);
    setSelectedEvaluator('');
    setSelectedApplicationId('');
    setSelectedParticipantCode('');
  };

  const handleAssignEvaluator = () => {
    // Perform the PATCH request to update the evaluator
    const payload = {
      applicationId: selectedApplicationId,
      evaluatorId: selectedEvaluator,
    };

    axios
      .patch(`http://localhost:8082/answerSheet`, payload)
      .then((response) => {
        if (response.status === 200) {
          // Show success toast
          toast.success('Evaluator assigned successfully.', {
            position: 'top-right',
            autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
          });
          fetchWrittenExamCandidates(); // Refresh the data after the update
          handleCloseModal(); // Close the modal after successful assignment
        } else {
          // Show error toast
          toast.error('Failed to assign evaluator. Please try again.', {
            position: 'top-right',
            autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
          });
        }
      })
      .catch((error) => {
        // Show error toast
        toast.error('Failed to assign evaluator. Please try again.', {
          position: 'top-right',
          autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
        });
      });
  };

  const handleEvaluatorChange = (event) => {
    setSelectedEvaluator(event.target.value);
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 100 },
    { field: 'applicationId', headerName: 'Application ID', width: 150 },
    { field: 'evaluatorId', headerName: 'Evaluator ID', width: 150 },
    { field: 'participantCode', headerName: 'Participant Code', width: 200 },
    {
      field: 'action',
      headerName: 'Action',
      width: 150,
      renderCell: (params) => (
        <Button
          variant="contained"
          color="primary"
          size="small"
          onClick={() => handleUpdateEvaluator(params.row.applicationId, params.row.participantCode)}
        >
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
      <ToastContainer />

      {/* Evaluator Assignment Modal */}
      <Dialog open={openModal} onClose={handleCloseModal}>
        <DialogTitle>Assign Evaluator</DialogTitle>
        <DialogContent>
          <TextField
            label="Application ID"
            value={selectedApplicationId}
            variant="outlined"
            fullWidth
            disabled
            sx={{ marginBottom: '10px' }}
          />
          <TextField
            label="Participant Code"
            value={selectedParticipantCode}
            variant="outlined"
            fullWidth
            disabled
            sx={{ marginBottom: '10px' }}
          />
          <FormControl fullWidth variant="outlined">
            <InputLabel>Evaluator</InputLabel>
            <Select value={selectedEvaluator} onChange={handleEvaluatorChange} label="Evaluator">
              {evaluators.map((evaluator) => (
                <MenuItem key={evaluator.id} value={evaluator.id}>
                  {evaluator.email}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseModal} color="primary">
            Cancel
          </Button>
          <Button onClick={handleAssignEvaluator} color="primary">
            Assign
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
};

export default AssignEvaluator;
