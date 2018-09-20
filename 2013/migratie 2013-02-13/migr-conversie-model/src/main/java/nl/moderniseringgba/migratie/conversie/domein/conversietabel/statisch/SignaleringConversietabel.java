/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SignaleringEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code.
 */
public final class SignaleringConversietabel implements Conversietabel<Lo3Signalering, Boolean> {

    @Override
    public Boolean converteerNaarBrp(final Lo3Signalering input) {
        return Lo3SignaleringEnum.SIGNALERING.equalsElement(input) ? Boolean.TRUE : null;
    }

    @Override
    public Lo3Signalering converteerNaarLo3(final Boolean input) {
        return Boolean.TRUE.equals(input) ? Lo3SignaleringEnum.SIGNALERING.asElement() : null;
    }

    @Override
    public boolean valideerLo3(final Lo3Signalering input) {
        return input == null || Lo3SignaleringEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final Boolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
