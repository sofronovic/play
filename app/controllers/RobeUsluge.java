package controllers;

import java.util.List;

import models.Grupa;
import models.Racun;
import models.RobaUsluga;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class RobeUsluge extends Controller {

	public static void show(String mode){
		List<RobaUsluga> robeUsluge = RobaUsluga.findAll();
		List<Grupa> grupe = Grupa.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(robeUsluge, grupe, mode);
	}
	
	public static void add(@Required String naziv, @Required String jedinicaMere, long grupa){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			RobaUsluga r = new RobaUsluga();
			r.naziv = naziv;
			r.jedinicaMere = jedinicaMere;
			r.grupa = Grupa.findById(grupa);
			r.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(String naziv, String jedinicaMere, long grupa){
		List<RobaUsluga> robeUsluge = RobaUsluga.find("byNazivLikeAndJedinicaMereLikeAndGrupa_id", naziv, jedinicaMere, grupa).fetch();
		String mode = "edit";
		renderTemplate("RobeUsluge/show.html", robeUsluge, mode);
	}
	
	public static void edit(@Required String naziv, @Required String jedinicaMere, long grupa, long id){
		RobaUsluga r = RobaUsluga.findById(id);
		r.naziv = naziv;
		r.jedinicaMere = jedinicaMere;
		r.grupa = Grupa.findById(grupa);
		r.save();
		show("");
	}
	
	public static void delete(long id){
		RobaUsluga r = RobaUsluga.findById(id);
		r.delete();
		show("");
	}
	
}
