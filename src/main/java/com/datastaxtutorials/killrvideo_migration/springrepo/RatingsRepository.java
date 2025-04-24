package com.datastaxtutorials.killrvideo_migration.springrepo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.datastaxtutorials.killrvideo_migration.springentities.VideoRatingEntity;

public interface RatingsRepository  extends CassandraRepository<VideoRatingEntity, UUID>{

}
