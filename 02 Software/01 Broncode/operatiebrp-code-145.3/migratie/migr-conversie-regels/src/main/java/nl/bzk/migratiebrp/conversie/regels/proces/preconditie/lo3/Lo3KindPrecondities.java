/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor categorie 09: Kind.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3KindPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3KindPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer alle stapels.
     * @param stapels stapels
     * @param persoonAnummer anummer van de persoon zelf (01.01.10)
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3KindInhoud>> stapels, final Lo3String persoonAnummer) {
        if (stapels == null) {
            return;
        }

        final List<String> anummers = new ArrayList<>();
        for (final Lo3Stapel<Lo3KindInhoud> stapel : stapels) {
            controleerPreconditie073(anummers, stapel);
            // Controleer de stapel
            controleerStapel(stapel, persoonAnummer);
        }
    }

    /**
     * Controleer preconditie073 of alle kinderen een apart anummer hebben.
     * @param anummers De gevonden anummers tot nu toe.
     * @param stapel De stapel die gecontroleerd moet worden.
     */
    private void controleerPreconditie073(final List<String> anummers, final Lo3Stapel<Lo3KindInhoud> stapel) {
        if (stapel != null && stapel.getLo3ActueelVoorkomen() != null && stapel.getLo3ActueelVoorkomen().getInhoud() != null) {
            final Lo3Categorie<Lo3KindInhoud> actueel = stapel.getLo3ActueelVoorkomen();
            final String anummer = Lo3String.unwrap(actueel.getInhoud().getaNummer());

            if (anummer != null) {
                if (anummers.contains(anummer) && !actueel.getHistorie().isOnjuist()) {
                    // Als het anummer in meerdere stapels voorkomt wordt preconditie 73 overtreden.
                    Foutmelding.logMeldingFout(actueel.getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.PRE073, null);
                }
                anummers.add(anummer);
            }
        }
    }

    private void controleerStapel(final Lo3Stapel<Lo3KindInhoud> stapel, final Lo3String persoonAnummer) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);
        controleerPreconditie113(stapel, persoonAnummer);

        for (final Lo3Categorie<Lo3KindInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3KindInhoud> categorie) {
        final Lo3KindInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep01Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud);
        final boolean groep02Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud);
        final boolean groep03Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud);
        // Groep 02: Naam
        if (!groep02Aanwezig) {

            // Groep 01: Identificatienummers
            if (groep01Aanwezig || groep03Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE048, null);
            }
        } else {
            controleerGroep02Naam(
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam(),
                    herkomst,
                    false);

            // Groep 01: Identificatienummers
            if (groep01Aanwezig) {
                controleerGroep01Identificatienummers(
                        inhoud.getaNummer(),
                        inhoud.getBurgerservicenummer(),
                        historie.getIngangsdatumGeldigheid(),
                        herkomst,
                        false);
            }

            // Groep 03: Geboorte
            if (groep03Aanwezig) {
                controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
            }

        }
    }

}
