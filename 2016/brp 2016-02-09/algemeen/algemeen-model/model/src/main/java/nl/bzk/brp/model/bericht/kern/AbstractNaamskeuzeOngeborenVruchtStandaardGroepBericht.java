/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.NaamskeuzeOngeborenVruchtStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractNaamskeuzeOngeborenVruchtStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        NaamskeuzeOngeborenVruchtStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 9378;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(9379);
    private DatumEvtDeelsOnbekendAttribuut datumNaamskeuzeOngeborenVrucht;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumNaamskeuzeOngeborenVrucht() {
        return datumNaamskeuzeOngeborenVrucht;
    }

    /**
     * Zet Datum naamskeuze ongeboren vrucht van Standaard.
     *
     * @param datumNaamskeuzeOngeborenVrucht Datum naamskeuze ongeboren vrucht.
     */
    public void setDatumNaamskeuzeOngeborenVrucht(final DatumEvtDeelsOnbekendAttribuut datumNaamskeuzeOngeborenVrucht) {
        this.datumNaamskeuzeOngeborenVrucht = datumNaamskeuzeOngeborenVrucht;
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
