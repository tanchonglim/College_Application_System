/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import bean.Room;
import bean.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.JDBCUtility;

/**
 *
 * @author Ong Shi Bing
 */
@WebServlet(name = "AdminSelectRoomByIDServlet", urlPatterns = {"/AdminSelectRoomByIDServlet"})
public class AdminSelectRoomByIDServlet extends HttpServlet {

    private JDBCUtility jdbcUtility;
    private Connection con;

    @Override
    public void init() throws ServletException
    {
        String driver = "com.mysql.jdbc.Driver";

        String dbName = "college_application";
        String url = "jdbc:mysql://localhost/" + dbName + "?allowMultiQueries=true";
        String userName = "root";
        String password = "";

        jdbcUtility = new JDBCUtility(driver,
                                      url,
                                      userName,
                                      password);

        jdbcUtility.jdbcConnect();
        con = jdbcUtility.jdbcGetConnection();
        jdbcUtility.prepareSQLStatemenSelectRoomByID();
    }

    public void destroy() {
        jdbcUtility.jdbcConClose();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null||!((User) session.getAttribute("user")).getUserType().equals("admin")){
            response.sendRedirect(request.getContextPath() + "/notAuthorized.jsp");
            return;
        }
        
        int cID = Integer.parseInt(request.getParameter("cid"));
        String collegeName = "";
        if(request.getParameter("cname") != null){
            collegeName = request.getParameter("cname");
        }

        //retrieve from database
        try {
            PreparedStatement ps = jdbcUtility.getpsSelectRoomByID();
            ps.setInt(1, cID);
            ResultSet rs = ps.executeQuery();


            ArrayList roomList = new ArrayList();
            Room room;

            while(rs.next()) {

                int roomID = rs.getInt("roomID");
                String roomName = rs.getString("roomName");
                int collegeID = rs.getInt("collegeID");
                Date addedDate = rs.getDate("addedDate");
                String roomType = rs.getString("roomType");
                int activated = rs.getInt("activated");
                int capacity = rs.getInt("capacity");
                int occupied = rs.getInt("occupied");

                room = new Room();
                room.setRoomID(roomID);
                room.setRoomName(roomName);
                room.setCollegeID(collegeID);
                room.setAddedDate(addedDate);
                room.setRoomType(roomType);
                room.setActivated(activated);
                room.setCapacity(capacity);
                room.setOccupied(occupied);

                roomList.add(room);
            }
            session.setAttribute("collegeName", collegeName);
            request.setAttribute("roomList", roomList);
            request.getRequestDispatcher("adminViewRoom.jsp?cid="+cID).forward(request, response);

            if(!request.getParameter("success").equals("")){
                response.sendRedirect(request.getContextPath() + "/adminViewRoom.jsp?success=" + request.getParameter("success"));
            }else if(!request.getParameter("message").equals("")){
                response.sendRedirect(request.getContextPath() + "/AdminSelectAllRoomByIDServlet?message=" + request.getParameter("message"));
            }
            else{
                response.sendRedirect(request.getContextPath() + "/adminViewRoom.jsp");
            }

           /*if(insertStatus == 1)
            response.sendRedirect(request.getContextPath() + "/adminViewRoom.jsp?cid="+cID);
           else
             response.sendRedirect(request.getContextPath() + "/adminHome.jsp");*/
        } catch (SQLException ex) {
            //failed
            while (ex != null) {
                System.out.println ("SQLState: " + ex.getSQLState ());
                System.out.println ("Message:  " + ex.getMessage ());
		System.out.println ("Vendor:   " + ex.getErrorCode ());
                ex = ex.getNextException ();
		System.out.println ("");
            }
            response.sendRedirect(request.getContextPath() + "/adminHome.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
