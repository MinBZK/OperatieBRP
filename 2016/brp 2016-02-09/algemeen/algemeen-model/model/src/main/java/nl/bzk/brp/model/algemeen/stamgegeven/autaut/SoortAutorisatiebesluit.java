/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;

/**
 * Classificatie van Autorisatiebesluit.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortAutorisatiebesluit implements BestaansPeriodeStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy", null, null),
    /**
     * Een autorisatie ten behoeve van het leveren van gegevens aan Partijen in de rol van Afnemer..
     */
    LEVERINGSAUTORISATIE("Leveringsautorisatie", "Een autorisatie ten behoeve van het leveren van gegevens aan Partijen in de rol van Afnemer.",
            new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Een autorisatie ten behoeve van het bijhouden..
     */
    BIJHOUDINGSAUTORISATIE("Bijhoudingsautorisatie", "Een autorisatie ten behoeve van het bijhouden.", new DatumEvtDeelsOnbekendAttribuut(0),
            new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String naam;
    private final String omschrijving;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortAutorisatiebesluit
     * @param omschrijving Omschrijving voor SoortAutorisatiebesluit
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor SoortAutorisatiebesluit
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor SoortAutorisatiebesluit
     */
    private SoortAutorisatiebesluit(
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
     * Retourneert Naam van Soort autorisatiebesluit.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort autorisatiebesluit.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Soort autorisatiebesluit.
     *
     * @return Datum aanvang geldigheid voor Soort autorisatiebesluit
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Soort autorisatiebesluit.
     *
     * @return Datum einde geldigheid voor Soort autorisatiebesluit
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
