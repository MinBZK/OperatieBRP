<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="nl"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="nl"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="nl"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="nl"> <!--<![endif]-->

<c:choose>
    <c:when test="${pageContext.request.secure}==true">
        <c:set var="baseUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}" />
    </c:when>
    <c:otherwise>
        <c:set var="baseUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.localPort}" />
    </c:otherwise>
</c:choose>
<c:set var="contextRoot" value="${baseUrl}${pageContext.request.contextPath}" />
<c:choose>
    <c:when test="${partij!=null && !empty partij}">
        <c:set var="berichtenUrl" value="${baseUrl}${properties.berichten_context}/service/berichten/partij/${partij}" />
    </c:when>
    <c:otherwise>
        <c:set var="berichtenUrl" value="${baseUrl}${properties.berichten_context}/service/berichten" />
    </c:otherwise>
</c:choose>
<head>
	<meta charset="utf-8" />
	<title><fmt:message key="application.title"/></title>
	<meta name="description" content="">
	
	<!-- Use the .htaccess and remove these lines to avoid edge case issues. More info: h5bp.com/i/378 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link href="${contextRoot}/resources/css/html5reset.css" rel="stylesheet" type="text/css" />
    <link href="${contextRoot}/resources/css/brp-generiek.css" rel="stylesheet" type="text/css" />
	<link href="${contextRoot}/resources/css/dashboard.css" rel="stylesheet" type="text/css" />
	<script src="${contextRoot}/resources/js/libs/jquery-1.8.0.min.js"></script>
	<!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
	<script src="${contextRoot}/resources/js/libs/modernizr-2.5.3.min.js"></script>
	<script src="${contextRoot}/resources/js/libs/jquery.timeago.js" type="text/javascript"></script>
    <script src="${contextRoot}/resources/js/libs/jquery.timeago.nl.js" type="text/javascript"></script>
    <script src="${contextRoot}/resources/js/libs/jquery.autoellipsis-1.0.10.min.js" type="text/javascript"></script>

	<script>
        //Page fadein
        $(document).ready(function() {
            $("body").css("display", "none");
            $("body").fadeIn(2000);
        });

        var console=console||{"log":function(){}};
        var lastCall = new Date();
        var aantalBerichtenOpHetScherm = 0;
        var maxAantalBerichtenTonen = 7; //default, wordt overschreven door de waarde zoals die door de berichten wordt doorgegeven
        var maxAantalNieuweBerichten = 25;
        var berichten = null;
        var updateRunning = false;
   
        function getNewMessages() {
            var urlMessagesSince;

            urlMessagesSince = "${berichtenUrl}/" + lastCall.getTime();
            console.log("URL berichten sinds: " + urlMessagesSince);
            if(!updateRunning) {
                var newMessages = $.ajax ({
                     url: urlMessagesSince,
                     dataType: 'json',
                     timeout: 10000,
                     error : function(xhr, status) {
                            console.log('Er is een fout opgetreden bij het opvragen van nieuwe berichten. ' + status);
                        },
                     success: function(data) {
                        if (!data.berichten){
                            console.log('Geen berichten in JSON resultaat gevonden');
                            return false;
                        }
                        console.log('Berichten opvragen sinds ' + lastCall.getHours() + ':' + lastCall.getMinutes() + ':' +lastCall.getSeconds() +', parameter:' +lastCall.getTime());
                        maxAantalBerichtenTonen = data.maximumAantalBerichtenPerPagina;
                        console.log('Aantal berichten tonen op pagina maximaal:' + data.maximumAantalBerichtenPerPagina);
                        if(data.aantalBerichten > 0)
                        {
                            berichten = data.berichten.reverse();
                            console.log('Aantal berichten:' + berichten.length);
                            if(berichten.length > maxAantalNieuweBerichten) {
                                try {
                                    var overhead = berichten.length-maxAantalNieuweBerichten;
                                    berichten.splice(maxAantalNieuweBerichten, overhead);
                                    console.log('Alleen de laatste ' + maxAantalNieuweBerichten + ' berichten toevoegen aan het scherm, aantal berichten weggegooid:' + overhead);
                                } catch(err) {
                                    console.log("fout bij het verkleinen van het aantal berichten:" + err);
                                }
                            }
                            lastCall = new Date();
                            updateRunning = true;
                            showNewMessage(berichten, 0);
                        }

                        $('.berichttekst').ellipsis();
                     }
                  });
              } else {
                  console.log("Er is nog een update bezig, we wachten totdat deze klaar is!");
              }
        }

        function showNewMessage(messages, index) {

            if(index < messages.length)
            {
                var message = messages[index];
                var htmlMsg = createMessage(message);
                if(htmlMsg == null)
                {
                    console.log("Geen html kunnen maken van bericht " + message.berichtId);
                    showNewMessage(messages,index+1);
                    return;
                }
                if(message.prevalidatie == true && message.aantalMeldingen == 0) {
                    console.log('Skip succesvolle prevalidatie. Bericht van ' + message.partij + ', tekst: ' +message.bericht);
                    showNewMessage(messages,index+1); //Skip de prevalidatie zonder meldingen
                    return;
                }
                var total = $("article").length;
                if(total >= maxAantalBerichtenTonen) {
                    var last = $('#messages article:last');
                    last.fadeOut(250, function() {
                        last.remove();
                        total = $("article").length;
                        showMessage(messages,htmlMsg,index);
                    });
                } else {
                    showMessage(messages,htmlMsg,index);
                }
                $(this).dequeue();
            } else {
                updateRunning = false;
            }
        }

        function formatDate(date, fmt) {
            function pad(value) {
                return (value.toString().length < 2) ? '0' + value : value;
            }
            return fmt.replace(/%([a-zA-Z])/g, function (_, fmtCode) {
                switch (fmtCode) {
                case 'Y':
                    return date.getUTCFullYear();
                case 'M':
                    return pad(date.getUTCMonth() + 1);
                case 'd':
                    return pad(date.getUTCDate());
                case 'H':
                    return pad(date.getHours());
                case 'm':
                    return pad(date.getMinutes());
                case 's':
                    return pad(date.getSeconds());
                default:
                    throw new Error('Unsupported format code: ' + fmtCode);
                }
            });
        }

        function showMessage(messages, htmlMessage, index) {
            console.log("Voeg nu " + messages[index].soortBijhouding + " bericht " + messages[index].berichtId + " van gemeente " + messages[index].partij + " toe aan het scherm");
            htmlMessage.hide();
            $("#messages").prepend(htmlMessage);
            aantalBerichtenOpHetScherm++;
            htmlMessage.slideDown(800, function() {
                $('.berichttekst').ellipsis();
                showNewMessage(messages, index+1);
            });
        }
		    
        function getAllMessages() {
            console.log("URL berichten" + "${berichtenUrl}");

            $.ajax ({
                 url: "${berichtenUrl}",
                 dataType: 'json',
                 timeout: 10000,
                 success: function(data) {
                    if (!data.berichten){
                        console.log('Geen berichten in JSON resultaat gevonden');
                        return false;
                    }

                    console.log('Berichten ' + data.aantalBerichten)
                    maxAantalBerichtenTonen = data.maximumAantalBerichtenPerPagina;
                    if(data.aantalBerichten > 0)
                    {
                        berichten = data.berichten;
                        console.log('Aantal berichten:' + berichten.length);
                        //Beperk het aantal nieuwe berichten dat we tonen tot x, omdat we anders niet rondkomen met de verversing van het scherm
                        if(berichten.length > maxAantalNieuweBerichten) {
                            try {
                                for(i=0; i<berichten.length;i++) {
                                    if(berichten[i].prevalidatie == true && berichten[i].aantalMeldingen == 0) {
                                        berichten.splice(i,1);
                                        console.log("skip prevalidatie bericht, lengte is nu " + berichten.length);
                                    }
                                }
                                var overhead = berichten.length-10;
                                berichten.splice(10, overhead);
                                console.log('Alleen de laatste 10 berichten toevoegen aan het scherm, aantal berichten weggegooid:' + overhead);
                            } catch(err) {
                                console.log("fout bij het verkleinen van het aantal berichten:" + err);
                            }
                        }
                        lastCall = new Date();
                        updateRunning = true;
                        //Keer de verzameling om, omdat we hem achterstevoren willen tonen op het scherm
                        showNewMessage(berichten.reverse(), 0);
                    }
                    lastCall = new Date();
                    $('.berichttekst').ellipsis();
                 }
            });
        }

        function createMessage(message) {
            var bestaandId = "bericht-"+message.berichtId;

            if($("#"+bestaandId).length != 0) {
                console.log("Skip bericht " + bestaandId + ", deze bestaat al op het scherm");
                return null;
            }
            if(aantalBerichtenOpHetScherm % 2 == 0) {
                berichtClass = "even";
            } else {
                berichtClass = "oneven";
            }

            var articleElem = '<article id="bericht-' + message.berichtId + '" class="bericht ' + berichtClass + '"" />';
            var $res = $(articleElem);
            $res.empty();

            var timestamp = new Date(message.verzondenOp);

            var content='<div class="padding-fix">';

            content += '<figure class="berichtIndicatorLinks"><img src="${contextRoot}/resources/images/' + message.partij + '.jpg" class="stadlogo" title="' + message.partij + '"></figure>';

            var messageClasses = 'berichttekst';
            if(message.aantalMeldingen > 0) {
                messageClasses += ' meldingenAanwezig';
            }

            content +='<div class="'+messageClasses + '">';
            if(timestamp.getUTCDate()==(new Date().getUTCDate())) {
                content +='<span class="timestamp" title="' + timestamp.toJSON() + '" >' + formatDate(timestamp,"%H:%m:%s") + '</span>';
            } else {
                content +='<span class="timestamp" title="' + timestamp.toJSON() + '" >' + formatDate(timestamp,"%d-%M %H:%m:%s") + '</span>';
            }
            content +='<span class="stadnaam">' + message.partij + '</span>&nbsp;via&nbsp;<span class="bzmnaam">' + message.burgerZakenModule + '</span>';

            content +='<p class="message">';
            content += message.bericht;
            details = message.berichtDetails.replace(/\n/g, '<br>');
            content += '<br>' + details;
            content += '</p>';
            content +='</div>';

            content +='<div class="berichtstatus">';
            if(message.prevalidatie == true) {
                content += '<figure class="prevalidatie"><img src="${contextRoot}/resources/images/prevalidatie.png" class="prevalidatie" title="prevalidatie"></figure>';
            }

            //Icoontjes die de status van de meldingen aangeven
             if(message.aantalMeldingen > 0) {
                 if(message.prevalidatie == false && message.berichtDetails.indexOf('Fout') == -1 && message.berichtDetails.indexOf('Overrulebaar')==-1) {
                     content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-ok.png" class="meldingstatus" title="Bericht verwerkt met meldingen"></figure>';
                 }

                 if(message.berichtDetails.indexOf('Waarschuwing') >= 0) {
                     content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-waarschuwing.png" class="meldingstatus" title="melding(en) met waarschuwingen"></figure>';
                 }
                 if(message.berichtDetails.indexOf('Overruled') >= 0) {
                     content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-overruled.png" class="meldingstatus" title="melding(en) overruled"></figure>';
                 }
                 if(message.berichtDetails.indexOf('Overrulebaar') >= 0) {
                     content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-overrulebaar.png" class="meldingstatus" title="melding(en) overrulebaar"></figure>';
                 }

                 if(message.berichtDetails.indexOf('Fout') >= 0) {
                     content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-fout.png" class="meldingstatus" title="melding(en) met fouten"></figure>';
                 }


             } else {
                 content += '<figure class="melding"><img src="${contextRoot}/resources/images/meldingen-ok.png" class="meldingstatus" title="Succesvol verwerkt bericht"></figure>';
             }
            content +='</div>';
            content += '<figure class="berichtIndicatorRechts"><img src="${contextRoot}/resources/images/' + message.soortBijhouding.toLowerCase() + '.jpg" class="berichttype" title="' + message.soortBijhouding + '"></figure>';


            content +='<div class="clear"></div></div>';

            $res.append(content);

            return $res;
        }

        getAllMessages();
        $('#message-container').show();
        setInterval(function(){ getNewMessages ()}, 10000);
        $('#footer').css({position:'absolute',bottom:0});
    </script>
