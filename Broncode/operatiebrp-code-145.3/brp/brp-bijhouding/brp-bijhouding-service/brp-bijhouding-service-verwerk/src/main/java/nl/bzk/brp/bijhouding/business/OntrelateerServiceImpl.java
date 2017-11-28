/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingPersoon;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingRelatie;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.ontrelateren.OntrelateerContext;
import nl.bzk.brp.bijhouding.ontrelateren.OntrelateerRelatieHelper;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link OntrelateerService}.
 */
@Service
public final class OntrelateerServiceImpl implements OntrelateerService {

    @Override
    public void ontrelateer(final BijhoudingsplanContext bijhoudingsplanContext,
                            final BijhoudingVerzoekBericht verzoekBericht) {
        ontrelateerRelaties(verzoekBericht.getTijdstipOntvangst().toTimestamp(), bepaalTeOntrelaterenRelaties(bijhoudingsplanContext), bijhoudingsplanContext,
                verzoekBericht);
    }

    @Override
    public List<OntrelateerRelatieHelper> bepaalTeOntrelaterenRelaties(final BijhoudingsplanContext bijhoudingsplanContext) {
        final OntrelateerContext ontrelateerContext = new OntrelateerContext();
        final List<OntrelateerRelatieHelper> results = new ArrayList<>();
        for (final BijhoudingPersoonPaar bijhoudingPersoonPaar : bepaalCombinatiesVanVerwerkbareEnNietVerwerkbarePersonen(
                new ArrayList<>(bijhoudingsplanContext.getPersonenUitHetBijhoudingsplan()))) {
            for (final BijhoudingRelatie bijhoudingRelatie : zoekRelatiesTussenPersonen(bijhoudingPersoonPaar)) {
                results.add(OntrelateerRelatieHelper
                        .getInstance(ontrelateerContext, bijhoudingPersoonPaar.getHoofdPersoon(), bijhoudingPersoonPaar.getGerelateerdePersoon(),
                                bijhoudingRelatie));
            }
        }
        return results;
    }

    @Override
    public void ontrelateerRelaties(final Timestamp tijdstipOntvangst, final List<OntrelateerRelatieHelper> teOntrelaterenRelaties,
                                    final BijhoudingsplanContext bijhoudingsplanContext, final BijhoudingVerzoekBericht verzoekBericht) {
        Timestamp datumTijdRegistratieAh = minusMillisecond(tijdstipOntvangst);
        final Map<Number, BijhoudingPersoon> ontrelateerdeNietVerwerktePersonen = new HashMap<>();
        for (final OntrelateerRelatieHelper teOntrelaterenRelatie : teOntrelaterenRelaties) {
            teOntrelaterenRelatie.ontrelateer(datumTijdRegistratieAh);
            bijhoudingsplanContext.addPersoonDieNietVerwerktMaarWelOntrelateerdIs(teOntrelaterenRelatie.getGerelateerdePersoon());
            datumTijdRegistratieAh = minusMillisecond(datumTijdRegistratieAh);
            if (teOntrelaterenRelatie.isNieuweRelatieNodigVoorVerdereVerwerking()) {
                verzoekBericht.vervangEntiteitMetId(BijhoudingRelatie.class, teOntrelaterenRelatie.getRelatie().getId(),
                        teOntrelaterenRelatie.getRelatieVoorVerdereVerwerking());
            }
            registreerKopiePersoonNaOntrelateren(ontrelateerdeNietVerwerktePersonen, teOntrelaterenRelatie.getGerelateerdePersoon().getId(),
                    teOntrelaterenRelatie.getGerelateerdePersoonVoorVerdereVerwerking());
        }
        for (final Map.Entry<Number, BijhoudingPersoon> ontrelateerden : ontrelateerdeNietVerwerktePersonen.entrySet()) {
            ontrelateerden.getValue().setIsResultaatVanOntrelateren();
            verzoekBericht.vervangEntiteitMetId(BijhoudingPersoon.class, ontrelateerden.getKey(), ontrelateerden.getValue());
        }
    }

    private void registreerKopiePersoonNaOntrelateren(final Map<Number, BijhoudingPersoon> ontrelateerdeNietVerwerktePersonen,
                                                      final Number idVanOudePersoon,
                                                      final BijhoudingPersoon kopiePersoonNaOntrelateren) {
        if (!ontrelateerdeNietVerwerktePersonen.containsKey(idVanOudePersoon)) {
            ontrelateerdeNietVerwerktePersonen.put(idVanOudePersoon, kopiePersoonNaOntrelateren);
        } else {
            ontrelateerdeNietVerwerktePersonen.get(idVanOudePersoon).addDelegate(kopiePersoonNaOntrelateren.getDelegates().iterator().next());
        }
    }

    private Timestamp minusMillisecond(final Timestamp tijdstip) {
        return new Timestamp(tijdstip.getTime() - 1);
    }

