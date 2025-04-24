package com.datastaxtutorials.killrvideo_migration.models;

import java.util.UUID;

public class VideoRating {
	
	private UUID videoid;
	private long ratingCounter;
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
