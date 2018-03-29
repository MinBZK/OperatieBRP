/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import java.util.function.Predicate;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;

/**
 * Predicate voor het verwijderen van materiele attributen die op het gegeven
 * materiele peildatum niet meer relevant zijn.
 *
 * Bij materieel terugreizen wordt mogelijk een ander voorkomen actueel.
 * Dit voorkomen bevat dan nog wel een datumEindeGeldigheid attribuut wat de actueelBepaling
 * doet bepalen dat het alsnog niet actueel is...Om dit te voorkomen wordt het attribuut gewist.
 *
 * ActieAanpassingGeldigheid wordt vooralsnog niet verwijderd.
 */
final class WisToekomstigMaterieelAttribuutPredicate implements Predicate<MetaAttribuut> {

    private final int peilDatum;

    /**
     * Constructor.
     * @param peilDatum de materiele peildatum
     */
    WisToekomstigMaterieelAttribuutPredicate(final int peilDatum) {
        this.peilDatum = peilDatum;
    }

    @Override
    public boolean test(final MetaAttribuut metaAttribuut) {
        return !(metaAttribuut.getAttribuutElement().isDatumEindeGeldigheid()
                && DatumUtil.valtDatumBinnenPeriode(metaAttribuut.getWaarde(), peilDatum, null));
    }

}
