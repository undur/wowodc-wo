package wowodc.wo;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.WaitUntilState;

import er.extensions.appserver.ERXApplication;

/**
 * The deck as a PDF: a headless Chromium (driven by Playwright) loads the print page from this application and prints
 * it, so the PDF looks exactly like the slides. Playwright downloads its Chromium the first time it's used.
 *
 * Chromium's requests for the print page reach this application while the request that asked for the PDF is still
 * being handled, so the application handles requests concurrently (WOAllowsConcurrentRequestHandling, in Properties).
 */

public class DeckPDF {

	/**
	 * The PDF made last, made again on every request in development, so it shows the slides as they are
	 */
	private static byte[] _cached;

	/**
	 * @param printURL The print page's URL, as Chromium on this machine reaches it
	 */
	public static synchronized byte[] pdf( final String printURL ) {
		if( _cached == null || ERXApplication.isDevelopmentModeSafe() ) {
			_cached = print( printURL );
		}

		return _cached;
	}

	private static byte[] print( final String printURL ) {
		try( Playwright playwright = Playwright.create(); Browser browser = playwright.chromium().launch() ) {
			final Page page = browser.newPage( new Browser.NewPageOptions().setViewportSize( 1600, 900 ) );
			page.navigate( printURL, new Page.NavigateOptions().setWaitUntil( WaitUntilState.NETWORKIDLE ) );

			// The fonts, loaded before printing, so the text isn't set in a fallback
			page.evaluate( "() => document.fonts.ready.then( () => true )" );

			return page.pdf( new Page.PdfOptions().setPrintBackground( true ).setPreferCSSPageSize( true ) );
		}
	}
}
