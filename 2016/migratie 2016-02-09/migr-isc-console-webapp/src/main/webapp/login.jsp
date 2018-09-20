<html>
<head>
    <title>JBoss SOA Login</title>
    <link href="/css/soa.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<script type="text/javascript" src="/css/header.js" ></script>
<div id="topsep">&nbsp;</div>

<div style="border: 1px solid darkgray; background: #cc0000; color: white; font-weight: bold; padding: 10px; margin: 5px">
    You must provide security credentials to access this management console.
</div>

<% if ("1".equals(request.getParameter("error"))) { %>
<div style="border: 1px solid darkgray; color: white; background: darkred; font-weight: bold; padding: 10px; margin: 5px">
    Invalid User Name/Password. Please Try Again.
</div>
<% }%>

<div align="center" style="border: 1px solid darkgray; background-color: #cccccc; font-size: 11px; padding: 15px;">
    <form name="loginform" method="post" action="j_security_check">
        <table class="leftmenu">
            <tbody>
                <tr class="leftmenu">
                    <th>User Name</th>
                    <td>
                        <input name="j_username" type="text"/>
                    </td>
                </tr>
                <tr class="leftmenu">
                    <th>Password</th>
                    <td>
                        <input name="j_password" type="password"/>
                    </td>
                </tr>
                <tr class="leftmenu">
                    <th/>
                    <td>
                        <input type="submit" value="Log In"/>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>
<script type="text/javascript" src="/css/footer.js" ></script>
</body>
</html>
