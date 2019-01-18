import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import axios from 'axios';
import NavBar from './components/NavBar';
import Auction from './components/Auction';
import Bidder from './,components/Bidder';
import { Grid } from '@material-ui/core';

class App extends Component {
  state = { auctionitems: [] };

  componentDidMount() {
    axios.get('/auctionitems')
      .then(res => {
        const auctionitems = res.data;
        this.setState({ auctionitems });
      });
  }

  render() {
    return (
      <div>
        <NavBar />
        <Bidder />
        {this.state.auctionitems ? (
          <div>
            <Grid container spacing={24} style={{ padding: 24 }}>
              {this.state.auctionitems.map(currentItem => (
                <Grid item xs={12} sm={6} lg={4} xl={3}>
                  <Auction auctionitem={currentItem} />
                </Grid>
              ))}
            </Grid>
          </div>
        ) : null
        }
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
