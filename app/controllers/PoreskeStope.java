package controllers;

import java.util.List;

import models.IstorijaPoreza;
import models.PoreskaStopa;
import models.Porez;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class PoreskeStope extends Controller {
	
	public static void show(String mode){
		List<PoreskaStopa> poreskeStope = PoreskaStopa.findAll();
		List<IstorijaPoreza> istorijePoreza = IstorijaPoreza.findAll();
		List<Porez> porezi = Porez.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(poreskeStope, istorijePoreza, porezi,mode);
	}
	
	public static void add(@Required float iznosStope, long istorijaPoreza, long porez){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			PoreskaStopa p = new PoreskaStopa();
			p.iznosStope = iznosStope;
			p.istorijaPoreza = IstorijaPoreza.findById(istorijaPoreza);
			p.porez = Porez.findById(porez);
			p.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(float iznosStope, long istorijaPoreza, long porez){
		List<PoreskaStopa> poreskeStope = PoreskaStopa.find("byIznosStopeLikeAndIstorijaPoreza_idAndPorez_id", iznosStope, istorijaPoreza, porez).fetch();
		String mode = "edit";
		renderTemplate("PoreskeStope/show.html", poreskeStope, mode);
	}
	
	public static void edit(@Required float iznosStope, long istorijaPoreza, long porez, long id){
		PoreskaStopa p = PoreskaStopa.findById(id);
		p.iznosStope = iznosStope;
		p.istorijaPoreza = IstorijaPoreza.findById(istorijaPoreza);
		p.porez = Porez.findById(porez);
		p.save();
		show("");
	}
	
	public static void delete(long id){
		PoreskaStopa p = PoreskaStopa.findById(id);
		p.delete();
		show("");
	}
}
