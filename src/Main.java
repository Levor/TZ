import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int i =1;
        int c;
        Writer wr = new Writer();

        while (i!=0){
            System.out.println("Для создания пользователя введите 1 \n"+"Для изменения пользователя введите 2 \n" +
                    "Для вывода списка пользователей введите 3\n" +"Для удаления пользователя введите 4\n"
                    +"Для выхода и завершения программы нажмите 5");

            String s =  scanner.next();
            try {
                c = Integer.parseInt(s);
                switch (c){
                    case 1:
                    {
                        wr.addUser();
                        break;
                    }
                    case 2:
                    {
                        wr.changeUser();
                        break;
                    }
                    case 3:
                    {
                        wr.printUser();
                        break;
                    }
                    case 4:
                    {
                        wr.removeUser();
                        break;
                    }
                    case 5:
                    {
                        i=0;
                        break;
                    }
                    default:
                    {
                        System.out.println("Введено неверное значение повторите ввод");
                    }
                }
            }catch (NumberFormatException ex){
                System.out.println("Введено неверное значение повторите ввод");
            }
        }

    }
}
