package engine.entity;

import com.badlogic.gdx.math.MathUtils;

public class NonPlayableEntity extends BaseEntity{
    private int currentDirection;
    private float directionChangeTimer;
    public NonPlayableEntity(String texturePath, float x, float y, String id, float speed,float width, float height) {
        super(texturePath, x, y, id, speed, width, height);
        this.currentDirection = MathUtils.random(0,3);
        this.directionChangeTimer = 0;
    }

    public int getCurrentDirection() { return currentDirection; }
    public void setCurrentDirection(int currentDirection) { this.currentDirection = currentDirection; }
    public float getDirectionChangeTimer() { return directionChangeTimer; }
    public void setDirectionChangeTimer(float directionChangeTimer) { this.directionChangeTimer = directionChangeTimer; }
}
