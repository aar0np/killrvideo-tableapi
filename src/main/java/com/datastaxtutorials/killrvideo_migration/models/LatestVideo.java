package com.datastaxtutorials.killrvideo_migration.models;

import java.time.Instant;
import java.util.UUID;

public class LatestVideo {

	private String yyyymmdd;
	private Instant addedDate;
	private UUID videoId;
	private String name;
    private String previewImageLocation;
    private UUID userId;
    
    
    public String getYyyymmdd() {
		return yyyymmdd;
	}

	public void setYyyymmdd(String yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}

	public UUID getVideoId() {
		return videoId;
	}

	public void setVideoId(UUID videoId) {
		this.videoId = videoId;
	}

	public Instant getAddedDate() {
		return this.addedDate;
	}
    
    public void setAddedDate(Instant addedDate) {
		this.addedDate = addedDate;
	}
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPreviewImageLocation() {
		return previewImageLocation;
	}
	
	public void setPreviewImageLocation(String previewImageLocation) {
		this.previewImageLocation = previewImageLocation;
	}
	
	public UUID getUserId() {
		return userId;
	}
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
