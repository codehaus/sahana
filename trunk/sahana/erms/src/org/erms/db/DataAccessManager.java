/*
* Created on Dec 30, 2004
*
* To change the template for this generated file go to
* Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
*/
package org.erms.db;

import org.erms.business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * <p/>
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DataAccessManager {

    private static Collection allOrganizationNames = null;
    private static Collection allDistricts = null;
    private static Collection allCategories = null;
    private static Collection allPriorities = null;
    private static Collection allStatuses = null;

    public DataAccessManager() {

    }

    public Collection getAllCategories() throws SQLException, Exception {
        if (allCategories == null) {
            allCategories = loadAllCategories();
        }

        return allCategories;
    }

    private static Collection loadAllCategories() throws SQLException, Exception {

        Connection conn = DBConnection.createConnection();
        List categoryDTOs = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLForAllCategories();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);

            String itemCode = null;
            String itemName = null;
            KeyValueDTO dto = null;

            while (rs.next()) {
                itemCode = rs.getString(DBConstants.TableColumns.CAT_CODE);
                itemName = rs.getString(DBConstants.TableColumns.CAT_DESCRIPTION);
                dto = new KeyValueDTO();
                dto.setDbTableCode(itemCode);
                dto.setDisplayValue(itemName);
                categoryDTOs.add(dto);
            }
        }
        finally {
            conn.close();
        }

        return categoryDTOs;
    }

    public Collection getAllPriorities() throws SQLException, Exception {
        if (allPriorities == null) {
            allPriorities = loadAllPriorities();
        }

        return allPriorities;
    }

    private Collection loadAllPriorities() throws SQLException, Exception {
        Connection conn = DBConnection.createConnection();
        List priorityDTOs = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLForAllPriorities();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            String itemCode = null;
            String itemName = null;
            KeyValueDTO dto = null;

            while (rs.next()) {
                itemCode = rs.getString(DBConstants.TableColumns.PRIORITY_LEVEL);
                itemName = rs.getString(DBConstants.TableColumns.PRIORITY_DESCRIPTION);
                dto = new KeyValueDTO();
                dto.setDbTableCode(itemCode);
                dto.setDisplayValue(itemName);
                priorityDTOs.add(dto);
            }
        }
        finally {
            conn.close();
        }

        return priorityDTOs;
    }


    /**
     * Method to add a request.
     *
     * @param request The Request value object which will contain the data that needs to be saved.
     * @throws Exception
     */
    public void addRequest(RequestTO request) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.createConnection();
            connection.setAutoCommit(false);

            // Setting the Request Header data.
            String sqlAddRequestHeaderString = SQLGenerator.getSQLAddRequestHeader();

            preparedStatement = connection.prepareStatement(sqlAddRequestHeaderString);
            preparedStatement.setString(1, request.getOrganization());
            preparedStatement.setDate(2, request.getCreateDate());
            preparedStatement.setDate(3, request.getRequestedDate());
            preparedStatement.setString(4, request.getCallerName());
            preparedStatement.setString(5, request.getCallerAddress());
            preparedStatement.setString(6, request.getCallerContactNumber());
            preparedStatement.setString(7, request.getDescription());
            preparedStatement.setString(8, request.getSiteType());
            preparedStatement.setString(9, request.getSiteDistrict());
            preparedStatement.setString(10, request.getSiteArea());
            preparedStatement.setString(11, request.getSiteName());

            preparedStatement.executeUpdate();

            // Getting the generated Key from the above statement
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            String generatedKey = new String();

            if (generatedKeys.next()) // We're expecting just one auto genrated key
            {
                generatedKey = generatedKeys.getObject(1).toString();
            }

            String sqlAddRequestDetailString = SQLGenerator.getSQLAddRequestDetail();

            // Now we're going to add the detail records.
            Collection requestDetails = request.getRequestDetails();

            for (Iterator iterator = requestDetails.iterator(); iterator.hasNext();) {
                RequestDetailTO requestDetailTO = (RequestDetailTO) iterator.next();

                preparedStatement = connection.prepareStatement(sqlAddRequestDetailString);
                preparedStatement.setString(1, generatedKey);
                preparedStatement.setString(2, requestDetailTO.getCategory());
                preparedStatement.setString(3, requestDetailTO.getItem());
                preparedStatement.setString(4, requestDetailTO.getDescription());
                preparedStatement.setString(5, requestDetailTO.getUnit());
                preparedStatement.setInt(6, requestDetailTO.getQuantity());
                preparedStatement.setString(7, requestDetailTO.getPriority());

                preparedStatement.executeUpdate();

            }

            // If all is ok then commit.
            connection.commit();
            connection.setAutoCommit(true);
        }
        catch (Exception e) {
            System.out.println("Exception!!!");
            try {
                if (connection != null)
                // Since an exception has occured lets roll back the partially inserted record.
                {
                    System.out.println("About to rollback");
                    connection.rollback();
                    connection.setAutoCommit(true);
                    System.out.println("After roll back");
                }
            }
            catch (SQLException e1) {
                System.out.println("rollback exception");
                e1.printStackTrace();
                throw e1;
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
    }


    public Collection getAllOrganizationNames() throws SQLException, Exception {
        if (allOrganizationNames == null) {
            allOrganizationNames = loadAllOrganizationNames();
        }

        return allOrganizationNames;
    }

    private Collection loadAllOrganizationNames() throws SQLException, Exception {
        Connection conn = DBConnection.createConnection();
        Collection orgDTOs = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLForAllOrganizationNames();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            String itemCode = null;
            String itemName = null;
            KeyValueDTO dto = null;

            while (rs.next()) {
                itemCode = rs.getString(DBConstants.TableColumns.ORG_CODE);
                itemName = rs.getString(DBConstants.TableColumns.ORG_NAME);
                dto = new KeyValueDTO();
                dto.setDbTableCode(itemCode);
                dto.setDisplayValue(itemName);
                orgDTOs.add(dto);
            }
        }
        finally {
            conn.close();
        }

        return orgDTOs;
    }

    public Collection getAllDistricts() throws SQLException, Exception {
        if (allDistricts == null) {
            allDistricts = loadAllDistricts();
        }

        return allDistricts;
    }

    private Collection loadAllDistricts() throws SQLException, Exception {
        Connection conn = DBConnection.createConnection();
        List districtDTOs = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLForAllDistricts();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            String itemCode = null;
            String itemName = null;
            KeyValueDTO dto = null;

            while (rs.next()) {
                itemCode = rs.getString(DBConstants.TableColumns.DISTRICT_CODE);
                itemName = rs.getString(DBConstants.TableColumns.NAME);
                dto = new KeyValueDTO();
                dto.setDbTableCode(itemCode);
                dto.setDisplayValue(itemName);
                districtDTOs.add(dto);
            }
        }
        finally {
            conn.close();
        }

        return districtDTOs;
    }

    public Collection getAllStatuses() throws SQLException, Exception {
        if (allStatuses == null) {
            allStatuses = loadAllStatuses();
        }

        return allStatuses;
    }

    private Collection loadAllStatuses() throws SQLException, Exception {
        Connection conn = DBConnection.createConnection();
        List statusDTOs = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLForAllStatuses();
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            String itemCode = null;
            String itemName = null;
            KeyValueDTO dto = null;

            while (rs.next()) {
                itemCode = rs.getString(DBConstants.TableColumns.STATUS);
                itemName = rs.getString(DBConstants.TableColumns.DESCRIPTION);
                dto = new KeyValueDTO();
                dto.setDbTableCode(itemCode);
                dto.setDisplayValue(itemName);
                statusDTOs.add(dto);
            }
        }
        finally {
            conn.close();
        }

        return statusDTOs;
    }

    /**
     * This method returns the Request which matches the passed requestdetailID.
     *
     * @param requestDetailID
     * @return An Arraylist, where the first object would be the RequestTO value object and the second object would be
     *         the services (if any) who are fulfilling the request.
     */
    public List getRequest(String requestDetailID) throws SQLException, Exception {

        if (requestDetailID == null || Integer.parseInt(requestDetailID) == 0)
            throw new Exception("The request detail ID is null");

        Connection connection = DBConnection.createConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        RequestTO requestTO = new RequestTO();
        RequestDetailTO requestDetailTO = new RequestDetailTO();
        RequestFulfillDetailTO requestFulfillDetailTO = new RequestFulfillDetailTO();
        List returnTOList = new ArrayList();

        try {
            String sql = SQLGenerator.getSQLGetRequestDetail(requestDetailID);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                requestDetailTO.setRequestDetailID(resultSet.getString(DBConstants.TableColumns.REQUEST_DETAIL_ID));
                requestDetailTO.setRequestID(resultSet.getString(DBConstants.TableColumns.REQUEST_ID));
                requestDetailTO.setCategory(resultSet.getString(DBConstants.TableColumns.CATEGORY));
                requestDetailTO.setItem(resultSet.getString(DBConstants.TableColumns.ITEM));
                requestDetailTO.setDescription(resultSet.getString(DBConstants.TableColumns.DESCRIPTION));
                requestDetailTO.setUnit(resultSet.getString(DBConstants.TableColumns.UNIT));
                requestDetailTO.setQuantity(resultSet.getInt(DBConstants.TableColumns.QUANTITY));
                requestDetailTO.setPriority(resultSet.getString(DBConstants.TableColumns.PRIORITY_DESCRIPTION));

                requestTO.setRequestID(resultSet.getString(DBConstants.TableColumns.REQUEST_ID));
                requestTO.setOrganization(resultSet.getString(DBConstants.TableColumns.ORG_CODE));
                requestTO.setCreateDate(resultSet.getDate(DBConstants.TableColumns.CREATE_DATE));
                requestTO.setRequestedDate(resultSet.getDate(DBConstants.TableColumns.REQUEST_DATE));
                requestTO.setCallerName(resultSet.getString(DBConstants.TableColumns.CALLER_NAME));
                requestTO.setCallerAddress(resultSet.getString(DBConstants.TableColumns.CALLER_ADDRESS));
                requestTO.setCallerContactNumber(resultSet.getString(DBConstants.TableColumns.CALLER_CONTACT_NO));
                requestTO.setDescription(resultSet.getString(DBConstants.TableColumns.DESCRIPTION));
                requestTO.setSiteType(resultSet.getString(DBConstants.TableColumns.SITE_TYPE));
                requestTO.setSiteDistrict(resultSet.getString(DBConstants.TableColumns.SITE_DISTRICT));
                requestTO.setSiteArea(resultSet.getString(DBConstants.TableColumns.SITE_AREA));
                requestTO.setSiteName(resultSet.getString(DBConstants.TableColumns.SITE_NAME));

                List requestDetailTOs = new ArrayList();
                requestDetailTOs.add(requestDetailTO);

                requestTO.setRequestDetails(requestDetailTOs);

                returnTOList.add(requestTO);

                // Now get the services who have serviced this request.
                String sqlFulfillServices = SQLGenerator.getSQLGetServiceDetailsForRequest(requestDetailID);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlFulfillServices);

                List servicerList = new ArrayList();

                while (resultSet.next()) {
                    requestFulfillDetailTO = new RequestFulfillDetailTO();
                    requestFulfillDetailTO.setOrgCode(resultSet.getString(DBConstants.TableColumns.ORG_CODE));
                    requestFulfillDetailTO.setOrgContact(resultSet.getString(DBConstants.TableColumns.ORG_ADDRESS));
                    requestFulfillDetailTO.setOrgName(resultSet.getString(DBConstants.TableColumns.ORG_NAME));
                    requestFulfillDetailTO.setQuantity(resultSet.getString(DBConstants.TableColumns.SERVICE_QTY));
                    requestFulfillDetailTO.setStatus(resultSet.getString(DBConstants.TableColumns.STATUS));

                    servicerList.add(requestFulfillDetailTO);
                }

                returnTOList.add(servicerList);
            }
        }
        finally {
            closeConnections(connection, statement, resultSet);
        }

        return returnTOList;
    }

    public Collection getFullFillDetails(String id) {
        return null;
    }

    /**
     * This method will save the fullfilled request.
     *
     * @param fullFillment The Fulfillment value object which has the data which needs to be saved.
     * @throws Exception
     */
    public void fulfillRequest(RequestFulfillTO fullFillment) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.createConnection();
            connection.setAutoCommit(false);

            // Setting the Request Header data.
            String sqlAddFulFillRequestString = SQLGenerator.getSQLAddFulFillRequest();

            preparedStatement = connection.prepareStatement(sqlAddFulFillRequestString);

            preparedStatement.setString(1, fullFillment.getOrganization());
            preparedStatement.setString(2, fullFillment.getRequestDetailID());
            preparedStatement.setString(3, fullFillment.getServiceQuantity());
            preparedStatement.setString(4, fullFillment.getStatus());

            preparedStatement.executeUpdate();

            // If all is ok then commit.
            System.out.println("Before commit");
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("After commit");
        }
        catch (Exception e) {
            System.out.println("Exception!!!");
            try {
                if (connection != null)
                // Since an exception has occured lets roll back the partially inserted record.
                {
                    System.out.println("About to rollback");
                    connection.rollback();
                    connection.setAutoCommit(true);
                    System.out.println("After roll back");
                }
            }
            catch (SQLException e1) {
                System.out.println("rollback exception");
                e1.printStackTrace();
                throw e1;
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
    }

    /**
     * This class is still in progress... The SQL statement in the SQLgenerator has to be completed with the appropriate search fields and select fields.
     * Similarly this method woudl have to be updated with the correct set statements.
     *
     * @param searchCriteria
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public List searchRequests(RequestSearchCriteriaTO searchCriteria) throws SQLException, Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.createConnection();
            connection.setAutoCommit(false);

            // Setting the Request Header data.
            String sqlSearchString = SQLGenerator.getSQLForSearchCriteria();

            preparedStatement = connection.prepareStatement(sqlSearchString);

            preparedStatement.setString(1, searchCriteria.getCategory());
            preparedStatement.setString(2, searchCriteria.getCategory());

            resultSet = preparedStatement.executeQuery();

            List returnSearchTOs = new ArrayList();
            RequestSearchTO requestSearchTo = new RequestSearchTO();

            while (resultSet.next()) {
                requestSearchTo = new RequestSearchTO();
                requestSearchTo.setItem(resultSet.getString(DBConstants.TableColumns.ITEM));
                returnSearchTOs.add(requestSearchTo);
            }

            return returnSearchTOs;
        }
        finally {
            closeConnections(connection, preparedStatement, resultSet);
        }
    }

    public void addOrganization(OrganizationRegistrationTO org) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

       try {
           try {
               connection = DBConnection.createConnection();
           } catch (Exception e) {
               throw new SQLException(e.getMessage());
           }
           preparedStatement = connection.
                    prepareStatement(SQLGenerator.getSQLForOrganizationRegistration());
            int i = 1;
            preparedStatement.setString(i++, org.getOrgCode() );
            preparedStatement.setString(i++, org.getContactPerson());
            preparedStatement.setString(i++, org.getOrgName());
            preparedStatement.setBoolean(i++, Boolean.getBoolean(org.getStatus()));
            preparedStatement.setString(i++, org.getOrgAddress());
            preparedStatement.setString(i++, org.getContactNumber());
            preparedStatement.setString(i++, org.getEmailAddress());
            preparedStatement.setString(i++, org.getCountryOfOrigin());
            preparedStatement.setString(i++, org.getFacilitiesAvailable());
            preparedStatement.setString(i++, org.getWorkingAreas());
            preparedStatement.setString(i++, org.getComments());
            preparedStatement.executeUpdate();
       }
        finally{
           closeStatement(preparedStatement);
           closeConnection(connection);
       }


    }

    /**
     * This method is purely for testing purposes.
     *
     * @param args
     */
    public static void main
            (String[] args) {
        DataAccessManager app = new DataAccessManager();
        try {
            Collection c1 = app.getAllCategories();
            Collection c2 = app.getAllDistricts();
            Collection c3 = app.getAllOrganizationNames();
            Collection c4 = app.getAllPriorities();
            Collection c5 = app.getAllStatuses();

            System.out.println("done");
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void closeResultSet
            (ResultSet
            resultSet) {
        // close the result set
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeConnection
            (Connection
            connection) {
        // close the connection
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes the open connections
     *
     * @param connection
     * @param resultSet
     */
    private static void closeConnections
            (Connection
            connection, Statement
            statement, ResultSet
            resultSet) {
        closeConnection(connection);

        closeStatement(statement);

        closeResultSet(resultSet);

    }

    private static void closeStatement
            (Statement
            statement) {
        // close the statement
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User loginSuccess(String userName, String password)throws SQLException,Exception{
        Connection conn = DBConnection.createConnection();
        try {
            String sql = SQLGenerator.getSQLForLogin(userName);
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String realpassword = rs.getString(DBConstants.TableColumns.PASSWORD);
                String organization = rs.getString(DBConstants.TableColumns.ORGANIZATION);

                User user = new User(userName, organization);
                if(realpassword.equals(password)){
                    return user;
                }
            }
        }
        finally {
            conn.close();
        }

        return null;
    }

    public boolean hasOrgAlreadyRegisterd(String orgCode) throws Exception {
        Connection conn = DBConnection.createConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
         try {
             String sql = SQLGenerator.getSQLForCountOrganizationCode();
             ps = conn.prepareStatement(sql);
             ps.setString(1, orgCode);
             rs = ps.executeQuery();
             if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
             }
             return false;
         }
         finally {
             closeResultSet(rs);
             closeStatement(ps);
             closeConnection(conn);
         }
    }

}
