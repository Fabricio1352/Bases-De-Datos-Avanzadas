/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.EmpresaTransporte;
import objetosNegocio.Vehiculo;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class EmpresaTransporteDAO {

    private MongoCollection getCollection() {
        ConexionBD conexion = new ConexionBD();
        MongoDatabase database = conexion.crearConexion();
        MongoCollection collection = database.getCollection("transportistas", EmpresaTransporte.class);
        return collection;
    }

    public boolean agregar(EmpresaTransporte ep) {
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

    public boolean editar(EmpresaTransporte ep) {

        try {
            UpdateResult result = this.getCollection().updateOne(eq("idEmpresa", ep.getIdEmpresa()), combine(set("nomEmpresa", ep.getNomEmpresa()), set("vehiculos", ep.getVehiculos()), set("translado", ep.getTranslado())));
            System.out.println("Objeto editado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar el objeto: ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean eliminar(EmpresaTransporte ep) {
        try {
            DeleteResult result = this.getCollection().deleteOne(eq("idEmpresaTransporte", ep.getIdEmpresa()));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            return false;
        }
    }

    public EmpresaTransporte buscar(EmpresaTransporte ep) {
        try {
            EmpresaTransporte result = (EmpresaTransporte) this.getCollection().find(eq("idEmpresaTransporte", ep.getIdEmpresa())).first();
            return result;

        } catch (Exception e) {
            System.out.println("Error en la busqueda");
            e.printStackTrace();
            return null;
        }
    }

    public EmpresaTransporte buscarPorId(ObjectId id) {
        try {
            EmpresaTransporte epa = (EmpresaTransporte) this.getCollection().find(eq("idEmpresaTransporte", id)).first();
            return epa;
        } catch (Exception e) {
            System.out.println("Error al buscar la empresa por ID:");
            e.printStackTrace();
            return null;
        }
    }

    public List<EmpresaTransporte> obtenerTodos() {
        List<EmpresaTransporte> lista = new ArrayList<>();
        MongoCursor<EmpresaTransporte> cursor = this.getCollection().find().iterator();
        try {
            while (cursor.hasNext()) {
                EmpresaTransporte emp = cursor.next();
                lista.add(emp);
            }
        } finally {
            cursor.close();
        }
        return lista;
    }

    public List<EmpresaTransporte> buscarPorNombre(String text) {
        List<EmpresaTransporte> lista = new ArrayList<>();
        String regexPattern = "^.*" + text + ".*$";
        regexPattern = regexPattern.toLowerCase();

        MongoCursor<EmpresaTransporte> cursor = this.getCollection().find(regex("nomEmpresa", regexPattern)).iterator();

        try {
            while (cursor.hasNext()) {
                EmpresaTransporte epa = cursor.next();
                lista.add(epa);
            }
        } finally {
            cursor.close();
        }
        return lista;

    }
}
