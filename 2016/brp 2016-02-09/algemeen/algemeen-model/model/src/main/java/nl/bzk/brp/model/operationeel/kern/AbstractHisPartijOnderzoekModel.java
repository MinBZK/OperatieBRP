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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartijOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 10785)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartijOnderzoekModel extends AbstractFormeelHistorischMetActieVerantwoording implements
        PartijOnderzoekStandaardGroepBasis, ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJONDERZOEK", sequenceName = "Kern.seq_His_PartijOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJONDERZOEK")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PartijOnderzoekHisVolledigImpl.class)
    @JoinColumn(name = "PartijOnderzoek")
    @JsonBackReference
    private PartijOnderzoekHisVolledig partijOnderzoek;

    @Embedded
    @AttributeOverride(name = SoortPartijOnderzoekAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rol"))
    @JsonProperty
    private SoortPartijOnderzoekAttribuut rol;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPartijOnderzoekModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param partijOnderzoek partijOnderzoek van HisPartijOnderzoekModel
     * @param rol rol van HisPartijOnderzoekModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijOnderzoekModel(
        final PartijOnderzoekHisVolledig partijOnderzoek,
        final SoortPartijOnderzoekAttribuut rol,
        final ActieModel actieInhoud)
    {
        this.partijOnderzoek = partijOnderzoek;
        this.rol = rol;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPartijOnderzoekModel(final AbstractHisPartijOnderzoekModel kopie) {
        super(kopie);
        partijOnderzoek = kopie.getPartijOnderzoek();
        rol = kopie.getRol();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param partijOnderzoekHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPartijOnderzoekModel(
        final PartijOnderzoekHisVolledig partijOnderzoekHisVolledig,
        final PartijOnderzoekStandaardGroep groep,
        final ActieModel actieInhoud)
    {
        this.partijOnderzoek = partijOnderzoekHisVolledig;
        this.rol = groep.getRol();
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Partij \ Onderzoek.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij \ Onderzoek van His Partij \ Onderzoek.
     *
     * @return Partij \ Onderzoek.
     */
    public PartijOnderzoekHisVolledig getPartijOnderzoek() {
        return partijOnderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10802)
    public SoortPartijOnderzoekAttribuut getRol() {
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
        return ElementEnum.PARTIJENINONDERZOEK_STANDAARD;
    }

}
