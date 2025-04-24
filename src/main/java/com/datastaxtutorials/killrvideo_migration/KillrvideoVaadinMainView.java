package com.datastaxtutorials.killrvideo_migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import com.datastaxtutorials.killrvideo_migration.customcomponents.VideoComponent;
import com.datastaxtutorials.killrvideo_migration.dataapi.DataAPIServices;
import com.datastaxtutorials.killrvideo_migration.models.LatestVideo;
import com.datastaxtutorials.killrvideo_migration.models.Video;
import com.datastaxtutorials.killrvideo_migration.models.VideoRating;
import com.datastaxtutorials.killrvideo_migration.springrepo.RatingsRepository;
import com.datastaxtutorials.killrvideo_migration.springrepo.VideoRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route("")
public class KillrvideoVaadinMainView extends VerticalLayout {

	private static final long serialVersionUID = -2421822321871704930L;
	
	//private boolean showVideo = false;
	
	private Controller controller;
	private Video currentVideo;
	
	// Components	
	private Image logo = new Image();
	private Paragraph videoDesc = new Paragraph();
	private Paragraph videoRating = new Paragraph();
	private Paragraph videoTitle = new Paragraph();
	private Paragraph overallRating = new Paragraph("overall rating");
	private Paragraph addedOn = new Paragraph();
	private Scroller carousel;
	private Span register = new Span();
	private Span recent = new Span();
	private Span signin = new Span();
	private Span spacer = new Span();
	private Span tour = new Span();
	private Span videos = new Span();
	private Span what = new Span();
	
	private VideoComponent videoComponent;
	private VerticalLayout videoMetaDataLayout;
	
	private TextField searchField = new TextField();
	
	public KillrvideoVaadinMainView (DataAPIServices dataAPIServices,
			RatingsRepository ratingsRepository, VideoRepository videoRepository) {
		
		this.controller = new Controller(dataAPIServices, ratingsRepository, videoRepository);
		
		videoComponent = new VideoComponent();
		videoComponent.setVisible(false);
		videoMetaDataLayout = new VerticalLayout();
		videoMetaDataLayout.setVisible(false);
		
		add(buildSearchBar());
		add(buildVideoViewer());
		add(buildRecentLabel());
		add(buildCarousel());
	}
	
	private Component buildSearchBar() {
		HorizontalLayout layout = new HorizontalLayout();
		
		layout.getStyle().set("background-color", "#000000");
		logo = new Image();
		
		// logo
		try {
			FileInputStream fileStream;
			fileStream = new FileInputStream(new File("static_images/killrvideo.png"));
			StreamResource imageResource = new StreamResource("image",() -> {
				return fileStream;
			});
			logo.setSrc(imageResource);
			logo.setWidth("110px");
			logo.setHeight("24px");
			logo.getStyle().set("margin-top", "10px");
			logo.getStyle().set("margin-left", "10px");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// search text well
		Icon search = new Icon(VaadinIcon.SEARCH);
		searchField.setPrefixComponent(search);
		//searchField.getStyle().set("background", "##A9A9A9");
		//searchField.getStyle().set("background", "##FFFFFF");
		
		tour.setText("  Tour: Off  ");
		tour.getStyle().set("color", "#FFFFFF");
		tour.getStyle().set("margin-top", "10px");
		what.setText("  What is this?  ");
		what.getStyle().set("color", "#FFFFFF");
		what.getStyle().set("margin-top", "10px");
		signin.setText("  SIGN IN  ");
		signin.getStyle().set("color", "#FFFFFF");
		signin.getStyle().set("margin-top", "10px");
		register.setText("  REGISTER  ");
		register.getStyle().set("padding-top", "10px");
		register.getStyle().set("padding-left", "15px");
		register.getStyle().set("color", "#FFFFFF");
		register.getStyle().set("background-color", "#008800");
		register.setHeight("35px");
		register.setWidth("90px");
		spacer.setWidth("80px");

		layout.add(logo,searchField,spacer,tour,what,signin,register);

		return layout;
	}
	
	private Component buildRecentLabel() {
		HorizontalLayout layout = new HorizontalLayout();
				
		recent.setText("RECENT");
		recent.getStyle().set("color", "#FF0000");
		
		videos.setText("VIDEOS");
		
		layout.add(recent,videos);
		
		return layout;
	}
	
	private Component buildVideoViewer() {
		HorizontalLayout layout = new HorizontalLayout();

		videoTitle.setWidth("300px");
		videoTitle.getStyle().set("font-size", "18px");
		videoTitle.getStyle().set("font-weight", "bold");
		videoTitle.getStyle().set("color", "#000000");
		
		videoDesc.setWidth("300px");
		
		videoRating.getStyle().set("font-size", "18px");
		videoRating.getStyle().set("font-weight", "bold");
		videoRating.getStyle().set("color", "#FFB800");
		
		overallRating.getStyle().set("font-size", "10px");
		overallRating.setWidth("50px");
		overallRating.getStyle().set("margin-left", "-12px");
		
		addedOn.getStyle().set("font-size", "8px");
		
		HorizontalLayout ratingLayout = new HorizontalLayout();
		ratingLayout.add(videoRating, overallRating);
		
		videoMetaDataLayout.add(videoTitle, ratingLayout, addedOn, videoDesc);
		videoComponent.getStyle().set("width", "540px");
		layout.add(videoComponent, videoMetaDataLayout);
		
		return layout;
	}
	
	private Component buildCarousel() {
		HorizontalLayout layout = new HorizontalLayout();
		HorizontalLayout scrollLayout = new HorizontalLayout();
		
		List<LatestVideo> latestVideos = controller.getLatest15Videos();
		
		for (LatestVideo video : latestVideos) {
			// preview image
			if (video.getPreviewImageLocation() != null && video.getName() != null) {
				Image preview = new Image();
				VerticalLayout previewLayout = new VerticalLayout();
				
				preview.setSrc(video.getPreviewImageLocation());
				preview.setWidth("200px");
				preview.setHeight("150px");
				
				preview.addClickListener(click -> {
					Optional<Video> videoOpt = controller.getVideoById(video.getVideoId());
					
					if (videoOpt.isPresent()) {
						//showVideo = true;
						currentVideo = videoOpt.get();
						loadVideo();
						videoComponent.setVisible(true);
						videoMetaDataLayout.setVisible(true);
						recent.setText("MORE");
						videos.setText("VIDEOS LIKE THIS");
						addedOn.setText("added on " + currentVideo.getAddedDate().toString());
						reloadCarousel();
					} else {
						//showVideo = false;
						currentVideo = null;
						videoComponent.setVisible(false);
						videoMetaDataLayout.setVisible(false);
					}
					
					//UI.getCurrent().getPage().reload();
				});
				
				// title
				Span title = new Span();
				String name = video.getName();
				
				if (name.length() > 43) {
					name = name.substring(0, 43) + "...";
				}
				
				title.setText(name);
				title.setWidth("200px");
				title.getStyle().set("margin-top", "-20px");
				title.getStyle().set("font-size", "8px");
				title.getStyle().set("color", "#FFFFFF");
				title.getStyle().set("background-color", "#000000");
				
				previewLayout.getStyle().set("margin-right", "-25px");
				previewLayout.getStyle().set("margin-left", "-25px");
				
				previewLayout.add(preview,title);
				scrollLayout.add(previewLayout);
			}
		}
		
		carousel = new Scroller(new Div(scrollLayout));
		carousel.setWidth("680px");
		carousel.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);
		
		layout.add(carousel);
		
		return layout;
	}
	
