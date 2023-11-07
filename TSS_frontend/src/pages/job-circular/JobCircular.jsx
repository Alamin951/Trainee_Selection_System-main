import React, { useState, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import AdapterDateFns from '@mui/lab/AdapterDateFns';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import DatePicker from 'react-datepicker'; // Import react-datepicker
import 'react-datepicker/dist/react-datepicker.css'; // Import the date picker styles

const JobCircular = () => {
  const [data, setData] = useState([]);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState({
    circularName: '',
    description: '',
    openDate: null,
    applicationDeadline: null
  });

  useEffect(() => {
    fetchJobCirculars();
  }, []);

  const fetchJobCirculars = () => {
    fetch('http://localhost:8082/job_circular')
      .then(response => response.json())
      .then(jsonData => {
        setData(jsonData);
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

  const handleDateChange = (name, date) => {
    setFormData({ ...formData, [name]: date });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform the POST request using formData
    fetch('http://localhost:8082/job_circular', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
      .then(response => response.json())
      .then(jsonData => {
        console.log('POST request successful:', jsonData);
        // Update the list of job circulars by fetching them again
        fetchJobCirculars();
      })
      .catch(error => {
        console.error('Error performing POST request:', error);
      });

    // Close the modal
    handleClose();
  };

  return (
    <div>
      <Button variant="contained" onClick={handleOpen} style={{ marginBottom: '20px', marginTop:'10px' }}>
        Add Job Circular
      </Button>

      <Box sx={{ display: 'flex', flexWrap: 'wrap' }}>
        {data.map(circular => (
          <Card key={circular.circularId} sx={{ width: 300, marginBottom: '20px', marginRight: '20px' }}>
            <CardContent>
              <Typography variant="h6" component="div">
                {circular.circularName}
              </Typography>
              <Typography variant="body2" color="text.secondary">
                {circular.description}
              </Typography>
              <Typography variant="body2" color="text.secondary" sx={{ marginTop: '10px' }}>
                Application Deadline: {circular.applicationDeadline}
              </Typography>
            </CardContent>
            <CardActions>
              <Button size="small">View Details</Button>
            </CardActions>
          </Card>
        ))}
      </Box>

      <Modal open={open} onClose={handleClose}>
        <Box sx={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)', width: 400, bgcolor: 'background.paper', boxShadow: 24, p: 4 }}>
          <Typography variant="h6" component="div" sx={{ marginBottom: '20px' }}>
            Add Job Circular
          </Typography>
          <form onSubmit={handleSubmit}>
            <TextField name="circularName" label="Circular Name" fullWidth sx={{ marginBottom: '10px' }} onChange={handleChange} />
            <TextField name="description" label="Description" multiline fullWidth sx={{ marginBottom: '10px' }} onChange={handleChange} />

            {/* Use Bootstrap date picker for "Open Date" */}
            <div style={{ marginBottom: '10px' }}>
              <label style={{ marginRight: '10px' }}>Open Date</label>
              <DatePicker
                selected={formData.openDate}
                onChange={(date) => handleDateChange('openDate', date)}
                dateFormat="yyyy-MM-dd"
                className="form-control"
              />
            </div>

            {/* Use Bootstrap date picker for "Application Deadline" */}
            <div style={{ marginBottom: '20px' }}>
              <label style={{ marginRight: '10px' }}>Application Deadline</label>
              <DatePicker
                selected={formData.applicationDeadline}
                onChange={(date) => handleDateChange('applicationDeadline', date)}
                dateFormat="yyyy-MM-dd"
                className="form-control"
              />
            </div>

            <Button type="submit" variant="contained">
              Submit
            </Button>
          </form>
        </Box>
      </Modal>
    </div>
  );
};

export default JobCircular;
