package controllers;

import java.util.Date;
import java.util.List;

import models.IstorijaPoreza;
import models.Narudzbenica;
import models.Preduzece;
import models.RobaUsluga;
import models.StavkaNarudzbenice;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class IstorijePoreza extends Controller {

	public static void show(String mode){
		List<IstorijaPoreza> istorijePoreza = IstorijaPoreza.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(istorijePoreza, preduzeca);
		
	}
	
	public static void add(Date date, long preduzece){
			
			if(validation.hasErrors()){
				for(Error error : validation.errors()){
					System.out.println(error.message());
				}
				validation.keep();
				show("add");
			}else {
				IstorijaPoreza istorija = new IstorijaPoreza();
				istorija.datumPrimene = date;
				istorija.preduzece = Preduzece.findById(preduzece);
				istorija.save();
				validation.keep();
				show("add");
			}
	}
	
	public static void filter(long preduzece, Date datum){
		
		List<IstorijaPoreza> istorijePoreza = IstorijaPoreza.find("byPreduzece_id", preduzece).fetch();
		List<Preduzece> preduzeca = Preduzece.findAll();
		String mode = "edit";
		renderTemplate("IstorijePoreza/show.html", istorijePoreza, preduzeca, mode);
		
	}
	
	public static void edit(Date datum, long preduzece, long id){
		IstorijaPoreza istorija = IstorijaPoreza.findById(id);
		istorija.datumPrimene = datum;
		istorija.preduzece = IstorijaPoreza.findById(preduzece);
		istorija.save();
		show("");
	}
	
	public static void delete(long id){
		IstorijaPoreza istorija = IstorijaPoreza.findById(id);
		istorija.delete();
		show("");
	}

}
