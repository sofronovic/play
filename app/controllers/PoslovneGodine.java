package controllers;

import java.util.Calendar;
import java.util.List;

import models.PoslovnaGodina;
import models.Preduzece;
import models.Racun;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class PoslovneGodine extends Controller {
	
	public static void show(String mode){
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(poslovneGodine, preduzeca, mode);
	}
	
	public static void add( int godina, boolean zakljucena, long preduzece){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			PoslovnaGodina p = new PoslovnaGodina();
			p.godina = godina;
			p.zakljucena = zakljucena;
			p.preduzece = Preduzece.findById(preduzece);
			
			p.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int godina, boolean zakljucena, long preduzece){
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.find("byGodinaLike", godina).fetch();
		String mode = "edit";
		renderTemplate("PoslovneGodine/show.html", poslovneGodine, mode);
	}
	
	public static void edit(@Required int godina, boolean zakljucena, long preduzece, long id){
		PoslovnaGodina p = PoslovnaGodina.findById(id);
		p.godina = godina;
		p.zakljucena = zakljucena;
		p.preduzece = Preduzece.findById(preduzece);
		p.save();
		show("");
	}
	
	public static void delete(long id){
		PoslovnaGodina p = PoslovnaGodina.findById(id);
		p.delete();
		show("");
	}

}
