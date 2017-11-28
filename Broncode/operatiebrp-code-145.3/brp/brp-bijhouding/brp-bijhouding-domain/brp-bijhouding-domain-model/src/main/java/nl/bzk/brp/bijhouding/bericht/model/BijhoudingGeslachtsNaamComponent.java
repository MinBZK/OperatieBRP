/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;

/**
 * wrapper voor geslachtsnaamComponent en aanduiding geslacht.
 * inclusief helper acties voor het bepalen van verschillen tussen personen.
 */
public class BijhoudingGeslachtsNaamComponent {
    private Predicaat predicaat;
    private AdellijkeTitel adelijkeTitel;
    private String stam;
    private String voorvoegsel;
    private Character scheidingsTeken;
    private Geslachtsaanduiding geslachtsaanduiding;
    private boolean geslachtsComponentInBericht = false;
    private boolean aanduidingGeslachtInBericht = false;

    /**
     * constructor.
     * Eerst wordt gekeken of een van de persoonElementen behorende bij de bijhoudingsPersoon
     * een geslachtsnaamComponent dan wel een samengestelde naam bevat en daarnaast nog een aanduiding geslacht.
     * Zoniet dan zullen de actuele gegevens op peildatum uit de database worden gehaald.
     * @param bijhoudingPersoon bijhoudingsPersoon
     * @param peildatum datum voor bepalen actuele geldigheid
     */
    BijhoudingGeslachtsNaamComponent(final BijhoudingPersoon bijhoudingPersoon, final int peildatum) {
        for (final PersoonElement persoon : bijhoudingPersoon.getPersoonElementen()) {
            probeerGegevensUitBerichtTeHalen(persoon);
        }
        if (!geslachtsComponentInBericht) {
            bepaalActueleGegevens(bijhoudingPersoon, peildatum);
        }
        if (!aanduidingGeslachtInBericht) {
            final PersoonGeslachtsaanduidingHistorie actueleGeslachtsAanduiding = bijhoudingPersoon.getActueleGeslachtsAanduiding(peildatum);
            if (actueleGeslachtsAanduiding != null) {
                geslachtsaanduiding = actueleGeslachtsAanduiding.getGeslachtsaanduiding();
            }
        }
    }

    /**
     * De gegevens met betrekking tot de geslachtsnaam worden vanuit het voorkomen worden gezet.
     * @param voorkomen voorkomen
     */
    BijhoudingGeslachtsNaamComponent(final PersoonSamengesteldeNaamHistorie voorkomen) {
        if (voorkomen != null) {
            stam = voorkomen.getGeslachtsnaamstam();
            voorvoegsel = voorkomen.getVoorvoegsel();
            scheidingsTeken = voorkomen.getScheidingsteken();
            adelijkeTitel = voorkomen.getAdellijkeTitel();
            predicaat = voorkomen.getPredicaat();
        }
    }

    private void bepaalActueleGegevens(final BijhoudingPersoon bijhoudingPersoon, final int peildatum) {
        final PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie = bijhoudingPersoon.getActueleGeslachtsnaamComponent(peildatum);
        if (persoonGeslachtsnaamcomponentHistorie != null) {
            stam = persoonGeslachtsnaamcomponentHistorie.getStam();
            voorvoegsel = persoonGeslachtsnaamcomponentHistorie.getVoorvoegsel();
            scheidingsTeken = persoonGeslachtsnaamcomponentHistorie.getScheidingsteken();
            adelijkeTitel = persoonGeslachtsnaamcomponentHistorie.getAdellijkeTitel();
            predicaat = persoonGeslachtsnaamcomponentHistorie.getPredicaat();
        } else {
            final PersoonSamengesteldeNaamHistorie geldigVoorkomenOpPeildatum =
                    MaterieleHistorie.getGeldigVoorkomenOpPeildatum(bijhoudingPersoon.getPersoonSamengesteldeNaamHistorieSet(), peildatum);
            if (geldigVoorkomenOpPeildatum != null) {
                stam = geldigVoorkomenOpPeildatum.getGeslachtsnaamstam();
                voorvoegsel = geldigVoorkomenOpPeildatum.getVoorvoegsel();
                scheidingsTeken = geldigVoorkomenOpPeildatum.getScheidingsteken();
                adelijkeTitel = geldigVoorkomenOpPeildatum.getAdellijkeTitel();
                predicaat = geldigVoorkomenOpPeildatum.getPredicaat();
            }
        }
    }

