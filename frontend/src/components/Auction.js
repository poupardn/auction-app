import React, { Component } from 'react';
import { Card, CardContent, Typography, Grid, Button, CardActions, TextField } from '@material-ui/core';
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

class Auction extends Component {

    constructor() {
        super();
        this.state = {
            bidAmount: null,
            error: false,
            open: true,
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
            }).then(res => {
                window.location.reload();
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
                return;
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
                            {sessionStorage.getItem('bidderName') === this.props.auctionitem.currentBidder ? (
                                <Typography component="p">
                                    You are the highest bidder!
                                </Typography>
                            ) : null}
                        </CardContent>
                        <CardActions>
                            <form onSubmit={this.handleSubmit}>
                                <Grid container spacing={24}>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            required
                                            id="postBid"
                                            name="postBid"
                                            label="Enter Max Bid"
                                            fullWidth
                                            autoComplete="postBid"
                                            onChange={this.handleChange}
                                            error={this.error}
                                            helperText={this.state.errorText}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <Button type="submit" color="primary" variant="outlined" className={this.props.classes.button}>
                                            Post Bid
                                    </Button>
                                    </Grid>
                                </Grid>
                            </form>
                        </CardActions>
                    </Card>
                ) : null
                }
            </div>
        );
    }
}

export default withStyles(styles)(Auction);