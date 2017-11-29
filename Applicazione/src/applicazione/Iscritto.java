/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicazione;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Cassa
 */

// CLASSE ISCRITTO
public class Iscritto {
    
    private final SimpleStringProperty ID;
	private final SimpleStringProperty Nome;
	private final SimpleStringProperty Cognome;
	private final SimpleStringProperty Sesso;
	private final SimpleStringProperty DataNascita;
	private final SimpleStringProperty LuogoNascita;
	private final SimpleStringProperty Comune;
	private final SimpleStringProperty Indirizzo;
	private final SimpleStringProperty CAP;
	private final SimpleStringProperty Provincia;
	private final SimpleStringProperty Regione;
	private final SimpleStringProperty Cellulare;
	private final SimpleStringProperty Email;
	
	
	public Iscritto(String ID,String Nome,String Cognome,String Sesso,String DataNascita,String LuogoNascita,String Comune,
			String Indirizzo, String CAP,String Provincia,String Regione, String Cellulare,String Email){
		
		this.ID = new SimpleStringProperty(ID);
		this.Nome = new SimpleStringProperty (Nome);
		this.Cognome = new SimpleStringProperty(Cognome);
		this.Sesso = new SimpleStringProperty(Sesso);
		this.DataNascita = new SimpleStringProperty(DataNascita);
		this.LuogoNascita = new SimpleStringProperty(LuogoNascita);
		this.Comune = new SimpleStringProperty(Comune);
		this.Indirizzo = new SimpleStringProperty(Indirizzo);
		this.CAP = new SimpleStringProperty(CAP);
		this.Provincia = new SimpleStringProperty(Provincia);
		this.Regione = new SimpleStringProperty(Regione);
		this.Cellulare = new SimpleStringProperty(Cellulare);
		this.Email = new SimpleStringProperty(Email);
		
	}

	public String getID() {
		return ID.get();
	}

	public String getNome() {
		return Nome.get();
	}

        public String getCognome() {
		return Cognome.get();
	}
	public String getSesso() {
		return Sesso.get();
	}

	public String getDataNascita() {
		return DataNascita.get();
	}
	
	public String getLuogoNascita() {
		return LuogoNascita.get();
	}
	
	public String getComune() {
		return Comune.get();
	}
	
	public String getIndirizzo() {
		return Indirizzo.get();
	}
	
	public String getCAP() {
		return CAP.get();	
	}
	
	public String getProvincia() {
		return Provincia.get();
	}
	
	public String getRegione() {
		return Regione.get();
	}
	
	public String getCellulare() {
		return Cellulare.get();
	}
	
	public String getEmail() {
		return Email.get();
	}
	


	public void setID(String id){
		ID.set(id);
	}
	
	public void setNome(String nome){
		Nome.set(nome);
	}
	
	
	public void setCognome(String cognome){
		Cognome.set(cognome);
	}
	
	public void setSesso(String sesso){
		Sesso.set(sesso);
	}
	
	public void setDataNascita(String datanascita){
		DataNascita.set(datanascita);
	}

	public void setLuogoNascita(String luogonascita){
		LuogoNascita.set(luogonascita);
	}

	public void setComune(String comune){
		Comune.set(comune);
	}
	
	public void setIndirizzo(String indirizzo){
		Indirizzo.set(indirizzo);
	}

	public void setCAP(String cap){
		CAP.set(cap);
	}

	public void setProvincia(String provincia){
		Provincia.set(provincia);
	}
	
	public void setRegione(String regione){
		Regione.set(regione);
	}

	public void setCellulare(String cellulare){
		Cellulare.set(cellulare);
	}

	public void setEmail(String email){
		Email.set(email);
	}

	
}
