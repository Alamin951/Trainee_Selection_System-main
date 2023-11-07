import React, { useState, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

const Evaluator = () => {
  const [evaluators, setEvaluators] = useState([]);
  const [selectedEvaluator, setSelectedEvaluator] = useState(null);
const [detailModalOpen, setDetailModalOpen] = useState(false);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  useEffect(() => {
    fetchEvaluators();
  }, []);

  const fetchEvaluators = () => {
    fetch('http://localhost:8082/evaluator')
      .then(response => response.json())
      .then(jsonData => {
        setEvaluators(jsonData);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  };

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform the POST request using formData
    fetch('http://localhost:8082/evaluator', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
      .then(response => response.json())
      .then(jsonData => {
        console.log('POST request successful:', jsonData);
        // Update the list of evaluators by fetching them again
        fetchEvaluators();
      })
      .catch(error => {
        console.error('Error performing POST request:', error);
      });

    // Close the modal
    handleClose();
  };
  const handleDetailModalOpen = (evaluator) => {
    setSelectedEvaluator(evaluator);
    setDetailModalOpen(true);
  };

  const handleDetailModalClose = () => {
    setDetailModalOpen(false);
  };
  

  return (
    <div>
      <Button variant="contained" onClick={handleOpen} style={{ marginBottom: '20px', marginTop: '10px' }}>
        Add Evaluator
      </Button>

      <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
        {evaluators.map(evaluator => (
          <Card key={evaluator.id} sx={{ width: 300, marginBottom: '20px', marginRight: '20px' }}>
            <CardContent>
              <Typography variant="h6" component="div">
                ID: {evaluator.id}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Email: {evaluator.email}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                Role: {evaluator.role}
              </Typography>
            </CardContent>
            <CardActions>
            <Button size="small" onClick={() => handleDetailModalOpen(evaluator)}>View Details</Button>
            </CardActions>
          </Card>
        ))}
      </Box>

      <Modal open={open} onClose={handleClose}>
        <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
          <Typography variant="h6" component="div" sx={{ marginBottom: '20px' }}>
            Add Evaluator
          </Typography>
          <form onSubmit={handleSubmit}>
            <TextField name="email" label="Email" fullWidth sx={{ marginBottom: '10px' }} onChange={handleChange} />
            <TextField name="password" type="password" label="Password" fullWidth sx={{ marginBottom: '20px' }} onChange={handleChange} />
            <Button type="submit" variant="contained">
              Submit
            </Button>
          </form>
        </Box>
      </Modal>
      <Modal open={detailModalOpen} onClose={handleDetailModalClose}>
  <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 600, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
    {selectedEvaluator && (
      <>
        <Typography variant="h6" component="div" sx={{ marginBottom: '20px' }}>
          Evaluator Details
        </Typography>
        <Typography variant="body1" sx={{ marginBottom: '10px' }}>
          ID: {selectedEvaluator.id}
        </Typography>
        <Typography variant="body1" sx={{ marginBottom: '10px' }}>
          Email: {selectedEvaluator.email}
        </Typography>
        <Typography variant="body1" sx={{ marginBottom: '20px' }}>
          Role: {selectedEvaluator.role}
        </Typography>
        {/* Add more details if needed */}
        <Button onClick={handleDetailModalClose}>Close</Button>
      </>
    )}
  </Box>
</Modal>

    </div>
  );
};

export default Evaluator;
