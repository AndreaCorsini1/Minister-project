package applicazione;

import javafx.beans.property.SimpleStringProperty;

public class User {

    	private final SimpleStringProperty id;
		private final SimpleStringProperty username;
        private final SimpleStringProperty password;
        private final SimpleStringProperty permessi;
        

 
        public User(String id,String username, String password, String permessi) {
        	this.id = new SimpleStringProperty(id);
            this.username = new SimpleStringProperty(username);
            this.password = new SimpleStringProperty(password);
            this.permessi = new SimpleStringProperty(permessi);
        }
        
        public String getId() {
        	return id.get();
        }
        
        public String getUsername() {
        	return username.get();
        }

        public String getPassword() {
			return password.get();
		}

        public String getPermessi() {
			return permessi.get();
		}
        
        public void setId(String Id){
        	id.set(Id);
        }
        
        public void setUsername(String Username){
        	username.set(Username);
        }
        
        public void setPassword(String passWord){
        	password.set(passWord);
        }
        
        public void setPermessi(String perMessi){
        	permessi.set(perMessi);
        }
            
    }
