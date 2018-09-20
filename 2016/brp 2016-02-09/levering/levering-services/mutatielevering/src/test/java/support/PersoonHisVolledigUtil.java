/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.util.hisvolledig.kern.DocumentHisVolledigImplBuilder;

import org.springframework.test.util.ReflectionTestUtils;

public class PersoonHisVolledigUtil {

    private PersoonHisVolledigUtil() {
        // Niet instantieerbaar
    }

    public static void maakVerantwoording(final PersoonHisVolledig persoon, final ActieModel... acties) {
        final List<AdministratieveHandelingHisVolledigImpl> handelingen = new ArrayList<>();

        for (final ActieModel actie : acties) {
            final AdministratieveHandelingHisVolledigImpl handeling = maakAdministratieveHandelingHisVolledigImpl(actie.getAdministratieveHandeling());
            handelingen.add(handeling);
        }

        ((PersoonHisVolledigImpl) persoon).vulAanMetAdministratieveHandelingen(handelingen);
    }

    private static AdministratieveHandelingHisVolledigImpl maakAdministratieveHandelingHisVolledigImpl(
        final AdministratieveHandelingModel administratieveHandeling)
    {
        final AdministratieveHandelingHisVolledigImpl result =
                new AdministratieveHandelingHisVolledigImpl(
                    administratieveHandeling.getSoort(),
                    administratieveHandeling.getPartij(),
                    administratieveHandeling.getToelichtingOntlening(),
                    administratieveHandeling.getTijdstipRegistratie());
        ReflectionTestUtils.setField(result, "iD", administratieveHandeling.getID());

        if (result.getActies() != null) {
            final Set<ActieHisVolledigImpl> acties = new HashSet<>();

            for (final ActieModel actie : administratieveHandeling.getActies()) {
                acties.add(maakActieHisVolledig(result, actie));
            }

            result.setActies(acties);
        }

        return result;
    }

    private static ActieHisVolledigImpl maakActieHisVolledig(final AdministratieveHandelingHisVolledigImpl administratieveHandeling, final ActieModel actie)
    {
        final ActieHisVolledigImpl result =
                new ActieHisVolledigImpl(
                    actie.getSoort(),
                    administratieveHandeling,
                    actie.getPartij(),
                    actie.getDatumAanvangGeldigheid(),
                    actie.getDatumEindeGeldigheid(),
                    actie.getTijdstipRegistratie(),
                    actie.getDatumOntlening());
        ReflectionTestUtils.setField(result, "iD", actie.getID());

        // BRONNEN
        if (actie.getBronnen() != null) {
            for (final ActieBronModel bron : actie.getBronnen()) {
                result.getBronnen().add(maakActieBronHisVolledig(actie, result, bron));
            }
        }

        return result;
    }

    private static ActieBronHisVolledigImpl maakActieBronHisVolledig(
        final ActieModel actie,
        final ActieHisVolledigImpl actieHisVolledig,
        final ActieBronModel bron)
    {
        final ActieBronHisVolledigImpl result =
                new ActieBronHisVolledigImpl(
                    actieHisVolledig,
                    maakDocumentHisVolledig(actie, bron.getDocument()),
                    bron.getRechtsgrond(),
                    bron.getRechtsgrondomschrijving());
        ReflectionTestUtils.setField(result, "iD", bron.getID());
        return result;
    }

    private static DocumentHisVolledigImpl maakDocumentHisVolledig(final ActieModel actie, final DocumentModel document) {
        if (document == null) {
            return null;
        }
        final DocumentHisVolledigImplBuilder builder = new DocumentHisVolledigImplBuilder(document.getSoort().getWaarde());
        builder.nieuwStandaardRecord(actie)
               .aktenummer(document.getStandaard().getAktenummer())
               .identificatie(document.getStandaard().getIdentificatie())
               .omschrijving(document.getStandaard().getOmschrijving())
               .partij(document.getStandaard().getPartij().getWaarde())
               .eindeRecord();

        final DocumentHisVolledigImpl result = builder.build();
        ReflectionTestUtils.setField(result, "iD", document.getID());
        return result;
    }

    public static Partij maakPartij() {
        return TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak();
    }

