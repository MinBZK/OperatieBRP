/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Voegt verbintenissen (huwelijken en geregistreerde partnerschappen) samen.
 */
@Component
public class BrpVerbintenissen {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final BrpRedenEindeRelatieCode EINDE = new BrpRedenEindeRelatieCode("Z");

    /**
     * Voegt verbintenissen samen obv acties.
     * 
     * @param relaties
     *            alle relaties
     * @return lijst van lijsten van stapels die samen horen
     */
    public final List<List<BrpRelatie>> bepaalSamenhangendeVerbintenissen(final List<BrpRelatie> relaties) {

        final List<BrpRelatie> verbintenissen = new ArrayList<BrpRelatie>();
        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.HUWELIJK.equals(relatie.getSoortRelatieCode())
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatieCode())) {
                verbintenissen.add(relatie);
            }
        }

        final List<Verbintenis> losseVerbintenissen = new ArrayList<Verbintenis>(verbintenissen.size());
        for (final BrpRelatie verbintenis : verbintenissen) {
            losseVerbintenissen.add(new Verbintenis(verbintenis));
        }

        final List<List<BrpRelatie>> result = new ArrayList<List<BrpRelatie>>();
        while (!losseVerbintenissen.isEmpty()) {
            // Verwijder het eerst record
            final List<Verbintenis> eiland = new ArrayList<Verbintenis>();
            eiland.add(losseVerbintenissen.remove(0));

            for (int eilandIndex = 0; eilandIndex < eiland.size(); eilandIndex++) {
                final Iterator<Verbintenis> actieIterator = losseVerbintenissen.iterator();
                while (actieIterator.hasNext()) {
                    final Verbintenis losseVerbintenis = actieIterator.next();

                    if (eiland.get(eilandIndex).heeftOverlap(losseVerbintenis)) {
                        eiland.add(losseVerbintenis);
                        actieIterator.remove();
                    }
                }
            }

            final List<BrpRelatie> relatieEiland = new ArrayList<BrpRelatie>();
            for (final Verbintenis verb : eiland) {
                relatieEiland.add(verb.getStapel());
            }

            result.add(relatieEiland);
        }

        return result;
    }

    
    
    
    
    
    
    
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    
    /**
     * Enkele verbintenis met logica om relaties tussen verbintenissen te zien.
     */
    private static class Verbintenis {
        private final BrpRelatie stapel;
        private final List<Long> mijnEindeActies = new ArrayList<Long>();
        private final List<Long> mijnBeginActies = new ArrayList<Long>();

        /**
         * Maak een verbintenis obv een relatie stapel.
         * 
         * @param stapel
         *            relatie stapel
         */
        public Verbintenis(final BrpRelatie stapel) {
            this.stapel = stapel;

            for (final BrpGroep<BrpRelatieInhoud> groep : stapel.getRelatieStapel()) {
                final boolean isEinde = EINDE.equals(groep.getInhoud().getRedenEinde());

                if (groep.getActieInhoud() != null) {
                    mijnBeginActies.add(groep.getActieInhoud().getId());
                    if (isEinde) {
                        mijnEindeActies.add(groep.getActieInhoud().getId());
                    }
                }
                if (groep.getActieGeldigheid() != null) {
                    mijnBeginActies.add(groep.getActieGeldigheid().getId());
                    if (isEinde) {
                        mijnEindeActies.add(groep.getActieGeldigheid().getId());
                    }
                }
                if (groep.getActieVerval() != null) {
                    mijnBeginActies.add(groep.getActieVerval().getId());
                    if (isEinde) {
                        mijnEindeActies.add(groep.getActieVerval().getId());
                    }
                }
            }

            LOG.debug("Verbintenis:\n   stapel:{}\n   einde:{}\n   begin:{}", new Object[] { stapel, mijnEindeActies,
                    mijnBeginActies, });
        }

        private boolean heeftOverlap(final Verbintenis losseVerbintenis) {
            final boolean result =
                    internalHeeftOverlap(losseVerbintenis) ? true : losseVerbintenis.internalHeeftOverlap(this);
            LOG.debug("heeftOverlap -> {} ", result);
            return result;
        }

        private boolean internalHeeftOverlap(final Verbintenis losseVerbintenis) {
            for (final Long mijnEindeActieId : mijnEindeActies) {
                for (final Long beginActieId : losseVerbintenis.mijnBeginActies) {
                    if (mijnEindeActieId.equals(beginActieId)) {
                        return true;
                    }
                }
            }

            return false;
        }

        public BrpRelatie getStapel() {
            return stapel;
        }
    }

}
