/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * 'Normale' Wa11 waarbij het oude a-nummer komt uit 01.20.10 en het nieuwe a-nummer uit 01.01.10.
 */
@Component
public final class Wa11Formatter extends AbstractWa11Formatter implements Formatter {

    @Override
    protected String bepaalOudAnummer(final List<Lo3CategorieWaarde> categorieen, final IdentificatienummerMutatie identificatienummerMutatie) {
        return identificatienummerMutatie.getVervallenIdentificatienummersRecord()
                .getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                .getWaarde()
                .toString();
    }

    @Override
    protected String bepaalNieuwAnummer(final List<Lo3CategorieWaarde> categorieen, final IdentificatienummerMutatie identificatienummerMutatie) {
        return identificatienummerMutatie.getNieuwIdentificatienummersRecord()
                .getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT)
                .getWaarde()
                .toString();
    }

    @Override
    protected String bepaalDatumIngangGeldigheid(final List<Lo3CategorieWaarde> categorieen, final IdentificatienummerMutatie identificatienummerMutatie) {
        return identificatienummerMutatie.getNieuwIdentificatienummersRecord().getDatumAanvangGeldigheid().toString();
    }

}
