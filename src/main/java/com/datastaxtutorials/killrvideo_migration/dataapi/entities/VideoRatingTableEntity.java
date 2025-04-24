package com.datastaxtutorials.killrvideo_migration.dataapi.entities;

import java.util.UUID;

import com.datastax.astra.client.tables.mapping.Column;
import com.datastax.astra.client.tables.mapping.EntityTable;
import com.datastax.astra.client.tables.mapping.PartitionBy;

@EntityTable("video_ratings")
public class VideoRatingTableEntity {

	@PartitionBy(0)
	@Column(name="videoid")
	private UUID videoid;
	@Column(name="rating_counter")
	private long ratingCounter;
	@Column(name="rating_total")
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
