/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.WoonplaatscodeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractPlaats implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = WoonplaatscodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private WoonplaatscodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dataanvgel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dateindegel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPlaats() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Plaats.
     * @param naam naam van Plaats.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Plaats.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Plaats.
     */
    protected AbstractPlaats(
        final WoonplaatscodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Plaats.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Plaats.
     *
     * @return Code.
     */
    public final WoonplaatscodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Plaats.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Plaats.
     *
     * @return Datum aanvang geldigheid voor Plaats
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Plaats.
     *
     * @return Datum einde geldigheid voor Plaats
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
        return ElementEnum.PLAATS;
    }

}
