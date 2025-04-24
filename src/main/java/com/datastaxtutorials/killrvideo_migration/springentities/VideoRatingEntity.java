package com.datastaxtutorials.killrvideo_migration.springentities;

import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.CassandraType.Name;

@Table("video_ratings")
public class VideoRatingEntity {

	@PrimaryKey("videoid")
	private UUID videoid;
	
    @Column("rating_counter")
    @CassandraType(type = Name.COUNTER)
	private long ratingCounter;

    @Column("rating_total")
    @CassandraType(type = Name.COUNTER)
    private long ratingTotal;
	
	public UUID getVideoid() {
		return videoid;
	}
	
	public void setVideoid(UUID videoid) {
		this.videoid = videoid;
	}
	
	public long getRatingCounter() {
		return ratingCounter;
	}
	
	public void setRatingCounter(long ratingCounter) {
		this.ratingCounter = ratingCounter;
	}
	
	public long getRatingTotal() {
		return ratingTotal;
	}

	public void setRatingTotal(long ratingTotal) {
		this.ratingTotal = ratingTotal;
	}
}
