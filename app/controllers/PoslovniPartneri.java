package controllers;

import java.util.List;

import models.IzlaznaFaktura;
import models.Narudzbenica;
import models.ObracunatiPorez;
import models.Otpremnica;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.Preduzece;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class PoslovniPartneri extends Controller {

	public static void show (String mode){
		List<PoslovniPartner> poslovniPartner = PoslovniPartner.findAll();
		List<Preduzece> preduzece = Preduzece.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(poslovniPartner, preduzece, mode);
	}
	
	
	public static void add(@Required String nazivPartnera,@Required String adresa, String vrstaPartnera, long preduzece)
	{
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			PoslovniPartner poslovniPartner = new PoslovniPartner();
			poslovniPartner.nazivPartnera = nazivPartnera;
			poslovniPartner.adresa = adresa;
			poslovniPartner.vrstaPartnera = vrstaPartnera;
			poslovniPartner.preduzece = Preduzece.findById(preduzece);
			poslovniPartner.save();
			validation.keep();
			show("add");
	}
	}
	
	
	public static void filter(String nazivPartnera){
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.find("byNazivPartneraLike", nazivPartnera).fetch();
		String mode = "edit";
		renderTemplate("PoslovniPartneri/show.html", poslovniPartneri, mode);
	}
	
	
	public static void edit(String nazivPartnera, String adresa, String vrstaPartnera, long preduzece,
			 long id)
	{
		
		PoslovniPartner poslovniPartner = PoslovniPartner.findById(id);
		poslovniPartner.nazivPartnera = nazivPartnera;
		poslovniPartner.adresa = adresa;
		poslovniPartner.vrstaPartnera = vrstaPartnera;
		poslovniPartner.preduzece = Preduzece.findById(preduzece);
		poslovniPartner.save();
		show("");
	}
	
	public static void delete(long id)
	{
		PoslovniPartner poslovniPartner = PoslovniPartner.findById(id);
		poslovniPartner.delete();
		show("");
	}
}

