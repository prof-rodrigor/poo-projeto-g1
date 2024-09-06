package com.gestaoNoticia.noticia.service;

import com.gestaoNoticia.db.MongoDBRepository;
import com.gestaoNoticia.noticia.model.Noticia;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class NoticiaService {
    private final MongoCollection<Document> collection;

    public NoticiaService(MongoDBRepository mongoDBRepository) {
        MongoDatabase database = mongoDBRepository.getDatabase("gestaoNoticia");
        this.collection = database.getCollection("noticias");
    }

    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new LinkedList<>();
        for (Document doc : collection.find()) {
            noticias.add(documentToNoticia(doc));
        }
        return noticias;
    }

    public Optional<Noticia> buscarNoticiaPorId(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        return Optional.ofNullable(doc).map(NoticiaService::documentToNoticia);
    }

    public void adicionarNoticia(Noticia noticia) {
        Document doc = noticiaToDocument(noticia);
        collection.insertOne(doc);
    }

    //public void atualizarNoticia(Noticia noticiaAtualizada) {
    //    Document doc = noticiaToDocument(noticiaAtualizada);
    //    collection.replaceOne(eq("_id", new ObjectId(noticiaAtualizada.getId().toString())), doc);
    //}

    public void removerNoticia(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }

    private static Noticia documentToNoticia(Document doc) {
        Date data = doc.getDate("dataPublicacao");

        LocalDateTime dataHora = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Noticia noticia = new Noticia(
                doc.get("_id").toString(),
                doc.getString("titulo"),
                doc.getString("subtitulo"),
                doc.getString("conteudo"),
                doc.getString("autor"),
                doc.getString("categoria"),
                dataHora
        );
        return noticia;
    }

    public static Document noticiaToDocument(Noticia noticia) {
        Document doc = new Document();
        if (noticia.getId() != null) {
            doc.put("_id", noticia.getId());
        }
        doc.put("titulo", noticia.getTitulo());
        doc.put("subtitulo", noticia.getSubtitulo());
        doc.put("conteudo", noticia.getConteudo());
        doc.put("autor", noticia.getAutor());
        doc.put("categoria", noticia.getCategoria());
        doc.put("dataPublicacao", noticia.getDataPublicacao());
        return doc;
    }

    public void editarNoticia(Noticia noticia, String id) {
        try {
            Document filter = new Document("_id", new ObjectId(id));
            Document atualizacao = new Document("$set", new Document("titulo", noticia.getTitulo())
                    .append("subtitulo", noticia.getSubtitulo())
                    .append("conteudo", noticia.getConteudo())
                    .append("autor", noticia.getAutor())
                    .append("categoria", noticia.getCategoria())
            );
            collection.updateOne(filter, atualizacao);
        } catch (MongoException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Noticia> buscarNoticiasFiltradas(Map<String, List<String>> filtros) {
        List<Noticia> noticias = new LinkedList<>();
        Document filter = new Document();

        for (Map.Entry<String, List<String>> entry : filtros.entrySet()) {
            filter.append(entry.getKey(), entry.getValue().getFirst());
        }

        for (Document doc: collection.find(filter)) {
            noticias.add(documentToNoticia(doc));
        }

        return noticias;
    }
}
