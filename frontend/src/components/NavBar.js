import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar, Typography, Button } from '@material-ui/core';
import { Link, Redirect } from 'react-router-dom';

const styles = theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    appBar: {
        position: 'relative',
    },
    toolbarTitle: {
        flex: 1,
    }
});

class NavBar extends Component {

    constructor() {
        super();
        this.state = { toHome: false };
    }

    onClick = (event) => {
        if (sessionStorage.getItem('bidderName')) {
            sessionStorage.removeItem('bidderName');
            this.setState({ toHome: true });
        }
        this.forceUpdate();
    }

    render() {
        const { classes } = this.props;
        if (this.state.toHome === true) {
            return (<Redirect push to='/' />);
        }
        return (
            <div className={classes.root}>
                <AppBar position="static" color="default" className={classes.appBar}>
                    <Toolbar>
                        <Typography variant="h6" color="inherit" noWrap className={classes.toolbarTitle}>
                            The Auction App
                    </Typography>

                        {sessionStorage.getItem('bidderName') === null ? (
                            <Link to='/login'>
                                <Button color="primary" variant="outlined">
                                    Login
                                </Button>
                            </Link>) : (
                                <div>
                                    <Link to='/addauction'>
                                        <Button color="primary" variant="outlined">
                                            Add Auction
                                        </Button>
                                    </Link>
                                    <Button color="primary" onClick={this.onClick} variant="outlined">
                                        Sign Out
                                        </Button>

                                </div>
                            )}

                    </Toolbar>
                </AppBar>
            </div >
        );
    }
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar);