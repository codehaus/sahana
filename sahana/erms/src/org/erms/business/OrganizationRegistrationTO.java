package org.erms.business;

import java.util.List;
import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: lsf
 * Date: Dec 31, 2004
 * Time: 8:03:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationRegistrationTO {

    private String orgCode;
    private String contactPerson;
    private String orgName;
    private String status;
    private String orgAddress;
    private String contactNumber;
    private String emailAddress;
    private String countryOfOrigin;
    private String facilitiesAvailable;
    private String workingAreas;
    private String comments;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public String getFacilitiesAvailable() {
        return facilitiesAvailable;
    }

    public void setFacilitiesAvailable(String facilitiesAvailable) {
        this.facilitiesAvailable = facilitiesAvailable;
    }

    public String getWorkingAreas() {
        return workingAreas;
    }

    public void setWorkingAreas(String workingAreas) {
        this.workingAreas = workingAreas;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public boolean isEmpty(String string) {
        if (string == null) return true;
        if (string.trim().length() <= 0) return true;
        return false;
    }

    public List validate() {
        List result = new LinkedList();
        if (isEmpty(orgCode)) {
            result.add("Organization Code is required.");
        }
        if (isEmpty(orgName)) {
            result.add("Organisation Name is required.");
        }
        if (isEmpty(contactPerson))  {
            result.add("Contact Person.");
        }
        if (isEmpty(orgAddress)) {
            result.add("Address is required.");
        }

        if (isEmpty(contactNumber)) {
            result.add("Contact No is required.");
        }

        if (isEmpty(emailAddress)) {
            result.add("Email Address is required.");
        }

        if (isEmpty(countryOfOrigin)) {
            result.add("Country of origin  is required.");
        }

        if (result.size() <=0) {
            if (orgAddress.length() > 255) {
                result.add("Address length is to long ( maximum allowed is 255 ).");
            }
            if (facilitiesAvailable.length() > 255) {
                result.add("Facilities available list is too long ( maximum allowed is 255 ).");
            }
            if (workingAreas.length() > 255) {
                result.add("Working areas list is too long ( maximum allowed is 255 ).");
            }
            if (comments.length() > 255)
                result.add("Comment is too long ( maximum allowed is 255 ).");
        }
        return result;
    }

}
