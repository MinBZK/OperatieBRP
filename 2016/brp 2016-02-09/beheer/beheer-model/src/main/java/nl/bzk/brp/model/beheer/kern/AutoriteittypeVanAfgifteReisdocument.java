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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteittypeVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * De mogelijke Autoriteittypen van afgifte van een reisdocument.
 *
 * Deze tabel wordt niet als directe verwijzing gebruikt van A:"Persoon \ Reisdocument.Autoriteit van afgifte", maar
 * slechts de eerste paar tekens worden gevalideerd.
 *
 * Naamgeving is wat problematisch, zo is de code BZ en BU beiden voor 'minister van buitenlandse zaken'. Om de naam
 * toch uniek te maken, is deze "stamtabel", die toch alleen bestaat ter afdekking van een bedrijfsregel voor de code
 * voor een autoriteit, uniek gemaakt.
 *
 *
 *
 */
@Entity(name = "beheer.AutoriteittypeVanAfgifteReisdocument")
@Table(schema = "Kern", name = "AuttypeVanAfgifteReisdoc")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class AutoriteittypeVanAfgifteReisdocument {

    @Id
    @SequenceGenerator(name = "AUTORITEITTYPEVANAFGIFTEREISDOCUMENT", sequenceName = "Kern.seq_AuttypeVanAfgifteReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTORITEITTYPEVANAFGIFTEREISDOCUMENT")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AutoriteittypeVanAfgifteReisdocumentCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private AutoriteittypeVanAfgifteReisdocumentCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Autoriteittype van afgifte reisdocument.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Autoriteittype van afgifte reisdocument.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AutoriteittypeVanAfgifteReisdocumentCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Autoriteittype van afgifte reisdocument.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Autoriteittype van afgifte reisdocument.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Autoriteittype van afgifte reisdocument.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Autoriteittype van afgifte reisdocument.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Autoriteittype van afgifte reisdocument.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final AutoriteittypeVanAfgifteReisdocumentCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Autoriteittype van afgifte reisdocument.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum aanvang geldigheid van Autoriteittype van afgifte reisdocument.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Autoriteittype van afgifte reisdocument.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
