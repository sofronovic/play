package controllers;

import java.util.Date;
import java.util.List;

import models.IstorijaPoreza;
import models.IzlaznaFaktura;
import models.Narudzbenica;
import models.PoreskaStopa;
import models.Porez;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.StavkaNarudzbenice;
import models.StavkeFakture;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Narudzbenice extends Controller {
	
	public static void show(String mode){
		List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(narudzbenice, poslovneGodine, poslovniPartneri, mode);
	}
	
	public static void add(@Required int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			Narudzbenica n = new Narudzbenica();
			List<Narudzbenica> narudzbenice = Narudzbenica.findAll();
			int noviBroj = 1;
			noviBroj += narudzbenice.size();
			n.brojNarudzbenice = noviBroj;
			n.kolicina = kolicina;
			n.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
			n.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
			n.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner){
		List<Narudzbenica> narudzbenice = Narudzbenica.find("byBrojNarudzbeniceLike", brojNarudzbenice).fetch();
/*		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();*/
		String mode = "edit";
		renderTemplate("Narudzbenice/show.html", narudzbenice, mode);
	}
	
	public static void edit(@Required int brojNarudzbenice, float kolicina, long poslovnaGodina, long poslovniPartner, long id){
		Narudzbenica n = Narudzbenica.findById(id);
		n.brojNarudzbenice = brojNarudzbenice;
		n.kolicina = kolicina;
		n.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		n.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
		n.save();
		show("");
	}
	
	public static void delete(long id){
		Narudzbenica n = Narudzbenica.findById(id);
		for(StavkaNarudzbenice i : n.stavkeNarudzbenice){
			i.delete();
		}
		n.delete();
		show("");
	}
	
	public static void generator(long id){
		Narudzbenica narudzbenica = Narudzbenica.findById(id);
		IzlaznaFaktura faktura = new IzlaznaFaktura();
		
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.findAll();
		int noviBroj = 1;
		noviBroj += izlazneFakture.size();	
		
		int pdv = 20;
		faktura.brojFakture = noviBroj;
		faktura.datumFakture = new Date();
		faktura.datumValute = new Date();
		faktura.datumObracuna = new Date();
		faktura.statusFakture = "U pripremi";
		faktura.poslovniPartner = narudzbenica.poslovniPartner;
		faktura.poslovnaGodina = narudzbenica.poslovnaGodina;
		
		float ukupanPorez = 0;
		float UkupnoRobe = 0;
		float ukupanRabat = 0;
		float iznosFakture = 0;
		float iznosFaktureOsnovica = 0;
		
		faktura.save();
		
		for(StavkaNarudzbenice stavkaNarudzbenice : narudzbenica.stavkeNarudzbenice){
			
			StavkeFakture stavkaFakture = new StavkeFakture();

			stavkaFakture.kolicina = stavkaNarudzbenice.kolicina;
			stavkaFakture.cenaPoJediniciMere = stavkaNarudzbenice.cenaPoJediniciMere;
			stavkaFakture.ukupanIznos = stavkaNarudzbenice.ukupnaCena;
			stavkaFakture.robaUsluga = stavkaNarudzbenice.robaUsluga;
			stavkaFakture.osnovica = stavkaFakture.kolicina*stavkaFakture.cenaPoJediniciMere;
			stavkaFakture.pdv = pdv/100;
			stavkaFakture.pdvIznos = stavkaFakture.osnovica * pdv/100;
			stavkaFakture.ukupanIznos = stavkaFakture.pdvIznos * stavkaFakture.osnovica;
			stavkaFakture.izlaznaFaktura = faktura;
			
			ukupanPorez += stavkaFakture.pdvIznos;
			UkupnoRobe += stavkaFakture.kolicina;
			ukupanRabat = 0;
			iznosFakture += stavkaFakture.ukupanIznos;
			iznosFaktureOsnovica += stavkaFakture.osnovica;
			
			stavkaFakture.save();
			
		}
		
		Long idf;
		
		idf = faktura.getId();
		
		if( faktura.id == idf){
			
			faktura.ukupanPorez = ukupanPorez;
			faktura.ukupnoRobe = UkupnoRobe;
			faktura.ukupanRabat = ukupanRabat;
			faktura.iznosFakture = iznosFakture;
			faktura.iznosFaktureOsnovica = iznosFaktureOsnovica;
			
			faktura.save();
		}
		
		show("");
		

		
	}

}
