import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom'
import AuctionGrid from './AuctionGrid'
import Bidder from './Bidder'

const Main = () => (
    <Switch>
        <Route exact path='/' component={AuctionGrid} />
        <Route path='/login' component={Bidder} />
    </Switch>
)

export default Main;