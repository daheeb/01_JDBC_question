package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class insertEmployeeInfo {

    // 사원 정보 전체를 입력받아 DTO객체에 담아서 insert
    // insert 성공하면 "직원 등록에 성공하였습니다." 출력
    // insert 실패하면 "직원 등록에 실패하였습니다." 출력

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));

            String query = prop.getProperty("insertEmployee");

            Scanner sc = new Scanner(System.in);

            System.out.print("사원의 ID를 입력하세요. : ");
            String empId = sc.nextLine();
            System.out.print("사원의 이름 입력하세요. : ");
            String empName = sc.nextLine();
            System.out.print("사원의 직원번호를 입력하세요. : ");
            String empNo = sc.nextLine();
            System.out.print("사원의 이메일을 입력하세요. : ");
            String email = sc.nextLine();
            System.out.print("사원의 휴대폰 번호를 입력하세요. : ");
            String phone = sc.nextLine();



            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpId(empId);
            employeeDTO.setEmpName(empName);
            employeeDTO.setEmpNo(empNo);
            employeeDTO.setEmail(email);
            employeeDTO.setPhone(phone);


            pstmt = con.prepareStatement(query);
            pstmt.setString(1, employeeDTO.getEmpId());
            pstmt.setString(2, employeeDTO.getEmpName());
            pstmt.setString(3, employeeDTO.getEmpNo());
            pstmt.setString(4, employeeDTO.getEmail());
            pstmt.setString(4, employeeDTO.getPhone());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
            close(con);
        }

        if (result > 0) {
            System.out.println("메뉴 등록 성공!");
        } else {
            System.out.println("메뉴 등록 실패!");
        }
    }

}
