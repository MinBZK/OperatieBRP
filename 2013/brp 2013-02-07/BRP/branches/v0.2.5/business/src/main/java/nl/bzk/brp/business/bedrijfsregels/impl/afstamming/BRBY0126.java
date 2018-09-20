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
import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.logisch.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.logisch.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.RelatieUtils;

/**
 * Implementatie @brp.bedrijfsregel BRBY0126: Kind met zelfde gegevens mag niet al geregristreerd zijn in GBA.
 */
public class BRBY0126 implements ActieBedrijfsRegel<Relatie> {

    @Inject
    PersoonRepository persoonRepository;

    @Override
    public String getCode() {
        return "BRBY0126";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();

        Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
        if (kind != null) {
            for (Persoon ouderBericht : RelatieUtils.haalOudersUitRelatie(nieuweSituatie)) {
                final PersoonModel ouder = persoonRepository.findByBurgerservicenummer(
                        ouderBericht.getIdentificatienummers().getBurgerservicenummer());
                if (ouder != null) {
                    if (isKindAlEerderGeregistreerd(ouder, kind)) {
                        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR,
                                                  MeldingCode.BRBY0126,
                                                  (Identificeerbaar) kind, "kind"));
                        break;
                    }
                }
            }
        }
        return meldingen;
    }

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

    private boolean isGeboorteIdentiek(final PersoonGeboorteGroep een, final PersoonGeboorteGroep twee) {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een || (een == twee) || isAttribuutIdentiek(een.getDatumGeboorte(), twee.getDatumGeboorte());
        }
    }

    private boolean isGeslachtIdentiek(final PersoonGeslachtsaanduidingGroep een,
                                       final PersoonGeslachtsaanduidingGroep twee)
    {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een || een == twee || een.getGeslachtsaanduiding().equals(twee.getGeslachtsaanduiding());
        }
    }

    private boolean isSamengesteldeNaamIdentiek(PersoonSamengesteldeNaamGroep een, PersoonSamengesteldeNaamGroep twee) {
        if ((null == een && null != twee) || (null != een && null == twee)) {
            return false;
        } else {
            return null == een
                    || (een == twee)
                    || (isAttribuutIdentiek(een.getVoornamen(), twee.getVoornamen())
                    && isAttribuutIdentiek(een.getGeslachtsnaam(), twee.getGeslachtsnaam())
                    && isAttribuutIdentiek(een.getScheidingsteken(), twee.getScheidingsteken())
                    && isAttribuutIdentiek(een.getVoorvoegsel(), twee.getVoorvoegsel()));
        }
    }

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
