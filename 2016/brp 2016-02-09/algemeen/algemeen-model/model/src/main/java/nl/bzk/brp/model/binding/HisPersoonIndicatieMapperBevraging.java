/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

/**
 * Custom marshaller voor indicaties van hoofd personen in bevraging berichten.
 */
public final class HisPersoonIndicatieMapperBevraging extends AbstractIndicatieMapper {

    @Override
    protected boolean historieVeldenMarshallen() {
        return true;
    }

    @Override
    protected boolean marshalObjectSleutel() {
        return true;
    }

    @Override
    protected boolean marshalVoorkomenSleutel() {
        return true;
    }

    @Override
    protected boolean isAutorisatieVanKracht() {
        return false;
    }

    @Override
    protected boolean marshalVerantwoording() {
        return false;
    }
}
