package edu.nps.moves.dis7;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class EntityIDTest
{
    EntityID id1;
    EntityID id2;
    EntityID id3;
    EntityID id4;

    @Before
    public void setUp()
        throws Exception
    {
        id1 = new EntityID();
        id1.setEntityID(1);
        id1.setApplicationID(2);
        id1.setSiteID(3);
        // id1 and id2 have the same entityID, applicationID, siteID
        id2 = new EntityID();
        id2.setEntityID(1);
        id2.setApplicationID(2);
        id2.setSiteID(3);

        id3 = new EntityID();
        id3.setEntityID(1);
        id3.setApplicationID(2);
        id3.setSiteID(4);   // different site id

        id4 = new EntityID();
        id4.setEntityID(1);
        id4.setApplicationID(2);
        id4.setSiteID(5);   // different site id
    }

    @Test
    public void EntityIDEquals_test()
    {
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id3, id4);
    }

    @Test
    public void EntityID_Lists_tests()
    {
        List<EntityID> list = Arrays.asList(id1, id2, id3, id4);

        assertTrue(list.contains(id1));
        assertEquals(id2, list.get(list.indexOf(id2)));

        List<EntityID> collect = list.stream().filter(e -> e.getSiteID() == 3).collect(Collectors.toList());
        assertEquals(2, collect.size());
        assertTrue(collect.contains(id1));
    }

    @Test
    public void EntityID_Map_tests()
    {
        Map<EntityID, String> map = new HashMap<>();
        map.put(id1, "id1");
        map.put(id2, "id2");    // id1 and id2 have the same entity, application, and site id. Therefore "id2" replaces "id1"
        map.put(id3, "id3");
        map.put(id4, "id4");

        assertEquals(3, map.size());
        assertEquals("id3", map.get(id3));
        assertTrue(map.containsKey(id2));
        assertTrue(map.containsValue("id3"));

        map.forEach((entityID, s) -> {
            if (entityID.equals(id4)) {
                assertTrue(true);
            }
        });
    }
}