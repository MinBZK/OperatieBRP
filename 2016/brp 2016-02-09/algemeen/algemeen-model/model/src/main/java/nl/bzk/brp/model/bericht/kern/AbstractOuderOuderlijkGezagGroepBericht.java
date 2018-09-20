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
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.OuderOuderlijkGezagGroepBasis;

/**
 * Gegevens met betrekking tot het ouderlijk gezag.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractOuderOuderlijkGezagGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        OuderOuderlijkGezagGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3211;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3208);
    private JaNeeAttribuut indicatieOuderHeeftGezag;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOuderHeeftGezag() {
        return indicatieOuderHeeftGezag;
    }

    /**
     * Zet Ouder heeft gezag? van Ouderlijk gezag.
     *
     * @param indicatieOuderHeeftGezag Ouder heeft gezag?.
     */
    public void setIndicatieOuderHeeftGezag(final JaNeeAttribuut indicatieOuderHeeftGezag) {
        this.indicatieOuderHeeftGezag = indicatieOuderHeeftGezag;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieOuderHeeftGezag != null) {
            attributen.add(indicatieOuderHeeftGezag);
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
