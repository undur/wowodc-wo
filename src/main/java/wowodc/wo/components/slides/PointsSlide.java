package wowodc.wo.components.slides;

import java.util.regex.Pattern;

import com.webobjects.appserver.WOContext;

/**
 * The project's points, as a list. A point's further lines are its sub-points, and text in `backticks` is code.
 */

public class PointsSlide extends Slide {

	private static final Pattern CODE = Pattern.compile( "`([^`]+)`" );

	public String currentPoint;

	public PointsSlide( final WOContext context ) {
		super( context );
	}

	/**
	 * @return The point as HTML: its first line, then its sub-points
	 */
	public String currentPointHTML() {
		final String[] lines = currentPoint.split( "\n" );
		final StringBuilder b = new StringBuilder( html( lines[0] ) );

		if( lines.length > 1 ) {
			b.append( "<ul class=\"sub-points\">" );

			for( int i = 1; i < lines.length; i++ ) {
				b.append( "<li>" ).append( html( lines[i] ) ).append( "</li>" );
			}

			b.append( "</ul>" );
		}

		return b.toString();
	}

	/**
	 * @return One line as HTML, `backticked` text as code and hyphenated words kept whole
	 */
	private static String html( final String line ) {
		final StringBuilder b = new StringBuilder();
		final var matcher = CODE.matcher( line );
		int last = 0;

		while( matcher.find() ) {
			b.append( keepingHyphenatedWords( line.substring( last, matcher.start() ) ) );
			b.append( "<code>" ).append( escape( matcher.group( 1 ) ) ).append( "</code>" );
			last = matcher.end();
		}

		b.append( keepingHyphenatedWords( line.substring( last ) ) );
		return b.toString();
	}

	private static String escape( final String text ) {
		return text.replace( "&", "&amp;" ).replace( "<", "&lt;" ).replace( ">", "&gt;" ).replace( "\"", "&quot;" );
	}
}
