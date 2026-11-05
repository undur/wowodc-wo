package wowodc.wo.components.slides;

import java.util.List;

import com.webobjects.appserver.WOContext;

import wowodc.wo.Deck;
import wowodc.wo.Deck.Project;

/**
 * The start: every project the talk covers, by group, each a link to its first slide
 */

public class OverviewSlide extends Slide {

	public String currentGroup;
	public Project currentProject;

	public OverviewSlide( final WOContext context ) {
		super( context );
	}

	public String deckTitle() {
		return Deck.TITLE;
	}

	public String whyURL() {
		return Deck.WHY.url();
	}

	public List<String> groups() {
		return Deck.GROUPS;
	}

	public List<Project> projectsInCurrentGroup() {
		return Deck.projectsInGroup( currentGroup );
	}
}
