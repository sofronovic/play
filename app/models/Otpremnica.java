package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Otpremnica extends Model{

	@Column(nullable = false, unique = true)
	public int brojOtpremnice;
	
	@Column
	public Date datumOtpremnice;

	@Column(nullable = false, precision = 15, scale = 2)
	public float osnovica;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float ukupanPdv;
	
	@Column(nullable = false, precision = 15, scale = 2)
	public float iznosZaPlacanje;
	
	@ManyToOne
	public PoslovniPartner poslovniPartner;
	
	@OneToMany(mappedBy="otpremnica")
	public List<IzlaznaFaktura> izlaznaFaktura;
	
	@ManyToOne
	public PoslovnaGodina poslovnaGodina;
	
	@OneToMany(mappedBy="otpremnica")
	public List<StavkeOtpremnice> stavkeOtpremnice;
	
	@ManyToOne
	public Narudzbenica narudzbenica;
}
