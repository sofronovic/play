package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Racun extends Model {
	
	@Column(nullable = false, length = 30)
	public int brojRacuna;

	@Column(nullable = false, length = 30)
	public String banka;
	
	@ManyToOne
	public Preduzece preduzece;
}
