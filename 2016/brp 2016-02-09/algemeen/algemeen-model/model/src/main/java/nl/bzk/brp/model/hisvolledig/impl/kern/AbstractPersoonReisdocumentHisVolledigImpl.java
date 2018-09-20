/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.FormeleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonReisdocumentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Reisdocument.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonReisdocumentHisVolledigImpl implements HisVolledigImpl, PersoonReisdocumentHisVolledigBasis, ALaagAfleidbaar,
        ElementIdentificeerbaar
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @JsonBackReference
    private PersoonHisVolledigImpl persoon;

    @Embedded
    @AssociationOverride(name = SoortNederlandsReisdocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Srt"))
    @JsonProperty
    private SoortNederlandsReisdocumentAttribuut soort;

    @Embedded
    private PersoonReisdocumentStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonReisdocument", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonReisdocumentModel> hisPersoonReisdocumentLijst;

    @Transient
    private FormeleHistorieSet<HisPersoonReisdocumentModel> persoonReisdocumentHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonReisdocumentHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Reisdocument.
     * @param soort soort van Persoon \ Reisdocument.
     */
    public AbstractPersoonReisdocumentHisVolledigImpl(final PersoonHisVolledigImpl persoon, final SoortNederlandsReisdocumentAttribuut soort) {
        this();
        this.persoon = persoon;
        this.soort = soort;

    }

    /**
     * Retourneert ID van Persoon \ Reisdocument.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONREISDOCUMENT", sequenceName = "Kern.seq_PersReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONREISDOCUMENT")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Reisdocument.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Soort van Persoon \ Reisdocument.
     *
     * @return Soort.
     */
    public SoortNederlandsReisdocumentAttribuut getSoort() {
        return soort;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leidALaagAf() {
        final HisPersoonReisdocumentModel actueelStandaard = getPersoonReisdocumentHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PersoonReisdocumentStandaardGroepModel(
                        actueelStandaard.getNummer(),
                        actueelStandaard.getAutoriteitVanAfgifte(),
                        actueelStandaard.getDatumIngangDocument(),
                        actueelStandaard.getDatumEindeDocument(),
                        actueelStandaard.getDatumUitgifte(),
                        actueelStandaard.getDatumInhoudingVermissing(),
                        actueelStandaard.getAanduidingInhoudingVermissing());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormeleHistorieSet<HisPersoonReisdocumentModel> getPersoonReisdocumentHistorie() {
        if (hisPersoonReisdocumentLijst == null) {
            hisPersoonReisdocumentLijst = new HashSet<>();
        }
        if (persoonReisdocumentHistorie == null) {
            persoonReisdocumentHistorie = new FormeleHistorieSetImpl<HisPersoonReisdocumentModel>(hisPersoonReisdocumentLijst);
        }
        return persoonReisdocumentHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_REISDOCUMENT;
    }

}
