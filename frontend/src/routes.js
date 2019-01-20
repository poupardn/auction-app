import React from 'react'
import { Route, HashRouter, Switch } from 'react-router-dom'
import Bidder from './components/Bidder'
import Auction from './components/AuctionGrid'

export default props => (
    <HashRouter>
        <Switch>
            <Route exact path='/' component={AuctionGrid} />
            <Route exact path='/login' component={Bidder} />
        </Switch>
    </HashRouter>
)