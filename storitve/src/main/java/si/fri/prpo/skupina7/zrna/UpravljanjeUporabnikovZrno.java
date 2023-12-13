package si.fri.prpo.skupina7.zrna;

import si.fri.prpo.skupina7.anotacije.ValidirajUporabnikDto;
import si.fri.prpo.skupina7.dtos.UporabnikDto;
import si.fri.prpo.skupina7.entitete.Uporabnik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.*;

@ApplicationScoped
public class UpravljanjeUporabnikovZrno {

    private UUID id;

    private Logger log = Logger.getLogger(UpravljanjeUporabnikovZrno.class.getName());

    @PostConstruct
    private void init() {
        id = java.util.UUID.randomUUID();
        log.info("Inicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeUporabnikovZrno.class.getSimpleName() + " (" + id + ")");
    }

    @Inject
    private UporabnikiZrno uporabnikiZrno;

    @ValidirajUporabnikDto
    @Transactional
    public Uporabnik dodajUporabnika(UporabnikDto uporabnikDto) {
        String uporabniskoIme = uporabnikDto.getUporabniskoIme();
        if(uporabniskoIme.length() < 3 || uporabniskoIme.length() > 30) {
            log.info("Neveljavno uporabniško ime.");
            return null;
        }

        String geslo = uporabnikDto.getGeslo();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern p = Pattern.compile(regex);

        if(geslo.length() < 8 || geslo.length() > 30 || !(p.matcher(geslo).matches())) {
            log.info("Neveljavno geslo.");
            return null;
        }

        String spol = uporabnikDto.getSpol();
        if(!spol.equals("M") && !spol.equals("F") && !spol.equals("-")) {
            log.info("Neveljaven spol.");
            return null;
        }

        Uporabnik uporabnik = new Uporabnik();
        uporabnik.setUporabniskoIme(uporabniskoIme);
        uporabnik.setEmail(uporabnikDto.getEmail());
        uporabnik.setGeslo(geslo);
        uporabnik.setIme(uporabnikDto.getIme());
        uporabnik.setPriimek(uporabnikDto.getPriimek());
        uporabnik.setStarost(uporabnikDto.getStarost());
        uporabnik.setSpol(uporabnikDto.getSpol());

        return uporabnikiZrno.dodajUporabnika(uporabnik);
    }

    public Uporabnik posodobiUporabnika(int id, UporabnikDto uporabnikDto) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);
        if(uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
            return null;
        }

        String uporabniskoIme = uporabnikDto.getUporabniskoIme();
        if(uporabniskoIme.length() < 3 || uporabniskoIme.length() > 30) {
            log.info("Neveljavno uporabniško ime.");
            return null;
        }

        String geslo = uporabnikDto.getGeslo();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern p = Pattern.compile(regex);

        if(geslo.length() < 8 || geslo.length() > 30 || !(p.matcher(geslo).matches())) {
            log.info("Neveljavno geslo.");
            return null;
        }

        String spol = uporabnikDto.getSpol();
        if(!spol.equals("M") && !spol.equals("F") && !spol.equals("-")) {
            log.info("Neveljaven spol.");
            return null;
        }

        uporabnik.setUporabniskoIme(uporabniskoIme);
        uporabnik.setEmail(uporabnikDto.getEmail());
        uporabnik.setGeslo(geslo);
        uporabnik.setIme(uporabnikDto.getIme());
        uporabnik.setPriimek(uporabnikDto.getPriimek());
        uporabnik.setStarost(uporabnikDto.getStarost());
        uporabnik.setSpol(uporabnikDto.getSpol());

        return uporabnikiZrno.posodobiUporabnika(id, uporabnik);
    }

    @Transactional
    public Uporabnik spremeniGeslo(int id, UporabnikDto uporabnikDto) {
        Uporabnik uporabnik = uporabnikiZrno.pridobiUporabnika(id);
        if(uporabnik == null) {
            log.info("Uporabnik ne obstaja.");
        }

        String geslo = uporabnikDto.getGeslo();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern p = Pattern.compile(regex);

        String staroGeslo = uporabnik.getGeslo();

        if(geslo.length() < 8 || geslo.length() > 30 || !(p.matcher(geslo).matches()) || geslo.equals(staroGeslo)) {
            log.info("Neveljavno geslo.");
        }

        uporabnik.setGeslo(geslo);
        return uporabnikiZrno.posodobiUporabnika(id, uporabnik);
    }
}
