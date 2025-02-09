package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;

public interface CollisionHanding {
    void handleCollision(BaseEntity entityA, BaseEntity entityB);

}
