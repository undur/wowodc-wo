package wowodc.wo.components.slides;

import java.util.ArrayList;
import java.util.List;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

/**
 * The invoice editor from AjaxPlayground, cut down to its lines and total: one observe field watches the form, and every
 * change re-renders the lines around the field being edited, which keeps its focus.
 */

public class InvoiceSlide extends Slide {

	public InvoiceLine currentLine;

	private final List<InvoiceLine> _lines = new ArrayList<>();
	private int _nextLineID = 1;

	public InvoiceSlide( final WOContext context ) {
		super( context );
		_lines.add( new InvoiceLine( _nextLineID++, "Widget", 2, 500 ) );
		_lines.add( new InvoiceLine( _nextLineID++, "Gadget", 1, 1200 ) );
		_lines.add( new InvoiceLine( _nextLineID++, "Sprocket", 4, 75 ) );
	}

	public List<InvoiceLine> lines() {
		return _lines;
	}

	public String quantityFieldID() {
		return "qty-" + currentLine.id();
	}

	public String priceFieldID() {
		return "price-" + currentLine.id();
	}

	public int total() {
		return _lines.stream().mapToInt( InvoiceLine::lineTotal ).sum();
	}

	public WOActionResults addLine() {
		_lines.add( new InvoiceLine( _nextLineID++, "Cog", 1, 100 ) );
		return null;
	}

	public WOActionResults removeLine() {
		_lines.remove( currentLine );
		return null;
	}
}
