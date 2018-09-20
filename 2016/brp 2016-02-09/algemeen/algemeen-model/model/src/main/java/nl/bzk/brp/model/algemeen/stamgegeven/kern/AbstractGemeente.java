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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * De gemeente als geografisch object.
 *
 * De gemeente komt in twee hoedanigheden voor in de BRP: enerzijds als partij die berichten uitwisselt met de BRP, en
 * anderzijds als geografisch object dat de opdeling van (het Europese deel van) Nederland bepaalt. Hier wordt de tweede
 * hoedanigheid bedoeld.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractGemeente implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = GemeenteCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private GemeenteCodeAttribuut code;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij partij;

    @ManyToOne
    @JoinColumn(name = "VoortzettendeGem")
    @Fetch(value = FetchMode.JOIN)
    private Gemeente voortzettendeGemeente;

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
    protected AbstractGemeente() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van Gemeente.
     * @param code code van Gemeente.
     * @param partij partij van Gemeente.
     * @param voortzettendeGemeente voortzettendeGemeente van Gemeente.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Gemeente.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Gemeente.
     */
    protected AbstractGemeente(
        final NaamEnumeratiewaardeAttribuut naam,
        final GemeenteCodeAttribuut code,
        final Partij partij,
        final Gemeente voortzettendeGemeente,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.naam = naam;
        this.code = code;
        this.partij = partij;
        this.voortzettendeGemeente = voortzettendeGemeente;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Gemeente.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Gemeente.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Code van Gemeente.
     *
     * @return Code.
     */
    public final GemeenteCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Partij van Gemeente.
     *
     * @return Partij.
     */
    public final Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Voortzettende gemeente van Gemeente.
     *
     * @return Voortzettende gemeente.
     */
    public final Gemeente getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Gemeente.
     *
     * @return Datum aanvang geldigheid voor Gemeente
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Gemeente.
     *
     * @return Datum einde geldigheid voor Gemeente
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
        return ElementEnum.GEMEENTE;
    }

}
