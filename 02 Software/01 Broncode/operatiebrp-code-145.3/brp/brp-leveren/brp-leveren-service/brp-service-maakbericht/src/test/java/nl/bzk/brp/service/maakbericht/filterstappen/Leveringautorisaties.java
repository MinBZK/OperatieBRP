/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.algemeen.TestPartijRolBuilder;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;


/**
 */
public class Leveringautorisaties {

    private static final Set<Integer> NIET_TE_AUTORISEREN_ATTRIBUTEN = Sets.newHashSet(
            Element.FAMILIERECHTELIJKEBETREKKING_SOORTCODE.getId(),
            Element.GEREGISTREERDPARTNERSCHAP_SOORTCODE.getId(),
            Element.HUWELIJK_SOORTCODE.getId(),
            Element.HUWELIJKGEREGISTREERDPARTNERSCHAP_SOORTCODE.getId()
    );


    public static Autorisatiebundel metVolledigeAutorisatie(final AutorisatieSettings autorisatieSettings, final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        maakDienstbundelMetVolledigeAutorisatie(autorisatieSettings, dienstbundel);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        final Partij maak = TestPartijBuilder.maakBuilder().metCode("000123").build();
        final PartijRol partijRol = TestPartijRolBuilder.maker()
                .metPartij(maak)
                .metRol(autorisatieSettings.rol)
                .maak();
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

    private static Set<DienstbundelGroep> maakDienstbundelMetVolledigeAutorisatie(final AutorisatieSettings settings, final Dienstbundel dienstbundel) {
        final Set<DienstbundelGroep> dienstbundelGroepen = new HashSet<>();

        for (final GroepElement groepElement : ElementHelper.getGroepen()) {
            final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(dienstbundel, Element.parseId(groepElement.getId()),
                    false, false, false);
            dienstbundelGroepen.add(dienstbundelGroep);
            final Set<DienstbundelGroepAttribuut> dienstbundelGroepAttributen = new HashSet<>();
            dienstbundelGroep.setDienstbundelGroepAttribuutSet(dienstbundelGroepAttributen);
            if (settings.geautoriseerdeGroepen == GeenAlles.GEEN || settings.groepUitsluitsels.contains(groepElement)) {
                continue;
            }

            if (settings.geautoriseerdeAttributen == GeenAlles.ALLES) {
                for (final AttribuutElement attribuutElement : groepElement.getAttributenInGroep()) {
                    if (settings.attribuutUitsluitsels.contains(attribuutElement)) {
                        continue;
                    }

                    if (NIET_TE_AUTORISEREN_ATTRIBUTEN.contains(attribuutElement.getId())) {
                        continue;
                    }
                    if (attribuutElement.getAutorisatie() == SoortElementAutorisatie.VIA_GROEPSAUTORISATIE
                            || attribuutElement.getAutorisatie() == SoortElementAutorisatie.NIET_VERSTREKKEN
                            || attribuutElement.getAutorisatie() == SoortElementAutorisatie.STRUCTUUR) {
                        continue;
                    }

                    if (!attribuutElement.heeftExpressie()) {
                        continue;
                    }

                    final DienstbundelGroepAttribuut dienstbundelGroepAttribuut = new DienstbundelGroepAttribuut(dienstbundelGroep, Element.parseId
                            (attribuutElement.getId()));
                    dienstbundelGroepAttributen.add(dienstbundelGroepAttribuut);

                }
            }
        }
        return dienstbundelGroepen;
    }


    enum GeenAlles {
        GEEN,
        ALLES
    }

    public final static class AutorisatieSettings {

        private GeenAlles geautoriseerdeGroepen = GeenAlles.ALLES;
        private GeenAlles geautoriseerdeAttributen = GeenAlles.ALLES;

        private Set<AttribuutElement> attribuutUitsluitsels = Sets.newHashSet();
        private Set<GroepElement> groepUitsluitsels = Sets.newHashSet();

        private Rol rol = Rol.AFNEMER;

        public void alleGroepenMetUitzonderingVan(final GroepElement... elementen) {
            geautoriseerdeGroepen = GeenAlles.ALLES;
            groepUitsluitsels.addAll(Arrays.asList(elementen));
        }

        public void alleAttributenMetUitzonderingVan(final AttribuutElement... elementen) {
            geautoriseerdeAttributen = GeenAlles.ALLES;
            attribuutUitsluitsels.addAll(Arrays.asList(elementen));
        }

        public void autoriseerGeenGroepen() {
            geautoriseerdeGroepen = GeenAlles.GEEN;
        }

        public void autoriseerGeenAttributen() {
        }

        public void setRol(final Rol rol) {
            this.rol = rol;
        }
    }
}
