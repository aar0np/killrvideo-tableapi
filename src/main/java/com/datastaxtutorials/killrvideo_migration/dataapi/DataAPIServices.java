package com.datastaxtutorials.killrvideo_migration.dataapi;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.datastax.astra.client.core.query.Filter;
import com.datastax.astra.client.core.query.Sort;
import com.datastax.astra.client.core.vector.DataAPIVector;
import com.datastax.astra.client.databases.Database;
import com.datastax.astra.client.tables.Table;
import com.datastax.astra.client.tables.commands.options.TableFindOptions;
import com.datastax.astra.client.tables.cursor.TableFindCursor;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.LatestVideoTableEntity;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.VideoTableEntity;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.VideoRatingTableEntity;

@Repository
public class DataAPIServices {

	Database db;
	
	public DataAPIServices(Database db) {
        this.db = db;
    }
	
	@Autowired
	@Qualifier("table.videos")
	Table<VideoTableEntity> videosRepository;
	
	@Autowired
	@Qualifier("table.latest_videos")
	Table<LatestVideoTableEntity> latestVideosRepository;
	
	@Autowired
	@Qualifier("table.video_ratings")
	Table<VideoRatingTableEntity> videoRatingsRepository;

	public Optional<VideoTableEntity> findVideoById(UUID videoId) {
		Filter filter = new Filter(Map.of("videoid", videoId));
		
		return videosRepository.findOne(filter);
	}
	
	public List<VideoTableEntity> findVideosByVector(DataAPIVector vector) {
		return videosRepository.find(null, new TableFindOptions()
				.sort(Sort.vector("video_vector", vector))
				.limit(10))
				.toList();
	}
	
	public List<VideoTableEntity> findVideosByTag(String tag) {
		
		Filter filter = new Filter(Map.of("tags", tag));
		
		TableFindOptions options = new TableFindOptions();
		options.limit(10);
				
		TableFindCursor<VideoTableEntity,VideoTableEntity> results =
				videosRepository.find(filter, options);

		return results.toList();
	}
	
	public List<LatestVideoTableEntity> findLatestVideosByDay(String yyyymmdd) {
		
		Filter filter = new Filter(Map.of("yyyymmdd", yyyymmdd));
		
		TableFindOptions options = new TableFindOptions();
		options.limit(10);
				
		TableFindCursor<LatestVideoTableEntity,LatestVideoTableEntity> results =
				latestVideosRepository.find(filter);

		return results.toList();
	}
	
	public List<LatestVideoTableEntity> findLimit15() {

		TableFindOptions options = new TableFindOptions();
		options.limit(15);
		
		TableFindCursor<LatestVideoTableEntity,LatestVideoTableEntity> results =
				latestVideosRepository.find(null, options);
		
		return results.toList();
	}
	
	public Optional<VideoRatingTableEntity> findVideoRatingsById(UUID videoId) {
		
		Filter filter = new Filter(Map.of("videoid", videoId));
		
		return videoRatingsRepository.findOne(filter);
	}
}
