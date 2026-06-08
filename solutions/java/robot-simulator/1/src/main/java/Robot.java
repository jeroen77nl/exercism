import javax.swing.text.Position;

class Robot {

    private GridPosition position;
    private Orientation orientation;

    Robot(GridPosition initialPosition, Orientation initialOrientation) {
        position = initialPosition;
        orientation = initialOrientation;
    }

    GridPosition getGridPosition() {
        return new GridPosition(position.x, position.y);
    }

    Orientation getOrientation() {
        return orientation;
    }

    void advance() {
        position = switch (orientation) {
            case NORTH -> new GridPosition(position.x, position.y + 1);
            case EAST -> new GridPosition(position.x + 1, position.y);
            case SOUTH -> new GridPosition(position.x, position.y - 1);
            case WEST -> new GridPosition(position.x - 1, position.y);
        };
    }

    void turnLeft() {
        orientation = switch(orientation) {
            case NORTH -> Orientation.WEST;
            case WEST -> Orientation.SOUTH;
            case SOUTH -> Orientation.EAST;
            case EAST -> Orientation.NORTH;
        };
    }

    void turnRight() {
        orientation = switch(orientation) {
            case NORTH -> Orientation.EAST;
            case WEST -> Orientation.NORTH;
            case SOUTH -> Orientation.WEST;
            case EAST -> Orientation.SOUTH;
        };
    }

    void simulate(String instructions) {
        for (int i = 0; i < instructions.length(); i++) {
            switch (instructions.charAt(i)) {
                case 'A':
                    advance();
                    break;
                case 'L':
                    turnLeft();
                    break;
                case 'R':
                    turnRight();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown instruction: " + instructions.charAt(i));
            }
        }
    }

}