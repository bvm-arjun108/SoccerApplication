package com.cricket.User.Application.Service;

interface LogIdentifierFactory<R> {
    String createLogIdentifier(R request);
}
