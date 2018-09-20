/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;

/**
 * De conversietabel om 'Lo3 Soort Nederlands reisdocument'-code (GBA Tabel 48) te converteren naar de 'BRP Soort
 * Nederlands reisdocument'-code en vice versa.
 * 
 */
public abstract class AbstractSoortNlReisdocumentConversietabel extends
        AbstractAttribuutConversietabel<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode>
{

    /**
     * Maakt een SoortNlReisdocumentConversietabel object.
     * 
     * @param conversieLijst
     *            de lijst met SoortNlReisdocument conversies.
     */
    public AbstractSoortNlReisdocumentConversietabel(final List<Entry<Lo3SoortNederlandsReisdocument, BrpSoortNederlandsReisdocumentCode>> conversieLijst)
    {
        super(conversieLijst);
    }

    @Override
    protected final Lo3SoortNederlandsReisdocument voegOnderzoekToeLo3(final Lo3SoortNederlandsReisdocument input, final Lo3Onderzoek onderzoek) {
        final Lo3SoortNederlandsReisdocument resultaat;
        if (nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie.isElementGevuld(input)) {
            resultaat = new Lo3SoortNederlandsReisdocument(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3SoortNederlandsReisdocument(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpSoortNederlandsReisdocumentCode voegOnderzoekToeBrp(final BrpSoortNederlandsReisdocumentCode input, final Lo3Onderzoek onderzoek) {
        final BrpSoortNederlandsReisdocumentCode resultaat;
        if (Validatie.isAttribuutGevuld(input)) {
            resultaat = new BrpSoortNederlandsReisdocumentCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpSoortNederlandsReisdocumentCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
