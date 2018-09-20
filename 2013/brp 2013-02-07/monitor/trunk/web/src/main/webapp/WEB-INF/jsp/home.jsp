<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.flipCounter.1.2.pack.js" />"></script>
<script type="text/javascript">
	var doUpdate = true;

	$(document).ready(function() {
		$("#button_stop").click(function() {
			if ($(this).attr("value") == "Stop") {
				doUpdate = false;
				$(this).attr("value", "Start");
			} else {
				doUpdate = true;
				$(this).attr("value", "Stop");
			}
		});
		
		$("#button_reset").click(function() {
			$.ajax({
				url: 'reset.html',
				success: function(data) {
					if (data == true) {
						alert('Reset is uitgevoerd');	
					} else {
						alert('Reset is mislukt');
					}
			  	}
			});
		});
	});
</script>

<div class="grid_16 alpha omega">

	<div id="controllePaneel">
		<div class="grid_5 alpha">
			<h2>Systeem Dashboard</h2>
		</div>

		<div class="grid_1 prefix_9">
			<input type="button" value="Stop" id="button_stop" />

		</div>
		<div class="grid_1 omega">
			<input type="button" value="Reset" id="button_reset" />
		</div>
	</div>

	<div class="grid_16 alpha omega">
		<hr />
	</div>

	<div id="diagrammen" class="grid_16 alpha omega">
	
		<div class="grid_4 alpha" style="height: 100px">
            <div class="formSectie">
                <p>Totaal aantal berichten</p>
                <hr />
                <div id="counter">
                    <input type="hidden" name="counter-value" value="0" />
                </div>
                <script type="text/javascript">
                    $(document).ready(
                        function($) {
                            $("#counter").flipCounter(
                            {
                                numIntegralDigits : 10, // number of places left of the decimal point to maintain
                                numFractionalDigits : 0, // number of places right of the decimal point to maintain
    
                                counterFieldName : "counter-value", // name of the hidden field
                                digitHeight : 40, // the height of each digit in the flipCounter-medium.png sprite image
                                digitWidth : 30, // the width of each digit in the flipCounter-medium.png sprite image
                                imagePath : "<c:url value='/resources/images/flipCounter-medium.png' />", // the path to the sprite image relative to your html document
                            })
                    });
                </script>
            </div>
        </div>
		
		<div class="grid_4">
			<div class="formSectie">
				<p>Gemiddelde berichtenverwerking per sec (over laatste 10 sec)</p>
				<hr />
				<div id="gauge_berichten">
					<script type="text/javascript">
						var gaugeBerichtenOptions = {
							min : 0,
							max : 20,
							minorTicks : 5
						};

						var berichtenGauge = new Gauge("gauge_berichten",
								gaugeBerichtenOptions);
						berichtenGauge.addColumn("number", "");
						berichtenGauge.draw();
					</script>
				</div>
			</div>
		</div>

		<div class="grid_4 omega">
			<div class="formSectie">
				<p>Gemiddelde responsetijd van bericht</p>
				<hr />
				<div id="gauge_responsetijd">
					<script type="text/javascript">
						var gaugeResponseTijdOptions = {
							min : 0,
							max : 2000,
							minorTicks : 5
						};

						var responseTijdGauge = new Gauge("gauge_responsetijd",
								gaugeResponseTijdOptions);
						responseTijdGauge.addColumn("number", "");
						responseTijdGauge.draw();
					</script>
				</div>
			</div>
		</div>
				
		<div class="clear">&nbsp;</div>
		
		<div class="grid_4 alpha" style="height: 100px">
            <div class="formSectie">
                
            </div>
        </div>
		
		<div class="grid_4">
            <div class="formSectie">
                <p>Aantal berichten verwerkt per sec</p>
                <hr />
                <div id="chart_berichten">
                    <script type="text/javascript">
                        var optionsBerichtenAanvraag = {
                            width : 475,
                            height : 300,
                            lineWidth : 2,
                            vAxis : {
                                viewWindow : {
                                    min : 0
                                }
                            },
                            legend : {
                                position : 'none'
                            }
                        };

                        var berichtenAanvraag = new LineChart(
                                "chart_berichten", optionsBerichtenAanvraag);
                        berichtenAanvraag.addColumn("string", "Tijdseenheden");
                        berichtenAanvraag.addColumn("number",
                                "Aantal verzoeken in verwerking");
                        berichtenAanvraag.draw();
                    </script>
                </div>
            </div>
        </div>
		<div class="grid_4">
			<div class="formSectie">
				<p>Gemiddelde responsetijd van bericht</p>
				<hr />
				<div id="chart_response">
					<script type="text/javascript">
						var optionsResponseTijden = {
							width : 475,
							height : 300,
							lineWidth : 2,
							vAxis : {
								viewWindow : {
									min : 0
								}
							},
							legend : {
								position : 'top'
							}
						};

						var responseTijden = new LineChart("chart_response",
								optionsResponseTijden);
						responseTijden.addColumn("string", "Tijdseenheden");
						//responseTijden.addColumn("number","Response tijd servlet");
						responseTijden.addColumn("number", "Response tijd cxf");
						//responseTijden.addColumn("number", "Actueel");
						responseTijden.draw();
					</script>
				</div>
			</div>
		</div>
		
		<!-- <div class="grid_4">
			<div class="formSectie">
				<p>Actieve database connecties</p>
				<hr />
				<div id="chart_db">
					<script type="text/javascript">
						var optionsActieveDbConnecties = {
							width : 500,
							height : 300,
							lineWidth : 1,
							vAxis : {
								viewWindow : {
									min : 0
								}
							},
							legend : {
								position : 'none'
							}
						};

						var dbConnecties = new LineChart("chart_db",
								optionsActieveDbConnecties);
						dbConnecties.addColumn("string", "Tijdseenheden");
						dbConnecties.addColumn("number",
								"Aantal actieve Database connecties");
						dbConnecties.draw();
					</script>
				</div>
			</div>
		</div>
		<div class="grid_4">
			<div class="formSectie">
				<p>Geheugen gebruik</p>
				<hr />
				<div id="chart_geheugen">
					<script type="text/javascript">
						var optionsGeheugenGebruik = {
							width : 500,
							height : 300,
							lineWidth : 1,
							vAxis : {
								viewWindow : {
									min : 0
								},
								format : "# Mb"
							},
							legend : {
								position : 'top'
							}
						};

						var geheugenGebruik = new LineChart("chart_geheugen",
								optionsGeheugenGebruik);
						geheugenGebruik.addColumn("string", "Tijdseenheden");
						geheugenGebruik.addColumn("number", "Heap memory");
						geheugenGebruik.addColumn("number", "Non Heap memory");
						geheugenGebruik.draw();
					</script>
				</div>
			</div>
		</div>
		<div class="grid_4">
			<div class="formSectie">
				<p>Soort opgetreden fouten</p>
				<hr />
				<div id="chart_fouten">
					<script type="text/javascript">
						var options = {
							width : 500,
							height : 300,
							vAxis : {
								viewWindow : {
									min : 0
								}
							},
							legend : {
								position : 'none'
							}
						};

						var berichtenFouten = new ColumnChart("chart_fouten", options);
						berichtenFouten.addColumn("string", "type");
						berichtenFouten.addColumn("number", "Aantal");
						berichtenFouten.draw();
					</script>
				</div>
			</div> 
		</div> -->
	</div>
</div>

<script type="text/javascript">
                      setInterval(function() {
                            if (doUpdate) {
                                $.getJSON("updatesysteem.json?interval=1000", 
                                function(jsonData) {
                                        berichtenGauge.updateGauge(jsonData.aantalBerichtenPerSeconden);
                                        berichtenAanvraag.updateChart(jsonData.berichtenVerkeer);
                                        responseTijdGauge.updateGauge(jsonData.responseTijdLaatsteBericht);
                                        responseTijden.updateChart(jsonData.responseTijden);
                                        //dbConnecties.updateChart(jsonData.dbConnecties);
                                        //geheugenGebruik.updateChart(jsonData.geheugenGebruik);
                                        //berichtenFouten.updateChart(jsonData.fouten);
                                        $("#counter").flipCounter("startAnimation",
                                        {
                                            end_number : jsonData.aantalUitgaandeBerichten, // the number we want the counter to scroll to
                                            duration : 500 // number of ms animation should take to complete,
                                        });

                                });
                            }
                        }, 1000);
                        </script>