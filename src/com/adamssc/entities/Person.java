package com.adamssc.entities;

/**
 * Class for person attributes.  Implements comparable for sorting.
 */
public class Person implements Comparable<Person>{
    //not bothering with getters or setters...
    public String lastName;
    public String firstName;
    public Double age; //Double to handle partial years for future files...

    /**
     * Compares two people by lastName
     * @param o
     * @return
     */
    @Override
    public int compareTo(Person o) {
        if (this.lastName == null && o.lastName == null) return 0;
        if (this.lastName == null && o.lastName != null) return 1;
        if (this.lastName != null && o.lastName == null) return -1;
        if (this.lastName.compareToIgnoreCase(o.lastName)==0){
            if (this.firstName.compareToIgnoreCase(o.lastName)==0){
                return (this.age.compareTo(o.age));
            }
            return (this.firstName.compareTo(o.firstName));
        }
        return this.lastName.compareToIgnoreCase(o.lastName);
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %.0f",lastName,firstName,age);
    }

    /**
     * Takes a delimited string with at least three values, the third being a number and
     * instantiates a Person.  The first value is the first name, the second value is the last name
     * and the third is the age.
     * @param delimitedLine
     * @param delimiter
     * @return
     */
    public static Person fromDelimitedLine(String delimitedLine, String delimiter){
        Person person = new Person();

        try{
            String[] values=delimitedLine.split(delimiter);

            if (values.length>0){
                person.firstName=values[0].trim();
                if (values.length>1){
                    person.lastName=values[1].trim();
                    if (values.length>2){
                        person.age=Double.parseDouble(values[2].trim());
                    }
                }
            }
        }   catch (NumberFormatException e){
            throw(new NumberFormatException(String.format("Exception converting value to number in line: (%s)",delimitedLine)));
        }

        return person;
    }

    /**
     * Using this to find duplicates.  Two people with the same name are
     * not necessarily equal, so this is a shortcut based on the stated
     * requirements.  Happens to suit our purposes for this project.
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other){
        if (other instanceof Person){
            return this.firstName.equals(((Person)other).firstName);
        }
        return false;
    }


    /**
     * Used in distinct() implementation.  Again, this is more of a shortcut. Might not make sense in another scenario.
     * @return
     */
    @Override
    public int hashCode() {
        return firstName.hashCode();
    }
}