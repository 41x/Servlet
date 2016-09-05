package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;

/**
 * Created by iskra on 04.09.2016.
 */
@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    Enumeration<String> params = request.getParameterNames();

    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/custom","postgres", "123cdtnrf");
      c.setAutoCommit(false);
      stmt = c.createStatement();
      String query="select firstname,lastname,middlename,id_city,city.name,id_car,carnum,color,model from person \n" +
              "inner join city on person.id_city=city.id\n" +
              "inner join car on person.id_car=car.id where ";
      while (params.hasMoreElements()) {
        String name=params.nextElement();
        query+=name+"="+request.getParameter(name)+" and ";
      }
      query=query.substring(0,query.lastIndexOf(" and "));
out.write(query);

//
//      ResultSet rs = stmt.executeQuery(query);
//
//      while ( rs.next() ) {
//        int id = rs.getInt("id");
//        String  name = rs.getString("name");
//        int age  = rs.getInt("age");
//        String  address = rs.getString("address");
//        float salary = rs.getFloat("salary");
//        System.out.println( "ID = " + id );
//        System.out.println( "NAME = " + name );
//        System.out.println( "AGE = " + age );
//        System.out.println( "ADDRESS = " + address );
//        System.out.println( "SALARY = " + salary );
//        System.out.println();
//      }
//      rs.close();
//      stmt.close();
//      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName()+": "+ e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");


  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