    public static ActieModel maakActie(
        final Long id,
        final SoortAdministratieveHandeling soortHandeling,
        final SoortActie soortActie,
        final Integer datumRegistratie,
        final Integer datumAanvang,
        final Integer datumEinde,
        final Partij partij)
    {
        final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        final Date tijdstipRegistratie;
        try {
            tijdstipRegistratie = format.parse(Integer.toString(datumRegistratie));
        } catch (final ParseException e) {
            throw new IllegalArgumentException(datumRegistratie + " is niet een geldig datum.", e);
        }

        final AdministratieveHandelingModel administratieveHandeling =
                new AdministratieveHandelingModel(
                    new SoortAdministratieveHandelingAttribuut(soortHandeling),
                    new PartijAttribuut(partij),
                    null,
                    new DatumTijdAttribuut(tijdstipRegistratie));
        ReflectionTestUtils.setField(administratieveHandeling, "iD", id);

        final ActieModel actie =
                new ActieModel(
                    new SoortActieAttribuut(soortActie),
                    administratieveHandeling,
                    new PartijAttribuut(partij),
                    new DatumEvtDeelsOnbekendAttribuut(datumAanvang),
                    datumEinde == null ? null : new DatumEvtDeelsOnbekendAttribuut(datumEinde),
                    new DatumTijdAttribuut(tijdstipRegistratie),
                    datumEinde == null ? new DatumEvtDeelsOnbekendAttribuut(datumAanvang) : new DatumEvtDeelsOnbekendAttribuut(datumEinde));
        ReflectionTestUtils.setField(actie, "iD", id);
        administratieveHandeling.getActies().add(actie);
        return actie;
    }

    public static ActieModel maakActie(
        final Long id,
        final SoortAdministratieveHandeling soortHandeling,
        final SoortActie soortActie,
        final int tijdstip,
        final Partij partij)
    {
        return maakActie(id, soortHandeling, soortActie, tijdstip + 1, tijdstip, null, partij);
    }

