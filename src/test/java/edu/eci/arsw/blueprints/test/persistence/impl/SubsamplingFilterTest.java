package edu.eci.arsw.blueprints.test.persistence.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.filters.SubsamplingFilter;

public class SubsamplingFilterTest {

    @Test
    public void keepsEveryOtherPoint() {
        SubsamplingFilter f = new SubsamplingFilter();
        Blueprint in = new Blueprint("a","s",
                new Point[]{ new Point(0,0), new Point(1,1), new Point(2,2), new Point(3,3), new Point(4,4) });

        Blueprint out = f.apply(in);

        assertEquals(3, out.getPoints().size()); // quedan 0,2,4
        assertEquals(0, out.getPoints().get(0).getX());
        assertEquals(2, out.getPoints().get(1).getX());
        assertEquals(4, out.getPoints().get(2).getX());
    }
}
