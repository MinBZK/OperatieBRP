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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoekAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 10763)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonOnderzoekModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PersoonOnderzoekStandaardGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONONDERZOEK", sequenceName = "Kern.seq_His_PersOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONONDERZOEK")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonOnderzoekHisVolledigImpl.class)
    @JoinColumn(name = "PersOnderzoek")
    @JsonBackReference
    private PersoonOnderzoekHisVolledig persoonOnderzoek;

    @Embedded
    @AttributeOverride(name = SoortPersoonOnderzoekAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol"))
    @JsonProperty
    private SoortPersoonOnderzoekAttribuut rol;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonOnderzoekModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoonOnderzoek persoonOnderzoek van HisPersoonOnderzoekModel
     * @param rol rol van HisPersoonOnderzoekModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonOnderzoekModel(
        final PersoonOnderzoekHisVolledig persoonOnderzoek,
        final SoortPersoonOnderzoekAttribuut rol,
        final ActieModel actieInhoud)
    {
        this.persoonOnderzoek = persoonOnderzoek;
        this.rol = rol;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPersoonOnderzoekModel(final AbstractHisPersoonOnderzoekModel kopie) {
        super(kopie);
        persoonOnderzoek = kopie.getPersoonOnderzoek();
        rol = kopie.getRol();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonOnderzoekHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonOnderzoekModel(
        final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig,
        final PersoonOnderzoekStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.persoonOnderzoek = persoonOnderzoekHisVolledig;
        this.rol = groep.getRol();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon \ Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon \ Onderzoek van His Persoon \ Onderzoek.
     *
     * @return Persoon \ Onderzoek.
     */
    public PersoonOnderzoekHisVolledig getPersoonOnderzoek() {
        return persoonOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10809)
    public SoortPersoonOnderzoekAttribuut getRol() {
        return rol;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (rol != null) {
            attributen.add(rol);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_ONDERZOEK_STANDAARD;
    }

}
