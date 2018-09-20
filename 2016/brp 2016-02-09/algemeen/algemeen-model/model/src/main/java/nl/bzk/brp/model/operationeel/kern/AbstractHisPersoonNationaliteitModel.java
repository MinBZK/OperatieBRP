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
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3604)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonNationaliteitModel extends AbstractMaterieelHistorischMetActieVerantwoording implements
        PersoonNationaliteitStandaardGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONNATIONALITEIT", sequenceName = "Kern.seq_His_PersNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONNATIONALITEIT")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonNationaliteitHisVolledigImpl.class)
    @JoinColumn(name = "PersNation")
    @JsonBackReference
    private PersoonNationaliteitHisVolledig persoonNationaliteit;

    @Embedded
    @AssociationOverride(name = RedenVerkrijgingNLNationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnVerk"))
    @JsonProperty
    private RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging;

    @Embedded
    @AssociationOverride(name = RedenVerliesNLNationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnVerlies"))
    @JsonProperty
    private RedenVerliesNLNationaliteitAttribuut redenVerlies;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBijhoudingBeeindigd"))
    @JsonProperty
    private JaAttribuut indicatieBijhoudingBeeindigd;

    @Embedded
    @AttributeOverride(name = LO3RedenOpnameNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnOpnameNation"))
    @JsonProperty
    private LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit;

    @Embedded
    @AttributeOverride(name = ConversieRedenBeeindigenNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnBeeindigenNation"))
    @JsonProperty
    private ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrDatEindeBijhouding"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonNationaliteitModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonNationaliteit persoonNationaliteit van HisPersoonNationaliteitModel
     * @param redenVerkrijging redenVerkrijging van HisPersoonNationaliteitModel
     * @param redenVerlies redenVerlies van HisPersoonNationaliteitModel
     * @param indicatieBijhoudingBeeindigd indicatieBijhoudingBeeindigd van HisPersoonNationaliteitModel
     * @param migratieRedenOpnameNationaliteit migratieRedenOpnameNationaliteit van HisPersoonNationaliteitModel
     * @param migratieRedenBeeindigenNationaliteit migratieRedenBeeindigenNationaliteit van HisPersoonNationaliteitModel
     * @param migratieDatumEindeBijhouding migratieDatumEindeBijhouding van HisPersoonNationaliteitModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonNationaliteitModel(
        final PersoonNationaliteitHisVolledig persoonNationaliteit,
        final RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging,
        final RedenVerliesNLNationaliteitAttribuut redenVerlies,
        final JaAttribuut indicatieBijhoudingBeeindigd,
        final LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit,
        final ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit,
        final DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.persoonNationaliteit = persoonNationaliteit;
        this.redenVerkrijging = redenVerkrijging;
        this.redenVerlies = redenVerlies;
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;
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
    public AbstractHisPersoonNationaliteitModel(final AbstractHisPersoonNationaliteitModel kopie) {
        super(kopie);
        persoonNationaliteit = kopie.getPersoonNationaliteit();
        redenVerkrijging = kopie.getRedenVerkrijging();
        redenVerlies = kopie.getRedenVerlies();
        indicatieBijhoudingBeeindigd = kopie.getIndicatieBijhoudingBeeindigd();
        migratieRedenOpnameNationaliteit = kopie.getMigratieRedenOpnameNationaliteit();
        migratieRedenBeeindigenNationaliteit = kopie.getMigratieRedenBeeindigenNationaliteit();
        migratieDatumEindeBijhouding = kopie.getMigratieDatumEindeBijhouding();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonNationaliteitHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonNationaliteitModel(
        final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig,
        final PersoonNationaliteitStandaardGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoonNationaliteit = persoonNationaliteitHisVolledig;
        this.redenVerkrijging = groep.getRedenVerkrijging();
        this.redenVerlies = groep.getRedenVerlies();
        this.indicatieBijhoudingBeeindigd = groep.getIndicatieBijhoudingBeeindigd();
        this.migratieRedenOpnameNationaliteit = groep.getMigratieRedenOpnameNationaliteit();
        this.migratieRedenBeeindigenNationaliteit = groep.getMigratieRedenBeeindigenNationaliteit();
        this.migratieDatumEindeBijhouding = groep.getMigratieDatumEindeBijhouding();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Nationaliteit van His Persoon \ Nationaliteit.
     *
     * @return Persoon \ Nationaliteit.
     */
    public PersoonNationaliteitHisVolledig getPersoonNationaliteit() {
        return persoonNationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9803)
    public RedenVerkrijgingNLNationaliteitAttribuut getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9804)
    public RedenVerliesNLNationaliteitAttribuut getRedenVerlies() {
        return redenVerlies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21238)
    public JaAttribuut getIndicatieBijhoudingBeeindigd() {
        return indicatieBijhoudingBeeindigd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21239)
    public LO3RedenOpnameNationaliteitAttribuut getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21240)
    public ConversieRedenBeeindigenNationaliteitAttribuut getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 21241)
    public DatumEvtDeelsOnbekendAttribuut getMigratieDatumEindeBijhouding() {
        return migratieDatumEindeBijhouding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (redenVerkrijging != null) {
            attributen.add(redenVerkrijging);
        }
        if (redenVerlies != null) {
            attributen.add(redenVerlies);
        }
        if (indicatieBijhoudingBeeindigd != null) {
            attributen.add(indicatieBijhoudingBeeindigd);
        }
        if (migratieRedenOpnameNationaliteit != null) {
            attributen.add(migratieRedenOpnameNationaliteit);
        }
        if (migratieRedenBeeindigenNationaliteit != null) {
            attributen.add(migratieRedenBeeindigenNationaliteit);
        }
        if (migratieDatumEindeBijhouding != null) {
            attributen.add(migratieDatumEindeBijhouding);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonNationaliteitModel kopieer() {
        return new HisPersoonNationaliteitModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_NATIONALITEIT_STANDAARD;
    }

}
