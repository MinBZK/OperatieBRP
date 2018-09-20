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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetDienstVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonAfnemerindicatieModel extends AbstractFormeelHistorischMetDienstVerantwoording implements
        PersoonAfnemerindicatieStandaardGroepBasis, ModelIdentificeerbaar<Long>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONAFNEMERINDICATIE", sequenceName = "AutAut.seq_His_PersAfnemerindicatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONAFNEMERINDICATIE")
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonAfnemerindicatieHisVolledigImpl.class)
    @JoinColumn(name = "PersAfnemerindicatie")
    @JsonBackReference
    private PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvMaterielePeriode"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeVolgen"))
    @JsonProperty
    private DatumAttribuut datumEindeVolgen;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonAfnemerindicatieModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoonAfnemerindicatie persoonAfnemerindicatie van HisPersoonAfnemerindicatieModel
     * @param datumAanvangMaterielePeriode datumAanvangMaterielePeriode van HisPersoonAfnemerindicatieModel
     * @param datumEindeVolgen datumEindeVolgen van HisPersoonAfnemerindicatieModel
     * @param dienstInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param tijdstipRegistratie Moment dat dit historie record wordt aangemaakt via de dienst.
     */
    public AbstractHisPersoonAfnemerindicatieModel(
        final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen,
        final Dienst dienstInhoud,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this.persoonAfnemerindicatie = persoonAfnemerindicatie;
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
        this.datumEindeVolgen = datumEindeVolgen;
        setVerantwoordingInhoud(dienstInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(tijdstipRegistratie);

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonAfnemerindicatieModel(final AbstractHisPersoonAfnemerindicatieModel kopie) {
        super(kopie);
        persoonAfnemerindicatie = kopie.getPersoonAfnemerindicatie();
        datumAanvangMaterielePeriode = kopie.getDatumAanvangMaterielePeriode();
        datumEindeVolgen = kopie.getDatumEindeVolgen();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonAfnemerindicatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param dienstInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param tijdstipRegistratie Moment dat dit historie record wordt aangemaakt via de dienst.
     */
    public AbstractHisPersoonAfnemerindicatieModel(
        final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig,
        final PersoonAfnemerindicatieStandaardGroep groep,
        final Dienst dienstInhoud,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        this.persoonAfnemerindicatie = persoonAfnemerindicatieHisVolledig;
        this.datumAanvangMaterielePeriode = groep.getDatumAanvangMaterielePeriode();
        this.datumEindeVolgen = groep.getDatumEindeVolgen();
        setVerantwoordingInhoud(dienstInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(tijdstipRegistratie);

    }

    /**
     * Retourneert ID van His Persoon \ Afnemerindicatie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Afnemerindicatie van His Persoon \ Afnemerindicatie.
     *
     * @return Persoon \ Afnemerindicatie.
     */
    public PersoonAfnemerindicatieHisVolledig getPersoonAfnemerindicatie() {
        return persoonAfnemerindicatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10335)
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10336)
    public DatumAttribuut getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvangMaterielePeriode != null) {
            attributen.add(datumAanvangMaterielePeriode);
        }
        if (datumEindeVolgen != null) {
            attributen.add(datumEindeVolgen);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_AFNEMERINDICATIE_STANDAARD;
    }

}
