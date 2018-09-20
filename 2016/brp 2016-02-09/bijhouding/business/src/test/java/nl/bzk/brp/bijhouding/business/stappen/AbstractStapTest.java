/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


public abstract class AbstractStapTest {

    /**
     * Zoek een melding op in de lijst met de opgegeven {@link Regel}
     *
     * @param regel de code waarmee gezocht wordt
     * @param meldingen lijst met {@link Melding}
     * @return null wanneer de melding niet gevonden kan worden of de corresponderende {@link
     *         nl.bzk.brp.model.validatie.Melding}
     */
    protected Melding zoekMelding(final Regel regel, final List<Melding> meldingen) {
        Melding resultaat = null;
        for (final Melding melding : meldingen) {
            if (regel.equals(melding.getRegel())) {
                resultaat = melding;
            }
        }

        return resultaat;
    }

    /**
     * Bouwt en retourneert een standaard {@link BerichtContext} instantie, met ingevulde in-
     * en uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    protected BijhoudingBerichtContext bouwBerichtContext(final String... bsns) {
        return bouwBerichtContextMetSpecifiekeWaarden("abc", true, "0034", bsns);
    }

    /**
     * Bouwt en retourneert een standaard {@link BerichtContext} instantie, met ingevulde in-
     * en uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @param soortDoc soort document
     * @param metStandaardGroep inclusief aanmaak standaard groep van document
     * @param partijCode partij code
     * @param bsns bsns
     * @return een geldig bericht context.
     */
    protected BijhoudingBerichtContext bouwBerichtContextMetSpecifiekeWaarden(final String soortDoc,
                                                                    final boolean metStandaardGroep,
                                                                    final String partijCode,
                                                                    final String... bsns)
    {
        final BerichtenIds ids = new BerichtenIds(2L, 3L);
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde();

        final BijhoudingBerichtContext context = new BijhoudingBerichtContext(ids, partij, "ref", null);

        final AdministratieveHandelingBronBericht bron = new AdministratieveHandelingBronBericht();
        final DocumentBericht doc = new DocumentBericht();
        doc.setSoortNaam(soortDoc);
        if (metStandaardGroep) {
            doc.setStandaard(new DocumentStandaardGroepBericht());
            doc.getStandaard().setPartijCode(partijCode);
        }
        bron.setDocument(doc);

        final ArrayList<BerichtIdentificeerbaar> berichtIdentificeerbaars = new ArrayList<>();
        berichtIdentificeerbaars.add(bron);

        context.setIdentificeerbareObjecten(new CommunicatieIdMap());
        context.getIdentificeerbareObjecten().put("com.id.bron1", berichtIdentificeerbaars);

        return context;
    }

    protected BijhoudingBerichtContext bouwBerichtContext() {
        final BerichtenIds ids = new BerichtenIds(2L, 3L);
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde();
        final BijhoudingBerichtContext b = new BijhoudingBerichtContext(ids, partij, "ref", null);
        return new BijhoudingBerichtContext(ids, partij, "ref", null);
    }

}
