import React, { Component } from 'react';
import { Button, FormControl, Input, InputLabel, Paper, Typography } from '@material-ui/core';
import CssBaseline from '@material-ui/core/CssBaseline';
import withStyles from '@material-ui/core/styles/withStyles';
import { Redirect } from 'react-router-dom';


const styles = theme => ({
    main: {
        width: 'auto',
        display: 'block', // Fix IE 11 issue.
        marginLeft: theme.spacing.unit * 3,
        marginRight: theme.spacing.unit * 3,
        [theme.breakpoints.up(400 + theme.spacing.unit * 3 * 2)]: {
            width: 400,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    paper: {
        marginTop: theme.spacing.unit * 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    },
    avatar: {
        margin: theme.spacing.unit,
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing.unit,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
    },
});

class Bidder extends React.Component {

    constructor() {
        super();
        this.state = { bidderName: null };
    }

    handleChange = (event) => {
        this.setState({
            bidderName: event.target.value
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        sessionStorage.setItem('bidderName', this.state.bidderName);
        this.setState({ toHome: true });
        this.forceUpdate();
    }

    render() {
        const { classes } = this.props;
        if (this.state.toHome === true) {
            return (<Redirect to='/' />);
        }
        return (

            <div>
                <main className={classes.main}>
                    <CssBaseline />
                    <Paper className={classes.paper}>
                        <Typography component="h1" variant="h5">
                            Enter a Bidder Name to sign in.
                            </Typography>
                        <form className={classes.form} onSubmit={this.handleSubmit}>
                            <FormControl margin="normal" required fullWidth>
                                <InputLabel htmlFor="bidderName">Bidder Name</InputLabel>
                                <Input id="bidderName" name="bidderName" autoComplete="bidderName" onChange={this.handleChange} autoFocus />
                            </FormControl>

                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className={classes.submit}
                            >
                                Sign in
                            </Button>
                        </form>
                    </Paper>
                </main>
            </div>)
    }
}

export default withStyles(styles)(Bidder)