/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.EmpresaProductora;
import objetosNegocio.Residuo;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class testConexion {

    public static void main(String[] args) {
        List<Residuo> residuos = new ArrayList<>();
        EmpresaProductoraDAO dao = new EmpresaProductoraDAO();
        EmpresaProductora empresa1 = new EmpresaProductora("holaa", new ObjectId(), residuos);
        
        Residuo r = new Residuo(new ObjectId(), "caca");
        ResiduoDAO rdao = new ResiduoDAO();
        rdao.agregar(r);
//        dao.agregar(empresa1);
//        empresa1.setNomEmpresa("otro name");
//        dao.editar(empresa1);
//        
//        dao.eliminar(empresa1);
//        EmpresaProductora ep = dao.buscar(empresa1);
    }
}
