package com.datastaxtutorials.killrvideo_migration.models;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class Video {

	private UUID videoId;
	private String name;
	private String previewImageLocation;
	private UUID userId;
	private Instant addedDate;
	private String description;
	private String location;
	private String locationType;
	private String solrQuery;
	private Set<String> tags;
	
	public UUID getVideoId() {
		return this.videoId;
	}
	
	public void setVideoId(UUID videoId) {
		this.videoId = videoId;
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
	
	public void setUserId(UUID userid) {
		this.userId = userid;
	}
	
	public Instant getAddedDate() {
		return addedDate;
	}
	
	public void setAddedDate(Instant addedDate) {
		this.addedDate = addedDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(String solrQuery) {
		this.solrQuery = solrQuery;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
}

