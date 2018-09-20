/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;


/** Implementatie @brp.bedrijfsregel BRBY0126: Kind met zelfde gegevens mag niet al geregristreerd zijn in GBA. */
public class BRBY0126 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    @Inject
    private PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0126";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kind != null) {
            for (Persoon ouderBericht : RelatieUtils.haalOudersUitRelatie(nieuweSituatie)) {
                // TODO: bolie, moet omgezet worden naar technische sleutel
                final PersoonModel ouder =
                    persoonRepository.findByBurgerservicenummer(ouderBericht.getIdentificatienummers()
                            .getBurgerservicenummer());
                if (ouder != null) {
                    if (isKindAlEerderGeregistreerd(ouder, kind)) {
                        meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0126,
                                (Identificeerbaar) kind, "kind"));
                        break;
                    }
                }
            }
        }
        return meldingen;
    }

    /**
     * .
     *
     * @param ouder .
     * @param kind .
     * @return .
     */
    private boolean isKindAlEerderGeregistreerd(final PersoonModel ouder, final Persoon kind) {
        for (Persoon eerderKind : RelatieUtils.haalAlleKinderenUitPersoon(ouder)) {
            if (isGeboorteIdentiek(eerderKind.getGeboorte(), kind.getGeboorte())
                && isSamengesteldeNaamIdentiek(eerderKind.getSamengesteldeNaam(), kind.getSamengesteldeNaam())
                && isGeslachtIdentiek(eerderKind.getGeslachtsaanduiding(), kind.getGeslachtsaanduiding()))

            {
                return true;
            }
        }
        return false;
    }

    /**
     * .
     *
     * @param een .
     * @param twee .
     * @return .
     */
    private boolean isGeboorteIdentiek(final PersoonGeboorteGroep een, final PersoonGeboorteGroep twee) {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een || (een == twee) || isAttribuutIdentiek(een.getDatumGeboorte(), twee.getDatumGeboorte());
        }
    }

    /**
     * .
     *
     * @param een .
     * @param twee .
     * @return .
     */
    private boolean isGeslachtIdentiek(final PersoonGeslachtsaanduidingGroep een,
            final PersoonGeslachtsaanduidingGroep twee)
    {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een || een == twee || een.getGeslachtsaanduiding().equals(twee.getGeslachtsaanduiding());
        }
    }

    /**
     * .
     *
     * @param een .
     * @param twee .
     * @return .
     */
    private boolean isSamengesteldeNaamIdentiek(final PersoonSamengesteldeNaamGroep een,
            final PersoonSamengesteldeNaamGroep twee)
    {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een
                || (een == twee)
                || (isAttribuutIdentiek(een.getVoornamen(), twee.getVoornamen())
                    && isAttribuutIdentiek(een.getGeslachtsnaam(), twee.getGeslachtsnaam())
                    && isAttribuutIdentiek(een.getScheidingsteken(), twee.getScheidingsteken()) && isAttribuutIdentiek(
                        een.getVoorvoegsel(), twee.getVoorvoegsel()));
        }
    }

    /**
     * .
     *
     * @param een .
     * @param twee .
     * @return .
     */
    private boolean isAttribuutIdentiek(final AbstractGegevensAttribuutType een,
            final AbstractGegevensAttribuutType twee)
    {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een || (een == twee) || een.equals(twee);
        }
    }
}
