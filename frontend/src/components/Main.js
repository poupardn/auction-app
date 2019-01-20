import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom'

const Main = () => (
    <main>
        <Switch>
            <Route exact path='/' component={AuctionGrid} />
            <Route path='/login' component={Bidder} />
        </Switch>
    </main>
)