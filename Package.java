import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONException;

public class Package {  
    public static void addPackage(String addName, String addDescription, 
            int addMealCategory, String addImageSource, double addPrice, 
            int addIsSpecial, int addMealType) {

            String updateStr = ("INSERT INTO Packages (Package_Id, Name, Description, Meal_Category, Image_Source, Price, Is_Special, Meal_Type)"
                    + " VALUES ("+Package.getNextPackageId()+",'"+addName+"','"+addDescription+"',"+addMealCategory+",'"+addImageSource+"',"+addPrice+","+addIsSpecial+","+addMealType+")");
            Utilities.sendUpdate(updateStr);
    }
    
    public static void editName(int editPackageId, String newName) {
        Utilities.sendUpdate("UPDATE Packages SET Name='"
                +newName+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editDescription(int editPackageId, String newDescription) {
        Utilities.sendUpdate("UPDATE Packages SET Description='"
                +newDescription+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editMealCategory(int editPackageId, int newMealCategory) {
        Utilities.sendUpdate("UPDATE Packages SET Meal_Category="
                +newMealCategory+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editImageSource(int editPackageId, String newImageSource) {
        Utilities.sendUpdate("UPDATE Packages SET Image_Source='"
                +newImageSource+"' WHERE Package_Id="+editPackageId);
    }
    
    public static void editPrice(int editPackageId, double newPrice) {
        Utilities.sendUpdate("UPDATE Packages SET Price="
                +newPrice+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editIsSpecial(int editPackageId, int newIsSpecial) {
        Utilities.sendUpdate("UPDATE Packages SET Is_Special="
                +newIsSpecial+" WHERE Package_Id="+editPackageId);
    }
    
    public static void editMealType(int editPackageId, int newMealType) {
        Utilities.sendUpdate("UPDATE Packages SET Meal_Type="
                +newMealType+" WHERE Package_Id="+editPackageId);
    }
    
    public static void deletePackage(int deletePackageId) {
        Utilities.sendUpdate("DELETE FROM Packages WHERE Package_Id="+deletePackageId);
    }
    
    public static int getNextPackageId() {
        try {
        ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT MAX(Package_Id) FROM Packages");
        if (!resultsAL.isEmpty())
            return Integer.parseInt(resultsAL.get(0).get("MAX(PACKAGE_ID)").toString())+1;
        else
            return 1;
        } catch (JSONException e) {return -1;}
    }
    
    public static ArrayList<JSONObject> getSinglePackageData(int inputPackageId) {
        return Utilities.sendQuery("SELECT * FROM Packages WHERE Package_Id="+inputPackageId);
    }
    public static ArrayList<JSONObject> getAllPackageData() {
        return Utilities.sendQuery("SELECT * FROM Packages");
    }
    
    public static String getMenuString() {
        ArrayList<JSONObject> resultsAL = Utilities.sendQuery("SELECT Package_Id, Name, Meal_Category, Price, Is_Special, Meal_Type FROM Packages ORDER BY Package_Id ASC");
        String output = String.format("%1$60s","Menu\n");
        output+= "--------------------------------------------------------------------------------------------------------------------------------------------\n";
        output+=String.format("%-20s%-20s%-20s%-20s%-20s%-20s%n","|Package Number","|Name",
                "|Meal Category","|Price", "|Specialty Item","|Meal Type");
        output+= "--------------------------------------------------------------------------------------------------------------------------------------------\n";
        if (!resultsAL.isEmpty()) {
            try {
                int rowCount = resultsAL.size();
                String[] columnNames = new String[]{"PACKAGE_ID","NAME","MEAL_CATEGORY","PRICE","IS_SPECIAL","MEAL_TYPE"};
                int columnCount = columnNames.length;
                for (int i=0; i<rowCount; i++) {
                    for (int j=0; j<columnCount; j++) {
                        if (resultsAL.get(i).has(columnNames[j])) {
                            if (columnNames[j].equals("MEAL_CATEGORY")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    //output += String.format("%-20s","|"+resultsAL.get(aLIndex).get(subALIndex));
                                    output += String.format("%-20s","|Breakfast");
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += String.format("%-20s","|Lunch");
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("2"))
                                    output += String.format("%-20s","|Dinner");
                            }
                            else if (columnNames[j].equals("IS_SPECIAL")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    output += String.format("%-20s","|Regular");
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += String.format("%-20s","|Special");
                            }
                            else if (columnNames[j].equals("MEAL_TYPE")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    output += String.format("%-20s","|Vegetarian");
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += String.format("%-20s","|Non-Vegetarian");
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("2"))
                                    output += String.format("%-20s","|Spicy");
                            }
                            else
                                output += String.format("%-20s","|"+resultsAL.get(i).get(columnNames[j]));
                        }
                    }
                    output += String.format("\n");
                }
                return output;
            } catch (JSONException e) {return output+e;}
        }
        else
            return "";
    }
        
    public static String getStringFromJSON(ArrayList<JSONObject> resultsAL) {
        if (!resultsAL.isEmpty()) {
            String output="";
            try {
                int rowCount = resultsAL.size();
                String[] columnNames = new String[]{"PACKAGE_ID","NAME","DESCRIPTION","MEAL_CATEGORY","IMAGE_SOURCE","PRICE","IS_SPECIAL","MEAL_TYPE"};
                int columnCount = columnNames.length;
                for (int i=0; i<rowCount; i++) {
                    for (int j=0; j<columnCount; j++) {
                        if (resultsAL.get(i).has(columnNames[j])) {
                            if (columnNames[j].equals("MEAL_CATEGORY")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    output += columnNames[j] + ": Breakfast\n";
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += columnNames[j] + ": Lunch\n";
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("2"))
                                    output += columnNames[j] + ": Dinner\n";
                            }
                            else if (columnNames[j].equals("IS_SPECIAL")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    output += columnNames[j] + ": Regular\n";
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += columnNames[j] + ": Special\n";
                            }
                            else if (columnNames[j].equals("MEAL_TYPE")) {
                                if (resultsAL.get(i).get(columnNames[j]).toString().equals("0"))
                                    output += columnNames[j] + ": Vegetarian\n";
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("1"))
                                    output += columnNames[j] + ": Non-Vegetarian\n";
                                else if (resultsAL.get(i).get(columnNames[j]).toString().equals("2"))
                                    output += columnNames[j] + ": Spicy\n";
                            }
                            else
                                output += columnNames[j] + ": " + resultsAL.get(i).get(columnNames[j]) + "\n";
                        }
                    }
                }
                return output;
            } catch (JSONException e) {return output+e;}
        }
        else
            return "";
    }
}
