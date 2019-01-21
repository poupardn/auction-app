import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';

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

const NavBar = (props) => {
    const { classes } = props;
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
                            <Link to='/addauction'>
                                <Button color="primary" variant="outlined">
                                    Add Auction
                                </Button>
                            </Link>
                        )}

                </Toolbar>
            </AppBar>
        </div>
    );
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar);