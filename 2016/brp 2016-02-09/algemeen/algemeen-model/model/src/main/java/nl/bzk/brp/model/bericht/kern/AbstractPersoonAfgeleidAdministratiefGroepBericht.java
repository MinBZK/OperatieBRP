/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonAfgeleidAdministratiefGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonAfgeleidAdministratiefGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3909;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(10111, 3251, 10404, 10899, 10901);
    private AdministratieveHandelingBericht administratieveHandeling;
    private DatumTijdAttribuut tijdstipLaatsteWijziging;
    private SorteervolgordeAttribuut sorteervolgorde;
    private JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    private DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek;

    /**
     * {@inheritDoc}
     */
    @Override
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
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
     * Zet Administratieve handeling van Afgeleid administratief.
     *
     * @param administratieveHandeling Administratieve handeling.
     */
    public void setAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Zet Tijdstip laatste wijziging van Afgeleid administratief.
     *
     * @param tijdstipLaatsteWijziging Tijdstip laatste wijziging.
     */
    public void setTijdstipLaatsteWijziging(final DatumTijdAttribuut tijdstipLaatsteWijziging) {
        this.tijdstipLaatsteWijziging = tijdstipLaatsteWijziging;
    }

    /**
     * Zet Sorteervolgorde van Afgeleid administratief.
     *
     * @param sorteervolgorde Sorteervolgorde.
     */
    public void setSorteervolgorde(final SorteervolgordeAttribuut sorteervolgorde) {
        this.sorteervolgorde = sorteervolgorde;
    }

    /**
     * Zet Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig? van Afgeleid administratief.
     *
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig Onverwerkt bijhoudingsvoorstel
     *            niet-ingezetene aanwezig?.
     */
    public void setIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig(
        final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig)
    {
        this.indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig = indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig;
    }

    /**
     * Zet Tijdstip laatste wijziging GBA-systematiek van Afgeleid administratief.
     *
     * @param tijdstipLaatsteWijzigingGBASystematiek Tijdstip laatste wijziging GBA-systematiek.
     */
    public void setTijdstipLaatsteWijzigingGBASystematiek(final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek) {
        this.tijdstipLaatsteWijzigingGBASystematiek = tijdstipLaatsteWijzigingGBASystematiek;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
