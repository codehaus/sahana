<%@ page import="org.erms.db.DataAccessManager,
                 org.erms.util.ERMSConstants,
                 java.sql.SQLException,
                 org.erms.business.RequestTO,
                 org.erms.business.RequestDetailTO,
                 java.text.Format,
                 java.text.SimpleDateFormat,
                 org.erms.business.User" %>
<%@ page import="org.erms.business.KeyValueDTO" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.IOException" %>
<html>
<head>
<title>:: Tsunami ::</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language='javascript' src='commonControls/popupcal/popcalendar.js'></script>
<script language='javascript' src='comman/validate.js'></script>
<script>
//type=1 is full submit
//type = 2 is save
function validate(type){
 var quantitiyStr = document.form1.quantity.value;

 if (type==1){
    document.form1.submitType.value = "Save";
 }else if (type==2){
    document.form1.submitType.value = "AddList";
 }

 if (!testNumber(quantitiyStr)){
    alert("please put in a number for quantity");
    return;
 }else{
    document.form1.submit();
 }
}
</script>

<link href="comman/style.css" rel="stylesheet" type="text/css">
</head>

<body topmargin="0" leftmargin="0">

<jsp:include page="comman/header.inc"></jsp:include>

<form name="form1" action="Add_Request.jsp" method="post">
<input type="hidden" name="submitType"/>
<%
    RequestTO requestObj = (RequestTO) request.getSession().getAttribute(ERMSConstants.IContextInfoConstants.REQUEST_DTO);
    User user = (User) request.getSession().getAttribute(ERMSConstants.IContextInfoConstants.USER_INFO);

    if (user==null){
        request.getSession().setAttribute(ERMSConstants.IContextInfoConstants.ERROR_DESCRIPTION, "User not authenticated!");
        response.sendRedirect("error.jsp");
    }

    boolean isSaveRequest = false;
    //save code
    String submitType = request.getParameter("submitType");

    System.out.println("submitType = " + submitType);

    isSaveRequest = "Save".equals(submitType);

    if (isSaveRequest){

        if (requestObj==null){
            requestObj = new RequestTO();
        }

        requestObj.setCallerName(request.getParameter("callerName"));
        requestObj.setCallerContactNumber(request.getParameter("callerContact"));
        requestObj.setCallerAddress(request.getParameter("callerAddress"));
        requestObj.setSiteName(request.getParameter("siteName"));
        requestObj.setSiteDistrict(request.getParameter("districts"));
        requestObj.setUser(request.getParameter("user"));

        DataAccessManager dam = new DataAccessManager();
        dam.addRequest(requestObj);

        //reset the DTobject
        requestObj = new RequestTO();
        request.getSession().setAttribute(ERMSConstants.IContextInfoConstants.REQUEST_DTO, requestObj);

    }else if (requestObj==null){
        requestObj = new RequestTO();
        request.getSession().setAttribute(ERMSConstants.IContextInfoConstants.REQUEST_DTO, requestObj);
    }else{
        //just update from the values
        requestObj.setCallerName(request.getParameter("callerName"));
        requestObj.setCallerContactNumber(request.getParameter("callerContact"));
        requestObj.setCallerAddress(request.getParameter("callerAddress"));
        requestObj.setSiteName(request.getParameter("siteName"));
        requestObj.setSiteDistrict(request.getParameter("districts"));
        requestObj.setUser(request.getParameter("user"));

        //adding the list
        String quantityStr = request.getParameter("quantity");
        String priorityStr = request.getParameter("priorities");
        String catogories = request.getParameter("catogories");
        String requestID = requestObj.getRequestID();
        String units = request.getParameter("units");
        String item = request.getParameter("item");
        int quantity = Integer.parseInt("".equals(quantityStr)?"0":quantityStr==null?"0":quantityStr);

        RequestDetailTO requestDetailTO = new RequestDetailTO(null, requestID,catogories, null, units, quantity, item, priorityStr);
        requestObj.addRequestDetails(requestDetailTO);


    }



%>
<table width="760" border="0" cellspacing="0" cellpadding="0">

