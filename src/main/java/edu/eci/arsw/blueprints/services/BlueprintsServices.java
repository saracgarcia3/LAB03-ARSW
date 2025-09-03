package edu.eci.arsw.blueprints.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

@Service
public class BlueprintsServices {

    private final BlueprintsPersistence bpp;

    @Autowired
    public BlueprintsServices(BlueprintsPersistence bpp) {
        this.bpp = bpp;
    }

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return bpp.getBlueprint(author, name);
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return bpp.getBlueprintsByAuthor(author);
    }

    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints();
    }
}

