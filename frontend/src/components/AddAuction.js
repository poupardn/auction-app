import React, { Component } from 'react';
import { Paper, Typography, Grid, TextField, Button } from '@material-ui/core';
import { withStyles } from '@material-ui/core/styles';
import { Redirect } from 'react-router-dom';


import axios from 'axios';

const styles = theme => ({
    layout: {
        width: 'auto',
        marginLeft: theme.spacing.unit * 2,
        marginRight: theme.spacing.unit * 2,
        [theme.breakpoints.up(600 + theme.spacing.unit * 2 * 2)]: {
            width: 600,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    paper: {
        marginTop: theme.spacing.unit * 3,
        marginBottom: theme.spacing.unit * 3,
        padding: theme.spacing.unit * 2,
        [theme.breakpoints.up(600 + theme.spacing.unit * 3 * 2)]: {
            marginTop: theme.spacing.unit * 6,
            marginBottom: theme.spacing.unit * 6,
            padding: theme.spacing.unit * 3,
        },
    },
    button: {
        display: 'flex',
        justifyContent: 'flex-end',
        marginTop: theme.spacing.unit * 3,
        marginLeft: theme.spacing.unit,
    },
})

class AddAuction extends Component {

    constructor() {
        super();
        this.state = {
            itemId: '',
            reservePrice: '',
            itemDescription: '',
            error: false,
        };
        this.handleChange = this.handleChange.bind(this);
        this.isEmpty = this.isEmpty.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange = (event) => {
        this.setState({ [event.target.name]: [event.target.value] });
    }

    isEmpty = (str) => {
        return (!str || 0 === str.length)
    }

    handleSubmit = (event) => {
        event.preventDefault();
        var itemId = this.state.itemId;
        var itemDescription = this.state.itemDescription;
        var inputReservePrice = this.state.reservePrice;
        if (this.isEmpty(itemId)) {
            this.setState({
                error: true,
                idErrorText: 'Please enter an item name/id.'
            });
            return;
        }
        if (this.isEmpty(inputReservePrice)) {
            this.setState({
                error: true,
                rpErrorText: 'Please enter a reserve price.'
            });
            return;
        }
        if (this.isEmpty(itemDescription)) {
            this.setState({
                error: true,
                discErrorText: 'Please enter an item description.'
            });
            return;
        }
        this.setState({ error: false });
        axios.post('auctionitems', {
            reservePrice: Number(inputReservePrice),
            item: {
                itemId: String(itemId),
                description: String(itemDescription)
            }
        });
        this.setState({ toHome: true });
        this.forceUpdate();
    }

    render() {
        const { classes } = this.props;
        if (this.state.toHome === true) {
            return (<Redirect push to='/' />);
        }
        return (
            <main className={classes.layout}>
                <Paper className={classes.paper}>
                    <Typography component="h1" variant="h4" align="center">
                        Add an Auction
                    </Typography>
                    <form onSubmit={this.handleSubmit}>
                        <Grid container spacing={24}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="itemId"
                                    name="itemId"
                                    label="Item Name/ID"
                                    fullWidth
                                    onChange={this.handleChange}
                                    error={this.error}
                                    errorText={this.state.idErrorText}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    id="reservePrice"
                                    name="reservePrice"
                                    label="Item Reserve Price"
                                    fullWidth
                                    onChange={this.handleChange}
                                    error={this.error}
                                    helperText={this.state.rpErrorText}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    id="itemDescription"
                                    name="itemDescription"
                                    label="Item Description"
                                    fullWidth
                                    multiline
                                    onChange={this.handleChange}
                                    error={this.error}
                                    errorText={this.state.descErrorText}
                                />
                            </Grid>
                        </Grid>
                        <div>
                            <Button type="submit" variant="contained" color="primary" className={classes.button}>
                                Add
                            </Button>
                        </div>
                    </form>
                </Paper>
            </main>
        );
    }

}
export default withStyles(styles)(AddAuction);