package com.adamssc;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.adamssc.entities.*;

public class Main {



    /**
     * Main method for Genova Name Wizard.  We are going to keep it simple.
     * @param args - command line arguments (not evaluated)
     */
    public static void main(String[] args) {
        List<Person> people=readPeople("input\\NameList1.txt");
        people.addAll(readPeople("input\\NameList2.txt"));
        Collections.sort(people);

        int duplicateNamesCount = people.stream().filter(i -> Collections.frequency(people, i) > 1)
                .collect(Collectors.toList()).size();

        int duplicateInstanceCount = people.stream().filter(i -> Collections.frequency(people, i) > 1)
                .collect(Collectors.toSet()).size();

        Double averageAge = people.parallelStream().filter(p -> p.age!=null).mapToDouble(p -> p.age).average().getAsDouble();

        print(people,"people.csv");

        System.out.println(String.format ("There were %d incidences of duplicate first names  (%d total names)",
                duplicateInstanceCount,duplicateNamesCount));
        System.out.println(String.format ("The average age for both files was %f",averageAge));
    }

    /**
     * Reads a file and gives us a list of people
     * @param filename
     * @return
     */
    private static List<Person> readPeople(String filename){
        List<Person> people;
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String str;
            str = in.readLine();

            //Probably nothing wrong with the file, let's initialize the list
            people = new ArrayList<Person>();

            while ((str = in.readLine()) != null) {
                people.add(Person.fromDelimitedLine(str,"\t")); //tab delimited
            }
            in.close();
            return people;
        } catch (IOException e) {
            System.out.println(e);
        } catch (NumberFormatException e){
            System.out.println(e);
        }
        // We're going to send an empty person list. At least we sent the exception message to std output.
        // We're not going to concern ourselves too much with exception handling for this
        // exercise (particularly since we know what files will be sent in)
        return new ArrayList<Person>();
    }

    /**
     * Print the list of people toStandard out and a file using the toString format.
     * @param people
     */
    private static void print(List<Person> people, String fileName){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
            for (Person person:people){
                System.out.println(person.toString());
                writer.println(person.toString());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }

    }
}
