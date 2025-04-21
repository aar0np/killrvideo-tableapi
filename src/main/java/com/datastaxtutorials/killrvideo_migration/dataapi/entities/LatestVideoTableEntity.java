package com.datastaxtutorials.killrvideo_migration.dataapi.entities;

import java.time.Instant;
import java.util.UUID;

import com.datastax.astra.client.core.query.SortOrder;
import com.datastax.astra.client.tables.mapping.Column;
import com.datastax.astra.client.tables.mapping.EntityTable;
import com.datastax.astra.client.tables.mapping.PartitionBy;
import com.datastax.astra.client.tables.mapping.PartitionSort;

@EntityTable("latest_videos")
public class LatestVideoTableEntity {

	@PartitionBy(0)
	@Column(name="yyyymmdd")
	private String yyyymmdd;

	@PartitionSort(position=1, order=SortOrder.DESCENDING)
	@Column(name="added_date")
	private Instant addedDate;
	
	@PartitionSort(position=2, order=SortOrder.ASCENDING)
	@Column(name="videoid")
	private UUID videoId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="preview_image_location")
	private String previewImageLocation;
	
	@Column(name="userid")
	private UUID userId;
	
	public String getYyyymmmdd() {
		return yyyymmdd;
	}
	
	public void setYyyymmmdd(String yyyymmmdd) {
		this.yyyymmdd = yyyymmmdd;
	}
	
	public Instant getAddedDate() {
		return addedDate;
	}
	
	public void setAddedDate(Instant addedDate) {
		this.addedDate = addedDate;
	}
	
	public UUID getVideoId() {
		return videoId;
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
	
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
}
