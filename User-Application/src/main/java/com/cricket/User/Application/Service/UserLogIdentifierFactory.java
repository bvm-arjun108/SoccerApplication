package com.cricket.User.Application.Service;

import com.cricket.User.Application.Model.User;

class UserLogIdentifierFactory implements LogIdentifierFactory<User> {
    @Override
    public String createLogIdentifier(User request) {
        return String.format(
                "%03d%09d|%s|%s",
                request.getEmail(),
                "register-user"
        );
    }
}
