package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class PoreskaStopa extends Model{
	
	@Column(nullable = false, precision = 5, scale = 2)
	public float iznosStope;
	
	@ManyToOne
	public IstorijaPoreza istorijaPoreza;
	
	@ManyToOne
	public Porez porez;
}
