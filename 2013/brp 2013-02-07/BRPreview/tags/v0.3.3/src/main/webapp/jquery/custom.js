jQuery(document).ready(function() {
	
	$.getJSON('/BRPreview/mvc/berichten?aantal=9', function(data) {
		var items = [];

		$.each(data, function(key, val) {
			items.push('<li id="' + key + '">' + val.tekst + '</li>');
		});

		$('<ul/>', {
			'class' : 'my-new-list',
			html : items.join('')
		}).appendTo('body');
	});
	
});
