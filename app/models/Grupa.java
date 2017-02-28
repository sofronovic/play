package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Grupa extends Model {

	@Column(unique = true, length=40)
	public String naziv;
	
	@ManyToOne
	public Preduzece preduzece;
	
	@OneToMany(mappedBy="grupa")
	public List<RobaUsluga> robaUsluga;
	
	@ManyToOne
	public Porez porez;
}
