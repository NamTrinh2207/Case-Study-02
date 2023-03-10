package views.menuViews;

import controller.command.*;
import controller.command.CommandListClients;
import controller.concreteCommand.*;
import controller.managerController.Manager;
import model.classModel.*;
import views.loginViews.Login;
import views.invoker.CoffeeApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    CommandListClients clients = new controller.concreteCommand.ListClients(Manager.getInstance());
    CommandListEmployees employees = new ListEmployees(Manager.getInstance());
    CommandListClients listInvoiceHistory = new ListInvoiceHistory(Manager.getInstance());
    CommandAddClients addNewClient = new AddNewClient(Manager.getInstance());
    CommandAddEmployee addNewEmployee = new AddNewEmployee(Manager.getInstance());
    CommandStringDataType deleteByEmploy = new DeleteByEmploy(Manager.getInstance());
    CommandStringDataType salaryPartTime = new PartTimeEmployeeSalary(Manager.getInstance());
    CommandStringDataType totalMoney = new TotalBillAmount(Manager.getInstance());
    CommandStringDataType salaryFullTime = new FullTimeEmployeeSalary(Manager.getInstance());
    CommandVoidDataType displayClients = new DisplayClients(Manager.getInstance());
    CommandVoidDataType deleteByEmploys = new DisplayEmployees(Manager.getInstance());
    CommandVoidDataType sortClient = new SortListClients(Manager.getInstance());
    CommandVoidDataType sortNameEmployees = new SortListEmployees(Manager.getInstance());
    CommandVoidDataType deleteInvoiceHistory = new DeleteInvoiceHistory(Manager.getInstance());
    CommandVoidDataType displayInvoiceHistory = new DisplayInvoiceHistory(Manager.getInstance());
    TotalSalaryAllEmployees totalSalaryAllEmployees = new TotalSalaryAllEmployees(Manager.getInstance());
    CommandEditEmployees editEmployees = new EditEmployee(Manager.getInstance());
    CoffeeApp menu = new CoffeeApp(clients, listInvoiceHistory, employees, addNewClient, addNewEmployee, deleteByEmploy, displayClients, deleteByEmploys, salaryFullTime, salaryPartTime, sortClient, sortNameEmployees, totalMoney, totalSalaryAllEmployees, editEmployees, displayInvoiceHistory, deleteInvoiceHistory);
    public Scanner input = new Scanner(System.in);
    public int checkInput;
    public String checkId;

    //Login--------------------------------------------------------------------------------------------------
    public void clubCoffee() {
        while (true) {
            showMessage("""
                    +----------LOGIN-----------+
                    |      1. ????ng nh???p        |
                    |      0. Tho??t            |
                    +--------------------------+
                    """);
            showMessage("L???a ch???n: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> {
                    userLogin();
                    menu();
                }
                case 0 -> {
                    showMessageErr("Goodbye");
                    System.exit(checkInput);
                }
            }
        }

    }

    public void menu() {
        while (true) {
            showMessage("""
                    +--------------- CLUB COFFEE -------------+
                    |        1. Qu???n l?? nh??n vi??n             |
                    |        2. Qu???n l?? kh??ch h??ng            |
                    |        3. ????ng xu???t                     |
                    |        0. Tho??t                         |
                    +-----------------------------------------+
                    """);
            showMessage("L???a ch???n: ");
            checkInput = checkInt();
            if (checkInput == 1) {
                adminLogin();
                menuEmployee();
            } else if (checkInput == 2) {
                menuClient();
            } else if (checkInput == 3) {
                showMessage("successful logout");
                clubCoffee();
            } else if (checkInput == 0) {
                showMessage("Goodbye");
                System.exit(checkInput);
            }
        }
    }

    public void userLogin() {
        Login view = new Login();
        controller.loginController.Login control = new controller.loginController.Login(view);
        control.userLogin();
    }

    public void adminLogin() {
        Login view = new Login();
        controller.loginController.Login control = new controller.loginController.Login(view);
        control.AdminLogin();
    }
    //Client--------------------------------------------------------------------------------------------

    public void menuClient() {
        while (true) {
            showMessage("""
                    +--------------- QU???N L?? H??A ????N ---------------+
                    |    1. Th??m h??a ????n m???i                        |
                    |    2. Danh s??ch h??a ????n                       |
                    |    3. T??nh ti???n h??a ????n theo id kh??ch h??ng    |
                    |    4. S???p x???p h??a ????n theo t??n kh??ch h??ng     |
                    |    5. L???ch s??? h??a ????n mua h??ng                |
                    |    6. X??a to??n b??? l???ch s??? h??a ????n mua h??ng    |
                    |    7. ????ng xu???t                               |
                    |    0. Tho??t                                   |
                    +-----------------------------------------------+
                    """);
            showMessage("L???a ch???n: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> menu.addClients(addClient());
                case 2 -> checkEmptyClients();
                case 3 -> prepareInvoice();
                case 4 -> sortByClients();
                case 5 -> showInvoiceHistory();
                case 6 -> deleteAllInvoiceHistory();
                case 7 -> {
                    showMessage("successful logout");
                    menu();
                }
                case 0 -> {
                    showMessage("Goodbye");
                    System.exit(checkInput);
                }
                default -> showMessageErr("Vui l??ng ch???n theo ????ng menu");
            }
        }
    }

    public void prepareInvoice() {
        if (menu.listClients().isEmpty()) {
            showMessageErr("Danh s??ch kh??ch h??ng ??ang tr???ng!!!");
        } else {
            showMessage("Nh???p id kh??ch h??ng mu???n t??nh ti???n: ");
            checkId = string();
            menu.totalMoney(checkId);
        }
    }

    public Client addClient() {
        String id = checkCustomerId();
        showMessage("M???i b???n nh???p t??n kh??ch h??ng : ");
        String name = string();
        showMessage("Nh???p tu???i kh??ch h??ng :");
        int age = checkInt();
        showMessage("Nh???p ?????a ch???:");
        String address = string();
        String phone = checkInputPhoneNumber();
        showMessage("Nh???p s??? lo???i c?? ph?? kh??ch h??ng mua:");
        int sp = checkInt();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= sp; i++) {
            showMessage("Th??m s???n ph???m th??? " + i + ":");
            showMessage("T??n c?? ph??: ");
            String nameSP = string();
            showMessage("Gi?? : ");
            double price = checkDouble();
            showMessage("S??? l?????ng: ");
            double quantity = checkDouble();
            products.add(new Product(nameSP, price, quantity));
        }
        return new Client(id, name, age, address, phone, products);
    }

    public void checkEmptyClients() {
        if (menu.listClients().size() == 0) {
            showMessageErr("Danh s??ch hi???n t???i ??ang tr???ng!");
        } else {
            menu.displayClient();
        }
    }

    public void sortByClients() {
        if (menu.listClients().isEmpty()) {
            showMessageErr("Danh s??ch hi???n t???i ??ang tr???ng!!!");
        } else {
            menu.sortByClients();
            showMessage("successful arrangement");
        }
    }

    public void showInvoiceHistory() {
        if (menu.listInvoiceHistory().isEmpty()) {
            showMessageErr("L???ch s??? h??a ????n tr???ng!");
        } else {
            menu.displayInvoiceHistory();
        }
    }

    public void deleteAllInvoiceHistory() {
        if (menu.listInvoiceHistory().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng!");
        } else {
            menu.deleteInvoiceHistory();
            showMessage("Deleted all invoice history");
        }
    }

    public String checkCustomerId() {
        showMessage("Nh???p m?? kh??ch h??ng:");
        String id = string();
        for (Client o : Manager.getInstance().getClients()) {
            while (true) {
                if (o.getId().equals(id)) {
                    showMessageErr("id b???n nh???p ???? c?? trong danh s??ch ! Vui l??ng nh???p l???i");
                    id = input.nextLine();
                } else {
                    break;
                }
            }
        }
        return id;
    }

    //Display Employee--------------------------------------------------------------------------------------------------
    public void menuEmployee() {
        while (true) {
            showMessage("""
                    +---------------QU???N L?? NH??N VI??N---------------+
                    |   1. Th??m nh??n vi??n                           |
                    |   2. S???p x???p danh s??ch nh??n vi??n theo t??n     |
                    |   3. Danh s??ch nh??n vi??n                      |
                    |   4. S???a th??ng tin nh??n vi??n                  |
                    |   5. X??a nh??n vi??n                            |
                    |   6. T???ng l????ng c???a t???t c??? nh??n vi??n          |
                    |   7. T??nh l????ng th???c l??nh nh??n vi??n full time |
                    |   8. T??nh l????ng th???c l??nh nh??n vi??n part time |
                    |   9. ????ng xu???t                                |
                    |   0. Tho??t                                    |
                    +-----------------------------------------------+
                    """);
            showMessage("L???a ch???n: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> {
                    showMessage("Th??m nh??n vi??n");
                    menu.addEmployees(addEmployee());
                }
                case 2 -> sortByEmployees();
                case 3 -> checkEmptyEmployee();
                case 4 -> {
                    editEmployee();
                }
                case 5 -> deleteEmployee();
                case 6 -> menu.totalSalaryAllEmployees();
                case 7 -> salaryEmployeeFullTime();
                case 8 -> salaryEmployeePartTime();
                case 9 -> {
                    showMessage("successful logout");
                    menu();
                }
                case 0 -> {
                    showMessage("Goodbye");
                    System.exit(checkInput);
                }
                default -> showMessageErr("Vui l??ng nh???p l???i!!!");
            }
        }
    }

    public Person addEmployee() {
        showMessage("""
                +--------TH??M NH??N VI??N M???I--------+
                |      1. nh??n vi??n full time      |
                |      2. nh??n vi??n part time      |
                |      0. Quay l???i                 |
                +----------------------------------+
                """);
        showMessage("L???a ch???n: ");
        checkInput = Integer.parseInt(input.nextLine());
        switch (checkInput) {
            case 1 -> {
                showMessage("M???i b???n nh???p m?? nh??n vi??n  : ");
                String id = checkEmployeeId();
                showMessage("M???i b???n nh???p t??n nh??n vi??n  : ");
                String name = string();
                showMessage("M???i b???n nh???p tu???i nh??n vi??n : ");
                int age = checkInt();
                showMessage("M???i b???n nh???p ?????a ch??? nh??n vi??n : ");
                String address = string();
                String phone = checkInputPhoneNumber();
                String email = checkInputEmail();
                showMessage("M???i b???n nh???p l????ng c???a nh??n vi??n : ");
                double hardSalary = checkDouble();
                return new FullTimeEmployee(id, name, age, address, phone, email, hardSalary);
            }
            case 2 -> {
                showMessage("M???i b???n nh???p m?? nh??n vi??n  : ");
                String id = checkEmployeeId();
                showMessage("M???i b???n nh???p t??n nh??n vi??n  : ");
                String name = string();
                showMessage("M???i b???n nh???p tu???i nh??n vi??n : ");
                int age = checkInt();
                showMessage("M???i b???n nh???p ?????a ch??? nh??n vi??n : ");
                String address = string();
                String phone = checkInputPhoneNumber();
                showMessage("M???i b???n nh???p s??? gi??? l??m vi???c : ");
                double workingTimes = checkInt();
                return new PartTimeEmployee(id, name, age, address, phone, workingTimes);
            }
            case 0 -> menuEmployee();
            default -> showMessageErr("Nh???p d??? li???u kh??ng ????ng !!");
        }
        return null;
    }

    public void editEmployee() {
        if (menu.listEmployees().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng!");
        } else {
            String newName;
            int newAge;
            String address;
            String newPhone;
            String newEmail;
            double newHardSalary;
            Person employee;
            showMessage("M???i b???n nh???p v??o id nh??n vi??n: ");
            String id = checkIdIsTheSame();
            for (Person e : menu.listEmployees()) {
                if (id.equals(e.getId())) {
                    if (e instanceof FullTimeEmployee) {
                        showMessage("M???i b???n nh???p t??n nh??n vi??n : ");
                        newName = string();
                        showMessage("M???i b???n nh???p tu???i nh??n vi??n : ");
                        newAge = checkInt();
                        showMessage("M???i b???n nh???p ?????a ch??? nh??n vi??n : ");
                        address = string();
                        newPhone = checkInputPhoneNumber();
                        newEmail = checkInputEmail();
                        showMessage("M???i b???n nh???p l????ng c???ng nh??n vi??n : ");
                        newHardSalary = checkDouble();
                        employee = new FullTimeEmployee(id, newName, newAge, address, newPhone, newEmail, newHardSalary);
                        menu.editEmployee(employee, id);
                    } else if (e instanceof PartTimeEmployee) {
                        showMessage("M???i b???n nh???p t??n nh??n vi??n : ");
                        newName = string();
                        showMessage("M???i b???n nh???p tu???i nh??n vi??n : ");
                        newAge = checkInt();
                        showMessage("M???i b???n nh???p ?????a ch??? nh??n vi??n : ");
                        address = string();
                        newPhone = checkInputPhoneNumber();
                        showMessage("M???i b???n nh???p s??? gi??? l??m vi???c : ");
                        double newWorkTime = checkInt();
                        employee = new PartTimeEmployee(id, newName, newAge, address, newPhone, newWorkTime);
                        menu.editEmployee(employee, id);
                    }
                }
            }
        }
    }

    public void checkEmptyEmployee() {
        if (menu.listEmployees().size() == 0) {
            showMessageErr("Danh s??ch hi???n t???i ??ang tr???ng!");
        } else {
            menu.displayEmployee();
        }
    }

    public void deleteEmployee() {
        if (menu.listEmployees().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng!");
        } else {
            showMessage("Nh???p id nh??n vi??n mu???n x??a: ");
            checkId = string();
            menu.deleteByEmployee(checkId);
        }
    }

    public void salaryEmployeeFullTime() {
        if (menu.listEmployees().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng!");
        } else {
            showMessage("Nh???p id nh??n vi??n full time:  ");
            checkId = string();
            menu.salaryFullTime(checkId);
        }
    }

    public void sortByEmployees() {
        if (menu.listEmployees().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng");
        } else {
            menu.sortByEmployees();
            showMessage("successful arrangement");
        }
    }

    public void salaryEmployeePartTime() {
        if (menu.listEmployees().isEmpty()) {
            showMessageErr("Danh s??ch tr???ng!");
        } else {
            showMessage("Nh???p id nh??n vi??n part time:  ");
            checkId = string();
            menu.salaryPartTime(checkId);
        }
    }

    public String checkEmployeeId() {
        String id = string();
        for (Person o : Manager.getInstance().getEmployees()) {
            while (true) {
                if (o.getId().equals(id)) {
                    showMessageErr("id n??y ???? c?? trong danh s??ch ! Vui l??ng nh???p l???i");
                    id = input.nextLine();
                } else {
                    break;
                }
            }
        }
        return id;
    }

    public String checkIdIsTheSame() {
        String id = string();
        for (Person o : Manager.getInstance().getEmployees()) {
                if (o.getId().equals(id)) {
                    return id;
                }
            }
        showMessageErr("Id b???n nh???p kh??ng c?? trong danh s??ch! Nh???p l???i:");
        return checkIdIsTheSame();
    }

    //check input-------------------------------------------------------------------------------------------------
    public String string() {
        while (true) {
            try {
                return input.nextLine();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public int checkInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public double checkDouble() {
        while (true) {
            try {
                return Double.parseDouble(input.nextLine());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showMessageErr(String msg) {
        System.err.println(msg);
    }

    //regex-------------------------------------------------------------------------------------------------
    public String checkInputPhoneNumber() {
        while (true) {
            showMessage("Nh???p s??t: ");
            String phone = string();
            Pattern checkPhone = Pattern.compile("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$");
            if (checkPhone.matcher(phone).find()) {
                System.out.println("phone is ok");
                return phone;
            } else {
                System.err.println("phone is not ok");
            }
        }
    }

    public String checkInputEmail() {
        while (true) {
            showMessage("Nh???p email: ");
            String email = string();
            Pattern checkPhone = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
            if (checkPhone.matcher(email).find()) {
                System.out.println("email is ok");
                return email;
            } else {
                System.err.println("email is not ok");
            }
        }
    }
}

