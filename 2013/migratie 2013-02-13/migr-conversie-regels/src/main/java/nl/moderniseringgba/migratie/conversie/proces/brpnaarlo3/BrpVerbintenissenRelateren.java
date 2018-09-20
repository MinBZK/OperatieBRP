/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Relateer verbintenissen.
 */
@Component
public class BrpVerbintenissenRelateren {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Voegt verbintenissen samen obv acties.
     * 
     * @param relaties
     *            alle relaties
     * @return lijst van lijsten van stapels die samen horen
     */
    public final List<List<BrpRelatie>> bepaalSamenhangendeVerbintenissen(final List<BrpRelatie> relaties) {
        LOG.debug("bepaalSamenhangendeVerbintenissen() aantal: {}", relaties.size());

        final List<BrpRelatie> verbintenissen = new ArrayList<BrpRelatie>();
        for (final BrpRelatie relatie : relaties) {
            if (BrpSoortRelatieCode.HUWELIJK.equals(relatie.getSoortRelatieCode())
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatieCode())) {
                verbintenissen.add(relatie);
            }
        }

        List<List<BrpRelatie>> result = relaterenVerbintenissen(verbintenissen);
        result = verwijderenOmzettingen(result);

        if (LOG.isDebugEnabled()) {
            LOG.debug("result: aantal eilanden: {}", result.size());
            for (final List<BrpRelatie> eiland : result) {
                LOG.debug(" - eiland: {}", eiland.size());
            }
        }

        return result;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<List<BrpRelatie>> verwijderenOmzettingen(final List<List<BrpRelatie>> samenhangendeVerbintenissen) {
        final List<List<BrpRelatie>> result = new ArrayList<List<BrpRelatie>>();

        for (final List<BrpRelatie> samenhangendeVerbintenis : samenhangendeVerbintenissen) {
            final List<BrpRelatie> verbintenissen = new ArrayList<BrpRelatie>();

            for (final BrpRelatie verbintenis : samenhangendeVerbintenis) {
                verbintenissen.add(verwijderenOmzettingen(verbintenis));
            }

            result.add(verbintenissen);
        }

        return result;

    }

