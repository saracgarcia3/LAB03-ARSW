package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.Config; 
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class BlueprintsServicesWiringTest {

    @Test
    public void testServiceInjectionAndSaveLoad() throws Exception {
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(Config.class)) { // ⚠️ cambia a AppConfig.class si ese es tu nombre

            BlueprintsServices svc = ctx.getBean(BlueprintsServices.class);
            assertNotNull("Spring no creó el servicio", svc);

            
            Blueprint bp = new Blueprint("ana", "casa",
                    new Point[]{new Point(0, 0), new Point(5, 5)});

            svc.addNewBlueprint(bp);

            Blueprint loaded = svc.getBlueprint("ana", "casa");

            assertNotNull("El blueprint no fue cargado", loaded);
            assertEquals("El blueprint cargado no coincide", "casa", loaded.getName());
            assertEquals("El autor no coincide", "ana", loaded.getAuthor());
        }
    }
}
