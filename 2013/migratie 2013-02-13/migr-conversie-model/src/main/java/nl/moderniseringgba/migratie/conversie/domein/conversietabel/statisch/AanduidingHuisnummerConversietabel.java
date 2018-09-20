/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingHuisnummerEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP aanduiding huisnummer.
 */
public final class AanduidingHuisnummerConversietabel implements
        Conversietabel<Lo3AanduidingHuisnummer, BrpAanduidingBijHuisnummerCode> {

    @Override
    public BrpAanduidingBijHuisnummerCode converteerNaarBrp(final Lo3AanduidingHuisnummer input) {
        return input == null ? null : new BrpAanduidingBijHuisnummerCode(input.getCode());
    }

    @Override
    public Lo3AanduidingHuisnummer converteerNaarLo3(final BrpAanduidingBijHuisnummerCode input) {
        if (input == null) {
            return null;
        }

        final Lo3AanduidingHuisnummer result;
        if ("BY".equalsIgnoreCase(input.getCode()) || "BIJ".equalsIgnoreCase(input.getCode())) {
            result = new Lo3AanduidingHuisnummer(Lo3AanduidingHuisnummerEnum.BY.getCode());
        } else if ("TO".equalsIgnoreCase(input.getCode())) {
            result = new Lo3AanduidingHuisnummer(Lo3AanduidingHuisnummerEnum.TEGENOVER.getCode());
        } else {
            throw new IllegalArgumentException("Onbekend waarde voor BRP aanduiding bij huisnummer: " + input);
        }

        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingHuisnummer input) {
        return input == null || Lo3AanduidingHuisnummerEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpAanduidingBijHuisnummerCode input) {
        return input == null || "BY".equals(input.getCode()) || "BIJ".equals(input.getCode())
                || "TO".equals(input.getCode());
    }

}
