package users;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements UserInterface, Serializable {
    private String name;
    private String surename;
    private String email;
    private ArrayList<String> rols = new ArrayList<>();
    private ArrayList<String> phons = new ArrayList<>();
    transient Scanner scanner = new Scanner(System.in);
    public User() {
        setName();
        setSurename();
        setEmail();
        setRols();
        setPhons();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurename() {
        return surename;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public ArrayList<String> getRols() {
        return rols;
    }

    @Override
    public ArrayList<String> getPhons() {
        return phons;
    }

    public void setName() {
        System.out.println("Введите имя");
        name = scanner.nextLine();
        if (name.length()<=1){
            System.out.println("Введите имя заново");
            name = scanner.nextLine();
        }else this.name = name;
    }

    public void setSurename() {
        System.out.println("Введите фамилию");
        surename = scanner.nextLine();
        if (surename.length()<=1){
            System.out.println("Введите имя заново");
            surename = scanner.nextLine();
        }else this.surename = surename;
    }

    public void setEmail() {
        System.out.println("Введите email");
        email = scanner.nextLine();
        boolean echeck = false;
        while (echeck == false){
            echeck = checkEmail(email);
            if (!echeck) {
                System.out.println("Email введен некорректно, повторите ввод (корректный email имеет " +
                        "следующий вид *******@*****.***)");
                email = scanner.nextLine();
            } else this.email = email;
        }
    }

    public void setRols() {
        System.out.println("Введите роль");
        int counterR = 0;
        String roll;
        ArrayList<String> rolls = new ArrayList<>();
        for (;counterR<3;) {
            roll = scanner.nextLine();

            if (roll.length()<=1 && counterR<1){
                System.out.println("Введите роль заново");
            }else if (roll.length()>1){
                rolls.add(roll);
                counterR++;
                if (counterR < 3) {
                    System.out.println("Введите дополнительную роль или нажмите ввод для завершения ввода ролей");
                }
            }else System.out.println("Введите роль заново");
            if (roll.length() == 0 && counterR > 0) {
                counterR = 3;
                break;
            }
        }

        this.rols = rolls;
    }

    public void setPhons() {
        int counterP = 0;
        String phone;
        ArrayList<String> phones = new ArrayList<>();
        System.out.println("Введите телефон (корректный ввод телефона имеет следующий вид 375** *******)");
        for (;counterP<3;) {
            phone = scanner.nextLine();
            if (checkPhone(phone)) {
                phones.add(phone);
                counterP++;
            }if (!(checkPhone(phone)) && phone.length()!=0){
                System.out.println("Введен некорректный номер телефон повторите ввод (корректный ввод" +
                        "телефона имеет следующий вид 375** *******)");
            }
            if(counterP>=1 && counterP <=3 && checkPhone(phone)){
                System.out.println("Введите ещё один телефон (корректный ввод телефона имеет " +
                        "следующий вид 375** *******) или нажмите ввод для завершения");
            }
            else if (phone.length() == 0 && counterP > 0) {
                counterP = 3;
                break;
            }else if (phone.length() == 0 && counterP == 0) {
                System.out.println("Введите телефон (корректный ввод телефона имеет следующий вид 375** *******)");
            }
        }

        this.phons= phones;
    }

    private static boolean checkPhone (String s){
        if (s.length() == 13) {
            if (s.startsWith("375") && s.startsWith(" ", 5)
                    && s.substring(6).matches("[\\d]+")) return true;
            else return false;
        }else return false;
    }

    private static boolean checkEmail(String s){

        int posAt = s.indexOf("@");
        int posLastPoint = s.lastIndexOf(".");
        if (s.length()>1){
            if(posAt > 2 && (posLastPoint > posAt && posLastPoint <= (s.length()-3))) return true;
            else return false;
        }else return false;
    }

    /*    public static void getUserList(){
        Map<Integer, User> userList= new HashMap<Integer, User>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.dat")))
        {
            userList=((Map<Integer, User>) ois.readObject());
            users = userList;
        }
        catch(Exception ex){
        }
    }
    public static int id (){
        int id = 0;
        for (Integer i: users.keySet()){
            id = i;
        }
        return id;
    }
    public static void printUserList(){
        for (Map.Entry<Integer, User> item: users.entrySet()){
            System.out.printf("Id: %d\nИмя: %s\nФамилия: %s\n", item.getKey(), item.getValue().getName(), item.getValue().getSurename());
            System.out.println();
        }
    }
    public static void printUserById(){
        Scanner scanner = new Scanner(System.in);
        if (users.isEmpty()) {
            System.out.println("Список пользователей пуст");
        }else {
            int id;
            System.out.println("Для просмотра подробной информации о пользователе введите его Id");
            id = scanner.nextInt();
            for (int i=1; i!=0;){
                if (!(users.containsKey(id))){
                    System.out.println("Пользователя с таким Id не существует, введите корректное Id");
                    id = scanner.nextInt();
                } else i=0;
            }

            System.out.printf("Имя: %s\nФамилия: %s\nEmail: %s\n", users.get(id).getName(),
                    users.get(id).getSurename(), users.get(id).getEmail());
            System.out.println("Список ролей: ");
            for (String s: users.get(id).getRols()) {
                System.out.println(s);
            }
            System.out.println("Список телефонов: ");
            for (String s: users.get(id).getPhons()) {
                System.out.println(s);
            }
            System.out.println();
        }


    }
    public static void removeUser(int id){

        if (users.containsKey(id)){
            users.remove(id);
            System.out.println("Пользователь был удалён");
        }else {
            System.out.println("Пользователя с таким id нет!");
        }


        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat")))
        {
            oos.writeObject(users);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }*/

}
