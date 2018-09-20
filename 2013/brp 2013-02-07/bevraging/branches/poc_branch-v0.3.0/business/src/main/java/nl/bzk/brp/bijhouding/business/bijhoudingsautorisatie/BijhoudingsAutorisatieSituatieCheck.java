/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;

import nl.bzk.brp.bijhouding.domein.SoortActie;
import nl.bzk.brp.bijhouding.domein.SoortDocument;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsAutorisatie;
import nl.bzk.brp.bijhouding.domein.aut.BijhoudingsSituatie;

/**
 * Een bijhoudingsautorisatie kan enkel gelden voor een of meerdere {@link BijhoudingsSituatie} waarbij voor de soort/
 * categorie actie van de bijhouding een bepaald document/categorie document gebruikt wordt.
 *
 * De input voor deze klasse is een {@link SoortActie} en {@link SoortDocument}, bijvoorbeeld huwelijksregistratie en
 * Nederlandse huwelijks akte respectievelijk. De {@link #apply(BijhoudingsAutorisatie)} functie controleert of er een
 * bijhoudingssituatie van toepassing is op de bijhoudingsautorisatie, indien dat het geval is dan wordt gecontroleerd
 * of de bijhouding met {@link #bijhoudingsSoortActie} en {@link #bijhoudingsSoortDocument} is toegestaan.
 *
 */
public final class BijhoudingsAutorisatieSituatieCheck implements Predicate<BijhoudingsAutorisatie> {

    private SoortActie    bijhoudingsSoortActie;
    private SoortDocument bijhoudingsSoortDocument;

    /**
     * Constructor.
     * Let op. Het kan zijn dat een bijhouding wordt gedaan zonder een soort document, terwijl dat wel vereist wordt
     * door een bijhoudingssituatie die bij de autorisatie hoort. In dat geval is soortDocument null.
     *
     * @param soortActie De soort actie van de bijhouding. Deze mag niet leeg (null) zijn.
     * @param soortDocument Het soort document dat gebruikt wordt voor de bijhouding.
     */
    public BijhoudingsAutorisatieSituatieCheck(final SoortActie soortActie,
            @Nullable final SoortDocument soortDocument)
    {
        // bijhoudingsSoortActie MOET gevuld zijn om bijhoudingsSituaties te kunnen evalueren.
        Preconditions.checkArgument(soortActie != null, "BijhoudingsSoortActie is null, mag niet leeg zijn.");
        this.bijhoudingsSoortActie = soortActie;
        this.bijhoudingsSoortDocument = soortDocument;
    }

    /**
     * Bepaal of er voor de bijhoudingsautorisatie een bijhoudingssituatie van toepassing is.
     * Indien ja, check of de situatie overeenkomt met de bijhouding die gedaan wordt. Hiervoor is de soort actie en
     * soort document van de bijhouding nodig. Deze zijn via de constructor doorgegeven.
     *
     * @param bijhoudingsAutorisatie De bijhoudings autorisatie die mogelijk een bijhoudingssituatie kent.
     * @return true als er geen bijhoudingssituatie van toepassing is op de bijhoudingsautorisatie (dus het mag!),
     * of als er wel een bijhoudingssituatie van toepassing is en de {@link #bijhoudingsSoortActie} en
     * {@link #bijhoudingsSoortDocument} voldoen aan die situatie. In alle andere gevallen false.
     */
    @Override
    public boolean apply(final BijhoudingsAutorisatie bijhoudingsAutorisatie) {
        // Als er voor de autorisatie geen bijhoudings situatie geldt dan is er ook geen beperking.
        boolean resultaat = false;
        if (bijhoudingsAutorisatie.getBijhoudingsSituaties() == null
            || bijhoudingsAutorisatie.getBijhoudingsSituaties().isEmpty())
        {
            resultaat = true;
        } else {
            // Evalueer elk bijhoudings situatie:
            for (BijhoudingsSituatie bijhoudingsSituatie : bijhoudingsAutorisatie.getBijhoudingsSituaties()) {
                // Match de actie.
                boolean actieIsVanToepassing = false;
                if (bijhoudingsSituatie.getSoortActie() != null) {
                    actieIsVanToepassing =
                        bijhoudingsSoortActie.getId().equals(bijhoudingsSituatie.getSoortActie().getId());
                } else if (bijhoudingsSituatie.getCategorieSrtActie() != null) {
                    actieIsVanToepassing =
                        bijhoudingsSoortActie.getCategorieActie().equals(bijhoudingsSituatie.getCategorieSrtActie());
                }

                // De actie is van toepassing, wordt het juiste document gebruikt?
                boolean juisteDocumentGebruikt = false;
                if (actieIsVanToepassing) {
                    // Match het document.
                    if (bijhoudingsSituatie.getSoortDocument() != null) {
                        juisteDocumentGebruikt =
                            bijhoudingsSoortDocument != null
                                && bijhoudingsSoortDocument.getId().equals(
                                        bijhoudingsSituatie.getSoortDocument().getId());
                    } else if (bijhoudingsSituatie.getCategorieSoortDocument() != null) {
                        juisteDocumentGebruikt =
                            bijhoudingsSoortDocument != null
                                && bijhoudingsSoortDocument.getCategorieSoortDocument().equals(
                                        bijhoudingsSituatie.getCategorieSoortDocument());
                    }
                }

                if (actieIsVanToepassing && juisteDocumentGebruikt) {
                    resultaat = true;
                }
            }
        }
        return resultaat;
    }
}
