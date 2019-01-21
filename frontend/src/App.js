import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import NavBar from './components/NavBar';
import Auction from './components/Auction';
import Bidder from './components/Bidder';
import { Grid } from '@material-ui/core';
import AuctionGrid from './components/AuctionGrid'
import Main from './components/Main'
import { Snackbar, Button, IconButton, CloseButton } from '@material-ui/core'
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { CloseIcon } from '@material-ui/icons';
const styles = theme => ({
  close: {
    padding: theme.spacing.unit / 2,
  },
});

class App extends Component {
  constructor() {
    super();
    this.state = { open: true, }
  }

  componentDidMount() {
    this.setInterval(this.getMessages(), 200)
  }

  handleClose(event) {
    this.setState({ open: false });
    sessionStorage.removeItem('message');
  }

  getMessages() {
    if (sessionStorage.getItem('bidderName')) {
      axios.get('/messages/' + sessionStorage.getItem('bidderName'))
        .then(res => {
          const message = res.data;
          sessionStorage.setItem('message', message)
        });
      axios.delete('/messages/' + sessionStorage.getItem('bidderName'))
    }
  }

  render() {
    return (
      <div>
        <NavBar />
        <Main />
        {sessionStorage.getItem('message') ? (
          <div>
            <Snackbar
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              open={this.state.open}
              onClose={this.handleClose}
              ContentProps={{
                'aria-describedby': 'message-id',
              }}
              message={<span id="message-id">{(sessionStorage.getItem('message')).message}</span>}
              action={[
                <Button key="undo" color="secondary" size="small" onClick={this.handleClose}>
                  UNDO
            </Button>,
                <IconButton
                  key="close"
                  aria-label="Close"
                  color="inherit"
                  onClick={this.handleClose}
                >
                  <CloseIcon />
                </IconButton>,
              ]}
            />
          </div>
        ) : null}
      </div >

    );
  }
}

export default App;
