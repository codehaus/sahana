<%@ page import="org.erms.util.ERMSConstants"%><html>
<head>
<title>:: Tsunami ::</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="comman/style.css" rel="stylesheet" type="text/css">
</head>

<body>

 <%
     out.println("An error has occured. Error ==> "+ request.getSession().getAttribute(ERMSConstants.IContextInfoConstants.ERROR_DESCRIPTION));
 %>
</body>