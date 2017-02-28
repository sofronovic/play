package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Preduzece extends Model{
	
	@Column(nullable = false)
	public int PIB; 
	
	@Column(length = 30, nullable = false)
	public String naziv;
	
	@Column(nullable = false, length = 50)
	public String adresa;
	
	@Column(nullable = false)
	public int maticniBroj;
	
	@Column(nullable = false)
	public int sifraDelatnosti;
	
	@Column(nullable = false)
	public int telefon;
	
	@Column(nullable = false, length = 50)
	public String email;
	
	@OneToMany(mappedBy="preduzece")
	public List<PoslovniPartner> poslovniPartner;
	
	@OneToMany(mappedBy="preduzece")
	public List<PoslovnaGodina> poslovnaGodina;
	
	@OneToMany(mappedBy="preduzece")
	public List<Racun> racun;
	
	@OneToMany(mappedBy="preduzece")
	public List<Cenovnik> cenovnik;
	
	@OneToMany(mappedBy="preduzece")
	public List<Grupa> grupa;
	
	@OneToMany(mappedBy="preduzece")
	public List<IstorijaPoreza> istorijaPoreza;
}
