/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP geslachtsaanduiding.
 * 
 */
public final class GeslachtsaanduidingConversietabel implements
        Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> {

    @Override
    public BrpGeslachtsaanduidingCode converteerNaarBrp(final Lo3Geslachtsaanduiding input) {
        if (input == null) {
            return null;
        }

        final Lo3GeslachtsaanduidingEnum code = Lo3GeslachtsaanduidingEnum.getByCode(input.getCode());

        // Geslachtsaanduiding is een statisch codering
        final BrpGeslachtsaanduidingCode result;
        switch (code) {
            case MAN:
                result = BrpGeslachtsaanduidingCode.MAN;
                break;
            case VROUW:
                result = BrpGeslachtsaanduidingCode.VROUW;
                break;
            default:
                result = BrpGeslachtsaanduidingCode.ONBEKEND;
                break;
        }
        return result;
    }

    @Override
    public Lo3Geslachtsaanduiding converteerNaarLo3(final BrpGeslachtsaanduidingCode input) {
        if (input == null) {
            return null;
        }

        // Geslachtsaanduiding is een statisch codering
        final Lo3Geslachtsaanduiding result;
        switch (input) {
            case MAN:
                result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.MAN.getCode());
                break;
            case VROUW:
                result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.VROUW.getCode());
                break;
            default:
                result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.ONBEKEND.getCode());
                break;
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3Geslachtsaanduiding input) {
        return input == null || Lo3GeslachtsaanduidingEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpGeslachtsaanduidingCode input) {
        // Enum
        return true;
    }
}
