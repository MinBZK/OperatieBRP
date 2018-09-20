/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.validatie.Periode;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Ouders relateren.
 * 
 */
@Component
public class BrpOudersRelateren {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Bepaal gerelateerde ouders.
     * 
     * @param ouderBetrokkenheden
     *            losse betrokkenheden
     * @return lijst van gerelateerde betrokkenheden
     */
    public final List<List<BrpBetrokkenheid>> bepaalOuderEilanden(final List<BrpBetrokkenheid> ouderBetrokkenheden) {
        LOG.debug("bepaalOuderEilanden(#ouderBetrokkenheden={})", ouderBetrokkenheden.size());

        final List<BrpBetrokkenheid> losseBetrokkenheden = new ArrayList<BrpBetrokkenheid>(ouderBetrokkenheden);

        final List<List<BrpBetrokkenheid>> eilanden = bepaalInitieleLijstenObvDocumentatie(losseBetrokkenheden);
        LOG.debug("Eilanden obv document ({})", eilanden.size());

        for (final List<BrpBetrokkenheid> eiland : eilanden) {
            voegLosseBetrokkenhedenToeAanEiland(eiland, losseBetrokkenheden);
        }
        LOG.debug("Eilanden obv document met toegevoegde betrokkenheden ({})", eilanden.size());

        while (!losseBetrokkenheden.isEmpty()) {
            final List<BrpBetrokkenheid> eiland = new ArrayList<BrpBetrokkenheid>();
            eiland.add(losseBetrokkenheden.remove(0));

            voegLosseBetrokkenhedenToeAanEiland(eiland, losseBetrokkenheden);

            eilanden.add(eiland);
        }
        LOG.debug("Eilanden ({})", eilanden.size());

        final List<List<BrpBetrokkenheid>> result = new ArrayList<List<BrpBetrokkenheid>>();
        for (final List<BrpBetrokkenheid> eiland : eilanden) {
            result.add(verwijderActieGeldigheid(eiland));
        }

        return result;
    }

    private List<List<BrpBetrokkenheid>> bepaalInitieleLijstenObvDocumentatie(
            final List<BrpBetrokkenheid> losseBetrokkenheden) {
        final Map<String, List<BrpBetrokkenheid>> resultMap = new TreeMap<String, List<BrpBetrokkenheid>>();

        final Iterator<BrpBetrokkenheid> betrokkenhedenIterator = losseBetrokkenheden.iterator();
        while (betrokkenhedenIterator.hasNext()) {
            final BrpBetrokkenheid betrokkenheid = betrokkenhedenIterator.next();

            for (final BrpGroep<BrpOuderInhoud> ouder : betrokkenheid.getOuderStapel()) {
                final String ouderDocument = getOuderDocumentOmschrijving(ouder);

                if (ouderDocument != null) {
                    if (!resultMap.containsKey(ouderDocument)) {
                        resultMap.put(ouderDocument, new ArrayList<BrpBetrokkenheid>());
                    }

                    resultMap.get(ouderDocument).add(betrokkenheid);
                    betrokkenhedenIterator.remove();
                    break; // ouder loop
                }
            }
        }

        return new ArrayList<List<BrpBetrokkenheid>>(resultMap.values());
    }

    private String getOuderDocumentOmschrijving(final BrpGroep<BrpOuderInhoud> ouder) {
        if (ouder.getActieInhoud().getDocumentStapels() != null) {
            for (final BrpStapel<BrpDocumentInhoud> documentStapel : ouder.getActieInhoud().getDocumentStapels()) {
                for (final BrpGroep<BrpDocumentInhoud> document : documentStapel) {
                    final BrpDocumentInhoud inhoud = document.getInhoud();

                    if (!BrpSoortDocumentCode.MIGRATIEVOORZIENING.equals(inhoud.getSoortDocumentCode())) {
                        continue;
                    }
                    if (!BrpPartijCode.MIGRATIEVOORZIENING.equals(inhoud.getPartijCode())) {
                        continue;
                    }
                    if (inhoud.getOmschrijving() != null
                            && inhoud.getOmschrijving().startsWith(
                                    BrpDocumentInhoud.EXTRA_DOCUMENT_OUDER_OMSCHRIJVING)) {
                        return inhoud.getOmschrijving();
                    }
                }
            }
        }

        return null;

    }

