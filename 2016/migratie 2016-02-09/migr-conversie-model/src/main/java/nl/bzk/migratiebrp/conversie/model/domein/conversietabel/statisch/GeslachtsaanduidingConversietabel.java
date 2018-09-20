/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP geslachtsaanduiding.
 * 
 */
public final class GeslachtsaanduidingConversietabel implements Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> {

    @Override
    public BrpGeslachtsaanduidingCode converteerNaarBrp(final Lo3Geslachtsaanduiding input) {
        if (input == null) {
            return null;
        }

        // Geslachtsaanduiding is een statisch codering
        final BrpGeslachtsaanduidingCode result;
        if (Lo3GeslachtsaanduidingEnum.MAN.equalsElement(input)) {
            result = new BrpGeslachtsaanduidingCode(BrpGeslachtsaanduidingCode.MAN.getWaarde(), input.getOnderzoek());
        } else if (Lo3GeslachtsaanduidingEnum.VROUW.equalsElement(input)) {
            result = new BrpGeslachtsaanduidingCode(BrpGeslachtsaanduidingCode.VROUW.getWaarde(), input.getOnderzoek());
        } else if (Lo3GeslachtsaanduidingEnum.ONBEKEND.equalsElement(input)) {
            result = new BrpGeslachtsaanduidingCode(BrpGeslachtsaanduidingCode.ONBEKEND.getWaarde(), input.getOnderzoek());
        } else {
            result = new BrpGeslachtsaanduidingCode(null, input.getOnderzoek());
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
        if (BrpGeslachtsaanduidingCode.MAN.getWaarde().equals(input.getWaarde())) {
            result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.MAN.getCode(), input.getOnderzoek());
        } else if (BrpGeslachtsaanduidingCode.VROUW.getWaarde().equals(input.getWaarde())) {
            result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.VROUW.getCode(), input.getOnderzoek());
        } else if (BrpGeslachtsaanduidingCode.ONBEKEND.getWaarde().equals(input.getWaarde())) {
            result = new Lo3Geslachtsaanduiding(Lo3GeslachtsaanduidingEnum.ONBEKEND.getCode(), input.getOnderzoek());
        } else {
            result = new Lo3Geslachtsaanduiding(null, input.getOnderzoek());
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3Geslachtsaanduiding input) {
        return !Validatie.isElementGevuld(input) || Lo3GeslachtsaanduidingEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpGeslachtsaanduidingCode input) {
        // Enum
        return true;
    }
}
