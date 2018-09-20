<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> 

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title><tiles:getAsString name="title" />
</title>
  <script type="text/javascript" src="<c:url value="/jarresources/dojo/dojo.js" />"></script>
  <script type="text/javascript" src="<c:url value="/jarresources/spring/Spring.js" />"> </script>
  <script type="text/javascript" src="<c:url value="/jarresources/spring/Spring-Dojo.js" />"> </script>
  <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.min.js" />"> </script>
  <script type="text/javascript" src="<c:url value="/resources/js/pagina-gedrag.js" />"></script>
  <!-- script type="text/javascript" src="https://www.google.com/jsapi"></script-->
  <script type="text/javascript" src="<c:url value="/resources/js/jsapi.js" />"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/charts.js" />"></script>
  
  
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/reset.css"/>" />
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/text.css"/>" />
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/grid.css"/>" />
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/layout.css"/>" />
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/nav.css"/>" />
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/displaytag.css"/>" />
</head>
<body>
	<div class="container_16">
		<div class="grid_16 alpha omega" id="headerPanelTitel">
			<tiles:insertAttribute name="header" />
		</div>
		<div class="grid_16 alpha omega" id="headerPanelTitelMenu">
			<tiles:insertAttribute name="menu" />
		</div>
		<div class="grid_16 alpha omega" id="notificatiePaneel">
			<tiles:insertAttribute name="berichten"/>
		</div>
		<div id="content">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>