    private void voegLosseBetrokkenhedenToeAanEiland(
            final List<BrpBetrokkenheid> eiland,
            final List<BrpBetrokkenheid> losseBetrokkenheden) {
        boolean gevonden = true;
        while (gevonden) {
            gevonden = false;
            final List<BrpBetrokkenheid> kandidaten = new ArrayList<BrpBetrokkenheid>();
            for (final BrpBetrokkenheid kandidaat : losseBetrokkenheden) {
                if (!heeftOverlap(kandidaat, eiland)) {
                    kandidaten.add(kandidaat);
                }
            }
            LOG.debug("kandidaten ({})", kandidaten.size());

            if (!kandidaten.isEmpty()) {
                Collections.sort(kandidaten, new KandidatenComparator(eiland));
                losseBetrokkenheden.remove(kandidaten.get(0));
                eiland.add(kandidaten.get(0));
                gevonden = true;
            }
        }

    }

    private static List<BrpBetrokkenheid> verwijderActieGeldigheid(final List<BrpBetrokkenheid> eiland) {
        final List<Long> actieInhoudIds = new ArrayList<Long>();

        for (final BrpBetrokkenheid eilandBetr : eiland) {
            for (final BrpGroep<BrpOuderInhoud> ouder : eilandBetr.getOuderStapel()) {
                actieInhoudIds.add(ouder.getActieInhoud().getId());
            }
        }

        final List<BrpBetrokkenheid> result = new ArrayList<BrpBetrokkenheid>();
        for (final BrpBetrokkenheid eilandBetr : eiland) {
            final List<BrpGroep<BrpOuderInhoud>> ouders = new ArrayList<BrpGroep<BrpOuderInhoud>>();
            for (final BrpGroep<BrpOuderInhoud> ouder : eilandBetr.getOuderStapel()) {
                if (ouder.getActieGeldigheid() != null && actieInhoudIds.contains(ouder.getActieGeldigheid().getId())) {
                    ouders.add(new BrpGroep<BrpOuderInhoud>(ouder.getInhoud(), new BrpHistorie(ouder.getHistorie()
                            .getDatumAanvangGeldigheid(), null, ouder.getHistorie().getDatumTijdRegistratie(), ouder
                            .getHistorie().getDatumTijdVerval()), ouder.getActieInhoud(), ouder.getActieVerval(),
                            null));
                } else {
                    ouders.add(ouder);
                }
            }

            result.add(new BrpBetrokkenheid(eilandBetr.getRol(), eilandBetr.getIdentificatienummersStapel(),
                    eilandBetr.getGeslachtsaanduidingStapel(), eilandBetr.getGeboorteStapel(), eilandBetr
                            .getOuderlijkGezagStapel(), eilandBetr.getSamengesteldeNaamStapel(),
                    new BrpStapel<BrpOuderInhoud>(ouders)));
        }

        return result;
    }

