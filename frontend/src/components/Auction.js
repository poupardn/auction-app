import React, { Component } from 'react';
import { Card } from '@material-ui/core';
import { CardContent } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import { Grid } from '@material-ui/core';
import { Button } from '@material-ui/core';
import { CardActions } from '@material-ui/core';
import { TextField } from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import axios from 'axios';

const styles = theme => ({
    button: {
        display: 'flex',
        justifyContent: 'flex-end',
        marginTop: theme.spacing.unit * 3,
        marginLeft: theme.spacing.unit,
    },
});

class Auction extends React.Component {

    constructor() {
        super();
        this.state = {
            bidAmount: null,
            error: false
        };
    }

    handleChange = (event) => {
        this.setState({
            bidAmount: event.target.value
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        var bidAmount = parseFloat(this.state.bidAmount);
        var reservePrice = parseFloat(this.props.auctionitem.reservePrice);
        var currentBid = parseFloat(this.props.auctionitem.currentBid);

        if (bidAmount > currentBid) {
            axios.post('bids', {
                auctionItemId: this.props.auctionitem.auctionItemId,
                maxAutoBidAmount: bidAmount,
                bidderName: sessionStorage.getItem('bidderName')
            }).catch((error) => {
                var errorText = '';
                if (error.response) {
                    errorText = error.response.data.message;
                } else if (error.request) {
                    errorText = 'No response from Bid server. Contact support.';
                }
                this.setState({
                    error: true,
                    errorText: errorText
                });
                this.forceUpdate();
            });
        }
        else {
            this.setState({ errorText: 'Bid does not exceed current bid.' });
        }

    };

    render() {
        return (
            <div>
                {this.props.auctionitem ? (
                    <Card>
                        <CardContent>
                            <Typography gutterBottom variant="headline" component="h2">
                                Auction item: {this.props.auctionitem.item.itemId}
                            </Typography>
                            <Typography component="p">
                                {this.props.auctionitem.item.description}
                            </Typography>
                            <Typography component="p">
                                Current Bid: ${this.props.auctionitem.currentBid}
                            </Typography>
                        </CardContent>
                        {sessionStorage.getItem('bidderName') !== null ? (
                            <CardActions>
                                <form onSubmit={this.handleSubmit}>
                                    <Grid container spacing={24}>
                                        <Grid item xs={12} sm={6}>
                                            <TextField
                                                required
                                                id="postBid"
                                                name="postBid"
                                                label="Post a bid"
                                                fullWidth
                                                autoComplete="postBid"
                                                onChange={this.handleChange}
                                                error={this.error}
                                                helperText={this.state.errorText}
                                            />
                                        </Grid>
                                        <Grid item xs={12} sm={6}>
                                            <Button type="submit" color="primary" variant="outlined" className={this.props.classes.button}>
                                                Bid
                                    </Button>
                                        </Grid>
                                    </Grid>
                                </form>
                            </CardActions>) : null}
                    </Card>
                ) : null
                }
            </div>
        );
    }
}

export default withStyles(styles)(Auction);