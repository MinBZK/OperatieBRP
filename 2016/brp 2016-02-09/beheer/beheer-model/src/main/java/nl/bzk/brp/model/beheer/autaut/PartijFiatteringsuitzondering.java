/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.SoortDocument;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Bijhoudingsvoorstellen waarvan de Partij niet wil dat deze automatisch gefiatteerd worden.
 *
 * Fiatteringsuitzonderingen mogen alleen gedefinieerd zijn als de Partij (in principe) automatisch fiatteert
 * (A:"Partij.Automatisch fiatteren?" = Ja). Alle bijhoudingen van andere bijhouders die voldoen aan een criterium in
 * dit Objecttype zijn dan uitzonderingen op het automatisch fiatteren en zal deze Partij handmatig fiatteren.
 *
 *
 *
 */
@Entity(name = "beheer.PartijFiatteringsuitzondering")
@Table(schema = "AutAut", name = "PartijFiatuitz")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class PartijFiatteringsuitzondering {

    @Id
    @SequenceGenerator(name = "PARTIJFIATTERINGSUITZONDERING", sequenceName = "AutAut.seq_PartijFiatuitz")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJFIATTERINGSUITZONDERING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partij")
    private Partij partij;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PartijBijhvoorstel")
    private Partij partijBijhoudingsvoorstel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SrtDoc")
    private SoortDocument soortDocument;

    @Column(name = "SrtAdmHnd")
    @Enumerated
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partijFiatteringsuitzondering", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisPartijFiatteringsuitzondering> hisPartijFiatteringsuitzonderingLijst = new HashSet<>();

    /**
     * Retourneert ID van Partij \ Fiatteringsuitzondering.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Fiatteringsuitzondering.
     *
     * @return Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Datum ingang van Partij \ Fiatteringsuitzondering.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij \ Fiatteringsuitzondering.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Partij bijhoudingsvoorstel van Partij \ Fiatteringsuitzondering.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * Retourneert Soort document van Partij \ Fiatteringsuitzondering.
     *
     * @return Soort document.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Retourneert Soort administratieve handeling van Partij \ Fiatteringsuitzondering.
     *
     * @return Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Retourneert Geblokkeerd? van Partij \ Fiatteringsuitzondering.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Standaard van Partij \ Fiatteringsuitzondering.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisPartijFiatteringsuitzondering> getHisPartijFiatteringsuitzonderingLijst() {
        return hisPartijFiatteringsuitzonderingLijst;
    }

    /**
     * Zet ID van Partij \ Fiatteringsuitzondering.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Partij van Partij \ Fiatteringsuitzondering.
     *
     * @param pPartij Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartij(final Partij pPartij) {
        this.partij = pPartij;
    }

    /**
     * Zet Datum ingang van Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Partij \ Fiatteringsuitzondering.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Partij bijhoudingsvoorstel van Partij \ Fiatteringsuitzondering.
     *
     * @param pPartijBijhoudingsvoorstel Partij bijhoudingsvoorstel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartijBijhoudingsvoorstel(final Partij pPartijBijhoudingsvoorstel) {
        this.partijBijhoudingsvoorstel = pPartijBijhoudingsvoorstel;
    }

    /**
     * Zet Soort document van Partij \ Fiatteringsuitzondering.
     *
     * @param pSoortDocument Soort document.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortDocument(final SoortDocument pSoortDocument) {
        this.soortDocument = pSoortDocument;
    }

    /**
     * Zet Soort administratieve handeling van Partij \ Fiatteringsuitzondering.
     *
     * @param pSoortAdministratieveHandeling Soort administratieve handeling.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoortAdministratieveHandeling(final SoortAdministratieveHandeling pSoortAdministratieveHandeling) {
        this.soortAdministratieveHandeling = pSoortAdministratieveHandeling;
    }

    /**
     * Zet Geblokkeerd? van Partij \ Fiatteringsuitzondering.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

}
