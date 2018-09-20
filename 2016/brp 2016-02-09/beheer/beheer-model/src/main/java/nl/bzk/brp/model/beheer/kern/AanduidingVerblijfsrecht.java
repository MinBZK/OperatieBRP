/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingVerblijfsrechtCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;

/**
 * Categorisatie van de Aanduiding van het verblijfsrecht van Persoon.
 *
 * 1. Naam: aanduiding verblijfsrecht is een verwarrende naam. Immers, de hier bedoelde aanduiding betreft niet alleen
 * het recht op verblijf van de vreemdeling (zoals geregeld in de Vreemdelingenwet 2000), maar ook o.a. het recht op
 * arbeid (zoals [deels] geregeld in de wet arbeid vreemdelingen). De in LO3.x gebruikte terminologie (verblijfstitel)
 * leek een correcte term. Deze term stuit echter op bezwaren vanuit juridische hoek. Om die reden is toch de term
 * verblijfsrecht gehanteerd, voor dit gegeven dat in de hoek van de IND ook wel wordt gedefinieerd als 'het recht op
 * voorzieningen'.
 *
 *
 *
 */
@Entity(name = "beheer.AanduidingVerblijfsrecht")
@Table(schema = "Kern", name = "AandVerblijfsr")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class AanduidingVerblijfsrecht {

    @Id
    @SequenceGenerator(name = "AANDUIDINGVERBLIJFSRECHT", sequenceName = "Kern.seq_AandVerblijfsr")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AANDUIDINGVERBLIJFSRECHT")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AanduidingVerblijfsrechtCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private AanduidingVerblijfsrechtCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Aanduiding verblijfsrecht.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Aanduiding verblijfsrecht.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AanduidingVerblijfsrechtCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Aanduiding verblijfsrecht.
     *
     * @return Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Aanduiding verblijfsrecht.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Aanduiding verblijfsrecht.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Aanduiding verblijfsrecht.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Aanduiding verblijfsrecht.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final AanduidingVerblijfsrechtCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Omschrijving van Aanduiding verblijfsrecht.
     *
     * @param pOmschrijving Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOmschrijving(final OmschrijvingEnumeratiewaardeAttribuut pOmschrijving) {
        this.omschrijving = pOmschrijving;
    }

    /**
     * Zet Datum aanvang geldigheid van Aanduiding verblijfsrecht.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Aanduiding verblijfsrecht.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
