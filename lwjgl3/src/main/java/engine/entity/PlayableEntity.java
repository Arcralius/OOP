package engine.entity;

import java.time.Instant;

public class PlayableEntity extends BaseEntity {
    private float hp;
    private Instant lastHitTime;
    private static final int INVINCIBILITY_DURATION_MS = 2000; // 2 seconds
    public PlayableEntity(String texturePath, float x, float y, String id, float speed, float width, float height, float hp) {
        super(texturePath, x, y, id, speed, width, height);
        this.hp = hp;
        this.lastHitTime = Instant.now().minusMillis(INVINCIBILITY_DURATION_MS);
    }

    public float getHP() {
        return hp;
    }

    public void setHP(float newHP) {
        hp = newHP;
    }

    public boolean canTakeDamage() {
        Instant now = Instant.now();
        return lastHitTime.plusMillis(INVINCIBILITY_DURATION_MS).isBefore(now);
    }

    public void registerHit() {
        lastHitTime = Instant.now(); // Start invincibility
    }

}
