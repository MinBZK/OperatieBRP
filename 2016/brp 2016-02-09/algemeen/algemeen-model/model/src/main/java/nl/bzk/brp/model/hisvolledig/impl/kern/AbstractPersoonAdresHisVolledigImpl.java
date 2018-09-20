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
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSetImpl;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ALaagAfleidbaar;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledigBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * HisVolledig klasse voor Persoon \ Adres.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonAdresHisVolledigImpl implements HisVolledigImpl, PersoonAdresHisVolledigBasis, ALaagAfleidbaar,
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
    private PersoonAdresStandaardGroepModel standaard;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persoonAdres", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    @JsonProperty
    @JsonManagedReference
    private Set<HisPersoonAdresModel> hisPersoonAdresLijst;

    @Transient
    private MaterieleHistorieSet<HisPersoonAdresModel> persoonAdresHistorie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractPersoonAdresHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Adres.
     */
    public AbstractPersoonAdresHisVolledigImpl(final PersoonHisVolledigImpl persoon) {
        this();
        this.persoon = persoon;

    }

    /**
     * Retourneert ID van Persoon \ Adres.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONADRES", sequenceName = "Kern.seq_PersAdres")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONADRES")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Adres.
     *
     * @return Persoon.
     */
    public PersoonHisVolledigImpl getPersoon() {
        return persoon;
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
        final HisPersoonAdresModel actueelStandaard = getPersoonAdresHistorie().getActueleRecord();
        if (actueelStandaard != null) {
            this.standaard =
                    new PersoonAdresStandaardGroepModel(
                        actueelStandaard.getSoort(),
                        actueelStandaard.getRedenWijziging(),
                        actueelStandaard.getAangeverAdreshouding(),
                        actueelStandaard.getDatumAanvangAdreshouding(),
                        actueelStandaard.getIdentificatiecodeAdresseerbaarObject(),
                        actueelStandaard.getIdentificatiecodeNummeraanduiding(),
                        actueelStandaard.getGemeente(),
                        actueelStandaard.getNaamOpenbareRuimte(),
                        actueelStandaard.getAfgekorteNaamOpenbareRuimte(),
                        actueelStandaard.getGemeentedeel(),
                        actueelStandaard.getHuisnummer(),
                        actueelStandaard.getHuisletter(),
                        actueelStandaard.getHuisnummertoevoeging(),
                        actueelStandaard.getPostcode(),
                        actueelStandaard.getWoonplaatsnaam(),
                        actueelStandaard.getLocatieTenOpzichteVanAdres(),
                        actueelStandaard.getLocatieomschrijving(),
                        actueelStandaard.getBuitenlandsAdresRegel1(),
                        actueelStandaard.getBuitenlandsAdresRegel2(),
                        actueelStandaard.getBuitenlandsAdresRegel3(),
                        actueelStandaard.getBuitenlandsAdresRegel4(),
                        actueelStandaard.getBuitenlandsAdresRegel5(),
                        actueelStandaard.getBuitenlandsAdresRegel6(),
                        actueelStandaard.getLandGebied(),
                        actueelStandaard.getIndicatiePersoonAangetroffenOpAdres());
        } else {
            this.standaard = null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MaterieleHistorieSet<HisPersoonAdresModel> getPersoonAdresHistorie() {
        if (hisPersoonAdresLijst == null) {
            hisPersoonAdresLijst = new HashSet<>();
        }
        if (persoonAdresHistorie == null) {
            persoonAdresHistorie = new MaterieleHistorieSetImpl<HisPersoonAdresModel>(hisPersoonAdresLijst);
        }
        return persoonAdresHistorie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON_ADRES;
    }

}
