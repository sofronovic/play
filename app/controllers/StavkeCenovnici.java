package controllers;

import java.util.List;

import models.Cenovnik;
import models.Narudzbenica;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.RobaUsluga;
import models.StavkeCenovnika;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class StavkeCenovnici extends Controller {
	
	public static void show(String mode){
		List<StavkeCenovnika> stavkeCenovnika = StavkeCenovnika.findAll();
		List<Cenovnik> cenovnici = Cenovnik.findAll();
		List<RobaUsluga> robaUsluga = RobaUsluga.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(stavkeCenovnika, cenovnici, robaUsluga, mode);
	}
	
	public static void add(@Required float jedinicnaCena, long cenovnik, long robaUsluga){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			StavkeCenovnika s = new StavkeCenovnika();
			s.jedinicnaCena = jedinicnaCena;
			s.cenovnik = Cenovnik.findById(cenovnik);
			s.robaUsluga = RobaUsluga.findById(robaUsluga);
			s.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(float jedinicnaCena, long cenovnik, long robaUsluga){
		List<StavkeCenovnika> stavkeCenovnika = StavkeCenovnika.find("byJedinicnaCenaLikeAndCenovnik_idAndRobaUsluga_id", jedinicnaCena, cenovnik, robaUsluga).fetch();
		String mode = "edit";
		renderTemplate("StavkeCenovnici/show.html", stavkeCenovnika, mode);
	}
	
	public static void edit(@Required float jedinicnaCena, long cenovnik, long robaUsluga, long id){
		StavkeCenovnika n = StavkeCenovnika.findById(id);
		n.jedinicnaCena = jedinicnaCena;
		n.cenovnik = Cenovnik.findById(cenovnik);
		n.robaUsluga = RobaUsluga.findById(robaUsluga);
		n.save();
		show("");
	}
	
	public static void delete(long id){
		StavkeCenovnika n = StavkeCenovnika.findById(id);
		n.delete();
		show("");
	}

}
