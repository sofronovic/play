package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class PoslovnaGodina extends Model{

	@Column(unique = true, length = 4)
	public int godina;
	
	@Column
	public boolean zakljucena;
	
	@OneToMany(mappedBy="poslovnaGodina")
	public List<IzlaznaFaktura> izlaznaFaktura;
	
	@ManyToOne
	public Preduzece preduzece;
	
	@OneToMany(mappedBy="poslovnaGodina")
	public List<Otpremnica> otpremnica;
	
	@OneToMany(mappedBy="poslovnaGodina")
	public List<Narudzbenica> narudzbenica;
}
