/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroepBasis;

/**
 * Deze groep bevat geen materiele historie (meer). De IND stuurt namelijk alleen de actuele status. Daarmee is het
 * verleden niet meer betrouwbaar (er zijn geen correcties doorgevoerd op de materiele tijdlijn). Daarom wordt er
 * uitsluitend 1 actueel voorkomen geregistreerd die aanvangt op een bepaalde datum.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVerblijfsrechtGroepModel implements PersoonVerblijfsrechtGroepBasis {

    @Embedded
    @AssociationOverride(name = AanduidingVerblijfsrechtAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "AandVerblijfsr"))
    @JsonProperty
    private AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatMededelingVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatVoorzEindeVerblijfsr"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonVerblijfsrechtGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param aanduidingVerblijfsrecht aanduidingVerblijfsrecht van Verblijfsrecht.
     * @param datumAanvangVerblijfsrecht datumAanvangVerblijfsrecht van Verblijfsrecht.
     * @param datumMededelingVerblijfsrecht datumMededelingVerblijfsrecht van Verblijfsrecht.
     * @param datumVoorzienEindeVerblijfsrecht datumVoorzienEindeVerblijfsrecht van Verblijfsrecht.
     */
    public AbstractPersoonVerblijfsrechtGroepModel(
        final AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht)
    {
        this.aanduidingVerblijfsrecht = aanduidingVerblijfsrecht;
        this.datumAanvangVerblijfsrecht = datumAanvangVerblijfsrecht;
        this.datumMededelingVerblijfsrecht = datumMededelingVerblijfsrecht;
        this.datumVoorzienEindeVerblijfsrecht = datumVoorzienEindeVerblijfsrecht;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerblijfsrechtGroep te kopieren groep.
     */
    public AbstractPersoonVerblijfsrechtGroepModel(final PersoonVerblijfsrechtGroep persoonVerblijfsrechtGroep) {
        this.aanduidingVerblijfsrecht = persoonVerblijfsrechtGroep.getAanduidingVerblijfsrecht();
        this.datumAanvangVerblijfsrecht = persoonVerblijfsrechtGroep.getDatumAanvangVerblijfsrecht();
        this.datumMededelingVerblijfsrecht = persoonVerblijfsrechtGroep.getDatumMededelingVerblijfsrecht();
        this.datumVoorzienEindeVerblijfsrecht = persoonVerblijfsrechtGroep.getDatumVoorzienEindeVerblijfsrecht();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingVerblijfsrechtAttribuut getAanduidingVerblijfsrecht() {
        return aanduidingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangVerblijfsrecht() {
        return datumAanvangVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumMededelingVerblijfsrecht() {
        return datumMededelingVerblijfsrecht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeVerblijfsrecht() {
        return datumVoorzienEindeVerblijfsrecht;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (aanduidingVerblijfsrecht != null) {
            attributen.add(aanduidingVerblijfsrecht);
        }
        if (datumAanvangVerblijfsrecht != null) {
            attributen.add(datumAanvangVerblijfsrecht);
        }
        if (datumMededelingVerblijfsrecht != null) {
            attributen.add(datumMededelingVerblijfsrecht);
        }
        if (datumVoorzienEindeVerblijfsrecht != null) {
            attributen.add(datumVoorzienEindeVerblijfsrecht);
        }
        return attributen;
    }

}
