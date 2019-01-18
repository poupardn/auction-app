import React, { Component } from 'react';
import { Typography } from '@material-ui/core';
import { TextField } from '@material-ui/core';
import { Button } from '@material-ui/core';

export default class Bidder extends React.Component {

    constructor() {
        super();
        this.state = { bidderName: null };
    }

    handleChange = (event) => {
        this.setState({
            bidderName: event.target.value
        })
    }

    handleSubmit = (event) => {
        event.preventDefault();
        sessionStorage.setItem('bidderName', this.state.bidderName);
        this.forceUpdate();
    }

    render() {
        return (
            <div>
                {sessionStorage.getItem('bidderName') === null ? (
                    <form onSubmit={this.handleSubmit}>
                        <TextField style={{ padding: 24 }}
                            id="bidderNameInput"
                            placeholder="Enter Bidder Name to Bid..."
                            margin="normal"
                            onChange={this.handleChange} />

                        <Button type="submit" variant="flat" size="small" color="primary">Register Bidder</Button>
                    </form>
                ) : this.state.bidderName}

            </div>
        )
    }
}