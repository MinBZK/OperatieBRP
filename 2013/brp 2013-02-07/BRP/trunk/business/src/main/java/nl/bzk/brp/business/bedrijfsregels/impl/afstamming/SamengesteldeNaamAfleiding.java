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

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.validatie.Melding;


/**
 * Afleidingsbedrijfsregel die de samengestelde naam groep van een nieuw geborene afleidt uit de voornamen en
 * geslachtsnaamcomponenten.
 */
public class SamengesteldeNaamAfleiding implements ActieBedrijfsRegel<RootObject> {

    @Override
    public String getCode() {
        // @TODO Bedrijfsregel code nog onbekend.
        return "SamengesteldeNaamAfleiding";
    }

    @Override
    public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie,
            final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        if (nieuweSituatie instanceof Persoon) {
            Melding melding = executeer((Persoon) nieuweSituatie);
            if (melding != null) {
                meldingen.add(melding);
            }
        } else if (nieuweSituatie instanceof FamilierechtelijkeBetrekkingBericht) {
            Melding melding = executeer((FamilierechtelijkeBetrekkingBericht) nieuweSituatie);
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
        JaNee indAlgoritmischAfgeleid = JaNee.NEE;
        if (persoon.getVoornamen() != null && !persoon.getVoornamen().isEmpty()) {
            samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
            indAlgoritmischAfgeleid = JaNee.JA;
            final StringBuilder voornaamAfgeleid = new StringBuilder();

            final TreeSet<PersoonVoornaam> gesorteerdeVoornamen =
                new TreeSet<PersoonVoornaam>(new Comparator<PersoonVoornaam>() {

                    @Override
                    public int compare(final PersoonVoornaam persVoornaam1, final PersoonVoornaam persVoornaam2) {
                        return persVoornaam1.getVolgnummer().getWaarde()
                                .compareTo(persVoornaam2.getVolgnummer().getWaarde());
                    }
                });

            // Sorteer de voornamen op volgnummer.
            for (PersoonVoornaam persoonVoornaam : persoon.getVoornamen()) {
                gesorteerdeVoornamen.add(persoonVoornaam);
            }

            for (PersoonVoornaam voornaam : gesorteerdeVoornamen) {
                voornaamAfgeleid.append(voornaam.getStandaard().getNaam().getWaarde());
                voornaamAfgeleid.append(" ");
            }
            // Laatste spatie moet eruit:
            samengesteldeNaam.setVoornamen(new Voornamen(voornaamAfgeleid.substring(0, voornaamAfgeleid.length() - 1)));
        }

        // Geslachtsnaam afleiden
        if (persoon.getGeslachtsnaamcomponenten() != null && !persoon.getGeslachtsnaamcomponenten().isEmpty()) {
            if (samengesteldeNaam == null) {
                samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
            }
            indAlgoritmischAfgeleid = JaNee.JA;
            final PersoonGeslachtsnaamcomponent persGeslachtsnaamcomponent =
                persoon.getGeslachtsnaamcomponenten().iterator().next();
            samengesteldeNaam.setAdellijkeTitel(persGeslachtsnaamcomponent.getStandaard().getAdellijkeTitel());
            samengesteldeNaam.setGeslachtsnaam(new Geslachtsnaam(persGeslachtsnaamcomponent.getStandaard().getNaam()
                    .getWaarde()));
            samengesteldeNaam.setPredikaat(persGeslachtsnaamcomponent.getStandaard().getPredikaat());
            samengesteldeNaam.setVoorvoegsel(persGeslachtsnaamcomponent.getStandaard().getVoorvoegsel());
            samengesteldeNaam.setScheidingsteken(persGeslachtsnaamcomponent.getStandaard().getScheidingsteken());
        }

        if (samengesteldeNaam != null) {
            samengesteldeNaam.setIndicatieAlgoritmischAfgeleid(indAlgoritmischAfgeleid);
            samengesteldeNaam.setIndicatieNamenreeks(JaNee.NEE);
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
    private Melding executeer(final FamilierechtelijkeBetrekkingBericht relatie) {
        Melding melding = null;
        if (relatie.getKindBetrokkenheid() != null) {
            melding = executeer(relatie.getKindBetrokkenheid().getPersoon());
        }
        return melding;
    }

}
