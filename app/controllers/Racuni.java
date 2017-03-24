package controllers;

import java.util.List;

import models.Grupa;
import models.Preduzece;
import models.Racun;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Racuni extends Controller {
	
	public static void show(String mode){
		List<Racun> racuni = Racun.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
			render(racuni, preduzeca, mode);
		}
	
	public static void add(@Required int brojRacuna, String banka, long preduzece){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			Racun r = new Racun();
			r.brojRacuna = brojRacuna;
			r.banka = banka;
			r.preduzece = Preduzece.findById(preduzece);
			r.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int brojRacuna, String banka, long preduzece){
		List<Racun> racuni = Racun.find("byBrojRacunaLikeAndBankaLikeAndPreduzece_id", brojRacuna, banka, preduzece).fetch();
		String mode = "edit";
		renderTemplate("Racuni/show.html", racuni, mode);
	}
	
	public static void edit(@Required int brojRacuna, String banka, long preduzece, long id){
		Racun r = Racun.findById(id);
		r.brojRacuna = brojRacuna;
		r.banka = banka;
		r.preduzece = Preduzece.findById(preduzece);
		r.save();
		show("");
	}
	
	public static void delete(long id){
		Racun r = Racun.findById(id);
		r.delete();
		show("");
	}
}
