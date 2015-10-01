package edu.nps.moves.deadreckoning.utils;

/**
 * The Exception class that is thrown by the Matrix.java class
 * Very basic Exception class, only passes a description of the error
 * that will hopefully lead to simple troubleshooting resolution...:)
 * 
 * @author Sheldon L. Snyder
 */
public class MatrixException extends Exception
{   
    public MatrixException(String s)
    {
        super(s);
    }
}
