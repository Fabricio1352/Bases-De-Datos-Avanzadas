/**
 * Fachada.java
 *
 * Clase creada el 3 de Diciembre de 2023
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Esta clase es un medio generico para guardar datos en la base de datos. Se
 * utilizan EntityManager, con las entidades que estan conectadas a la unidad de
 * persistencia, se hacen operaciones CRUD, se manejan parametros de clase y
 * datos genericos.
 *
 * @author Fabricio Aldaco Aguilera
 * @param <T> tipo de dato a controlar
 *
 */
public class Fachada<T> {

    private static final String UNIDAD_DE_PERSISTENCIA = "disenoP"; // unidad de persistencia
    private static EntityManagerFactory emf; //crear instancias de EntityManager, que es la interfaz principal para interactuar con JPA.
    private static Fachada instancia; //singleton 
    private final Map<Class<?>, EntityManager> entityManagers;

    /* Un mapa que mantiene instancias específicas de EntityManager para cada tipo de entidad (Class<?>). Este mapa asegura que haya un EntityManager único para cada tipo de entidad, evitando la creación innecesaria de múltiples instancias. */
    private Fachada() {
        emf = Persistence.createEntityManagerFactory(UNIDAD_DE_PERSISTENCIA); //La unidad de persistencia se crea
        entityManagers = new HashMap<>();
    }

    /**
     * La palabra clave synchronized en Java se utiliza para controlar el acceso
     * a bloques de código o métodos por múltiples hilos (threads)
     * simultáneamente. Cuando un método o bloque de código está marcado como
     * synchronized, solo un hilo puede ejecutar ese método o bloque a la vez,
     * mientras que otros hilos deben esperar su turno para acceder a él.
     *
     * la sincronización se utiliza para evitar problemas relacionados con la
     * concurrencia y asegurarse de que solo se cree una única instancia de la
     * clase, incluso cuando múltiples hilos intentan obtenerla simultáneamente.
     * y se romperia el patron singleton
     *
     *
     * @return a
     */
    public static synchronized Fachada getInstance() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    /**
     * ahora se comunica dentro de la clase, cada vez que necesite una netidad,
     * la consigue de aqui y la clase especifcia viene de cada metodo entocnes
     * cada vez que genero la instancai de fachada, con diferente tipo de dato,
     * se crea un entity manager distinto *
     *
     * por unos metodos recibe el dato, y en otros, recibe la clase pero siempre
     * esta clase va intentar conseguir su entidad.
     */
    /**
     * método privado que obtiene el EntityManager correspondiente para una
     * clase de entidad dada. Utiliza computeIfAbsent para garantizar que se
     * cree un EntityManager solo si no existe para la clase de entidad dada.
     *
     * @param claseEntidad entidad
     * @return regresa su entity manager
     *
     * osea, si no existe, creala
     */
    private EntityManager getEntityManager(Class<?> claseEntidad) {
        return entityManagers.computeIfAbsent(claseEntidad, k -> emf.createEntityManager());
    }

    public <T> void crear(T dato) {
        EntityManager entityManager = getEntityManager(dato.getClass());
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(dato);
        transaction.commit();
    }

    public <T> void eliminar(Class<T> claseEntidad, Object id) {
        EntityManager entityManager = getEntityManager(claseEntidad);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        T dato = entityManager.find(claseEntidad, id);
        if (dato != null) {
            entityManager.remove(dato);
        }
        transaction.commit();
    }

    public <T> void actualizar(T dato) {
        EntityManager entityManager = getEntityManager(dato.getClass());
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(dato);
        transaction.commit();
    }

    public <T> List<T> obtenerTodos(Class<T> claseEntidad) {
        EntityManager entityManager = getEntityManager(claseEntidad);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(claseEntidad);
        cq.select(cq.from(claseEntidad));
        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * Método para obtener un dato por su ID.
     *
     * @param claseEntidad Clase de la entidad.
     * @param id ID del dato que se desea obtener.
     * @return El dato encontrado, o null si no se encuentra.
     */
    public <T> T obtenerPorId(Class<T> claseEntidad, Object id) {
        EntityManager entityManager = getEntityManager(claseEntidad);
        return entityManager.find(claseEntidad, id);
    }

}
