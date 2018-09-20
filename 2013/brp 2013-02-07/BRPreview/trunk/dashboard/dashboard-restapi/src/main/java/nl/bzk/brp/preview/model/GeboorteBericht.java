/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * De Class GeboorteBericht, het bericht voor de administratieve handeling Geboorte.
 */
public class GeboorteBericht extends AdministratieveHandelingBericht {

    /**
     * De nieuwgeborene.
     */
    private final Persoon nieuwgeborene;

    /**
     * De ouder1.
     */
    private final Persoon ouder1;

    /**
     * De ouder2.
     */
    private final Persoon ouder2;

    /**
     * Instantieert een geboorte bericht op basis van een geboorte.
     * @param geboorte De geboorte voor dit bericht.
     */
    public GeboorteBericht(final Geboorte geboorte) {
        super(geboorte);
        nieuwgeborene = geboorte.getNieuwgeborene();
        ouder1 = geboorte.getOuder1();
        ouder2 = geboorte.getOuder2();
    }

    /**
     * Geef de nieuwgeborene.
     *
     * @return de nieuwgeborene
     */
    public Persoon getNieuwgeborene() {
        return nieuwgeborene;
    }

    /**
     * Geef de ouder1.
     *
     * @return de ouder1
     */
    public Persoon getOuder1() {
        return ouder1;
    }

    /**
     * Geef de ouder2.
     *
     * @return de ouder2
     */
    public Persoon getOuder2() {
        return ouder2;
    }

    /**
     * Geef de gemeente geboorte.
     *
     * @return de gemeente geboorte
     */
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

    /**
     * Creeert de tekst voor dit bericht.
     * @return De tekst voor dit bericht.
     */
    @Override
    public String creeerBerichtTekst() {
        String geboorteDatum = nieuwgeborene.getDatumGeboorteTekst();
        String gemeenteGeboorte = getGemeenteGeboorte();
        String naamOuder1 = "?";
        String naamOuder2 = "?";
        if (ouder1 != null) {
            naamOuder1 = ouder1.getNaamTekst();
        }
        if (ouder2 != null) {
            naamOuder2 = ouder2.getNaamTekst();
        }

        String template = "%s geboren op %s te %s. BSN: %d. Ouders: %s en %s.";

        String resultaat =
                String.format(template, nieuwgeborene.getNaamTekst(), geboorteDatum, gemeenteGeboorte,
                        nieuwgeborene.getBsn(), naamOuder1, naamOuder2);

        return resultaat;
    }

    /**
     * Creeert de lijst van BSN nummers die door deze handeling geraakt worden.
     * @return De lijst met BSN nummers.
     */
    @Override
    public final List<Integer> creeerBsnLijst() {
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

    /**
     * Geeft de soort bijhouding voor dit type handeling.
     * @return De OndersteundeBijhoudingsTypes voor deze administratieve handeling.
     */
    @Override
    public OndersteundeBijhoudingsTypes getSoortBijhouding() {
        return OndersteundeBijhoudingsTypes.GEBOORTE;
    }

}