<tr>
<td>
<%--<table width="100%" border="0" cellspacing="0" cellpadding="0">--%>
<%--<tr>--%>
<%--<td width="10" height="24" class="statusBar"><img src="images/spacer.gif" width="10" height="10"></td>--%>
<%--<td width="110" class="statusBar"><a href="Fulfill.jsp">Fulfill request</a></td>--%>
<%--<td width="10" class="statusBar"><img src="images/spacer.gif" width="10" height="10"></td>--%>
<%--<td width="93" class="statusBar"><a href="Add_Request.jsp">Add Request </a></td>--%>
<%--<td width="10" class="statusBar"><img src="images/spacer.gif" width="10" height="10"></td>--%>
<%--<td width="449" class="statusBar"><a href="Search_Request.jsp">Search Request</a></td>--%>
<%--<td width="80" class="statusBar"><a href="Index.html">Log off</a></td>--%>
<%--</tr>--%>
<%--</table>--%>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr align="left">
          <td nowrap background="images/BannerBG.jpg" class="statusBar"><img src="images/Tab.gif" width="2" height="15"></td>
          <td nowrap background="images/BannerBG.jpg" class="statusBar"><a href="Fulfill.jsp" style="text-decoration:none"><font color="#000000">&nbsp;&nbsp;&nbsp;Fulfil
            Request</font></a>&nbsp;&nbsp;</td>
          <td nowrap background="images/BannerBG.jpg" class="statusBar"><img src="images/Tab.gif" width="2" height="15"></td>
          <td nowrap background="images/BannerBG.jpg" class="statusBar"><a href="Search_Request.jsp" style="text-decoration:none"><font color="#000000">&nbsp;&nbsp;Search
            Request</font></a>&nbsp;&nbsp;</td>
          <td nowrap background="images/BannerBG.jpg" class="statusBar"><img src="images/Tab.gif" width="2" height="15"></td>
          <td width="100%" height="23" align="right" background="images/BannerBG.jpg" bgcolor="#000099" class="statusBar"><a href="Index.jsp" style="text-decoration:none"><font color="#000000">Log
            off&nbsp;&nbsp;&nbsp;&nbsp;</font></a></td>
        </tr>
      </table>
</td>
</tr>
<tr>
<td><table width="760" border="0" cellspacing="0" cellpadding="0">
<tr>
<%--<td width="134" height="350" valign="top" class="leftMenuBG"><img src="images/spacer.gif" width="160" height="10"></td>--%>
 <td><img src="images/Blank.gif" width="10" height="10"></td><td width="100%">&nbsp;</td>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td height="25" colspan="2" class="formTitle">Add request  </td>
</tr>
<tr>
<td width="117">&nbsp;</td>
<td width="484">&nbsp;</td>
</tr>
<tr>
<td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="19%" class="formText">&nbsp;Organization </td>
<td width="19%"  class="formText" align="center" ><%=user.getOrganization()%></td>
<td class="formText">User</td>
<td class="formText" align="center"><%=user.getUserName()%></td>
<td class="formText">Date</td>
<%
    Format formatter = new SimpleDateFormat("MM/dd/yy");
    String s = formatter.format(new java.util.Date());
%>
<td width="25%"  class="formText" align="center" disabled="true"><%=s%></td>
</tr>
</table></td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>
<td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>

<td width="20%" class="formText">&nbsp;Request Date</td>
<td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="16%"><input type="text" name="txtMDate" class="textBox" disabled="true" id="txtMDate1" value="<%=requestObj.getRequestedDate()%>" >
</td>
<td width="82%"><img src="Images/calendar.gif" onClick="popUpCalendar(this, document.getElementById('txtMDate1'), 'mm/dd/yyyy')" width="18" height="17"></td>
</tr>
</table></td>
</tr>
<tr>
<td class="formText">&nbsp;Caller Name</td>
<td colspan="4"><input type="text" name="callerName" class="textBox" value="
<%=requestObj.getCallerName()%>" ></td>
</tr>
<tr>
<td class="formText">&nbsp;Caller Contact</td>
<td colspan="4"><input type="text" name="callerContact" class="textBox" value="
<%=requestObj.getCallerContactNumber()%>" ></td>
</tr>
<tr>
<td class="formText">&nbsp;Caller Address</td>
<td colspan="4"><input type="text" name="callerAddress" class="textBox" value="
<%=requestObj.getCallerAddress()%>"></td>
</tr>
<tr>
<td>&nbsp;</td>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td class="formText">&nbsp;Site Name</td>
<td colspan="4"><input type="text" name="siteName" class="textBox" value="
<%=requestObj.getSiteName()%>"></td>
</tr>
<tr>
<td class="formText">&nbsp;Site District </td>
<td colspan="4"><select name="districts" class="textBox">
                   <%
                       DataAccessManager dam = new DataAccessManager();
                       Collection districtList=null;
                       try{
                           districtList =  dam.getAllDistricts();
                           if(districtList != null){
                               Iterator ditr = districtList.iterator();
                               while(ditr.hasNext()){
                                   KeyValueDTO district = (KeyValueDTO) ditr.next();
                                   String districtCode = district.getDbTableCode();
                                   String districtvalue = district.getDisplayValue();
                                   if (requestObj.getSiteDistrict().equals(districtCode)){
                   %>
                   <option selected value="<%=districtCode%>"><%=districtvalue%></option>
                                       <%
                                   }else{
                                       %>
                                       <option value="<%=districtCode%>"><%=districtvalue%></option>
                                       <%
                                   }
                               }
                           }
                       }catch(Exception e) {
                           out.print("Exception occurd "+e);
                       }
                                       %>
                                       </select></td>
                                       </tr>
                                       <tr>
                                       <td class="formText">&nbsp;Site Area</td>
                                       <td colspan="4"><input type="text" name="siteArea" class="textBox" value="<%=
                                           requestObj.getSiteArea()
                                       %>" ></td>
                                       </tr>

