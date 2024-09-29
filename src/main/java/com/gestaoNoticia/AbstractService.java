package com.gestaoNoticia;

import com.gestaoNoticia.db.MongoDBRepository;

public class AbstractService {

    protected final MongoDBRepository mongoDBRepository;

    public AbstractService(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    public MongoDBRepository getMongoDBConnector() {
        return mongoDBRepository;
    }
}
