# ​ Blueprints – ARSW LAB03

Este laboratorio cubre la implementación de **inyección de dependencias con Spring**, **persistencia en memoria** y la aplicación de **filtros** sobre planos. A continuación se detalla qué hace cada clase en el proyecto.

---

## 📚 Resumen del laboratorio

Este laboratorio implementa un sistema para gestionar planos (`Blueprints`) utilizando Spring y una base de datos en memoria. Se aplican filtros para modificar los datos de los planos antes de ser retornados.

## 💻​ **Clases y su Funcionalidad**

### 1) **AppConfig.java** (Configuración Spring)

- **Ubicación:** `src/main/java/edu/eci/arsw/blueprints/config/AppConfig.java`
- **Responsabilidad:** Configura Spring con la anotación `@Configuration`. Utiliza `@ComponentScan` para detectar los beans en el paquete `edu.eci.arsw.blueprints` (que incluye los servicios, persistencia y filtros).

```java
@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfig { }
```

### 2) **Blueprint.java** (Modelo de Plano)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/model/Blueprint.java

- **Responsabilidad:** Representa un plano (Blueprint), que tiene un autor, un nombre y un conjunto de puntos (Point).

```java
public class Blueprint {
    private String author;
    private String name;
    private List<Point> points;
    
}
```
### 3) **Point.java** (Modelo de Punto)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/model/Point.java

- **Responsabilidad:** Representa un punto en el espacio con coordenadas X y Y.

```java
public class Point {
    private int x;
    private int y;
}
```

### 4) **InMemoryBlueprintPersistence.java** (Persistencia en Memoria)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/persistence/impl/InMemoryBlueprintPersistence.java

- **Responsabilidad:** Implementa la interfaz BlueprintsPersistence y almacena los planos en un HashMap en memoria. Gestiona las excepciones de persistencia, como los duplicados.

```java
@Repository
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {
    private Map<String, Blueprint> blueprints = new HashMap<>();
    
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException { ... }
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException { ... }
}
```

### 5) **BlueprintsServices.java** (Servicio de Planos)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/services/BlueprintsServices.java

- **Responsabilidad:** Proporciona la lógica de negocio. Permite agregar, consultar y obtener planos. Aplica un filtro en los resultados, que puede ser RedundancyFilter o SubsamplingFilter.

```java
@Service
public class BlueprintsServices {
    private final BlueprintsPersistence bpp;
    private final BlueprintFilter filter;

    @Autowired
    public BlueprintsServices(BlueprintsPersistence bpp, BlueprintFilter filter) {
        this.bpp = bpp;
        this.filter = filter;
    }

    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint raw = bpp.getBlueprint(author, name);
        return filter.apply(raw); 
    }
    
}
```
### 6) **BlueprintFilter.java** (Interfaz de Filtro)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/services/filters/BlueprintFilter.java

- **Responsabilidad:** Define la interfaz para aplicar filtros a los planos.

```java
public interface BlueprintFilter {
    Blueprint apply(Blueprint bp); 
}
```

### 7) **RedundancyFilter.java** (Filtro A: Elimina Duplicados Consecutivos)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/services/filters/RedundancyFilter.java

- **Responsabilidad:** Elimina los puntos consecutivos idénticos del plano. Utiliza la anotación @Primary para ser el filtro predeterminado.

```java
@Component
@Primary
public class RedundancyFilter implements BlueprintFilter {
    @Override
    public Blueprint apply(Blueprint bp) {
        List<Point> pts = bp.getPoints();
        if (pts == null || pts.isEmpty()) return new Blueprint(bp.getAuthor(), bp.getName(), new Point[]{});
        List<Point> out = new ArrayList<>();
        out.add(pts.get(0));
        for (int i = 1; i < pts.size(); i++) {
            Point prev = pts.get(i-1), curr = pts.get(i);
            if (curr.getX()!=prev.getX() || curr.getY()!=prev.getY()) out.add(curr);
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), out.toArray(Point[]::new));
    }
}
```

### 8) **SubsamplingFilter.java** (Filtro B: Submuestro)

- **Ubicación:** src/main/java/edu/eci/arsw/blueprints/services/filters/SubsamplingFilter.java

- **Responsabilidad:** Elimina 1 de cada 2 puntos del plano, alternando los puntos.

```java
@Component
public class SubsamplingFilter implements BlueprintFilter {
    @Override
    public Blueprint apply(Blueprint bp) {
        List<Point> pts = bp.getPoints();
        if (pts == null || pts.isEmpty()) {
            return new Blueprint(bp.getAuthor(), bp.getName(), new Point[]{});
        }
        List<Point> out = new ArrayList<>();
        for (int i = 0; i < pts.size(); i += 2) out.add(pts.get(i));
        return new Blueprint(bp.getAuthor(), bp.getName(), out.toArray(Point[]::new));
    }
}
```

- **Cómo alternar filtros (A/B)**

Para usar el filtro RedundancyFilter (A), toca poner @Primary en la clase del filtro que se quiera usar:

```java
@Component
@Primary
public class RedundancyFilter implements BlueprintFilter { ... }
```
```java
@Component
@Primary
public class SubsamplingFilter implements BlueprintFilter { ... }
```

## ▶️​ Cómo ejecutar

- Para ejecutar el programa principal

```java
mvn exec:java
```

<p align="center">
<img width="1214" height="474" alt="image" src="https://github.com/user-attachments/assets/02963714-f65d-40cc-af48-3ca1de415287" />
</p>

- Para compilar y ejecutar las pruebas

```java
mvn -U clean test
```
<p align="center">
<img width="1288" height="413" alt="image" src="https://github.com/user-attachments/assets/298ddd89-5a7d-4d30-a17d-7cddc420f1dc" />
</p>

## ✅ Pruebas

Las pruebas incluyen:

- **InMemoryPersistenceTest:** Valida la persistencia de planos en memoria, asegurando que no se guarden duplicados.

- **BlueprintsServicesWiringTest:** Testea la correcta inyección de BlueprintsServices.

- **RedundancyFilterTest / SubsamplingFilterTest:** Verifican la lógica de cada filtro.

- **FilterWiringWithSpringTest:** Verifica que el servicio aplica el filtro que está @Primary ya sea A o B.


