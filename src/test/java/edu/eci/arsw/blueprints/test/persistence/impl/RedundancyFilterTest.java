package edu.eci.arsw.blueprints.test.persistence.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.filters.RedundancyFilter;

public class RedundancyFilterTest {

    @Test
    public void removesConsecutiveDuplicates() {
        RedundancyFilter f = new RedundancyFilter();
        Blueprint in = new Blueprint("a","r",
                new Point[]{ new Point(1,1), new Point(1,1), new Point(2,2), new Point(2,2), new Point(3,3) });

        Blueprint out = f.apply(in);

        assertEquals(3, out.getPoints().size());
        assertEquals(1, out.getPoints().get(0).getX());
        assertEquals(2, out.getPoints().get(1).getX());
        assertEquals(3, out.getPoints().get(2).getX());
    }
}
