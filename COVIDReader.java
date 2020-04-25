/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package covidreader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author John Grant
 * Feat Ethan Westerburg & David Bradley
 */

public class COVIDReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String csvFile = "covid19dataset.csv";
        String line = "";
        String cvsSplitBy = ",";
        
        ArrayList<COVIDPatient> covidPatients = new ArrayList<COVIDPatient>();
        //size = 0 now, is equal to 30018 after running
        boolean headerDone = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] lineStrings = line.split(cvsSplitBy);
                if (headerDone) {
                    COVIDPatient newDude = new COVIDPatient(lineStrings[0].trim(),
                            lineStrings[1].trim(), lineStrings[8].trim(),
                            lineStrings[7].trim(), lineStrings[2].trim().toLowerCase());
                    //order inputted is age, gender, symptoms, DateOfC, and div
                    covidPatients.add(newDude);
                }
                else headerDone = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //insertion sort
        long startTime = System.nanoTime();
        
        for (int i = 1; i < covidPatients.size(); i++) {
            int j = i;
            while ((j > 0)&&((covidPatients.get(j-1).getAdministrativeDivision().compareTo(
            covidPatients.get(i).getAdministrativeDivision())) > 0)) {
                j--;
            }
            covidPatients.add(j,covidPatients.get(i));
            covidPatients.remove(i+1);
        }
        long endTime = System.nanoTime();
        System.out.println("Insertion sort time in nanoseconds " +(endTime-startTime));
        //output: Insertion sort time in nanoseconds 6675442737
        
        //sequential search
        Scanner scan = new Scanner(System.in);
        System.out.print("What state do you live in?");
        String state = scan.nextLine().toLowerCase();
        
        long startTime2 = System.nanoTime();
        for (int i = 0; i < covidPatients.size(); i++) {
            int firstSpace = covidPatients.get(i).getAdministrativeDivision().indexOf(" ");
            
            if ((firstSpace > 0) &&
                    (covidPatients.get(i).getAdministrativeDivision().substring(0, firstSpace).equals(state))) {
                //if firstSpace = -1 (adDiv is blank) then the above statewould give
                //an error if I didn't include that first conditional
                System.out.println(covidPatients.get(i));
                i = covidPatients.size();
            }
        }
        long endTime2 = System.nanoTime();
        System.out.println("Sequential Search time in nanoseconds " +(endTime2-startTime2));
        
        //binary search
        int firstIndex = binarySearch(state, covidPatients);
        System.out.println(covidPatients.get(firstIndex));
        //output - BS Execution time in nanoseconds: 37289
        
        //gender percentage
        ArrayList<COVIDPatient> copyPatients = copyList(covidPatients);
        int i = 0;
        while (i < copyPatients.size()) {
            if (copyPatients.get(i).getGender().equals("")) {
                copyPatients.remove(i);
            }
            else i++;
        }
        System.out.println("Male Percentage per country");
        System.out.println("America: " +(genderPercent(copyPatients, "unitedstates")*100));
        System.out.println("Italy: " + (genderPercent(copyPatients, "italy")*100));
        System.out.println("China: " + (genderPercent(copyPatients, "china")*100));
        /* output
        America: 53.75
        Italy: 61.90476190476191
        China: 54.4179523141655
        */
        
        //average age
        ArrayList<COVIDPatient> copyPatients2 = copyList(covidPatients);
        i = 0;
        while (i < copyPatients2.size()) {
            if (copyPatients2.get(i).getAge().equals("")) {
                copyPatients2.remove(i);
            }
            else i++;
        }
        
        System.out.println("Average age per country");
        System.out.println("America: " +averageAge(copyPatients2, "unitedstates"));
        System.out.println("China: " + averageAge(copyPatients2, "china"));
        System.out.println("Italy: " + averageAge(copyPatients2, "italy"));
        /* output
        America: 53
        China: 47
        Italy: 39
        */
        
        
        //selection sort
        //first we have to shuffle the arrayList, because it was sorted
        Collections.shuffle(covidPatients);
        long startTime3 = System.nanoTime();
        for (int k1 = 0; k1 < covidPatients.size()-1; k1++) {
            int min = k1;
            for (int k2 = k1+1; k2 < covidPatients.size()-1; k2++) {
                if (covidPatients.get(min).getAdministrativeDivision().compareTo(covidPatients.get(k2).getAdministrativeDivision()) > 0) {
                    min = k2;
                }
            }
            if (min != k1) {
                COVIDPatient temp = covidPatients.get(min);
                covidPatients.set(min, covidPatients.get(k1));
                covidPatients.set(k1, temp);
            }
        }
        long endTime3 = System.nanoTime();
        System.out.println("Selection sort runtime: " + (endTime3-startTime3));
        //output - Selection sort runtime: 42032799260
        
        //1. Complete the COVIDPatient class
        //2. Create an ArrayList of type COVIDPatient. Call it covidPatients. Note its size in the comments.
        //3. Code a loop to instantiate covidPatients with instances of actual 
        //patients using the records in covid19dataset.csv. Do not include the header line (hint:fencepost).
        //4. Code a sequential search to find the first confirmed patient in your state.
        //Note the runtime in the comments. 
        //5. Now code a binary search to find the first confirmed patient in your state.
        //Note the runtime in the comments.
        //6. Create a copy of covidPatients, then use ArrayList remove method to remove any COVIDPatients that do not specify gender.
        //7. Using a loop, calculate and report what percentage of patients in the United States are male?
        //8. Do the same for China.
        //9. Do the same for Italy.
        //10. Create a copy of the original covidPatients, then use ArrayList 
        // remove method to remove any COVIDPatients that do not specify age. (Hint: String's trim method can remove spaces. If trim results in an empty String, then field is blank. 
        //11. Using a loop, calculate and report the average age of patients in China?
        //12. Do the same for the United States. 
        //13. Do the same for Italy.
        //14. Using the original covidPatients, code an insertion sort by country. Report the resulting sorted list. Note the runtime in the comments. http://programmedlessons.org/Java9/chap111/ch111_06.html
        //15. Using the original covidPatients, code a selection sort by country. Report the resulting sorted list. Note the runtime in the comments. http://programmedlessons.org/Java9/chap110/ch110_10.html   
    }
    
    public static int binarySearch(String target, ArrayList<COVIDPatient> sortedList) {
        long startTime = System.nanoTime();

        int begin = 0, last = sortedList.size() - 1, mid = 0;
        while (begin <= last) {
            mid = (begin + last) / 2;

            // Check if target is present at mid 
            if (sortedList.get(mid).getAdministrativeDivision().contains(target)) {
                //note change from the given code, this is to make sure
                //the first entry in the list is returned
                while ((mid > 0) && sortedList.get(mid-1).getAdministrativeDivision().contains(target)) {
                    mid--;
                }
                long endTime = System.nanoTime();
                System.out.println("BS Execution time in nanoseconds: " + (endTime - startTime));
                return mid;
            }

            // If target greater, ignore left half 
            if (sortedList.get(mid).getAdministrativeDivision().trim().compareTo(target) < 0) {
                begin = mid + 1;
            } // If x is smaller, ignore right half 
            else {
                last = mid - 1;
            }
        }

        // if we reach here, then element was 
        // not present 
        long endTime = System.nanoTime();
        System.out.println("BS Execution time in nanoseconds :" + (endTime - startTime));
        return -1; //not found
    }
    
    public static ArrayList<COVIDPatient> copyList (ArrayList<COVIDPatient> list) {
        ArrayList<COVIDPatient> output = new ArrayList<COVIDPatient>();
        for (COVIDPatient item: list) {
            output.add(item);
        }
        return output;
    }
    
    public static double genderPercent(ArrayList<COVIDPatient> list, String country) {
        int male = 0;
        int total = 0;
        for (COVIDPatient patient: list) {
            if (patient.getAdministrativeDivision().contains(country)) {
                total++;
                if (patient.getGender().equals("Male")) male++;
            }
        }
        return (double) male/total;
    }
    
    public static int averageAge(ArrayList<COVIDPatient> list, String country) {
        int count = 0;
        int totalAge = 0;
        for (COVIDPatient patient: list) {
            if ((patient.getAdministrativeDivision().contains(country))) {
                if (!(patient.getAge().contains("["))) {
                    totalAge += Integer.parseInt(patient.getAge());
                }
                else {
                    //this next set of nasty statements is to deal with the "interval [" case
                    //find the younger age and the older age
                    //by using the "[" and " " characters
                    int brack = patient.getAge().indexOf("[");
                    String firstString = patient.getAge().substring(brack+1);
                    int youngAge = Integer.parseInt(firstString.substring(0, firstString.indexOf(" ")));
                    int oldAge = Integer.parseInt(firstString.substring(firstString.indexOf(" ")).trim());
                    totalAge += (youngAge+oldAge)/2;                    
                }
                count++;
            }
        }
        return totalAge/count;
    }
}
