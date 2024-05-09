/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

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

        EmpresasProductoras empresas = new EmpresasProductoras();
        List<Residuo> residuos = new ArrayList<>();
        residuos.add(new Residuo(new ObjectId(), "caca"));
        EmpresaProductora empresa1 = new EmpresaProductora("rio yaki", new ObjectId(), residuos);
        EmpresaProductoraDAO dao = new EmpresaProductoraDAO();
        dao.agregar(empresa1);
    }
}
