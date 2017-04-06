package controllers;

import java.util.Date;
import java.util.List;

import models.Narudzbenica;
import models.Otpremnica;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.StavkeOtpremnice;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Otpremnice extends Controller {

	public static void show(String mode){
		List<Otpremnica> otpremnice = Otpremnica.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(otpremnice, poslovniPartneri, poslovneGodine, narudzbenice, mode);
	}
	
	public static void add(@Required int brojOtpremnice, Date datumOtpremnice, float osnovica, float ukupanPdv,
			float iznosZaPlacanje, long poslovniPartner, long poslovnaGodina, long narudzbenica){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			Otpremnica o = new Otpremnica();
			o.brojOtpremnice = brojOtpremnice;
			o.datumOtpremnice = datumOtpremnice;
			o.osnovica = osnovica;
			o.ukupanPdv = ukupanPdv;
			o.iznosZaPlacanje = iznosZaPlacanje;
			o.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
			o.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
			o.narudzbenica = Narudzbenica.findById(narudzbenica);
			o.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int brojOtpremnice, Date datumOtpremnice, float osnovica, float ukupanPdv,
			float iznosZaPlacanje, long poslovniPartner, long poslovnaGodina, long narudzbenica){
			List<Otpremnica> otpremnice = Otpremnica.find("byBrojOtpremniceLikeAndDatumOtpremniceLikeAndOsnovicaLikeAndUkupanPdvLikeAnd"
				+ "IznosZaPlacanjeLikeAndPoslovniPartner_idAndPoslovnaGodina_idAndNarudzbenica_id", brojOtpremnice, datumOtpremnice, osnovica, ukupanPdv,
				iznosZaPlacanje, poslovniPartner, poslovnaGodina, narudzbenica).fetch();
			String mode = "edit";
			renderTemplate("Otpremnice/show.html", otpremnice, mode);
	}
	
	public static void edit(@Required int brojOtpremnice, Date datumOtpremnice, float osnovica, float ukupanPdv,
			float iznosZaPlacanje, long poslovniPartner, long poslovnaGodina, long narudzbenica, long id){
		Otpremnica o = Otpremnica.findById(id);
		o.brojOtpremnice = brojOtpremnice;
		o.datumOtpremnice = datumOtpremnice;
		o.osnovica = osnovica;
		o.ukupanPdv = ukupanPdv;
		o.iznosZaPlacanje = iznosZaPlacanje;
		o.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
		o.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		o.narudzbenica = Narudzbenica.findById(narudzbenica);
		o.save();
		show("");
	}
	
	public static void delete(long id){
		Otpremnica o = Otpremnica.findById(id);
		for(StavkeOtpremnice i: o.stavkeOtpremnice){
			i.delete();
		}
		o.delete();
		show("");
	}
}

