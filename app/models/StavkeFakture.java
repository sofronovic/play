package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class StavkeFakture extends Model {
	
	@Column(nullable = false)
	public float kolicina;
	
	@Column(nullable = false)
	public float cenaPoJediniciMere;
	
	@Column(nullable = false)
	public float rabat;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float osnovica;
	
	@ManyToOne
	public IzlaznaFaktura izlaznaFaktura;
	
	@ManyToOne
	public Porez porez;
	
	@ManyToOne
	public RobaUsluga robaUsluga;
}
