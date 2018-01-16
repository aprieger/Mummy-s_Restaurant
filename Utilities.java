import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Utilities {
    public static ArrayList<JSONObject> sendQuery(String queryStr) {
        try (Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","hr","hr");
                Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery(queryStr);
            ResultSetMetaData rsmd = rs.getMetaData();
            
            ArrayList<JSONObject> resultsAL = new ArrayList();
            while(rs.next()) {
                int totalColumns = rsmd.getColumnCount();
                JSONObject columnValuePair = new JSONObject();
                //At every row, the column and its value are mapped...
                for (int i = 0; i < totalColumns; i++) {
                    //..and then put into the JSONObject
                    columnValuePair.put(rsmd.getColumnName(i+1),
                            rs.getObject(i+1));
                }
                //Which is added to this ArrayList to be returned
                resultsAL.add(columnValuePair);
            }
            return resultsAL;
        } catch (Exception ex) {return null;}
    }

    public static void sendUpdate(String queryStr) {
        try (Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE","hr","hr");
                Statement stmt = conn.createStatement();){
            stmt.executeUpdate(queryStr);
        } catch (Exception ex) {}
    }
    
    public static String printJSON(ArrayList<JSONObject> resultsAL) {
        if (!resultsAL.isEmpty()) {
            String output="";
            try {
                int rowCount = resultsAL.size();
                String[] columnNames = new String[]{"EMPLOYEE_ID","FIRST_NAME","LAST_NAME", "JOB_ID", "DEPARTMENT_ID","SALARY"};
                int columnCount = columnNames.length;
                for (int i=0; i<rowCount; i++) {
                    for (int j=0; j<columnCount; j++) {
                        if (resultsAL.get(i).has(columnNames[j]))
                            output += columnNames[j] + ": " + resultsAL.get(i).get(columnNames[j]) + "\n";
                        else
                            output += columnNames[j] + ":\n";
                    }
                    output +="\n";
                }
                return output;
            } catch (Exception e) {return output+e;}
        }
        else
            return "";
    }
}
