package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;


public class CollisionController{
    private CollisionHandler collisionHandler;
    private boolean isEnabled;
        private ArrayList<Pair<BaseEntity, BaseEntity>> collisionPairs;

        public CollisionController() {
            this.collisionPairs = new ArrayList<>();
            this.collisionHandler = new CollisionHandler();

        }

        public boolean checkCollision(BaseEntity entityA, BaseEntity entityB) {
            if (entityA.getboundary().overlaps(entityB.getboundary())) {
                 collisionHandler.handleCollision(entityA, entityB);
            }
            else{
                System.out.println(("not collided"));

            }
            return false;
        }

    public void detectAllCollisions() {

    }

// is handling collisions and resolving collisions the same



    public void addCollisionPair(BaseEntity entityA, BaseEntity entityB) {
            collisionPairs.add(new Pair<>(entityA, entityB));
            System.out.println(("added to collisionpairs"));
        }



        public void clearCollisionPairs() {
            collisionPairs.clear();
        }

        public void setEnabled(boolean enabled) {
            this.isEnabled = enabled;
        }
    }

