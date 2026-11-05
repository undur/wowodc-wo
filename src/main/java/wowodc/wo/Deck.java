package wowodc.wo;

import java.util.ArrayList;
import java.util.List;

import wowodc.wo.components.slides.AdaptorHandlerSlide;
import wowodc.wo.components.slides.AdaptorThroughputSlide;
import wowodc.wo.components.slides.AdaptorWaitingSlide;
import wowodc.wo.components.slides.AdminSlide;
import wowodc.wo.components.slides.ClosingSlide;
import wowodc.wo.components.slides.ConfigurationSlide;
import wowodc.wo.components.slides.ApiextSlide;
import wowodc.wo.components.slides.AjaxSlimDemoSlide;
import wowodc.wo.components.slides.InvoiceSlide;
import wowodc.wo.components.slides.LiveSlide;
import wowodc.wo.components.slides.LoggingSlide;
import wowodc.wo.components.slides.ModuloFrontendDiagramSlide;
import wowodc.wo.components.slides.ModuloFutureSlide;
import wowodc.wo.components.slides.ModuloOriginSlide;
import wowodc.wo.components.slides.ModuloProxyDiagramSlide;
import wowodc.wo.components.slides.ParslipsChangesSlide;
import wowodc.wo.components.slides.ParslipsClosingSlide;
import wowodc.wo.components.slides.ParsleyASTSlide;
import wowodc.wo.components.slides.ParsleyAliasesSlide;
import wowodc.wo.components.slides.ParsleyErrorsSlide;
import wowodc.wo.components.slides.ParsleyInlineErrorSlide;
import wowodc.wo.components.slides.ParsleyInlineSlide;
import wowodc.wo.components.slides.ParsleyLocationSlide;
import wowodc.wo.components.slides.ParsleyNamespaceSlide;
import wowodc.wo.components.slides.ParsleyNegationSlide;
import wowodc.wo.components.slides.ParsleyOgnlSlide;
import wowodc.wo.components.slides.ParsleySyntaxSlide;
import wowodc.wo.components.slides.PointsSlide;
import wowodc.wo.components.slides.ProjectTitleSlide;
import wowodc.wo.components.slides.RecapSlide;
import wowodc.wo.components.slides.ResourcesSlide;
import wowodc.wo.components.slides.RoutingSlide;
import wowodc.wo.components.slides.ShortURLsSlide;
import wowodc.wo.components.slides.Slide;
import wowodc.wo.components.slides.StandaloneSlide;
import wowodc.wo.components.slides.VermilinguaGoalsSlide;
import wowodc.wo.components.slides.VermilinguaLayoutSlide;
import wowodc.wo.components.slides.VermilinguaPropertiesSlide;
import wowodc.wo.components.slides.WhyBenderSlide;
import wowodc.wo.components.slides.WhyDesignSlide;
import wowodc.wo.components.slides.WhyOriginSlide;
import wowodc.wo.components.slides.WhyPathSlide;
import wowodc.wo.components.slides.WhyStatementSlide;
import wowodc.wo.components.slides.WonderSlimCleanupSlide;
import wowodc.wo.components.slides.WonderSlimKeptSlide;

/**
 * The talk: the projects it covers, in the order they're presented, and each project's slides.
 * The overview (at /) shows every project; the arrow keys walk through all of them in order.
 */

public class Deck {

	/**
	 * Shown in each slide's footer and the browser's title bar
	 */
	public static final String TITLE = "My Own WO";

	/**
	 * A project's opening slide (its name, tagline and what it replaced) followed by its points
	 */
	private static final List<Class<? extends Slide>> STANDARD = List.of( ProjectTitleSlide.class, PointsSlide.class );

	/**
	 * The groups the overview arranges the projects in, in order
	 */
	public static final List<String> GROUPS = List.of( "Framework", "Build & tools", "Runtime & deployment" );

	/**
	 * The introduction: why any of this exists. First in the talk, but not one of the overview's projects (the overview links to it on its own).
	 */
	public static final Project WHY = new Project( "why", "Why", "Introduction", "2020", "Getting my own applications off custom WO code", null, List.of(),
			List.of( WhyOriginSlide.class, WhyBenderSlide.class, WhyStatementSlide.class, WhyPathSlide.class, WhyDesignSlide.class ) );

