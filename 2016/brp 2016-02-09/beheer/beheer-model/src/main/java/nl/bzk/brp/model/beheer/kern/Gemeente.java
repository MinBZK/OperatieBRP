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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

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
@Entity(name = "beheer.Gemeente")
@Table(schema = "Kern", name = "Gem")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Gemeente {

    @Id
    @SequenceGenerator(name = "GEMEENTE", sequenceName = "Kern.seq_Gem")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEMEENTE")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = GemeenteCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private GemeenteCodeAttribuut code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partij")
    private Partij partij;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VoortzettendeGem")
    private Gemeente voortzettendeGemeente;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Gemeente.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Gemeente.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Code van Gemeente.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public GemeenteCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Partij van Gemeente.
     *
     * @return Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Voortzettende gemeente van Gemeente.
     *
     * @return Voortzettende gemeente.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Gemeente getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Gemeente.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Gemeente.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Gemeente.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Naam van Gemeente.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Code van Gemeente.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final GemeenteCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Partij van Gemeente.
     *
     * @param pPartij Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartij(final Partij pPartij) {
        this.partij = pPartij;
    }

    /**
     * Zet Voortzettende gemeente van Gemeente.
     *
     * @param pVoortzettendeGemeente Voortzettende gemeente.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setVoortzettendeGemeente(final Gemeente pVoortzettendeGemeente) {
        this.voortzettendeGemeente = pVoortzettendeGemeente;
    }

    /**
     * Zet Datum aanvang geldigheid van Gemeente.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Gemeente.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
