package wowodc.wo.components.slides;

import com.webobjects.appserver.WOContext;

/**
 * A project's opening slide: its name, what it is, when it started and what it replaces
 */

public class ProjectTitleSlide extends Slide {

	public ProjectTitleSlide( final WOContext context ) {
		super( context );
	}

	/**
	 * @return The project's name as HTML: free to wrap at its spaces, never at a hyphen
	 */
	public String nameHTML() {
		return keepingHyphenatedWords( project().name() );
	}
}
