/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

public class VerhuisBerichtRequest extends BerichtRequest {

    private Persoon persoon;

    private Adres   oudAdres;

    private Adres   nieuwAdres;

    public Persoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final Persoon persoon) {
        this.persoon = persoon;
    }

    public Adres getOudAdres() {
        return oudAdres;
    }

    public void setOudAdres(final Adres oudAdres) {
        this.oudAdres = oudAdres;
    }

    public Adres getNieuwAdres() {
        return nieuwAdres;
    }

    public void setNieuwAdres(final Adres nieuwAdres) {
        this.nieuwAdres = nieuwAdres;
    }

    @Override
    public void valideer() {
        super.valideer();
        if (getOudAdres() == null) {
            throw new IllegalStateException("oud adres ontbreekt");
        }
        if (getPersoon() == null) {
            throw new IllegalStateException("persoon ontbreekt");
        }
        if (getPersoon().getVoornamen() == null) {
            throw new IllegalStateException("voornamen ontbreken");
        }
        if (getPersoon().getGeslachtsnaamcomponenten() == null) {
            throw new IllegalStateException("geslachtsnaamcomponenten ontbreken");
        }
        if (getNieuwAdres() == null) {
            throw new IllegalStateException("nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getGemeente() == null) {
            throw new IllegalStateException("gemeente van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getStraat() == null) {
            throw new IllegalStateException("straat van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getHuisnummer() == null) {
            throw new IllegalStateException("huisnummer van nieuw adres ontbreekt");
        }
        if (getNieuwAdres().getPlaats() == null) {
            throw new IllegalStateException("plaats van nieuw adres ontbreekt");
        }
        if (getOudAdres().getGemeente() == null) {
            throw new IllegalStateException("gemeente van oud adres ontbreekt");
        }
    }

    @Override
    public String creeerBerichtTekst() {
        String verzendendePartij = getKenmerken().getVerzendendePartij().getNaam();
        Gemeente gemeenteOud = getOudAdres().getGemeente();
        Gemeente gemeenteNieuw = getNieuwAdres().getGemeente();
        String voornaam = getPersoon().getVoornamen().get(0);
        String geslachtsnaam = getPersoon().getGeslachtsnaamcomponenten().get(0).getVolledigeNaam();
        String oudeGemeenteTekst = creeerOudeGemeenteTekst(gemeenteOud, gemeenteNieuw);
        String prevalidatieTekst = creeerPrevalidatieTekst(getKenmerken().isPrevalidatie());
        String meldingenGeneriek = creeerGeneriekeTekstVoorMeldingen(getMeldingen());
        String berichtTemplate = "%s:%s %s van %s %s.%s";

        return String.format(berichtTemplate, verzendendePartij, prevalidatieTekst, oudeGemeenteTekst, voornaam,
                geslachtsnaam, meldingenGeneriek);
    }

    private String creeerOudeGemeenteTekst(final Gemeente gemeenteOud, final Gemeente gemeenteNieuw) {
        String resultaat;
        if (gemeenteOud.equals(gemeenteNieuw)) {
            resultaat = "verhuizing binnen gemeente";
        } else {
            resultaat = String.format("verhuizing vanuit %s", gemeenteOud.getNaam());
        }
        return resultaat;
    }

    @Override
    protected String creeerDetailTekstVoorGevolg() {
        return getNieuwAdres().getAdresVolledig();
    }

    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.VERHUIZING;
    }

}
