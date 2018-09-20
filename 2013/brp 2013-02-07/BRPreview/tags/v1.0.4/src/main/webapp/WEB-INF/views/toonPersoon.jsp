<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="brp" uri="http://nl.bzk.brp.preview/TagFuncties" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:choose>
    <c:when test="${pageContext.request.secure}==true">
        <c:set var="baseUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}" />
    </c:when>
    <c:otherwise>
        <c:set var="baseUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.localPort}${pageContext.request.contextPath}" />
    </c:otherwise>
</c:choose>
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


    <link type="text/css" href="${baseUrl}/resources/css/html5reset.css" rel="stylesheet" />
    <link type="text/css" href="${baseUrl}/resources/css/bevraging.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="/${pageContext.request.contextPath}/dashboard/resources/js/libs/jquery-1.7.2.min.js"><\/script>')</script>
    <script>document.documentElement.className = "js";</script>
  
    <!-- All JavaScript at the bottom, except this Modernizr build.
       Modernizr enables HTML5 elements & feature detects for optimal performance.
       Create your own custom Modernizr build: www.modernizr.com/download/ -->
    <script src="${baseUrl}/resources/js/libs/modernizr-2.5.3.min.js"></script>
</head>
<body>
    <header>
    <hgroup><h1>BRP Sneak Preview </h1></hgroup>
    </header>
    
    <div role="main">
        <section id="search">
            <form method="GET" id="searchbsn">
            <label for="bsn">BSN</label>
                <input type="number" name="bsn" placeholder="Type hier het BSN dat u zoekt" size="35" maxlength="12">
                <input type="submit" value="Zoek"></form>
        </section>
        
        <c:if test="${persoon!=null && bsn==persoon.bsn}">
        <section id="person">
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
				   <p>&nbsp;</p>
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
            <c:if test="${persoon.datoverlijden}!=null">
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
                    <td class="data"><c:out value="${nationaliteiten[0].rdnverk.naam}"/></td>
                    </tr> 
                    </table>
                </div> 
            </section>   
            <section class="collapsable">
                <h3 class="inactive">Adres<img src="${baseUrl}/resources/images/history.png" class="historietonen"></h3>
                <div>
                <c:set var="vanaf" value="${brp:datum(adres.dataanvadresh)}"/>
                <c:set var="adreskopregel" value="vanaf ${vanaf}"/>
           
                 <section>
                 <h3 class="active">Adres ${adreskopregel}</h3>
                    <div>
                 <table>                   
                    <tr>
	                    <td class="label">Soort</td>
	                    <td class="data">
	                        <c:out value="${adres.srt.naam}"/>
	                    </td>
	                    <td class="label">Naam ob. ruimte</td>
	                    <td class="data"><c:out value="${adres.afgekortenor}"/></td>
                    </tr> 
                    <tr>
                        <td class="label">Reden wijziging</td>
                        <td class="data">
                            <c:out value="${adres.rdnwijz.naam}"/>
                        </td>
                        <td class="label">Huisnummer</td>
                        <td class="data"><c:out value="${adres.huisnr}"/></td>
                    </tr>          
                    <tr>
                        <td class="label">Aangever</td>
                        <td class="data">
                            <c:out value="${adres.aangadresh.naam}"/>
                        </td>
                        <td class="label">Huisletter</td>
                        <td class="data"><c:out value="${adres.huisletter}"/></td>
                    </tr>      
                    <tr>
                        <td class="label">Datum aanvang</td>
                        <td class="data">
                            <c:out value="${vanaf}"/>
                        </td>
                        <td class="label">Huisnummertoev.</td>
                        <td class="data"><c:out value="${adres.huisnrtoevoeging}"/></td>
                    </tr>   
                    <tr>
                        <td class="label">Gemeente</td>
                        <td class="data">
                            <c:out value="${adres.gem.naam}"/>
                        </td>
                        <td class="label">Postcode</td>
                        <td class="data"><c:out value="${adres.postcode}"/></td>
                    </tr>       
                    <tr>
                        <td class="label">Adreseerbaar obj.</td>
                        <td class="data">
                            <c:out value="${adres.adresseerbaarobject}"/>
                        </td>
                        <td class="label">Woonplaats</td>
                        <td class="data"><c:out value="${adres.wpl.naam}"/></td>
                    </tr>        
                    <tr>
                        <td class="label">Id.code nr aanduid.</td>
                        <td class="data">
                            <c:out value="${adres.identcodenraand}"/>
                        </td>
                        <td class="label">&nbsp;</td>
                        <td class="data">&nbsp;</td>
                    </tr> 
                    </table>
                    </div>
                 </section>
                 <section id="adreshistorie">
                    <c:forEach var="historiesadres" items="${adreshistorie}">
                    <section class="historie">
                    <c:set var="vanaf" value="${brp:datum(historiesadres.dataanvadresh)}" />
                    <c:set var="tot" value="${brp:datum(historiesadres.dateindegel)}"/>
                    <c:choose>
                        <c:when test="${!empty historiesadres.dateindegel && !empty historiesadres.dataanvadresh}">
                            <c:set var="adreskopregel" value="vanaf ${vanaf} tot ${tot}"/>                    
                        </c:when>
                        <c:when test="${!empty historiesadres.dateindegel && empty historiesadres.dataanvadresh}">
                            <c:set var="adreskopregel" value="tot ${tot}"/>                    
                        </c:when>
	                    <c:otherwise>
	                        <c:set var="adreskopregel" value="vanaf ${vanaf}"/>
	                    </c:otherwise>
                    </c:choose>
	                <h3 class="inactive"><img src="${baseUrl}/resources/images/history.png" class="historiesadres" title="Histories adres"/>Adres ${adreskopregel}</h3>
	                <div>
                    <table>                   
                    <tr>
                        <td class="label">Soort</td>
                        <td class="data">
                            <c:out value="${historiesadres.srt.naam}"/>
                        </td>
                        <td class="label">Naam ob. ruimte</td>
                        <td class="data"><c:out value="${historiesadres.afgekortenor}"/></td>
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
                        <td class="label">Datum aanvang</td>
                        <td class="data">
                            <c:out value="${vanaf}"/>
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
                        <td class="label">Adreseerbaar obj.</td>
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
                        <section class="collapsable">
                <h3 class="inactive">Partner</h3>
                <c:if test="${not empty partner.betrokkene}">
                	<tag:betrokkene betrokkene="${partner}" />
                </c:if>
            </section>    
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
                	<c:if test="${empty ouders}">
                	   <p>&nbsp;</p>
                	</c:if>
                </div> 
            </section>
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
                        <section class="collapsable">
                <h3 class="inactive">Inschrijving en bijhoudingsverantwoordelijke</h3>
                <div>
                </div> 
            </section>
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
                        <section class="collapsable">
                <h3 class="inactive">Indicaties</h3>
                <div><p>&nbsp;</p>
                </div> 
            </section>
        </section>
        <aside id="actions">
        <c:if test="${berichten!=null}">
			<c:forEach var="bericht" items="${berichten}" varStatus="counter">
			    <c:if test="${bericht.prevalidatie != true}">
					<article>
					<img src="${baseUrl}/resources/images/${bericht.partij}.jpg" class="stadlogo-mini" title="${bericht.partij}">
		            <h3>${bericht.soortBijhouding.naam}</h3>
			            <p>Op: <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${bericht.verzondenOp.time}" /></p>
			            <p>${bericht.bericht}</p>
			           
			            <p>${fn:replace(bericht.berichtDetails,"","<br>")}</p>

		            </article>
	            </c:if>
			</c:forEach>
		</c:if>
		<c:if test="${berichten==null}">
		  <p>Geen berichten vandaag voor deze persoon</p>
		</c:if>
		
        </aside>
        </c:if>
        <c:if test="${bsn!=persoon.bsn}">
            <p>Persoon niet gevonden voor deze BSN.</p>
        </c:if>
    </div>
    
    <footer>
        <div clearfix><p><img src="${baseUrl}/resources/images/rijksoverheid-logo.jpg" height="50px"/>&copy;2012&nbsp;Versie <fmt:message key="application.version"/></p></div>
    </footer>
    

    
   
    <script src="${baseUrl}/resources/js/libs/jquery.collapse.js"></script>
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