package edu.nps.moves.dis;

import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({PduTest.class, MarkingTest.class, IntercomSignalPduUnitTest.class, VariableDatumTest.class,
                     Vector3DoubleTest.class})

/**
* Runs all class unit tests. To add a new class's unit tests, add it to the
* list above. The body of this class may be empty; the only reason to add
* content is backward compatiblity with JUnit 3.x.
*
* @author DMcG 
*/
public class DisAllTests 
{

}
