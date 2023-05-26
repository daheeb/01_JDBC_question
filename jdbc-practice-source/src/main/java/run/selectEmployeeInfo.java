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

public class selectEmployeeInfo {

    // 사원 번호를 입력받아 해당 사원을 조회하고 DTO객체에 담아서 출력
    // 출력 구문은 DTO 객체의 toString() 내용과
    // "[이름]([부서명]) [직급명]님 환영합니다." 로 출력.

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("selectEmployee");
            System.out.println(query);

            Scanner sc = new Scanner(System.in);

            System.out.print("사원의 ID를 입력하세요. : ");
            String empId = sc.nextLine();


            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpId(empId);

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

        System.out.println("[" + "이름" + "]([" + "부서명" + "]) [" + "직급명" + "]님 환영합니다.");


    }
}
