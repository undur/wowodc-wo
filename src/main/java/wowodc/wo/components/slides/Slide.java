package wowodc.wo.components.slides;

import java.util.regex.Pattern;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXNonSynchronizingComponent;
import wowodc.wo.Deck.Project;

/**
 * A slide: a component holding only its content. SlidePage frames it, and hands it the project it belongs to.
 */

public abstract class Slide extends ERXNonSynchronizingComponent {

	/**
	 * A word with a hyphen in it, such as a project's name
	 */
	private static final Pattern HYPHENATED = Pattern.compile( "[\\w.]+(?:-[\\w.]+)+" );

	public Slide( final WOContext context ) {
		super( context );
	}

	/**
	 * @return The project the slide belongs to, null on the overview
	 */
	public Project project() {
		return (Project)valueForBinding( "project" );
	}

	/**
	 * @return The text as HTML, its hyphenated words kept whole ("ng-objects" doesn't break at its hyphen)
	 */
	protected static String keepingHyphenatedWords( final String text ) {
		final String escaped = text.replace( "&", "&amp;" ).replace( "<", "&lt;" ).replace( ">", "&gt;" );
		return HYPHENATED.matcher( escaped ).replaceAll( "<span class=\"name\">$0</span>" );
	}
}
