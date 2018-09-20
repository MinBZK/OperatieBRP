/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.Collections;
import java.util.Comparator;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonSamengesteldeNaam;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Afleidingsbedrijfsregel die de samengestelde naam groep van een nieuw geborene afleidt uit de voornamen en
 * geslachtsnaamcomponenten.
 */
public class SamengesteldeNaamAfleiding implements BedrijfsRegel<PersistentRootObject, RootObject> {

    @Override
    public String getCode() {
        // @TODO Bedrijfsregel code nog onbekend.
        return "SamengesteldeNaamAfleiding";
    }

    @Override
    public Melding executeer(final PersistentRootObject huidigeSituatie, final RootObject nieuweSituatie,
        final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;
        if (nieuweSituatie instanceof Persoon) {
            melding = executeer((Persoon) nieuweSituatie);
        } else if (nieuweSituatie instanceof Relatie) {
            melding = executeer((Relatie) nieuweSituatie);
        }
        return melding;
    }

    /**
     * Voert de werkelijke afleiding uit van de samen gestelde naam voor de opgegeven {@link Persoon} instantie.
     *
     * @param persoon de persoon waarvoor de afleiding geldt.
     * @return een eventuele foutmelding indien de afleiding niet kan worden uitgevoerd.
     */
    private Melding executeer(final Persoon persoon) {
        PersoonSamengesteldeNaam samengesteldeNaam = null;
        boolean indAlgoritmischAfgeleid = false;
        if (persoon.getPersoonVoornamen() != null && !persoon.getPersoonVoornamen().isEmpty()) {
            samengesteldeNaam = new PersoonSamengesteldeNaam();
            indAlgoritmischAfgeleid = true;
            final StringBuilder voornaamAfgeleid = new StringBuilder();
            // Sorteer de voornamen op volgnummer.
            Collections.sort(persoon.getPersoonVoornamen(), new Comparator<PersoonVoornaam>() {

                @Override
                public int compare(final PersoonVoornaam persVoornaam1, final PersoonVoornaam persVoornaam2) {
                    return persVoornaam1.getVolgnummer().compareTo(persVoornaam2.getVolgnummer());
                }
            });

            for (PersoonVoornaam voornaam : persoon.getPersoonVoornamen()) {
                voornaamAfgeleid.append(voornaam.getNaam());
                voornaamAfgeleid.append(" ");
            }
            // Laatste spatie moet eruit:
            samengesteldeNaam.setVoornamen(voornaamAfgeleid.substring(0, voornaamAfgeleid.length() - 1));
        }

        // Geslachtsnaam afleiden
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            if (samengesteldeNaam == null) {
                samengesteldeNaam = new PersoonSamengesteldeNaam();
            }
            indAlgoritmischAfgeleid = true;
            final PersoonGeslachtsnaamcomponent persGeslNaamComp = persoon.getGeslachtsnaamcomponenten().get(0);
            samengesteldeNaam.setAdellijkeTitel(persGeslNaamComp.getAdellijkeTitel());
            samengesteldeNaam.setGeslachtsnaam(persGeslNaamComp.getNaam());
            samengesteldeNaam.setPredikaat(persGeslNaamComp.getPredikaat());
            samengesteldeNaam.setVoorvoegsel(persGeslNaamComp.getVoorvoegsel());
            samengesteldeNaam.setScheidingsTeken(persGeslNaamComp.getScheidingsTeken());
        }

        if (samengesteldeNaam != null) {
            samengesteldeNaam.setIndAlgoritmischAfgeleid(indAlgoritmischAfgeleid);
            samengesteldeNaam.setIndNamenreeksAlsGeslachtsnaam(false);
        }
        persoon.setSamengesteldenaam(samengesteldeNaam);
        return null;
    }

    /**
     * Voert de werkelijke afleiding uit op een eventueel aanwezig kind in de opgegeven relatie.
     *
     * @param relatie de relatie die mogelijk een kind bevat waarvoor dan de samengestelde naam wordt afgeleid.
     * @return een eventuele foutmelding indien de afleiding niet kan worden uitgevoerd.
     */
    private Melding executeer(final Relatie relatie) {
        Melding melding = null;
        if (relatie.getKindBetrokkenheid() != null) {
            melding = executeer(relatie.getKindBetrokkenheid().getBetrokkene());
        }
        return melding;
    }

}