    private List<BijhoudingRelatie> zoekRelatiesTussenPersonen(final BijhoudingPersoonPaar bijhoudingPersoonPaar) {
        final List<BijhoudingRelatie> results = new ArrayList<>();
        for (final Betrokkenheid partnerBetrokkenheid : bijhoudingPersoonPaar.getHoofdPersoon().getActuelePartners()) {
            if (bijhoudingPersoonPaar.komtGerelateerdePersoonOvereenMetBetrokkenheid(partnerBetrokkenheid)) {
                results.add(BijhoudingRelatie.decorate(partnerBetrokkenheid.getRelatie()));
            }
        }
        for (final Betrokkenheid ouderBetrokkenheid : bijhoudingPersoonPaar.getHoofdPersoon().getActueleOuders()) {
            if (bijhoudingPersoonPaar.komtGerelateerdePersoonOvereenMetBetrokkenheid(ouderBetrokkenheid)) {
                results.add(BijhoudingRelatie.decorate(ouderBetrokkenheid.getRelatie()));
            }
        }
        for (final Betrokkenheid kindBetrokkenheid : bijhoudingPersoonPaar.getHoofdPersoon().getActueleKinderen()) {
            if (bijhoudingPersoonPaar.komtGerelateerdePersoonOvereenMetBetrokkenheid(kindBetrokkenheid)) {
                results.add(BijhoudingRelatie.decorate(kindBetrokkenheid.getRelatie()));
            }
        }
        return results;
    }

    private List<BijhoudingPersoonPaar> bepaalCombinatiesVanVerwerkbareEnNietVerwerkbarePersonen(final List<BijhoudingPersoon> personenUitHetBijhoudingsplan) {
        final List<BijhoudingPersoonPaar> results = new ArrayList<>();
        for (final Paar indexPaar : bepaalCombinatiesVanTwee(genereerIndexLijst(personenUitHetBijhoudingsplan.size()))) {
            final BijhoudingPersoonPaar
                    combinatieVanPersonen =
                    new BijhoudingPersoonPaar(personenUitHetBijhoudingsplan.get(indexPaar.index1), personenUitHetBijhoudingsplan.get(indexPaar.index2));
            if (combinatieVanPersonen.isCombinatieVerwerkbaarEnNietVerwerkbaar()) {
                results.add(combinatieVanPersonen);
            }
        }
        return results;
    }

    private static Collection<Integer> genereerIndexLijst(final int aantal) {
        final Collection<Integer> results = new ArrayList<>();
        for (int index = 0; index < aantal; index++) {
            results.add(index);
        }
        return results;
    }

    private static List<Paar> bepaalCombinatiesVanTwee(final Collection<Integer> nummerLijst) {
        final Integer[] nummers = nummerLijst.toArray(new Integer[nummerLijst.size()]);
        final List<Paar> results = new ArrayList<>();
        for (int index1 = 0; index1 < nummers.length; index1++) {
            for (int index2 = 0; index2 < nummers.length; index2++) {
                final Paar paar = new Paar(nummers[index1], nummers[index2]);
                if (index1 != index2 && !results.contains(paar)) {
                    results.add(paar);
                }
            }
        }
        return results;
    }

    private static final class BijhoudingPersoonPaar {
        private final BijhoudingPersoon persoon1;
        private final BijhoudingPersoon persoon2;

        private BijhoudingPersoonPaar(final BijhoudingPersoon persoon1, final BijhoudingPersoon persoon2) {
            this.persoon1 = persoon1;
            this.persoon2 = persoon2;
        }

        private boolean isCombinatieVerwerkbaarEnNietVerwerkbaar() {
            return (persoon1.isVerwerkbaar() && !persoon2.isVerwerkbaar()) || (!persoon1.isVerwerkbaar() && persoon2.isVerwerkbaar());
        }

        private boolean komtGerelateerdePersoonOvereenMetBetrokkenheid(final Betrokkenheid betrokkenheid) {
            return getGerelateerdePersoon().getId() != null && getGerelateerdePersoon().getId().equals(betrokkenheid.getPersoon().getId());
        }

        private BijhoudingPersoon getHoofdPersoon() {
            if (persoon1.isVerwerkbaar()) {
                return persoon1;
            } else if (persoon2.isVerwerkbaar()) {
                return persoon2;
            } else {
                throw new IllegalStateException("Dit BijhoudingPersoonPaar bevat geen hoofdpersoon.");
            }
        }

        private BijhoudingPersoon getGerelateerdePersoon() {
            if (!persoon1.isVerwerkbaar()) {
                return persoon1;
            } else if (!persoon2.isVerwerkbaar()) {
                return persoon2;
            } else {
                throw new IllegalStateException("Dit BijhoudingPersoonPaar bevat geen gerelateerdepersoon.");
            }
        }
    }

    private static final class Paar {
        private final int index1;
        private final int index2;

        private Paar(int index1, int index2) {
            this.index1 = Math.min(index1, index2);
            this.index2 = Math.max(index1, index2);
        }

        @Override
        public boolean equals(Object o) {
            return !(o == null || getClass() != o.getClass()) && index1 == ((Paar) o).index1 && index2 == ((Paar) o).index2;
        }

        @Override
        public int hashCode() {
            return 31 * index1 + index2;
        }
    }
}
