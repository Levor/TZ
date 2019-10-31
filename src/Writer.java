import users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Writer {
    private LinkedList<User> userList = new LinkedList<User>();
    Scanner scanner = new Scanner(System.in);

    public void addUser(){
        readUser();
        userList.add(new User());
        writeUser();
        System.out.println("Пользователь был успешно добавлен");
    }
    public void printUser(){
        readUser();
        int id = 0;
        System.out.println("Список пользователей:");
        if (userList.size()==0){
            System.out.println("Список пуст");
        }
        for (User us: userList) {
            id++;
            System.out.print("id: " + id + "\tИмя: " + us.getName()+ "\tФамилия: " + us.getSurename()+ "\tEmail: " +
                    us.getEmail()+" ");
            if (us.getRols().size()>1){
                System.out.print("\tРоли: ");
            }else System.out.print("\tРоль: ");
            for (String s: us.getRols()){
                System.out.print(s+"; ");
            }
            if (us.getPhons().size()>1){
                System.out.print("\tНомера телефонов: ");
            }else System.out.print("\tНомер телефона: ");
            for (String s: us.getPhons()){
                System.out.print(s+"; ");
            }
            System.out.println();
        }
    }
    public void changeUser(){
        printUser();
        System.out.println("Введите id пользователя которого хотите изменить или нажмите 0 чтобы выйти в предыдущее меню");
        int id=-1;
        int stop = 1;
        while (stop!=0){
            String s = scanner.next();
            try {
                id = Integer.parseInt(s);
                if(id == 0){
                    stop=0;
                }
                else if ((userList.size())>=id && id>0){
                    userList.set(--id, new User());
                    stop =0;

                }else {
                    System.out.println("Введено неверное id повторите ввод");
                }
            }catch (NumberFormatException ex){
                System.out.println("Введено неверное id повторите ввод");
            }
        }
        writeUser();
        System.out.println("Пользователь был успешно изменен");

    }
    public void removeUser(){
        printUser();
        System.out.println("Введите id пользователя которого хотите удалить или нажмите 0 чтобы выйти в предыдущее меню");
        int id=-1;
        int stop = 1;
        while (stop!=0){
            String s = scanner.next();
            try {
                id = Integer.parseInt(s);
                if(id == 0){
                    stop=0;
                }
                else if ((userList.size())>=id && id>0){
                    userList.remove(--id);
                    stop=0;
                }else {
                    System.out.println("Введено неверное id повторите ввод");
                }
            }catch (NumberFormatException ex){
                System.out.println("Введено неверное id повторите ввод");
            }
        }
        writeUser();
        System.out.println("Пользователь был успешно удален");

    }

    private void writeUser(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.dat"))) {
            oos.writeObject(userList);
        }catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
    private void readUser(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.dat")))
        {
            userList = (LinkedList<User>) ois.readObject();
        }
        catch(Exception ex){
            if (ex.getMessage() != null){
                System.out.println(ex.getMessage());
            }
        }
    }


}

