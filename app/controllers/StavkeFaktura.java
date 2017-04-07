package controllers;

import java.util.Date;
import java.util.List;

import models.IzlaznaFaktura;
import models.Narudzbenica;
import models.Porez;
import models.RobaUsluga;
import models.StavkaNarudzbenice;
import models.StavkeFakture;
import play.data.validation.Error;
import play.data.validation.Min;
import play.data.validation.Required;
import play.mvc.Controller;

public class StavkeFaktura extends Controller {
		
	public static void show (String mode){
		List<StavkeFakture> stavkeFakture = StavkeFakture.findAll();
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.findAll();
		List<RobaUsluga> robaUsluga = RobaUsluga.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(stavkeFakture, izlazneFakture, robaUsluga, mode);
	}
	
	public static void add(@Required @Min(1) float kolicina, @Min(0)float rabat,
		 float pdv, long izlaznaFaktura, long robaUsluga)
	{
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			params.flash();
			validation.keep();
			show("add");
		}else {
			StavkeFakture stavkeFakture = new StavkeFakture();
			RobaUsluga r = RobaUsluga.findById(robaUsluga);
			
			stavkeFakture.kolicina = kolicina;
			stavkeFakture.cenaPoJediniciMere = r.stavkeCenovnika.get(0).jedinicnaCena;
			stavkeFakture.rabat = rabat/100;
			float iznosRabata = 0;
			if(rabat == 0){
				
				stavkeFakture.osnovica = stavkeFakture.cenaPoJediniciMere*kolicina;
	
			}else{
				
				float vrednost = stavkeFakture.cenaPoJediniciMere*kolicina;
				iznosRabata = vrednost * rabat/100;
				stavkeFakture.osnovica = stavkeFakture.cenaPoJediniciMere*kolicina-iznosRabata;
			
			}
			stavkeFakture.pdv = r.grupa.porez.poreskaStopa.get(0).iznosStope/100;
			stavkeFakture.pdvIznos = stavkeFakture.osnovica*stavkeFakture.pdv;
			stavkeFakture.ukupanIznos = stavkeFakture.osnovica+stavkeFakture.pdvIznos;
			stavkeFakture.robaUsluga = RobaUsluga.findById(robaUsluga);
			stavkeFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
			
			IzlaznaFaktura faktura = IzlaznaFaktura.findById(izlaznaFaktura);
			faktura.ukupanPorez+=stavkeFakture.pdvIznos;
			faktura.ukupnoRobe+=stavkeFakture.kolicina;
			faktura.ukupanRabat += iznosRabata;
			faktura.iznosFakture+=stavkeFakture.ukupanIznos	;
			faktura.iznosFaktureOsnovica+=stavkeFakture.osnovica;
			faktura.statusFakture = "Obracunata";
			
			faktura.save();
			stavkeFakture.save();
			
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(float kolicina, float cenaPoJediniciMere, float rabat,
			long izlaznaFaktura, float pdv, long robaUsluga){
		List<StavkeFakture> stavkeFakture = StavkeFakture.find("byKolicinaLikeAndCenaPoJediniciMereLikeAnd"
				+ "RabatLikeAndIzlaznaFakturaLike"
				+ "AndPdvLikeAndRobaUslugaLike",
				kolicina, cenaPoJediniciMere, rabat, izlaznaFaktura, pdv, robaUsluga).fetch();
		String mode = "edit";
		renderTemplate("StavkeFaktura/show.html", stavkeFakture, mode);
		
	}
	
	public static void edit(float kolicina, float cenaPoJediniciMere, float rabat,
			float osnovica, float pdv, long izlaznaFaktura, long robaUsluga, long id)
	{
		System.out.println("EDIT");
		StavkeFakture stavkeFakture = StavkeFakture.findById(id);
		System.out.println(stavkeFakture.id);
		stavkeFakture.kolicina = kolicina;
		stavkeFakture.cenaPoJediniciMere = cenaPoJediniciMere;
		stavkeFakture.rabat = rabat/100;
		float iznosRabata = 0;
		if(rabat == 0){
			
			stavkeFakture.osnovica = stavkeFakture.cenaPoJediniciMere*kolicina;

		}else{
			
			float vrednost = stavkeFakture.cenaPoJediniciMere*kolicina;
			iznosRabata = vrednost * rabat/100;
			stavkeFakture.osnovica = stavkeFakture.cenaPoJediniciMere*kolicina-iznosRabata;
		
		}
		stavkeFakture.pdv = pdv/100;
		stavkeFakture.pdvIznos = stavkeFakture.osnovica*stavkeFakture.pdv;
		stavkeFakture.ukupanIznos = stavkeFakture.osnovica+stavkeFakture.pdvIznos;
		stavkeFakture.robaUsluga = RobaUsluga.findById(robaUsluga);
		stavkeFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
		
		IzlaznaFaktura faktura = IzlaznaFaktura.findById(izlaznaFaktura);
		faktura.ukupanPorez+=stavkeFakture.pdvIznos;
		
		float novaUkupnoRobe = faktura.ukupnoRobe - stavkeFakture.kolicina;
		stavkeFakture.kolicina = kolicina;
		
		faktura.ukupnoRobe = novaUkupnoRobe + kolicina;

		faktura.ukupanRabat += iznosRabata;
		faktura.iznosFakture+=stavkeFakture.ukupanIznos;
		faktura.iznosFaktureOsnovica+=stavkeFakture.osnovica;
		
		faktura.save();
		stavkeFakture.save();

		show("");
	}
	
	public static void delete(long id)
	{
		StavkeFakture stavkeFakture = StavkeFakture.findById(id);
		stavkeFakture.delete();
		show("");
	}
	
	
}