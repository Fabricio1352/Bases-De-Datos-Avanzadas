/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import objetosNegocio.EmpresaProductora;

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
            this.getCollection().insertOne(ep);
            System.out.println("El objeto ha sido agregado exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al agregar el objeto:");
            e.printStackTrace();
            return false;
        }
    }
}
