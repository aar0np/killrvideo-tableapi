package com.datastaxtutorials.killrvideo_migration.springentities;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("videos")
public class VideoEntity {
	@PrimaryKey("videoid")
	@Column("videoid")
	private UUID videoId;
	
	@Column("added_date")
	private Instant addedDate;
	private String description;
	private String location;
	
	@Column("location_type")
	private String locationType;
	private String name;
	
	@Column("preview_image_location")
	private String previewImageLocation;
	
	@Column("solr_query")
	private String solrQuery;
	private Set<String> tags;
	
	@Column("user_id")
	private UUID userId;
	
	public UUID getVideoId() {
		return videoId;
	}
	public void setVideoId(UUID videoId) {
		this.videoId = videoId;
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
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
