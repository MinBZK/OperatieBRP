/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP aanduiding huisnummer.
 */
public final class AanduidingHuisnummerConversietabel implements Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode> {

    @Override
    public BrpAanduidingBijHuisnummerCode converteerNaarBrp(final Lo3AanduidingHuisnummer input) {
        if (input == null) {
            return null;
        }
        return new BrpAanduidingBijHuisnummerCode(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public Lo3AanduidingHuisnummer converteerNaarLo3(final BrpAanduidingBijHuisnummerCode input) {
        if (input == null) {
            return null;
        }

        final String waarde;
        if (input.getWaarde() == null) {
            waarde = null;
        } else if (BrpAanduidingBijHuisnummerCode.CODE_BY.equals(input.getWaarde())) {
            waarde = Lo3AanduidingHuisnummerEnum.BY.getCode();
        } else if (BrpAanduidingBijHuisnummerCode.CODE_TO.equals(input.getWaarde())) {
            waarde = Lo3AanduidingHuisnummerEnum.TEGENOVER.getCode();
        } else {
            throw new IllegalArgumentException("Onbekend waarde voor BRP aanduiding bij huisnummer: " + input);
        }

        return new Lo3AanduidingHuisnummer(waarde, input.getOnderzoek());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingHuisnummer input) {
        return !Validatie.isElementGevuld(input) || Lo3AanduidingHuisnummerEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpAanduidingBijHuisnummerCode input) {
        return !nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie.isAttribuutGevuld(input)
               || BrpAanduidingBijHuisnummerCode.CODE_BY.equals(input.getWaarde())
               || BrpAanduidingBijHuisnummerCode.CODE_TO.equals(input.getWaarde());
    }

}
