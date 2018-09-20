/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 21.
 *
 */
@Component
@Requirement(Requirements.CHP001_LB21)
@SuppressWarnings("PMD.CompareObjectsWithEquals" /* Controle op specifiek object voorkomen. */)
public class Lo3HistorieConversieVariantLB21 extends Lo3HistorieConversieVariant {

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
        if (lo3Groep.isInhoudelijkLeeg()) {
            return null;
        }

        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final boolean isOnjuist = lo3Historie.isOnjuist();

        final BrpDatum aanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        // bij een onjuiste rij wordt de einde geldigheid niet gevuld
        final BrpDatum eindeGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        final BrpActie actieInhoud = maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst(), actieCache);
        final BrpActie actieVerval;
        final BrpCharacter nadereAanduidingVerval = maakNadereAanduidingVerval(lo3Historie);

        if (isOnjuist) {
            eindeGeldigheid = null;
            datumTijdVerval = datumTijdRegistratie;
            actieVerval = actieInhoud;
        } else {
            final TussenGroep<T> volgendeRij = bepaalVolgendeJuisteGroep(lo3Groep, lo3Groepen);
            final TussenGroep<T> actueleRij = bepaalActueleGroep(lo3Groepen);

            final BrpDatum aanvangGeldigheidVolgendeJuistGroep =
                    volgendeRij == null ? null : BrpDatum.fromLo3Datum(volgendeRij.getHistorie().getIngangsdatumGeldigheid());
            final BrpDatum aanvangGeldigheidActueleGroep =
                    actueleRij == null ? null : BrpDatum.fromLo3Datum(actueleRij.getHistorie().getIngangsdatumGeldigheid());

            final boolean volgendeRijEerderGeldig =
                    aanvangGeldigheidVolgendeJuistGroep != null && aanvangGeldigheidVolgendeJuistGroep.compareTo(aanvangGeldigheid) <= 0;
            final boolean actueleRijEerderGeldig =
                    aanvangGeldigheidActueleGroep != null && actueleRij != lo3Groep && aanvangGeldigheidActueleGroep.compareTo(aanvangGeldigheid) <= 0;

            if (volgendeRijEerderGeldig || actueleRijEerderGeldig) {
                eindeGeldigheid = null;
                datumTijdVerval = new BrpDatumTijd(datumTijdRegistratie.getJavaDate(), null);
                actieVerval = actieInhoud;
            } else {
                if (aanvangGeldigheidVolgendeJuistGroep != null) {
                    eindeGeldigheid = new BrpDatum(aanvangGeldigheidVolgendeJuistGroep.getWaarde(), null);
                } else {
                    eindeGeldigheid = null;
                }
                datumTijdVerval = null;
                actieVerval = null;
            }
        }

        final BrpHistorie historie = new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);

        return new BrpGroep<>(lo3Groep.getInhoud(), historie, actieInhoud, actieVerval, null);
    }

    /**
     * Doe de nabewerking voor historie conversie variant 21 om actieGeldigheid aan te vullen.
     *
     * @param inGroepen
     *            de BRP groepen
     * @param <T>
     *            groep inhoud type
     * @return de BRP groepen parameter
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> inGroepen) {
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();

        for (final BrpGroep<T> groep : inGroepen) {
            if (groep.getActieGeldigheid() == null
                && groep.getHistorie().getDatumEindeGeldigheid() != null
                && groep.getHistorie().getDatumEindeGeldigheid().isInhoudelijkGevuld())
            {
                final BrpGroep<T> opvolger = bepaalNietVervallenOpvolger(groep, inGroepen);
                brpGroepen.add(
                    new BrpGroep<>(groep.getInhoud(), groep.getHistorie(), groep.getActieInhoud(), groep.getActieVerval(), opvolger.getActieInhoud()));
            } else {
                brpGroepen.add(groep);
            }
        }

        return brpGroepen;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalNietVervallenOpvolger(final BrpGroep<T> basis, final List<BrpGroep<T>> groepen) {
        final BrpDatum basisEindeGeldigheid = basis.getHistorie().getDatumEindeGeldigheid();
        BrpGroep<T> opvolger = null;

        for (final BrpGroep<T> groep : groepen) {
            if (groep.getHistorie().getDatumTijdVerval() == null
                && groep.getHistorie().getDatumAanvangGeldigheid() != null
                && groep.getHistorie().getDatumAanvangGeldigheid().getWaarde().equals(basisEindeGeldigheid.getWaarde())
                && groep != basis)
            {
                if (opvolger == null) {
                    opvolger = groep;
                } else {
                    throw new IllegalStateException("Kan niet-vervallen opvolger niet eenduidig bepalen.");
                }
            }
        }

        if (opvolger != null) {
            return opvolger;
        } else {
            return bepaalMeestRecenteOpvolger(basis, groepen);
        }
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalMeestRecenteOpvolger(final BrpGroep<T> basis, final List<BrpGroep<T>> groepen) {
        final BrpDatum basisEindeGeldigheid = basis.getHistorie().getDatumEindeGeldigheid();
        BrpGroep<T> opvolger = null;

        for (final BrpGroep<T> groep : groepen) {
            if (!Validatie.isAttribuutGevuld(groep.getHistorie().getNadereAanduidingVerval())
                && groep.getHistorie().getDatumAanvangGeldigheid() != null
                && groep.getHistorie().getDatumAanvangGeldigheid().getWaarde().equals(basisEindeGeldigheid.getWaarde())
                && groep != basis)
            {
                if (opvolger == null || groep.getHistorie().getDatumTijdRegistratie().compareTo(opvolger.getHistorie().getDatumTijdRegistratie()) > 0) {
                    opvolger = groep;
                }
            }
        }

        if (opvolger != null) {
            return opvolger;
        } else {
            throw new IllegalStateException("Kan meest-recente opvolger niet bepalen.");
        }
    }
}
