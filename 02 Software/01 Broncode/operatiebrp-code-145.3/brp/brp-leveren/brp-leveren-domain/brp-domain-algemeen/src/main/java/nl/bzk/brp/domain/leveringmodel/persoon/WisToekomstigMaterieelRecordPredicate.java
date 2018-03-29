/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.util.function.Predicate;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Predicate voor het verwijderen van materiele records welke een datumaanvang
 * in de toekomst hebben.
 */
final class WisToekomstigMaterieelRecordPredicate implements Predicate<MetaRecord> {

    private final int peilDatum;

    /**
     * Constructor.
     *
     * @param peilDatum de materiele peildatum
     */
    WisToekomstigMaterieelRecordPredicate(final int peilDatum) {
        this.peilDatum = peilDatum;
    }

    @Override
    public boolean test(final MetaRecord metaRecord) {
        //true is behouden
        if (metaRecord.getDatumAanvangGeldigheid() == null) {
            return true;
        }
        //behoud als datumAanvang val op/voor peildatum
        return DatumUtil.valtDatumBinnenPeriodeInclusief(metaRecord.getDatumAanvangGeldigheid(), null, peilDatum);
    }
}
