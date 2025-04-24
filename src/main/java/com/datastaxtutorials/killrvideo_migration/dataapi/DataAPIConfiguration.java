package com.datastaxtutorials.killrvideo_migration.dataapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.core.options.DataAPIClientOptions;
import com.datastax.astra.client.databases.Database;
import com.datastax.astra.client.tables.Table;

import com.datastaxtutorials.killrvideo_migration.dataapi.entities.LatestVideoTableEntity;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.VideoRatingTableEntity;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.VideoTableEntity;

@Configuration
public class DataAPIConfiguration {

    static Logger logger = LoggerFactory.getLogger(DataAPIConfiguration.class);

    @Value("${astra.api.application-token}")
    private String astraToken;

    @Value("${astra.api.database-endpoint}")
    private String astraDbApiEndpoint;

    @Value("${astra.cql.driver-config.basic.session-keyspace}")
    private String astraDbKeyspace;

    @Bean
    public DataAPIClient dataAPIClient() {
        logger.info("Initializing Data API Client...");
        DataAPIClientOptions options = new DataAPIClientOptions().logRequests();
        return new DataAPIClient(astraToken, options);
    }
    
    @Bean
    public Database database(DataAPIClient client) {
        logger.info("Initializing database ...");
        // astraDbKeyspace can be omitted if default value : default_keyspace is used
        return client.getDatabase(astraDbApiEndpoint, astraDbKeyspace);
    }
    
    @Bean("table.videos")
    public Table<VideoTableEntity> tableProduct(Database db) {
        if (db.tableExists("videos")) {
            return db.getTable(VideoTableEntity.class);
        } else {
            logger.info("Table 'videos' does not exist, creating it ...");
            return db.createTable(VideoTableEntity.class);
        }
    }
    
    @Bean("table.latest_videos")
    public Table<LatestVideoTableEntity> tableLatestVideos(Database db) {
		if (db.tableExists("latest_videos")) {
			return db.getTable(LatestVideoTableEntity.class);
		} else {
			logger.info("Table 'latest_videos' does not exist, creating it ...");
			return db.createTable(LatestVideoTableEntity.class);
		}
	}
    
    @Bean("table.video_ratings")
    public Table<VideoRatingTableEntity> tableVideoRatings(Database db) {
		if (db.tableExists("video_ratings")) {
			return db.getTable(VideoRatingTableEntity.class);
		} else {
			logger.info("Table 'video_ratings' does not exist, creating it ...");
			return db.createTable(VideoRatingTableEntity.class);
		}
	}
}
