google.load("visualization", "1", {
	packages : [ "corechart", "gauge" ]
});

LineChart = function(divId, options) {
	this.keepNumberOfDataSeq = 6;
	this.data = new google.visualization.DataTable();
	this.chart;

	if (options.hAxis == null) {
		var hAxis = {
			slantedText : true,
			// Per hoeveel waarden dient een tekstlabel getoond te worden?
			showTextEvery : 1
		}
		options.hAxis = hAxis;
	}
	
	if (options.chartArea == null) {
		var chartArea = {
				left : 50
		}
		
		options.chartArea = chartArea;
	}

	this.addColumn = function(type, label) {
		this.data.addColumn(type, label);
	}

	this.draw = function() {
		google.setOnLoadCallback(this._drawChart(this));
	}

	this._drawChart = function(targetObject) {
		targetObject.chart = new google.visualization.LineChart(document
				.getElementById(divId));
		targetObject.chart.draw(this.data, options);
	}

	this.updateChart = function(jsonData) {
		if (this.data.getNumberOfRows() > this.keepNumberOfDataSeq
				* jsonData.length) {
			this.data.removeRows(0, jsonData.length);
		}
		var data = this.data;
		$.each(jsonData, function(i, item) {
			data.addRow(item.c);
		});

		this.chart.draw(data, options);
	}
}

var ColumnChart = function(divId, options) {
	this.data = new google.visualization.DataTable();
	this.chart;

	if (options.chartArea == null) {
		var chartArea = {
				left : 50
		}
		
		options.chartArea = chartArea;
	}

	
	// Voeg standaard 1 rij toe
	this.data.addRows(1);

	this.addColumn = function(type, label) {
		this.data.addColumn(type, label);
	}

	this.draw = function() {
		google.setOnLoadCallback(this._drawChart(this));
	}

	this._drawChart = function(targetObject) {
		targetObject.chart = new google.visualization.ColumnChart(document
				.getElementById(divId));

		targetObject.data.addRows(2);

		targetObject.chart.draw(targetObject.data, options);
	}

	this.updateChart = function(jsonData) {
		this.data.removeRows(0, jsonData.length);
		
		var data = this.data;
		$.each(jsonData, function(i, item) {
			data.addRow(item.c);
		});
		this.chart.draw(data, options);
	}

}

var Gauge = function(divId, options) {
	this.data = new google.visualization.DataTable();
	this.chart;

	// Voeg standaard 1 rij toe
	this.data.addRows(1);

	this.addColumn = function(type, label) {
		this.data.addColumn(type, label);
		this.data.setCell(0, this.data.getNumberOfColumns() - 1, 0);
	}

	this.draw = function() {
		google.setOnLoadCallback(this._drawChart(this));
	}

	this._drawChart = function(targetObject) {
		targetObject.chart = new google.visualization.Gauge(document.getElementById(divId));

		targetObject.data.addRows(2);

		targetObject.chart.draw(targetObject.data, options);
	}

	this.updateGauge = function(waarde) {
		this.data.setCell(0, 0, waarde);
		this.chart.draw(this.data, options);
	}
}