	private void reloadCarousel() {
		HorizontalLayout scrollLayout = new HorizontalLayout();
		
		List<Video> relatedVideos = controller.getVideosByTags(currentVideo.getTags(),
				currentVideo.getVideoId());
		
		for (Video video : relatedVideos) {
			// preview image
			if (video.getPreviewImageLocation() != null && video.getName() != null) {
				Image preview = new Image();
				VerticalLayout previewLayout = new VerticalLayout();
				
				preview.setSrc(video.getPreviewImageLocation());
				preview.setWidth("200px");
				preview.setHeight("150px");
				
				preview.addClickListener(click -> {
					currentVideo = video;
					loadVideo();
					videoComponent.setVisible(true);
					videoMetaDataLayout.setVisible(true);
					recent.setText("MORE");
					videos.setText("VIDEOS LIKE THIS");
					addedOn.setText("added on " + currentVideo.getAddedDate().toString());
					reloadCarousel();
				});
				
				// title
				Span title = new Span();
				String name = video.getName();
				
				if (name.length() > 43) {
					name = name.substring(0, 43) + "...";
				}
				
				title.setText(name);
				title.setWidth("200px");
				title.getStyle().set("margin-top", "-20px");
				title.getStyle().set("font-size", "8px");
				title.getStyle().set("color", "#FFFFFF");
				title.getStyle().set("background-color", "#000000");
				
				previewLayout.getStyle().set("margin-right", "-25px");
				previewLayout.getStyle().set("margin-left", "-25px");
				
				previewLayout.add(preview,title);
				scrollLayout.add(previewLayout);
			}
		}
		carousel.setContent(new Div(scrollLayout));
	}
	
	// loads the video based on currentVideo and component data
	private void loadVideo() {
		StreamResource videoResource = new StreamResource("video", () -> {
			try {
				return new FileInputStream(new File("movies/" + currentVideo.getLocation()));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		});

		if (videoResource != null) {
			videoComponent.setSource(videoResource);
		}
		
		// rating & share
		Optional<VideoRating> ratings = controller.getVideoRatings(currentVideo.getVideoId());
		
		if (ratings.isPresent()) {
			VideoRating videoRatings = ratings.get();
			long ratingCounter = videoRatings.getRatingCounter();
			long ratingTotal = videoRatings.getRatingTotal();
			
			if (ratingCounter > 0) {
				double averageRating = (double) ratingTotal / ratingCounter;
				videoRating.setText(String.format("%.1f", averageRating));
			}
		} else {
			videoRating.setText("No Ratings Yet");
		}
		
		
		// title
		videoTitle.setText(currentVideo.getName());
				
		// description
		videoDesc.setText(currentVideo.getDescription());
	}
}
