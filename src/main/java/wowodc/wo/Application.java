package wowodc.wo;

import com.webobjects.appserver.WOActionResults;

import er.extensions.appserver.ERXApplication;
import er.extensions.resources.ERXPublicResources;
import er.extensions.routing.ERXRouter;
import er.extensions.routing.RouteHandler;
import er.extensions.routing.RouteInvocation;
import parsley.Parsley;
import parsley.ParsleyHTMLElementFactory;
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
