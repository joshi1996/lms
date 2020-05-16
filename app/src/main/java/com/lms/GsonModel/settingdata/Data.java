
package com.lms.GsonModel.settingdata;

import android.graphics.Color;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("companyId")
    @Expose
    private String companyId;
    @SerializedName("organizationName")
    @Expose
    private String organizationName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("smsNotification")
    @Expose
    private String smsNotification;
    @SerializedName("faviconIcon")
    @Expose
    private String faviconIcon;
    @SerializedName("licenseExpieryDate")
    @Expose
    private String licenseExpieryDate;
    @SerializedName("themeColor")
    @Expose
    private String themeColor;
    @SerializedName("importantPoints")
    @Expose
    private String importantPoints;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("websiteLink")
    @Expose
    private String websiteLink;

    @SerializedName("about")
    @Expose
    private String about;


    @SerializedName("supportEmail")
    @Expose
    private String supportEmail;

    @SerializedName("supportContent")
    @Expose
    private String supportContent;



    @SerializedName("termsAndConditions")
    @Expose
    private String termsAndConditions;


    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;


    @SerializedName("slogan")
    @Expose
    private String slogan;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportContent() {
        return supportContent;
    }

    public void setSupportContent(String supportContent) {
        this.supportContent = supportContent;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmsNotification() {
        return smsNotification;
    }

    public void setSmsNotification(String smsNotification) {
        this.smsNotification = smsNotification;
    }

    public String getFaviconIcon() {
        return faviconIcon;
    }

    public void setFaviconIcon(String faviconIcon) {
        this.faviconIcon = faviconIcon;
    }

    public String getLicenseExpieryDate() {
        return licenseExpieryDate;
    }

    public void setLicenseExpieryDate(String licenseExpieryDate) {
        this.licenseExpieryDate = licenseExpieryDate;
    }

    public int getThemeColor() {
         int color=themeColor==null? Color.parseColor("#2222FF"): Color.parseColor(themeColor);
         return color;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getImportantPoints() {
        return importantPoints;
    }

    public void setImportantPoints(String importantPoints) {
        this.importantPoints = importantPoints;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
