package wowodc.wo;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOApplication;
import com.webobjects.appserver.WOResponse;
import com.webobjects.foundation.NSData;

import er.extensions.appserver.ERXApplication;
import er.extensions.resources.ERXPublicResources;
import er.extensions.routing.ERXRouter;
import er.extensions.routing.RouteHandler;
import er.extensions.routing.RouteInvocation;
import parsley.Parsley;
import parsley.ParsleyHTMLElementFactory;
import wowodc.wo.components.PrintPage;
import wowodc.wo.components.SlidePage;
import wowodc.wo.components.demo.TemplateExceptionDemo;

public class Application extends ERXApplication {

	public static void main( String[] args ) {
		ERXApplication.main( args, Application.class );
	}

	public Application() {
		Parsley.configure()
				.elementFactory( "html", new ParsleyHTMLElementFactory() )
				.inlineErrors( true ) // The deck shows them live, so in production too
				.register();

		ERXRouter.declare( routes -> {
			routes.map( "/", SlidePage.class );
			routes.map( "/demo/template-exception", TemplateExceptionDemo.class );
			routes.map( "/print", PrintPage.class );
			routes.map( "/pdf", Application::pdf );
			routes.map( "/{project}/{slide?}", Application::slide );
			routes.fallback( new ERXPublicResources() );
		} );
	}

	/**
	 * The session class, named outright: WO looks it up by its short name, "Session", and in a built bundle that can
	 * find Jetty's org.eclipse.jetty.server.Session first
	 */
	@Override
	protected Class _sessionClass() {
		return Session.class;
	}

	/**
	 * @return The deck as a PDF, to download
	 */
	private static WOActionResults pdf( final RouteInvocation invocation ) {
		final byte[] pdf = DeckPDF.pdf( "http://127.0.0.1:" + WOApplication.application().port() + "/print" );

		final WOResponse response = new WOResponse();
		response.setHeader( "application/pdf", "content-type" );
		response.setHeader( "attachment; filename=\"my-own-wo.pdf\"", "content-disposition" );
		response.setContent( new NSData( pdf ) );
		return response;
	}

	/**
	 * @return A project's slide; declined for a project that doesn't exist, so the URL goes on to the public resources
	 */
	private static WOActionResults slide( final RouteInvocation invocation ) {
		final String project = invocation.parameter( "project" );

		if( Deck.project( project ) == null ) {
			return RouteHandler.DECLINED;
		}

		final SlidePage page = invocation.page( SlidePage.class );
		page.project = project;
		page.slide = invocation.parameter( "slide", Integer.class );
		return page;
	}
}
