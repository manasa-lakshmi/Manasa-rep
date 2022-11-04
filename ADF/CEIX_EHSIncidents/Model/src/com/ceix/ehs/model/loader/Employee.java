package com.ceix.ehs.model.loader;
 

import com.opencsv.bean.CsvBindByName;

public class Employee {
    @CsvBindByName(column="EMPLOYEE_ID")
         String employeeId ;
        
        

    @CsvBindByName(column="EMP_PERSONAL_NUM")
         String      empPersonalNum ; 
        
        
    @CsvBindByName(column="EMP_DOB")
            String empDob ; 
    @CsvBindByName(column="EMP_NAME")
            String empDisplayName ;
    @CsvBindByName(column="PHONE")
            String phone; 
    @CsvBindByName(column="MARITAL_STATUS")
           String maritalStatus ; 
    @CsvBindByName(column="COUNTY")
          String  county ; 
    @CsvBindByName(column="STATE")
          String  state ; 

    
           String address ; 
    @CsvBindByName(column="GENDER")
          String  gender ; 
    @CsvBindByName(column="DATEOFHIRE")
          String  hiredDate;
    @CsvBindByName(column="WAGES")
          String  wages ; 

    @CsvBindByName(column="SALARY_UNION_UNIONFREE")
           String salaryUnionfree ; 
    @CsvBindByName(column="F_NAME")
          String  fName ; 
    @CsvBindByName(column="L_NAME")
          String  lName ; 
              
    @CsvBindByName(column="M_NAME")
         String  mName; 
    @CsvBindByName(column="SSN_NUM")
         String   ssn;
    @CsvBindByName(column="JOB_TITLE")
    String jobTitle;
    
    @CsvBindByName(column="ADDRESS_LINE_1")
    String addr1;
    @CsvBindByName(column="ADDRESS_LINE_2")
    String addr2;
    @CsvBindByName(column="ADDRESS_LINE_3")
    String addr3;
         
    @CsvBindByName(column="ZIP")
    String zip;
    @CsvBindByName(column="CITY")
    String city;
    
    public Employee() {
        super();
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmpPersonalNum(String empPersonalNum) {
        this.empPersonalNum = empPersonalNum;
    }

    public String getEmpPersonalNum() {
        return empPersonalNum;
    }

    public void setEmpDob(String empDob) {
        this.empDob = empDob;
    }

    public String getEmpDob() {
        return empDob;
    }

    public void setEmpDisplayName(String empDisplayName) {
        this.empDisplayName = empDisplayName;
    }

    public String getEmpDisplayName() {
        return empDisplayName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county!=null?county:"";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state!=null?state:"";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        StringBuffer str= new StringBuffer();
        str.append(this.getAddr1());
        str.append(" ");
        str.append(this.getAddr2());
        str.append(" ");
        str.append(this.getAddr3());
        str.append(" ");
       str.append(this.getCity());
        str.append(" ");
        str.append(this.getState());
        str.append(" ");
        str.append(this.getZip());
        return str.toString();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setHiredDate(String hiredDate) {
        this.hiredDate = hiredDate;
    }

    public String getHiredDate() {
        return hiredDate;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getWages() {
        return wages;
    }

    public void setSalaryUnionfree(String salaryUnionfree) {
        this.salaryUnionfree = salaryUnionfree;
    }

    public String getSalaryUnionfree() {
        return salaryUnionfree;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFName() {
        return fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getLName() {
        return lName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getMName() {
        return mName;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr1() {
        return addr1!=null?addr1:"";
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getAddr2() {
        return addr2!=null?addr2:"";
    }

    public void setAddr3(String addr3) {
        this.addr3 = addr3;
    }

    public String getAddr3() {
        return addr3!=null?addr3:"";
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip!=null ?zip:"";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city!=null?city:"";
    }
}