</head>
<body>
	<header><hgroup><h1><img src="${contextRoot}/resources/images/rijksoverheid-logo.jpg" height="25px" class="logo"/><fmt:message key="application.title"/> <c:out value="${partij}" /></h1></hgroup></header>
	
	<div id="wrapper" role="main">
	   
		<section id="message-container">
			
				<div id="messages" class="notification-area">
				</div>
		</section>
      
 	</div>
   <footer id="footer">
    <div id="footerline">
        <div class="legenda">
             <figure class="melding"><img src="${contextRoot}/resources/images/meldingen-ok.png" class="meldingstatus" title="Succesvol verwerkt bericht">Verwerkt</figure>
             <figure class="melding"><img src="${contextRoot}/resources/images/meldingen-waarschuwing.png" class="meldingstatus" title="melding(en) met waarschuwingen">Waarschuwing</figure>
             <figure class="melding"><img src="${contextRoot}/resources/images/meldingen-fout.png" class="meldingstatus" title="melding(en) met fouten">Fout</figure>
             <figure class="melding"><img src="${contextRoot}/resources/images/meldingen-overrulebaar.png" class="meldingstatus" title="melding(en) overrulebaar">Overrulebaar</figure>
             <figure class="melding"><img src="${contextRoot}/resources/images/meldingen-overruled.png" class="meldingstatus" title="melding(en) overruled">Overruled</figure>
             <figure class="melding"><img src="${contextRoot}/resources/images/prevalidatie.png" class="meldingstatus" title="Prevalidatie">Prevalidatie</figure>

        </div>
        <p><img src="${contextRoot}/resources/images/rijksoverheid-logo.jpg" height="25px" class="logo"/>&copy;2012&nbsp;Versie&nbsp;<fmt:message key="application.version"/></p>
    </div>
    </footer>
	

</body>
</html>	