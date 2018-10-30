package pl.coderslab.controller;

import pl.coderslab.dao.SolutionDao;
import pl.coderslab.model.Solution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SolutionController_Get",urlPatterns = "/main")
public class SolutionController_Get extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sol_id = request.getParameter("solution_id");
        if(sol_id!=null && !sol_id.isEmpty()){
            try{
                int id = Integer.parseInt(sol_id);
                Solution solution = SolutionDao.findById(id);
                SolutionDao.delete(solution);
            }catch (NumberFormatException e){

            }
            doGet(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Solution> solutions = SolutionDao.findLastItems(5);
        request.setAttribute("solutions",solutions);
        request.getRequestDispatcher("/main.jsp").forward(request,response);
    }
}
