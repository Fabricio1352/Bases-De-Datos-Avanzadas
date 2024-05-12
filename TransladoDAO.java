
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Translado;
import objetosNegocio.Vehiculo;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class TransladoDAO {
        
    private MongoCollection getCollection() {
        ConexionBD conexion = new ConexionBD();
        MongoDatabase database = conexion.crearConexion();
        MongoCollection collection = database.getCollection("translados", Translado.class);
        return collection;
    }

    public boolean agregar(Translado ep) {
        try {
//            Document doc = empresaProductoraToDocument(ep);
            this.getCollection().insertOne(ep);
            System.out.println("El objeto ha sido agregado exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar el objeto:");
            e.printStackTrace();
            return false;
        }
    }


    public boolean editar(Translado ep) {

        try {
            UpdateResult result = this.getCollection().updateOne(eq("idTranslado", ep.getIdTranslado()), combine(set("fechaenvio", ep.getFechaEnviado()),set("fecharecibo", ep.getFechaRecibido()), set("origen", ep.getOrigen()), set("destino",ep.getDestino()), set("empresatransporte", ep.getEmpresasTransporte()), set("residuo",ep.getResiduo()), set("empresaproductora",ep.getEmpresaProductora())));
            System.out.println("Objeto editado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar el objeto: ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean eliminar(Translado ep) {
        try {
            DeleteResult result = this.getCollection().deleteOne(eq("idTranslado", ep.getIdTranslado()));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            return false;
        }
    }

    public Translado buscar(Translado ep) {
        try {
            Translado result = (Translado) this.getCollection().find(eq("idTranslado", ep.getIdTranslado())).first();
            return result;

        } catch (Exception e) {
            System.out.println("Error en la busqueda");
            e.printStackTrace();
            return null;
        }
    }

    public Translado buscarPorId(ObjectId id) {
        try {
            Translado epa = (Translado) this.getCollection().find(eq("idTranslado", id)).first();
            return epa;
        } catch (Exception e) {
            System.out.println("Error al buscar la empresa por ID:");
            e.printStackTrace();
            return null;
        }
    }
    
        public List<Translado> obtenerTodos(){
        List<Translado> lista = new ArrayList<>();
        MongoCursor<Translado> cursor = this.getCollection().find().iterator();
        try{
            while(cursor.hasNext()){
                Translado emp = cursor.next();
                lista.add(emp);
            }
        }finally{
            cursor.close();
        }
        return lista;
    }
 
}
