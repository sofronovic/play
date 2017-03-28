package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class IzlaznaFaktura extends Model {

	@Column(unique = true, nullable = false)
	public int brojFakture;

	@Column(nullable = false)
	public Date datumFakture;
	
	@Column(nullable = true)
	public Date datumValute;
	
	@Column(nullable = true)
	public Date datumObracuna;
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float ukupnoRobe;
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float ukupanRabat;
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float ukupanPorez; //pdv iznos ++
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float iznosFakture;
	
	@Column(nullable = true, precision = 15, scale = 2)
	public float iznosFaktureOsnovica; //todo
	
	@Column(nullable = true, length = 40)
	public String uplataNaRacun;
	
	@Column(nullable = true, length = 20)
	public String pozivNaBroj;
	
	@OneToMany(mappedBy="izlaznaFaktura")
	public List<StavkeFakture> stavkeFakture;
	
	@OneToMany(mappedBy="izlaznaFaktura")
	public List<ObracunatiPorez> obracunatiPorez;
	
	@Column(nullable = false) 
	public String statusFakture;
	
	@ManyToOne
	public PoslovniPartner poslovniPartner;
	
	@ManyToOne
	public PoslovnaGodina poslovnaGodina;
	
	@ManyToOne
	public Otpremnica otpremnica;
}
