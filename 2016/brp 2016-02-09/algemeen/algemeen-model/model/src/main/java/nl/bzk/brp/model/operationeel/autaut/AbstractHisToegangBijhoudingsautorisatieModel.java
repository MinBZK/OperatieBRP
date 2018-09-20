/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.ToegangBijhoudingsautorisatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.autaut.ToegangBijhoudingsautorisatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatieStandaardGroep;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatieStandaardGroepBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisToegangBijhoudingsautorisatieModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        ToegangBijhoudingsautorisatieStandaardGroepBasis, ModelIdentificeerbaar<Integer>
{

    @Id
    @SequenceGenerator(name = "HIS_TOEGANGBIJHOUDINGSAUTORISATIE", sequenceName = "AutAut.seq_His_ToegangBijhautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_TOEGANGBIJHOUDINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ToegangBijhoudingsautorisatieHisVolledigImpl.class)
    @JoinColumn(name = "ToegangBijhautorisatie")
    @JsonBackReference
    private ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisToegangBijhoudingsautorisatieModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param toegangBijhoudingsautorisatie toegangBijhoudingsautorisatie van HisToegangBijhoudingsautorisatieModel
     * @param datumIngang datumIngang van HisToegangBijhoudingsautorisatieModel
     * @param datumEinde datumEinde van HisToegangBijhoudingsautorisatieModel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisToegangBijhoudingsautorisatieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisToegangBijhoudingsautorisatieModel(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatie,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatie;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisToegangBijhoudingsautorisatieModel(final AbstractHisToegangBijhoudingsautorisatieModel kopie) {
        super(kopie);
        toegangBijhoudingsautorisatie = kopie.getToegangBijhoudingsautorisatie();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param toegangBijhoudingsautorisatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisToegangBijhoudingsautorisatieModel(
        final ToegangBijhoudingsautorisatieHisVolledig toegangBijhoudingsautorisatieHisVolledig,
        final ToegangBijhoudingsautorisatieStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.toegangBijhoudingsautorisatie = toegangBijhoudingsautorisatieHisVolledig;
        this.datumIngang = groep.getDatumIngang();
        this.datumEinde = groep.getDatumEinde();
        this.indicatieGeblokkeerd = groep.getIndicatieGeblokkeerd();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Toegang bijhoudingsautorisatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Toegang bijhoudingsautorisatie van His Toegang bijhoudingsautorisatie.
     *
     * @return Toegang bijhoudingsautorisatie.
     */
    public ToegangBijhoudingsautorisatieHisVolledig getToegangBijhoudingsautorisatie() {
        return toegangBijhoudingsautorisatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21527)
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21528)
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21529)
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
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
