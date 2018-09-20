/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieOnjuistEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Voeg meerdere ouder stapels samen tot 1 ouder stapel.
 */
@Component
public class BrpOudersSamenvoegen {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Voeg de lijst van ouder stapels samen tot 1 stapels voor beide ouder 1 en ouder 2 stapels.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return geconverteerde persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.ouder1Stapels(samenvoegenOuderStapels(persoonslijst.getOuder1Stapels()));
        builder.ouder2Stapels(samenvoegenOuderStapels(persoonslijst.getOuder2Stapels()));

        return builder.build();
    }

    private static List<Lo3Stapel<Lo3OuderInhoud>> samenvoegenOuderStapels(
            final List<Lo3Stapel<Lo3OuderInhoud>> ouderStapels) {
        final List<Lo3Categorie<Lo3OuderInhoud>> alleCategorieen = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        for (final Lo3Stapel<Lo3OuderInhoud> ouderStapel : ouderStapels) {
            alleCategorieen.addAll(ouderStapel.getCategorieen());
        }

        // Controleer dat er niet meerdere rijen zijn met dezelfde datum ingang en allemaal juist zijn.
        // Indien dat het geval is dient enkel de meest recente registreerde rij juist te blijven.
        Collections.sort(alleCategorieen, new OuderComparator());

        Lo3Datum ingang = null;
        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();
        for (final Lo3Categorie<Lo3OuderInhoud> cat : alleCategorieen) {
            if (ingang != null && ingang.equals(cat.getHistorie().getIngangsdatumGeldigheid())) {
                categorieen.add(maakOnjuist(cat));
            } else {
                categorieen.add(cat);
                ingang = cat.getHistorie().getIngangsdatumGeldigheid();
            }
        }

        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();
        if (!categorieen.isEmpty()) {
            result.add(new Lo3Stapel<Lo3OuderInhoud>(categorieen));
        }

        return result;
    }

    private static Lo3Categorie<Lo3OuderInhoud> maakOnjuist(final Lo3Categorie<Lo3OuderInhoud> cat) {
        return new Lo3Categorie<Lo3OuderInhoud>(cat.getInhoud(), cat.getDocumentatie(), new Lo3Historie(
                Lo3IndicatieOnjuistEnum.ONJUIST.asElement(), cat.getHistorie().getIngangsdatumGeldigheid(), cat
                        .getHistorie().getDatumVanOpneming()), cat.getLo3Herkomst());
    }

    /**
     * Sorteren; aflopend op datum ingang en daarbinnen aflopend op datum registratie en daarbinnen eerst juist dan
     * onjuist.
     */
    public static final class OuderComparator implements Comparator<Lo3Categorie<Lo3OuderInhoud>> {

        @Override
        public int compare(final Lo3Categorie<Lo3OuderInhoud> arg0, final Lo3Categorie<Lo3OuderInhoud> arg1) {
            final Lo3Historie his0 = arg0.getHistorie();
            final Lo3Historie his1 = arg1.getHistorie();

            int result = -his0.getIngangsdatumGeldigheid().compareTo(his1.getIngangsdatumGeldigheid());
            if (result == 0) {
                result = -his0.getDatumVanOpneming().compareTo(his1.getDatumVanOpneming());
            }
            if (result == 0) {
                final boolean juist0 = his0.getIndicatieOnjuist() == null;
                final boolean juist1 = his1.getIndicatieOnjuist() == null;

                if (juist0) {
                    result = juist1 ? 0 : -1;
                } else {
                    result = juist1 ? 1 : 0;
                }
            }

            return result;
        }

    }
}
