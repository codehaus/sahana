/*
 * Created on Dec 30, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.erms.db;

/**
 * @author Administrator
 * <p/>
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SQLGenerator {

    public static String getSQLForAllDistricts() {
        String s = "SELECT *"
            + " FROM " + DBConstants.Tables.DISTRICT;
        return s;
    }

    public static String getSQLForAllCategories() {
        String s = "SELECT *"
            + " FROM " + DBConstants.Tables.CATOGORY;
        return s;
    }

    public static String getSQLForAllPriorities() {
        String s = "SELECT *"
            + " FROM " + DBConstants.Tables.PRIORITIES;
        return s;
    }


    public static String getSQLForLogin(String userName) {
        String s = "SELECT *"
            + " FROM " + DBConstants.Tables.USERS + " WHERE "+DBConstants.TableColumns.USER_NAME+"='"+userName+"'";
        return s;
    }

    public static String getSQLForAllOrganizationNames() {
        return "SELECT "
            + DBConstants.TableColumns.ORG_CODE + ","
            + DBConstants.TableColumns.ORG_NAME
            + "FROM"
            + DBConstants.Tables.ORGANIZATION;
    }

    public static String getSQLForAllStatuses() {
        return "SELECT "
            + DBConstants.TableColumns.STATUS + ","
            + DBConstants.TableColumns.DESCRIPTION
            + "FROM"
            + DBConstants.Tables.FULFILL_STATUS;
    }

    /**
     * returns the sql statement for inserting records into the request header
     *
     * @return
     */
    public static String getSQLAddRequestHeader() {
        return "INSERT into "
            + DBConstants.Tables.REQUEST_HEADER
            + " ("
            + DBConstants.TableColumns.ORG_CODE + ", "
            + DBConstants.TableColumns.CREATE_DATE + ", "
            + DBConstants.TableColumns.REQUEST_DATE + ", "
            + DBConstants.TableColumns.CALLER_NAME + ", "
            + DBConstants.TableColumns.CALLER_ADDRESS + ", "
            + DBConstants.TableColumns.CALLER_CONTACT_NO + ", "
            + DBConstants.TableColumns.DESCRIPTION + ", "
            + DBConstants.TableColumns.SITE_TYPE + ", "
            + DBConstants.TableColumns.SITE_DISTRICT + ", "
            + DBConstants.TableColumns.SITE_AREA + ", "
            + DBConstants.TableColumns.SITE_NAME
            + ") "
            + " VALUES "
            + "(?,?,?,?,?,?,?,?,?,?,?)";
    }

    /**
     * returns the sql statement for inserting records into the request detail
     *
     * @return
     */
    public static String getSQLAddRequestDetail() {
        return "INSERT into "
            + DBConstants.Tables.REQUEST_DETAIL
            + " ("
            + DBConstants.TableColumns.REQUEST_ID + ", "
            + DBConstants.TableColumns.CATEGORY + ", "
            + DBConstants.TableColumns.ITEM + ", "
            + DBConstants.TableColumns.DESCRIPTION + ", "
            + DBConstants.TableColumns.UNIT + ", "
            + DBConstants.TableColumns.QUANTITY + ", "
            + DBConstants.TableColumns.PRIORITY_LEVEL
            + ") "
            + " VALUES "
            + "(?,?,?,?,?,?,?)";
    }

    /**
     * returns the sql statement for inserting records into the request detail
     *
     * @return
     */
    public static String getSQLAddFulFillRequest() {
        return "INSERT into "
            + DBConstants.Tables.REQUEST_FULFILL
            + " ("
            + DBConstants.TableColumns.ORG_CODE + ", "
            + DBConstants.TableColumns.REQUEST_DETAIL_ID + ", "
            + DBConstants.TableColumns.SERVICE_QTY + ", "
            + DBConstants.TableColumns.STATUS
            + ") "
            + " VALUES "
            + "(?,?,?,?)";
    }

    /**
     * Returns the query which will get the request detail To as well the header information.
     *
     * @return
     */
    public static String getSQLGetRequestDetail(String requestDetailID) {

        final String REQ_HEAD = "req_head";
        final String REQ_DET = "req_detail";

        return "SELECT "
            // first the detail table details
            + REQ_DET + "." + DBConstants.TableColumns.REQUEST_DETAIL_ID + ", "
            + REQ_DET + "." + DBConstants.TableColumns.REQUEST_ID + ", "
            + REQ_DET + "." + DBConstants.TableColumns.CATEGORY + ", "
            + REQ_DET + "." + DBConstants.TableColumns.ITEM + ", "
            + REQ_DET + "." + DBConstants.TableColumns.DESCRIPTION + ", "
            + REQ_DET + "." + DBConstants.TableColumns.UNIT + ", "
            + REQ_DET + "." + DBConstants.TableColumns.QUANTITY + ", "
            + REQ_DET + "." + DBConstants.TableColumns.PRIORITY_DESCRIPTION + ", "
            // now the header details
            + REQ_HEAD + "." + DBConstants.TableColumns.REQUEST_ID + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.ORG_CODE + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.CREATE_DATE + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.REQUEST_DATE + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.CALLER_NAME + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.CALLER_ADDRESS + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.CALLER_CONTACT_NO + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.DESCRIPTION + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.SITE_TYPE + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.SITE_DISTRICT + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.SITE_AREA + ", "
            + REQ_HEAD + "." + DBConstants.TableColumns.SITE_NAME

            + " FROM "
            + DBConstants.Tables.REQUEST_DETAIL + " as " + REQ_DET + ", "
            + DBConstants.Tables.REQUEST_HEADER + " as " + REQ_HEAD

            + " WHERE "
            + REQ_DET + "." + DBConstants.TableColumns.REQUEST_DETAIL_ID + "=" + requestDetailID
            + " AND "
            + REQ_DET + "." + DBConstants.TableColumns.REQUEST_ID + "="
            + REQ_HEAD + "." + DBConstants.TableColumns.REQUEST_ID;

    }

    /**
     * Returns the query which will return all the services for that particular request.
     *
     * @return
     */
    public static String getSQLGetServiceDetailsForRequest(String requestDetailID) {

        final String ORG = "ORG";
        final String FULFILL = "FULFILL";

        return "SELECT "
            // first the detail table details
            + ORG + "." + DBConstants.TableColumns.ORG_CODE + ", "
            + ORG + "." + DBConstants.TableColumns.ORG_NAME + ", "
            + ORG + "." + DBConstants.TableColumns.ORG_ADDRESS + ", "
            // now the header details
            + FULFILL + "." + DBConstants.TableColumns.SERVICE_QTY + ", "
            + FULFILL + "." + DBConstants.TableColumns.STATUS

            + " FROM "
            + DBConstants.Tables.REQUEST_FULFILL + " as " + FULFILL + ", "
            + DBConstants.Tables.ORGANIZATION + " as " + ORG

            + " WHERE "
            + FULFILL + "." + DBConstants.TableColumns.REQUEST_DETAIL_ID + "=" + requestDetailID
            + " AND "
            + FULFILL + "." + DBConstants.TableColumns.ORG_CODE + "="
            + ORG + "." + DBConstants.TableColumns.ORG_CODE;

    }

    /**
     * This method is in progress. need to complete with the appropriate fields.
     * @return
     */
    public static String getSQLForSearchCriteria() {

        final String RQH = "RQH";
        final String RQD = "RQD";

        return "SELECT "
            + RQH + "." + DBConstants.TableColumns.DESCRIPTION + ", "
            + RQD + "." + DBConstants.TableColumns.ITEM
            + " FROM "
            + DBConstants.Tables.REQUEST_HEADER + " as " + RQH + ", "
            + DBConstants.Tables.REQUEST_DETAIL + " as " + RQD
            + " WHERE "
            + "(? is null OR (" + DBConstants.TableColumns.CATEGORY + " = ?))"
            + " and "
            + RQH + "." + DBConstants.TableColumns.REQUEST_ID
            + " = "
            + RQD + "." + DBConstants.TableColumns.REQUEST_ID
            ;
    }
   /**
    * Gives an sql insert statement to insert values to organization table
    * in following order.
    * OrgCode, ContactPerson, OrgName,Status, OrgAddress, ContactNumber,
    * EmailAddress, CountryOfOrigin, FacilitiesAvailable, WorkingAreas,
    * Comments
    * @return
    */
    public static String getSQLForOrganizationRegistration() {
        return "INSERT into "
            + DBConstants.Tables.ORGANIZATION
            + " ("
            + DBConstants.TableColumns.ORG_CODE + ", "
            + DBConstants.TableColumns.ORG_CONTACT_PERSON + ", "
            + DBConstants.TableColumns.ORG_NAME + ", "
            + DBConstants.TableColumns.STATUS + ", "
            + DBConstants.TableColumns.ORG_ADDRESS + ", "
            + DBConstants.TableColumns.ORG_CONTACT_NUMBER + ", "
            + DBConstants.TableColumns.ORG_EMAIL_ADDRESS + ", "
            + DBConstants.TableColumns.ORG_COUNTRY_OF_ORIGIN + ", "
            + DBConstants.TableColumns.ORG_FACILITIES_AVAILABLE + ", "
            + DBConstants.TableColumns.ORG_WORKING_AREAS + ", "
            + DBConstants.TableColumns.ORG_COMMENTS
            + ") "
            + " VALUES "
            + "(?,?,?,?,?,?,?,?,?,?,?)";
    }

    public static String getSQLForCountOrganizationCode() {
        return "select count(" + DBConstants.TableColumns.ORG_CODE + ") from " + DBConstants.Tables.ORGANIZATION +" where "
                + DBConstants.TableColumns.ORG_CODE + "=?";
    }
}
