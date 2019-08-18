import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class User implements Serializable {
    private String name;
    private String surename;
    private String email;
    private ArrayList<String> rols = new ArrayList<>();
    private ArrayList<String> phons = new ArrayList<>();
    private static Map<Integer,User> users = new HashMap<Integer, User>();

    public User() {
    }

    public User(String name, String surename, String email, ArrayList<String> rols, ArrayList<String> phons) {
        this.name = name;
        this.surename = surename;
        this.email = email;
        this.rols = rols;
        this.phons = phons;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getRols() {
        return rols;
    }

    public void setRols(ArrayList<String> rols) {
        this.rols = rols;
    }

    public ArrayList<String> getPhons() {
        return phons;
    }

    public void setPhons(ArrayList<String> phons) {
        this.phons = phons;
    }

    public static void add(int id) {
        Scanner scanner = new Scanner(System.in);
        String name, surename, email, roll, phone;
        int counterR = 0, counterP=0;
        ArrayList<String> rolls = new ArrayList<>();
        ArrayList<String> phones = new ArrayList<>();

        //Ввод имени
        System.out.println("Введите имя");
        name = scanner.nextLine();

        //Ввод фамилии
        System.out.println("Введите фамилию");
        surename = scanner.nextLine();

        //Ввод и валидация email
        System.out.println("Введите email");
        boolean echeck = false;
        email = scanner.nextLine();
        while (echeck == false){
            echeck = checkEmail(email);
            if (!echeck) {
                System.out.println("Email введен некорректно, повторите ввод (корректный email имеет " +
                        "следующий вид *******@*****.***)");
                email = scanner.nextLine();
            }
        }

        //Ввод роли
        System.out.println("Введите роль");
        for (;counterR<3;) {
            roll = scanner.nextLine();

            if (roll.length()<=1 && counterR<1){
                System.out.println("Введите роль заново");
            }else if (roll.length()>=1){
                rolls.add(roll);
                counterR++;
                if (counterR < 3) {
                    System.out.println("Введите дополнительную роль или нажмите ввод для завершения ввода ролей");
                }
            }
            if (roll.length() == 0 && counterR > 0) {
                counterR = 3;
                break;
            }
        }

        //Ввод и валидация м. телефона
        System.out.println("Введите телефон (корректный ввод телефона имеет следующий вид 375** *******)");
            for (;counterP<3;) {
                phone = scanner.nextLine();

                if (checkPhone(phone)) {
                    phones.add(phone);
                    counterP++;
                }if (checkPhone(phone) == false && phone.length()!=0){
                    System.out.println("Введен некорректный номер телефон повторите ввод (корректный ввод" +
                            "телефона имеет следующий вид 375** *******)");
                }
                if (phone.length() == 0 && counterP > 0) {
                    counterP = 3;
                    break;
                }else if(counterP>=1 && counterP <=3) System.out.println("Введите ещё один телефон (корректный ввод телефона имеет " +
                        "следующий вид 375** *******) или нажмите ввод для завершения");
            }

        if (name.length()<=1){
            System.out.println("Введите имя заново");
            name = scanner.nextLine();
        }if (surename.length()<=1){
            System.out.println("Введите фамилию заново");
            surename = scanner.nextLine();
        }if (email.length()<=1){
            System.out.println("Email не введен. Введите занова");
            email = scanner.nextLine();
        }

        users.put(id, new User(name, surename, email, rolls, phones));
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat")))
        {
            oos.writeObject(users);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void getUserList(){
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
    }

    private static boolean checkPhone (String s){

        char c;
        if (s.length() == 13) {
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);

                if (!(c >= '0' && c <= '9')) {
                    if (!(i == 5 && c==' ')){
                        return false;
                    }
                    if (i==0 && c!='3'){
                        return false;
                    }
                    if(i==1 && c != '7'){
                        return false;
                    }
                    if(i==2 && c == '5'){
                        return false;
                    }
                }

            }
        }else {return false;}
        return true;
    }
    private static boolean checkEmail(String s){
        char c;
        int k = 0;
        int j =0;
        if (s.length()>=5){
            for(int i = 0; i< s.length(); i++){
                c = s.charAt(i);
                if(i>=1 && c == '@'){
                    k = i;
                }
                if (i>k && k!=0 && c =='.'){
                    j = i;
                }
                if(i>=j+2 && j!=0){
                    i = s.length()+1;
                    return true;
                }

            }
        }
    return false;
    }

}
