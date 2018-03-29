/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractMaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * Class  waarmee de tijdslijn van de materiele historie kan worden gecontroleerd. Deze class bouwt voor de verschillende groepen, met materiele historie, een
 * tijdslijn op zoals deze na bv correctie zou zijn. Vervolgens wordt er gecontorleerd of de tijdslijn klopt volgens de gestelde regels.
 */
public final class MaterieleTijdslijnHelper {

    private MaterieleTijdslijnHelper() {
        throw new AssertionError("Er mag geen instantie gemaakt worden van MaterieleTijdslijnHelper.");
    }

    static void controleerTijdslijn(final BijhoudingPersoon persoon, final AdministratieveHandelingElement administratieveHandelingElement,
                                    final List<MeldingElement> meldingen) {
        if (!administratieveHandelingElement.getSoort().isCorrectie()) {
            return;
        }

        // Alle gerelateerden langs lopen (voor nu alleen partners)
        controleerPartners(persoon.getActuelePartners(), administratieveHandelingElement, meldingen);
    }

    private static void controleerPartners(final Set<Betrokkenheid> actuelePartners, final AdministratieveHandelingElement administratieveHandelingElement,
                                           final List<MeldingElement> meldingen) {
        final List<MockPersoon> mockPartners = new ArrayList<>(actuelePartners.size());
        for (final Betrokkenheid partner : actuelePartners) {
            final MockPersoon mockPartner = MockPersoon.getInstance(partner.getPersoon());
            mockPartners.add(mockPartner);
            // Geslachtsaanduiding
            pasVervalActieToe(mockPartner, mockPartner.geslachtsaanduidingHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEVERVAL_GESLACHTSAANDUIDING_GERELATEERDE));
            pasRegistratieActieToe(mockPartner, mockPartner.geslachtsaanduidingHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEREGISTRATIE_GESLACHTSAANDUIDING_GERELATEERDE));
            // Identificatienummers
            pasVervalActieToe(mockPartner, mockPartner.idHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEVERVAL_IDENTIFICATIENUMMERS_GERELATEERDE));
            pasRegistratieActieToe(mockPartner, mockPartner.idHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEREGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE));
            // Samengestelde naam
            pasVervalActieToe(mockPartner, mockPartner.samengesteldenaamHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEVERVAL_SAMENGESTELDE_NAAM_GERELATEERDE));
            pasRegistratieActieToe(mockPartner, mockPartner.samengesteldenaamHistorie, administratieveHandelingElement.getActiesBySoort(
                    SoortActie.CORRECTIEREGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE));
        }

        mockPartners.stream().filter(mockPartner -> mockPartner.geschiedenisAangepast)
                .forEach(mockPartner -> controleerTijdslijnPartner(mockPartner, administratieveHandelingElement, meldingen));
    }

    private static void controleerTijdslijnPartner(final MockPersoon partner, final AdministratieveHandelingElement administratieveHandelingElement,
                                                   final List<MeldingElement> meldingen) {
        controleerTijdslijnGroep(partner.geslachtsaanduidingHistorie, administratieveHandelingElement, meldingen);
        controleerTijdslijnGroep(partner.idHistorie, administratieveHandelingElement, meldingen);
        controleerTijdslijnGroep(partner.samengesteldenaamHistorie, administratieveHandelingElement, meldingen);
    }

    @Bedrijfsregel(Regel.R2530)
    @Bedrijfsregel(Regel.R2675)
    @Bedrijfsregel(Regel.R2691)
    private static void controleerTijdslijnGroep(final List<MockMaterieleHistorie> histories,
                                                 final AdministratieveHandelingElement administratieveHandelingElement,
                                                 final List<MeldingElement> meldingen) {
        if (histories.isEmpty()) {
            return;
        }

        // Filter de historie op vervallen records en records met datumAanvang die (deels)  onbekend is.
        final List<MockMaterieleHistorie> gefilterdeHistorie =
                histories.stream().filter(historie -> !historie.isVervallen)
                        .filter(historie -> DatumUtil.isGeldigeKalenderdatum(historie.getDatumAanvangGeldigheid()))
                        .collect(Collectors.toList());

        final Iterator<MockMaterieleHistorie> iterator = gefilterdeHistorie.iterator();
        MockMaterieleHistorie huidigeRij = iterator.next();
        MockMaterieleHistorie volgendeRij = getVolgendeRij(iterator);
        while (volgendeRij != null) {
            final Integer datumAanvang = volgendeRij.getDatumAanvangGeldigheid();
            final Integer datumEinde = huidigeRij.getDatumEindeGeldigheid();
            if (huidigeRij.moetHistorieAaneengeslotenZijn()) {
                if (!Objects.equals(datumAanvang, datumEinde)) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2530, administratieveHandelingElement));
                }
            } else {
                if (datumEinde == null || datumAanvang < datumEinde) {
                    meldingen.add(MeldingElement.getInstance(Regel.R2675, administratieveHandelingElement));
                }
            }
            huidigeRij = volgendeRij;
            volgendeRij = getVolgendeRij(iterator);
        }

        // Huidige rij is de laatste in de historie. Er hoeft niet gecontroleerd te worden of de huidige rij vervallen is aangezien vervallen rijen er eerder
        // zijn uitgefilterd.
        if (huidigeRij.moetHistorieAaneengeslotenZijn && huidigeRij.getDatumEindeGeldigheid() != null) {
            meldingen.add(MeldingElement.getInstance(Regel.R2691, administratieveHandelingElement));
        }
    }

    private static MockMaterieleHistorie getVolgendeRij(final Iterator<MockMaterieleHistorie> iterator) {
        return iterator.hasNext() ? iterator.next() : null;
    }

    private static void pasVervalActieToe(final MockPersoon persoon, final List<MockMaterieleHistorie> bestaandeHistorie, final List<ActieElement> acties) {
        persoon.setGeschiedenisAangepast(!acties.isEmpty());
        for (final ActieElement actie : acties) {
            final FormeleHistorie teVervallenVoorkomen = ((AbstractCorrectieVervalActieElement) actie).bepaalTeVervallenVoorkomen();
            final Number id = teVervallenVoorkomen == null ? null : teVervallenVoorkomen.getId();
            for (final MockMaterieleHistorie historie : bestaandeHistorie) {
                if (id != null && Objects.equals(historie.getId(), id)) {
                    historie.isVervallen = true;
                    break;
                }
            }
        }
    }

    private static void pasRegistratieActieToe(final MockPersoon persoon, final List<MockMaterieleHistorie> bestaandeHistorie,
                                               final List<ActieElement> acties) {
        persoon.setGeschiedenisAangepast(!acties.isEmpty());
        for (final ActieElement actie : acties) {
            final MaterieleHistorie nieuweHistorie = (MaterieleHistorie) ((AbstractCorrectieRegistratieActieElement) actie).maakNieuwVoorkomen();
            final MockMaterieleHistorie historie = new MockMaterieleHistorie();
            historie.isDegGelijkAanDagToegestaan = nieuweHistorie.isDegGelijkAanDagToegestaan();
            historie.moetHistorieAaneengeslotenZijn = nieuweHistorie.moetHistorieAaneengeslotenZijn();
            historie.setDatumAanvangGeldigheid(actie.getDatumAanvangGeldigheid().getWaarde());
            historie.setDatumEindeGeldigheid(BmrAttribuut.getWaardeOfNull(actie.getDatumEindeGeldigheid()));
            bestaandeHistorie.add(historie);
        }
    }

    private static class MockMaterieleHistorie extends AbstractMaterieleHistorie {
        private static final long serialVersionUID = 1L;
        private Number id;
        private boolean isDegGelijkAanDagToegestaan;
        private boolean moetHistorieAaneengeslotenZijn;
        private boolean isVervallen;


        static MockMaterieleHistorie maakKopie(final MaterieleHistorie origineel) {
            final MockMaterieleHistorie kopie = new MockMaterieleHistorie();
            kopie.id = origineel.getId();
            kopie.setDatumAanvangGeldigheid(origineel.getDatumAanvangGeldigheid());
            kopie.setDatumEindeGeldigheid(origineel.getDatumEindeGeldigheid());
            kopie.isDegGelijkAanDagToegestaan = origineel.isDegGelijkAanDagToegestaan();
            kopie.moetHistorieAaneengeslotenZijn = origineel.moetHistorieAaneengeslotenZijn();
            kopie.isVervallen = origineel.isVervallen();
            return kopie;
        }

        @Override
        public <T extends MaterieleHistorie> T kopieer() {
            return null;
        }

        @Override
        public Persoon getPersoon() {
            return null;
        }

        @Override
        public boolean isDegGelijkAanDagToegestaan() {
            return isDegGelijkAanDagToegestaan;
        }

        @Override
        public boolean moetHistorieAaneengeslotenZijn() {
            return moetHistorieAaneengeslotenZijn;
        }

        @Override
        public Number getId() {
            return id;
        }
    }

    private static class MockPersoon {
        private boolean geschiedenisAangepast;
        private List<MockMaterieleHistorie> geslachtsaanduidingHistorie = new ArrayList<>();
        private List<MockMaterieleHistorie> idHistorie = new ArrayList<>();
        private List<MockMaterieleHistorie> samengesteldenaamHistorie = new ArrayList<>();

        static MockPersoon getInstance(final Persoon origineel) {
            final MockPersoon kopie = new MockPersoon();

            for (final MaterieleHistorie historie : origineel.getPersoonGeslachtsaanduidingHistorieSet()) {
                kopie.geslachtsaanduidingHistorie.add(MockMaterieleHistorie.maakKopie(historie));
            }

            for (final MaterieleHistorie historie : origineel.getPersoonIDHistorieSet()) {
                kopie.idHistorie.add(MockMaterieleHistorie.maakKopie(historie));
            }

            for (final MaterieleHistorie historie : origineel.getPersoonSamengesteldeNaamHistorieSet()) {
                kopie.samengesteldenaamHistorie.add(MockMaterieleHistorie.maakKopie(historie));
            }

            return kopie;
        }

            void setGeschiedenisAangepast(final boolean aangepast){
            if (!geschiedenisAangepast) {
                geschiedenisAangepast = aangepast;
            }
        }
    }
}
