package views;

import controller.ControllerManager;
import controller.LoginController;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static ControllerManager controllerManager = ControllerManager.getInstance();
    public static Scanner input = new Scanner(System.in);
    public int checkInput;
    public String checkId;

    //Login--------------------------------------------------------------------------------------------------
    public void clubCoffee() {
        while (true) {
            System.out.println("""
                    +----------LOGIN-----------+
                    |      1. Đăng nhập        |
                    |      0. Thoát            |
                    |                          |
                    +--------------------------+
                    """);
            System.out.print("Lựa chọn: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> {
                    userLogin();
                    menu();
                }
                case 0 -> System.exit(checkInput);
            }
        }

    }

    public void menu() {
        while (true) {
            System.out.println("""
                    +--------------- CLUB COFFEE -------------+
                    |        1. Quản lý nhân viên             |
                    |        2. Quản lý khách hàng            |
                    |        3. Đăng xuất                     |
                    |        0. Thoát                         |
                    +-----------------------------------------+
                    """);
            System.out.print("Lựa chọn: ");
            checkInput = checkInt();
            if (checkInput == 1) {
                adminLogin();
                menuEmployee();
            } else if (checkInput == 2) {
                menuClient();
            } else if (checkInput == 3) {
                clubCoffee();
            } else if (checkInput == 0) {
                System.exit(checkInput);
            }
        }
    }

    public void userLogin() {
        LoginView view = new LoginView();
        LoginController control = new LoginController(view);
        control.userLogin();
    }

    public void adminLogin() {
        LoginView view = new LoginView();
        LoginController control = new LoginController(view);
        control.AdminLogin();
    }
    //Client----------------------------------------------------------------------

    public void menuClient() {
        while (true) {
            System.out.println("""
                    +--------------- QUẢN LÝ HÓA ĐƠN ---------------+
                    |    1. Thêm khách hàng                         |
                    |    2. Danh sách khách mua hàng                |
                    |    3. Tính tiền theo id khách hàng            |
                    |    4. Sắp xếp theo tên khách hàng             |
                    |    5. Đăng xuất                               |
                    |    0. Thoát                                   |
                    +-----------------------------------------------+
                    """);
            System.out.print("Lựa chọn: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> controllerManager.addNewClient(addClient());
                case 2 -> controllerManager.display();
                case 3 -> prepareInvoice();
                case 4 -> {
                    controllerManager.sortClient();
                    System.out.println("successful arrangement");
                }
                case 5 -> menu();
                case 0 -> System.exit(checkInput);
                default -> System.err.println("Vui lòng chọn theo đúng menu");
            }
        }
    }

    public void prepareInvoice() {
        System.out.print("Nhập id khách hàng muốn tính tiền: ");
        checkId = string();
        System.out.println(controllerManager.totalMoney(checkId));
    }

    public Client addClient() {
        String id = duplicateCheck();
        System.out.print("Nhập tên khách hàng:");
        String name = string();
        System.out.print("Nhập tuổi khách hàng :");
        int age = checkInt();
        System.out.print("Nhập địa chỉ:");
        String address = string();
        System.out.print("Nhập sđt:");
        String phone = string();
        System.out.println("Nhập số lượng cà phê khách hàng mua:");
        int sp = checkInt();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i <= sp; i++) {
            System.out.println("thêm sản phẩm thứ " + i + ":");
            System.out.print("Tên cà phê: ");
            String nameSP = string();
            System.out.print("Giá : ");
            double price = checkDouble();
            System.out.print("Số lượng: ");
            double quantity = checkDouble();
            products.add(new Product(nameSP, price, quantity));
        }
        return new Client(id, name, age, address, phone, products);
    }

    public String duplicateCheck() {
        System.out.print("Nhập mã khách hàng:");
        String id = string();
        for (Client o : ControllerManager.getInstance().getClients()) {
            while (true) {
                if (o.getId().equals(id)) {
                    System.out.println("Trùng id ! Vui lòng nhập lại");
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
            System.out.println("""
                    +---------------QUẢN LÝ NHÂN VIÊN---------------+
                    |   1. Thêm nhân viên                           |
                    |   2. Sắp xếp danh sách nhân viên theo tên     |        \s
                    |   3. Danh sách nhân viên                      |
                    |   4. Sửa thông tin nhân viên                  |
                    |   5. Xóa nhân viên                            |
                    |   6. Tổng lương của tất cả nhân viên          |
                    |   7. Tính lương nhân viên                     |
                    |   8. Đăng xuất                                |
                    |   0. Thoát                                    |
                    +-----------------------------------------------+
                    """);
            System.out.print("Lựa chọn: ");
            checkInput = checkInt();
            switch (checkInput) {
                case 1 -> controllerManager.addNewEmployee(addEmployee());
                case 2 -> controllerManager.sortEmployees();
                case 3 -> controllerManager.displayEmployees();
                case 4 -> controllerManager.editEmployee(input);
                case 5 -> deleteEmployee();
                case 6 -> System.out.println(controllerManager.totalSalaryAllEmployees());
                case 7 -> salaryEmployee();
                case 8 -> menu();
                case 0 -> System.exit(checkInput);
                default -> System.err.println("Vui lòng nhập lại!!!");
            }
        }
    }

    public Person addEmployee() {
        System.out.println("""
                Thêm nhân viên mới :
                1. nhân viên full time
                2. nhân viên part time
                0. Quay lại""");
        System.out.print("Lựa chọn: ");
        checkInput = Integer.parseInt(input.nextLine());
        switch (checkInput) {
            case 1 -> {
                System.out.print("Mời bạn nhập mã nhân viên  : ");
                String id = string();
                System.out.print("Mời bạn nhập tên nhân viên : ");
                String name = string();
                System.out.print("Mời bạn nhập tuổi nhân viên : ");
                int age = checkInt();
                System.out.print("Mời bạn nhập địa chỉ nhân viên : ");
                String address = string();
                System.out.print("Mời bạn nhập sđt nhân viên : ");
                String phone = string();
                System.out.print("Mời bạn nhập email nhân viên : ");
                String email = string();
                System.out.print("Mời bạn nhập lương của nhân viên : ");
                double hardSalary = checkDouble();
                System.out.print("Mời bạn nhập số ngày làm việc : ");
                double workingDays = checkInt();
                return new FullTimeEmployee(id, name, age, address, phone, email, hardSalary, workingDays);
            }
            case 2 -> {
                System.out.print("Mời bạn nhập mã nhân viên  : ");
                String id = string();
                System.out.print("Mời bạn nhập tên nhân viên : ");
                String name = string();
                System.out.print("Mời bạn nhập tuổi nhân viên : ");
                int age = checkInt();
                System.out.print("Mời bạn nhập địa chỉ nhân viên : ");
                String address = string();
                System.out.print("Mời bạn nhập sđt nhân viên : ");
                String phone = string();
                System.out.print("Mời bạn nhập số giờ làm việc : ");
                double workingTimes = checkInt();
                return new PartTimeEmployee(id, name, age, address, phone, workingTimes);
            }
            case 0 -> menuEmployee();
            default -> System.err.println("Nhập dữ liệu không đúng !!");
        }
        return null;
    }

    public void deleteEmployee() {
        System.out.print("Nhập id nhân viên muốn xóa: ");
        checkId = string();
        controllerManager.deleteByEmploy(checkId);
    }

    public void salaryEmployee() {
        System.out.print("Nhập id nhân viên muốn tính lương:  ");
        checkId = string();
        System.out.print("Nhập tiền thưởng:  ");
        int bonus = checkInt();
        System.out.print("Nhập tiền phạt:  ");
        int punish = checkInt();
        System.out.println(controllerManager.salaryEmployee(checkId, bonus, punish));
    }

    //check input-------------------------------------------------------------------------------------------------
    public String string() {
        try {
            return input.nextLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public int checkInt() {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public double checkDouble() {
        try {
            return Double.parseDouble(input.nextLine());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}