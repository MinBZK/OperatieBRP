/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonAfgeleidAdministratiefGroepModel implements PersoonAfgeleidAdministratiefGroepBasis {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdmHnd")
    @JsonProperty
    private AdministratieveHandelingModel administratieveHandeling;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsLaatsteWijz"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipLaatsteWijziging;

    @Embedded
    @AttributeOverride(name = SorteervolgordeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Sorteervolgorde"))
    @JsonProperty
    private SorteervolgordeAttribuut sorteervolgorde;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOnverwBijhvoorstelNietIng"))
    @JsonProperty
    private JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsLaatsteWijzGBASystematiek"))
    @JsonProperty
    private DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonAfgeleidAdministratiefGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandeling administratieveHandeling van Afgeleid administratief.
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van Afgeleid administratief.
     * @param sorteervolgorde sorteervolgorde van Afgeleid administratief.
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *            indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig van Afgeleid administratief.
     * @param tijdstipLaatsteWijzigingGBASystematiek tijdstipLaatsteWijzigingGBASystematiek van Afgeleid administratief.
     */
    public AbstractPersoonAfgeleidAdministratiefGroepModel(
        final AdministratieveHandelingModel administratieveHandeling,
        final DatumTijdAttribuut tijdstipLaatsteWijziging,
        final SorteervolgordeAttribuut sorteervolgorde,
        final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
        final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek)
    {
        this.administratieveHandeling = administratieveHandeling;
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
        this.sorteervolgorde = sorteervolgorde;
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
        this.tijdstipLaatsteWijzigingGBASystematiek = tijdstipLaatsteWijzigingGBASystematiek;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfgeleidAdministratiefGroep te kopieren groep.
     */
    public AbstractPersoonAfgeleidAdministratiefGroepModel(final PersoonAfgeleidAdministratiefGroep persoonAfgeleidAdministratiefGroep) {
        this.tijdstipLaatsteWijziging = persoonAfgeleidAdministratiefGroep.getTijdstipLaatsteWijziging();
        this.sorteervolgorde = persoonAfgeleidAdministratiefGroep.getSorteervolgorde();
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig =
                persoonAfgeleidAdministratiefGroep.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig();
        this.tijdstipLaatsteWijzigingGBASystematiek = persoonAfgeleidAdministratiefGroep.getTijdstipLaatsteWijzigingGBASystematiek();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingModel getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipLaatsteWijziging() {
        return tijdstipLaatsteWijziging;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SorteervolgordeAttribuut getSorteervolgorde() {
        return sorteervolgorde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig() {
        return indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getTijdstipLaatsteWijzigingGBASystematiek() {
        return tijdstipLaatsteWijzigingGBASystematiek;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (tijdstipLaatsteWijziging != null) {
            attributen.add(tijdstipLaatsteWijziging);
        }
        if (sorteervolgorde != null) {
            attributen.add(sorteervolgorde);
        }
        if (indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig != null) {
            attributen.add(indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig);
        }
        if (tijdstipLaatsteWijzigingGBASystematiek != null) {
            attributen.add(tijdstipLaatsteWijzigingGBASystematiek);
        }
        return attributen;
    }

}
