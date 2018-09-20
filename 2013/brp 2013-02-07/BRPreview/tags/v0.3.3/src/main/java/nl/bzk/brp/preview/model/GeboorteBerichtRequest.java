/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class GeboorteBerichtRequest extends BerichtRequest {

    private Persoon nieuwgeborene;

    private Adres   adresNieuwgeborene;

    private Persoon ouder1;

    private Persoon ouder2;

    public Persoon getNieuwgeborene() {
        return nieuwgeborene;
    }

    public void setNieuwgeborene(final Persoon persoon) {
        nieuwgeborene = persoon;
    }

    public Adres getAdresNieuwgeborene() {
        return adresNieuwgeborene;
    }

    public void setAdresNieuwgeborene(final Adres adresNieuwgeborene) {
        this.adresNieuwgeborene = adresNieuwgeborene;
    }

    public Persoon getOuder1() {
        return ouder1;
    }

    public void setOuder1(final Persoon ouder1) {
        this.ouder1 = ouder1;
    }

    public Persoon getOuder2() {
        return ouder2;
    }

    public void setOuder2(final Persoon ouder2) {
        this.ouder2 = ouder2;
    }

    @Override
    public void valideer() {
        super.valideer();
        if (nieuwgeborene == null) {
            throw new IllegalStateException("nieuwgeborene ontbreekt");
        }
        if (nieuwgeborene.getBsn() == null) {
            throw new IllegalStateException("bsn nieuwgeborene ontbreekt");
        }
        if (adresNieuwgeborene == null) {
            throw new IllegalStateException("adres nieuwgeborene ontbreekt");
        }
        if (adresNieuwgeborene.getGemeente() == null) {
            throw new IllegalStateException("gemeente van adres nieuwgeborene ontbreekt");
        }
        if (nieuwgeborene.getVoornamen() == null) {
            throw new IllegalStateException("voornamen nieuwgeborene ontbreken");
        }
        if (nieuwgeborene.getGeslachtsnaamcomponenten() == null) {
            throw new IllegalStateException("geslachtsnaamcomponenten nieuwgeborene ontbreken");
        }
        if (nieuwgeborene.getGemeenteGeboorte() == null) {
            throw new IllegalStateException("gemeente geboorte nieuwgeborene ontbreekt");
        }
        if (ouder1 == null) {
            throw new IllegalStateException("ouder1 ontbreekt");
        }
        if (ouder1.getVoornamen() == null) {
            throw new IllegalStateException("voornamen ouder1 ontbreken");
        }
        if (ouder1.getGeslachtsnaamcomponenten() == null) {
            throw new IllegalStateException("geslachtsnaamcomponenten ouder1 ontbreken");
        }
        if (ouder2 == null) {
            throw new IllegalStateException("ouder2 ontbreekt");
        }
        if (ouder2.getVoornamen() == null) {
            throw new IllegalStateException("voornamen ouder2 ontbreken");
        }
        if (ouder2.getGeslachtsnaamcomponenten() == null) {
            throw new IllegalStateException("geslachtsnaamcomponenten ouder2 ontbreken");
        }
    }

    @Override
    public String creeerBerichtTekst() {
        String verzendendePartij = getKenmerken().getVerzendendePartij().getNaam();
        String voorGemeenteTekst = creeerVoorGemeenteTekst();
        String voornaam = getNieuwgeborene().getVoornamen().get(0);
        String geslachtsnaam = getNieuwgeborene().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String prevalidatieTekst = creeerPrevalidatieTekst(getKenmerken().isPrevalidatie());
        String meldingenGeneriek = creeerGeneriekeTekstVoorMeldingen(getMeldingen());
        String template = "%s%s:%s inschrijving nieuwgeborene %s %s.%s";

        return String.format(template, verzendendePartij, voorGemeenteTekst, prevalidatieTekst, voornaam,
                geslachtsnaam, meldingenGeneriek);
    }

    private String creeerVoorGemeenteTekst() {
        String tekst;
        if (getKenmerken().getVerzendendePartij().equals(adresNieuwgeborene.getGemeente())) {
            tekst = "";
        } else {
            tekst = String.format(" (voor %s)", adresNieuwgeborene.getGemeente().getNaam());
        }
        return tekst;
    }

    @Override
    protected String creeerDetailTekstVoorGevolg() {
        String geboorteDatum = nieuwgeborene.getDatumGeboorteTekst();
        String gemeenteGeboorte = nieuwgeborene.getGemeenteGeboorte().getNaam();
        String voornaamOuder1 = ouder1.getVoornamen().get(0);
        String geslachtsnaamOuder1 = ouder1.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String voornaamOuder2 = ouder2.getVoornamen().get(0);
        String geslachtsnaamOuder2 = ouder2.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String template = "Geboren op %s te %s. BSN: %d. Ouders: %s %s en %s %s.";

        String resultaat =
            String.format(template, geboorteDatum, gemeenteGeboorte, nieuwgeborene.getBsn(), voornaamOuder1,
                    geslachtsnaamOuder1, voornaamOuder2, geslachtsnaamOuder2);

        return resultaat;
    }

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.GEBOORTE;
    }

}