<tr>
<td>&nbsp;</td>
<td colspan="4">&nbsp;</td>
</tr>
<tr>
<td height="22" class="formText">&nbsp;Category</td>
<td colspan="4"><select name="catogories" class="textBox">
                   <%
                       Collection catogoryList=null;
                       try{
                           catogoryList =  dam.getAllCategories();
                           if(catogoryList != null){
                               Iterator citr = catogoryList.iterator();
                               while(citr.hasNext()){
                                   KeyValueDTO catogory = (KeyValueDTO) citr.next();
                                   String catogoryCode = catogory.getDbTableCode();
                                   String catogoryvalue = catogory.getDisplayValue();

                   %>
                   <option value="<%=catogoryCode%>"><%=catogoryvalue%></option>                      <%
                               }
                           }
                       }catch(Exception e) {
                           out.print("Exception occurd "+e);
                       }
                   %>
                   </select></td>
                   </tr>
                   <tr>
                   <td class="formText">&nbsp;Item</td>
                   <td colspan="4"><input type="text" name="item" class="textBox"></td>
                   </tr>
                   <tr>
                   <td class="formText">&nbsp;Units</td>
                   <td width="17%"><input type="text" name="units" class="textBox"></td>
                   <td width="10%" align="center" class="formText">Quantity</td>
                   <td width="18%"><input type="text" name="quantity" class="textBox"></td>
                   <td width="35%"><input name="Submit1" type="button" value="Add to List" class="buttons" onclick="validate(2)" ></td>
                   </tr>
                   <tr>
                   <td class="formText">&nbsp;Priority</td>
                   <td colspan="4"><select name="priorities" class="textBox">
                   <%
                       Collection priorityList=null;
                       try{
                           priorityList =  dam.getAllPriorities();
                           if(priorityList != null){
                               Iterator pitr = priorityList.iterator();
                               while(pitr.hasNext()){
                                   KeyValueDTO priorityDTO = (KeyValueDTO) pitr.next();
                                   String priorityCode = priorityDTO.getDbTableCode();
                                   String priorityvalue = priorityDTO.getDisplayValue();%>
                                   <option value="<%=priorityCode%>"><%=priorityvalue%></option>
                      <%
                               } }
                       }catch(Exception e) {
                           out.print("Exception occurd "+e);
                       }
                      %>
                      </select></td>
                      </tr>
                      </table></td>
                      </tr>
                      <tr>
                      <td>&nbsp;</td>
                      <td colspan="4">&nbsp;</td>
                      </tr>
                      <tr>
                      <td colspan="2"><div id="Layer1" style="position:auto; left:150px; top:279px; width:100%; height:200px; z-index:1; overflow: auto;overflow-x">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<th  class="formTitle">
<td height="25" class="formText" class="formText">Catogory</td>
<td class="formText">Item</td>
<td class="formText">Units</td>
<td class="formText">Quantity</td>
<td class="formText">Priority</td>
</th>
<%
    Collection requestDetails = requestObj.getRequestDetails();
    Iterator iterator = requestDetails.iterator();
    RequestDetailTO    tempRequestDetailDto;
    while (iterator.hasNext()) {
        tempRequestDetailDto =(RequestDetailTO) iterator.next();
%>
<tr>
<td class="formText" height="25" align="center"  ><%=tempRequestDetailDto.getCategory()%></td>
<td  class="formText" align="center" ><%=tempRequestDetailDto.getItem()%></td>
<td  class="formText" align="center" ><%=tempRequestDetailDto.getUnit()%></td>
<td  class="formText" align="center" ><%=tempRequestDetailDto.getQuantity()%></td>
<td  class="formText" align="center" ><%=tempRequestDetailDto.getPriority()%></td>
</tr>
        <%
    }
        %>

        <tr height="3">
        <td colspan="5">
</td>
</tr>

</table>

</div></td>
</tr>
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<tr>

<td class="formTitle" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="73%" height="30">&nbsp;</td>
<td width="12%" align="right"><input type="button" name="Submit2" value="Save" class="buttons" onclick="validate(1)" >&nbsp;</td>
</tr>
</table></td>
</tr>
<tr>
<td colspan="2" class="pageBg">&nbsp;</td>
</tr>
</table>
</tr>
</table></td>
</tr>
<tr>
<td><table width="760" border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="statusBar">&nbsp;</td>
</tr>
</table></td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</form>
</body>
</html>
