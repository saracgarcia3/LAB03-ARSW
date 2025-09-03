package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) throws Exception {
        // Levantar el contexto de Spring con tu clase de configuración
        try (AnnotationConfigApplicationContext ctx =
                     new AnnotationConfigApplicationContext(Config.class)) {

            // Obtener el servicio inyectado por Spring
            BlueprintsServices services = ctx.getBean(BlueprintsServices.class);

            // Probar que funciona
            Blueprint bp = new Blueprint("alice", "house",
                    new Point[]{ new Point(0, 0), new Point(10, 10) });
            services.addNewBlueprint(bp);

            System.out.println("Blueprint recuperado: "
                    + services.getBlueprint("alice", "house"));
            System.out.println("Todos los blueprints: "
                    + services.getAllBlueprints());
        }
    }
}

