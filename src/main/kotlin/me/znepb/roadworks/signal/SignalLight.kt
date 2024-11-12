package me.znepb.roadworks.signal

enum class SignalLight(val light: String, val genericType: SignalLight, val isGeneric: Boolean, val signalDirection: SignalDirection) {
    GREEN("green", GREEN, true, SignalDirection.NONE),
    YELLOW("yellow", YELLOW, true, SignalDirection.NONE),
    RED("red", RED, true, SignalDirection.NONE),
    GREEN_LEFT("green_left", GREEN, false, SignalDirection.LEFT),
    YELLOW_LEFT("yellow_left", YELLOW, false, SignalDirection.LEFT),
    RED_LEFT("red_left", RED, false, SignalDirection.LEFT),
    GREEN_STRAIGHT("green_straight", GREEN, false, SignalDirection.STRAIGHT),
    YELLOW_STRAIGHT("yellow_straight", YELLOW, false, SignalDirection.STRAIGHT),
    RED_STRAIGHT("red_straight", RED, false, SignalDirection.STRAIGHT),
    GREEN_RIGHT("green_right", GREEN, false, SignalDirection.RIGHT),
    YELLOW_RIGHT("yellow_right", YELLOW, false, SignalDirection.RIGHT),
    RED_RIGHT("red_right", RED, false, SignalDirection.RIGHT),
    WALK("walk", GREEN, false, SignalDirection.NONE),
    DONT_WALK("dont_walk", RED, false, SignalDirection.NONE);

    enum class SignalDirection {
        STRAIGHT,
        LEFT,
        RIGHT,
        NONE;
    }

    companion object {
        fun fromName(name: String): SignalLight? {
            entries.forEach {
                if(it.name == name) return it
            }

            return null
        }
    }
}