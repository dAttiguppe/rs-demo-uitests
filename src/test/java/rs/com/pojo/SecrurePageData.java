package rs.com.pojo;

//Secure page fields getters and setters
public class SecrurePageData {

    private String title;
    private String firstName;
    private String surName;
    private String city;
    private String contactTelephoneNumber;
    private String companyName;
    private String addressLine1;
    private String town;
    private String postCode;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactTelephoneNumber() {
        return contactTelephoneNumber;
    }

    public void setContactTelephoneNumber(String contactTelephoneNumber) {
        this.contactTelephoneNumber = contactTelephoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "SecurePageData [title=" + title + ", firstName=" + firstName + ", surName=" + surName + ", town=" + town + ", companyName=" + companyName
                + ", addressLine1=" + addressLine1 + ", postCode=" + postCode + ", contactTelephoneNumber=" + contactTelephoneNumber + "]";
    }
}
