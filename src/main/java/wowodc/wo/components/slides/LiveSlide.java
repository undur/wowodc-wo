package wowodc.wo.components.slides;

import java.lang.management.ManagementFactory;
import java.time.Duration;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WOContext;

/**
 * This deck, live: the JVM it runs in, refreshed by AjaxSlim, and a counter whose updates morph into the page
 */

public class LiveSlide extends Slide {

	public int count;

	public LiveSlide( final WOContext context ) {
		super( context );
	}

	public WOActionResults increment() {
		count++;
		return null;
	}

	public String javaVersion() {
		return String.valueOf( Runtime.version().feature() );
	}

	public String uptime() {
		final Duration uptime = Duration.ofMillis( ManagementFactory.getRuntimeMXBean().getUptime() );
		return "%d:%02d:%02d".formatted( uptime.toHours(), uptime.toMinutesPart(), uptime.toSecondsPart() );
	}

	public String heapUsed() {
		final Runtime runtime = Runtime.getRuntime();
		return "%d MB".formatted( (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) );
	}

	public int threads() {
		return ManagementFactory.getThreadMXBean().getThreadCount();
	}
}
