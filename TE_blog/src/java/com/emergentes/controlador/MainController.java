package com.emergentes.controlador;

import com.emergentes.modelo.Blog;
import com.emergentes.utiles.ConectaBD;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Danci
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String op;
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";
        ArrayList<Blog> lista = new ArrayList<>();
        ConectaBD canal = new ConectaBD();
        Connection conn = canal.conectar();

        PreparedStatement ps;
        ResultSet rs;
        if (op.equals("list")) {
            try {
                String sql = "select * from blogs order by id desc";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                HttpSession ses = request.getSession();
                while (rs.next()) {
                    Blog b = new Blog();
                    b.setId(rs.getInt("id"));
                    b.setFecha(rs.getString("fecha"));
                    b.setTitulo(rs.getString("titulo"));
                    b.setContenido(rs.getString("contenido"));
                    b.setUsuario((String)ses.getAttribute("usuario"));
                    lista.add(b);

                }

                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error en SQL" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        if (op.equals("nuevo")) {
            Blog nb = new Blog();

            request.setAttribute("blog", nb);
            request.getRequestDispatcher("editar.jsp").forward(request, response);

        }
        if (op.equals("eliminar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));

                String sql = "delete from blogs where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error de SQL" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainController");
        }
        if (op.equals("editar")) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "select * from blogs where id=?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                Blog eb = new Blog();
                while (rs.next()) {
                    eb.setId(rs.getInt("id"));
                    eb.setFecha(rs.getString("fecha"));
                    eb.setTitulo(rs.getString("titulo"));
                    eb.setContenido(rs.getString("contenido"));

                }
                request.setAttribute("blog", eb);
                request.getRequestDispatcher("editar.jsp").forward(request, response);

            } catch (SQLException ex) {
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String fecha = request.getParameter("fecha");
        String titulo = request.getParameter("titulo");
        String contenido = request.getParameter("contenido");
        Blog b = new Blog();
        b.setFecha(fecha);
        b.setTitulo(titulo);
        b.setContenido(contenido);

        ConectaBD canal = new ConectaBD();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        if (id == 0) {
            try {
                String sql;
                HttpSession ses = request.getSession();
                sql="select id from usuarios where usuario=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, (String) ses.getAttribute("usuario"));
                rs = ps.executeQuery();
                int id_user=0;
                while(rs.next()){
                    id_user=rs.getInt("id");
                }
 
                sql="insert into blogs (fecha,titulo,contenido,usuario) values (?,?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, b.getFecha());
                ps.setString(2, b.getTitulo());
                ps.setString(3, b.getContenido());
                ps.setInt(4, id_user);

                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error de SQL" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainController");

        } else {
            try {
                String sql = "update blogs set fecha=?,titulo=?,contenido=? where id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, b.fecha);
                ps.setString(2, b.titulo);
                ps.setString(3, b.contenido);
                ps.setInt(4, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error al actualizar" + ex.getMessage());
            }
            response.sendRedirect("MainController");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
