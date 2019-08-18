import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i =1;
        User.getUserList();
        int id = User.id();

        while (i!=0){
            System.out.println("Для создания пользователя введите 1 \n"+"Для изменения пользователя введите 2 \n" +
                    "Для вывода списка пользователей введите 3\n" +"Для удаления пользователя введите 4\n"
                    +"Для выхода и завершения программы нажмите 5");
            String s = scanner.next();
            if (s.equals("5")){
                i = 0;
            }else if(s.equals("1")){
                id++;
                User.add(id);
                System.out.println("Пользователь был сохранён");
            }else if(s.equals("2")){
                User.getUserList();
                System.out.println("Список пользователей:");
                User.printUserList();
                System.out.println("Для редактирования пользователя введите номер пользователя:");
                int fId = scanner.nextInt();
                User.add(fId);
                System.out.println("Пользователь по id " + fId + " был изменён");
            }else if(s.equals("3")){
                User.getUserList();
                User.printUserList();
                User.printUserById();
            }else if(s.equals("4")){
                System.out.println("Список пользователей:");
                User.getUserList();
                User.printUserList();
                System.out.println("Для удаления пользователя введите номер пользователя:");
                int fId = scanner.nextInt();
                User.removeUser(fId);
            }else{
                System.out.println("Введено неверное значение. Повторите ввод.");
            }
        }

    }
}
