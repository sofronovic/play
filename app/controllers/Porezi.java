package controllers;
import play.mvc.Controller;
import java.util.List;
import play.data.validation.Error;
import play.data.validation.Required;
import models.Grupa;
import models.ObracunatiPorez;
import models.PoreskaStopa;
import models.Porez;
import models.StavkeFakture;
public class Porezi extends Controller{

	public static void show (String mode){
		List<Porez> porezi = Porez.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(porezi, mode);
	}
	
	public static void add(@Required String nazivPoreza, boolean vazeci ){
		
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			Porez porez = new Porez();
			porez.nazivPoreza = nazivPoreza;
			porez.vazeci = vazeci;
			porez.save();
			validation.keep();
			show("add");
	}
	}
	
	public static void filter(String nazivPoreza, boolean vazeci){
		List<Porez> porezi = Porez.find("byNazivPoreza", "%"+nazivPoreza+"%").fetch();
		String mode = "edit";
		renderTemplate("Porezi/show.html", porezi, mode);
		
	}
	
	
	
	public static void edit(String nazivPoreza, boolean vazeci, long id )
	{	
		Porez porez = Porez.findById(id);
		porez.nazivPoreza = nazivPoreza;
		porez.vazeci = vazeci;
		porez.save();
		show("");
	}
	
	public static void delete(long id)
	{	
		Porez porez = Porez.findById(id);
		porez.delete();
		show("");
	}
}
