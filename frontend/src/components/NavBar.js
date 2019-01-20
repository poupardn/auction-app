import React from 'react';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import { AppBar } from '@material-ui/core';
import { Toolbar } from '@material-ui/core';
import { Typography } from '@material-ui/core';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import { Link } from 'react-router-dom'

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
        <React.Fragment>
            <CssBaseline />
            <AppBar position="static" color="default" className={classes.appBar}>
                <Toolbar>
                    <Typography variant="h6" color="inherit" noWrap className={classes.toolbarTitle}>
                        The Auction App
                    </Typography>
                    <Link to='/roster'>
                        <Button color="primary" variant="outlined">
                            Login
                        </Button>
                    </Link>
                </Toolbar>
            </AppBar>
        </React.Fragment>
    );
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar);