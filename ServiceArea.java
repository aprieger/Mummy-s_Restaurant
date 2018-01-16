import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;

public class ServiceArea {
    public static void addServiceArea(String name, int areaCode, int packageId, double taxRate) {
            String updateStr = ("INSERT INTO ServiceAreas (Area_Id, Name, Area_Code, Package_Id, Tax_Rate)"
                    + " VALUES ("+ServiceArea.getNextServiceAreaId()+",'"+name+"',"+packageId+","+areaCode+","+packageId+","+taxRate+")");
            Utilities.sendUpdate(updateStr);
    }
    
    public static void editPackageId(int editServiceAreaId, int newPackageId) {
        Utilities.sendUpdate("UPDATE ServiceAreas SET Package_Id="
                +newPackageId+" WHERE Area_Id="+editServiceAreaId);
    }
    
    public static void editName(int editServiceAreaId, String newName) {
        Utilities.sendUpdate("UPDATE ServiceAreas SET Name='"
                +newName+"' WHERE Area_Id="+editServiceAreaId);
    }
    
    public static void editAreaCode(int editServiceAreaId, int newAreaCode) {
        Utilities.sendUpdate("UPDATE ServiceAreas SET Area_Code="
                +newAreaCode+" WHERE Area_Id="+editServiceAreaId);
    }
    
    public static void editTaxRate(int editServiceAreaId, double newTaxRate) {
        Utilities.sendUpdate("UPDATE ServiceAreas SET Tax_Rate="
                +newTaxRate+" WHERE Area_Id="+editServiceAreaId);
    }
    
    public static void deleteServiceArea(int deleteServiceAreaId) {
        Utilities.sendUpdate("DELETE FROM ServiceAreas WHERE Area_Id="+deleteServiceAreaId);
    }
    
    public static int searchServiceAreaIdByName(String searchName) {
        try {
            ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT Area_Id FROM Pkg_Orders WHERE Name='"+searchName+"'");
            if (!resultsAL.isEmpty())
                return Integer.parseInt(resultsAL.get(0).get("AREA_ID").toString())+1;
            else
                return 0;
        } catch (JSONException e) {return -1;}
    } 
    
    public static int getNextServiceAreaId() {
        try {
            ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT MAX(Area_Id) FROM ServiceAreas");
            if (!resultsAL.isEmpty())
                return Integer.parseInt(resultsAL.get(0).get("AREA_ID").toString())+1;
            else
                return 1;
        } catch (JSONException e) {return -1;}
    }
    
    public static String getStringFromJSON(ArrayList<JSONObject> resultsAL) {
        if (!resultsAL.isEmpty()) {
            String output="";
            try {
                int rowCount = resultsAL.size();
                String[] columnNames = new String[]{"AREA_ID","PACKAGE_ID","NAME","AREA_CODE","TAX_RATE"};
                int columnCount = columnNames.length;
                for (int i=0; i<rowCount; i++) {
                    for (int j=0; j<columnCount; j++) {
                        if (resultsAL.get(i).has(columnNames[j]))
                            output += columnNames[j] + ": " + resultsAL.get(i).get(columnNames[j]) + "\n";
                        else
                            output += columnNames[j] + ":\n";
                    }
                }
                return output;
            } catch (JSONException e) {return output+e;}
        }
        else
            return "";
    }
    
    public static ArrayList<JSONObject> getSingleServiceAreaData(int inputPackageId) {
        return Utilities.sendQuery("SELECT * FROM ServiceAreas WHERE Area_Id="+inputPackageId);
    }
    
    public static ArrayList<JSONObject> getAllServiceAreaData(int inputPackageId) {
        return Utilities.sendQuery("SELECT * FROM ServiceAreas");
    }
}
