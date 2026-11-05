package wowodc.wo.components.demo;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

/**
 * A page with a binding that throws while rendering, to show where Parsley says the exception happened
 */

public class TemplateExceptionDemo extends ERXComponent {

	public TemplateExceptionDemo( final WOContext context ) {
		super( context );
	}

	public String badgeStyle() {
		throw new IllegalStateException( "The badge has no colour" );
	}
}
