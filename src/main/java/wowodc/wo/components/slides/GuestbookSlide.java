package wowodc.wo.components.slides;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

/**
 * A plain guestbook, for showing parslips-skill at work live: an agent is asked to change it while the audience watches
 */

public class GuestbookSlide extends Slide {

	/**
	 * A signature in the guestbook
	 */
	public record Entry( String name, String message, LocalTime time ) {

		public String timeString() {
			return time.format( DateTimeFormatter.ofPattern( "HH:mm" ) );
		}
	}

	/**
	 * The signatures, newest first. Kept by the application, so they survive leaving the slide and coming back.
	 */
	private static final List<Entry> ENTRIES = new CopyOnWriteArrayList<>( List.of( new Entry( "Hugi", "Welcome to the guestbook!", LocalTime.now() ) ) );

	/**
	 * The signer's name. Not 'name', which is WOComponent's own name() and wins over a field.
	 */
	public String signer;
	public String message;
	public Entry currentEntry;

	public GuestbookSlide( final WOContext context ) {
		super( context );
	}

	public List<Entry> entries() {
		return ENTRIES;
	}

	/**
	 * Adds a signature, if there's a message, and clears the form
	 */
	public WOActionResults sign() {
		if( message != null && !message.isBlank() ) {
			final String who = signer == null || signer.isBlank() ? "Anonymous" : signer.strip();
			ENTRIES.addFirst( new Entry( who, message.strip(), LocalTime.now() ) );
		}

		signer = null;
		message = null;
		return null;
	}
}
