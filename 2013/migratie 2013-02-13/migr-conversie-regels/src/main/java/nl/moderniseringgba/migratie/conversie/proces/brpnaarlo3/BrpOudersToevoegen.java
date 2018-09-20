/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Juridisch geen ouder wordt in BRP niet opgeslagen als relatie. Soms is dit herkenbaar doordat bijvoorbeeld een
 * 'onbekende ouder' vervalt, maar als enkel juridisch geen ouder aanwezig is dan resulteert dat niet in rijen voor LO3.
 * De relevante LO3 rij wordt door deze conversiestap toegevoegd.
 */
@Component
public class BrpOudersToevoegen {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Voeg, indien nodig, juridisch geen ouder toe.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.ouder1Stapels(toevoegenJuridischGeenOuder(persoonslijst.getOuder1Stapels(),
                persoonslijst.getPersoonStapel()));
        builder.ouder2Stapels(toevoegenJuridischGeenOuder(persoonslijst.getOuder2Stapels(),
                persoonslijst.getPersoonStapel()));

        return builder.build();
    }

    private List<Lo3Stapel<Lo3OuderInhoud>> toevoegenJuridischGeenOuder(
            final List<Lo3Stapel<Lo3OuderInhoud>> ouderStapels,
            final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        List<Lo3Stapel<Lo3OuderInhoud>> result = ouderStapels;
        if (ouderStapels == null || ouderStapels.isEmpty()) {
            // alse er geen ouder is, dan juridisch geen ouder toevoegen
            final Lo3OuderInhoud inhoud =
                    new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
            final Lo3Historie historie = persoonStapel.get(0).getHistorie();
            final Lo3Documentatie documentatie = persoonStapel.get(0).getDocumentatie();
            final Lo3Herkomst herkomst = persoonStapel.get(0).getLo3Herkomst();

            final Lo3Categorie<Lo3OuderInhoud> categorie =
                    new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, herkomst);

            final Lo3Stapel<Lo3OuderInhoud> stapel =
                    new Lo3Stapel<Lo3OuderInhoud>(Collections.singletonList(categorie));

            result = Collections.singletonList(stapel);
        } else if (heeftAanvangLaterDanGeboorte(ouderStapels, persoonStapel)) {
            // als de stapel niet begint op de geboortedatum, dan ook juridisch geen ouder toevoegen
            final Lo3OuderInhoud inhoud =
                    new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
            final Lo3Categorie<? extends Lo3CategorieInhoud> oudsteNietOnjuist = getOudsteNietOnjuist(persoonStapel);
            final Lo3Historie historie =
                    new Lo3Historie(null, ((Lo3PersoonInhoud) oudsteNietOnjuist.getInhoud()).getGeboortedatum(),
                            oudsteNietOnjuist.getHistorie().getDatumVanOpneming());
            final Lo3Documentatie documentatie = oudsteNietOnjuist.getDocumentatie();
            final Lo3Herkomst herkomst = oudsteNietOnjuist.getLo3Herkomst();

            final List<Lo3Categorie<Lo3OuderInhoud>> bestaandeCategorieen = ouderStapels.get(0).getCategorieen();
            final Lo3Categorie<Lo3OuderInhoud> categorie =
                    new Lo3Categorie<Lo3OuderInhoud>(inhoud, documentatie, historie, herkomst);
            bestaandeCategorieen.add(categorie);

            final Lo3Stapel<Lo3OuderInhoud> stapel =
                    new Lo3Stapel<Lo3OuderInhoud>(Collections.unmodifiableList(bestaandeCategorieen));

            result = Collections.singletonList(stapel);
        }

        return result;
    }

    private boolean heeftAanvangLaterDanGeboorte(
            final List<Lo3Stapel<Lo3OuderInhoud>> ouderStapels,
            final Lo3Stapel<Lo3PersoonInhoud> persoonStapel) {
        @SuppressWarnings("unchecked")
        final Lo3Categorie<Lo3PersoonInhoud> oudstePersoon =
                (Lo3Categorie<Lo3PersoonInhoud>) getOudsteNietOnjuist(persoonStapel);
        final int oudsteGeboorteDatum =
                oudstePersoon == null || oudstePersoon.getInhoud().getGeboortedatum() == null ? 0 : oudstePersoon
                        .getInhoud().getGeboortedatum().getDatum();
        final Lo3Categorie<? extends Lo3CategorieInhoud> oudsteOuder = getOudsteNietOnjuist(ouderStapels.get(0));
        final int oudsteIngang =
                oudsteOuder == null ? 0 : oudsteOuder.getHistorie().getIngangsdatumGeldigheid().getDatum();
        return oudsteIngang > oudsteGeboorteDatum;
    }

    private Lo3Categorie<? extends Lo3CategorieInhoud> getOudsteNietOnjuist(
            final Lo3Stapel<? extends Lo3CategorieInhoud> stapel) {
        int vorigeOudste = Integer.MAX_VALUE;
        Lo3Categorie<? extends Lo3CategorieInhoud> oudste = null;
        for (final Lo3Categorie<? extends Lo3CategorieInhoud> element : stapel) {
            if (!element.getHistorie().isOnjuist()
                    && element.getHistorie().getIngangsdatumGeldigheid().getDatum() < vorigeOudste) {
                oudste = element;
                vorigeOudste = element.getHistorie().getIngangsdatumGeldigheid().getDatum();
            }
        }
        return oudste;
    }

}
