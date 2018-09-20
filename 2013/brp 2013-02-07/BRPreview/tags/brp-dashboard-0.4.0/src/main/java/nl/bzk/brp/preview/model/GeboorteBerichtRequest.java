/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;


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
    }

    @Override
    public String creeerBerichtTekst() {
        String verzendendePartij = getKenmerken().getVerzendendePartij().getNaam();
        String voorGemeenteTekst = creeerVoorGemeenteTekst();
        String naam = getNaam();
        String prevalidatieTekst = creeerPrevalidatieTekst(getKenmerken().isPrevalidatie());
        String meldingenGeneriek = creeerGeneriekeTekstVoorMeldingen(getMeldingen());
        String template = "%s%s:%s inschrijving nieuwgeborene %s.%s";

        return String
                .format(template, verzendendePartij, voorGemeenteTekst, prevalidatieTekst, naam, meldingenGeneriek);
    }

    private String getNaam() {
        String resultaat;
        String voornaam = getVoornaam();
        String geslachtsnaam = getGeslachtsnaam();
        if (voornaam != null && geslachtsnaam != null) {
            resultaat = voornaam + " " + geslachtsnaam;
        } else if (voornaam != null && geslachtsnaam == null) {
            resultaat = voornaam;
        } else if (voornaam == null && geslachtsnaam != null) {
            resultaat = geslachtsnaam;
        } else {
            resultaat = "naam onbekend";
        }
        return resultaat;
    }

    private String getGeslachtsnaam() {
        String resultaat;
        if (getNieuwgeborene().getGeslachtsnaamcomponenten() == null
            || getNieuwgeborene().getGeslachtsnaamcomponenten().isEmpty())
        {
            resultaat = null;
        } else {
            resultaat = getNieuwgeborene().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        }
        return resultaat;
    }

    private String getVoornaam() {
        String resultaat;
        if (getNieuwgeborene().getVoornamen() == null || getNieuwgeborene().getVoornamen().isEmpty()) {
            resultaat = null;
        } else {
            resultaat = getNieuwgeborene().getVoornamen().get(0);
        }
        return resultaat;
    }

    private String creeerVoorGemeenteTekst() {
        String tekst;
        if (adresNieuwgeborene == null
            || getKenmerken().getVerzendendePartij().equals(adresNieuwgeborene.getGemeente()))
        {
            tekst = "";
        } else {
            tekst = String.format(" (voor %s)", adresNieuwgeborene.getGemeente().getNaam());
        }
        return tekst;
    }

    @Override
    protected String creeerDetailTekstVoorGevolg() {
        String geboorteDatum = nieuwgeborene.getDatumGeboorteTekst();
        String gemeenteGeboorte = getGemeenteGeboorte();
        String voornaamOuder1 = ouder1 == null ? "?" : ouder1.getVoornamen().get(0);
        String geslachtsnaamOuder1 =
            ouder1 == null ? "" : ouder1.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String voornaamOuder2 = ouder1 == null ? "?" : ouder2.getVoornamen().get(0);
        String geslachtsnaamOuder2 =
            ouder1 == null ? "" : ouder2.getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String template = "Geboren op %s te %s. BSN: %d. Ouders: %s %s en %s %s.";

        String resultaat =
            String.format(template, geboorteDatum, gemeenteGeboorte, nieuwgeborene.getBsn(), voornaamOuder1,
                    geslachtsnaamOuder1, voornaamOuder2, geslachtsnaamOuder2);

        return resultaat;
    }

    private String getGemeenteGeboorte() {
        String resultaat;
        if (getNieuwgeborene().getGemeenteGeboorte() == null
            || getNieuwgeborene().getGemeenteGeboorte().getNaam() == null)
        {
            resultaat = "?";
        } else {
            resultaat = nieuwgeborene.getGemeenteGeboorte().getNaam();
        }
        return resultaat;
    }

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.GEBOORTE;
    }

    @Override
    public List<Integer> creeerBsnLijst() {
        List<Integer> resultaat = new ArrayList<Integer>();
        resultaat.add(nieuwgeborene.getBsn());
        if (ouder1 != null && ouder1.getBsn() != null) {
            resultaat.add(ouder1.getBsn());
        }
        if (ouder2 != null && ouder2.getBsn() != null) {
            resultaat.add(ouder2.getBsn());
        }
        return resultaat;
    }

}
