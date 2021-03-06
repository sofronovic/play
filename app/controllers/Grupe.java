package controllers;

import java.util.List;

import models.Grupa;
import models.Porez;
import models.Preduzece;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Grupe extends Controller{
	
	public static void show(String mode){
	List<Grupa> grupe = Grupa.findAll();
	List<Preduzece> preduzeca = Preduzece.findAll();
	List<Porez> porezi = Porez.findAll();
	if(mode == null || mode.equals("")){
		mode = "edit";
	}
		render(grupe, preduzeca, porezi, mode);
	}

	public static void add(@Required String naziv, long preduzece, long porez){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			Grupa g = new Grupa();
			g.naziv = naziv;
			g.preduzece = Preduzece.findById(preduzece);
			g.porez = Porez.findById(porez);
			g.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(String naziv){
		List<Grupa> grupe = Grupa.find("byNazivLike", naziv).fetch();
		List<Preduzece> preduzeca = Preduzece.findAll();
		List<Porez> porezi = Porez.findAll();
		String mode = "edit";
		renderTemplate("Grupe/show.html", grupe, preduzeca, porezi, mode);
	}
	
	public static void edit(@Required String naziv, long preduzece, long porez, long id){
		Grupa g = Grupa.findById(id);
		g.naziv = naziv;
		g.preduzece = Preduzece.findById(preduzece);
		g.porez = Porez.findById(porez);
		g.save();
		show("");
	}
	
	public static void delete(long id){
		Grupa g = Grupa.findById(id);
		g.delete();
		show("");
	}
}
