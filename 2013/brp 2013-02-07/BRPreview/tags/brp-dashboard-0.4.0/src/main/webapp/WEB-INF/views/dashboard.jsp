<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="nl"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="nl"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="nl"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="nl"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>Delta Dashboard</title>
	<meta name="description" content="">
	
	<!-- Use the .htaccess and remove these lines to avoid edge case issues. More info: h5bp.com/i/378 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link href="resources/css/htm5reset.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/dashboard.css" rel="stylesheet" type="text/css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="resources/js/libs/jquery-1.7.2.min.js"><\/script>')</script>
	<!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
	<script src="resources/js/libs/modernizr-2.5.3.min.js"></script>
	<script src="resources/js/libs/jquery.timeago.js" type="text/javascript"></script>
	<script>
	
			// Dutch settings for timestring
			jQuery.timeago.settings.strings = {
			   suffixAgo: "geleden",
			   suffixFromNow: "vanaf nu",
			   seconds: "iets minder dan een minuut",
			   minute: "ongeveer een minuut",
			   minutes: "%d minuten",
			   hour: "ongeveer een uur",
			   hours: "ongeveer %d uur",
			   day: "een dag",
			   days: "%d dagen",
			   month: "ongeveer een maand",
			   months: "%d maanden",
			   year: "ongeveer een jaar",
			   years: "%d jaar"
			};


			function newMessage(){
					//$('#messages article:last').slideDown( function () { $(this).prependTo($('#messages')).slideUp(); });
					//$(this).prependTo($('#messages'));
					$('#messages article:last').slideUp(function() {$(this).prependTo($('#messages')).slideDown();});
			}
				

			var console=console||{"log":function(){}};
		    var lastCall = new Date();
			
		    function getNewMessages() {
		    	
		    	var urlMessagesSince;
		    	urlMessagesSince = 'http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/service/berichten/' + lastCall.getTime();
		    	 $.ajax ({
                     url: urlMessagesSince,
                     dataType: 'json',
                     timeout: 10000,
                     success: function(data) {
                        if (!data.berichten){
                            console.log('Geen berichten in JSON resultaat gevonden');
                            return false;
                        }
                        console.log('Berichten opvragen sinds ' + lastCall.getTime());
                        console.log('Aantal berichten:' + data.aantalBerichten);
                        if(data.aantalBerichten > 0) {
                            lastCall = new Date();
                            newMessage ();
                        }
                     }
	                });
	            }
	                 
		    
		    function getAllMessages() {
				 $.ajax ({
					 url: 'http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/service/berichten',
					 dataType: 'json',
					 timeout: 10000,
					 success: function(data) {
						if (!data.berichten){
							console.log('Geen berichten in JSON resultaat gevonden');
							return false;
						}


						for( var i in data.berichten) {
							if(i > data.aantalPerPagina)
								break;
							
							var result = data.berichten[i];
	                        var $res = $("<article class='bericht' />");                        
	                        $res.empty();
							
							var timestamp = new Date(result.verzondenOp);
							var content='<div class="padding-fix">';
							
                            content += '<figure class="berichtIndicatorLinks"><img src="resources/images/' + result.partij + '.jpg" class="stadlogo" title="' + result.partij + '"></figure>';
                           
							var messageClasses = 'berichttekst';
                            if(result.aantalMeldingen > 0) {
                                messageClasses += ' meldingenAanwezig';
                            }

							content +='<div class="'+messageClasses + '">';
							content +='<span class="timestamp" title="' + timestamp.toJSON() + '" >' + timestamp.toISOString() + '</span>';
							content +='<span class="stadnaam">' + result.partij + '</span>&nbsp;via&nbsp;<span class="bzmnaam">' + result.burgerZakenModule + '</span>';
							if(result.prevalidatie == true) {
                                content += '<figure class="prevalidatie"><img src="resources/images/prevalidatie.png" class="prevalidatie" title="prevalidatie"></figure>';
                            }
							content +='<p class="message">';
                            

							content += result.bericht;
							content += '<br>' + result.berichtDetails;
							content += '</p>';
							content +='</div>';
							content += '<figure style="berichtIndicatorRechts"><img src="http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/resources/images/' + result.soortBijhouding.toLowerCase() + '.jpg" class="berichttype" title="' + result.soortBijhouding + '"></figure>';

							
							content +='<div class="clear"></div></div>';
							
							if(result.prevalidatie == true && result.aantalMeldingen == 0) {
								continue; //Skip de prevalidatie zonder meldingen
							}
							
						    $res.append(content);
							console.log('bericht ' + i);
							console.log(data.berichten[i]);
							$res.appendTo($('#messages'));
							
							//humanitize timestamps
							jQuery("span.timestamp").timeago();
							//newMessage ();
						}
						

						
						
						lastCall = new Date();
					 }
				});
			}
				
		    
		    function showMessage(message) {
		    	
		    }
				 
			getAllMessages(); 
			$('#message-container').show();
			setInterval(function(){ getNewMessages () }, 5000);  
			
			</script>
</head>
<body>
	<header><hgroup><h1>BRPreview Dashboard</h1></hgroup></header>
	
	<div role="main">
		<section id="message-container">
			
				<div id="messages" class="notification-area">
						
				</div>
			
		</section>
	
		<!-- aside class="statistics-area">		
			<table><tr><th>Gemeente</th><th>Geboortes</th><th>Verhuizingen</th></tr>
			<tr><td><img src="resources/images/Amsterdam.jpg" class="stadlogo-mini"></td><td>12</td><td>0</td></tr>
            <tr><td><img src="resources/images/Arnhem.jpg" class="stadlogo-mini"></td><td>5</td><td>4</td></tr>
            <tr><td><img src="resources/images/Utrecht.jpg" class="stadlogo-mini"></td><td>3</td><td>7</td></tr>
            <tr><td><img src="resources/images/'s-Gravenhage.jpg" class="stadlogo-mini"></td><td>1</td><td>10</td></tr>
            <tr><td><img src="resources/images/Groningen.jpg" class="stadlogo-mini"></td><td>14</td><td>20</td></tr>
            <tr><td><img src="resources/images/Haarlem.jpg" class="stadlogo-mini"></td><td>0</td><td>0</td></tr>
            </table>
			
			</div>
			
			
		</aside-->
	</div>
	<footer>
	<div><p><img src="resources/images/Logo-Min-BZK.jpg" height="50px"/>&copy;2012</p></div>
	</footer>
</body>
</html>	