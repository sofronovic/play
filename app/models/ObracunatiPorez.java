package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class ObracunatiPorez extends Model {

	@Column(nullable = false, precision = 5, scale = 2)
	public float stopa;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float iznos;
	
	@ManyToOne
	public Porez porez;
	
	@ManyToOne
	public IzlaznaFaktura izlaznaFaktura;
}
