package com.datastaxtutorials.killrvideo_migration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datastaxtutorials.killrvideo_migration.dataapi.DataAPIServices;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.LatestVideoTableEntity;
import com.datastaxtutorials.killrvideo_migration.dataapi.entities.VideoTableEntity;
import com.datastaxtutorials.killrvideo_migration.models.LatestVideo;
import com.datastaxtutorials.killrvideo_migration.models.Video;
import com.datastaxtutorials.killrvideo_migration.models.VideoRating;
import com.datastaxtutorials.killrvideo_migration.springentities.VideoEntity;
import com.datastaxtutorials.killrvideo_migration.springentities.VideoRatingEntity;
import com.datastaxtutorials.killrvideo_migration.springrepo.RatingsRepository;
import com.datastaxtutorials.killrvideo_migration.springrepo.VideoRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/killrvideo")
public class Controller {

	private DataAPIServices dataAPIServices;
	private RatingsRepository ratingsRepository;
	private VideoRepository videoRepository;
	
	public Controller(DataAPIServices dataAPIServices, RatingsRepository ratingsRepository, VideoRepository videoRepository) {
		this.dataAPIServices = dataAPIServices;
		this.ratingsRepository = ratingsRepository;
		this.videoRepository = videoRepository;
	}
	
	@GetMapping("/video/{videoid}")
	public ResponseEntity<Video> getVideo(HttpServletRequest req, 
            @PathVariable(value = "videoid")
            UUID videoId) {
		
		Optional<Video> video = getVideoById(videoId);
		
		if (video.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(video.get());
	}
	
	@GetMapping("/latestvideos")
	public ResponseEntity<List<LatestVideo>> getLatestVideos(HttpServletRequest req) {
		
		List<LatestVideo> latestVideos = getLatest15Videos();
		
		if (latestVideos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(latestVideos);
	}
	
	@GetMapping("latestvideos/{yyyymmdd}")
	public ResponseEntity<List<LatestVideo>> getLatestVideosByDay(HttpServletRequest req, 
			@PathVariable(value = "yyyymmdd")
			String yyyymmdd) {
		
		List<LatestVideo> latestVideos = getLatestVideos(yyyymmdd);
		
		if (latestVideos.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(latestVideos);
	}
	
	public Optional<Video> getVideoById(UUID videoId) {
		
		Optional<VideoTableEntity> returnVal =  dataAPIServices.findVideoById(videoId);
		
		return returnVal.map(this::mapVideo);
	}
	
	public List<Video> getVideosByTags(Set<String> tags, UUID videoid) {

		List<Video> returnVal = new ArrayList<>();
		List<UUID> videoIds = new ArrayList<>();
		videoIds.add(videoid);
		
		for (String tag : tags) {
			List<VideoEntity> videoEntities = videoRepository.findByTagsContaining(tag);
			
			for (VideoEntity videoEntity : videoEntities) {
				if (!videoIds.contains(videoEntity.getVideoId())) {
					returnVal.add(mapVideo(videoEntity));
					videoIds.add(videoEntity.getVideoId());
				}
			}
		}
		
		return returnVal;
	}
	
	public Optional<VideoRating> getVideoRatings(UUID videoId) {
		
		Optional<VideoRatingEntity> returnVal = ratingsRepository.findById(videoId);
		
		if (returnVal.isEmpty()) {
			return Optional.empty();
		}
				
		return Optional.of(mapVideoRating(returnVal.get()));
	}
	
	public List<LatestVideo> getLatest15Videos() {
		
		List<LatestVideo> returnVal = mapLatestVideos(dataAPIServices.findLimit15());

		return returnVal;
	}
	
	public List<LatestVideo> getLatestVideos(String yyyymmdd) {
		
		List<LatestVideo> returnVal = mapLatestVideos(dataAPIServices.findLatestVideosByDay(yyyymmdd));

		return returnVal;
	}
	
	private Video mapVideo(VideoEntity videoEntity) {
		
		Video video = new Video();
		
		video.setVideoId(videoEntity.getVideoId());
		video.setAddedDate(videoEntity.getAddedDate());
		video.setDescription(videoEntity.getDescription());
		video.setLocation(videoEntity.getLocation());
		video.setLocationType(videoEntity.getLocationType());
		video.setName(videoEntity.getName());
		video.setPreviewImageLocation(videoEntity.getPreviewImageLocation());
		video.setSolrQuery(videoEntity.getSolrQuery());
		video.setTags(videoEntity.getTags());
		video.setUserId(videoEntity.getUserId());
		
		return video;
	}
	
	private Video mapVideo(VideoTableEntity videoEntity) {
		
		Video video = new Video();
		
		video.setVideoId(videoEntity.getVideoId());
		video.setAddedDate(videoEntity.getAddedDate());
		video.setDescription(videoEntity.getDescription());
		video.setLocation(videoEntity.getLocation());
		video.setLocationType(videoEntity.getLocationType());
		video.setName(videoEntity.getName());
		video.setPreviewImageLocation(videoEntity.getPreviewImageLocation());
		video.setSolrQuery(videoEntity.getSolrQuery());
		video.setTags(videoEntity.getTags());
		video.setUserId(videoEntity.getUserId());
		
		return video;
	}
	
	private List<LatestVideo> mapLatestVideos(List<LatestVideoTableEntity> latestVideoEntities) {
		
		List<LatestVideo> returnVal = new ArrayList<>();

		for (LatestVideoTableEntity videoEntity : latestVideoEntities) {
			LatestVideo latestVideo = new LatestVideo();

			latestVideo.setVideoId(videoEntity.getVideoId());
			latestVideo.setAddedDate(videoEntity.getAddedDate());
			latestVideo.setYyyymmdd(videoEntity.getYyyymmmdd());
			latestVideo.setName(videoEntity.getName());
			latestVideo.setPreviewImageLocation(videoEntity.getPreviewImageLocation());
			latestVideo.setUserId(videoEntity.getUserId());
			
			returnVal.add(latestVideo);
		}
		
		return returnVal;
	}
	
	private LatestVideo mapLatestVideo(LatestVideoTableEntity latestVideoEntity) {
		
		LatestVideo latestVideo = new LatestVideo();

		latestVideo.setVideoId(latestVideoEntity.getVideoId());
		latestVideo.setAddedDate(latestVideoEntity.getAddedDate());
		latestVideo.setYyyymmdd(latestVideoEntity.getYyyymmmdd());
		latestVideo.setName(latestVideoEntity.getName());
		latestVideo.setPreviewImageLocation(latestVideoEntity.getPreviewImageLocation());
		latestVideo.setUserId(latestVideoEntity.getUserId());
		
		return latestVideo;
	}
	
	private VideoRating mapVideoRating(VideoRatingEntity videoRatingEntity) {
		
		VideoRating videoRating = new VideoRating();
		
		videoRating.setVideoid(videoRatingEntity.getVideoid());
		videoRating.setRatingCounter(videoRatingEntity.getRatingCounter());
		videoRating.setRatingTotal(videoRatingEntity.getRatingTotal());
		
		return videoRating;
	}
}
