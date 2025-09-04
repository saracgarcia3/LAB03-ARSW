package edu.eci.arsw.blueprints.test.persistence.impl;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.eci.arsw.blueprints.config.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import edu.eci.arsw.blueprints.services.filters.BlueprintFilter;

public class FilterWiringWithSpringTest {

    @Test
    public void serviceAppliesPrimaryFilter() throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BlueprintsServices svc = ctx.getBean(BlueprintsServices.class);
        BlueprintFilter active = ctx.getBean(BlueprintFilter.class); // el bean @Primary actual

        Blueprint original = new Blueprint("w","x",
                new Point[]{ new Point(1,1), new Point(1,1), new Point(1,1), new Point(1,1) });
        svc.addNewBlueprint(original);

        Blueprint expected = active.apply(original);
        Blueprint got = svc.getBlueprint("w","x");

        assertEquals(expected.getPoints().size(), got.getPoints().size());
        for (int i = 0; i < expected.getPoints().size(); i++) {
            assertEquals(expected.getPoints().get(i), got.getPoints().get(i));
        }
    }
}
