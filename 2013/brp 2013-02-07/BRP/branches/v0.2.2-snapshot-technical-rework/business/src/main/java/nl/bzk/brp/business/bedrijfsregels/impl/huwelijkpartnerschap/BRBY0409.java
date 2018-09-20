/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.collections.CollectionUtils;


/**
 * Trouwen (of geregistreerd partnerschap aangaan) mag niet worden uitgevoerd als er verwantschap is tussen beoogde
 * huwelijkspartners (of geregistreerde partners) en beoogde partners Nederlandse nationaliteit hebben.
 * <p/>
 * Verwantschap wordt bepaald (zie in BRBY0001):
 *
 * @brp.bedrijfsregel BRBY0409
 */
public class BRBY0409 implements ActieBedrijfsRegel<Relatie> {

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0409";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        Set<? extends Betrokkenheid> partners = ((RelatieBericht) nieuweSituatie).getPartnerBetrokkenheden();

        PersoonModel persoon1 = null;
        PersoonModel persoon2 = null;
        if (CollectionUtils.isNotEmpty(partners)) {
            Iterator<? extends Betrokkenheid> it = partners.iterator();

            persoon1 =
                persoonRepository.findByBurgerservicenummer(it.next().getBetrokkene().getIdentificatienummers()
                                                              .getBurgerservicenummer());
            persoon2 =
                persoonRepository.findByBurgerservicenummer(it.next().getBetrokkene().getIdentificatienummers()
                                                              .getBurgerservicenummer());
        }

        if (hebbenBeideNederlandseNationaliteit(persoon1, persoon2)
            && relatieRepository.isVerwant(persoon1.getId(), persoon2.getId()))
        {
            meldingen.add(new Melding(SoortMelding.OVERRULEBAAR, MeldingCode.BRBY0409,
                (Identificeerbaar) nieuweSituatie, "huwelijk"));
        }

        return meldingen;
    }

    /**
     * Controleert of beide personen de nederlandse nationaliteit hebben. Indien een of beide personen
     * <code>null</code> zijn, dan retourneert deze methode ook <code>false</code>.
     *
     * @param persoon1 de eerste persoon
     * @param persoon2 de tweede persoon
     * @return of beide personen de nederlandse nationaliteit hebben.
     */
    private boolean hebbenBeideNederlandseNationaliteit(final PersoonModel persoon1, final PersoonModel persoon2) {
        return persoon1 != null && persoon2 != null && persoon1.heeftActueleNederlandseNationaliteit() && persoon2
            .heeftActueleNederlandseNationaliteit();
    }
}
