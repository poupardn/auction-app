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

const theme = createMuiTheme({
  palette: {
    secondary: {
      main: blue[900]
    },
    primary: {
      main: indigo[700]
    }
  },
  typography: {
    // Use the system font instead of the default Roboto font.
    fontFamily: [
      '"Lato"',
      'sans-serif'
    ].join(',')
  }
});


class App extends Component {
  render() {
    return (
      <div>
        <MuiThemeProvider theme={theme}>
          <Routes />
        </MuiThemeProvider>
      </div >
      /* <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
          <br />
          <ul>
            {this.state.auctionitems.map(item => <li>{item.reservePrice}</li>)}
          </ul>
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div> */
    );
  }
}

export default App;
