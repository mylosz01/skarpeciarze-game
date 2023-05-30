package com.skarpeta.skarpeciarzegame;

import java.io.Serializable;

public enum PlayerMove implements Serializable {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_TOP_LEFT,
    MOVE_TOP_RIGHT,
    MOVE_BOTTOM_LEFT,
    MOVE_BOTTOM_RIGHT,
    BUILD_SAWMIL,
    BUILD_MINESHAFT,
    BUILD_QUARRY
}
