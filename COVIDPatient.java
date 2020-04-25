/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covidreader;

/**
 *
 * @author CKLang
 */
public class COVIDPatient {
    private String age = "";
    private String gender = "";
    private String symptom = "";
    private String dateOfOnsetSymptoms = "";
    private String administrativeDivision = "";
    //create constructors
    
    public COVIDPatient(String age, String gender, String symp, String date, String div) {
        this.age = age;
        this.gender = gender;
        this.symptom = symp;
        this.dateOfOnsetSymptoms = date;
        this.administrativeDivision = div;
    }
    
    
    //create setters, getters, toString

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getSymptom() {
        return symptom;
    }

    public String getDateOfConfirmation() {
        return dateOfOnsetSymptoms;
    }

    public String getAdministrativeDivision() {
        return administrativeDivision;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public void setDateOfOnsetSymptoms(String dateOfOnsetSymptoms) {
        this.dateOfOnsetSymptoms = dateOfOnsetSymptoms;
    }

    public void setAdministrativeDivision(String administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }

    @Override
    public String toString() {
        return "COVIDPatient{" + "age=" + age + ", gender=" + gender + ", symptom=" + symptom + ", dateOfConfirmation=" + dateOfOnsetSymptoms + ", administrativeDivision=" + administrativeDivision + '}';
    }
    
    
}
