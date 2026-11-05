package wowodc.wo.components;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;
import wowodc.wo.Deck;
import wowodc.wo.Deck.Project;

/**
 * The page every slide is shown on: the 16:9 stage, the footer, and the URLs the keyboard navigates to.
 * Without a project it shows the overview.
 */

public class SlidePage extends ERXComponent {

	/**
	 * The slug of the project shown, from the URL; null on the overview
	 */
	public String project;

	/**
	 * The number of the slide shown in the project, counting from 1, from the URL; null for the first
	 */
	public Integer slide;

	public SlidePage( final WOContext context ) {
		super( context );
	}

	/**
	 * @return The project shown, null on the overview (or for a project that doesn't exist)
	 */
	public Project currentProject() {
		return project == null ? null : Deck.project( project );
	}

	public int number() {
		return slide == null ? 1 : slide;
	}

	public boolean isOverview() {
		return currentProject() == null || currentProject().slide( number() ) == null;
	}

	public String slideComponentName() {
		return isOverview() ? "OverviewSlide" : currentProject().slide( number() ).getSimpleName();
	}

	public String title() {
		return isOverview() ? Deck.TITLE : currentProject().name() + " · " + Deck.TITLE;
	}

	public String deckTitle() {
		return Deck.TITLE;
	}

	public String overviewURL() {
		return Deck.overviewURL();
	}

	/**
	 * @return The previous slide's URL: the previous project's last slide before a project's first; null on the overview
	 */
	public String previousURL() {
		if( isOverview() ) {
			return null;
		}

		if( number() > 1 ) {
			return Deck.url( currentProject(), number() - 1 );
		}

		final Project previous = Deck.previous( currentProject() );
		return previous != null ? Deck.url( previous, previous.size() ) : Deck.overviewURL();
	}

	/**
	 * @return The next slide's URL: the next project's first after a project's last; from the overview, the first project
	 */
	public String nextURL() {
		if( isOverview() ) {
			return Deck.PROJECTS.getFirst().url();
		}

		if( number() < currentProject().size() ) {
			return Deck.url( currentProject(), number() + 1 );
		}

		final Project next = Deck.next( currentProject() );
		return next != null ? next.url() : null;
	}

	/**
	 * @return The name the counter shows: the project's, or the overview's
	 */
	public String counterName() {
		return isOverview() ? "Overview" : currentProject().name();
	}

	/**
	 * @return Where the counter says we are in the project, null on the overview
	 */
	public String counter() {
		return isOverview() ? null : number() + " / " + currentProject().size();
	}

	/**
	 * @return The progress bar's width, as a style: how far through the projects the talk is
	 */
	public String progressStyle() {
		if( isOverview() ) {
			return "width: 0";
		}

		final double done = Deck.PROJECTS.indexOf( currentProject() ) + (double)number() / currentProject().size();
		return "width: %.2f%%".formatted( 100.0 * done / Deck.PROJECTS.size() );
	}
}
