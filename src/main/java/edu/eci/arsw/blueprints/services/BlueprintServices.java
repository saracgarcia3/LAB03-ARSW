package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BlueprintServices {

    private final BlueprintsPersistence bpp;

    // Inyección por constructor (recomendada)
    @Autowired
    public BlueprintServices(BlueprintsPersistence bpp) {
        this.bpp = bpp;
    }

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    /** PUNTO 2: completar esta operación */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    /** PUNTO 2: completar esta operación */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }

    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints();
    }
}

