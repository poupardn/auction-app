import React, { Component } from 'react';
import { Typography } from '@material-ui/core';
import { TextField } from '@material-ui/core';
import { Button } from '@material-ui/core';

export default class Bidder extends React.Component {
    state = {
        bidderName: '',
    }

    handleChange = event => {
        this.setState({ bidderName: event.target.value });
    }

    render() {
        return (
            <div>
                {this.state.bidderName === '' ? (
                    <form onSubmit={this.handleSubmit}>
                        <TextField style={{ padding: 24 }}
                            id="bidderNameInput"
                            placeholder="Enter Bidder Name to Bid..."
                            margin="normal"
                            onChange={this.handleChange} />
                    </form>
                ) : null}
            </div>
        )
    }
}