    private BrpRelatie verwijderenOmzettingen(final BrpRelatie verbintenis) {
        final List<BrpGroep<BrpRelatieInhoud>> groepen = new ArrayList<BrpGroep<BrpRelatieInhoud>>();

        for (final BrpGroep<BrpRelatieInhoud> groep : verbintenis.getRelatieStapel()) {
            if (!BrpRedenEindeRelatieCode.OMZETTING.equals(groep.getInhoud().getRedenEinde())) {
                groepen.add(groep);
            }
        }

        final BrpStapel<BrpRelatieInhoud> stapel =
                groepen.isEmpty() ? null : new BrpStapel<BrpRelatieInhoud>(groepen);

        return new BrpRelatie(verbintenis.getSoortRelatieCode(), verbintenis.getRolCode(),
                verbintenis.getBetrokkenheden(), stapel);

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<List<BrpRelatie>> relaterenVerbintenissen(final List<BrpRelatie> losseVerbintenissen) {
        final List<List<BrpRelatie>> result = new ArrayList<List<BrpRelatie>>();

        while (!losseVerbintenissen.isEmpty()) {
            final List<BrpRelatie> eiland = new ArrayList<BrpRelatie>();
            eiland.add(losseVerbintenissen.remove(0));
            eiland.addAll(zoekLosseVerbintenisssenBij(eiland.get(0), losseVerbintenissen));

            result.add(eiland);
        }

        return result;
    }

    private List<BrpRelatie> zoekLosseVerbintenisssenBij(
            final BrpRelatie eiland,
            final List<BrpRelatie> losseVerbintenissen) {
        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();
        BrpRelatie match = null;
        while ((match = zoekLosseVerbintenisBij(eiland, losseVerbintenissen)) != null) {
            result.add(match);
            result.addAll(zoekLosseVerbintenisssenBij(match, losseVerbintenissen));
        }

        return result;
    }

    private BrpRelatie zoekLosseVerbintenisBij(final BrpRelatie eiland, final List<BrpRelatie> losseVerbintenissen) {
        final Iterator<BrpRelatie> iterator = losseVerbintenissen.iterator();

        while (iterator.hasNext()) {
            final BrpRelatie verbintenis = iterator.next();

            if (isMatch(eiland, verbintenis)) {
                iterator.remove();
                return verbintenis;
            }
        }

        return null;

    }

    private boolean isMatch(final BrpRelatie eiland, final BrpRelatie verbintenis) {
        return isMatchObvOmzetting(eiland, verbintenis) || isMatchObvDocument(eiland, verbintenis);
    }

    private boolean isMatchObvOmzetting(final BrpRelatie relatie1, final BrpRelatie relatie2) {
        for (final BrpGroep<BrpRelatieInhoud> relatieInhoudGroep1 : relatie1.getRelatieStapel()) {
            for (final BrpGroep<BrpRelatieInhoud> relatieInhoudGroep2 : relatie2.getRelatieStapel()) {
                if (isMatchObvOmzetting(relatieInhoudGroep1.getInhoud(), relatieInhoudGroep2.getInhoud())
                        || isMatchObvOmzetting(relatieInhoudGroep2.getInhoud(), relatieInhoudGroep1.getInhoud())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isMatchObvOmzetting(final BrpRelatieInhoud ontbinding, final BrpRelatieInhoud sluiting) {
        final BrpRedenEindeRelatieCode redenEinde = ontbinding.getRedenEinde();
        if (!BrpRedenEindeRelatieCode.OMZETTING.equals(redenEinde)) {
            return false;
        }

        // CHECKSTYLE:OFF - Boolean complexity - niet complex
        return isGelijk(ontbinding.getDatumEinde(), sluiting.getDatumAanvang())
                && isGelijk(ontbinding.getGemeenteCodeEinde(), sluiting.getGemeenteCodeAanvang())
                && isGelijk(ontbinding.getPlaatsCodeEinde(), sluiting.getPlaatsCodeAanvang())
                && isGelijk(ontbinding.getBuitenlandsePlaatsEinde(), sluiting.getPlaatsCodeAanvang())
                && isGelijk(ontbinding.getBuitenlandseRegioEinde(), sluiting.getBuitenlandseRegioAanvang())
                && isGelijk(ontbinding.getLandCodeEinde(), sluiting.getLandCodeAanvang())
                && isGelijk(ontbinding.getOmschrijvingLocatieEinde(), sluiting.getOmschrijvingLocatieAanvang());
        // CHECKSTYLE:ON
    }

    private boolean isGelijk(final Object o1, final Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    private boolean isMatchObvDocument(final BrpRelatie relatie1, final BrpRelatie relatie2) {
        for (final BrpGroep<BrpRelatieInhoud> relatieInhoudGroep1 : relatie1.getRelatieStapel()) {
            for (final BrpGroep<BrpRelatieInhoud> relatieInhoudGroep2 : relatie2.getRelatieStapel()) {
                if (isMatchObvDocument(relatieInhoudGroep1.getActieInhoud(), relatieInhoudGroep2.getActieInhoud())
                        || isMatchObvDocument(relatieInhoudGroep2.getActieInhoud(),
                                relatieInhoudGroep1.getActieInhoud())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isMatchObvDocument(final BrpActie actie1, final BrpActie actie2) {
        final String extraDocument = getExtraDocument(actie1);
        if (extraDocument == null) {
            return false;
        }

        return extraDocument.equals(getExtraDocument(actie2));
    }

    private String getExtraDocument(final BrpActie actie) {
        if (actie != null) {
            for (final BrpStapel<BrpDocumentInhoud> documentStapel : actie.getDocumentStapels()) {
                for (final BrpGroep<BrpDocumentInhoud> document : documentStapel) {
                    final BrpDocumentInhoud inhoud = document.getInhoud();

                    if (!BrpSoortDocumentCode.MIGRATIEVOORZIENING.equals(inhoud.getSoortDocumentCode())) {
                        continue;
                    }
                    if (!BrpPartijCode.MIGRATIEVOORZIENING.equals(inhoud.getPartijCode())) {
                        continue;
                    }
                    if (inhoud.getOmschrijving() != null
                            && inhoud.getOmschrijving().startsWith(
                                    BrpDocumentInhoud.EXTRA_DOCUMENT_VERBINTENIS_OMSCHRIJVING)) {
                        return inhoud.getOmschrijving();
                    }
                }
            }
        }

        return null;

    }

}
