package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sun.xml.internal.txw2.Document;

import models.Cenovnik;
import models.Narudzbenica;
import models.PoslovnaGodina;
import models.PoslovniPartner;
import models.Preduzece;
import models.StavkeCenovnika;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class Cenovnici extends Controller{
	
	public static void show(String mode){
		List<Cenovnik> cenovnici = Cenovnik.findAll();
		List<Preduzece> preduzeca = Preduzece.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(cenovnici, preduzeca, mode);
	}
	
	public static void add(@Required Date datumPrimene, long preduzece){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			Cenovnik c = new Cenovnik();
			c.datumPrimene = datumPrimene;
			c.preduzece = Preduzece.findById(preduzece);
			c.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(Date datumPrimene, long preduzece){
		List<Cenovnik> cenovnici = Narudzbenica.find("byDatumPrimeneLikeAndPreduzece_id", datumPrimene, preduzece).fetch();
		String mode = "edit";
		renderTemplate("Cenovnici/show.html", cenovnici, mode);
	}
	
	public static void edit(@Required Date datumPrimene, long preduzece, long id){
		Cenovnik c = Cenovnik.findById(id);
		c.datumPrimene = datumPrimene;
		c.preduzece = Preduzece.findById(preduzece);
		c.save();
		show("");
	}
	
	public static void delete(long id){
		Cenovnik c = Cenovnik.findById(id);
		c.delete();
		show("");
	}
	
	public static void copy(long id, float procenat){
		Cenovnik c = Cenovnik.findById(id);
		Cenovnik c2 = new Cenovnik();
		c2.datumPrimene=c.datumPrimene;
		c2.preduzece=c.preduzece;
		c2.stavkeCenovnika = new ArrayList<StavkeCenovnika>();
		
		c2.save();
		
		for(StavkeCenovnika s: c.stavkeCenovnika){
			StavkeCenovnika s2 = new StavkeCenovnika();
			s2.robaUsluga = s.robaUsluga;
			
		if(procenat>0){
			s2.jedinicnaCena=s.jedinicnaCena+(s.jedinicnaCena*procenat/100);
		}else{
			procenat=-procenat;
			s2.jedinicnaCena=s.jedinicnaCena-(s.jedinicnaCena*procenat/100);
		}
		s2.cenovnik=c2;
		s2.save();
		c2.stavkeCenovnika.add(s2);
		}
		
		c2.save();
		show("");
	}

}
