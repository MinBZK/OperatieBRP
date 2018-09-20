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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.annotaties.AttribuutAccessor;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroepBasis;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3664)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPersoonBijhoudingModel extends AbstractMaterieelHistorischMetActieVerantwoording implements PersoonBijhoudingGroepBasis,
        ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PERSOONBIJHOUDING", sequenceName = "Kern.seq_His_PersBijhouding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PERSOONBIJHOUDING")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PersoonHisVolledigImpl.class)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledig persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Bijhpartij"))
    @JsonProperty
    private PartijAttribuut bijhoudingspartij;

    @Embedded
    @AttributeOverride(name = BijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bijhaard"))
    @JsonProperty
    private BijhoudingsaardAttribuut bijhoudingsaard;

    @Embedded
    @AttributeOverride(name = NadereBijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NadereBijhaard"))
    @JsonProperty
    private NadereBijhoudingsaardAttribuut nadereBijhoudingsaard;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOnverwDocAanw"))
    @JsonProperty
    private JaNeeAttribuut indicatieOnverwerktDocumentAanwezig;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPersoonBijhoudingModel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param persoon persoon van HisPersoonBijhoudingModel
     * @param bijhoudingspartij bijhoudingspartij van HisPersoonBijhoudingModel
     * @param bijhoudingsaard bijhoudingsaard van HisPersoonBijhoudingModel
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van HisPersoonBijhoudingModel
     * @param indicatieOnverwerktDocumentAanwezig indicatieOnverwerktDocumentAanwezig van HisPersoonBijhoudingModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     * @param historie De groep uit een bericht
     */
    public AbstractHisPersoonBijhoudingModel(
        final PersoonHisVolledig persoon,
        final PartijAttribuut bijhoudingspartij,
        final BijhoudingsaardAttribuut bijhoudingsaard,
        final NadereBijhoudingsaardAttribuut nadereBijhoudingsaard,
        final JaNeeAttribuut indicatieOnverwerktDocumentAanwezig,
        final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        this.persoon = persoon;
        this.bijhoudingspartij = bijhoudingspartij;
        this.bijhoudingsaard = bijhoudingsaard;
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
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
    public AbstractHisPersoonBijhoudingModel(final AbstractHisPersoonBijhoudingModel kopie) {
        super(kopie);
        persoon = kopie.getPersoon();
        bijhoudingspartij = kopie.getBijhoudingspartij();
        bijhoudingsaard = kopie.getBijhoudingsaard();
        nadereBijhoudingsaard = kopie.getNadereBijhoudingsaard();
        indicatieOnverwerktDocumentAanwezig = kopie.getIndicatieOnverwerktDocumentAanwezig();

    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param historie historie
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public AbstractHisPersoonBijhoudingModel(
        final PersoonHisVolledig persoonHisVolledig,
        final PersoonBijhoudingGroep groep,
        final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        this.persoon = persoonHisVolledig;
        this.bijhoudingspartij = groep.getBijhoudingspartij();
        this.bijhoudingsaard = groep.getBijhoudingsaard();
        this.nadereBijhoudingsaard = groep.getNadereBijhoudingsaard();
        this.indicatieOnverwerktDocumentAanwezig = groep.getIndicatieOnverwerktDocumentAanwezig();
        getMaterieleHistorie().setDatumAanvangGeldigheid(historie.getDatumAanvangGeldigheid());
        getMaterieleHistorie().setDatumEindeGeldigheid(historie.getDatumEindeGeldigheid());
        setVerantwoordingInhoud(actieInhoud);
        getMaterieleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Retourneert ID van His Persoon Bijhouding.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van His Persoon Bijhouding.
     *
     * @return Persoon.
     */
    public PersoonHisVolledig getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9738)
    public PartijAttribuut getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9737)
    public BijhoudingsaardAttribuut getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 10946)
    public NadereBijhoudingsaardAttribuut getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @AttribuutAccessor(dbObjectId = 9740)
    public JaNeeAttribuut getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (bijhoudingspartij != null) {
            attributen.add(bijhoudingspartij);
        }
        if (bijhoudingsaard != null) {
            attributen.add(bijhoudingsaard);
        }
        if (nadereBijhoudingsaard != null) {
            attributen.add(nadereBijhoudingsaard);
        }
        if (indicatieOnverwerktDocumentAanwezig != null) {
            attributen.add(indicatieOnverwerktDocumentAanwezig);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HisPersoonBijhoudingModel kopieer() {
        return new HisPersoonBijhoudingModel(this);
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_BIJHOUDING;
    }

}
