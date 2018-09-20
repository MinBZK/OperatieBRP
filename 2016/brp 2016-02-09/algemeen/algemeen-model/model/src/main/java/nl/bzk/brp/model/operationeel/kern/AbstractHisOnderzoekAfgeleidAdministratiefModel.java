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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 10841)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisOnderzoekAfgeleidAdministratiefModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        OnderzoekAfgeleidAdministratiefGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_ONDERZOEKAFGELEIDADMINISTRATIEF", sequenceName = "Kern.seq_His_OnderzoekAfgeleidAdminis")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ONDERZOEKAFGELEIDADMINISTRATIEF")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = OnderzoekHisVolledigImpl.class)
    @JoinColumn(name = "Onderzoek")
    @JsonBackReference
    private OnderzoekHisVolledig onderzoek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisOnderzoekAfgeleidAdministratiefModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param onderzoek onderzoek van HisOnderzoekAfgeleidAdministratiefModel
     * @param administratieveHandeling administratieveHandeling van HisOnderzoekAfgeleidAdministratiefModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisOnderzoekAfgeleidAdministratiefModel(
        final OnderzoekHisVolledig onderzoek,
        final AdministratieveHandelingModel administratieveHandeling,
        final ActieModel actieInhoud)
    {
        this.onderzoek = onderzoek;
        this.administratieveHandeling = administratieveHandeling;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisOnderzoekAfgeleidAdministratiefModel(final AbstractHisOnderzoekAfgeleidAdministratiefModel kopie) {
        super(kopie);
        onderzoek = kopie.getOnderzoek();
        administratieveHandeling = kopie.getAdministratieveHandeling();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param onderzoekHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisOnderzoekAfgeleidAdministratiefModel(
        final OnderzoekHisVolledig onderzoekHisVolledig,
        final OnderzoekAfgeleidAdministratiefGroep groep,
        final ActieModel actieInhoud)
    {
        this.onderzoek = onderzoekHisVolledig;
        if (groep.getAdministratieveHandeling() != null) {
            this.administratieveHandeling = new AdministratieveHandelingModel(groep.getAdministratieveHandeling());
        }
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Onderzoek Afgeleid administratief.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Onderzoek van His Onderzoek Afgeleid administratief.
     *
     * @return Onderzoek.
     */
    public OnderzoekHisVolledig getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10928)
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ONDERZOEK_AFGELEIDADMINISTRATIEF;
    }

}
