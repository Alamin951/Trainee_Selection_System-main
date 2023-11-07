import React from 'react';
import { ListItem, ListItemAvatar, Avatar, ListItemText, Typography, Divider } from '@mui/material';

const NotificationItem = ({ notification }) => {
  const { message, date } = notification;

  return (
    <>
      <ListItem alignItems="flex-start">
        <ListItemAvatar>
          <Avatar>
            {/* Add an icon or letter to represent the notification */}
            N
          </Avatar>
        </ListItemAvatar>
        <ListItemText
          primary={
            <Typography variant="subtitle1" color="primary">
              {message}
            </Typography>
          }
          secondary={
            <Typography variant="caption" color="textSecondary">
              {new Date(date).toLocaleString()}
            </Typography>
          }
        />
      </ListItem>
      <Divider variant="inset" component="li" />
    </>
  );
};

export default NotificationItem;
