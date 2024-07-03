package com.example.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
        Boolean isTrue = true;
        while (isTrue){
            Session session = sessionFactory.getCurrentSession();
            System.out.println("1: Create a new person\n" +
                    "2: Show Table\n" +
                    "3: Change Something\n" +
                    "4: Delet\n" +
                    "5: leave");
            int acttionFirst = scan.nextInt();
            switch (acttionFirst){
                case 1:
                    session.beginTransaction();
                    createPersonFromJavaToSQL(session);
                    session.getTransaction().commit();
                    break;
                case 2:
                    session.beginTransaction();
                    ShowAllTableFormSQL(session);
                    break;
                case 3:
                    session.beginTransaction();
                    ShowAllTableFormSQL(session);
                    changeSomethingFromSQL(session);
                    break;
                case 4:session.beginTransaction();
                    ShowAllTableFormSQL(session);
                    deletePersonFromSQL(session);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Invalid action");
                    break;
            }
            session.getTransaction().commit();
        }
    }

    public static void deletePersonFromSQL(Session session) {
        System.out.println("Enter the ID of the person you want to delete");
        int id = scan.nextInt();
        Person person = session.get(Person.class, id);
        session.delete(person);
    }

    public static void changeSomethingFromSQL(Session session){
        System.out.println("Choose ID");
        int personID = scan.nextInt();
        System.out.println(
                "1: Change FirstName\n" +
                        "2: Change LastName\n" +
                        "3: Change Age\n" +
                        "4: Change Skill");
        int secondAction = scan.nextInt();
        Person person = session.get(Person.class, personID);
        switch (secondAction){
            case 1:
                scan.nextLine();
                String firstName = scan.nextLine();
                person.setFirstName(firstName);
                break;
            case 2:
                scan.nextLine();
                String lastName = scan.nextLine();
                person.setLastName(lastName);
                break;
            case 3:
                scan.nextLine();
                int age = scan.nextInt();
                person.setAge(age);
                break;
            case 4:
                scan.nextLine();
                String skill = scan.nextLine();
                person.setSkill(skill);
                break;
            default:
                System.out.println("Invalid action");
                break;
        }
    }
    public static void createPersonFromJavaToSQL(Session session){
        System.out.println("Enter first name: ");
        String firstName = scan.next();
        System.out.println("Enter last name: ");
        String lastName = scan.next();
        System.out.println("Enter age: ");
        int age = scan.nextInt();
        System.out.println("Enter skill: ");
        String skill = scan.next();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        person.setSkill(skill);
        session.save(person);
    }
    public static void ShowAllTableFormSQL(Session session){
        List<Person> list= session.createQuery("from Person").getResultList();
        for(Person person:list){
            System.out.println(person);
        }
    }
}
