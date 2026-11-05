package wowodc.wo.components.slides;

import com.webobjects.appserver.WOContext;

/**
 * A live inline error: the template asks a user for a key it doesn't have
 */

public class ParsleyInlineErrorSlide extends Slide {

	public record User( String username, String email ) {}

	public final User user = new User( "hugi", "hugi@example.com" );

	public ParsleyInlineErrorSlide( final WOContext context ) {
		super( context );
	}
}
