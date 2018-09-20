/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Hoedanigheid van een Partij binnen het BRP-stelsel die bepaalt welke BRP-Functies en/of BRP-Acties die Partij mag
 * verrichten.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Rol implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", null, null),
    /**
     * Afnemer.
     */
    AFNEMER("Afnemer", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Bijhoudingsorgaan College.
     */
    BIJHOUDINGSORGAAN_COLLEGE("Bijhoudingsorgaan College", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231)),
    /**
     * Bijhoudingsorgaan Minister.
     */
    BIJHOUDINGSORGAAN_MINISTER("Bijhoudingsorgaan Minister", new DatumEvtDeelsOnbekendAttribuut(0), new DatumEvtDeelsOnbekendAttribuut(99991231));

    private final String naam;
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Rol
     * @param datumAanvangGeldigheid DatumAanvangGeldigheid voor Rol
     * @param datumEindeGeldigheid DatumEindeGeldigheid voor Rol
     */
    private Rol(final String naam, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid, final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Retourneert Naam van Rol.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Rol.
     *
     * @return Datum aanvang geldigheid voor Rol
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Rol.
     *
     * @return Datum einde geldigheid voor Rol
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ROL;
    }

}