    private void probeerGegevensUitBerichtTeHalen(final PersoonElement persoon) {
        if (!geslachtsComponentInBericht && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            stam = BmrAttribuut.getWaardeOfNull(persoon.getGeslachtsnaamcomponenten().get(0).getStam());
            voorvoegsel = BmrAttribuut.getWaardeOfNull(persoon.getGeslachtsnaamcomponenten().get(0).getVoorvoegsel());
            scheidingsTeken = BmrAttribuut.getWaardeOfNull(persoon.getGeslachtsnaamcomponenten().get(0).getScheidingsteken());
            adelijkeTitel = persoon.getGeslachtsnaamcomponenten().get(0).getAdellijkeTitel();
            predicaat = persoon.getGeslachtsnaamcomponenten().get(0).getPredicaat();
            geslachtsComponentInBericht = true;
        }
        if (!geslachtsComponentInBericht && persoon.getSamengesteldeNaam() != null) {
            stam = BmrAttribuut.getWaardeOfNull(persoon.getSamengesteldeNaam().getGeslachtsnaamstam());
            voorvoegsel = BmrAttribuut.getWaardeOfNull(persoon.getSamengesteldeNaam().getVoorvoegsel());
            scheidingsTeken = BmrAttribuut.getWaardeOfNull(persoon.getSamengesteldeNaam().getScheidingsteken());
            adelijkeTitel = persoon.getSamengesteldeNaam().getAdellijkeTitel();
            predicaat = persoon.getSamengesteldeNaam().getPredicaat();
            geslachtsComponentInBericht = true;
        }
        if (!aanduidingGeslachtInBericht && persoon.getGeslachtsaanduiding() != null) {
            aanduidingGeslachtInBericht = true;
            if (Geslachtsaanduiding.bestaatCode(BmrAttribuut.getWaardeOfNull(persoon.getGeslachtsaanduiding().getCode()))) {
                geslachtsaanduiding = Geslachtsaanduiding.parseCode(persoon.getGeslachtsaanduiding().getCode().getWaarde());
            }
        }
    }


    /**
     * vergelijkt stam, scheidingsteken en voorvoegsel van een andere {@link BijhoudingGeslachtsNaamComponent} met deze.
     * @param ander {@link BijhoudingGeslachtsNaamComponent}
     * @return true indien gelijk
     */
    boolean heeftZelfdeGeslachtsNaam(final BijhoudingGeslachtsNaamComponent ander) {
        return Objects.equals(stam, ander.getStam()) && Objects.equals(scheidingsTeken, ander.getScheidingsteken()) && Objects
                .equals(voorvoegsel, ander.getVoorvoegsel());
    }


    /**
     * geeft aan of persoon een man is/
     * @return true als man
     */
    boolean isMan() {
        return Objects.equals(Geslachtsaanduiding.MAN, geslachtsaanduiding);
    }

    /**
     * geeft aan pf predicaat of adelijke titel aanwezig is
     * @return true als 1 van deze aanwezig is
     */
    boolean heeftPredicaatOfAdelijkeTitel() {
        return adelijkeTitel != null || predicaat != null;
    }

    /**
     * geeft geslachtsnaamStam terug.
     * @return String stam
     */
    String getStam() {
        return stam;
    }

    /**
     * geeft voorvoegsel terug.
     * @return String
     */
    String getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * geeft scheidingsteken terug
     * @return Character
     */
    Character getScheidingsteken() {
        return scheidingsTeken;
    }
}

