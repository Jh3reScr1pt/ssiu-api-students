package com.example.ssiu_spring_boot_api.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.example.ssiu_spring_boot_api.models.StudentEntity;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBStudentRepository implements StudentRepository {
    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<StudentEntity> studentCollection;

    public MongoDBStudentRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        studentCollection = client.getDatabase("univalle").getCollection("students", StudentEntity.class);
    }

    @Override
    public StudentEntity save(StudentEntity studentEntity) {
        studentEntity.setId(new ObjectId());
        studentCollection.insertOne(studentEntity);
        return studentEntity;
    }

    @Override
    public List<StudentEntity> saveAll(List<StudentEntity> studentEntities) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                studentEntities.forEach(p -> p.setId(new ObjectId()));
                studentCollection.insertMany(clientSession, studentEntities);
                return studentEntities;
            }, txnOptions);
        }
    }

    @Override
    public List<StudentEntity> findAll() {
        return studentCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<StudentEntity> findAll(List<String> ids) {
        return studentCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public StudentEntity findOne(String id) {
        return studentCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return studentCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return studentCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> studentCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> studentCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public StudentEntity update(StudentEntity studentEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return studentCollection.findOneAndReplace(eq("_id", studentEntity.getId()), studentEntity, options);
    }

    @Override
    public long update(List<StudentEntity> studentEntities) {
        List<ReplaceOneModel<StudentEntity>> writes = studentEntities.stream()
                                                                   .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()),
                                                                                                   p))
                                                                   .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> studentCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).toList();
    }

}
