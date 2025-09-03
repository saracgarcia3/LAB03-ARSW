package edu.eci.arsw.blueprints.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Blueprint {
    private String author;
    private String name;
    private List<Point> points = new ArrayList<>();

    public Blueprint() { }

    public Blueprint(String author, String name, Point[] pts) {
        this.author = author;
        this.name = name;
        if (pts != null) {
            for (Point p : pts) points.add(p);
        }
    }

    public String getAuthor() { return author; }
    public String getName() { return name; }
    public List<Point> getPoints() { return points; }

    public void setAuthor(String author) { this.author = author; }
    public void setName(String name) { this.name = name; }
    public void setPoints(List<Point> points) { this.points = points; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blueprint)) return false;
        Blueprint that = (Blueprint) o;
        return Objects.equals(author, that.author) &&
               Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(author, name); }
}
