/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroep;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3211)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisOuderOuderlijkGezagModel extends AbstractMaterieelHistorischMetActieVerantwoording implements
        OuderOuderlijkGezagGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_OUDEROUDERLIJKGEZAG", sequenceName = "Kern.seq_His_OuderOuderlijkGezag")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_OUDEROUDERLIJKGEZAG")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = BetrokkenheidHisVolledigImpl.class)
    @JoinColumn(name = "Betr")
    @JsonBackReference
    private BetrokkenheidHisVolledig betrokkenheid;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOuderHeeftGezag"))
    @JsonProperty
    private JaNeeAttribuut indicatieOuderHeeftGezag;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisOuderOuderlijkGezagModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param betrokkenheid betrokkenheid van HisOuderOuderlijkGezagModel
     * @param indicatieOuderHeeftGezag indicatieOuderHeeftGezag van HisOuderOuderlijkGezagModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisOuderOuderlijkGezagModel(
        final BetrokkenheidHisVolledig betrokkenheid,
        final JaNeeAttribuut indicatieOuderHeeftGezag,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        this.betrokkenheid = betrokkenheid;
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisOuderOuderlijkGezagModel(final AbstractHisOuderOuderlijkGezagModel kopie) {
        super(kopie);
        betrokkenheid = kopie.getBetrokkenheid();
        indicatieOuderHeeftGezag = kopie.getIndicatieOuderHeeftGezag();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param betrokkenheidHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisOuderOuderlijkGezagModel(
        final BetrokkenheidHisVolledig betrokkenheidHisVolledig,
        final OuderOuderlijkGezagGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.betrokkenheid = betrokkenheidHisVolledig;
        this.indicatieOuderHeeftGezag = groep.getIndicatieOuderHeeftGezag();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Ouder Ouderlijk gezag.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Betrokkenheid van His Ouder Ouderlijk gezag.
     *
     * @return Betrokkenheid.
     */
    public BetrokkenheidHisVolledig getBetrokkenheid() {
        return betrokkenheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9692)
    public JaNeeAttribuut getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieOuderHeeftGezag != null) {
            attributen.add(indicatieOuderHeeftGezag);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisOuderOuderlijkGezagModel kopieer() {
        return new HisOuderOuderlijkGezagModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.OUDER_OUDERLIJKGEZAG;
    }

}
