package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class BlueprintsServices {

    private final BlueprintsPersistence bpp;

 
    public BlueprintsServices(BlueprintsPersistence bpp) {
        this.bpp = bpp;
    }

    
    public void addNewBlueprint(Blueprint bp) {
        try {
            bpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            // La firma no permite checked exceptions; propagamos como RuntimeException.
            throw new RuntimeException("Error guardando el blueprint", e);
        }
    }


    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints();
    }

   
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

   
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }
}
