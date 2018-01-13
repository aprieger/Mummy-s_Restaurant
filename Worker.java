/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;





public class Worker extends Person{
    
    private int userId;
    private int isAdmin;
    private int isActive;

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int is_Admin) {
        this.isAdmin = is_Admin;
    }
}

