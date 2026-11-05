package wowodc.wo.components.slides;

import com.webobjects.appserver.WOContext;

/**
 * WO as one handler in a chain of Jetty handlers, as wong-hybrid wires it
 */

public class AdaptorHandlerSlide extends Slide {

	public AdaptorHandlerSlide( final WOContext context ) {
		super( context );
	}
}
