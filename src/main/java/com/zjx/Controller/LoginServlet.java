package com.zjx.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/loginservlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        HttpSession session = req.getSession();
        resp.setContentType("text/html;charset=UTF-8");
        String username = req.getParameter("username");
        String userpwd = req.getParameter("upwd");
//        String rembpwd = req.getParameter("rembpwd");
//        String sessionID = session.getId();
//        session.setAttribute("Username","123456");
        System.out.println(username+"  ;" + userpwd + "  ;"  );
        resp.getWriter().write("succeed");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        resp.setContentType("text/html;charset=UTF-8");
        String username = req.getParameter("username");
        String userpwd = req.getParameter("upwd");
//        String rembpwd = req.getParameter("rembpwd");
//        String sessionID = session.getId();
//        session.setAttribute("Username","123456");
        System.out.println(username+"  ;" + userpwd + "  ;"  );
        resp.getWriter().write("succeed");

    }

}
