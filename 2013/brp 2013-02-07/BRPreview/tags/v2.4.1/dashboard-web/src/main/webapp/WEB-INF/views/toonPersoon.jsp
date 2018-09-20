<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="brp" uri="http://nl.bzk.brp.preview/TagFuncties" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
    <c:when test="${bsn!=null && !empty bsn}">
        <c:set var="berichtenUrl" value="${baseUrl}${properties.berichten_context}/service/berichten/bsn/${bsn}" />
    </c:when>
    <c:otherwise>
        <c:set var="berichtenUrl" value="${baseUrl}${properties.berichten_context}/service/berichten" />
    </c:otherwise>

</c:choose>
<c:set var="bsnUrl" value="${baseUrl}${properties.berichten_context}/service/berichten/bsns" />
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="nl"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="nl"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="nl"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="nl"> <!--<![endif]-->
<head>
    <meta charset="utf-8" />
    <title>Bevraging</title>
    <meta name="description" content="">
    
    <!-- Use the .htaccess and remove these lines to avoid edge case issues. More info: h5bp.com/i/378 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

    <link type="text/css" href="${contextRoot}/resources/css/html5reset.css" rel="stylesheet" />
    <link href="${contextRoot}/resources/css/brp-generiek.css" rel="stylesheet" type="text/css" />
    <link type="text/css" href="${contextRoot}/resources/css/bevraging.css" rel="stylesheet" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="${contextRoot}/resources/js/libs/jquery-1.8.0.min.js"><\/script>')</script>
    <script>document.documentElement.className = "js";</script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.23/themes/redmond/jquery-ui.css" type="text/css" media="all" />
	<script src="http://code.jquery.com/ui/1.8.23/jquery-ui.min.js" type="text/javascript"></script>

    <!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
    <script src="${contextRoot}/resources/js/libs/modernizr-2.5.3.min.js"></script>
