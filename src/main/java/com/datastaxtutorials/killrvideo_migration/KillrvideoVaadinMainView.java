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
	
	//private static final int STAR_WIDTH = 73;
	//private static final int STAR_HEIGHT = 55;
	private static final int STAR_WIDTH = 16;
	private static final int STAR_HEIGHT = 12;
	private static final int STAR_MARGIN_TOP = 15;
	private static final int STAR_MARGIN_LEFT = -15;
	
	// static image resources
	private StreamResource fullStar;
	private StreamResource halfStar;
	private StreamResource emptyStar;
	
	// Components	
	private Image logo = new Image();
	private Image star1 = new Image();
	private Image star2 = new Image();
	private Image star3 = new Image();
	private Image star4 = new Image();
	private Image star5 = new Image();
	
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
		
		// r
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
		videoRating.getStyle().set("color", "#FFD700");
		//videoRating.getStyle().set("border", "1px solid #A9A9A9");
		
		overallRating.getStyle().set("font-size", "10px");
		overallRating.setWidth("50px");
		overallRating.getStyle().set("margin-left", "-12px");
		
		addedOn.getStyle().set("font-size", "8px");		
		
		HorizontalLayout ratingLayout = new HorizontalLayout();
		
		// pre-load star images
		try {
			FileInputStream fileStreamF;
			FileInputStream fileStreamH;
			FileInputStream fileStreamE;
			
			fileStreamF = new FileInputStream(new File("static_images/full_star.png"));
			fileStreamH = new FileInputStream(new File("static_images/half_star.png"));
			fileStreamE = new FileInputStream(new File("static_images/empty_star.png"));
			
			fullStar = new StreamResource("image",() -> {
				return fileStreamF;
			});
			halfStar = new StreamResource("image",() -> {
				return fileStreamH;
			});
			emptyStar = new StreamResource("image",() -> {
				return fileStreamE;
			});
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		star1.setWidth(STAR_WIDTH + "px");
		star1.setHeight(STAR_HEIGHT + "px");
		star1.getStyle().set("margin-top", STAR_MARGIN_TOP + "px");
		star2.setWidth(STAR_WIDTH + "px");
		star2.setHeight(STAR_HEIGHT + "px");
		star2.getStyle().set("margin-top", STAR_MARGIN_TOP + "px");
		star2.getStyle().set("margin-left", STAR_MARGIN_LEFT + "px");
		star3.setWidth(STAR_WIDTH + "px");
		star3.setHeight(STAR_HEIGHT + "px");
		star3.getStyle().set("margin-top", STAR_MARGIN_TOP + "px");
		star3.getStyle().set("margin-left", STAR_MARGIN_LEFT + "px");
		star4.setWidth(STAR_WIDTH + "px");
		star4.setHeight(STAR_HEIGHT + "px");
		star4.getStyle().set("margin-top", STAR_MARGIN_TOP + "px");
		star4.getStyle().set("margin-left", STAR_MARGIN_LEFT + "px");
		star5.setWidth(STAR_WIDTH + "px");
		star5.setHeight(STAR_HEIGHT + "px");
		star5.getStyle().set("margin-top", STAR_MARGIN_TOP + "px");
		star5.getStyle().set("margin-left", STAR_MARGIN_LEFT + "px");

		
		ratingLayout.add(videoRating, overallRating, star1, star2, star3, star4, star5);
		
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
		
		//List<Video> relatedVideos = controller.getVideosByTags(currentVideo.getTags(),
		List<Video> relatedVideos = controller.getVideosByVector(currentVideo.getVideoVector(),
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
				computeStars(averageRating);
			}
		} else {
			videoRating.setText("No Ratings Yet");
			computeStars(0.0);
		}
		
		
		// title
		videoTitle.setText(currentVideo.getName());
				
		// description
		videoDesc.setText(currentVideo.getDescription());
	}
	
	private void computeStars(double rating) {
		int starCounter = 1;
		// compute full stars
		for (; starCounter <= (int)rating; starCounter++) {
			if (starCounter == 1) {
				star1.setSrc(fullStar);
			} else if (starCounter == 2) {
				star2.setSrc(fullStar);
			} else if (starCounter == 3) {
				star3.setSrc(fullStar);
			} else if (starCounter == 4) {
				star4.setSrc(fullStar);
			} else if (starCounter == 5) {
				star5.setSrc(fullStar);
			}
		}
		
		if (starCounter > 5) {
			return;
		}
		
		// compute half stars
		double halfRating = rating - (starCounter - 1);
		
		if (halfRating <= 0.5 && halfRating > 0) {
			if (starCounter == 1) {
				star1.setSrc(halfStar);
			} else if (starCounter == 2) {
				star2.setSrc(halfStar);
			} else if (starCounter == 3) {
				star3.setSrc(halfStar);
			} else if (starCounter == 4) {
				star4.setSrc(halfStar);
			} else if (starCounter == 5) {
				star5.setSrc(halfStar);
			}
			
			starCounter++;
		} else if (halfRating > 0.5) {
			if (starCounter == 1) {
				star1.setSrc(fullStar);
			} else if (starCounter == 2) {
				star2.setSrc(fullStar);
			} else if (starCounter == 3) {
				star3.setSrc(fullStar);
			} else if (starCounter == 4) {
				star4.setSrc(fullStar);
			} else if (starCounter == 5) {
				star5.setSrc(fullStar);
			}

			starCounter++;
		}
		
		// compute empty stars
		for (; starCounter <= 5; starCounter++) {
			if (starCounter == 1) {
				star1.setSrc(emptyStar);
			} else if (starCounter == 2) {
				star2.setSrc(emptyStar);
			} else if (starCounter == 3) {
				star3.setSrc(emptyStar);
			} else if (starCounter == 4) {
				star4.setSrc(emptyStar);
			} else if (starCounter == 5) {
				star5.setSrc(emptyStar);
			}
		}
	}
}
