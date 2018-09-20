/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import org.hibernate.annotations.DiscriminatorFormula;


/**
 * Abstracte klasse voor indicaties op een persoon.
 */
@Entity
@Table(schema = "Kern", name = "His_PersIndicatie")
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3654)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("(SELECT indicatie.srt FROM kern.persindicatie indicatie WHERE indicatie.id = persindicatie)")
public abstract class HisPersoonIndicatieModel implements HistorieEntiteit, ModelIdentificeerbaar<Integer>,
    PersoonIndicatieStandaardGroep
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONINDICATIE", sequenceName = "Kern.seq_His_PersIndicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONINDICATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PersIndicatie")
    @JsonBackReference
    private PersoonIndicatieHisVolledigImpl<?> persoonIndicatie;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Waarde"))
    @JsonProperty
    private JaAttribuut waarde;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnOpnameNation"))
    @JsonProperty
    private LO3RedenOpnameNationaliteitAttribuut lo3RedenOpnameNationaliteitAttribuut;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnBeeindigenNation"))
    @JsonProperty
    private ConversieRedenBeeindigenNationaliteitAttribuut conversieRedenBeeindigenNationaliteitAttribuut;

    @Transient
    private Verwerkingssoort verwerkingssoort;

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonIndicatieModel() {
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonIndicatieHisVolledig instantie van A-laag klasse.
     * @param groep                       groep
     */
    public HisPersoonIndicatieModel(final PersoonIndicatieHisVolledigImpl<?> persoonIndicatieHisVolledig,
        final PersoonIndicatieStandaardGroep groep)
    {
        this.persoonIndicatie = persoonIndicatieHisVolledig;
        this.waarde = groep.getWaarde();
        this.lo3RedenOpnameNationaliteitAttribuut = groep.getMigratieRedenOpnameNationaliteit();
        this.conversieRedenBeeindigenNationaliteitAttribuut = groep.getMigratieRedenBeeindigenNationaliteit();

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie de kopie.
     */
    public HisPersoonIndicatieModel(final HisPersoonIndicatieModel kopie) {
        this(kopie.getPersoonIndicatie(), kopie);
    }

    /**
     * @param persoonIndicatie persoonIndicatie van HisPersoonIndicatieModel
     * @param waarde           waarde van HisPersoonIndicatieModel
     */
    public HisPersoonIndicatieModel(final PersoonIndicatieHisVolledigImpl<?> persoonIndicatie,
        final JaAttribuut waarde)
    {
        this.persoonIndicatie = persoonIndicatie;
        this.waarde = waarde;

    }

    /**
     * Retourneert ID van His Persoon \ Indic`atie.
     *
     * @return ID.
     */
    @Override
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Indicatie van His Persoon \ Indicatie.
     *
     * @return Persoon \ Indicatie.
     */
    public PersoonIndicatieHisVolledigImpl<?> getPersoonIndicatie() {
        return persoonIndicatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9802)
    public JaAttribuut getWaarde() {
        return waarde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3RedenOpnameNationaliteitAttribuut getMigratieRedenOpnameNationaliteit() {
        return lo3RedenOpnameNationaliteitAttribuut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConversieRedenBeeindigenNationaliteitAttribuut getMigratieRedenBeeindigenNationaliteit() {
        return conversieRedenBeeindigenNationaliteitAttribuut;
    }

    /**
     * Bepaalt of deze groep geleverd mag worden aan een afnemer.
     *
     * @return true indien minstens één attribuut van deze groep geleverd mag worden aan de afnemer, anders false.
     */
    @Override
    public boolean isMagGeleverdWorden() {
        return (getWaarde() != null && getWaarde().isMagGeleverdWorden());
    }

    @Override
    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    @Override
    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        this.verwerkingssoort = verwerkingssoort;
    }

}
