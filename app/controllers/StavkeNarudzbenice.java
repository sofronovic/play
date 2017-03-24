package controllers;

import java.util.List;

import models.Narudzbenica;
import models.RobaUsluga;
import models.StavkaNarudzbenice;
import play.data.validation.Error;
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
	
	public static void add(@Required float kolicina, @Required float cenaPoJediniciMere, @Required float ukupnaCena, long robaUsluga, long narudzebnica){
		
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
			stavka.ukupnaCena = ukupnaCena;
			stavka.robaUsluga = RobaUsluga.findById(robaUsluga);
			stavka.narudzbenica = Narudzbenica.findById(narudzebnica);
			stavka.save();
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
	
	public static void edit(float kolicina, float cenaPoJediniciMere, float ukupnaCena, long robaUsluga, long narudzebnica, long id){
		StavkaNarudzbenice stavke = StavkaNarudzbenice.findById(id);
		stavke.kolicina = kolicina;
		stavke.cenaPoJediniciMere = cenaPoJediniciMere;
		stavke.ukupnaCena = ukupnaCena;
		stavke.robaUsluga = RobaUsluga.findById(robaUsluga);
		stavke.narudzbenica = Narudzbenica.findById(narudzebnica);
		stavke.save();
		show("");
	}
	
	public static void delete(long id){
		StavkaNarudzbenice stavka = StavkaNarudzbenice.findById(id);
		stavka.delete();
		show("");
	}

}
