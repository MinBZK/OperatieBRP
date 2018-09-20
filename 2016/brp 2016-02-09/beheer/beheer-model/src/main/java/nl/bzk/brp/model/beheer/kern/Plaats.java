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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.WoonplaatscodeAttribuut;

/**
 * De woonplaatsen, zoals onderhouden vanuit de BAG.
 *
 * De inhoud van de woonplaatsentabel wordt overgenomen vanuit de BAG. Qua vorm wijkt deze wel af, zo wordt er apart
 * bijgehouden welke gemeenten er zijn, terwijl de BAG deze in één en dezelfde tabel heeft gestopt.
 *
 * De Plaats tabel is uitsluitend bedoels als controlemiddel om te kijken of de Plaatsnaam uit het adres wel bestaat. De
 * naam wordt immers opgeslagen en geen verwijzing naar de Plaats.
 *
 * De huidige vulling van de plaats is niet 100% correct. Zo heeft Vinkel 3 records in onze Plaats tabel:
 *
 * 2749;Vinkel;20090602;20141229 3063;Vinkel;20100501; 3612;Vinkel;20141229;
 *
 * Inmiddels is Vinkel opgedeeld in een groot westelijk deel (BAG-ID 3612) dat sinds 1 januari 2015 bij de gemeente
 * 's-Hertogenbosch hoort, en twee kleine oostelijke delen die onder het gezag van de burgemeester van Bernheze valt.
 * Die oostelijke delen (BAG-ID 3063) lijken een multipolygoon te zijn, maar blijken bij nadere bestudering door een 5
 * meter brede corridor langs de gemeentegrens met elkaar verbonden te zijn.
 *
 *
 *
 */
@Entity(name = "beheer.Plaats")
@Table(schema = "Kern", name = "Plaats")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Plaats {

    @Id
    @SequenceGenerator(name = "PLAATS", sequenceName = "Kern.seq_Plaats")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PLAATS")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = WoonplaatscodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private WoonplaatscodeAttribuut code;

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
     * Retourneert ID van Plaats.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Plaats.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public WoonplaatscodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Plaats.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Plaats.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Plaats.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Plaats.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Plaats.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final WoonplaatscodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Plaats.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum aanvang geldigheid van Plaats.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Plaats.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
