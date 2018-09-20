/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.autaut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonAfnemerindicatieStandaardGroepBericht extends AbstractFormeleHistorieGroepBericht implements Groep,
        PersoonAfnemerindicatieStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 10319;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(10327, 10328);
    private DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode;
    private DatumAttribuut datumEindeVolgen;

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    /**
     * Zet Datum aanvang materiële periode van Standaard.
     *
     * @param datumAanvangMaterielePeriode Datum aanvang materiële periode.
     */
    public void setDatumAanvangMaterielePeriode(final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    /**
     * Zet Datum einde volgen van Standaard.
     *
     * @param datumEindeVolgen Datum einde volgen.
     */
    public void setDatumEindeVolgen(final DatumAttribuut datumEindeVolgen) {
        this.datumEindeVolgen = datumEindeVolgen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvangMaterielePeriode != null) {
            attributen.add(datumAanvangMaterielePeriode);
        }
        if (datumEindeVolgen != null) {
            attributen.add(datumEindeVolgen);
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
