package edu.eci.arsw.blueprints.services.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

@Component("subsamplingFilter")
public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint bp) {
        List<Point> pts = bp.getPoints();
        if (pts == null || pts.isEmpty()) {
            return new Blueprint(bp.getAuthor(), bp.getName(), new Point[]{});
        }
        List<Point> out = new ArrayList<>();
        for (int i = 0; i < pts.size(); i += 2) {
            out.add(pts.get(i));
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), out.toArray(Point[]::new));

    }
}