    public static ActieModel[] geefAlleActies(final PersoonHisVolledig persoon) {
        final Map<Long, ActieModel> alleActies = new HashMap<>();

        bepaalActies(alleActies, persoon.getPersoonAfgeleidAdministratiefHistorie());
        bepaalActies(alleActies, persoon.getPersoonBijhoudingHistorie());
        bepaalActies(alleActies, persoon.getPersoonDeelnameEUVerkiezingenHistorie());
        bepaalActies(alleActies, persoon.getPersoonGeboorteHistorie());
        bepaalActies(alleActies, persoon.getPersoonGeslachtsaanduidingHistorie());
        bepaalActies(alleActies, persoon.getPersoonIdentificatienummersHistorie());
        bepaalActies(alleActies, persoon.getPersoonInschrijvingHistorie());
        bepaalActies(alleActies, persoon.getPersoonMigratieHistorie());
        bepaalActies(alleActies, persoon.getPersoonNaamgebruikHistorie());
        bepaalActies(alleActies, persoon.getPersoonNummerverwijzingHistorie());
        bepaalActies(alleActies, persoon.getPersoonOverlijdenHistorie());
        bepaalActies(alleActies, persoon.getPersoonPersoonskaartHistorie());
        bepaalActies(alleActies, persoon.getPersoonSamengesteldeNaamHistorie());
        bepaalActies(alleActies, persoon.getPersoonUitsluitingKiesrechtHistorie());
        bepaalActies(alleActies, persoon.getPersoonVerblijfsrechtHistorie());

        for (final PersoonVoornaamHisVolledig voornaam : persoon.getVoornamen()) {
            bepaalActies(alleActies, voornaam.getPersoonVoornaamHistorie());
        }

        for (final PersoonGeslachtsnaamcomponentHisVolledig geslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
            bepaalActies(alleActies, geslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorie());
        }

        for (final PersoonIndicatieHisVolledig<?> indicatie : persoon.getIndicaties()) {
            bepaalActies(alleActies, indicatie.getPersoonIndicatieHistorie());
        }

        for (final PersoonVerificatieHisVolledig verificatie : persoon.getVerificaties()) {
            bepaalActies(alleActies, verificatie.getPersoonVerificatieHistorie());
        }

        final PersoonAdresHisVolledig adres = persoon.getAdressen().iterator().next();
        bepaalActies(alleActies, adres.getPersoonAdresHistorie());

        for (final PersoonNationaliteitHisVolledig nationaliteit : persoon.getNationaliteiten()) {
            bepaalActies(alleActies, nationaliteit.getPersoonNationaliteitHistorie());
        }
        for (final PersoonReisdocumentHisVolledig reisdocument : persoon.getReisdocumenten()) {
            bepaalActies(alleActies, reisdocument.getPersoonReisdocumentHistorie());
        }
        // Verwerk ouders
        final KindHisVolledig mijnKindBetrokkenheid = persoon.getKindBetrokkenheid();
        if (mijnKindBetrokkenheid != null) {
            final RelatieHisVolledig mijnKindRelatie = mijnKindBetrokkenheid.getRelatie();

            bepaalActies(alleActies, mijnKindBetrokkenheid.getBetrokkenheidHistorie());
            bepaalActies(alleActies, mijnKindRelatie.getRelatieHistorie());

            for (final OuderHisVolledig gerelateerdeOuderBetrokkenheid : mijnKindRelatie.getOuderBetrokkenheden()) {
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getBetrokkenheidHistorie());
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getOuderOuderlijkGezagHistorie());
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getOuderOuderschapHistorie());
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie());
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie());
                bepaalActies(alleActies, gerelateerdeOuderBetrokkenheid.getPersoon().getPersoonGeboorteHistorie());
            }
        }

        // Verwerk kinderen
        for (final OuderHisVolledig mijnOuderBetrokkenheid : persoon.getOuderBetrokkenheden()) {
            final RelatieHisVolledig mijnOuderRelatie = mijnOuderBetrokkenheid.getRelatie();
            final KindHisVolledig gerelateerdeKindBetrokkenheid = mijnOuderRelatie.getKindBetrokkenheid();

            bepaalActies(alleActies, mijnOuderBetrokkenheid.getBetrokkenheidHistorie());
            bepaalActies(alleActies, mijnOuderRelatie.getRelatieHistorie());
            bepaalActies(alleActies, gerelateerdeKindBetrokkenheid.getBetrokkenheidHistorie());
            bepaalActies(alleActies, gerelateerdeKindBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie());
            bepaalActies(alleActies, gerelateerdeKindBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie());
            bepaalActies(alleActies, gerelateerdeKindBetrokkenheid.getPersoon().getPersoonGeboorteHistorie());
            bepaalActies(alleActies, gerelateerdeKindBetrokkenheid.getPersoon().getPersoonGeslachtsaanduidingHistorie());
        }

        // Verwerken huwelijken/gerelateerd partnerschappen
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig mijnPartnerRelatie : persoon.getHuwelijkGeregistreerdPartnerschappen()) {
            final PartnerHisVolledig gerelateerdePersoon = mijnPartnerRelatie.geefPartnerVan(persoon);

            bepaalActies(alleActies, mijnPartnerRelatie.getRelatieHistorie());
            bepaalActies(alleActies, gerelateerdePersoon.getPersoon().getPersoonIdentificatienummersHistorie());
            bepaalActies(alleActies, gerelateerdePersoon.getPersoon().getPersoonSamengesteldeNaamHistorie());
            bepaalActies(alleActies, gerelateerdePersoon.getPersoon().getPersoonGeboorteHistorie());
            bepaalActies(alleActies, gerelateerdePersoon.getPersoon().getPersoonGeslachtsaanduidingHistorie());
        }

        return alleActies.values().toArray(new ActieModel[] {});

    }

    private static void bepaalActies(final Map<Long, ActieModel> alleActies, final HistorieSet<?> historieSet) {
        for (final HistorieEntiteit entiteit : historieSet) {
            if (entiteit instanceof FormeelVerantwoordbaar<?>) {
                final Object verantwoordingInhoud = ((FormeelVerantwoordbaar<?>) entiteit).getVerantwoordingInhoud();
                if (verantwoordingInhoud instanceof ActieModel) {
                    alleActies.put(((ActieModel) verantwoordingInhoud).getID(), (ActieModel) verantwoordingInhoud);
                }
                final Object verantwoordingVerval = ((FormeelVerantwoordbaar<?>) entiteit).getVerantwoordingVerval();
                if (verantwoordingVerval instanceof ActieModel) {
                    alleActies.put(((ActieModel) verantwoordingVerval).getID(), (ActieModel) verantwoordingVerval);
                }
            }
            if (entiteit instanceof MaterieelVerantwoordbaar<?>) {
                final Object verantwoordingEindeGeldigheid = ((MaterieelVerantwoordbaar<?>) entiteit).getVerantwoordingAanpassingGeldigheid();
                if (verantwoordingEindeGeldigheid instanceof ActieModel) {
                    alleActies.put(((ActieModel) verantwoordingEindeGeldigheid).getID(), (ActieModel) verantwoordingEindeGeldigheid);
                }
            }
            if (entiteit instanceof VerantwoordingTbvLeveringMutaties) {
                final ActieModel verantwoordingVervalTbvLeveringMutaties =
                        ((VerantwoordingTbvLeveringMutaties) entiteit).getVerantwoordingVervalTbvLeveringMutaties();
                if (verantwoordingVervalTbvLeveringMutaties != null) {
                    alleActies.put(verantwoordingVervalTbvLeveringMutaties.getID(), verantwoordingVervalTbvLeveringMutaties);
                }
            }
        }
    }
}
