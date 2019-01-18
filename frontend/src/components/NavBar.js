import React from 'react';
import { AppBar } from '@material-ui/core';
import { Toolbar } from '@material-ui/core';
import { Typography } from '@material-ui/core';

const NavBar = () => {
    return (
        <div>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="title" color="inherit">
                        SuperAuctions Auction Application
                    </Typography>
                </Toolbar>
            </AppBar>
        </div>
    );
}

export default NavBar;