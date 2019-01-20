import React, { Component } from 'react';
import { Card } from '@material-ui/core';
import { CardContent } from '@material-ui/core';
import { Typography } from '@material-ui/core';

const styles = theme => ({
    paper: {
        padding: theme.spacing.unit * 3,
        textAlign: 'left',
        color: theme.palette.text.secondary
    },
    itemContainer: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-start',
        [theme.breakpoints.down('sm')]: {
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center'
        }
    },
    baseline: {
        alignSelf: 'baseline',
        marginLeft: theme.spacing.unit * 4,
        [theme.breakpoints.down('sm')]: {
            display: 'flex',
            flexDirection: 'column',
            textAlign: 'center',
            alignItems: 'center',
            width: '100%',
            marginTop: theme.spacing.unit * 2,
            marginBottom: theme.spacing.unit * 2,
            marginLeft: 0
        }
    },
    inline: {
        display: 'inline-block',
        marginLeft: theme.spacing.unit * 4,
        [theme.breakpoints.down('sm')]: {
            marginLeft: 0
        }
    },
    inlineRight: {
        width: '30%',
        textAlign: 'right',
        marginLeft: 50,
        alignSelf: 'flex-end',
        [theme.breakpoints.down('sm')]: {
            width: '100%',
            margin: 0,
            textAlign: 'center'
        }
    },
    spaceTop: {
        marginTop: 20
    },
    secondary: {
        background: theme.palette.secondary['100'],
        color: 'white'
    },
});

const Auction = (props) => {
    console.log(props);
    return (
        <React.Fragment>
            <CssBaseline />
            <NavBar />
            {props.auctionitem ? (
                <div className={classes.root}>
                    <Paper className={classes.paper}>
                        <div className={classes.itemContainer}>
                            <div className={classes.baseline}>
                                <div className={classes.inline}>
                                    <Typography style={{ textTransform: 'uppercase' }} color='secondary' gutterBottom>
                                        Auction Item: {props.auctionitem.item.itemId}
                                    </Typography>
                                    <Typography variant="h6" gutterBottom>
                                        {props.auctionitem.item.description}
                                    </Typography>
                                </div>
                            </div>
                            <div className={classes.inlineRight}>
                                <Typography style={{ textTransform: 'uppercase' }} color='secondary' gutterBottom>
                                    Current Bid
                                </Typography>
                                <Typography variant="h5" gutterBottom>
                                    ${props.acitonitem.currentBid ? props.auctionitem.currentBid : 0}
                                </Typography>
                                <div className={classes.spaceTop}>
                                    <Button variant="contained" color="primary" className={classes.secondary}>
                                        Bid
                                    </Button>
                                </div>
                            </div>
                        </div>
                    </Paper>
                </div>
                /*
                <Card>
                    <CardContent>
                        <Typography gutterBottom variant="headline" component="h2">
                            Auction item: {props.auctionitem.item.itemId}
                        </Typography>
                        <Typography component="p">
                            {props.auctionitem.item.description}
                        </Typography>
                        <Typography component="p">
                            Reserve Price: ${props.auctionitem.reservePrice}
                        </Typography>
                    </CardContent>
                </Card> */
            ) : null
            }
        </React.Fragment>
    )
}

export default Auction;