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
	
	@Column(unique = true, length = 1, nullable = false)
	public String tipFakture;
	
	@Column(nullable = false)
	public Date datumFakture;
	
	@Column(nullable = false)
	public Date datumValute;
	
	@Column(nullable = false)
	public Date datumObracuna;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float ukupnoRobe;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float ukupanRabat;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float ukupanPorez;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float iznosFakture;
	
	@Column(nullable = false, length = 40)
	public String uplataNaRacun;
	
	@Column(nullable = false, length = 20)
	public String pozivNaBroj;
	
	@OneToMany(mappedBy="izlaznaFaktura")
	public List<StavkeFakture> stavkeFakture;
	
	@OneToMany(mappedBy="izlaznaFaktura")
	public List<ObracunatiPorez> obracunatiPorez;
	
	@OneToOne
	public StatusFakture statusFakture;
	
	@ManyToOne
	public PoslovniPartner poslovniPartner;
	
	@ManyToOne
	public PoslovnaGodina poslovnaGodina;
	
	@ManyToOne
	public Otpremnica otpremnica;
}
