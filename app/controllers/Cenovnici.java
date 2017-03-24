package controllers;

import java.util.Date;
import java.util.List;

import models.Cenovnik;
import models.Narudzbenica;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.Preduzece;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Cenovnici extends Controller{
	
	public static void show(String mode){
		List<Cenovnik> cenovnici = Cenovnik.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(cenovnici, preduzeca, mode);
	}
	
	public static void add(@Required Date datumPrimene, long preduzece){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			Cenovnik c = new Cenovnik();
			c.datumPrimene = datumPrimene;
			c.preduzece = Preduzece.findById(preduzece);
			c.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(Date datumPrimene, long preduzece){
		List<Cenovnik> cenovnici = Narudzbenica.find("byDatumPrimeneLikeAndPreduzece_id", datumPrimene, preduzece).fetch();
		String mode = "edit";
		renderTemplate("Cenovnici/show.html", cenovnici, mode);
	}
	
	public static void edit(@Required Date datumPrimene, long preduzece, long id){
		Cenovnik c = Cenovnik.findById(id);
		c.datumPrimene = datumPrimene;
		c.preduzece = Preduzece.findById(preduzece);
		c.save();
		show("");
	}
	
	public static void delete(long id){
		Cenovnik c = Cenovnik.findById(id);
		c.delete();
		show("");
	}

}
