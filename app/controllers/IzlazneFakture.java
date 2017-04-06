package controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import models.IzlaznaFaktura;
import models.Narudzbenica;
import models.Otpremnica;
import models.PoslovnaGodina;
import models.PoslovniPartner;

import models.StavkeFakture;
import models.StavkeOtpremnice;
import play.data.validation.Error;
import play.mvc.Controller;

public class IzlazneFakture extends Controller {
		
	public static void show(String mode){
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.findAll();
		List<PoslovniPartner> poslovniPartneri = PoslovniPartner.findAll();
		List<PoslovnaGodina> poslovneGodine = PoslovnaGodina.findAll();
		List<Otpremnica> otpremnice = Otpremnica.findAll();
		if(mode == null || mode.equals("")){
			mode = "edit";
		}
		render(izlazneFakture, poslovniPartneri,
				poslovneGodine, otpremnice, mode);
	}

	
	public static void add(int brojFakture, Date datumFakture,
			Date datumValute, Date datumObracuna,
			float ukupnoRobe, float ukupanRabat, float ukupanPorez, float iznosFakture,
			float iznosFaktureOsnovica, String uplataNaRacun, String pozivNaBroj,
			String statusFakture, long poslovniPartner, long poslovnaGodina,
			long otpremnica){
		if(validation.hasErrors()){
			for(Error error : validation.errors()){
				System.out.println(error.message());
			}
			validation.keep();
			show("add");
		}else{
			IzlaznaFaktura i = new IzlaznaFaktura();
			i.brojFakture = brojFakture;
			i.datumFakture = new Date();
			i.datumValute = new Date();
			i.datumObracuna = new Date();
			i.ukupnoRobe = ukupnoRobe;
			i.ukupanRabat = ukupanRabat;
			i.ukupanPorez = ukupanPorez;
			i.iznosFakture = iznosFakture;
			i.iznosFakture = iznosFaktureOsnovica;
			i.uplataNaRacun = uplataNaRacun;
			i.pozivNaBroj = pozivNaBroj;
			i.statusFakture = statusFakture; //TODO
			i.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
			i.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);	
			i.otpremnica = Otpremnica.findById(otpremnica);
			i.save();
			validation.keep();
			show("add");
		}
	}
	
	public static void filter(int brojFakture, Date datumFakture,
			Date datumValute, Date datumObracuna,
			float ukupnoRobe, float ukupanRabat, float ukupanPorez, float iznosFakture,
			float iznosFaktureOsnovica, String uplataNaRacun, String pozivNaBroj,
			String statusFakture, long poslovniPartner, long poslovnaGodina,
			long otpremnica){
		List<IzlaznaFaktura> izlazneFakture = IzlaznaFaktura.find(
				"byBrojFaktureLikeAndDatumFaktureLike", brojFakture, datumFakture).fetch();
		String mode = "edit";
		renderTemplate("IzlazneFakture/show.html", izlazneFakture, mode);
	}
	
	public static void edit(int brojFakture, Date datumFakture,
			Date datumValute, Date datumObracuna,
			float ukupnoRobe, float ukupanRabat, float ukupanPorez, float iznosFakture,
			float iznosFaktureOsnovica, String uplataNaRacun, String pozivNaBroj,
			String statusFakture, long poslovniPartner, long poslovnaGodina,
			long otpremnica, long id){
		IzlaznaFaktura i = IzlaznaFaktura.findById(id);
		i.brojFakture = brojFakture;
		i.datumFakture = datumFakture;
		i.datumValute = datumValute;
		i.datumObracuna = datumObracuna;
		i.ukupnoRobe = ukupnoRobe;
		i.ukupanRabat = ukupanRabat;
		i.ukupanPorez = ukupanPorez;
		i.iznosFakture = iznosFakture;
		i.iznosFakture = iznosFaktureOsnovica;
		i.uplataNaRacun = uplataNaRacun;
		i.pozivNaBroj = pozivNaBroj;
		i.statusFakture = statusFakture;
		i.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
		i.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
		i.otpremnica = Otpremnica.findById(otpremnica);
		i.save();
		show("");
	}
	
	public static void delete(long id){
		IzlaznaFaktura i = IzlaznaFaktura.findById(id);
		for(StavkeFakture x : i.stavkeFakture){
			x.delete();
		}
		i.delete();
		show("");
	}
	
	public static void export(long id) throws IOException{
		
		IzlaznaFaktura faktura = IzlaznaFaktura.findById(id);
		
		XStream xStream = new XStream();
		String xml = xStream.toXML(faktura);
		
		String path = "E:\\faktura_"+faktura.brojFakture+".xml";
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(xml);
		bw.close();
		show("");
	}
	
	public static void generate(int brojOtpremnice, long poslovnaGodina, long poslovniPartner, long id, long narudzbenica)
	{
	    List<Otpremnica> listaOtpremnica =  Otpremnica.findAll();
		int n = 1;
		n += listaOtpremnica.size();
		
		IzlaznaFaktura faktura = IzlaznaFaktura.findById(id);
		Otpremnica o = new Otpremnica();
			o.datumOtpremnice = faktura.datumFakture;
			o.brojOtpremnice = n;
			o.osnovica = faktura.iznosFaktureOsnovica;
			o.ukupanPdv = faktura.ukupanPorez;
			o.iznosZaPlacanje = faktura.iznosFakture;
			o.poslovnaGodina = PoslovnaGodina.findById(poslovnaGodina);
			o.poslovniPartner = PoslovniPartner.findById(poslovniPartner);
			o.save();
		System.out.println(o);
		
		for(StavkeFakture stavkeFakture : faktura.stavkeFakture)
		{
			StavkeOtpremnice so = new StavkeOtpremnice();
			so.cenaPoJediniciMere = stavkeFakture.cenaPoJediniciMere;
			so.kolicina = stavkeFakture.kolicina;
			so.ukupnaCena = stavkeFakture.ukupanIznos;
			so.otpremnica = o;
			so.robaUsluga = stavkeFakture.robaUsluga;
			so.save();
			
			
		}
		show("");
	}
	
	
	
	
	
	
}
