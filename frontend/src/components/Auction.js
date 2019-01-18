import React, { Component } from 'react';
import { Card } from '@material-ui/core';
import { CardContent } from '@material-ui/core';
import { Typography } from '@material-ui/core';

const Auction = (props) => {
    console.log(props);
    return (
        <div>
            {props.auctionitem ? (
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
                </Card>
            ) : null
            }
        </div>
    )
}

export default Auction;