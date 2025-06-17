package com.wypozyczalnia.mas_project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/* todo
    - Czy konieczne są dwa konstruktory? Każdy samochód musi mieć ubezpieczenie.
    - Czy konieczne jest rozdzielanie serwisów na historię i zaplanowane? może być po prostu lista serwisów i filtrując
    po dacie/statusieSerwisu można pozyskać info czy zakończony czy zaplanowany
    - metoda dodajRezerwacje chyba nie powinna być w tej klasie, raczej z poziomu rezerwacji bierzemy samochód i tam odbywa się przypisanie.
    - Czy kwestie z serwisem nie powinny być raczej w klasie Serwis?
    - uporządkować metody i zmienne
 */

public class Samochod {
    private String marka;
    private String model;
    private KategoriaSamochodu kategoriaSamochodu;
    private int rokProdukcji;
    private Ubezpieczenie ubezpieczenie;
    private List<Serwis> historiaSerwisow;
    private List<Serwis> zaplanowaneSerwisy;
    private List<Rezerwacja> rezerwacje;

    public Samochod(String marka, String model, KategoriaSamochodu kategoriaSamochodu, int rokProdukcji) {
        this.marka = marka;
        this.model = model;
        this.kategoriaSamochodu = kategoriaSamochodu;
        this.rokProdukcji = rokProdukcji;
        this.historiaSerwisow = new ArrayList<>();
        this.zaplanowaneSerwisy = new ArrayList<>();
        this.rezerwacje = new ArrayList<>();
    }

    public Samochod(String marka, String model, KategoriaSamochodu kategoriaSamochodu, 
                    int rokProdukcji, Ubezpieczenie ubezpieczenie) {
        this.marka = marka;
        this.model = model;
        this.kategoriaSamochodu = kategoriaSamochodu;
        this.rokProdukcji = rokProdukcji;
        this.ubezpieczenie = ubezpieczenie;
        this.historiaSerwisow = new ArrayList<>();
        this.zaplanowaneSerwisy = new ArrayList<>();
        this.rezerwacje = new ArrayList<>();
    }

    public void dodajSerwisDoHistorii(Serwis serwis) {
        if (serwis.getStatusSerwisu() == StatusSerwisu.ZAKONCZONY) {
            historiaSerwisow.add(serwis);
        } else {
            throw new IllegalArgumentException("Do historii można dodać tylko zakończone serwisy");
        }
    }

    public void zaplanujSerwis(Serwis serwis) {
        if (serwis.getStatusSerwisu() == StatusSerwisu.ZAPLANOWANY) {
            zaplanowaneSerwisy.add(serwis);
        } else {
            throw new IllegalArgumentException("Można zaplanować tylko serwis o statusie ZAPLANOWANY");
        }
    }

    public void rozpocznijSerwis(Serwis serwis, String serwisant) {
        if (zaplanowaneSerwisy.contains(serwis)) {
            serwis.rozpocznijSerwis(serwisant);
            zaplanowaneSerwisy.remove(serwis);
        } else {
            throw new IllegalArgumentException("Ten serwis nie jest zaplanowany dla tego samochodu");
        }
    }

    public void zakonczSerwis(Serwis serwis, double koszt, String dodatkowyOpis) {
        serwis.zakonczSerwis(koszt, dodatkowyOpis);
        historiaSerwisow.add(serwis);
    }

    public List<Serwis> getHistoriaSerwisow() {
        return new ArrayList<>(historiaSerwisow);
    }

    public List<Serwis> getZaplanowaneSerwisy() {
        return new ArrayList<>(zaplanowaneSerwisy);
    }

    public boolean czyPrzegladAktualny() {
        // Zakładamy, że przegląd jest aktualny, jeśli był wykonany w ciągu ostatniego roku
        Date dzisiaj = new Date();
        Date rokTemu = new Date(dzisiaj.getTime() - 365L * 24 * 60 * 60 * 1000);

        for (Serwis serwis : historiaSerwisow) {
            if (Serwis.TYP_PRZEGLAD_TECHNICZNY.equals(serwis.getTypSerwisu()) && 
                serwis.getDataZakonczenia() != null && 
                serwis.getDataZakonczenia().after(rokTemu)) {
                return true;
            }
        }

        return false;
    }

    public boolean czyWSerwisie() {
        for (Serwis serwis : historiaSerwisow) {
            if (serwis.getStatusSerwisu() == StatusSerwisu.W_TRAKCIE) {
                return true;
            }
        }
        return false;
    }

    public Ubezpieczenie getUbezpieczenie() {
        return ubezpieczenie;
    }

    public void setUbezpieczenie(Ubezpieczenie ubezpieczenie) {
        this.ubezpieczenie = ubezpieczenie;
    }

    public boolean czyUbezpieczonyWazne() {
        return ubezpieczenie != null && ubezpieczenie.czyWazne();
    }

    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public KategoriaSamochodu getKategoriaSamochodu() {
        return kategoriaSamochodu;
    }

    public int getRokProdukcji() {
        return rokProdukcji;
    }

    public double getStawkaPodstawowa() {
        return Cennik.getStawkaPodstawowa(kategoriaSamochodu);
    }

    public double getStawkaWeekendowa() {
        return Cennik.getStawkaWeekendowa(kategoriaSamochodu);
    }

    public void dodajRezerwacje(Rezerwacja rezerwacja) {
        if (rezerwacja != null && rezerwacja.getSamochod() == this) {
            rezerwacje.add(rezerwacja);
        } else {
            throw new IllegalArgumentException("Rezerwacja musi być powiązana z tym samochodem");
        }
    }

    public List<Rezerwacja> getRezerwacje() {
        return new ArrayList<>(rezerwacje);
    }

    public boolean czyDostepny(Date dataOd, Date dataDo) {
        if (czyWSerwisie()) {
            return false;
        }

        for (Rezerwacja rez : rezerwacje) {
            if ((rez.getStatusRezerwacji() == StatusRezerwacji.ZATWIERDZONA || 
                 rez.getStatusRezerwacji() == StatusRezerwacji.AKTYWNA) && 
                !(dataDo.before(rez.getDataOd()) || dataOd.after(rez.getDataDo()))) {
                return false;
            }
        }

        for (Serwis serwis : zaplanowaneSerwisy) {
            Date dataPlanowana = serwis.getDataPlanowana();
            if (dataPlanowana != null && 
                dataPlanowana.after(dataOd) && dataPlanowana.before(dataDo)) {
                return false;
            }
        }

        return true;
    }

}