	/**
	 * The end of the talk. Last in it, but not one of the overview's projects.
	 */
	public static final Project END = new Project( "follow", "whoacommunity.com", "Closing", "2025", "Where to follow it all", null, List.of(),
			List.of( ClosingSlide.class ) );

	/**
	 * Picking up where the first talk stopped: a recap, and what works without wonder-slim. Not one of the overview's projects.
	 */
	public static final Project RECAP = new Project( "so-far", "So far", "Recap", "2026", "Where we got to, and what needs wonder-slim", null, List.of(),
			List.of( RecapSlide.class, StandaloneSlide.class ) );

	public static final List<Project> PROJECTS = List.of(
			WHY,
			new Project( "wonder-slim", "wonder-slim", "Framework", "2020", "Project Wonder slimmed to my own requirements", null, List.of(),
					List.of( ProjectTitleSlide.class, WonderSlimKeptSlide.class, WonderSlimCleanupSlide.class, ShortURLsSlide.class, RoutingSlide.class, ResourcesSlide.class, ConfigurationSlide.class, LoggingSlide.class, AdminSlide.class ) ),
			new Project( "ajaxslim", "AjaxSlim", "Framework", "2022", "Simplified Ajax, and a bridge to ng-objects' Ajax", "Wonder's Ajax framework", List.of(
					"Updates morph into the page: focus, scroll and state survive\nUsing Idiomorph, developed by mr. htmx himself",
					"One unified page cache for component actions and Ajax actions\nNo separate \"page fragment cache\"",
					"Several containers updated in one request\n`updateContainerID=\"cont1;cont2;etc\"`",
					"Errors shown, never swallowed" ),
					List.of( ProjectTitleSlide.class, PointsSlide.class, LiveSlide.class, InvoiceSlide.class, AjaxSlimDemoSlide.class ) ),
			new Project( "parsley", "Parsley", "Framework", "2024", "The template parser, with inline error display", "WOOgnl's parser", List.of(),
					List.of( ProjectTitleSlide.class, ParsleyInlineSlide.class, ParsleyASTSlide.class, ParsleyNamespaceSlide.class, ParsleySyntaxSlide.class, ParsleyErrorsSlide.class, ParsleyInlineErrorSlide.class, ParsleyLocationSlide.class, ParsleyAliasesSlide.class, ParsleyNegationSlide.class, ParsleyOgnlSlide.class ) ),
			new Project( "vermilingua", "vermilingua", "Build & tools", "2021", "Builds and deploys WO and ng apps", "woproject and wolifecycle-maven-plugin", List.of(
					"Builds any runnable Java application, not just WebObjects and ng-objects",
					"More or less a drop-in replacement for wolifecycle-maven-plugin: swap the <plugin> element",
					"Faster builds that are easy to change",
					"\"Correct\" classpath order: the application first, then Maven's order",
					"Simplified launch script: no NEXT_ROOT",
					"Project layout declared in build.properties (if you use something like Fluffy Bunny)" ),
					List.of( ProjectTitleSlide.class, PointsSlide.class, VermilinguaLayoutSlide.class, VermilinguaPropertiesSlide.class, VermilinguaGoalsSlide.class ) ),
			RECAP,
			new Project( "adaptor", "wo-adaptor-jetty", "Runtime & deployment", "2025", "Gives you control over what your application serves", "WO's HTTP adaptor", List.of(
					"Used in all of my projects for a year, since November 2025",
					"WebSockets and server push, experimental",
					"WO and ng-objects in one server" ),
					List.of( ProjectTitleSlide.class, PointsSlide.class, AdaptorHandlerSlide.class, AdaptorThroughputSlide.class, AdaptorWaitingSlide.class ) ),
			new Project( "modulo", "modulo", "Runtime & deployment", "2025", "A front-end server in Java", "Apache, mod_WebObjects and certbot", List.of(
					"Many sites on one server, TLS by SNI",
					"Let's Encrypt certificates, built in",
					"HTTP/2, HTTP/3 and WebSockets",
					"Session affinity and failover across instances",
					"One TOML file, configured from wotaskd like the adaptor was" ),
					List.of( ProjectTitleSlide.class, ModuloOriginSlide.class, ModuloProxyDiagramSlide.class, ModuloFrontendDiagramSlide.class, PointsSlide.class, ModuloFutureSlide.class ) ),
			new Project( "javamonitor", "JavaMonitor & wotaskd", "Runtime & deployment", "2020", "The deployment tools, cleaned up", "Apple's deployment tools", List.of(
					"Work in progress",
					"Rebuilt on wonder-slim, Java's HTTP client and the Jetty adaptor",
					"Deploy endpoint: push a build to every host",
					"The protocol documented",
					"Rewritten to remove WOXMLCoding",
					"Nicer UI" ), STANDARD ),
			new Project( "parslips", "Parslips", "Build & tools", "2022", "The template editor, and a dev server agents can drive", "WOLips", List.of(
					"Entirely vibe coded",
					"Never meant to be an Eclipse plugin: I wanted the template editing out, for an LSP plugin for IntelliJ and VS Code",
					"Everything related to EOF removed",
					"Validation, quick fixes and renames that reach the templates",
					"An improved dev server: launch, refresh, validate, inspect",
					"parslips-skill: an AI agent works the whole loop" ),
					List.of( ProjectTitleSlide.class, PointsSlide.class, ParslipsChangesSlide.class, ParslipsClosingSlide.class ) ),
			new Project( "apiext", ".apiext", "Build & tools", "2026", "Element APIs that say what bindings mean", "WO's .api files", List.of(
					"Binding types and direction: what goes in, what comes out",
					"Typed constraints instead of <validation>",
					"Defaults, deprecations and docs",
					"Drives Parslips' editor and whoacommunity's element reference" ),
					List.of( ProjectTitleSlide.class, PointsSlide.class, ApiextSlide.class ) ),
			END );