    private static boolean heeftOverlap(final BrpBetrokkenheid kandidaat, final List<BrpBetrokkenheid> eiland) {
        for (final BrpBetrokkenheid betrokkenheid : eiland) {
            if (heeftOverlap(kandidaat, betrokkenheid)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Bepaal of de ouder stapels van twee betrokkenheden overlap hebben (gekeken naar beide materiele en formele
     * tijdslijnen).
     * 
     * @param betrokkenheid1
     *            betrokkenheid 1
     * @param betrokkenheid2
     *            betrokkenheid 2
     * @return true als de betrokkenheden overlap hebben, anders false
     */
    private static boolean heeftOverlap(final BrpBetrokkenheid betrokkenheid1, final BrpBetrokkenheid betrokkenheid2) {
        return heeftOverlap(betrokkenheid1.getOuderStapel(), betrokkenheid2.getOuderStapel());
    }

    private static <T extends BrpGroepInhoud> boolean heeftOverlap(
            final BrpStapel<T> stapel1,
            final BrpStapel<T> stapel2) {
        for (final BrpGroep<T> groep1 : stapel1) {
            for (final BrpGroep<T> groep2 : stapel2) {
                if (heeftOverlapInFormeleTijd(groep1.getHistorie(), groep2.getHistorie())
                        && heeftOverlapInMaterieleTijd(groep1.getHistorie(), groep2.getHistorie())) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean heeftOverlapInFormeleTijd(final BrpHistorie historie1, final BrpHistorie historie2) {
        final BrpDatumTijd begin1 = historie1.getDatumTijdRegistratie();
        final BrpDatumTijd eind1 = historie1.getDatumTijdVerval();
        final BrpDatumTijd begin2 = historie2.getDatumTijdRegistratie();
        final BrpDatumTijd eind2 = historie2.getDatumTijdVerval();

        return new Periode(begin1, eind1).heeftOverlap(new Periode(begin2, eind2));
    }

    private static boolean heeftOverlapInMaterieleTijd(final BrpHistorie historie1, final BrpHistorie historie2) {
        final BrpDatum begin1 = historie1.getDatumAanvangGeldigheid();
        final BrpDatum eind1 = historie1.getDatumEindeGeldigheid();
        final BrpDatum begin2 = historie2.getDatumAanvangGeldigheid();
        final BrpDatum eind2 = historie2.getDatumEindeGeldigheid();

        return new Periode(begin1, eind1).heeftOverlap(new Periode(begin2, eind2));
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Comparator om kandidaten te sorteren op voorkeur.
     */
    private static final class KandidatenComparator implements Comparator<BrpBetrokkenheid> {

        private final List<BrpBetrokkenheid> eiland;

        private KandidatenComparator(final List<BrpBetrokkenheid> eiland) {
            this.eiland = eiland;
        }

        @Override
        public int compare(final BrpBetrokkenheid o1, final BrpBetrokkenheid o2) {
            final boolean o1HeeftActieOverlap = heeftActieOverlap(eiland, o1);
            final boolean o2HeeftActieOverlap = heeftActieOverlap(eiland, o2);

            int result;

            if (o1HeeftActieOverlap) {
                if (o2HeeftActieOverlap) {
                    result = 0;
                } else {
                    result = -1;
                }
            } else {
                if (o2HeeftActieOverlap) {
                    result = 1;
                } else {
                    result = 0;
                }
            }

            if (result == 0) {
                final BrpDatum datum1 = getDatum(o1);
                final BrpDatum datum2 = getDatum(o2);

                result = datum1.compareTo(datum2);
            }

            return result;
        }

        private BrpDatum getDatum(final BrpBetrokkenheid o1) {
            BrpDatum aanvang = null;

            for (final BrpGroep<BrpOuderInhoud> ouder : o1.getOuderStapel()) {
                if (aanvang == null || aanvang.compareTo(ouder.getHistorie().getDatumAanvangGeldigheid()) > 0) {
                    aanvang = ouder.getHistorie().getDatumAanvangGeldigheid();
                }
            }

            return aanvang;
        }

        private boolean heeftActieOverlap(final List<BrpBetrokkenheid> lijst, final BrpBetrokkenheid o1) {
            for (final BrpBetrokkenheid betr : lijst) {
                if (heeftActieOverlap(betr, o1)) {
                    return true;
                }
            }

            return false;
        }

        // CHECKSTYLE:OFF - Cyclomatic complexity - niet te complx, gewoon een aantal loops
        private boolean heeftActieOverlap(final BrpBetrokkenheid betr, final BrpBetrokkenheid o1) {
            final List<Long> actieIds = new ArrayList<Long>();

            for (final BrpGroep<BrpOuderInhoud> ouder : betr.getOuderStapel()) {
                if (ouder.getActieInhoud() != null) {
                    actieIds.add(ouder.getActieInhoud().getId());
                }
                if (ouder.getActieGeldigheid() != null) {
                    actieIds.add(ouder.getActieGeldigheid().getId());
                }
                if (ouder.getActieVerval() != null) {
                    actieIds.add(ouder.getActieVerval().getId());
                }
            }

            for (final BrpGroep<BrpOuderInhoud> andere : o1.getOuderStapel()) {
                if (andere.getActieInhoud() != null && actieIds.contains(andere.getActieInhoud().getId())
                        || andere.getActieGeldigheid() != null
                        && actieIds.contains(andere.getActieGeldigheid().getId()) || andere.getActieVerval() != null
                        && actieIds.contains(andere.getActieVerval().getId())) {
                    return true;
                }
            }

            return false;

        }
    }

}
