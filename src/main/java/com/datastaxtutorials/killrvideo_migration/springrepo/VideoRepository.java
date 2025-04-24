package com.datastaxtutorials.killrvideo_migration.springrepo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import com.datastaxtutorials.killrvideo_migration.springentities.VideoEntity;

public interface VideoRepository extends CassandraRepository<VideoEntity,UUID> {

	static final String FIND_BY_TAG = "SELECT * FROM videos WHERE tags CONTAINS ?0";
	
	@Query(FIND_BY_TAG)
	List<VideoEntity> findByTagsContaining(String tag);
}