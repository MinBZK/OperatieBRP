/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de geslachtsaanduiding.
 */
@Component
public abstract class AbstractGeslachtsaanduidingMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonGeslachtsaanduidingModel, BrpGeslachtsaanduidingInhoud>
{
    private final ElementEnum geslachtsaanduidingCode;

    /**
     * Constructor.
     * 
     * @param datumAanvangGeldigheid
     *            element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            element voor datum einde geldigheid
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param geslachtsaanduidingCode
     *            element voor geslachtsaanduiding
     */
    protected AbstractGeslachtsaanduidingMapper(
        final ElementEnum datumAanvangGeldigheid,
        final ElementEnum datumEindeGeldigheid,
        final ElementEnum tijdstipRegistratie,
        final ElementEnum tijdstipVerval,
        final ElementEnum geslachtsaanduidingCode)
    {
        super(datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
        this.geslachtsaanduidingCode = geslachtsaanduidingCode;
    }

    @Override
    protected final Iterable<HisPersoonGeslachtsaanduidingModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonGeslachtsaanduidingHistorie();
    }

    @Override
    public final BrpGeslachtsaanduidingInhoud mapInhoud(final HisPersoonGeslachtsaanduidingModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpGeslachtsaanduidingInhoud(
            BrpMapperUtil.mapBrpGeslachtsaanduidingCode(
                historie.getGeslachtsaanduiding(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), geslachtsaanduidingCode, true)));
    }
}
