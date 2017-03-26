package controllers;

import java.util.List;

import models.IzlaznaFaktura;
import models.Porez;
import models.RobaUsluga;
import models.StavkeFakture;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class StavkeFaktura extends Controller {
		
	public static void show (String mode){
		List<StavkeFakture> stavkeFakture = StavkeFakture.findAll();
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.findAll();
		List<Porez> porezi = Porez.findAll();
		List<RobaUsluga> robaUsluga = RobaUsluga.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(stavkeFakture, izlazneFakture, porezi, robaUsluga, mode);
	}
	
	public static void add(@Required float kolicina, float cenaPoJediniciMere, float rabat,
			float osnovica, long izlaznaFaktura, long porez, long robaUsluga)
	{
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			StavkeFakture stavkeFakture = new StavkeFakture();
			stavkeFakture.kolicina = kolicina;
			stavkeFakture.cenaPoJediniciMere = cenaPoJediniciMere;
			stavkeFakture.rabat = rabat;
			stavkeFakture.osnovica = osnovica;
			stavkeFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
			stavkeFakture.porez = Porez.findById(porez);
			stavkeFakture.robaUsluga = RobaUsluga.findById(robaUsluga);
			
			stavkeFakture.save();
			validation.keep();
			show("add");
	}
	}
	
	
	public static void edit(float kolicina, float cenaPoJediniciMere, float rabat,
			float osnovica, long izlaznaFaktura, long porez, long robaUsluga, long id)
	{
		StavkeFakture stavkeFakture = StavkeFakture.findById(id);
		stavkeFakture.kolicina = kolicina;
		stavkeFakture.cenaPoJediniciMere = cenaPoJediniciMere;
		stavkeFakture.rabat = rabat;
		stavkeFakture.osnovica = osnovica;
		stavkeFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
		stavkeFakture.porez = Porez.findById(porez);
		stavkeFakture.robaUsluga = RobaUsluga.findById(robaUsluga);
		
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