<%@ page import="org.erms.db.DataAccessManager,
                 org.erms.util.ERMSConstants,
                 org.erms.business.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>:: Tsunami ::</title>
<link href="comman/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<jsp:include page="comman/header.inc"></jsp:include>

   <%
       DataAccessManager dataAccessManager = new DataAccessManager();
       String username = request.getParameter("userName");
       String password = request.getParameter("password");

       User user = null;
       try {
           user = dataAccessManager.loginSuccess(username, password);
       } catch (Exception e) {
           e.printStackTrace();
           request.getSession().setAttribute(ERMSConstants.IContextInfoConstants.ERROR_DESCRIPTION, "Problem with validating login information");
           response.sendRedirect("error.jsp");
       }
       if( !"".equals(username) && user != null) {
           request.getSession().setAttribute(ERMSConstants.IContextInfoConstants.USER_INFO, user);
   %>

    <!--   -->
    <table width="100%" border="0" cellspacing="2" cellpadding="0" bgcolor="#D8E9FD">
              <tr>
                <td width="34%" class="formText" ><font size="2"><a href="Fulfill.jsp">Fulfill Request</a></font></td>
                <td width="66%"  >&nbsp;</td>
              </tr>
              <tr>
                <td class="formText"><font size="2"><a href="Add_Request.jsp">Add Request </a><font></font></td>
                <td >&nbsp;</td>
              </tr>
           <tr>
                <td class="formText"><font size="2"><a href="Search_Request.jsp">Search Request</a></font></td>
                <td >&nbsp;</td>
              </tr><tr>
              <!-- todo finish this with a proper link -->
                <td class="formText"><font size="2"><a href="Index.html">Log off</a><font></td>
                <td >&nbsp;</td>
              </tr>
               <!--   -->
            <%
           }else{  %>
                <tr>
                <td class="formText"  ><font size="2">Invalid Username / Password. Please <a href="Index.jsp">Try Again</a></font></td>
                <td >&nbsp;</td>
              </tr>
      <%
       }
      %>
      </table>

      <jsp:include page="comman/footer.inc"></jsp:include>
      </body>
</html>