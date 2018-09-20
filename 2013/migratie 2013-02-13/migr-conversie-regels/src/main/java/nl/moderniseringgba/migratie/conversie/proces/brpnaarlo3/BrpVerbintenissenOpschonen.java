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

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;

import org.springframework.stereotype.Component;

/**
 * Verbintenissen opschonen. BRP slaat gegevens anders op (beide sluiting en ontbinding) dan LO3 (sluiting en ontbinding
 * apart). Dit moet opgeschoond worden naar de 'lo3'-manier voor de terugconversie.
 */
@Component
public final class BrpVerbintenissenOpschonen {

    private static final Comparator<BrpGroep<?>> DATUMTIJD_REGISTRATIE_COMPARATOR =
            new DatumtijdRegistratieComparator();

    /**
     * Opschonen.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst
     */
    public BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) {
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        builder.relaties(Collections.<BrpRelatie>emptyList());
        for (final BrpRelatie relatie : persoonslijst.getRelaties()) {
            if (BrpSoortRelatieCode.HUWELIJK.equals(relatie.getSoortRelatieCode())
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.equals(relatie.getSoortRelatieCode())) {
                builder.relatie(opschonenVerbintenissen(relatie));
            } else {
                builder.relatie(relatie);

            }
        }

        return builder.build();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /*
     * In de conversie van LO3 -> BRP worden sluitingen en ontbindngen gekopieerd naar latere records omdat dit anders
     * opgeslagen wordt in BRP. Deze stap schoont deze informatie weer op.
     */
    private BrpRelatie opschonenVerbintenissen(final BrpRelatie relatie) {
        return new BrpRelatie(relatie.getSoortRelatieCode(), relatie.getRolCode(), relatie.getBetrokkenheden(),
                opschonenVerbintenissen(relatie.getRelatieStapel()));
    }

    private BrpStapel<BrpRelatieInhoud> opschonenVerbintenissen(final BrpStapel<BrpRelatieInhoud> relatieStapel) {
        if (relatieStapel == null || relatieStapel.size() <= 1) {
            return relatieStapel;
        }

        final List<BrpGroep<BrpRelatieInhoud>> result = new ArrayList<BrpGroep<BrpRelatieInhoud>>();

        final List<BrpGroep<BrpRelatieInhoud>> groepen = relatieStapel.getGroepen();
        Collections.sort(groepen, DATUMTIJD_REGISTRATIE_COMPARATOR);

        BrpGroep<BrpRelatieInhoud> vorigeGroep = null;
        for (final BrpGroep<BrpRelatieInhoud> dezeGroep : groepen) {
            if (vorigeGroep != null) {
                result.add(opschonenVerbintenisssen(dezeGroep, vorigeGroep));
            } else {
                result.add(dezeGroep);
            }
            vorigeGroep = dezeGroep;

        }
        return new BrpStapel<BrpRelatieInhoud>(result);
    }

    private BrpGroep<BrpRelatieInhoud> opschonenVerbintenisssen(
            final BrpGroep<BrpRelatieInhoud> dezeGroep,
            final BrpGroep<BrpRelatieInhoud> vorigeGroep) {
        final boolean sluitingGelijk = vergelijkSluiting(dezeGroep, vorigeGroep);
        final boolean ontbindingGelijk = vergelijkOntbinding(dezeGroep, vorigeGroep);

        final BrpGroep<BrpRelatieInhoud> result;
        if (sluitingGelijk != ontbindingGelijk) {
            if (sluitingGelijk) {
                result = opschonenSluiting(dezeGroep);
            } else {
                result = opschonenOntbinding(dezeGroep);
            }
        } else {
            final boolean sluitingGevuld = dezeGroep.getInhoud().getDatumAanvang() != null;
            final boolean ontbindingGevuld = dezeGroep.getInhoud().getDatumEinde() != null;

            if (sluitingGevuld && ontbindingGevuld) {
                result = opschonenSluiting(dezeGroep);
            } else {
                result = dezeGroep;
            }
        }

        return result;

    }

    private boolean vergelijkSluiting(
            final BrpGroep<BrpRelatieInhoud> dezeGroep,
            final BrpGroep<BrpRelatieInhoud> vorigeGroep) {
        final BrpRelatieInhoud deze = dezeGroep.getInhoud();
        final BrpRelatieInhoud vorige = vorigeGroep.getInhoud();

        // CHECKSTYLE:OFF - Boolean complexity - niet complex
        return isGelijk(deze.getDatumAanvang(), vorige.getDatumAanvang())
                && isGelijk(deze.getGemeenteCodeAanvang(), vorige.getGemeenteCodeAanvang())
                && isGelijk(deze.getPlaatsCodeAanvang(), vorige.getPlaatsCodeAanvang())
                && isGelijk(deze.getBuitenlandsePlaatsAanvang(), vorige.getPlaatsCodeAanvang())
                && isGelijk(deze.getBuitenlandseRegioAanvang(), vorige.getBuitenlandseRegioAanvang())
                && isGelijk(deze.getLandCodeAanvang(), vorige.getLandCodeAanvang())
                && isGelijk(deze.getOmschrijvingLocatieAanvang(), vorige.getOmschrijvingLocatieAanvang());
        // CHECKSTYLE:ON
    }

    private boolean vergelijkOntbinding(
            final BrpGroep<BrpRelatieInhoud> dezeGroep,
            final BrpGroep<BrpRelatieInhoud> vorigeGroep) {
        final BrpRelatieInhoud deze = dezeGroep.getInhoud();
        final BrpRelatieInhoud vorige = vorigeGroep.getInhoud();

        // CHECKSTYLE:OFF - Boolean complexity - niet complex
        return isGelijk(deze.getDatumEinde(), vorige.getDatumEinde())
                && isGelijk(deze.getGemeenteCodeEinde(), vorige.getGemeenteCodeEinde())
                && isGelijk(deze.getPlaatsCodeEinde(), vorige.getPlaatsCodeEinde())
                && isGelijk(deze.getBuitenlandsePlaatsEinde(), vorige.getBuitenlandsePlaatsEinde())
                && isGelijk(deze.getBuitenlandseRegioEinde(), vorige.getBuitenlandseRegioEinde())
                && isGelijk(deze.getLandCodeEinde(), vorige.getLandCodeEinde())
                && isGelijk(deze.getOmschrijvingLocatieEinde(), vorige.getOmschrijvingLocatieEinde())
                && isGelijk(deze.getRedenEinde(), vorige.getRedenEinde());
        // CHECKSTYLE:ON
    }

    private boolean isGelijk(final Object o1, final Object o2) {
        if (o1 == null) {
            return o2 == null;
        } else {
            return o1.equals(o2);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private BrpGroep<BrpRelatieInhoud> opschonenSluiting(final BrpGroep<BrpRelatieInhoud> dezeGroep) {
        final BrpRelatieInhoud deze = dezeGroep.getInhoud();

        final BrpRelatieInhoud inhoud =
                new BrpRelatieInhoud(null, null, null, null, null, null, null, deze.getRedenEinde(),
                        deze.getDatumEinde(), deze.getGemeenteCodeEinde(), deze.getPlaatsCodeEinde(),
                        deze.getBuitenlandsePlaatsEinde(), deze.getBuitenlandseRegioEinde(), deze.getLandCodeEinde(),
                        deze.getOmschrijvingLocatieEinde());

        return new BrpGroep<BrpRelatieInhoud>(inhoud, dezeGroep.getHistorie(), dezeGroep.getActieInhoud(),
                dezeGroep.getActieVerval(), dezeGroep.getActieGeldigheid());
    }

    private BrpGroep<BrpRelatieInhoud> opschonenOntbinding(final BrpGroep<BrpRelatieInhoud> dezeGroep) {
        final BrpRelatieInhoud deze = dezeGroep.getInhoud();

        final BrpRelatieInhoud inhoud =
                new BrpRelatieInhoud(deze.getDatumAanvang(), deze.getGemeenteCodeAanvang(),
                        deze.getPlaatsCodeAanvang(), deze.getBuitenlandsePlaatsAanvang(),
                        deze.getBuitenlandseRegioAanvang(), deze.getLandCodeAanvang(),
                        deze.getOmschrijvingLocatieEinde(), null, null, null, null, null, null, null, null);

        // En dan zetten we de datum ontbinding in datum einde geldigheid.
        final BrpHistorie historie =
                new BrpHistorie(null, deze.getDatumEinde(), dezeGroep.getHistorie().getDatumTijdRegistratie(),
                        dezeGroep.getHistorie().getDatumTijdVerval());

        return new BrpGroep<BrpRelatieInhoud>(inhoud, historie, dezeGroep.getActieInhoud(),
                dezeGroep.getActieVerval(), dezeGroep.getActieGeldigheid());
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** Sorteer op datumtijd registratie. */
    private static final class DatumtijdRegistratieComparator implements Comparator<BrpGroep<?>> {

        @Override
        public int compare(final BrpGroep<?> o1, final BrpGroep<?> o2) {
            return o1.getHistorie().getDatumTijdRegistratie().compareTo(o2.getHistorie().getDatumTijdRegistratie());
        }

    }
}
