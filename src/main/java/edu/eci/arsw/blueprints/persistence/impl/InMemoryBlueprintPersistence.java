package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final Map<Key, Blueprint> blueprints = new HashMap<>();

    private static final class Key {
        final String author; final String name;
        Key(String a, String n){ this.author=a; this.name=n; }
        @Override public boolean equals(Object o){
            if(this==o) return true; if(!(o instanceof Key)) return false;
            Key k=(Key)o; return Objects.equals(author,k.author) && Objects.equals(name,k.name);
        }
        @Override public int hashCode(){ return Objects.hash(author,name); }
    }

    @Override
    public synchronized void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        Key k = new Key(bp.getAuthor(), bp.getName());
        if (blueprints.containsKey(k)) {
            throw new BlueprintPersistenceException("Blueprint already exists: " + bp.getAuthor() + "/" + bp.getName());
        }
        blueprints.put(k, bp);
    }

    @Override
    public synchronized Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = blueprints.get(new Key(author, name));
        if (bp == null) throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + name);
        return bp;
    }

    @Override
    public synchronized Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> out = new HashSet<>();
        for (Map.Entry<Key, Blueprint> e : blueprints.entrySet()) {
            if (e.getKey().author != null && e.getKey().author.equals(author)) {
                out.add(e.getValue());
            }
        }
        if (out.isEmpty()) throw new BlueprintNotFoundException("No blueprints for author: " + author);
        return out;
    }

    @Override
    public synchronized Set<Blueprint> getAllBlueprints() {
        return new HashSet<>(blueprints.values());
    }
}
