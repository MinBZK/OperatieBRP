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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroepBasis;

/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonPersoonskaartGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonPersoonskaartGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3662;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3233, 3313);
    private String gemeentePersoonskaartCode;
    private PartijAttribuut gemeentePersoonskaart;
    private JaNeeAttribuut indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Retourneert Gemeente persoonskaart van Persoonskaart.
     *
     * @return Gemeente persoonskaart.
     */
    public String getGemeentePersoonskaartCode() {
        return gemeentePersoonskaartCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Zet Gemeente persoonskaart van Persoonskaart.
     *
     * @param gemeentePersoonskaartCode Gemeente persoonskaart.
     */
    public void setGemeentePersoonskaartCode(final String gemeentePersoonskaartCode) {
        this.gemeentePersoonskaartCode = gemeentePersoonskaartCode;
    }

    /**
     * Zet Gemeente persoonskaart van Persoonskaart.
     *
     * @param gemeentePersoonskaart Gemeente persoonskaart.
     */
    public void setGemeentePersoonskaart(final PartijAttribuut gemeentePersoonskaart) {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
    }

    /**
     * Zet Persoonskaart volledig geconverteerd? van Persoonskaart.
     *
     * @param indicatiePersoonskaartVolledigGeconverteerd Persoonskaart volledig geconverteerd?.
     */
    public void setIndicatiePersoonskaartVolledigGeconverteerd(final JaNeeAttribuut indicatiePersoonskaartVolledigGeconverteerd) {
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (gemeentePersoonskaart != null) {
            attributen.add(gemeentePersoonskaart);
        }
        if (indicatiePersoonskaartVolledigGeconverteerd != null) {
            attributen.add(indicatiePersoonskaartVolledigGeconverteerd);
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