</head>
<body>
	<script>
			//Page fadein
			$(document).ready(function() {
				$("body").css("display", "none");
				$("body").fadeIn(2000);
			});

			
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
						if(data.aantalBerichten > 0) {
                        	console.log('Aantal berichten:' + data.aantalBerichten);
                        	$("#actions").empty();
                        	maxAantalBerichtenTonen = data.maximumAantalBerichtenPerPagina;
                        	//maxAantalBerichtenTonen = 4;
                            for( var i in data.berichten) {
                            	var result = data.berichten[i];
								$res = createMessage(result);
                                if($res == null) {
                                	continue;
                                }
                                if(result.prevalidatie == true) {
                                    console.log('Skip prevalidatie. Bericht van ' + result.partij + ', tekst: ' + result.bericht);
                                	continue; //Skip de prevalidatie zonder meldingen
                                }
								console.log("Bericht " + i + " tonen");
								showNewMessage($res);
								if(i > 15) {
								    break; //niet meer dan 15 berichten laten zien
								}
							}
						}
					}
	            });
	            
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
                        return pad(date.getUTCHours());
                    case 'm':
                        return pad(date.getUTCMinutes());
                    case 's':
                        return pad(date.getUTCSeconds());
                    default:
                        throw new Error('Unsupported format code: ' + fmtCode);
                    }
                });
            }
			
			function showNewMessage(msg) {
				if(msg != null) {
					msg.hide();
					$("#actions").append(msg);
					msg.slideDown(1000);
			    }
			}
			
			function createMessage(message) { 	
		    	var bestaandId = "bericht-"+message.berichtId;
		    	
		    	if($("#"+bestaandId).length != 0) {
		    		console.log("Skip bericht " + bestaandId + ", deze bestaat al op het scherm");
		    		return null;
		    	} 
		    	
		    	var articleElem = '<article id="bericht-' + message.berichtId + '" />';
		    	var $res = $(articleElem);                        
                $res.empty();
                
                var timestamp = new Date(message.verzondenOp);
                
                var content='<div class="padding-fix">';

                content +='<div class="bericht-mini">';

                content += '<figure class="berichtIndicatorLinks"><img src="${contextRoot}/resources/images/' + message.soortBijhouding.toLowerCase() + '.jpg" class="berichttype-mini" title="' + message.soortBijhouding + '"></figure>';
                content += '<figure class="berichtIndicatorLinks"><img src="${contextRoot}/resources/images/' + message.partij + '.jpg" class="stadlogo-mini" title="' + message.partij + '"></figure>';
                content += '<figure class="berichtIndicatorRechts"><img src="${contextRoot}/resources/images/arrow-down.png" class="bericht-details-knop bericht-details-knop-show" id="bericht-details-knop-' + message.berichtId + '" title="Meer details" onClick="toggleDetails(' + message.berichtId + ');"></figure>';
                content +='<p class="bericht" title="' + message.bericht + "\n" + message.berichtDetails + '">';
                content +='Op:&nbsp;<span class="timestamp" title="' + timestamp.toJSON() + '" >' + timestamp.getDate() + '-' + timestamp.getMonth() + '-' + timestamp.getFullYear() + '&nbsp;' + timestamp.toTimeString().substring(0,8) + '</span><br />';
                content +='Door:&nbsp;<span class="stadnaam">' + message.partij + '</span><br />';
                content += message.bericht;
                content += "<span id='bericht-details-" + message.berichtId + "' class='berichtdetails'><br>" + message.berichtDetails.replace('\n','<br>') +"</span>";
                content += '</p>';

                content +='</div>';
                
                $res.append(content);
                
                return $res;
		    }
			
			function toggleDetails(berichtId) {
                var details = $("#bericht-details-" + berichtId);
                var detailsKnop = $("#bericht-details-knop-" + berichtId);

                if(detailsKnop.attr('src').indexOf('arrow-down.png') > 0) {
                    detailsKnop.attr('src','${contextRoot}/resources/images/arrow-up.png');
                } else {
                    detailsKnop.attr('src','${contextRoot}/resources/images/arrow-down.png');
                }

                details.toggle();
            }

			$(function() {
				$( "#bsn" ).autocomplete({
					source: "${bsnUrl}"
				});
			});
			
			getAllMessages(); 
	</script>
    <header>
    <hgroup><h1>BRPreview zoekscherm (GEEN PRODUCTIE FUNCTIONALITEIT)</h1></hgroup>
    </header>
    <div id="wrapper" role="main">
        <section id="search">
            <form method="GET" id="searchbsn">
            <label for="bsn">BSN</label>
                <input type="number" id="bsn" name="bsn" placeholder="Type hier een BSN" size="45" maxlength="12">
                <input type="submit" value="Zoek"></form>
        </section>
        
        <c:if test="${persoon!=null && bsn==persoon.bsn}">
        <section id="person">
            <c:choose>
                <c:when test="${persoon.geslachtsaand.naam}=='Man'">
                    <c:set var="geslachtUrl" value="${contextRoot}/resources/images/man.png" />
                </c:when>
                <c:otherwise>
                    <c:set var="geslachtUrl" value="${contextRoot}/resources/images/vrouw.png" />
                </c:otherwise>
            </c:choose>
            <h2><img src="${geslachtUrl}" class="geslacht" /><c:out value="${brp:naam(persoon)}"/></h2>
            <section class="collapsable">
               <h3 class="active">Identificatienummers</h3>
				  <div>
				    <table>
				    <tr>
				    <td class="label">Burgerservicenummer</td><td class="data"><c:out value="${persoon.bsn}"/></td>
				    <td class="label">A-nummer</td><td class="data"><c:out value="${persoon.anr}"/></td>
			         </tr>
				    </table>	
				  </div>
			</section>
			<section class="collapsable">
				  <h3 class="active">Geslachtsaanduiding en naam</h3>
				  <div>
				  <table>                   
                    <tr>
                    <td class="label">Geslacht</td><td class="data">
                    <c:out value="${persoon.geslachtsaand.naam}"/>
				    </td>
                    <td class="label">Geslachtsnaam</td><td class="data"><c:out value="${persoon.geslnaam}"/></td>
                    </tr>
                    <tr>
                    <td class="label">Voornamen</td><td class="data"><c:out value="${persoon.voornamen}"/></td>
                    <td class="label">Adelijke titel</td><td class="data">
                    <c:catch var="ex">
                        <c:out value="${persoon.adellijketitel}"/>
                    </c:catch>
                    </td>
					<c:if test="${not empty ex}">
					  &nbsp;
					</c:if>
                    </tr>
                    <tr>
                    <td class="label">Voorvoegsel</td><td class="data"><c:out value="${persoon.voorvoegsel}"/></td>
                    <td class="label">Predikaat</td><td class="data"> <c:catch var="ex"><c:out value="${persoon.predikaat}"/></c:catch></td>
                    </tr>
                    <tr>
                    <td class="label">Scheidingsteken</td><td class="data"><c:out value="${persoon.scheidingsteken}"/></td>
                    <td class="label">&nbsp;</td><td class="data">&nbsp;</td>
                    </tr>
                 
                    </table>
				  </div>
			</section>
            <section class="collapsable">
				  <h3 class="inactive">Aanschrijving</h3>
				  <div>
				   <table>                   
                    <tr>
                    <td class="label">Naamgebruik</td><td class="data"><c:out value="${persoon.gebrgeslnaamegp.oms}" /></td>
                    <td class="label">Voorvoegsel</td><td class="data"><c:out value="${persoon.voorvoegselaanschr}"/></td>
                    </tr> 
                    <tr>
                    <td class="label">Gebruik adelijke titels/predikaten</td><td class="data">
                    <c:out value="${brp:jaNee(persoon.indaanschrmetadellijketitels)}"/>
                    </td>
                    <td class="label">Scheidingsteken</td><td class="data"><c:out value="${persoon.scheidingstekenaanschr}"/></td>
                    </tr>
                    <tr>
                    <td class="label">Predikaat</td><td class="data"><c:out value="${predikaat}" /></td>
                    <td class="label">Geslachtsnaam</td><td class="data"><c:out value="${persoon.geslnaamaanschr}"/></td>
                    </tr> 
                    <tr>
                    <td class="label">Voornamen</td><td class="data"><c:out value="${persoon.voornamenaanschr}"/></td>
                    <td class="label">Adelijke titel</td><td class="data"><c:out value="${adellijkeTitelAanschr}"/></td>
                    </tr>
                    <tr>
                    <td class="label">Algoritmisch afgeleid</td><td class="data"><c:out value="${brp:jaNee(persoon.indaanschralgoritmischafgele)}"/></td>
                    <td class="label">&nbsp;</td><td class="data">&nbsp;</td>
                    </tr>
                    </table>
				  </div>
			</section>
            <section class="collapsable">
				  <h3 class="inactive">Geboorte</h3>
				  <div>
				   <table>                   
                    <tr>
                    <td class="label">Datum geboorte</td><td class="data">
                         <c:out value="${brp:datum(persoon.datgeboorte)}" />
                    </td>
                    <td class="label">Plaats geboorte</td><td class="data"><c:out value="${persoon.wplgeboorte.naam}"/></td>
                    </tr> 
                    <tr>
                    <td class="label">Gemeente geboorte</td><td class="data">
                    <c:out value="${persoon.gemgeboorte.naam}"/>
                    </td>
                    <td class="label">Land geboorte</td><td class="data"><c:out value="${persoon.landgeboorte.naam}"/></td>
                    </tr>
                    </table>
				  </div>
            </section>
            <c:if test="${not empty persoon.datoverlijden}">
            <section class="collapsable">
                <h3 class="inactive">Overlijden</h3>
                <div><table>                   
                    <tr>
                    <td class="label">Datum overlijden</td><td class="data">
                    <c:out value="${persoon.datoverlijden}"/>
                    </td>
                    <td class="label">Plaats overlijden</td><td class="data"><c:out value="${persoon.wploverlijden.naam}"/></td>
                    </tr> 
                    <tr>
                    <td class="label">Gemeente overlijden</td><td class="data">
                    <c:out value="${persoon.gemoverlijden.naam}"/>
                    </td>
                    <td class="label">Land overlijden</td><td class="data"><c:out value="${persoon.landoverlijden.naam}"/></td>
                    </tr>
                    </table>
                </div> 
            </section>
            </c:if>
            <section class="collapsable">
                <h3 class="inactive">Nationaliteit</h3>
                <div><table>                   
                    <tr>
                    <td class="label">Nationaliteit</td>
                    <td class="data">
                        <c:out value="${nationaliteiten[0].nation.naam}"/>
                    </td>
                    <td class="label">Reden verkrijging</td>
                    <td class="data"><c:out value="${nationaliteiten[0].rdnverk.oms}"/></td>
                    </tr> 
                    </table>
                </div> 
            </section>   
            <section class="collapsable">
                <h3 class="inactive">Adres</h3>
                <div>
                <c:set var="vanaf" value="${brp:datum(adreshistorie[0].dataanvgel)}"/>
                <c:set var="adreskopregel" value="vanaf ${vanaf}"/>
           
                 <section>
                 <h3 class="active">Adres ${adreskopregel}<img src="${contextRoot}/resources/images/history.png" class="historietonen" onclick="$('#adreshistorie').toggle();return false;" title="Toon historische adressen"></h3>
                    <div>
                 <table>                   
                    <tr>
	                    <td class="label">Soort</td>
	                    <td class="data">
	                        <c:out value="${adreshistorie[0].srt.naam}"/>
	                    </td>
	                    <td class="label">Naam ob. ruimte</td>
	                    <td class="data"><c:out value="${adreshistorie[0].nor}"/></td>
                    </tr> 
                    <tr>
                        <td class="label">Reden wijziging</td>
                        <td class="data">
                            <c:out value="${adreshistorie[0].rdnwijz.naam}"/>
                        </td>
                        <td class="label">Huisnummer</td>
                        <td class="data"><c:out value="${adreshistorie[0].huisnr}"/></td>
                    </tr>          
                    <tr>
                        <td class="label">Aangever</td>
                        <td class="data">
                            <c:out value="${adreshistorie[0].aangadresh.naam}"/>
                        </td>
                        <td class="label">Huisletter</td>
                        <td class="data"><c:out value="${adreshistorie[0].huisletter}"/></td>
                    </tr>      
                    <tr>
                        <td class="label">Aanv. adreshouding</td>
                        <td class="data">
                            <c:out value="${brp:datum(adreshistorie[0].dataanvadresh)}"/>
                        </td>
                        <td class="label">Huisnummertoev.</td>
                        <td class="data"><c:out value="${adreshistorie[0].huisnrtoevoeging}"/></td>
                    </tr>   
                    <tr>
                        <td class="label">Gemeente</td>
                        <td class="data">
                            <c:out value="${adreshistorie[0].gem.naam}"/>
                        </td>
                        <td class="label">Postcode</td>
                        <td class="data"><c:out value="${adreshistorie[0].postcode}"/></td>
                    </tr>       
                    <tr>
                        <td class="label">Adresseerbaar obj.</td>
                        <td class="data">
                            <c:out value="${adreshistorie[0].adresseerbaarobject}"/>
                        </td>
                        <td class="label">Woonplaats</td>
                        <td class="data"><c:out value="${adreshistorie[0].wpl.naam}"/></td>
                    </tr>        
                    <tr>
                        <td class="label">Id.code nr aanduid.</td>
                        <td class="data">
                            <c:out value="${adreshistorie[0].identcodenraand}"/>
                        </td>
                        <td class="label">&nbsp;</td>
                        <td class="data">&nbsp;</td>
                    </tr> 
                    </table>
                    </div>
                 </section>
                 <section id="adreshistorie">
                    <c:forEach var="historiesadres" items="${adreshistorie}" begin="1">
                    <section class="historie">
                    <c:set var="vanaf" value="${brp:datum(historiesadres.dataanvgel)}" />
                    <c:set var="tot" value="${brp:datum(historiesadres.dateindegel)}"/>
                    <c:choose>
                        <c:when test="${!empty historiesadres.dateindegel && !empty historiesadres.dataanvgel}">
                            <c:set var="adreskopregel" value="vanaf ${vanaf} tot ${tot}"/>                    
                        </c:when>
                        <c:when test="${!empty historiesadres.dateindegel && empty historiesadres.dataanvgel}">
                            <c:set var="adreskopregel" value="tot ${tot}"/>                    
                        </c:when>
	                    <c:otherwise>
	                        <c:set var="adreskopregel" value="vanaf ${vanaf}"/>
	                    </c:otherwise>
                    </c:choose>
	                <h3 class="active"><img src="${contextRoot}/resources/images/history.png" class="historiesadres" title="Histories adres"/>Adres ${adreskopregel}</h3>
	                <div>
                    <table>                   
                    <tr>
                        <td class="label">Soort</td>
                        <td class="data">
                            <c:out value="${historiesadres.srt.naam}"/>
                        </td>
                        <td class="label">Naam ob. ruimte</td>
                        <td class="data"><c:out value="${historiesadres.nor}"/></td>
                    </tr> 
                     <tr>
                        <td class="label">Reden wijziging</td>
                        <td class="data">
                            <c:out value="${historiesadres.rdnwijz.naam}"/>
                        </td>
                        <td class="label">Huisnummer</td>
                        <td class="data"><c:out value="${historiesadres.huisnr}"/></td>
                    </tr>          
                    <tr>
                        <td class="label">Aangever</td>
                        <td class="data">
                            <c:out value="${historiesadres.aangadresh.naam}"/>
                        </td>
                        <td class="label">Huisletter</td>
                        <td class="data"><c:out value="${historiesadres.huisletter}"/></td>
                    </tr>      
                    <tr>
                        <td class="label">Aanv. adreshouding</td>
                        <td class="data">
                            <c:out value="${brp:datum(historiesadres.dataanvadresh)}"/>
                        </td>
                        <td class="label">Huisnummertoev.</td>
                        <td class="data"><c:out value="${historiesadres.huisnrtoevoeging}"/></td>
                    </tr>   
                    <tr>
                        <td class="label">Gemeente</td>
                        <td class="data">
                            <c:out value="${historiesadres.gem.naam}"/>
                        </td>
                        <td class="label">Postcode</td>
                        <td class="data"><c:out value="${historiesadres.postcode}"/></td>
                    </tr>       
                    <tr>
                        <td class="label">Adresseerbaar obj.</td>
                        <td class="data">
                            <c:out value="${historiesadres.adresseerbaarobject}"/>
                        </td>
                        <td class="label">Woonplaats</td>
                        <td class="data"><c:out value="${historiesadres.wpl.naam}"/></td>
                    </tr>        
                    <tr>
                        <td class="label">Id.code nr aanduid.</td>
                        <td class="data">
                            <c:out value="${historiesadres.identcodenraand}"/>
                        </td>
                        <td class="label">&nbsp;</td>
                        <td class="data">&nbsp;</td>
                    </tr>     
                      </table>
                     
                      </div>
                      </section>
                    </c:forEach>
                
                </section> 
                </div>    
            </section>
            <c:if test="${not empty partner}">
            <section class="collapsable">
                <h3 class="inactive">Partner</h3>
                <c:if test="${not empty partner.pers}">
                	<tag:betrokkene betrokkene="${partner}" />
                </c:if>
            </section>
            </c:if>
           <c:if test="${not empty ouders}">
            <section class="collapsable">
                <h3 class="inactive">Ouders</h3>
                <div>
                 <section id="collapsable">
                    <c:forEach var="ouder" items="${ouders}">
                    <section>
		                <h3 class="active">Ouder</h3>
		                <tag:betrokkene betrokkene="${ouder}" />
                    </section>
                    </c:forEach>
                 </section>
                </div> 
            </section>
            </c:if>
            <c:if test="${not empty kinderen}">
            <section class="collapsable">
                <h3 class="inactive">Kinderen</h3>
                <div>
                <c:forEach var="kind" items="${kinderen}">
                  <section>
	              	<h3 class="active">Kind</h3>
	                <tag:betrokkene betrokkene="${kind}" />
                  </section>
                  </c:forEach>
                </div>
            </section>
            </c:if>
            <section class="collapsable">
                <h3 class="inactive">Inschrijving en bijhoudingsverantwoordelijke</h3>
				  <div>
				   <table>                   
                    <tr>
                    <td class="label">Datum inschrijving</td><td class="data">
                         <c:out value="${brp:datum(persoon.datinschr)}" />
                    </td>
                    <td class="label">Opgeschort</td><td class="data">
                    <c:out value="${brp:jaNee(not empty persoon.rdnopschortingbijhouding)}"/>
                    </td>
                    </tr> 
                    <tr>
                    <td class="label">Verantwoordelijke</td><td class="data"><c:out value="${persoon.verantwoordelijke.naam}"/></td>
                    <td class="label">Reden opschorting</td><td class="data"><c:out value="${persoon.rdnopschortingbijhouding.naam}"/></td>
                    </tr>
                    </table>
				  </div>
            </section>
            <c:if test="${not empty persoon.bijhgem}">
            <section class="collapsable">
                <h3 class="inactive">Bijhoudingsgemeente</h3>
                <div>
                    <table>
	                    <tr>
		                    <td class="label">Bijhoudingsgemeente</td><td class="data"><c:out value="${persoon.bijhgem.naam}"/></td>
		                    <td class="label">Datum Inschrijving</td><td class="data"><c:out value="${brp:datum(persoon.datinschringem)}"/></td>
		                </tr>
                    </table>    
                </div> 
            </section>
            </c:if>
            <section class="collapsable">
                <h3 class="inactive">Indicaties</h3>
				  <div>
				    <table>
				    <tr>
				    <td class="label">Verstrekkingsbeperking</td><td class="data"><c:out value="${brp:jaNee(verstrekkingsbeperking)}"/></td>
				    <td class="label">Onder curatele</td><td class="data"><c:out value="${brp:jaNee(onderCuratele)}"/></td>
			         </tr>
				    </table>	
				  </div>
            </section>
        </section>
        <aside id="actions">
         <p class="welcome">Geen acties voor deze persoon gevonden in de BRP.</p>
        </aside>
        </c:if>
        <c:if test="${bsn!=persoon.bsn}">
            <p>Persoon niet gevonden voor deze BSN.</p>
        </c:if>
    </div>
    
    <footer id="footer">
        <div class="clearfix"><p><img src="${contextRoot}/resources/images/rijksoverheid-logo.jpg" class="logo" />&copy;2012&nbsp;Versie <fmt:message key="application.version"/></p></div>
    </footer>
    

    
   
    <script src="${contextRoot}/resources/js/libs/jquery.collapse.js"></script>
    <script>
        $(".collapsable").collapse({show: function(){
                this.animate({
                    opacity: 'toggle', 
                    height: 'toggle'
                }, 300);
            },
            hide : function() {
                this.animate({
                    opacity: 'toggle', 
                    height: 'toggle'
                }, 300);
            }
        });
        
        
    
    
    </script>
</body>
</html>