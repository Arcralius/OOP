package engine.collision;


import engine.entity.BaseEntity;

import java.util.List;

public interface CollisionInterface {

    void checkCollisions(BaseEntity player, List<BaseEntity> entityList);


}
