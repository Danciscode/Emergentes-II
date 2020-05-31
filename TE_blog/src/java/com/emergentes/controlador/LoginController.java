package com.emergentes.controlador;

import com.emergentes.utiles.ConectaBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //login
        String action = (request.getParameter("action") == null) ? "view" : request.getParameter("action");
        if (action.equals("view")) {
            response.sendRedirect("login.jsp");
        }
        if (action.equals("logout")) {
            HttpSession ses = request.getSession();
            ses.invalidate();
            response.sendRedirect("login.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String pass = request.getParameter("pass");

        ConectaBD canal = new ConectaBD();
        Connection conn = canal.conectar();
        try {
            String sql = "select * from usuarios where usuario=? and password=? limit 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, pass);
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession ses = request.getSession();
                ses.setAttribute("logueado", "ok");
                ses.setAttribute("usuario", usuario);
                response.sendRedirect("MainController");
            } else {
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException ex) {
            System.out.println("Error de SQL" + ex.getMessage());
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
