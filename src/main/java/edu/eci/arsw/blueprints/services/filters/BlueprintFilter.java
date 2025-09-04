package edu.eci.arsw.blueprints.services.filters;
import edu.eci.arsw.blueprints.model.Blueprint;


public interface BlueprintFilter {
    
    Blueprint apply(Blueprint bp);
}
