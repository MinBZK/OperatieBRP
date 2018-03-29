/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

/**
 * Bevat een lijst met delta processen. De aanroepen van {@link #bepaalVerschillen(DeltaBepalingContext)} en
 * {@link #verwerkVerschillen(DeltaBepalingContext)} worden geforward naar alle delta processen die zijn toegevoegd aan
 * dit samengestelde deltaproces.
 */
public final class SamengesteldDeltaProces implements DeltaProces {

    private final List<DeltaProces> deltaProcessen;

    /**
     * Maakt een nieuw SamengesteldDeltaProces object.
     * @param deltaProcessen de processen die moeten worden toegevoegd aan dit samengestelde delta proces
     */
    private SamengesteldDeltaProces(final List<DeltaProces> deltaProcessen) {
        this.deltaProcessen = new ArrayList<>(deltaProcessen);
    }

    /**
     * Maakt een SamengesteldDeltaProces object die alle soorten delta processen bevat.
     * @return een samengesteld delta process met daarin alle soorten processen
     */
    public static SamengesteldDeltaProces newInstanceMetAlleProcessen() {
        final List<DeltaProces> deltaProcessen = new ArrayList<>();
        // Onderzoekproces moet altijd als eerst gedaan worden ivm mogelijk herkoppelen van onderzoek aan een andere
        // (historisch) entiteit.
        deltaProcessen.add(new OnderzoekDeltaProces());
        deltaProcessen.add(new DeltaRootEntiteitenProces());
        deltaProcessen.add(new ActieConsolidatieProces());
        deltaProcessen.add(new LoggingDeltaProces());
        deltaProcessen.add(new AfgeleidAdministratiefDeltaProces());
        return new SamengesteldDeltaProces(deltaProcessen);
    }

    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        for (final DeltaProces deltaProces : deltaProcessen) {
            deltaProces.bepaalVerschillen(context);
        }

    }

    @Override
    public void verwerkVerschillen(final DeltaBepalingContext context) {
        if (context.heeftPersoonWijzigingen()) {
            for (final DeltaProces deltaProces : deltaProcessen) {
                deltaProces.verwerkVerschillen(context);
            }
        } else {
            new OnderzoekDeltaProces().verwerkVerschillen(context);
            new LoggingDeltaProces().verwerkVerschillen(context);
            new AfgeleidAdministratiefDeltaProces().verwerkVerschillen(context);
        }
    }
}
