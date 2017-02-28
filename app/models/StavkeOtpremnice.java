package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class StavkeOtpremnice extends Model {
	
	@Column(nullable = false)
	public float kolicina;
	
	@Column(nullable = false)
	public float cenaPoJediniciMere;
	
	@Column(nullable = false)
	public float ukupnaCena;
	
	@ManyToOne
	public Otpremnica otpremnica;
	
	@ManyToOne
	public RobaUsluga robaUsluga;
}
