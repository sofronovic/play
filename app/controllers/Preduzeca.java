package controllers;

import java.util.List;

import models.Preduzece;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Preduzeca extends Controller {
	
	public static void show(String mode){
		List<Preduzece> preduzeca = Preduzece.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(preduzeca, mode);
	
	}
	
	public static void add(@Required  int PIB, @Required String naziv, @Required String adresa, 
			@Required int maticniBroj, @Required int sifraDelatnosti, @Required int telefon,
			String email){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			Preduzece p = new Preduzece();
			p.PIB = PIB;
			p.naziv = naziv;
			p.adresa = adresa;
			p.maticniBroj = maticniBroj;
			p.sifraDelatnosti = sifraDelatnosti;
			p.telefon = telefon;
			p.email = email;
			p.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(@Required int PIB, String naziv, String adresa, int maticniBroj, int sifraDelatnosti, int telefon,
			String email){
		List<Preduzece> preduzeca = Preduzece.find("byNazivLike", naziv).fetch();
		String mode = "edit";
		renderTemplate("Preduzeca/show.html", preduzeca, mode);
		
	}
	
	public static void edit(@Required int PIB, @Required String naziv,@Required String adresa,
			@Required int maticniBroj, @Required int sifraDelatnosti, @Required int telefon,
			String email, long id){
		Preduzece p = Preduzece.findById(id);
		p.PIB = PIB;
		p.naziv = naziv;
		p.adresa = adresa;
		p.maticniBroj = maticniBroj;
		p.sifraDelatnosti = sifraDelatnosti;
		p.telefon = telefon;
		p.email = email;
		p.save();
		show("");
	}
	
	public static void delete(long id){
		Preduzece p = Preduzece.findById(id);
		p.delete();
		show("");
	}
	
}
