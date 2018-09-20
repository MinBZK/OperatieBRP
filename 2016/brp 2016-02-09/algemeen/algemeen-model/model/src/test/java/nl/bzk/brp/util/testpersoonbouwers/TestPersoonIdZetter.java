/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.testpersoonbouwers;

import java.util.Random;
import java.util.Set;
import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AbstractOuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Hulpklasse om ids te zetten op objecten en voorkomens
 */
public final class TestPersoonIdZetter {

    private static final String ID = "iD";

    private TestPersoonIdZetter() {
        // private constructor
    }

    public static void zetIds(final PersoonHisVolledigImpl persoon) {

        zetObjectIntegerIds(persoon.getVoornamen());
        for (final PersoonVoornaamHisVolledigImpl voornaamHisVolledig : persoon.getVoornamen()) {
            zetVoorkomenIntegerIds(voornaamHisVolledig.getPersoonVoornaamHistorie());
        }
        zetObjectIntegerIds(persoon.getGeslachtsnaamcomponenten());
        for (final PersoonGeslachtsnaamcomponentHisVolledigImpl persoonGeslachtsnaamcomponentHisVolledig : persoon.getGeslachtsnaamcomponenten()) {
            zetVoorkomenIntegerIds(persoonGeslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie());
        }
        zetObjectIntegerIds(persoon.getAdressen());
        for (final PersoonAdresHisVolledigImpl persoonAdresHisVolledig : persoon.getAdressen()) {
            zetVoorkomenIntegerIds(persoonAdresHisVolledig.getPersoonAdresHistorie());
        }
        zetObjectIntegerIds(persoon.getNationaliteiten());
        for (final PersoonNationaliteitHisVolledigImpl persoonNationaliteitHisVolledig : persoon.getNationaliteiten()) {
            zetVoorkomenIntegerIds(persoonNationaliteitHisVolledig.getPersoonNationaliteitHistorie());
        }
        zetObjectIntegerIds(persoon.getReisdocumenten());
        for (final PersoonReisdocumentHisVolledigImpl persoonReisdocumentHisVolledig : persoon.getReisdocumenten()) {
            zetVoorkomenIntegerIds(persoonReisdocumentHisVolledig.getPersoonReisdocumentHistorie());
        }
        zetObjectIntegerIds(persoon.getIndicaties());
        for (final PersoonIndicatieHisVolledigImpl persoonIndicatieHisVolledig : persoon.getIndicaties()) {
            zetVoorkomenIntegerIds(persoonIndicatieHisVolledig.getPersoonIndicatieHistorie());
        }
        zetObjectIntegerIds(persoon.getVerstrekkingsbeperkingen());
        for (final PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperkingHisVolledig : persoon.getVerstrekkingsbeperkingen()) {
            zetVoorkomenIntegerIds(persoonVerstrekkingsbeperkingHisVolledig.getPersoonVerstrekkingsbeperkingHistorie());
        }
        zetObjectLongIds(persoon.getVerificaties());
        for (final PersoonVerificatieHisVolledigImpl persoonVerificatieHisVolledig : persoon.getVerificaties()) {
            zetVoorkomenLongIds(persoonVerificatieHisVolledig.getPersoonVerificatieHistorie());
        }

        zetObjectIntegerIds(persoon.getOnderzoeken());
        for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoekHisVolledig : persoon.getOnderzoeken()) {
            zetVoorkomenIntegerIds(persoonOnderzoekHisVolledig.getPersoonOnderzoekHistorie());
        }


        zetVoorkomenIntegerIds(persoon.getPersoonDeelnameEUVerkiezingenHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonGeslachtsaanduidingHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonIdentificatienummersHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonInschrijvingHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonMigratieHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonNaamgebruikHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonNummerverwijzingHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonOverlijdenHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonPersoonskaartHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonSamengesteldeNaamHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonUitsluitingKiesrechtHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonVerblijfsrechtHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonGeboorteHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonBijhoudingHistorie());
        zetVoorkomenIntegerIds(persoon.getPersoonAfgeleidAdministratiefHistorie());

