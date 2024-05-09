/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.EmpresaProductora;
import objetosNegocio.Residuo;
import objetosNegocio.Translado;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author fabri
 */
public class EmpresasProductoras {

    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;

    public EmpresasProductoras() {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase("hola");
        collection = database.getCollection("productores");
    }

    private Document objectToDocument(EmpresaProductora empresaProductora) {
        Document empresa = new Document()
                .append("nomEmpresa", empresaProductora.getNomEmpresa());

        List<Residuo> residuos = empresaProductora.getResiduos();
        if (residuos != null && !residuos.isEmpty()) {
            List<Document> residuosDocument = new ArrayList<>();
            for (Residuo residuo : residuos) {
                Document residuoDoc = new Document()
                        .append("_id", residuo.getIdResiduo())
                        .append("compuestos", residuo.getCompuestos());
                List<ObjectId> transladosId = new ArrayList<>();
                List<Translado> translados = residuo.getTranslados();

                if (residuo.getTranslados() != null && !residuo.getTranslados().isEmpty()) {

                    for (Translado t : translados) {
                        ObjectId transladoId = t.getIdTranslado();
                        if (transladoId != null) {
                            transladosId.add(transladoId);
                        }
                    }
                }

                residuoDoc.append("translados", transladosId);
                residuosDocument.add(residuoDoc);
            }
            empresa.append("residuos", residuosDocument);
        }
        return empresa;

    }
    
    public ObjectId agregarEmpresaProductora(EmpresaProductora empresaProductora){
        ObjectId id = new ObjectId();
        Document empresa   = objectToDocument(empresaProductora).append("_id",id);
        collection.insertOne(empresa);
        return id;
    }
    
    public ArrayList<EmpresaProductora> obtenerEmpresasProductoras(){
        ArrayList<EmpresaProductora> empresas = new ArrayList();
        MongoCursor<Document> cursor = collection.find().iterator();
        
        try{
            while(cursor.hasNext()){
                Document d = cursor.next();
                
                EmpresaProductora p = new EmpresaProductora(d.getObjectId("_id"), d.getString("nomEmpresa"));
                System.out.println(p.toString());
                empresas.add(p);
            }
            return empresas;
        }finally{
            cursor.close();
        }
    }
    
}
