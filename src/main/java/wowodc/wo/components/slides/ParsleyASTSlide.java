package wowodc.wo.components.slides;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import com.webobjects.appserver.WOContext;

import ng.appserver.templating.parser.NGDeclaration.NGBindingValue;
import ng.appserver.templating.parser.NGTemplateParser;
import ng.appserver.templating.parser.model.PBasicNode;
import ng.appserver.templating.parser.model.PCommentNode;
import ng.appserver.templating.parser.model.PHTMLNode;
import ng.appserver.templating.parser.model.PNode;
import ng.appserver.templating.parser.model.PRawNode;
import ng.appserver.templating.parser.model.PRootNode;

/**
 * This slide's own template, and the AST Parsley's parser makes of it, parsed again as the slide renders
 */

public class ParsleyASTSlide extends Slide {

	/**
	 * How much of an HTML node's text the tree shows
	 */
	private static final int TEXT_LENGTH = 34;

	public ParsleyASTSlide( final WOContext context ) {
		super( context );
	}

	/**
	 * @return The source of this component's template
	 */
	public String templateSource() {
		try {
			final Path folder = Path.of( pathURL().toURI() );
			return Files.readString( folder.resolve( name().substring( name().lastIndexOf( '.' ) + 1 ) + ".html" ), StandardCharsets.UTF_8 );
		}
		catch( final IOException e ) {
			throw new UncheckedIOException( e );
		}
		catch( final Exception e ) {
			throw new IllegalStateException( "Couldn't read the template of " + name(), e );
		}
	}

	/**
	 * @return The template's AST, as an indented tree
	 */
	public String ast() {
		try {
			final StringBuilder b = new StringBuilder();
			append( b, new NGTemplateParser( templateSource(), "" ).parse(), "", "" );
			return b.toString();
		}
		catch( final Exception e ) {
			throw new IllegalStateException( "Couldn't parse the template of " + name(), e );
		}
	}

	private static void append( final StringBuilder b, final PNode node, final String prefix, final String childPrefix ) {
		final String description = describe( node );

		// Whitespace between elements is a node too, but showing it says nothing
		if( description == null ) {
			return;
		}

		b.append( prefix ).append( description ).append( '\n' );

		final java.util.List<PNode> children = switch( node ) {
			case PRootNode root -> root.children();
			case PBasicNode basic -> basic.children();
			default -> java.util.List.of();
		};

		final java.util.List<PNode> shown = children.stream().filter( child -> describe( child ) != null ).toList();

		for( int i = 0; i < shown.size(); i++ ) {
			final boolean last = i == shown.size() - 1;
			append( b, shown.get( i ), childPrefix + (last ? "└─ " : "├─ "), childPrefix + (last ? "   " : "│  ") );
		}
	}

	/**
	 * @return One line describing the node, null for whitespace
	 */
	private static String describe( final PNode node ) {
		return switch( node ) {
			case PRootNode root -> "PRootNode";
			case PBasicNode basic -> "PBasicNode  <%s:%s>%s".formatted( basic.namespace(), basic.type(), bindings( basic.bindings() ) );
			case PHTMLNode html -> text( "PHTMLNode", html.value() );
			case PRawNode raw -> text( "PRawNode", raw.value() );
			case PCommentNode comment -> text( "PCommentNode", comment.value() );
			default -> node.getClass().getSimpleName();
		};
	}

	private static String bindings( final Map<String, NGBindingValue> bindings ) {
		if( bindings.isEmpty() ) {
			return "";
		}

		return "  " + bindings.entrySet().stream().map( e -> e.getKey() + "=" + value( e.getValue() ) ).collect( Collectors.joining( " " ) );
	}

	private static String value( final NGBindingValue value ) {
		return switch( value ) {
			case NGBindingValue.Value v -> v.isQuoted() ? "\"" + v.value() + "\"" : v.value();
			case NGBindingValue.BooleanPresence p -> "(present)";
			default -> value.toString();
		};
	}

	private static String text( final String type, final String value ) {
		final String collapsed = value.replaceAll( "\\s+", " " ).strip();

		if( collapsed.isEmpty() ) {
			return null;
		}

		return "%s  \"%s\"".formatted( type, collapsed.length() > TEXT_LENGTH ? collapsed.substring( 0, TEXT_LENGTH ) + "…" : collapsed );
	}
}
