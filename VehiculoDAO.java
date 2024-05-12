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
import objetosNegocio.EmpresaTransporte;
import objetosNegocio.Vehiculo;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class VehiculoDAO {
    
    private MongoCollection getCollection() {
        ConexionBD conexion = new ConexionBD();
        MongoDatabase database = conexion.crearConexion();
        MongoCollection collection = database.getCollection("vehiculos", Vehiculo.class);
        return collection;
    }

    public boolean agregar(Vehiculo ep) {
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


    public boolean editar(Vehiculo ep) {

        try {
//            UpdateResult result = this.getCollection().updateOne(eq("idVehiculo", ep.getIdVehiculo()), combine(set("km", ep.getKm()),set("nomVehiculo", ep.getNomVehiculo()), set("empresatransporte", ep.getEmpresaTransporte())));
             this.getCollection().updateOne(eq("idVehiculo", ep.getIdVehiculo()), combine(set("km", ep.getKm()),set("nomVehiculo", ep.getNomVehiculo())));
            System.out.println("Objeto editado correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al editar el objeto: ");
            e.printStackTrace();
            return false;
        }

    }

    public boolean eliminar(Vehiculo ep) {
        try {
            DeleteResult result = this.getCollection().deleteOne(eq("idVehiculo", ep.getIdVehiculo()));
            return result.getDeletedCount() == 1;
        } catch (Exception e) {
            System.out.println("Error al eliminar");
            return false;
        }
    }

    public Vehiculo buscar(Vehiculo ep) {
        try {
            Vehiculo result = (Vehiculo) this.getCollection().find(eq("idVehiculo", ep.getIdVehiculo())).first();
            return result;

        } catch (Exception e) {
            System.out.println("Error en la busqueda");
            e.printStackTrace();
            return null;
        }
    }

    public Vehiculo buscarPorId(ObjectId id) {
        try {
            Vehiculo epa = (Vehiculo) this.getCollection().find(eq("idVehiculo", id)).first();
            return epa;
        } catch (Exception e) {
            System.out.println("Error al buscar la empresa por ID:");
            e.printStackTrace();
            return null;
        }
    }
    
       public List<Vehiculo> obtenerTodos(){
        List<Vehiculo> lista = new ArrayList<>();
        MongoCursor<Vehiculo> cursor = this.getCollection().find().iterator();
        try{
            while(cursor.hasNext()){
                Vehiculo emp = cursor.next();
                lista.add(emp);
            }
        }finally{
            cursor.close();
        }
        return lista;
    }

    public List<Vehiculo> obtenerVehiculosDeEmpresa(EmpresaTransporte selectedValue) {
        
              List<Vehiculo> lista = new ArrayList<>();
        System.out.println("id desde el dao:" + selectedValue.getIdEmpresa());
        MongoCursor<Vehiculo> cursor = this.getCollection().find(eq("idVehiculo", selectedValue.getIdEmpresa())).iterator();
        try {
            while (cursor.hasNext()) {
                Vehiculo emp = cursor.next();
                lista.add(emp);
            }
        } finally {
            cursor.close();
        }
        System.out.println("lista desde el dao: "+lista);
        return lista;





    }
 
    
 
}
