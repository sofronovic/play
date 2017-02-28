package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class StavkeCenovnika extends Model{

	@Column(nullable = false, precision = 10, scale = 2)
	public float jedinicnaCena;
	
	@ManyToOne
	public Cenovnik cenovnik;
	
	@ManyToOne
	public RobaUsluga robaUsluga;
}
