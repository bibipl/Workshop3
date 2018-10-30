package pl.coderslab.dao;



import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SolutionDao {


    public static Solution save(Solution solution){
        return null;
    }

    public static Solution findById(int id){
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(id));
        List<Solution> list = prepareSolutions("SELECT id, description, created, updated FROM solution WHERE id=?",params);
        if(list!=null && list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    public static List<Solution> findAll(){
        return prepareSolutions("SELECT id, description, created, updated FROM solution",null);
    }


    public static List<Solution> findLastItems(int limit){


        return prepareSolutions("SELECT id, description, created, updated FROM solution ORDER BY id DESC LIMIT "+limit,null);
    }
    public static void delete(Solution solution){
        List<String> params = new ArrayList<>();
        params.add(String.valueOf(solution.getId()));

        try {
            DbService.executeUpdate("DELETE FROM solution WHERE id=?",params);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static List<Solution> prepareSolutions(String q, List<String> params){
        List<Solution> solutions = null;
        try {

            List<String[]> list = DbService.getData(q,params);
            solutions = new ArrayList<>();
            for(String[] item: list){
                Solution solutionItem = new Solution();
                solutionItem.setId(Integer.parseInt(item[0]));
                solutionItem.setDescription(item[1]);

                SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //solutionItem.setCreated(item[2]);
               // solutionItem.setUpdated(st.parse(item[3]));
                solutions.add(solutionItem);
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
        //} catch (ParseException e) {
            e.printStackTrace();
        }
        return solutions;
    }

}
