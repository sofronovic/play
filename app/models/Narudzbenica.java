package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Narudzbenica extends Model {

	@Column(nullable = false, unique = true)
	public int brojNarudzbenice;
	
	@Column(nullable = false)
	public float kolicina;
	
	@ManyToOne
	public PoslovnaGodina poslovnaGodina;
	
	@ManyToOne
	public PoslovniPartner poslovniPartner;
	
	@OneToMany(mappedBy="narudzbenica")
	public List<Otpremnica> otpremnica;
	
	@OneToMany(mappedBy="narudzbenica")
	public List<StavkaNarudzbenice> stavkeNarudzbenice;
}
