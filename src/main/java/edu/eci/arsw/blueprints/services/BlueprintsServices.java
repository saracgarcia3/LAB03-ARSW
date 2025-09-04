package edu.eci.arsw.blueprints.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.filters.BlueprintFilter;

@Service
public class BlueprintsServices {

    private final BlueprintsPersistence bpp;
    private final BlueprintFilter filter;

    @Autowired
    public BlueprintsServices(BlueprintsPersistence bpp,
                              @Qualifier("redundancyFilter") BlueprintFilter filter) { // 👈 cambia a "subsamplingFilter" para el otro
        this.bpp = bpp;
        this.filter = filter;
    }

    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint raw = bpp.getBlueprint(author, name);
        return filter.apply(raw);
    }

    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> raws = bpp.getBlueprintsByAuthor(author);
        return raws.stream().map(filter::apply).collect(Collectors.toSet());
    }

    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints().stream().map(filter::apply).collect(Collectors.toSet());
    }
}

