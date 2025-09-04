package edu.eci.arsw.blueprints.services.filters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;   

@Primary
@Component("redundancyFilter")
public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint bp) {
        List<Point> pts = bp.getPoints();
        if (pts == null || pts.isEmpty()) {
            return new Blueprint(bp.getAuthor(), bp.getName(), new Point[]{});
        }

        List<Point> out = new ArrayList<>();
        out.add(pts.get(0));
        for (int i = 1; i < pts.size(); i++) {
            Point prev = pts.get(i - 1);
            Point curr = pts.get(i);
            if (!(curr.getX() == prev.getX() && curr.getY() == prev.getY())) {
                out.add(curr);
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), out.toArray(Point[]::new));

    }
}

