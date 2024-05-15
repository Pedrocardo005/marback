package com.mar.back.modules.usuario.exceptions;

public class UserAlreadyCreated extends Exception {
    public UserAlreadyCreated() {
        super();
    }

    public UserAlreadyCreated(String text) {
        super(text);
    }
}
