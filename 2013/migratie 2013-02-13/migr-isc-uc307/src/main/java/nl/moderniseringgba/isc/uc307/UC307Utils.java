/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3GeslachtsaanduidingEnum;

/**
 * Util methodes gebruikt binnen UC307.
 */
public final class UC307Utils {

    private UC307Utils() {
        // niet instantieerbaar
    }

    /**
     * Geeft de LO3OuderInhoud terug van de actuele moeder op de meegegeven LO3 persoonslijst.
     * 
     * @param persoonslijst
     *            De persoonslijst van het kind waarop de actuele moeder gegevens (LO3 Ouderinhoud) staan.
     * @return De LO3OuderInhoud.
     */
    public static Lo3OuderInhoud actueleMoederGegevens(final Lo3Persoonslijst persoonslijst) {
        final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = persoonslijst.getOuder1Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = ouder1Stapels.get(0);
        final Lo3Categorie<Lo3OuderInhoud> meestRecenteOuder1 = ouder1Stapel.getMeestRecenteElement();
        final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = persoonslijst.getOuder2Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = ouder2Stapels.get(0);
        final Lo3Categorie<Lo3OuderInhoud> meestRecenteOuder2 = ouder2Stapel.getMeestRecenteElement();

        final Lo3OuderInhoud moederGegevens;
        if (isMoeder(meestRecenteOuder1) && !isMoeder(meestRecenteOuder2)) {
            moederGegevens = meestRecenteOuder1.getInhoud();
        } else if (!isMoeder(meestRecenteOuder1) && isMoeder(meestRecenteOuder2)) {
            moederGegevens = meestRecenteOuder2.getInhoud();
        } else {
            moederGegevens = null;
        }

        return moederGegevens;
    }

    private static boolean isMoeder(final Lo3Categorie<Lo3OuderInhoud> ouderInhoud) {

        // In prinicipe zou er ook een voor het gevoel een null-controle dienen te zijn op ouderInhoud en
        // ouderInhoud.getInhoud() echter als gevolg van de verplichte vulling van een stapel en de inhoud, kan deze
        // situatie niet optreden. De controle is daarom dan ook hier niet aanwezig.
        if (ouderInhoud.getInhoud().getGeslachtsaanduiding() != null) {
            return Lo3GeslachtsaanduidingEnum.VROUW.getCode().equals(
                    ouderInhoud.getInhoud().getGeslachtsaanduiding().getCode());
        } else {
            return false;
        }
    }

}
