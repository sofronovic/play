package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Porez extends Model{

	@Column(nullable = false, length = 120)
	public String nazivPoreza;
	
	@Column
	public boolean vazeci;
	
	@OneToMany(mappedBy="porez")
	public List<StavkeFakture> stavkeFakture;
	
	@OneToMany(mappedBy="porez")
	public List<ObracunatiPorez> obracunatiPorez;
	
	@OneToMany(mappedBy="porez")
	public List<PoreskaStopa> poreskaStopa;
	
	@OneToMany(mappedBy="porez")
	public List<Grupa> grupa;
}
