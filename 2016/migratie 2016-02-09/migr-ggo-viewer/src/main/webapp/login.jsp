<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="nl">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Login pagina | GGO Viewer</title>
	
	<meta name="description" content="" />
	<meta name="keywords" content="" />
	<meta name="distribution" content="global" />
	<meta name="robots" content="index, follow" />
	<meta name="language" content="nl" />	
	
	<link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" />
	<!--[if IE 8]><link rel="stylesheet" href="css/ie/ie8.css" type="text/css" media="screen" /><![endif]-->
	<!--[if lte IE 7]><link rel="stylesheet" href="css/ie/ie7.css" type="text/css" media="screen" /><![endif]-->
	<!--[if lte IE 6]><link rel="stylesheet" href="css/ie/ie6.css" type="text/css" media="screen" /><![endif]-->
	<link rel="stylesheet" href="css/print.css" type="text/css" media="print" />
	
	<link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon" />

	<link rel="stylesheet" href="css/viewer/viewer-color.css" />

	<script type="text/javascript" src="js/lib/jquery-1.8.3.min.js" ></script>
	<script type="text/javascript" src="js/login.js"></script>

</head>
<body id="login">

	<div class="container blauwig">
		
		<div class="header">
			<span></span>
					
			<h1 class="branding"><img src="images/RO_BZK_BPR_Logo_Homepage.png" alt="Ministerie van Binnenlandse Zaken en Koninkrijksrelaties | Basisregistraties Persoonsgegevens en Reisdocumenten" height="101" width="500" /></h1>

		</div>

		<div class="content_wrapper1"><div class="content_wrapper2">
			
			<div class="content">
				
			<!-- START : CONTENT -->
			<div id = "errorcontainer">
<%
   if (request.getAttribute("shiroLoginFailure") != null) {
%>
				<div class="mod message_err">
					<h2>Uw gebruikersnaam en / of wachtwoord zijn niet correct.</h2>
				</div>			
<%
   }
%>
			</div>

				<div class="content_main">
					
					<h1>GGO Viewer</h1>
					<p>De GGO Viewer is alleen toegankelijk met een geldig account.</p>
										
					<form method="post" action="#" id="loginForm" class="form_ll">
												
						<fieldset>
							<ol>
								<li>
									<label for="gebruikersnaam">Gebruikersnaam</label>
									<input type="text" class="text" name="gebruikersnaam" id="gebruikersnaam" />
								</li>								
								<li>
									<label for="wachtwoord">Wachtwoord</label>
									<input type="password" class="text" name="wachtwoord" id="wachtwoord" />
									<!-- <p class="password"><a href="#" title="Wachtwoord vergeten?">Wachtwoord vergeten?</a></p> -->
								</li>
							    <!-- 
                                <li>
							        <label for="remember">Onthouden</label>
							        <input type="checkbox" name="onthouden" id="onthouden" />
							    </li>
                                --> 
							</ol>
						</fieldset>
						
						<p><input type="submit" name="submit" value="Login" class="submit" /></p>
												
					</form>
					
				</div>				
				
			<!-- END : CONTENT -->						
			
			</div>
		
		</div></div>	
		
		<div class="footer">
				&nbsp; <!-- this 'space' is necessary to display the bottom border shadow!!! -->
			<span></span>			
		</div>		
		
	</div>
	
</body>
</html>