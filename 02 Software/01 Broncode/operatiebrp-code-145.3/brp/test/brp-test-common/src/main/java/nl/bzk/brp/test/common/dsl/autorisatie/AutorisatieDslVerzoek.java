/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.DslSectieParser;
import nl.bzk.brp.test.common.dsl.Ref;
import org.springframework.core.io.Resource;

/**
 * Databaseverzoek voor het invoeren van de autorisatie.
 */
public class AutorisatieDslVerzoek implements Consumer<EntityManager> {

    private final String afleverpunt;
    private final List<Resource> bestandList;

    private List<Ref<Dienst>> dienstResult;
    private List<Ref<ToegangLeveringsAutorisatie>> toegangResult;

    /**
     * Constructor.
     * @param bestandList een dsl autorisatie
     * @param afleverpunt een afleverpunt
     */
    public AutorisatieDslVerzoek(final Resource bestandList, final String afleverpunt) {
        this(Collections.singletonList(bestandList), afleverpunt);
    }

    public AutorisatieDslVerzoek(final List<Resource> bestanden, final String afleverpunt) {
        this.bestandList = bestanden;
        this.afleverpunt = afleverpunt;
    }

    @Override
    public void accept(final EntityManager entityManager) {
        final DienstbundelGroepParser dienstbundelGroepParser = new DienstbundelGroepParser();
        final DienstbundelGroepAttribuutParser dienstbundelGroepAttribuutParser = new DienstbundelGroepAttribuutParser();

        final Lo3RubriekParser lo3RubriekParser = new Lo3RubriekParser(rubriekStringList -> {
            final TypedQuery<Lo3Rubriek> query = entityManager.createQuery("SELECT l FROM Lo3Rubriek l", Lo3Rubriek.class);
            final List<Lo3Rubriek> lo3RubriekList = query.getResultList();
            return rubriekStringList.contains("*")
                    ? lo3RubriekList :
                    lo3RubriekList.stream().filter(r -> rubriekStringList.contains(r.getNaam())).collect(Collectors.toList());
        });

        final DienstParser dienstParser = new DienstParser();
        final DienstbundelParser dienstbundelParser = new DienstbundelParser(dienstbundelGroepParser,
                dienstbundelGroepAttribuutParser, lo3RubriekParser, dienstParser);
        final LeveringautorisatieParser leveringautorisatieParser = new LeveringautorisatieParser(dienstbundelParser);

        final Function<String, Partij> partijResolver = partijNaam -> {
            final TypedQuery<Partij> query = entityManager.
                    createQuery(String.format("select p from Partij p where p.naam = '%s'", partijNaam), Partij.class);
            return query.getSingleResult();
        };
        final ToegangLeveringautorisatieParser toegangLeveringautorisatieParser = new ToegangLeveringautorisatieParser(partijResolver, afleverpunt);

        for (Resource resource : bestandList) {

            final List<DslSectie> list = DslSectieParser.parse(resource);
            Deque<DslSectie> secties = new LinkedList<>(list);

            //er is hoogstens 1 leveringsautorisatie per bestand
            final Leveringsautorisatie leveringsautorisatie = getLeveringsautorisatie(list, leveringautorisatieParser);
            for (DslSectie dslSectie : list) {
                if (ToegangLeveringautorisatieParser.SECTIE_TOEGANG_LEVERINGAUTORISATIE.equals(dslSectie.getSectieNaam())) {
                    toegangLeveringautorisatieParser.parse(secties, leveringsautorisatie);
                }
            }
        }

        toegangLeveringautorisatieParser.getResult().stream().map(Ref::getValue).forEach(entityManager::persist);
        entityManager.flush();

        this.dienstResult = dienstParser.getResult();
        this.toegangResult = toegangLeveringautorisatieParser.getResult();


    }

    private Leveringsautorisatie getLeveringsautorisatie(final List<DslSectie> list, final LeveringautorisatieParser leveringautorisatieParser) {
        final ListIterator<DslSectie> sectieIterator = list.listIterator();
        Leveringsautorisatie leveringsautorisatie = null;
        while (sectieIterator.hasNext()) {
            final DslSectie next = sectieIterator.next();
            if (!ToegangLeveringautorisatieParser.SECTIE_TOEGANG_LEVERINGAUTORISATIE.equals(next.getSectieNaam())) {
                sectieIterator.previous();
                leveringsautorisatie = leveringautorisatieParser.parse(sectieIterator);
                break;
            }
        }
        return leveringsautorisatie;
    }

    public List<ToegangLeveringsAutorisatie> getToegangen() {
        return toegangResult.stream().map(Ref::getValue).collect(Collectors.toList());
    }

    public Map<String, ToegangLeveringsAutorisatie> getToegangRefs() {
        final HashMap<String, ToegangLeveringsAutorisatie> map = Maps.newHashMap();
        for (Ref<ToegangLeveringsAutorisatie> ref : toegangResult) {
            if (ref.getRef() != null) {
                if (map.containsKey(ref.getRef())) {
                    throw new IllegalStateException("Ref bestaat al voor toegangleveringsautorisatie: " + ref.getRef());
                }
                map.put(ref.getRef(), ref.getValue());
            }
        }
        return map;
    }

    public Map<String, Dienst> getDienstRefs() {
        final HashMap<String, Dienst> map = Maps.newHashMap();
        for (Ref<Dienst> ref : dienstResult) {
            if (ref.getRef() != null) {
                if (map.containsKey(ref.getRef())) {
                    throw new IllegalStateException("Ref bestaat al voor dienst: " + ref.getRef());
                }
                map.put(ref.getRef(), ref.getValue());
            }
        }
        return map;
    }
}