        zetObjectIntegerIds(persoon.getBetrokkenheden());
        for (final BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : persoon.getBetrokkenheden()) {

            zetRandomIntegerId(betrokkenheidHisVolledig.getRelatie());
            zetVoorkomenIntegerIds(betrokkenheidHisVolledig.getRelatie().getRelatieHistorie());

            zetVoorkomenIntegerIds(betrokkenheidHisVolledig.getBetrokkenheidHistorie());
            if (betrokkenheidHisVolledig instanceof AbstractOuderHisVolledigImpl) {
                final AbstractOuderHisVolledigImpl betrokkenheidHisVolledig1 = (AbstractOuderHisVolledigImpl) betrokkenheidHisVolledig;
                zetVoorkomenIntegerIds(betrokkenheidHisVolledig1.getOuderOuderlijkGezagHistorie());
                zetVoorkomenIntegerIds(betrokkenheidHisVolledig1.getOuderOuderschapHistorie());
            } else if (betrokkenheidHisVolledig instanceof KindHisVolledigImpl) {
                zetObjectIntegerIds(betrokkenheidHisVolledig.getRelatie().getBetrokkenheden());
                for (OuderHisVolledigImpl ouderHisVolledig : betrokkenheidHisVolledig.getRelatie().getOuderBetrokkenheden()) {
                    zetVoorkomenIntegerIds(ouderHisVolledig.getOuderOuderlijkGezagHistorie());
                    zetVoorkomenIntegerIds(ouderHisVolledig.getOuderOuderschapHistorie());
                }
            }
        }

        for (final AdministratieveHandelingHisVolledigImpl administratieveHandelingHisVolledig : persoon.getAdministratieveHandelingen()) {
            zetRandomLongId(administratieveHandelingHisVolledig);
            for (final ActieHisVolledigImpl actieHisVolledig : administratieveHandelingHisVolledig.getActies()) {
                for (final ActieBronHisVolledigImpl actieBronHisVolledig : actieHisVolledig.getBronnen()) {
                    zetRandomLongId(actieBronHisVolledig);
                    if (actieBronHisVolledig.getDocument() != null) {
                        zetRandomLongId(actieBronHisVolledig.getDocument());
                        zetVoorkomenLongIds(actieBronHisVolledig.getDocument().getDocumentHistorie());
                    }
                }
            }
        }

        zetObjectLongIds(persoon.getAfnemerindicaties());
        for (final PersoonAfnemerindicatieHisVolledigImpl persoonAfnemerindicatieHisVolledig : persoon.getAfnemerindicaties()) {
            zetVoorkomenLongIds(persoonAfnemerindicatieHisVolledig.getPersoonAfnemerindicatieHistorie());
        }
    }

    public static void zetObjectIntegerIds(final Set<? extends HisVolledigImpl> hisVolledigSet) {
        for (final HisVolledigImpl hisVolledig : hisVolledigSet) {
            zetRandomIntegerId(hisVolledig);
        }
    }

    public static void zetObjectLongIds(final Set<? extends HisVolledigImpl> hisVolledigSet) {
        for (final HisVolledigImpl hisVolledig : hisVolledigSet) {
            zetRandomLongId(hisVolledig);
        }
    }

    public static void zetVoorkomenIntegerIds(final HistorieSet fhs) {
        for (final Object next : fhs) {
            zetRandomIntegerId(next);
        }
    }

    public static void zetVoorkomenLongIds(final HistorieSet fhs) {
        for (final Object next : fhs) {
            zetRandomLongId(next);
        }
    }

    public static void zetRandomIntegerId(final Object object) {
        ReflectionTestUtils.setField(object, ID, new Random().nextInt(10000));
    }

    public static void zetRandomLongId(final Object object) {
        final Integer random = new Random().nextInt(10000);
        ReflectionTestUtils.setField(object, ID, random.longValue());
    }

    public static void zetId(final Object object, Object idWaarde) {
        ReflectionTestUtils.setField(object, ID, idWaarde);
    }
}
