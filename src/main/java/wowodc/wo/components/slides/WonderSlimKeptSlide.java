package wowodc.wo.components.slides;

import com.webobjects.appserver.WOContext;

/**
 * What was kept of Project Wonder, and how much smaller it got.
 *
 * The lines are lines of Java with code on them: no comments, no blank lines, no tests. Before: ERExtensions,
 * JavaWOExtensions and WOOgnl at the fork (e8ce291acc, 2020-09-19). After: ERExtensions/src/main/java (2026-11-05).
 */

public class WonderSlimKeptSlide extends Slide {

	public static final int LINES_BEFORE = 90_788;
	public static final int LINES_AFTER = 17_290;

	public WonderSlimKeptSlide( final WOContext context ) {
		super( context );
	}

	public String linesBefore() {
		return "%,d".formatted( LINES_BEFORE );
	}

	public String linesAfter() {
		return "%,d".formatted( LINES_AFTER );
	}

	public String reduction() {
		return "%d%% less".formatted( Math.round( 100.0 * (LINES_BEFORE - LINES_AFTER) / LINES_BEFORE ) );
	}
}
