/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.brp0200.GroepBerichtResultaat;
import nl.bzk.brp.brp0200.ObjectFactory;
import nl.bzk.brp.brp0200.VerwerkingsresultaatNaam;
import nl.bzk.brp.brp0200.VerwerkingsresultaatNaamS;
import nl.bzk.brp.brp0200.VrijBerichtStuurVrijBericht;
import nl.bzk.brp.brp0200.VrijBerichtStuurVrijBerichtResultaat;
import nl.bzk.brp.vrijbericht.vrijbericht.service.VrbStuurVrijBericht;

/**
 * Stub voor {@link VrbStuurVrijBericht}.
 */
final class VrijBerichtServiceStub implements VrbStuurVrijBericht {

    @Override
    public VrijBerichtStuurVrijBerichtResultaat stuurVrijBericht(final VrijBerichtStuurVrijBericht stuurVrijBerichtVerzoek) {
        final ObjectFactory objectFactory = new ObjectFactory();
        final VrijBerichtStuurVrijBerichtResultaat berichtResultaat = new VrijBerichtStuurVrijBerichtResultaat();
        final GroepBerichtResultaat resultaat = new GroepBerichtResultaat();
        final VerwerkingsresultaatNaam verwerkingsresultaatNaam = objectFactory.createVerwerkingsresultaatNaam();
        verwerkingsresultaatNaam.setValue(VerwerkingsresultaatNaamS.GESLAAGD);
        resultaat.setVerwerking(objectFactory.createGroepBerichtResultaatVerwerking(verwerkingsresultaatNaam));
        berichtResultaat.setResultaat(objectFactory.createObjecttypeBerichtResultaat(resultaat));
        return berichtResultaat;
    }
}
