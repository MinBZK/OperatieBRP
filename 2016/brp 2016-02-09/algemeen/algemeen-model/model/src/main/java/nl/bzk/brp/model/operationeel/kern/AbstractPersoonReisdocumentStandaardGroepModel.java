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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroepBasis;

/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter éénmalig wijzigen, zoals
 * reden vervallen. Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonReisdocumentStandaardGroepModel implements PersoonReisdocumentStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = ReisdocumentNummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Nr"))
    @JsonProperty
    private ReisdocumentNummerAttribuut nummer;

    @Embedded
    @AttributeOverride(name = AutoriteitVanAfgifteReisdocumentCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "AutVanAfgifte"))
    @JsonProperty
    private AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngangDoc"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumIngangDocument;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeDoc"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEindeDocument;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatUitgifte"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumUitgifte;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatInhingVermissing"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing;

    @Embedded
    @AssociationOverride(name = AanduidingInhoudingVermissingReisdocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(
            name = "AandInhingVermissing"))
    @JsonProperty
    private AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonReisdocumentStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param nummer nummer van Standaard.
     * @param autoriteitVanAfgifte autoriteitVanAfgifte van Standaard.
     * @param datumIngangDocument datumIngangDocument van Standaard.
     * @param datumEindeDocument datumEindeDocument van Standaard.
     * @param datumUitgifte datumUitgifte van Standaard.
     * @param datumInhoudingVermissing datumInhoudingVermissing van Standaard.
     * @param aanduidingInhoudingVermissing aanduidingInhoudingVermissing van Standaard.
     */
    public AbstractPersoonReisdocumentStandaardGroepModel(
        final ReisdocumentNummerAttribuut nummer,
        final AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte,
        final DatumEvtDeelsOnbekendAttribuut datumIngangDocument,
        final DatumEvtDeelsOnbekendAttribuut datumEindeDocument,
        final DatumEvtDeelsOnbekendAttribuut datumUitgifte,
        final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing,
        final AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing)
    {
        this.nummer = nummer;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
        this.datumIngangDocument = datumIngangDocument;
        this.datumEindeDocument = datumEindeDocument;
        this.datumUitgifte = datumUitgifte;
        this.datumInhoudingVermissing = datumInhoudingVermissing;
        this.aanduidingInhoudingVermissing = aanduidingInhoudingVermissing;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonReisdocumentStandaardGroep te kopieren groep.
     */
    public AbstractPersoonReisdocumentStandaardGroepModel(final PersoonReisdocumentStandaardGroep persoonReisdocumentStandaardGroep) {
        this.nummer = persoonReisdocumentStandaardGroep.getNummer();
        this.autoriteitVanAfgifte = persoonReisdocumentStandaardGroep.getAutoriteitVanAfgifte();
        this.datumIngangDocument = persoonReisdocumentStandaardGroep.getDatumIngangDocument();
        this.datumEindeDocument = persoonReisdocumentStandaardGroep.getDatumEindeDocument();
        this.datumUitgifte = persoonReisdocumentStandaardGroep.getDatumUitgifte();
        this.datumInhoudingVermissing = persoonReisdocumentStandaardGroep.getDatumInhoudingVermissing();
        this.aanduidingInhoudingVermissing = persoonReisdocumentStandaardGroep.getAanduidingInhoudingVermissing();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReisdocumentNummerAttribuut getNummer() {
        return nummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocumentCodeAttribuut getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeDocument() {
        return datumEindeDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AanduidingInhoudingVermissingReisdocumentAttribuut getAanduidingInhoudingVermissing() {
        return aanduidingInhoudingVermissing;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (nummer != null) {
            attributen.add(nummer);
        }
        if (autoriteitVanAfgifte != null) {
            attributen.add(autoriteitVanAfgifte);
        }
        if (datumIngangDocument != null) {
            attributen.add(datumIngangDocument);
        }
        if (datumEindeDocument != null) {
            attributen.add(datumEindeDocument);
        }
        if (datumUitgifte != null) {
            attributen.add(datumUitgifte);
        }
        if (datumInhoudingVermissing != null) {
            attributen.add(datumInhoudingVermissing);
        }
        if (aanduidingInhoudingVermissing != null) {
            attributen.add(aanduidingInhoudingVermissing);
        }
        return attributen;
    }

}
