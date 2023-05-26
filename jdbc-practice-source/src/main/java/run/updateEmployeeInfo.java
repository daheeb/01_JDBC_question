package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class updateEmployeeInfo {

    // 수정할 사원 번호를 입력받고
    // 사원 정보(전화번호, 이메일, 부서코드, 급여, 보너스)를 입력받아 DTO객체에 담아서 update
    // update 성공하면 "직원 정보 수정에 성공하였습니다." 출력
    // update 실패하면 "직원 정보 수정에 실패하였습니다." 출력
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/menu-query.xml"));
            String query = prop.getProperty("updateMenu");

            Scanner sc = new Scanner(System.in);

            System.out.print("수정할 사원의 ID를 입력하세요 : ");
            String empId = sc.nextLine();
            System.out.print("수정할 사원의 전화번호를 입력하세요 : ");
            String phone = sc.nextLine();
            System.out.print("수정할 사원의 이메일을 입력하세요 : ");
            String email = sc.nextLine();
            System.out.print("수정할 사원의 부서코드를 입력하세요 : ");
            String deptCode = sc.nextLine();
            System.out.print("수정할 사원의 급여를 입력하세요 : ");
            int salary = sc.nextInt();
            System.out.print("수정할 사원의 보너스를 입력하세요 : ");
            int bonus = sc.nextInt();

            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpId(empId);
            employeeDTO.setPhone(phone);
            employeeDTO.setEmail(email);
            employeeDTO.setDeptCode(deptCode);
            employeeDTO.setSalary(salary);
            employeeDTO.setBonus(bonus);

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, empId);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);
            pstmt.setString(4, deptCode);
            pstmt.setInt(5, salary);
            pstmt.setInt(6, bonus);


            result = pstmt.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }

        if(result > 0) {
            System.out.println("메뉴 수정 성공!");
        } else {
            System.out.println("메뉴 수정 실패!");
        }

    }
}
