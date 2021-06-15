/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Tester {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner scanner = new Scanner(System.in);

        HocVien hv = new HocVien();
        QLHocVien qlhv = new QLHocVien();

        System.out.println("***** ENGLISH TEST PROGRAM *****\n");
        System.out.println("1. == Log in ==");
        System.out.println("2. == Register ==");
        System.out.println("3. == Exit ==");

        String mk = null;
        String username = null;
        String kw = null;
        int choice = 0, dem = 0;
        boolean check;
        do {
            System.out.print("\n==> Your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    do {
                        dem++;
                        System.out.println("\n=== LOG IN ===");
                        System.out.print(" + Enter Username: ");
                        username = scanner.nextLine();
                        System.out.print(" + Enter Password: ");
                        mk = scanner.nextLine();
                        check = qlhv.dangNhap(scanner, username, mk);
                        if (check == false) {
                            System.out.println("==> Wrong information. Please log in again...!!!");
                        } else {
                            System.out.println("==> Logged in successfully...!!!");
                        }
                        if (dem == 3 && check == false) {
                            System.exit(0);
                        }
                    } while (check == false);
                    break;
                case 2:
                	System.out.println("\n=== REGISTER ===");
                    System.out.print(" + Enter Username: ");
                    username = scanner.nextLine();
                    System.out.print(" + Enter Password: ");
                    mk = scanner.nextLine();
                    hv.dangKi(scanner, username, mk);
                    System.out.println("==> Registered information:");
                    hv.xemThongTinHocVien(username);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("==> Out of choices !!!");
            }
        } while (choice < 1 || choice > 3);

        String kiTu;
        String lv = "";
        QLCauHoi qlch = new QLCauHoi();
        double diem = 0;
        do {
            System.out.println("\n======================= MENU =======================");
            System.out.println("1. ===   Delete The Account                      ===");
            System.out.println("2. ===   View Personal Information               ===");
            System.out.println("3. ===   Update Personal Information             ===");
            System.out.println("4. ===   Statistics Of Training Results By Month ===");
            System.out.println("5. ===   Practice                                ===");
            System.out.println("6. ===   Search Questions By Keyword (or Level)  ===");
            System.out.println("7. ===   Search Questions By Category            ===");
            System.out.println("8. ===   See list of students                    ===");
            System.out.println("9. ===   Search Students By Date Of Birth        ===");
            System.out.println("10.===   Search Students By Name,Hometown,Gender ===");
            System.out.println("11.===   Exit                                    ===");
            System.out.print("\n => Your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    qlhv.xoaUser(username, mk);
                    dem = 0;
                    do {
                        dem++;
                        System.out.println("=== LOG IN ===");
                        System.out.print(" + Enter Username: ");
                        username = scanner.nextLine();
                        System.out.print(" + Enter Password: ");
                        mk = scanner.nextLine();
                        check = qlhv.dangNhap(scanner, username, mk);
                        if (check == false) {
                            System.out.println("==> Wrong information. Please log in again...!!!");
                        } else {
                            System.out.println("==> Logged in successfully...!!!");
                        }
                        if (dem == 3 && check == false) {
                            System.exit(0);
                        }
                    } while (check == false);
                    break;
                case 2:
                    System.out.println("\n=== PERSONAL INFORMATION OF ACCOUNT ===");
                    hv.xemThongTinHocVien(username);
                    break;
                case 3:
                	System.out.println("\n==== UPDATE PERSONAL INFORMATION ====");
                    System.out.println("1. ===     Update Full Name       ===");
                    System.out.println("2. ===     Change Password        ===");
                    System.out.println("3. ===     Update Hometown        ===");
                    System.out.println("4. ===     Update Gender          ===");
                    System.out.println("5. ===     Update Date Of Birth   ===");
                    System.out.print("\n=> Your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    String change = null;
                    switch (choice) {
                        case 1:
                            System.out.print("\n=> Update Full name: ");
                            change = scanner.nextLine();
                            break;
                        case 2:
                            System.out.print("\n=> Update Password: ");
                            change = scanner.nextLine();
                            break;
                        case 3:
                            System.out.print("\n=> Update Hometown: ");
                            change = scanner.nextLine();
                            break;
                        case 4:
                            System.out.print("\n=> Update gender: :");
                            change = scanner.nextLine();
                            break;
                        case 5:
                            System.out.print("\n=> Update Date of birth(yyyy-MM-dd): ");
                            change = scanner.nextLine();
                            break;
                    }
                    qlhv.capNhatThongTin(scanner, username, choice, change);
                    if (choice == 2) {
                        mk = change;
                    }
                    break;
                case 4:
                    System.out.println("\n=== TRAINING RESULTS BY MONTH ===");
                    hv.thongKeTheoThang(username);
                    break;
                case 5:
                	System.out.println("\n======== PRACTICE ========");
                    System.out.println("Question Types Want To Practice");
                    System.out.println("1. ===   MultipleChoice  ===");
                    System.out.println("2. ===   Incomplete      ===");
                    System.out.println("3. ===   Conversaion     ===");
                    System.out.print("\n=> Your choice: ");
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            int sl = 0;
                            System.out.print("\n=> Enter the quantity: ");
                            sl = scanner.nextInt();
                            scanner.nextLine();
                            diem = qlch.chamDiem(qlch.practiceMultipleChoice(scanner,username, sl));
                            hv.luuThongTinLuyenTap(username, sl, "Multiplechoice", diem);
                            System.out.printf("=== Your score: %.2f ===\n",diem);
                            scanner.nextLine();
                            break;
                        case 2:
                            scanner.nextLine();
                            System.out.print("\nEnter the level of questions (easy, medium, hard) to practice: ");
                            lv = scanner.nextLine();
                            diem = qlch.chamDiem(qlch.practiceIncomplete(scanner,username, lv));
                            hv.luuThongTinLuyenTap(username, 1, "Incomplete", diem);
                            System.out.printf("=== Your score: %.2f ===\n",diem);
                            scanner.nextLine();
                            break;
                        case 3:
                            scanner.nextLine();
                            System.out.print("\nEnter the level of questions (easy, medium, hard) to practice: ");
                            lv = scanner.nextLine();
                            diem = qlch.chamDiem(qlch.practiceConversation(scanner,username, lv));
                            hv.luuThongTinLuyenTap(username, 1, "Conversation", diem);
                            System.out.printf("=== Your score: %.2f ===\n",diem);
                            scanner.nextLine();
                            break;
                        default:
                            System.out.println("Out of choices !!!");
                    }
                    break;
                case 6:
                    System.out.print("\nSearch the level (easy, medium, hard) or content of the question: ");
                    kw = scanner.nextLine();
                    List<CauHoi> h = new ArrayList<>();
                    System.out.println("== > Search Results: ");
                    h = qlch.timKiemCauHoi(scanner, kw);
                    for (int i = 0; i < h.size(); i++) {
                    	System.out.print(i + 1 +". ");
                        System.out.println(h.get(i).getNoiDung());
                    }
                    break;
                case 7:
                    System.out.print("\nSearch the question category (adv, adj, verb,...): ");
                    kw = scanner.nextLine();
                    List<CauHoi> k = new ArrayList<>();
                    System.out.println("== > Search Results: ");
                    k = qlch.timKiemCauHoiTheoDanhMuc(scanner, kw);
                    for (int i = 0; i < k.size(); i++) {
                    	System.out.print(i + 1 +". ");
                        System.out.println(k.get(i).getNoiDung());
                    }
                    break;
                case 8:
                	System.out.println("\n=== List of students: ===");
                	qlhv.xemDSHocVien();
                	break;
                case 9:
                	System.out.print("\nEnter student's date of birth (yyyy-MM-dd): ");
                    kw = scanner.nextLine();
                    System.out.println("== > Search Results: ");
                    qlhv.traCuuTheoNgaySinh(scanner, kw);
                	break;
                case 10:
                	System.out.print("\nEnter the keyword: ");
                    kw = scanner.nextLine();
                    System.out.println("== > Search Results: ");
                    qlhv.traCuuTheoKw(scanner, kw);
                	break;
                case 11:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Out of choices !!!");
            }
            System.out.print("\nDo you want to continue(Y: Yes/N: No): ");
            kiTu = scanner.nextLine().toLowerCase();
            if (kiTu.toLowerCase().equals("y") == false) {
                System.exit(0);
            }
        } while (kiTu.equals("y"));
    }
}
