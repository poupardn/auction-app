import React, { Component } from 'react';
import axios from 'axios';
import Auction from './Auction';
import { Grid } from '@material-ui/core';

class AuctionGrid extends Component {
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
                {this.state.auctionitems ? (
                    <div>
                        <Grid container spacing={40} alignItems="flex-end">
                            {this.state.auctionitems.map(currentItem => (
                                <Grid item xs={12} sm={6} lg={4} xl={3}>
                                    <Auction auctionitem={currentItem} />
                                </Grid>
                            ))}
                        </Grid>
                    </div>
                ) : null
                }
            </div>);
    }
}

export default AuctionGrid;