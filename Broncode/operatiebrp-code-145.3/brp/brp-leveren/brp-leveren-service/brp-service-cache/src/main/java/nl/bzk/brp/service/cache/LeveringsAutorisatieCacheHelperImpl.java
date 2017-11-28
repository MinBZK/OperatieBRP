/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import org.springframework.stereotype.Component;


/**
 * LeveringsAutorisatieCacheHelperImpl.
 */
@Component
final class LeveringsAutorisatieCacheHelperImpl implements LeveringsAutorisatieCacheHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private LeveringsautorisatieRepository leveringsautorisatieRepository;

    private LeveringsAutorisatieCacheHelperImpl() {

    }

    @Override
    public List<ToegangLeveringsAutorisatie> ophalenAlleToegangLeveringsautorisaties(final PartijCacheImpl.Data partijData) {
        return maakToegangleveringsAutorisaties(partijData);
    }

    /**
     * Maak een lijst met toegangleveringsautorisaties.
     * @param partijData partijData
     * @return lijst met toegangleveringsautorisaties
     */
    private List<ToegangLeveringsAutorisatie> maakToegangleveringsAutorisaties(final PartijCacheImpl.Data partijData) {
        final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisatieLijst = new ArrayList<>();

        //haal alle data op
        final List<ToegangLeveringsAutorisatie> toegangLeveringsautorisatiesOpZonderAssociaties = leveringsautorisatieRepository
                .haalAlleToegangLeveringsautorisatiesOpZonderAssociaties();
        final List<Dienst> alleDiensten = leveringsautorisatieRepository.haalAlleDienstenOpZonderAssocaties();
        final List<Dienstbundel> alleDienstBundels = leveringsautorisatieRepository.haalAlleDienstbundelsOpZonderAssocaties();
        final List<DienstbundelGroep> alleDienstBundelgroepen = leveringsautorisatieRepository.haalAlleDienstbundelGroepenOpZonderAssocaties();
        final List<DienstbundelLo3Rubriek> alleDienstbundelLo3Rubrieken = leveringsautorisatieRepository.haalAlleDienstbundelLo3Rubrieken();
        final List<DienstbundelGroepAttribuut> alleDienstBundelgroepenAttributen = leveringsautorisatieRepository
                .haalAlleDienstbundelGroepAttributenOpZonderAssocaties();

        //maak de lookup mappen
        final SetMultimap<Integer, Dienst> dienstenBijDienstbundelMap = maakDienstMapping(alleDiensten);
        final SetMultimap<Integer, DienstbundelGroep> dienstbundelGroepenBijDienstbundelMap = maakDienstbundelgroepDienstbundelMapping(
                alleDienstBundelgroepen);
        final SetMultimap<Integer, DienstbundelLo3Rubriek> dienstbundelLo3RubriekenBijDienstbundelMap = maakDienstbundelLo3RubriekDienstbundelMapping(
                alleDienstbundelLo3Rubrieken);
        final SetMultimap<Integer, Dienstbundel> dienstbundelMap = maakLeveringautorisatieDienstbundelMapping(alleDienstBundels);
        final Map<Integer, Dienstbundel> dienstbundelIdMap = new HashMap<>(alleDienstBundels.size(), 1);
        for (Dienstbundel dienstbundel : alleDienstBundels) {
            dienstbundelIdMap.put(dienstbundel.getId(), dienstbundel);
        }
        final Map<Integer, DienstbundelGroep> dienstbundelGroepIdMap = new HashMap<>(alleDienstBundelgroepen.size(), 1);
        for (DienstbundelGroep dienstbundelGroep : alleDienstBundelgroepen) {
            dienstbundelGroepIdMap.put(dienstbundelGroep.getId(), dienstbundelGroep);
        }
        final SetMultimap<Integer, DienstbundelGroepAttribuut> dbGrAttrBijDbGroep = maakDienstbundelgroepAttribuutMapping(
                alleDienstBundelgroepenAttributen);

        //zet de relaties
        final Map<Integer, Leveringsautorisatie> leveringsAutorisatieMap = maakLeveringsAutorisatieMap();
        toevoegenDienstbundelsAanLevAutorisaties(leveringsAutorisatieMap, dienstbundelMap);
        toevoegenDienstbundelsAanDiensten(alleDiensten, dienstbundelIdMap);
        toevoegenAttributenAanDienstbundelGroepen(alleDienstBundelgroepen, dienstbundelIdMap, dbGrAttrBijDbGroep);
        toevoegenDienstbundelsAanDienstbundelLo3Rubriek(alleDienstbundelLo3Rubrieken, dienstbundelIdMap);
        toevoegenSetsAanDienstbundels(alleDienstBundels, dienstbundelGroepenBijDienstbundelMap, dienstbundelLo3RubriekenBijDienstbundelMap,
                dienstenBijDienstbundelMap);
        toevoegenDienstbundelgroepAanDienstBundelGroepAttributen(alleDienstBundelgroepenAttributen, dienstbundelGroepIdMap);
        toevoegenToegangLeveringsAutorisaties(toegangLeveringsautorisatiesOpZonderAssociaties, leveringsAutorisatieMap, partijData,
                toegangLeveringsAutorisatieLijst);

        return toegangLeveringsAutorisatieLijst;
    }

    private void toevoegenToegangLeveringsAutorisaties(final List<ToegangLeveringsAutorisatie> toegangLeveringsautorisatiesOpZonderAssociaties,
                                                       final Map<Integer, Leveringsautorisatie> leveringsAutorisatieMap, final PartijCacheImpl.Data partijData,
                                                       final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisatieLijst) {
        for (final ToegangLeveringsAutorisatie tla : toegangLeveringsautorisatiesOpZonderAssociaties) {
            final Leveringsautorisatie leveringsautorisatie = leveringsAutorisatieMap.get(tla.getLeveringsautorisatie().getId());

            if (isInvalideToegangLeveringsAutorisatie(tla, leveringsautorisatie)) {
                continue;
            }

            if (tla.getOndertekenaar() != null && tla.getOndertekenaar().getId() != null) {
                final Partij ondertekenaar = partijData.getPartijIdMap().get(tla.getOndertekenaar().getId());
                tla.setOndertekenaar(ondertekenaar);
            }
            if (tla.getTransporteur() != null && tla.getTransporteur().getId() != null) {
                final Partij transporteur = partijData.getPartijIdMap().get(tla.getTransporteur().getId());
                tla.setTransporteur(transporteur);
            }

            final PartijRol geautoriseerde = partijData.getPartijRolMap().get(tla.getGeautoriseerde().getId());
            tla.setGeautoriseerde(geautoriseerde);
            tla.setLeveringsautorisatie(leveringsautorisatie);

            toegangLeveringsAutorisatieLijst.add(tla);
        }
    }

    private boolean isInvalideToegangLeveringsAutorisatie(final ToegangLeveringsAutorisatie toegangLevAut, final Leveringsautorisatie levAut) {
        final boolean geenLevAutAanwezig = levAut == null;
        final boolean geenAfleverpuntAanwezig = !geenLevAutAanwezig && levAut.getStelsel() == Stelsel.BRP && toegangLevAut.getAfleverpunt() == null;
        if (geenLevAutAanwezig) {
            LOGGER.warn("Discard Toegangleveringsautorisatie met id {}: geen leveringsautorisatie", toegangLevAut.getId());

        } else if (geenAfleverpuntAanwezig) {
            LOGGER.warn("Discard BRP Toegangleveringsautorisatie '{}': afleverpunt leeg", levAut.getNaam());
        }
        return geenLevAutAanwezig || geenAfleverpuntAanwezig;
    }

    private void toevoegenDienstbundelsAanLevAutorisaties(final Map<Integer, Leveringsautorisatie> leveringsAutorisatieMap,
                                                          final SetMultimap<Integer, Dienstbundel> dienstbundelMap) {
        for (Leveringsautorisatie leveringsautorisatie : leveringsAutorisatieMap.values()) {
            final Set<Dienstbundel> dienstbundelSet = dienstbundelMap.get(leveringsautorisatie.getId());
            if (dienstbundelSet != null) {
                leveringsautorisatie.setDienstbundelSet(dienstbundelSet);
            } else {
                leveringsautorisatie.setDienstbundelSet(Collections.emptySet());
            }
        }
    }

    private void toevoegenDienstbundelsAanDiensten(final List<Dienst> alleDiensten, final Map<Integer, Dienstbundel> dienstbundelIdMap) {
        for (final Dienst dienst : alleDiensten) {
            final Dienstbundel dienstbundel = dienstbundelIdMap.get(dienst.getDienstbundel().getId());
            if (dienstbundel != null) {
                dienst.setDienstbundel(dienstbundel);
            }
        }
    }

    private void toevoegenAttributenAanDienstbundelGroepen(final List<DienstbundelGroep> alleDienstBundelgroepen,
                                                           final Map<Integer, Dienstbundel> dienstbundelIdMap,
                                                           final SetMultimap<Integer, DienstbundelGroepAttribuut> dbGrAttrBijDbGroep) {
        for (final DienstbundelGroep dienstbundelGroep : alleDienstBundelgroepen) {
            final Dienstbundel dienstbundel = dienstbundelIdMap.get(dienstbundelGroep.getDienstbundel().getId());
            if (dienstbundel != null) {
                dienstbundelGroep.setDienstbundel(dienstbundel);
            }
            final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttribuutSet = dbGrAttrBijDbGroep.get(dienstbundelGroep.getId());
            if (dienstbundelGroepAttribuutSet != null) {
                dienstbundelGroep.setDienstbundelGroepAttribuutSet(dienstbundelGroepAttribuutSet);
            } else {
                dienstbundelGroep.setDienstbundelGroepAttribuutSet(Collections.emptySet());
            }
        }
    }

    private void toevoegenDienstbundelsAanDienstbundelLo3Rubriek(final List<DienstbundelLo3Rubriek> alleDienstbundelLo3Rubrieken,
                                                                 final Map<Integer, Dienstbundel> dienstbundelIdMap) {
        for (final DienstbundelLo3Rubriek dienstbundelLo3Rubriek : alleDienstbundelLo3Rubrieken) {
            final Dienstbundel dienstbundel = dienstbundelIdMap.get(dienstbundelLo3Rubriek.getDienstbundel().getId());
            if (dienstbundel != null) {
                dienstbundelLo3Rubriek.setDienstbundel(dienstbundel);
            }
        }
    }

    private void toevoegenSetsAanDienstbundels(final List<Dienstbundel> alleDienstBundels,
                                               final SetMultimap<Integer, DienstbundelGroep> dienstbundelGroepenBijDienstbundelMap,
                                               final SetMultimap<Integer, DienstbundelLo3Rubriek> dienstbundelLo3RubriekenBijDienstbundelMap,
                                               final SetMultimap<Integer, Dienst> dienstenBijDienstbundelMap) {
        for (final Dienstbundel dienstbundel : alleDienstBundels) {
            final Set<DienstbundelGroep> dienstbundelGroepSet = dienstbundelGroepenBijDienstbundelMap.get(dienstbundel.getId());
            final Set<DienstbundelLo3Rubriek> dienstbundelLo3RubriekSet = dienstbundelLo3RubriekenBijDienstbundelMap.get(dienstbundel.getId());
            if (dienstbundelGroepSet != null) {
                dienstbundel.setDienstbundelGroepSet(dienstbundelGroepSet);
                dienstbundel.setDienstbundelLo3RubriekSet(dienstbundelLo3RubriekSet);
                dienstbundel.setDienstSet(dienstenBijDienstbundelMap.get(dienstbundel.getId()));
            } else {
                dienstbundel.setDienstbundelGroepSet(Collections.emptySet());
                dienstbundel.setDienstSet(Collections.emptySet());
            }
        }
    }

    private void toevoegenDienstbundelgroepAanDienstBundelGroepAttributen(final List<DienstbundelGroepAttribuut> alleDienstBundelgroepenAttributen,
                                                                          final Map<Integer, DienstbundelGroep> dienstbundelGroepIdMap) {
        for (final DienstbundelGroepAttribuut dienstbundelGroepAttribuut : alleDienstBundelgroepenAttributen) {
            final DienstbundelGroep dienstbundelGroep = dienstbundelGroepIdMap.get(dienstbundelGroepAttribuut.getDienstbundelGroep().getId());
            if (dienstbundelGroep != null) {
                dienstbundelGroepAttribuut.setDienstbundelGroep(dienstbundelGroep);
            }
        }
    }

    private Map<Integer, Leveringsautorisatie> maakLeveringsAutorisatieMap() {
        final List<Leveringsautorisatie> leveringsAutorisaties = leveringsautorisatieRepository.haalAlleLeveringsautorisatiesOpZonderAssocaties();
        final Map<Integer, Leveringsautorisatie> map = new HashMap<>(leveringsAutorisaties.size(), 1);
        for (final Leveringsautorisatie leveringsAutorisatie : leveringsAutorisaties) {
            map.put(leveringsAutorisatie.getId(), leveringsAutorisatie);
        }
        return map;
    }

    //map van lev id -> dienstbundels
    private SetMultimap<Integer, Dienstbundel> maakLeveringautorisatieDienstbundelMapping(final List<Dienstbundel> alleDienstBundels) {
        final SetMultimap<Integer, Dienstbundel> map = HashMultimap.create();
        for (final Dienstbundel dienstbundel : alleDienstBundels) {
            map.put(dienstbundel.getLeveringsautorisatie().getId(), dienstbundel);
        }
        return map;
    }

    //map van dienstbundel id -> diensten
    private SetMultimap<Integer, Dienst> maakDienstMapping(final List<Dienst> alleDiensten) {
        final SetMultimap<Integer, Dienst> map = HashMultimap.create();
        for (final Dienst dienst : alleDiensten) {
            map.put(dienst.getDienstbundel().getId(), dienst);
        }
        return map;

    }

    //map van dienstbundel id -> dienstbundelgroepen
    private SetMultimap<Integer, DienstbundelGroep> maakDienstbundelgroepDienstbundelMapping(final List<DienstbundelGroep> alleDienstBundelgroepen) {
        final SetMultimap<Integer, DienstbundelGroep> map = HashMultimap.create();
        for (final DienstbundelGroep dienstbundelGroep : alleDienstBundelgroepen) {
            map.put(dienstbundelGroep.getDienstbundel().getId(), dienstbundelGroep);
        }
        return map;
    }

    //map van dienstbundel groep id -> dienstbundelgroepattributen
    private SetMultimap<Integer, DienstbundelGroepAttribuut> maakDienstbundelgroepAttribuutMapping(
            final List<DienstbundelGroepAttribuut> alleDienstBundelgroepenAttributen) {
        final SetMultimap<Integer, DienstbundelGroepAttribuut> map = HashMultimap.create();
        for (final DienstbundelGroepAttribuut dienstbundelGroepAttribuut : alleDienstBundelgroepenAttributen) {
            map.put(dienstbundelGroepAttribuut.getDienstbundelGroep().getId(), dienstbundelGroepAttribuut);
        }
        return map;
    }

    private SetMultimap<Integer, DienstbundelLo3Rubriek> maakDienstbundelLo3RubriekDienstbundelMapping(
            final List<DienstbundelLo3Rubriek> alleDienstbundelLo3Rubrieken) {
        final SetMultimap<Integer, DienstbundelLo3Rubriek> map = HashMultimap.create();
        for (final DienstbundelLo3Rubriek dienstbundelLo3Rubriek : alleDienstbundelLo3Rubrieken) {
            map.put(dienstbundelLo3Rubriek.getDienstbundel().getId(), dienstbundelLo3Rubriek);
        }
        return map;
    }
}
