/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import com.mongodb.client.model.Updates;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import objetosNegocio.EmpresaProductora;
import objetosNegocio.Residuo;
import objetosNegocio.Translado;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class EmpresaProductoraDAO {

    private MongoCollection getCollection() {
        ConexionBD conexion = new ConexionBD();
        MongoDatabase database = conexion.crearConexion();
        MongoCollection collection = database.getCollection("productores", EmpresaProductora.class);
        return collection;
    }

    public boolean agregar(EmpresaProductora ep) {
        try {
            Document doc = empresaProductoraToDocument(ep);
            this.getCollection().insertOne(doc);
            System.out.println("El objeto ha sido agregado exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar el objeto:");
            e.printStackTrace();
            return false;
        }
    }

    private Document empresaProductoraToDocument(EmpresaProductora ep) {
        Document doc = new Document();
        doc.append("idEmpresa", ep.getIdEmpresa());
        doc.append("nomEmpresa", ep.getNomEmpresa());
        // Aquí puedes agregar la lógica para convertir la lista de residuos a un formato adecuado
        return doc;
    }

    public boolean editar(EmpresaProductora ep) {

        try {
            UpdateResult result = this.getCollection().updateOne(eq("idEmpresa", ep.getIdEmpresa()), combine(set("nomEmpresa", ep.getNomEmpresa()), set("residuos", ep.getResiduos())));
            System.out.println("Objeto editado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar el objeto: ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean eliminar(EmpresaProductora ep) {
        try {
            DeleteResult result = this.getCollection().deleteOne(eq("idEmpresa", ep.getIdEmpresa()));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            return false;
        }
    }

    public EmpresaProductora buscar(EmpresaProductora ep) {
        try {
            EmpresaProductora result = (EmpresaProductora) this.getCollection().find(eq("idEmpresa", ep.getIdEmpresa())).first();
            return result;

        } catch (Exception e) {
            System.out.println("Error en la busqueda");
            e.printStackTrace();
            return null;
        }
    }

    public EmpresaProductora buscarPorId(ObjectId id) {
        try {
            EmpresaProductora epa = (EmpresaProductora) this.getCollection().find(eq("idEmpresa", id)).first();
            return epa;
        } catch (Exception e) {
            System.out.println("Error al buscar la empresa por ID:");
            e.printStackTrace();
            return null;
        }
    }

    public List<EmpresaProductora> buscarPorNombre(String nomEmpresa) {
        List<EmpresaProductora> lista = new ArrayList<>();
        String regexPattern = "^.*" + nomEmpresa + ".*$";
        regexPattern = regexPattern.toLowerCase();

        MongoCursor<EmpresaProductora> cursor = this.getCollection().find(regex("nomEmpresa",regexPattern)).iterator();

        try {
            while (cursor.hasNext()) {
                EmpresaProductora epa = cursor.next();
                lista.add(epa);
            }
        } finally {
            cursor.close();
        }
        return lista;

    }

    public List<EmpresaProductora> obtenerEmpresas() {
        List<EmpresaProductora> lista = new ArrayList<>();
        MongoCursor<EmpresaProductora> cursor = this.getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                EmpresaProductora emp = cursor.next();
                lista.add(emp);
            }
        } finally {
            cursor.close();
        }
        return lista;
    }

}
