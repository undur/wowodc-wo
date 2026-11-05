package wowodc.wo.components.slides;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

/**
 * Two containers updated by one link in one request, and an action that fails, to show the error message
 */

public class AjaxSlimDemoSlide extends Slide {

	public int count;
	public final List<String> history = new ArrayList<>();
	public String currentEntry;

	public AjaxSlimDemoSlide( final WOContext context ) {
		super( context );
	}

	public WOActionResults addOne() {
		count++;
		history.addFirst( "%s  %d".formatted( LocalTime.now().format( DateTimeFormatter.ofPattern( "HH:mm:ss" ) ), count ) );

		if( history.size() > 4 ) {
			history.removeLast();
		}

		return null;
	}

	public WOActionResults fail() {
		throw new IllegalStateException( "Something went wrong on the server" );
	}
}
