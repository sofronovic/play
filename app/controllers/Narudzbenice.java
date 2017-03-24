package controllers;

import java.util.List;

import models.IstorijaPoreza;
import models.Narudzbenica;
import models.PoreskaStopa;
import models.Porez;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Narudzbenice extends Controller {
	
	public static void show(String mode){
		List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(narudzbenice, poslovneGodine, poslovniPartneri, mode);
	}
	
	public static void add(@Required int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			Narudzbenica n = new Narudzbenica();
			n.brojNarudzbenice = brojNarudzbenice;
			n.kolicina = kolicina;
			n.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
			n.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
			n.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner){
		List<Narudzbenica> narudzbenice = Narudzbenica.find("byBrojNarudzbeniceLikeAndKolicinaLikeAndPoslovnaGodina_idAndPoslovniPartner_id", brojNarudzbenice, kolicina, poslovnaGodina, poslovniPartner).fetch();
/*		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();*/
		String mode = "edit";
		renderTemplate("Narudzbenice/show.html", narudzbenice, mode);
	}
	
	public static void edit(@Required int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner, long id){
		Narudzbenica n = Narudzbenica.findById(id);
		n.brojNarudzbenice = brojNarudzbenice;
		n.kolicina = kolicina;
		n.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		n.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
		n.save();
		show("");
	}
	
	public static void delete(long id){
		Narudzbenica n = Narudzbenica.findById(id);
		n.delete();
		show("");
	}

}