	/**
	 * @return The project with the given slug, null if there's none
	 */
	public static Project project( final String slug ) {
		for( final Project project : PROJECTS ) {
			if( project.slug().equals( slug ) ) {
				return project;
			}
		}

		return null;
	}

	/**
	 * @return The projects in the given group, in the deck's order
	 */
	public static List<Project> projectsInGroup( final String group ) {
		final List<Project> result = new ArrayList<>();

		for( final Project project : PROJECTS ) {
			if( project.group().equals( group ) ) {
				result.add( project );
			}
		}

		return result;
	}

	/**
	 * @return The project presented after the given one, null after the last
	 */
	public static Project next( final Project project ) {
		final int index = PROJECTS.indexOf( project );
		return index < PROJECTS.size() - 1 ? PROJECTS.get( index + 1 ) : null;
	}

	/**
	 * @return The project presented before the given one, null before the first
	 */
	public static Project previous( final Project project ) {
		final int index = PROJECTS.indexOf( project );
		return index > 0 ? PROJECTS.get( index - 1 ) : null;
	}

	public static String overviewURL() {
		return "/";
	}

	/**
	 * @param number The slide's number in its project, counting from 1
	 */
	public static String url( final Project project, final int number ) {
		return number == 1 ? "/" + project.slug() : "/" + project.slug() + "/" + number;
	}

	/**
	 * A project the talk covers
	 *
	 * @param slug Its URL
	 * @param group The overview group it's shown in
	 * @param since The year it started
	 * @param tagline One line saying what it is
	 * @param replaces What it replaces, null if nothing
	 * @param points What to say about it, shown on PointsSlide
	 * @param slides Its slides, in order
	 */
	public record Project( String slug, String name, String group, String since, String tagline, String replaces, List<String> points, List<Class<? extends Slide>> slides ) {

		public String url() {
			return Deck.url( this, 1 );
		}

		public int size() {
			return slides.size();
		}

		/**
		 * @return The slide with the given number, counting from 1; null if there's no such slide
		 */
		public Class<? extends Slide> slide( final int number ) {
			if( number < 1 || number > slides.size() ) {
				return null;
			}

			return slides.get( number - 1 );
		}
	}
}
