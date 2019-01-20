import React, { Component } from 'react';
import Paper from '@material-ui/core'
import Typography from '@material-ui/core'

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
})

class AddAuction extends React.Component {
    render() {
        const { classes } = this.props;
        return (
            <main className={classes.layout}>
                <Paper className={classes.paper}>
                    <Typography component="h1" variant="h4" align="center">
                        Add an Auction
                    </Typography>
                    <Grid container spacing={24}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                id="itemId"
                                name="itemId"
                                label="Item Name/ID"
                                fullWidth
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                id="reservePrice"
                                name="reservePrice"
                                label="Item Reserve Price"
                                fullWidth
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
                            />
                        </Grid>
                    </Grid>

                </Paper>
            </main>
        )
    }

}