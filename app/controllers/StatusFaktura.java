package controllers;

import java.util.List;

import models.IzlaznaFaktura;
import models.Porez;
import models.StatusFakture;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class StatusFaktura extends Controller{

	public static void show (String mode){
		List<StatusFakture> statusFakture = StatusFakture.findAll();
		List<IzlaznaFaktura> izlaznaFaktura = IzlaznaFaktura.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(statusFakture, izlaznaFaktura, mode);
	}
	
	public static void add(@Required String naziv, long izlaznaFaktura)
	{
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			StatusFakture statusFakture = new StatusFakture();
			statusFakture.naziv = naziv;
			statusFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
			statusFakture.save();
			validation.keep();
			show("add");
	}
	}
	
	public static void edit(String naziv, long izlaznaFaktura, long id)
	{
		StatusFakture statusFakture = StatusFakture.findById(id);
		statusFakture.naziv = naziv;
		statusFakture.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
		statusFakture.save();
		show("");
	}
	
	public static void delete(long id)
	{
		StatusFakture statusFakture = StatusFakture.findById(id);
		statusFakture.delete();
		show("");
	}
	
	}



