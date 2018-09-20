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
<c:set var="statistiekenUrl" value="${contextRoot}/statistieken/data/${dag}/${maand}/${jaar}/${uur}/${minuut}/${seconde}" />
<head>
	<meta charset="utf-8" />
	<title><fmt:message key="application.title"/> Statistieken</title>
	<meta name="description" content="">

	<!-- Use the .htaccess and remove these lines to avoid edge case issues. More info: h5bp.com/i/378 -->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link href="${contextRoot}/resources/css/html5reset.css" rel="stylesheet" type="text/css" />
    <link href="${contextRoot}/resources/css/brp-generiek.css" rel="stylesheet" type="text/css" />
    <link href="${contextRoot}/resources/css/statistieken.css" rel="stylesheet" type="text/css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script>window.jQuery || document.write('<script src="${contextRoot}/resources/js/libs/jquery-1.7.2.min.js"><\/script>')</script>
	<!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
	<script src="${contextRoot}/resources/js/libs/modernizr-2.5.3.min.js"></script>
		<script src="${contextRoot}/resources/js/libs/jquery.timeago.js" type="text/javascript"></script>
        <script src="${contextRoot}/resources/js/libs/jquery.timeago.nl.js" type="text/javascript"></script>
<script>
	//Page fadein
	$(document).ready(function() {
        $("body").css("display", "none");
        $("body").fadeIn(2000);
    });
    var console=console||{"log":function(){}};
    var lastCall = new Date();

	function getStatistieken() {
        console.log("URL statistieken" + "${statistiekenUrl}");

    	$.ajax ({
			 url: "${statistiekenUrl}",
			 dataType: 'json',
			 timeout: 10000,
			 success: function(data) {
				$('#aantalGeboortePrevalidaties').html(data.geboorteStatistiek.aantalPrevalidaties);
				$('#aantalHuwelijkPrevalidaties').html(data.huwelijkStatistiek.aantalPrevalidaties);
				$('#aantalVerhuizingPrevalidaties').html(data.verhuizingStatistiek.aantalPrevalidaties);
				$('#aantalAdrescorrectiePrevalidaties').html(data.adrescorrectieStatistiek.aantalPrevalidaties);
				$('#aantalGeboorteBijhoudingen').html(data.geboorteStatistiek.aantalBijhoudingen);
				$('#aantalHuwelijkBijhoudingen').html( data.huwelijkStatistiek.aantalBijhoudingen);
				$('#aantalVerhuizingBijhoudingen').html(data.verhuizingStatistiek.aantalBijhoudingen);
				$('#aantalAdrescorrectieBijhoudingen').html(data.adrescorrectieStatistiek.aantalBijhoudingen);
				console.log("statistieken vernieuwd. ");
			}
        });
        
	}
    
	getStatistieken();
	setInterval(function(){ getStatistieken ()}, 10000);
</script>
</head>
<body>
	<header><hgroup><h1><img src="${contextRoot}/resources/images/rijksoverheid-logo.jpg" height="25px" class="logo"/><fmt:message key="application.title"/> Statistieken</h1></hgroup></header>

	<div id="wrapper" role="main">
        <table id="statistieken">
            <tr>
                <td>&nbsp;</td>
                <td><img src="${contextRoot}/resources/images/geboorte.jpg" class="berichttype"/></td>
                <td><img src="${contextRoot}/resources/images/verhuizing.jpg" class="berichttype"/></td>
                <td><img src="${contextRoot}/resources/images/huwelijk.jpg" class="berichttype"/></td>
                <td><img src="${contextRoot}/resources/images/adrescorrectie.jpg" class="berichttype"/></td>
            </tr>
            <tr>
                <td><img src="${contextRoot}/resources/images/bericht-prevalidatie.png" class="berichtsoort"/></td>
                <td id="aantalGeboortePrevalidaties" class="aantal">&nbsp;</td>
                <td id="aantalVerhuizingPrevalidaties" class="aantal">&nbsp;</td>
                <td id="aantalHuwelijkPrevalidaties" class="aantal">&nbsp;</td>
                <td id="aantalAdrescorrectiePrevalidaties" class="aantal">&nbsp;</td>
            </tr>
            <tr>
                <td><img src="${contextRoot}/resources/images/bericht.png" class="berichtsoort"/></td>
                <td id="aantalGeboorteBijhoudingen" class="aantal">&nbsp;</td>
                <td id="aantalVerhuizingBijhoudingen" class="aantal">&nbsp;</td>
                <td id="aantalHuwelijkBijhoudingen" class="aantal">&nbsp;</td>
                <td id="aantalAdrescorrectieBijhoudingen" class="aantal">&nbsp;</td>
            </tr>
        </table>


    </div>
    <footer id="footer">
        <div id="footerline">
            <p><img src="${contextRoot}/resources/images/rijksoverheid-logo.jpg" height="25px" class="logo"/>&copy;2012&nbsp;Versie&nbsp;<fmt:message key="application.version"/></p>
        </div>
    </footer>
</body>
</html>