<%@ page import="org.erms.business.OrganizationRegistrationTO,
                 org.erms.db.DataAccessManager,
                 java.util.List,
                 java.util.LinkedList,
                 java.util.Iterator"%>
<jsp:useBean id="orgReg" scope="request" class="org.erms.business.OrganizationRegistrationTO" />
<jsp:setProperty name="orgReg" property="*" />
<%
    List  messages = new LinkedList();
    if (request.getParameter("submit") != null) {
        DataAccessManager dataAccessManager = new DataAccessManager();
        orgReg.setStatus("false");
        messages.addAll(orgReg.validate());
        if (dataAccessManager.hasOrgAlreadyRegisterd(orgReg.getOrgCode())) {
           messages.add(orgReg.getOrgCode() + " : Organization has already been registerd.");
        }
        if (messages.size() <= 0) {
            dataAccessManager.addOrganization(orgReg);
%>
            <p align="center">
                <h2>You have been successfully registerd. Your User Name & Password will be e-mailed shortly.</h2>
            </p>
            <a href="<%=request.getContextPath()%>/Welcome.jsp">Back to Main Page.</a>
<%
            return;
        }
    }
%>


    <form method="post" name="tsunamiOrgReg" action="Registration.jsp">
          <table border="0" width="100%" cellspacing="1" cellpadding="1">
          <% if (messages.size() > 0) { %>
            <tr>
              <td>&nbsp;</td>
              <td vAlign="top" ><p align="left"><font size="2"><font face="Arial" color="red">Please Correct Following Errors :</font></font><br/>

                    <%
                        for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
                            String s = (String) iterator.next();
                    %>
                        <li><font size="1" face="Arial" color="red"><%=s%></font><br/></li>
                    <%
                        }
                    %>
                </td>
            </tr>
            <% } %>
            <tr>
              <td vAlign="top"><p align="right"><font size="2"><font face="Arial" color="#000000">Organisation
                  Code :</font></font></td>
              <td><font face="Arial" color="#F0C468">
                <input name="orgCode" size="38" maxlength="10" type="text" id="Code" value="<jsp:getProperty name="orgReg" property="orgCode" />">
                </font></td>
            </tr>
            <tr>
              <td vAlign="top"><p align="right"><font size="2"><font face="Arial" color="#000000">Organisation
                  Name :</font></font></td>
              <td><font face="Arial" color="#F0C468">
                <input name="orgName" maxlength="99" size="38" type="text" id="Name" value="<jsp:getProperty name="orgReg" property="orgName" />">
                </font></td>
            </tr>
            <tr>
              <td vAlign="top"><div align="right"><font size="2"><font face="Arial" color="#000000">Contact
                  Person :</font></font></div></td>
              <td><font face="Arial" color="#F0C468">
                <input name="contactPerson" maxlength="99" size="38" type="text" id="contactperson" value="<jsp:getProperty name="orgReg" property="contactPerson" />">
                </font></td>
            </tr>
            <tr>
              <td vAlign="top"><p align="right"><font size="2"><font face="Arial" color="#000000">Address
                  :</font></font></td>
              <td><font face="Arial" color="#F0C468">
                <textarea cols="38" name="orgAddress" rows="5" id="contact"><jsp:getProperty name="orgReg" property="orgAddress" /></textarea>
                </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font size="2" face="Arial, Helvetica, sans-serif">Contact
                No :</font></td>
              <td><font face="Arial" color="#F0C468">
                <input name="contactNumber" maxlength="99" size="38" type="text" id="contactno" value="<jsp:getProperty name="orgReg" property="contactNumber" />">
                </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font size="2" face="Arial, Helvetica, sans-serif">Email
                Address :</font></td>
              <td><font face="Arial" color="#F0C468">
                <input name="emailAddress" maxlength="99" size="38" type="text" id="email" value="<jsp:getProperty name="orgReg" property="emailAddress" />">
                </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font size="2" face="Arial, Helvetica, sans-serif">Country
                of origin :</font></td>
              <td><font face="Arial" color="#F0C468">
                <input name="countryOfOrigin" maxlength="99" size="38" type="text" id="country" value="<jsp:getProperty name="orgReg" property="countryOfOrigin" />">
                </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font color="#000000" size="2" face="Arial">Facilities
                available :</font></td>
              <td><font face="Arial" color="#F0C468">
                <textarea cols="38" name="facilitiesAvailable" rows="5" id="Facilities"><jsp:getProperty name="orgReg" property="facilitiesAvailable" /></textarea>
                </font><font face="Arial">&nbsp; </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font size="2"><font face="Arial" color="#000000">Working
                areas :</font><font face="Arial"> </font></font></td>
              <td><font face="Arial">
                <textarea name="workingAreas" cols="38" rows="5" id="workarea"><jsp:getProperty name="orgReg" property="workingAreas" /></textarea>
                </font></td>
            </tr>
            <tr>
              <td align="right" vAlign="top"><font color="#000000" size="2"><font face="Arial">Comments
                :</font></font></td>
              <td><font face="Arial">
                <textarea name="comments" cols="38" rows="5" id="comments"><jsp:getProperty name="orgReg" property="comments" /></textarea>
                </font></td>
            </tr>
            <tr>
              <td colspan="2" align="right" vAlign="top">&nbsp;</td>
            </tr>
            <tr>
              <td align="right" vAlign="top">&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td></td>
              <td><font face="Arial">
                <input name="submit" type="submit" value=" Send ">
                </font><font face="Arial">
                <input name="reset" type="reset" value="Cancel">
                </font></td>
            </tr>
          </table>
        </form>



