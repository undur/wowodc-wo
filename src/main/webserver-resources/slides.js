// Keys between slides (arrows, space, page up/down for a clicker), escape or o for the overview, and t for the light/dark theme.
// A mouse works as a clicker too: a click on a slide goes forward, a right click back.
// Loaded in the head, so the remembered theme applies before the slide is drawn.
(() => {
	const root = document.documentElement;
	const THEME_KEY = "wowodc-wo.theme";

	const storedTheme = () => {
		try {
			return localStorage.getItem( THEME_KEY );
		}
		catch( e ) {
			return null;
		}
	};

	const storeTheme = theme => {
		try {
			localStorage.setItem( THEME_KEY, theme );
		}
		catch( e ) {
			// The theme just isn't remembered
		}
	};

	if( storedTheme() ) {
		root.dataset.theme = storedTheme();
	}

	document.addEventListener( "keydown", event => {

		// Leave shortcuts and typing in a demo alone
		if( event.metaKey || event.ctrlKey || event.altKey || event.target.closest( "input, textarea, select, [contenteditable]" ) ) {
			return;
		}

		const stage = document.querySelector( ".stage" );

		const go = url => {
			if( url ) {
				event.preventDefault();
				location.href = url;
			}
		};

		switch( event.key ) {
			case "ArrowRight":
			case "ArrowDown":
			case "PageDown":
				go( stage.dataset.next );
				break;
			case "ArrowLeft":
			case "ArrowUp":
			case "PageUp":
				go( stage.dataset.previous );
				break;
			case " ":
				go( event.shiftKey ? stage.dataset.previous : stage.dataset.next );
				break;
			case "Escape":
			case "o":
			case "Home":
				go( stage.dataset.overview );
				break;
			case "t":
				root.dataset.theme = root.dataset.theme === "light" ? "dark" : "light";
				storeTheme( root.dataset.theme );
				break;
		}
	} );

	// What a click on the slide leaves alone: links, buttons, fields, and the live demos
	const INTERACTIVE = "a, button, input, textarea, select, label, summary, [contenteditable], form, [id][data-updateUrl], .live-panel, .invoice, .demo";

	const isOnSlideBackground = event => {
		const stage = document.querySelector( ".stage" );

		if( !stage || !stage.contains( event.target ) || event.target.closest( INTERACTIVE ) ) {
			return false;
		}

		// Selecting text to point at it isn't a click to move on
		return !String( window.getSelection() ).length;
	};

	document.addEventListener( "click", event => {
		if( event.button === 0 && !event.metaKey && !event.ctrlKey && !event.shiftKey && !event.altKey && isOnSlideBackground( event ) ) {
			const next = document.querySelector( ".stage" ).dataset.next;

			if( next ) {
				location.href = next;
			}
		}
	} );

	document.addEventListener( "contextmenu", event => {
		if( isOnSlideBackground( event ) ) {
			event.preventDefault();
			const previous = document.querySelector( ".stage" ).dataset.previous;

			if( previous ) {
				location.href = previous;
			}
		}
	} );
})();
