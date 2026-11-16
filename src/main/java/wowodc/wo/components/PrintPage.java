package wowodc.wo.components;

import java.util.ArrayList;
import java.util.List;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;
import wowodc.wo.Deck;
import wowodc.wo.Deck.Project;

/**
 * Every slide of the deck, one after another, each on a page of its own when printed. DeckPDF prints it to a PDF.
 */

public class PrintPage extends ERXComponent {

	/**
	 * A slide in the deck: the overview (with no project) or one of a project's slides
	 *
	 * @param number The slide's number in its project, counting from 1
	 */
	public record PrintedSlide( Project project, String componentName, int number ) {

		public String counterName() {
			return project == null ? "Overview" : project.name();
		}

		public String counter() {
			return project == null ? null : number + " / " + project.size();
		}
	}

	public PrintedSlide currentSlide;

	public PrintPage( final WOContext context ) {
		super( context );
	}

	/**
	 * @return Every slide, in the order the talk shows them: the overview, then each project's slides
	 */
	public List<PrintedSlide> slides() {
		final List<PrintedSlide> slides = new ArrayList<>();
		slides.add( new PrintedSlide( null, "OverviewSlide", 1 ) );

		for( final Project project : Deck.PROJECTS ) {
			for( int number = 1; number <= project.size(); number++ ) {
				slides.add( new PrintedSlide( project, project.slide( number ).getSimpleName(), number ) );
			}
		}

		return slides;
	}

	public String deckTitle() {
		return Deck.TITLE;
	}
}
