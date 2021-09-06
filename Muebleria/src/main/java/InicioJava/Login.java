/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InicioJava;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author emili
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("contrasena");

        LoginModel loginM = new LoginModel();
        int tipoPersona = loginM.devolverTipoPersona(nombre, password);
        //Verificamos que tipo de usuario es y buscamos en la tabla respectiva
        switch (tipoPersona) {
            case 1:
                //FABRICANTE
                request.getSession().setAttribute("nombre", nombre);
                request.getRequestDispatcher("InicioFabrica.jsp").forward(request, response);
                break;
            case 2:
                //fiNANCIERA
                request.getSession().setAttribute("nombre", nombre);
                request.getRequestDispatcher("/AreaFinanciera/InicioFinanciera.jsp").forward(request, response);
                break;
            case 3:
                //VENTAS
                request.getSession().setAttribute("nombre", nombre);
                request.getRequestDispatcher("/AreaVenta/InicioVenta.jsp").forward(request, response);
                break;
            case 0:
                request.setAttribute("fail", true);
                request.getRequestDispatcher("/Inicio/Login.jsp").forward(request, response);
                break;
        }
    }

}

