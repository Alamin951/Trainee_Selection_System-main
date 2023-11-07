import React, { useEffect, useState } from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Grid from '@mui/material/Grid';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Approval = () => {
  const [pendingData, setPendingData] = useState([]);
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    fetch(`http://localhost:8082/applications/pending/${userId}`)
      .then((response) => response.json())
      .then((jsonData) => {
        setPendingData(jsonData);
      })
      .catch((error) => console.error('Error fetching data:', error));
  }, [userId]);

  const handleAccept = (approvalId) => {
    fetch(`http://localhost:8082/approval/pending/${approvalId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
    })
      .then((response) => {
        if (response.ok) {
          // Show success toast
          toast.success('Request accepted successfully.', {
            position: 'top-right',
            autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
          });
        } else {
          // Show error toast
          toast.error('Failed to accept the request. Please try again.', {
            position: 'top-right',
            autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
          });
          // Reload the page to refresh the data
          window.location.reload();
        }
      })
      .catch((error) => {
        console.error('Error accepting request:', error);
        // Show error toast
        toast.error('Failed to accept the request. Please try again.', {
          position: 'top-right',
          autoClose: 3000, // Auto close the toast after 3000 milliseconds (3 seconds)
        });
        // Reload the page to refresh the data
        window.location.reload();
      });
  };

  return (
    <>
      <Grid container spacing={2}>
        {pendingData.length === 0 ? (
          <Grid item xs={12}>
            <Typography variant="h5" component="div" gutterBottom>
              You have no pending requests.
            </Typography>
          </Grid>
        ) : (
          pendingData.map((data) => (
            <Grid item key={data.approvalId} xs={12} sm={6} md={4}>
              <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column', marginTop: '10px' }}>
                <CardContent sx={{ flexGrow: 1, display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                  <Typography variant="h6" component="div" sx={{ flexGrow: 1, alignSelf: 'center' }}>
                    {data.circularName}
                  </Typography>
                  <Button
                    variant="contained"
                    color="primary"
                    size="small"
                    onClick={() => handleAccept(data.approvalId)}
                    sx={{ marginTop: '10px', borderRadius: '20px', alignSelf: 'center' }}
                  >
                    Accept
                  </Button>
                </CardContent>
              </Card>
            </Grid>
          ))
        )}
      </Grid>
      <ToastContainer />
    </>
  );
};

export default Approval;
