package pl.coderslab.model;

import java.sql.*;
import java.util.ArrayList;

// pobranie wszystkich rozwiązań danego użytkownika (dopisz metodę loadAllByUserId do klasy Solution),

public class Solution {
//CREATE TABLE solution (id int AUTO_INCREMENT PRIMARY KEY, created DATETIME, updated DATETIME, description text, exercise_id int NOT NULL, users_id BIGINT(20) NOT NULL,FOREIGN KEY(exercise_id) REFERENCES exercise(id), FOREIGN KEY(users_id) REFERENCES users(id)) default character set utf8 collate utf8_polish_ci;

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private Exercise exercise;
    private User user;

    public Solution() {
    }

    public Solution(Date created, Date updated, String description, Exercise excercise, User user) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.exercise = excercise;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Exercise getExcercise_id() {
        return exercise;
    }
    public void setExcercise_id(Exercise excercise) {
        this.exercise = excercise;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    // Zapisz do  BD zapisuje nowy element do BD ub zmodyfikowany element. Poznajmy po id==0
    public void saveToDB(Connection conn) throws SQLException {
        if (this.id == 0) {
            String sql = "INSERT INTO solution(created, updated, description, exercise_id, users_id) VALUES (?, ?, ?, ?, ?)";
            String[] generatedColumns = {"ID"};
            PreparedStatement preparedStatement = conn.prepareStatement(sql, generatedColumns);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise.getId());
            preparedStatement.setInt(5, this.user.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } else {
            String sql = "UPDATE solution SET created=?, updated=?, description=?, exercise_id=?, users_id=? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setDate(1, this.created);
            preparedStatement.setDate(2, this.updated);
            preparedStatement.setString(3, this.description);
            preparedStatement.setInt(4, this.exercise.getId()); // pobieramy z pola GR ident GRid
            preparedStatement.setInt(5, this.user.getId()); // pobieramy z pola GR ident GRid
            preparedStatement.setInt(6, this.id); // pobieramy z pola GR ident GRid
            preparedStatement.executeUpdate();
        }
    }

    // Wczytaj 1 solution po id. Metoda statyczna dlatego przekzujemy dodatkowo id
    static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM solution where id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));
            return loadedSolution;}
        return null;}

    // Wczytaj wszystkich z BD
    static public Solution[] loadAllSolutions(Connection conn) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));  // tu wczytujemy exercise o exerciseId i przekazujemy do javy
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));// tu wczytujemy user o userId i przekazujemy do javy
            solutions.add(loadedSolution);
        }
        Solution[] solutionArray = new Solution[solutions.size()];
        solutionArray = solutions.toArray(solutionArray);
        return solutionArray;
    }

    // usuń exercise zBD
    public void delete(Connection conn) throws SQLException {
        if (this.id != 0) {
            String sql = "DELETE FROM solution WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, this.id);
            preparedStatement.executeUpdate();
            this.id = 0;
        }
    }


//pobranie wszystkich rozwiązań danego użytkownika (dopisz metodę loadAllByUserId do klasy Solution)
    static public Solution[] loadAllByUserId (Connection conn, int userId) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution WHERE users_id=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));  // tu wczytujemy exercise o exerciseId i przekazujemy do javy
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));// tu wczytujemy user o userId i przekazujemy do javy
            solutions.add(loadedSolution);
        }
        Solution[] solutionArray = new Solution[solutions.size()];
        solutionArray = solutions.toArray(solutionArray);
        return solutionArray;
    }
    //pobranie wszystkich rozwiązań danego zadania, posortowanych od najnowszego do najstarszego (dopisz metodę loadAllByExerciseId do klasy Solution),
    static public Solution[] loadAllByExerciseId (Connection conn, int exerciseId) throws SQLException {
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        String sql = "SELECT * FROM solution WHERE exercise_id=? ORDER BY created ASC";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, exerciseId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Solution loadedSolution = new Solution();
            loadedSolution.id = resultSet.getInt("id");
            loadedSolution.created = resultSet.getDate("created");
            loadedSolution.updated = resultSet.getDate("updated");
            loadedSolution.description = resultSet.getString("description");
            loadedSolution.exercise = Exercise.loadExerciseById(conn, resultSet.getInt("exercise_id"));  // tu wczytujemy exercise o exerciseId i przekazujemy do javy
            loadedSolution.user = User.loadUserById(conn, resultSet.getInt("users_id"));// tu wczytujemy user o userId i przekazujemy do javy
            solutions.add(loadedSolution);
        }
        Solution[] solutionArray = new Solution[solutions.size()];
        solutionArray = solutions.toArray(solutionArray);
        return solutionArray;
    }


    @Override
    public String toString() {
        return " |Solution[" + id +
                "], created='" + created +
                " | updated='" + updated +
                " | '" + description + '\''+
                user.showPrintUser() + " | " + exercise.showPrintExercise();
    }
    public String showByUser() {
        return " |"+user.showPrintUser() + " | " + exercise.showPrintExercise()+
                " | Solution[" + id +
                "], created='" + created +
                " | updated='" + updated +
                " | '" + description + '\'';
    }

    public String showByExercise() {
        return " |"+exercise.showPrintExercise()+" | " +
                user.showPrintUser() +
                " | Solution[" + id +
                "], created='" + created +
                " | updated='" + updated +
                " | '" + description + '\'';
    }
    public String showPrintSolution () {
        return "Solution["+ id +"]: "+ user.getName()+ " | "+ user.getGroup() + " | "+exercise.getTitle();
    }


} // last class bracket
