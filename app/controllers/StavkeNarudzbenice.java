package controllers;

import java.util.List;

import models.Narudzbenica;
import models.RobaUsluga;
import models.StavkaNarudzbenice;
import play.data.validation.Error;
import play.data.validation.Min;
import play.data.validation.Required;
import play.mvc.Controller;

public class StavkeNarudzbenice extends Controller {
	
	public static void show(String mode){
		
		List<StavkaNarudzbenice> stavkeNarudzbenice = StavkaNarudzbenice.findAll();
		List<RobaUsluga> robaUsluga = RobaUsluga.findAll();
		List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
		
		if(mode == null || mode.equals(""))
			mode = "edit";
		render(stavkeNarudzbenice, robaUsluga, narudzbenice, mode);
	}
	
	public static void add(@Required @Min(1) float kolicina, @Required float cenaPoJediniciMere, long robaUsluga, long narudzbenica){
		
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			StavkaNarudzbenice stavka = new StavkaNarudzbenice();
			stavka.kolicina = kolicina;
			stavka.cenaPoJediniciMere = cenaPoJediniciMere;
			stavka.ukupnaCena = kolicina*cenaPoJediniciMere;
			stavka.robaUsluga = RobaUsluga.findById(robaUsluga);
			stavka.narudzbenica = Narudzbenica.findById(narudzbenica);
			
			Narudzbenica n = Narudzbenica.findById(narudzbenica);
			n.kolicina+=stavka.kolicina;
			stavka.save();
			n.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(long robaUsluga, long narudzbenica, float kolicina, float cenaPoJediniciMere, float ukupnaCena){
		
		List<StavkaNarudzbenice> stavkeNarudzbenice = StavkaNarudzbenice.find("byRobaUsluga_idAndNarudzbenica_idAndKolicinaAndCenaPoJediniciMereAndUkupnaCenaLike", robaUsluga, narudzbenica, kolicina, cenaPoJediniciMere, ukupnaCena).fetch();
		List<RobaUsluga> roba = RobaUsluga.findAll();
		List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
		String mode = "edit";
		renderTemplate("StavkeNarudzbenice/show.html", stavkeNarudzbenice, roba, narudzbenice, mode);
		
	}
	
	public static void edit(float kolicina, float cenaPoJediniciMere, long robaUsluga, long narudzbenica, long id){
		StavkaNarudzbenice stavke = StavkaNarudzbenice.findById(id);
		stavke.cenaPoJediniciMere = cenaPoJediniciMere;
		stavke.robaUsluga = RobaUsluga.findById(robaUsluga);
		stavke.narudzbenica = Narudzbenica.findById(narudzbenica);
		Narudzbenica n = Narudzbenica.findById(narudzbenica);
		
		float novaKolicina = n.kolicina - stavke.kolicina;
		stavke.kolicina = kolicina;
		
		stavke.ukupnaCena = kolicina*cenaPoJediniciMere;
		n.kolicina = novaKolicina + kolicina;
		n.save();
		stavke.save();
		show("");
	}
	
	public static void delete(long id){
		StavkaNarudzbenice stavka = StavkaNarudzbenice.findById(id);
		stavka.delete();
		show("");
	}

}
