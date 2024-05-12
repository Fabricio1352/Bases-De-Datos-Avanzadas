/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import objetosNegocio.EmpresaProductora;
import objetosNegocio.Residuo;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class ResiduoDAO {

    private MongoCollection getCollection() {
        ConexionBD conexion = new ConexionBD();
        MongoDatabase database = conexion.crearConexion();
        MongoCollection collection = database.getCollection("residuos", Residuo.class);
        return collection;
    }

    public boolean agregar(Residuo ep) {
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

    /*
    private Document empresaProductoraToDocument(Residuo ep) {
        Document doc = new Document();
        doc.append("idResiduo", ep.getIdResiduo());
        doc.append("compuestos", ep.getCompuestos());
        doc.append("translados", ep.getTranslados());
        doc.append("empresaProductora", ep.getEmpresaProductora());
        
        return doc;
    }
     */
    public boolean editar(Residuo ep) {

        try {
//            UpdateResult result = this.getCollection().updateOne(eq("idResiduo", ep.getIdResiduo()), combine(set("translados", ep.getTranslados()), set("compuestos", ep.getCompuestos()), set("empresaProductora", ep.getEmpresaProductora()), set("translados", ep.getTranslados())));
            this.getCollection().updateOne(eq("idResiduo", ep.getIdResiduo()),combine(set("compuestos", ep.getCompuestos())) );
            System.out.println("Objeto editado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar el objeto: ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean eliminar(Residuo ep) {
        try {
            DeleteResult result = this.getCollection().deleteOne(eq("idResiduo", ep.getIdResiduo()));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            return false;
        }
    }

    public Residuo buscar(Residuo ep) {
        try {
            Residuo result = (Residuo) this.getCollection().find(eq("idResiduo", ep.getIdResiduo())).first();
            return result;

        } catch (Exception e) {
            System.out.println("Error en la busqueda");
            e.printStackTrace();
            return null;
        }
    }

    public Residuo buscarPorId(ObjectId id) {
        try {
            Residuo epa = (Residuo) this.getCollection().find(eq("idResiduo", id)).first();
            return epa;
        } catch (Exception e) {
            System.out.println("Error al buscar la empresa por ID:");
            e.printStackTrace();
            return null;
        }
    }

    public List<Residuo> obtenerResiduos() {
        List<Residuo> lista = new ArrayList<>();
        MongoCursor<Residuo> cursor = this.getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                Residuo emp = cursor.next();
                lista.add(emp);
            }
        } finally {
            cursor.close();
        }
        return lista;
    }

    public List<Residuo> obtenerResiduosDeEmpresa(EmpresaProductora selectedValue) {
        
        List<Residuo> lista = new ArrayList<>();
        System.out.println("id desde el dao:" + selectedValue.getIdEmpresa());
        MongoCursor<Residuo> cursor = this.getCollection().find(eq("idEmpresa", selectedValue.getIdEmpresa())).iterator();
        try {
            while (cursor.hasNext()) {
                Residuo emp = cursor.next();
                lista.add(emp);
            }
        } finally {
            cursor.close();
        }
        System.out.println("lista desde el dao: "+lista);
        return lista;

    }


}
