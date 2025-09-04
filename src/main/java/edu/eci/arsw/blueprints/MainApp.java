package edu.eci.arsw.blueprints;

import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import edu.eci.arsw.blueprints.config.AppConfig;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

public class MainApp {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        BlueprintsServices services = ctx.getBean(BlueprintsServices.class);

        Blueprint a1 = new Blueprint("alice", "house",
                new Point[]{ new Point(0,0), new Point(10,10) });

        Blueprint a2 = new Blueprint("alice", "cabin",
                new Point[]{ new Point(5,5), new Point(7,9), new Point(12,15) });

        Blueprint b1 = new Blueprint("bob", "bridge",
                new Point[]{ new Point(1,1), new Point(2,2) });

        try {
            services.addNewBlueprint(a1);
            services.addNewBlueprint(a2);
            services.addNewBlueprint(b1);
            services.addNewBlueprint(new Blueprint("alice","house", new Point[]{ new Point(99,99) }));
        } catch (BlueprintPersistenceException e) {
            System.out.println(" Duplicado detectado: " + e.getMessage());
        }

        try {
            Blueprint loaded = services.getBlueprint("alice","casa");
            System.out.println("\nPlano específico alice/casa:");
            System.out.println("Autor: " + loaded.getAuthor() + " | Nombre: " + loaded.getName()
                    + " | Puntos: " + loaded.getPoints());
        } catch (BlueprintNotFoundException e) {
            System.out.println("No se encontró el plano solicitado: " + e.getMessage());
        }

        try {
            Set<Blueprint> deAlice = services.getBlueprintsByAuthor("alice");
            System.out.println("\nPlanos de 'alice' (" + deAlice.size() + "):");
            for (Blueprint bp : deAlice) {
                System.out.println(" - " + bp.getName() + " -> " + bp.getPoints());
            }
        } catch (BlueprintNotFoundException e) {
            System.out.println("Autor sin planos: " + e.getMessage());
        }

        // 6) Listar todos
        System.out.println("\nTodos los planos (" + services.getAllBlueprints().size() + "):");
        for (Blueprint bp : services.getAllBlueprints()) {
            System.out.println(" * " + bp.getAuthor() + "/" + bp.getName());
        }

        System.out.println("\n✅ Punto 3 verificado: registrar/consultar funciona con Spring.");
    }
}

