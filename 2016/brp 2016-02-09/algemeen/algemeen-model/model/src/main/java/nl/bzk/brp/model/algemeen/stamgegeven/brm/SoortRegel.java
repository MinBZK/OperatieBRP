/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;

/**
 * Het soort regel.
 *
 * Regels hebben het doel de kwaliteit van de BRP hoog te houden. Sommige regels zijn nodig om de technische werking van
 * de BRP te kunnen garanderen: ze zijn 'verweven' met het systeem. Andere regels dienen ter verhoging van de kwaliteit
 * van het systeem, alleen de technische werking kan gegarandeerd blijven ook als ze niet van toepassing zijn, omdat het
 * niet verweven is in het systeem. Van de categorie die niet verweven is in het systeem is het in principe denkbaar dat
 * de regels niet altijd actief zijn, voor degene die verweven zijn met het systeem is dat niet denkbaar. Zo is het
 * systeem niet in staat om de string 'gisteren' op te nemen als datum, omdat het in de database een numeriek veld is.
 * Deze regel is dus verweven met het systeem.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortRegel implements BestaansPeriodeStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy", null, null),
    /**
     * Regels die moeten gelden voor een goede technische werking van het stelsel, doordat ze verweven zijn met de
     * structuur van het systeem..
     */
    SYSTEEMREGEL("Systeemregel",
            "Regels die moeten gelden voor een goede technische werking van het stelsel, doordat ze verweven zijn met de structuur van het systeem.",
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Regels die moeten gelden voor een goede inhoudelijke werking van het stelsel..
     */
    BEDRIJFSREGEL("Bedrijfsregel", "Regels die moeten gelden voor een goede inhoudelijke werking van het stelsel.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String naam;
    private final String omschrijving;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortRegel
     * @param omschrijving Omschrijving voor SoortRegel
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor SoortRegel
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor SoortRegel
     */
    private SoortRegel(
        final String naam,
        final String omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.naam = naam;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Naam van Soort regel.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort regel.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Soort regel.
     *
     * @return Datum aanvang geldigheid voor Soort regel
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Soort regel.
     *
     * @return Datum einde geldigheid voor Soort regel
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
