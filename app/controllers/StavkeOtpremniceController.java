package controllers;

import java.util.List;

import models.Otpremnica;
import models.RobaUsluga;
import models.StavkeOtpremnice;
import play.data.validation.Error;
import play.data.validation.Min;
import play.data.validation.Required;
import play.mvc.Controller;

public class StavkeOtpremniceController extends Controller {
	
	public static void show(String mode){
		List<StavkeOtpremnice> stavkeOtpremnice = StavkeOtpremnice.findAll();
		List<Otpremnica> otpremnice = Otpremnica.findAll();
		List<RobaUsluga> robeUsluge = RobaUsluga.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
			render(stavkeOtpremnice, otpremnice, robeUsluge, mode);
	}
	
	public static void add(@Required @Min(1) float kolicina, float cenaPoJediniciMere, float ukupnaCena,
			long otpremnica, long robaUsluga){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			StavkeOtpremnice s = new StavkeOtpremnice();
			s.kolicina = kolicina;
			s.cenaPoJediniciMere = cenaPoJediniciMere;
			s.ukupnaCena = ukupnaCena;
			s.otpremnica = Otpremnica.findById(otpremnica);
			s.robaUsluga = RobaUsluga.findById(robaUsluga);
			s.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(float kolicina, float cenaPoJediniciMere, float ukupnaCena,
			long otpremnica, long robaUsluga, long id){
		List<StavkeOtpremnice> stavkeOtpremnice = StavkeOtpremnice.find("byKolicinaLikeAndCenaPoJediniciMereLikeAndUkupnaCenaLike"
				+ "AndOtpremnica_idAndRobaUsluga_id", kolicina, cenaPoJediniciMere, ukupnaCena, otpremnica, robaUsluga).fetch();
		String mode = "edit";
		renderTemplate("StavkeOtpremniceController/show.html", stavkeOtpremnice, mode);
	}
	
	public static void edit(@Required float kolicina, float cenaPoJediniciMere, float ukupnaCena,
			long otpremnica, long robaUsluga, long id){
		StavkeOtpremnice s = StavkeOtpremnice.findById(id);
		s.kolicina = kolicina;
		s.cenaPoJediniciMere = cenaPoJediniciMere;
		s.ukupnaCena = ukupnaCena;
		s.otpremnica = Otpremnica.findById(otpremnica);
		s.robaUsluga = RobaUsluga.findById(robaUsluga);
		s.save();
		show("");
	}
	
	public static void delete(long id){
		StavkeOtpremnice s = StavkeOtpremnice.findById(id);
		s.delete();
		show("");
	}
}
