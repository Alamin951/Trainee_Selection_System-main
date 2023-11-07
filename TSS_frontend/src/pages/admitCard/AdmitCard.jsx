import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Button, Card, CardContent, Typography, Grid } from '@mui/material';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';

// Import the default user image
import defaultUserImage from '../../images/users/user.jpg';

const AdmitCard = () => {
  const [admitCards, setAdmitCards] = useState([]);
  const userId = localStorage.getItem('userId');

  useEffect(() => {
    axios
      .get(`http://localhost:8082/admit-card/${userId}`)
      .then((response) => {
        setAdmitCards(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [userId]);

  const handleDownloadPDF = (admitCard) => {
    const cardElement = document.getElementById(`card-${admitCard.admitCardId}`);
    const cardContentElement = cardElement.querySelector('.card-content');

    html2canvas(cardContentElement).then((canvas) => {
      const imgData = canvas.toDataURL('image/png');

      const pdf = new jsPDF('p', 'mm', 'a4');
      const pdfWidth = 210;
      const pdfHeight = (canvas.height * pdfWidth) / canvas.width;

      pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight);
      pdf.save(`AdmitCard_${admitCard.applications.applicantId}.pdf`);
    });
  };

  return (
    <Grid container spacing={2} style={{ marginTop: '10px' }}>
      {admitCards.map((admitCard) => (
        <Grid item xs={12} sm={6} md={4} key={admitCard.admitCardId}>
          <Card id={`card-${admitCard.admitCardId}`} style={{ height: '100%' }}>
            <CardContent className="card-content" style={{ paddingTop: '15px' }}>
              {/* Admit Card Title */}
              <Typography variant="h5" align="center" style={{ marginBottom: '10px' }}>
                ADMIT CARD
              </Typography>

              {/* User Image */}
              <div style={{ display: 'flex', justifyContent: 'center' }}>
                <img
                  src={admitCard.applicants.photoUrl || defaultUserImage}
                  alt="User"
                  style={{ width: '100px', height: '100px', borderRadius: '50%' }}
                  onError={(e) => {
                    e.target.src = defaultUserImage;
                  }}
                />
              </div>

              {/* User Information */}
              <Typography variant="body1" style={{ marginTop: '10px' }}>
                Name: {`${admitCard.applicants.firstName} ${admitCard.applicants.lastName}`}
              </Typography>
              <Typography variant="body1">Circular Name: {admitCard.applications.jobCircular.circularName}</Typography>
              <Typography variant="body1">Gender: {admitCard.applicants.gender}</Typography>

              {/* QR Code */}
              <div style={{ display: 'flex', justifyContent: 'center', marginTop: '10px' }}>
                <img src={`data:image/png;base64,${admitCard.qrCode}`} alt="QR Code" style={{ width: '100px' }} />
              </div>

              {/* Expiring Date */}
              <Typography variant="body1" style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
  Expiring Date: {new Date(admitCard.expiringDate).toLocaleDateString('en', {
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  })}
</Typography>

              {/* Download Button */}
              <Button variant="contained" color="primary" onClick={() => handleDownloadPDF(admitCard)}>
                Download as PDF
              </Button>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default AdmitCard;
 