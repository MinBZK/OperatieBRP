/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.springframework.stereotype.Component;

/**
 * Historie variant LB25 voor historie conversie van afnemerindicatie. Maakt van LO3 historie (geen formele historie,
 * wel materiele historie) BRP historie (geen materiele historie, wel formele historie).
 */
@Component
public class Lo3HistorieConversieVariantLB25 extends Lo3HistorieConversieVariant {

    private static final int EEN_MINUUT = 100;

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        final BrpDatumTijd tijdstipRegistratie = bepaalTijdstipRegistratie(lo3Groep, brpGroepen);
        final BrpActie actieInhoud = maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst(), actieCache);

        BrpGroep<T> vorigRecord = brpGroepen.isEmpty() ? null : brpGroepen.get(brpGroepen.size() - 1);

        boolean updateVorigRecord = false;

        final BrpGroep<T> nieuwRecord;
        if (!lo3Groep.isInhoudelijkLeeg()) {
            // / Maak nieuw record, vul datum ingang en ts registratie
            final T inhoud = lo3Groep.getInhoud();
            final BrpHistorie historie =
                    new BrpHistorie(BrpDatum.fromLo3Datum(lo3Groep.getHistorie().getIngangsdatumGeldigheid()), null, tijdstipRegistratie, null, null);

            nieuwRecord = new BrpGroep<>(inhoud, historie, actieInhoud, null, null);
            // Laat vorig record vervallen (als geen vorig record, dan negeren)
            updateVorigRecord = true;
        } else {
            if (vorigRecord != null && vorigRecord.getHistorie().getDatumEindeGeldigheid() == null) {
                // Laat vorig record vervallen
                nieuwRecord = null;
                updateVorigRecord = true;
            } else {
                // Geen vorig record; record negeren en fout AFN008 loggen
                Logging.log(lo3Groep.getLo3Herkomst(), LogSeverity.SUPPRESSED, SoortMeldingCode.AFN008, null);
                nieuwRecord = null;
            }
        }

        if (updateVorigRecord && vorigRecord != null) {
            if (vorigRecord.getActieVerval() == null) {
                // Vul tijdstip verval, met tijdstip registratie voor huidige record
                // Vul actie verval met actie inhoud voor huidige record
                final T vorigeInhoud = vorigRecord.getInhoud();
                final BrpHistorie vorigeHistorie = vorigRecord.getHistorie().laatVervallen(tijdstipRegistratie);
                final BrpActie vorigeActieInhoud = vorigRecord.getActieInhoud();
                final BrpActie vorigeActieVerval = actieInhoud;

                vorigRecord = new BrpGroep<>(vorigeInhoud, vorigeHistorie, vorigeActieInhoud, vorigeActieVerval, null);
                brpGroepen.set(brpGroepen.size() - 1, vorigRecord);
            } else if (lo3Groep.isInhoudelijkLeeg()) {
                // Dit record is leeg, maar vorig record was al vervallen; record negeren en fout AFN008 loggen
                Logging.log(lo3Groep.getLo3Herkomst(), LogSeverity.SUPPRESSED, SoortMeldingCode.AFN008, null);
            }
        }

        return nieuwRecord;
    }

    private <T extends BrpGroepInhoud> BrpDatumTijd bepaalTijdstipRegistratie(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen) {
        BrpDatumTijd tijdstipRegistratie = BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming());

        while (tijdstipRegistratieBestaatReeds(tijdstipRegistratie, brpGroepen)) {
            tijdstipRegistratie = ophogenMetMinuut(tijdstipRegistratie);
        }
        return tijdstipRegistratie;
    }

    private <T extends BrpGroepInhoud> boolean tijdstipRegistratieBestaatReeds(
        final BrpDatumTijd tijdstipRegistratie,
        final List<BrpGroep<T>> brpGroepen)
    {
        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (AbstractBrpAttribuutMetOnderzoek.equalsWaarde(tijdstipRegistratie, brpGroep.getHistorie().getDatumTijdRegistratie())) {
                return true;
            }
        }
        return false;
    }

    private BrpDatumTijd ophogenMetMinuut(final BrpDatumTijd tijdstipRegistratie) {
        return BrpDatumTijd.fromDatumTijd(tijdstipRegistratie.getDatumTijd() + EEN_MINUUT, tijdstipRegistratie.getOnderzoek());
    }

    /*
     * Nabewerking: <ul> <li>Voor BrpAbonnementExpressieInhoud: als groep een datumEindeGeldigheid heeft dan groep
     * verwijderen.</li> </ul>
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen) {
        final List<BrpGroep<T>> nabewerkteBrpGroepen;

        if (brpGroepen.isEmpty()) {
            nabewerkteBrpGroepen = brpGroepen;
        } else {
            final BrpGroepInhoud inhoud = brpGroepen.get(0).getInhoud();
            if (inhoud instanceof BrpDienstbundelLo3Rubriek) {
                nabewerkteBrpGroepen = verwijderBeeindigdeGroepen(brpGroepen);
            } else {
                nabewerkteBrpGroepen = brpGroepen;
            }
        }

        return nabewerkteBrpGroepen;
    }

    private <T extends BrpGroepInhoud> List<BrpGroep<T>> verwijderBeeindigdeGroepen(final List<BrpGroep<T>> brpGroepen) {
        final List<BrpGroep<T>> result = new ArrayList<>();
        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (brpGroep.getHistorie().getDatumEindeGeldigheid() == null) {
                result.add(brpGroep);
            }
        }
        return result;
    }
}
