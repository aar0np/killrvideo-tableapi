package com.datastaxtutorials.killrvideo_migration.customcomponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("video")
public class VideoComponent extends Component {

	private static final long serialVersionUID = -4641545071664047559L;

	public VideoComponent() {
		getElement().setAttribute("autoplay", "false");
        getElement().setAttribute("loop", "true");
        getElement().setAttribute("controls", "true");
    }	
	
	public void setSource(StreamResource source) {
		getElement().setAttribute("src", source);
	}
	
    public void setSource(String resource) {
        getElement().setAttribute("src", resource);
    }
}
