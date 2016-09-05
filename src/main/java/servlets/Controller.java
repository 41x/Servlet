package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Enumeration;

/**
 * Created by iskra on 04.09.2016.
 */
@WebServlet(name = "Controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();
    Enumeration<String> params = request.getParameterNames();

    Connection c = null;
    PreparedStatement stmt = null;
    String result = "[";

    try {
      Class.forName("org.postgresql.Driver");
      c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/custom", "postgres", "123cdtnrf");
      c.setAutoCommit(false);

      String query = "select lastname,firstname,middlename,city.name as cname,carnum,color,model from person " +
              "inner join city on person.id_city=city.id " +
              "inner join car on person.id_car=car.id where ";
      while (params.hasMoreElements()) {
        String name = params.nextElement();
        query += name + "= ? and ";
      }

      query = query.replaceAll("\\sand\\s$", "");
      stmt=c.prepareStatement(query);
      params = request.getParameterNames();
      int i=0;
      while (params.hasMoreElements()) {
        String name = params.nextElement();
        stmt.setString(++i,request.getParameter(name));
      }

//      out.write(query);
//      out.close();

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String middlename = rs.getString("middlename");
        String cityname = rs.getString("cname");
        String carnum = rs.getString("carnum");
        String color = rs.getString("color");
        String model = rs.getString("model");
        result += String.format("{\"firstname\":\"%s\"," +
                " \"lastname\":\"%s\"," +
                "\"middlename\":\"%s\"," +
                "\"cname\":\"%s\"," +
                "\"carnum\":\"%s\"," +
                "\"color\":\"%s\"," +
                "\"model\":\"%s\"},", firstname, lastname, middlename, cityname, carnum, color, model);
      }
      result = result.replaceAll(",$", "") + "]";
      rs.close();
      stmt.close();
      c.close();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    out.write(result);
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }
}
