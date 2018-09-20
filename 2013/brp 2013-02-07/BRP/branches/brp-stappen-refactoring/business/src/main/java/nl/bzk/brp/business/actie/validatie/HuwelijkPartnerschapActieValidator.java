/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;


/** Validator die huwelijk en partnerschap acties valideert. */
@Component
public class HuwelijkPartnerschapActieValidator implements ActieValidator {

    @Override
    public List<Melding> valideerActie(final Actie actie) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (null == actie.getRootObjecten() || 0 == actie.getRootObjecten().size()) {
            ValidatieUtil.voegMeldingVerplichtVeld(meldingen, (ActieBericht) actie, "huwelijk");
        } else {
            HuwelijkBericht huwelijk = (HuwelijkBericht) actie.getRootObjecten().get(0);
            meldingen.addAll(valideerHuwelijk(huwelijk));

            List<BetrokkenheidBericht> partnerBetrokkenheden =
                huwelijk.getBetrokkenheden();

            if (!CollectionUtils.isEmpty(partnerBetrokkenheden) && partnerBetrokkenheden.size() == 2) {
                for (Betrokkenheid betrokkenheid : partnerBetrokkenheden) {
                    if (betrokkenheid == null || betrokkenheid.getPersoon() == null) {
                        ValidatieUtil.voegMeldingVerplichtVeld(meldingen, huwelijk, "partner");
                    } else {
                        Persoon partner = betrokkenheid.getPersoon();
                        if (partner != null) {
                            meldingen.addAll(valideerPartner((PersoonBericht) partner));
                        }
                    }

                }
            } else {
                // Conditie zou in principe niet voor kunnen komen, xsd en binding dwingen af dat er 2 aanwezig moet
                // zijn
                meldingen
                    .add(new Melding(SoortMelding.FOUT,
                        MeldingCode.ALG0002, "Ontbrekende partners"));
            }
        }

        return meldingen;
    }

    /**
     * Valideert de gegevens van huwelijk.
     *
     * @param huwelijk Relatie.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerHuwelijk(final HuwelijkBericht huwelijk) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (huwelijk.getStandaard() != null) {
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, huwelijk,
                huwelijk.getStandaard().getDatumAanvang(), "datumAanvang");
            ValidatieUtil.controlleerVerplichteVeldInObjectType(meldingen, huwelijk,
                huwelijk.getStandaard().getGemeenteAanvang(), "gemeenteAanvangCode");
        } else {
            // Dit zou in principe niet voor kunnen komen omdat gegevens aangemaakt wordt door de bindings
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                "Ontbrekende huwelijk gegevens"));
        }

        return meldingen;
    }

    /**
     * Valideert de gegevens van het partner.
     *
     * @param partner Het te valideren partner.
     * @return Lijst van validatie meldingen.
     */
    private Collection<? extends Melding> valideerPartner(final PersoonBericht partner) {
        final List<Melding> meldingen = new ArrayList<Melding>();

        PersoonIdentificatienummersGroepBericht persoonIdentificatienummers = partner.getIdentificatienummers();
        // TODO: bolie, moet omgezet worden naar technische sleutel
        if (persoonIdentificatienummers != null) {
            ValidatieUtil.controleerVerplichteVeldInGroep(meldingen, persoonIdentificatienummers,
                persoonIdentificatienummers.getBurgerservicenummer(), "burgerservicenummer");
        } else {
            // TODO voor huidige release 0.1.7 is de scope alleen ingescheven personen
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                "Personen zonder BSN wordt op het moment nog niet ondersteund"));
        }

        return meldingen;
    }
}
