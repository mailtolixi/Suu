package com.nnsuu.suu.actor;

public interface ActorTouch {
    public abstract void touchDown();
    public abstract void touchMove(int x, int y);
    public abstract void touchUp(int x, int y);
}
