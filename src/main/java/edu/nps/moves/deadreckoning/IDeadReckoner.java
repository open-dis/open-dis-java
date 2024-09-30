package edu.nps.moves.deadreckoning;

public interface IDeadReckoner<T> {

    void performDeadReckoning(T entityStatePdu, double deltaTime);

}
