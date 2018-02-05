$(function() {
	var c,
		$table = $('#sort_table'),
		mode = true, // tablesorter enabled
		filterRow = false;

	$('.toggle').click(function(){
		mode = !mode;
		$(this).text( mode ? 'Disable' : 'Enable' );
		$table.trigger( mode ? 'enable' : 'disable' );
		// if using the pager:
		// $table.trigger( ( mode ? 'enable' : 'disable' ) + 'Pager' );
	});

	$('.filterRow').click(function(){
		filterRow = !filterRow;
		$table[0].config.widgetOptions.toggleTS_hideFilterRow = filterRow;
		$(this).text( '' + filterRow );
	});

	$table.tablesorter({
		theme: 'blue',
		widthFixed : true,
		widgets: ['zebra', 'filter', 'toggle-ts'],
		widgetOptions : {
			filter_reset: '.reset',
			filter_external: '.search',
			filter_columnFilters : true,
			toggleTS_hideFilterRow : filterRow
		}
	});

});

/*! tablesorter enable/disable sort & filter (BETA) - 11/7/2015 (v2.24.4)
 * Requires jQuery 1.7+
 * by Rob Garrison
 */
;( function( $ ) {
	'use strict';
	var ts = $.tablesorter,

	tst = ts.toggleTS = {

		init : function( c, wo ) {
			wo.toggleTS_enabled = true; // enabled
			wo.toggleTS_areDisabled = {
				headers : [],
				filters : []
			};
			c.$table.on('enable.toggleTS disable.toggleTS', function( event ) {
				tst.toggle( this.config, this.config.widgetOptions, event.type === 'enable' );
			});
		},
		toggle : function( c, wo, enable ) {
			if ( wo.toggleTS_enabled !== enable ) {
				wo.toggleTS_enabled = enable;
				var indx, $el,
					len = c.$headers.length;

				// table headers
				for ( indx = 0; indx < len; indx++ ) {
					$el = c.$headers.eq( indx );
					if ( enable && !wo.toggleTS_areDisabled.headers[ indx ] ) {
						$el
							.removeClass( 'sorter-false' )
							.prop( 'sortDisabled', false )
							.attr( 'aria-disabled', false );
						if ( c.tabIndex ) {
							$el.attr( 'tabindex', 0 );
						}
					} else if ( !enable ) {
						if ( $el.hasClass( 'sorter-false' ) ) {
							wo.toggleTS_areDisabled.headers[ indx ] = true;
						}
						$el
							.addClass( 'sorter-false' )
							.prop( 'sortDisabled', true )
							.attr( 'aria-disabled', true )
							.removeAttr( 'tabindex' );
					}
				}
				if ( wo.toggleTS_hideFilterRow ) {
					c.$table.find( '.' + ts.css.filterRow ).toggle( enable );
				} else if ( ts.hasWidget( c.$table, 'filter' ) ) {
					// c.$filters points to filter CELL
					len = c.$filters.length;
					for ( indx = 0; indx < len; indx++ ) {
						if ( enable && !wo.toggleTS_areDisabled.filters[ indx ] ) {
							c.$filters.eq( indx ).find( 'input, select' )
								.removeClass( ts.css.filterDisabled )
								.prop( 'disabled', false );
						} else if ( !enable ) {
							$el = c.$filters.eq( indx ).find( 'input, select' );
							if ( $el.hasClass( ts.css.filterDisabled ) ) {
								wo.toggleTS_areDisabled.filters[ indx ] = true;
							}
							$el
								.addClass( ts.css.filterDisabled )
								.prop( 'disabled', true );
						}
					}
				}
				// include external filters
				wo.filter_$externalFilters
					.toggleClass( ts.css.filterDisabled, enable )
					.prop( 'disabled', !enable );
			}
		}
	};

	ts.addWidget({
		id: 'toggle-ts',
		options: {
			toggleTS_hideFilterRow : false
		},
		init : function( table, thisWidget, c, wo ) {
			tst.init( c, wo );
		},
		remove : function( table, c ) {
			c.$table.off( 'enable.toggleTS disable.toggleTS' );
		}
	});

})( jQuery );