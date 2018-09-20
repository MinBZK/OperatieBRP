<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="nl"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="nl"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="nl"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="nl"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title><fmt:message key="application.title"/></title>
	<meta name="description" content="">
	
	<!-- Use the .htaccess and remove these lines to avoid edge case issues. More info: h5bp.com/i/378 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link href="resources/css/html5reset.css" rel="stylesheet" type="text/css" />
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

			var console=console||{"log":function(){}};
            var lastCall = new Date();
            var aantalBerichtenOpHetScherm = 0;
            var maxAantalBerichtenTonen = 5;

			function newMessage(bericht){
					//$('#messages article:last').slideDown( function () { $(this).prependTo($('#messages')).slideUp(); });
					//$(this).prependTo($('#messages'));
					$('#messages article:last').slideUp(function() {bericht.prependTo($('#messages')).slideDown();});
					if(aantalBerichtenOpHetScherm > maxAantalBerichtenTonen) {
						   $('#messages article:last').remove();
					}
			}
				
			
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
                        	maxAantalBerichtenTonen = data.maximumAantalBerichtenPerPagina;
                            for( var i in data.berichten) {
                            	var result = data.berichten[i];
                                $res = createMessage(result);
                                
                                if(result.prevalidatie == true && result.aantalMeldingen == 0) {
                                    continue; //Skip de prevalidatie zonder meldingen
                                }
                               
                                console.log('bericht ' + i);
                                console.log(data.berichten[i]);
                            
                                
                                //$('#messages article:last').slideUp();
                                
                                $res.prependTo($('#messages')).slideDown();
				                 //humanitize timestamps
				                 jQuery("span.timestamp").timeago();
                                
                                aantalBerichtenOpHetScherm++;
                                console.log('berichten op het scherm ' + aantalBerichtenOpHetScherm + ', max tonen ' + maxAantalBerichtenTonen);
                                if(aantalBerichtenOpHetScherm > maxAantalBerichtenTonen) {
                                    $('#messages article:last').remove();
                                }
                            }
                            lastCall = new Date();
                        }
                      //humanitize timestamps
                        jQuery("span.timestamp").timeago();
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

						maxAantalBerichtenTonen = data.maximumAantalBerichtenPerPagina;
						for( var i in data.berichten) {
							if(i > data.maximumAantalBerichtenPerPagina)
								break;
							
							
							var result = data.berichten[i];
	                        $res = createMessage(result);
							
							if(result.prevalidatie == true && result.aantalMeldingen == 0) {
								continue; //Skip de prevalidatie zonder meldingen
							}
							
						   
							console.log('bericht ' + i);
							console.log(data.berichten[i]);
							$res.appendTo($('#messages'));
							aantalBerichtenOpHetScherm++;
							//humanitize timestamps
							jQuery("span.timestamp").timeago();

							if(aantalBerichtenOpHetScherm > maxAantalBerichtenTonen) {
		                           $('#messages article:last').remove();
		                    }
							
						}
						

						
						
						lastCall = new Date();
					 }
				});
			}
				
		    
		    function createMessage(message) {
		    	var articleElem = '<article id="bericht-' + message.berichtId + '"class="bericht" />';
		    	var $res = $(articleElem);                        
                $res.empty();
                
                var timestamp = new Date(message.verzondenOp);
                
                var content='<div class="padding-fix">';
                
                content += '<figure class="berichtIndicatorLinks"><img src="resources/images/' + message.partij + '.jpg" class="stadlogo" title="' + message.partij + '"></figure>';
               
                var messageClasses = 'berichttekst';
                if(message.aantalMeldingen > 0) {
                    messageClasses += ' meldingenAanwezig';
                }

                content +='<div class="'+messageClasses + '">';
                content +='<span class="timestamp" title="' + timestamp.toJSON() + '" >' + timestamp.getDate() + '-' + timestamp.getMonth() + '-' + timestamp.getFullYear() + '&nbsp;' + timestamp.getHours() + ':' + timestamp.getMinutes() + ':' + timestamp.getSeconds() + '</span>';
                content +='<span class="stadnaam">' + message.partij + '</span>&nbsp;via&nbsp;<span class="bzmnaam">' + message.burgerZakenModule + '</span>';
                if(message.prevalidatie == true) {
                    content += '<figure class="prevalidatie"><img src="resources/images/prevalidatie.png" class="prevalidatie" title="prevalidatie"></figure>';
                }
                content +='<p class="message">';
                

                content += message.bericht;
                details = message.berichtDetails.replace(/\n/g, '<br>');
                content += '<br>' + details;
                content += '</p>';
                content +='</div>';
                content += '<figure class="berichtIndicatorRechts"><img src="http://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}/resources/images/' + message.soortBijhouding.toLowerCase() + '.jpg" class="berichttype" title="' + message.soortBijhouding + '"></figure>';

                
                content +='<div class="clear"></div></div>';
                
                $res.append(content);
                
                return $res;
		    }
				 
			getAllMessages(); 
			$('#message-container').show();
			setInterval(function(){ getNewMessages () }, 10000);  
			
			</script>
</head>
<body>
	<header><hgroup><h1><fmt:message key="application.title"/></h1></hgroup></header>
	
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
	<div><p><img src="resources/images/rijksoverheid-logo.jpg" height="50px"/>&copy;2012&nbsp;Versie&nbsp;<fmt:message key="application.version"/></p></div>
	</footer>
</body>
</html>	