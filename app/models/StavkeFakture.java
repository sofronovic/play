package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class StavkeFakture extends Model {
	
	@Column(nullable = true)
	public float kolicina;
	
	@Column(nullable = true)
	public float cenaPoJediniciMere;
	
	@Column(nullable = true)
	public float rabat;
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float osnovica;
	
	@Column(nullable = true)
	public float pdvIznos; 
	
	@Column(nullable = true)
	public float ukupanIznos;
	
	@Column(nullable = true)
	public float pdv;
	
	@ManyToOne
	public IzlaznaFaktura izlaznaFaktura;
	
	
	@ManyToOne
	public RobaUsluga robaUsluga;
}
