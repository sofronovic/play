package controllers;
import java.util.List;
import models.IzlaznaFaktura;
import models.ObracunatiPorez;
import models.Porez;
import models.StatusFakture;
import play.data.validation.Error;
import play.data.validation.Required;
import play.mvc.Controller;

public class ObracunatiPorezi  extends Controller{

	public static void show (String mode){
		List<ObracunatiPorez> obracunatiPorez = ObracunatiPorez.findAll();
		List<Porez> porezi = Porez.findAll();
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.findAll();
		if (mode == null || mode.equals(""))
			mode = "edit";
		render(obracunatiPorez, porezi, izlazneFakture, mode);
	}
	
	
	public static void add(@Required float stopa, float iznos, long porez, long izlaznaFaktura)
	{
		if(validation.hasErrors()) {
			for(Error error : validation.errors()) {
			System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else {
			ObracunatiPorez obracunatiPorez = new ObracunatiPorez();
			obracunatiPorez.stopa = stopa;
			obracunatiPorez.iznos = iznos;
			obracunatiPorez.porez = Porez.findById(porez);
			obracunatiPorez.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
			obracunatiPorez.save();
			validation.keep();
			show("add");
	}
	}
	
	
	public static void edit(float stopa, float iznos, long porez, long izlaznaFaktura, long id)
	{
		ObracunatiPorez obracunatiPorez = ObracunatiPorez.findById(id);
		obracunatiPorez.stopa = stopa;
		obracunatiPorez.iznos = iznos;
		obracunatiPorez.porez = Porez.findById(porez);
		obracunatiPorez.izlaznaFaktura = IzlaznaFaktura.findById(izlaznaFaktura);
		obracunatiPorez.save();
		show("");
	}
	
	public static void delete(long id)
	{
		ObracunatiPorez obracunatiPorez = ObracunatiPorez.findById(id);
		obracunatiPorez.delete();
		show("");
	}
	
	
}

