package com.datastaxtutorials.killrvideo_migration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import com.datastaxtutorials.killrvideo_migration.dataapi.DataAPIServices;
import com.datastaxtutorials.killrvideo_migration.models.LatestVideo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
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
	
	private Controller controller;
	
	private Scroller carousel;
	
	private Image logo = new Image();
	private Span register = new Span();
	private Span recent = new Span();
	private Span signin = new Span();
	private Span spacer = new Span();
	private Span tour = new Span();
	private Span videos = new Span();
	private Span what = new Span();
	
	private TextField searchField = new TextField();
	
	public KillrvideoVaadinMainView (DataAPIServices dataAPIServices) {
		this.controller = new Controller(dataAPIServices);
		add(buildSearchBar());
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
		searchField.getStyle().set("background", "##A9A9A9");
		
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
}
