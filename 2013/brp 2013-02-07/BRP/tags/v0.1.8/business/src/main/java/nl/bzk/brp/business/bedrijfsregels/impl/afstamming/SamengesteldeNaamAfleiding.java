/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.bericht.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Afleidingsbedrijfsregel die de samengestelde naam groep van een nieuw geborene afleidt uit de voornamen en
 * geslachtsnaamcomponenten.
 */
public class SamengesteldeNaamAfleiding implements BedrijfsRegel<RootObject> {

    @Override
    public String getCode() {
        // @TODO Bedrijfsregel code nog onbekend.
        return "SamengesteldeNaamAfleiding";
    }

    @Override
    public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        if (nieuweSituatie instanceof Persoon) {
            Melding melding = executeer((Persoon) nieuweSituatie);
            if (melding != null) {
                meldingen.add(melding);
            }
        } else if (nieuweSituatie instanceof Relatie) {
            Melding melding = executeer((Relatie) nieuweSituatie);
            if (melding != null) {
                meldingen.add(melding);
            }
        }
        return meldingen;
    }

    /**
     * Voert de werkelijke afleiding uit van de samen gestelde naam voor de opgegeven {@link Persoon} instantie.
     *
     * @param persoon de persoon waarvoor de afleiding geldt.
     * @return een eventuele foutmelding indien de afleiding niet kan worden uitgevoerd.
     */
    private Melding executeer(final Persoon persoon) {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = null;
        JaNee indAlgoritmischAfgeleid = JaNee.Nee;
        if (persoon.getPersoonVoornaam() != null && !persoon.getPersoonVoornaam().isEmpty()) {
            samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
            indAlgoritmischAfgeleid = JaNee.Ja;
            final StringBuilder voornaamAfgeleid = new StringBuilder();

            final TreeSet<PersoonVoornaam> gesorteerdeVoornamen = new TreeSet<PersoonVoornaam>(
                new Comparator<PersoonVoornaam>() {
                    @Override
                    public int compare(final PersoonVoornaam persVoornaam1, final PersoonVoornaam persVoornaam2) {
                        return persVoornaam1.getVolgnummer().getWaarde().
                            compareTo(persVoornaam2.getVolgnummer().getWaarde());
                    }
                });

            // Sorteer de voornamen op volgnummer.
            for (PersoonVoornaam persoonVoornaam : persoon.getPersoonVoornaam()) {
                gesorteerdeVoornamen.add(persoonVoornaam);
            }

            for (PersoonVoornaam voornaam : gesorteerdeVoornamen) {
                voornaamAfgeleid.append(voornaam.getGegevens().getVoornaam().getWaarde());
                voornaamAfgeleid.append(" ");
            }
            // Laatste spatie moet eruit:
            samengesteldeNaam.setVoornamen(new Voornaam(voornaamAfgeleid.substring(0, voornaamAfgeleid.length() - 1)));
        }

        // Geslachtsnaam afleiden
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            if (samengesteldeNaam == null) {
                samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
            }
            indAlgoritmischAfgeleid = JaNee.Ja;
            final PersoonGeslachtsnaamcomponent persGeslachtsnaamcomponent =
                persoon.getGeslachtsnaamcomponenten().iterator().next();
            samengesteldeNaam.setAdellijkeTitel(persGeslachtsnaamcomponent.getGegevens().getAdellijkeTitel());
            samengesteldeNaam.setGeslachtsnaam(persGeslachtsnaamcomponent.getGegevens().getNaam());
            samengesteldeNaam.setPredikaat(persGeslachtsnaamcomponent.getGegevens().getPredikaat());
            samengesteldeNaam.setVoorvoegsel(persGeslachtsnaamcomponent.getGegevens().getVoorvoegsel());
            samengesteldeNaam.setScheidingsteken(persGeslachtsnaamcomponent.getGegevens().getScheidingsteken());
        }

        if (samengesteldeNaam != null) {
            samengesteldeNaam.setIndAlgorithmischAfgeleid(indAlgoritmischAfgeleid);
            samengesteldeNaam.setIndNamenreeksAlsGeslachtsNaam(JaNee.Nee);
        }
        ((PersoonBericht) persoon).setSamengesteldeNaam(samengesteldeNaam);
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
