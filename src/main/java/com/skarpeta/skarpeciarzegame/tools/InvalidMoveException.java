package com.skarpeta.skarpeciarzegame.tools;

/** Exception walidacji ruchu gracza na heksagonalnej mapie */
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String s) {
        super(s);
    }
}
