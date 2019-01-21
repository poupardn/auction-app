import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import AuctionGrid from './AuctionGrid';
import Bidder from './Bidder';
import AddAuction from './AddAuction';

const Main = () => (
    <Switch>
        <Route exact path='/' component={AuctionGrid} />
        <Route path='/login' component={Bidder} />
        <Route path='/addauction' component={AddAuction} />
    </Switch>
);

export default Main;