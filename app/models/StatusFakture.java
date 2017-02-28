package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class StatusFakture extends Model{

	@Column(nullable = false, length = 10)
	public String naziv; //Obracunata, Poslata, Stornirana
	
	@OneToOne
	public IzlaznaFaktura izlaznaFaktura;
}
