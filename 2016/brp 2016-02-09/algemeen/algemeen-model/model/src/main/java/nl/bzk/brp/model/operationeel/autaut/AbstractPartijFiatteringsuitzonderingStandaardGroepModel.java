/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.autaut.PartijFiatteringsuitzonderingStandaardGroepBasis;

/**
 * De uitzondering wordt gedefinieerd door middel van een aantal attributen te weten; A:"Partij \
 * Fiatteringsuitzondering.Partij bijhoudingsvoorstel", A:"Partij \ Fiatteringsuitzondering.Soort document" en A:"Partij
 * \ Fiatteringsuitzondering.Soort administratieve handeling". Minimaal ��n van deze attributen moet gevuld zijn. Als er
 * meerdere attributen gevuld zijn, dan moet het bijhoudinsvoorstel voldoen aan alle criteria.
 *
 * Evaluatie van akten gaan simpel en bewust niet subtiel. We verzamelen alle akten die voorkomen bij een bijhouding en
 * kijken of in die set van akten het soort document (dat is opgegeven als uitzondering) voorkomt. We gaan dus niet per
 * persoon kijken etc.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPartijFiatteringsuitzonderingStandaardGroepModel implements PartijFiatteringsuitzonderingStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "PartijBijhvoorstel"))
    @JsonProperty
    private PartijAttribuut partijBijhoudingsvoorstel;

    @Embedded
    @AssociationOverride(name = SoortDocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "SrtDoc"))
    @JsonProperty
    private SoortDocumentAttribuut soortDocument;

    @Embedded
    @AttributeOverride(name = SoortAdministratieveHandelingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtAdmHnd"))
    @JsonProperty
    private SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPartijFiatteringsuitzonderingStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumIngang datumIngang van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van Standaard.
     * @param soortDocument soortDocument van Standaard.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Standaard.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Standaard.
     */
    public AbstractPartijFiatteringsuitzonderingStandaardGroepModel(
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PartijAttribuut partijBijhoudingsvoorstel,
        final SoortDocumentAttribuut soortDocument,
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling,
        final JaAttribuut indicatieGeblokkeerd)
    {
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.partijBijhoudingsvoorstel = partijBijhoudingsvoorstel;
        this.soortDocument = soortDocument;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocumentAttribuut getSoortDocument() {
        return soortDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortAdministratieveHandelingAttribuut getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (partijBijhoudingsvoorstel != null) {
            attributen.add(partijBijhoudingsvoorstel);
        }
        if (soortDocument != null) {
            attributen.add(soortDocument);
        }
        if (soortAdministratieveHandeling != null) {
            attributen.add(soortAdministratieveHandeling);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
