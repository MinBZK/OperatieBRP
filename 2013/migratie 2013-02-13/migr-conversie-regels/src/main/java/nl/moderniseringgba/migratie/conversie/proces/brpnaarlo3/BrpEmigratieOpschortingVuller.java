/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.Definitie;
import nl.moderniseringgba.migratie.Definities;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;

import org.springframework.stereotype.Component;

/**
 * Een emigratie is in BRP geen reden tot opschorting van de PL; in LO3 wel.
 * 
 * Deze klasse zorgt er voor dat een emigratie in BRP leidt tot een opschorting in LO3.
 */
@Component
public class BrpEmigratieOpschortingVuller {

    /**
     * Voer de na-conversie uit voor opschorting in het geval van emigratie.
     * 
     * @param lo3Persoonslijst
     *            persoonslijst
     * @param brpPersoonslijst
     *            de BRP persoonslijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(
            final Lo3Persoonslijst lo3Persoonslijst,
            final BrpPersoonslijst brpPersoonslijst) {
        final BrpDatum datumVertrek = bepaalDatumVertrek(brpPersoonslijst);
        if (datumVertrek != null) {
            // geemigreerd
            return vulOpschorting(lo3Persoonslijst, datumVertrek.converteerNaarLo3Datum());
        } else {
            return lo3Persoonslijst;
        }
    }

    private Lo3Persoonslijst
            vulOpschorting(final Lo3Persoonslijst lo3Persoonslijst, final Lo3Datum opschortingsdatum) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(lo3Persoonslijst);

        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieen =
                new ArrayList<Lo3Categorie<Lo3InschrijvingInhoud>>();
        final Lo3Categorie<Lo3InschrijvingInhoud> inschrijving =
                lo3Persoonslijst.getInschrijvingStapel().getMeestRecenteElement();
        final Lo3InschrijvingInhoud.Builder inschrijvingBuilder =
                new Lo3InschrijvingInhoud.Builder(inschrijving.getInhoud());
        inschrijvingBuilder.setRedenOpschortingBijhoudingCode(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE
                .asElement());
        inschrijvingBuilder.setDatumOpschortingBijhouding(opschortingsdatum);

        final Lo3InschrijvingInhoud inhoud = inschrijvingBuilder.build();

        final Lo3Categorie<Lo3InschrijvingInhoud> nieuweInschrijving =
                new Lo3Categorie<Lo3InschrijvingInhoud>(inhoud, inschrijving.getDocumentatie(),
                        inschrijving.getHistorie(), inschrijving.getLo3Herkomst());
        categorieen.add(nieuweInschrijving);
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = new Lo3Stapel<Lo3InschrijvingInhoud>(categorieen);
        builder.inschrijvingStapel(inschrijvingStapel);

        return builder.build();
    }

    /**
     * Geef de datum vertrek uit Nederland, uit het actuele Adres (datum/tijd verval is leeg en Datum einde geldigheid
     * is leeg) waarbij de persoonslijst niet is opgeschort.
     * 
     * @param brpPersoonslijst
     *            de BRP Persoonslijst
     * @return De datum vertrek uit Nederland, of null.
     */
    @Definitie(Definities.DEF059)
    private BrpDatum bepaalDatumVertrek(final BrpPersoonslijst brpPersoonslijst) {
        if (brpPersoonslijst.getAdresStapel() == null) {
            return null;
        }

        final BrpGroep<BrpAdresInhoud> recentAdres = getActueleGroep(brpPersoonslijst.getAdresStapel());
        final boolean actueel =
                recentAdres.getHistorie().getDatumTijdVerval() == null
                        && recentAdres.getHistorie().getDatumEindeGeldigheid() == null;
        final boolean isGeemigreerd = recentAdres.getInhoud().getDatumVertrekUitNederland() != null;

        final BrpStapel<BrpOpschortingInhoud> opschortingStapel = brpPersoonslijst.getOpschortingStapel();
        final boolean isOpgeschort =
                opschortingStapel != null && !getActueleGroep(opschortingStapel).getInhoud().isLeeg();
        final boolean geldigeVertrekDatum = actueel && isGeemigreerd && !isOpgeschort;

        return geldigeVertrekDatum ? recentAdres.getInhoud().getDatumVertrekUitNederland() : null;
    }

    // CHECKSTYLE:OFF - Return count
    private <T extends BrpGroepInhoud> BrpGroep<T> getActueleGroep(final BrpStapel<T> stapel) {
        // CHECKSTYLE:ON
        if (stapel == null || stapel.isEmpty()) {
            return null;
        }

        final List<BrpGroep<T>> groepen = stapel.getGroepen();
        for (final BrpGroep<T> groep : groepen) {
            if (groep.getHistorie().getDatumEindeGeldigheid() == null
                    && groep.getHistorie().getDatumTijdVerval() == null) {
                return groep;
            }

        }
        return null;
    }
}
