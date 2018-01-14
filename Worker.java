/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

public class Worker extends Person{
    
    private int employeeId;
    private int isAdmin;
    private int isActive;
    private Login login;
    
public Worker(){
    login = new Login();
}
    public Login getLogin() {
        return login;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int user_id) {
        this.employeeId = user_id;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int is_Admin) {
        this.isAdmin = is_Admin;
    }
}

