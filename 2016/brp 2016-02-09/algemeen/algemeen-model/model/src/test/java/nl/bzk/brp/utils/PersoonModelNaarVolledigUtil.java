/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.OuderModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;

import org.springframework.test.util.ReflectionTestUtils;


/**
 * Helper klasse voor het omzetten van een {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie naar een
 * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
 */
public final class PersoonModelNaarVolledigUtil {

    private static final DatumEvtDeelsOnbekendAttribuut STANDAARD_DATUM_AANVANGGELDIGHEID =
            new DatumEvtDeelsOnbekendAttribuut(
                    20000101);
    private static final DatumTijdAttribuut             STANDAARD_TIJDSTIP_REGISTRATIE    = new DatumTijdAttribuut(
            new Date());

    /**
     * Lege constructor zoals vereist voor utility classes met alleen static methodes.
     */
    private PersoonModelNaarVolledigUtil() {

    }

    /**
     * Zet de opgegeven {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie om naar een
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie waarbij
     * alle informatie wordt gekopieerd en de datum aanvang geldigheid en het tijdstip registratie voor alle groepen
     * op een standaard waarde in het verleden worden gezet.
     * <p>
     * Merk op dat lang niet alle groepen en onderliggende object (als nationaliteiten etc.) over worden gezet. Alleen
     * die zaken die nodig zijn in de tests die deze klasse gebruiken worden overgezet. Uiteraard kan dat worden
     * uitgebreid, maar vooralsnog wordt dus nog lang niet alles overgezet.
     * </p>
     *
     * @param persoonModel            de persoon die omgezet dient te worden.
     * @param inclusiefBetrokkenheden een parameter die aangeeft of ook de betrokkenheden van de persoon omgezet
     *                                dienen te worden.
     * @return een gevulde {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
     */
    public static PersoonHisVolledigImpl zetPersoonOm(final PersoonModel persoonModel,
            final boolean inclusiefBetrokkenheden)
    {
        return zetPersoonOm(persoonModel, inclusiefBetrokkenheden, STANDAARD_DATUM_AANVANGGELDIGHEID,
                STANDAARD_TIJDSTIP_REGISTRATIE);
    }

    /**
     * Zet de opgegeven {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie om naar een
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie waarbij
     * alle informatie wordt gekopieerd en de datum aanvang geldigheid en het tijdstip registratie voor alle groepen
     * op de opgegeven waardes worden gezet.
     * <p>
     * Merk op dat lang niet alle groepen en onderliggende object (als nationaliteiten etc.) over worden gezet. Alleen
     * die zaken die nodig zijn in de tests die deze klasse gebruiken worden overgezet. Uiteraard kan dat worden
     * uitgebreid, maar vooralsnog wordt dus nog lang niet alles overgezet.
     * </p>
     *
     * @param persoonModel            de persoon die omgezet dient te worden.
     * @param inclusiefBetrokkenheden een parameter die aangeeft of ook de betrokkenheden van de persoon omgezet
     *                                dienen te worden.
     * @return een gevulde {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
     */
    public static PersoonHisVolledigImpl zetPersoonOm(final PersoonModel persoonModel,
            final boolean inclusiefBetrokkenheden, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
            final DatumTijdAttribuut tijdstipRegistratie)
    {
        final PersoonHisVolledigImpl persoonVolledig = new PersoonHisVolledigImpl(persoonModel.getSoort());
        ReflectionTestUtils.setField(persoonVolledig, "iD", persoonModel.getID());

        final ActieModel actie =
                new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), null, null, datumAanvangGeldigheid, null,
                        tijdstipRegistratie, null);
        final MaterieleHistorieImpl historie = new MaterieleHistorieImpl();
        // historie.setActieInhoud(actie);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumTijdRegistratie(tijdstipRegistratie);

        zetPersoonGroepenOm(persoonModel, persoonVolledig, actie, historie);
        zetPersoonObjectenOm(persoonModel, persoonVolledig, actie, historie);

        if (inclusiefBetrokkenheden) {
            zetPersoonBetrokkenhedenOm(persoonModel, persoonVolledig, actie, historie);
        }

        persoonVolledig.leidALaagAf();
        return persoonVolledig;
    }

    /**
     * Zet alle groepen uit de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie om naar de
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
     * Hierbij wordt voor elke groep een historie instantie aangemaakt en toegevoegd met opgegeven historie en
     * actie inhoud.
     *
     * @param persoonModel    het persoon model waarvan de groepen moeten worden overgezet.
     * @param persoonVolledig de persoon volledig waarnaar de groepen worden overgezet.
     * @param actie           de actie die gebruikt wordt voor de inhoud van de groep (verantwoording)
     * @param historie        de historie voor elke groep.
     */
    private static void zetPersoonGroepenOm(final PersoonModel persoonModel,
            final PersoonHisVolledigImpl persoonVolledig, final ActieModel actie, final MaterieleHistorieImpl historie)
    {
        // Afgeleid administratief
        if (persoonModel.getAfgeleidAdministratief() != null) {
            persoonVolledig.getPersoonAfgeleidAdministratiefHistorie().voegToe(
                    new HisPersoonAfgeleidAdministratiefModel(persoonVolledig,
                            persoonModel.getAfgeleidAdministratief(), actie));
        }
        // Identificatienummers
        if (persoonModel.getIdentificatienummers() != null) {
            persoonVolledig.getPersoonIdentificatienummersHistorie().voegToe(
                    new HisPersoonIdentificatienummersModel(persoonVolledig, persoonModel.getIdentificatienummers(),
                            historie, actie));
        }
        // Samengesteldenaam
        if (persoonModel.getSamengesteldeNaam() != null) {
            persoonVolledig.getPersoonSamengesteldeNaamHistorie().voegToe(
                    new HisPersoonSamengesteldeNaamModel(persoonVolledig, persoonModel.getSamengesteldeNaam(),
                            historie, actie));
        }
        // Geboorte
        if (persoonModel.getGeboorte() != null) {
            persoonVolledig.getPersoonGeboorteHistorie().voegToe(
                    new HisPersoonGeboorteModel(persoonVolledig, persoonModel.getGeboorte(), actie));
        }
        // Geslachtsaanduiding
        if (persoonModel.getGeslachtsaanduiding() != null) {
            persoonVolledig.getPersoonGeslachtsaanduidingHistorie().voegToe(
                    new HisPersoonGeslachtsaanduidingModel(persoonVolledig, persoonModel.getGeslachtsaanduiding(),
                            historie, actie));
        }
    }

    /**
     * Zet alle 1-n relaties (dus alle objecten) van een persoon (bijv. adressen) om van de opgegeven
     * {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie naar de opgegeven
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
     *
     * @param persoonModel    het persoon model waarvan de objecten moeten worden omgezet.
     * @param persoonVolledig het volledige persoon waar de omgezette objecten aan toegevoegd moeten worden.
     * @param actie           de actie die gebruikt wordt voor de inhoud van de groep (verantwoording)
     * @param historie        de historie voor elke groep.
     */
    private static void zetPersoonObjectenOm(final PersoonModel persoonModel,
            final PersoonHisVolledigImpl persoonVolledig, final ActieModel actie, final MaterieleHistorieImpl historie)
    {
        zetAdressenOm(persoonModel, persoonVolledig, actie, historie);
    }

    /**
     * Zet alle adressen van de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie om naar
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl} instantie
     * en voegt deze toe aan de opgegeven {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl}
     * instantie.
     *
     * @param persoonModel    het persoon model waarvan de adressen moeten worden omgezet.
     * @param persoonVolledig het volledige persoon waar de omgezette adressen aan toegevoegd moeten worden.
     * @param actie           de actie die gebruikt wordt voor de inhoud van de groep (verantwoording)
     * @param historie        de historie voor elke groep.
     */
    private static void zetAdressenOm(final PersoonModel persoonModel, final PersoonHisVolledigImpl persoonVolledig,
            final ActieModel actie, final MaterieleHistorieImpl historie)
    {
        if (persoonModel.getAdressen() != null) {
            for (PersoonAdresModel adresModel : persoonModel.getAdressen()) {
                final PersoonAdresHisVolledigImpl adresVolledig = new PersoonAdresHisVolledigImpl(persoonVolledig);
                ReflectionTestUtils.setField(adresVolledig, "iD", adresModel.getID());

                adresVolledig.getPersoonAdresHistorie().voegToe(
                        new HisPersoonAdresModel(adresVolledig, adresModel.getStandaard(), historie, actie));
                adresVolledig.leidALaagAf();

                persoonVolledig.getAdressen().add(adresVolledig);
            }
        }
    }

    /**
     * Zet alle betrokkenheden van de {@link nl.bzk.brp.model.operationeel.kern.PersoonModel} instantie om naar
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl} instantie en voegt deze toe aan de
     * opgegeven {@link nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl} instantie.
     *
     * @param persoonModel    het persoon model waarvan de betrokkenheden moeten worden omgezet.
     * @param persoonVolledig het volledige persoon waar de omgezette betrokkenheden aan toegevoegd moeten worden.
     * @param actie           de actie die gebruikt wordt voor de inhoud/verantwoording.
     * @param historie        de historie voor elke groep/his record.
     */
    private static void zetPersoonBetrokkenhedenOm(final PersoonModel persoonModel,
            final PersoonHisVolledigImpl persoonVolledig, final ActieModel actie, final MaterieleHistorieImpl historie)
    {
        final Map<Relatie, RelatieHisVolledigImpl> relaties = new HashMap<Relatie, RelatieHisVolledigImpl>();

        for (BetrokkenheidModel betrokkenheidModel : persoonModel.getBetrokkenheden()) {
            final RelatieModel relatieModel = betrokkenheidModel.getRelatie();
            final RelatieHisVolledigImpl relatieVolledig = getRelatieUitRelatiesOfNieuw(relatieModel, relaties);

            if (relatieVolledig != null) {
                final BetrokkenheidHisVolledigImpl betrokkenheidVolledig =
                        zetBetrokkenheidOm(betrokkenheidModel, persoonVolledig, relatieVolledig, actie, historie);
                if (betrokkenheidVolledig != null) {
                    persoonVolledig.getBetrokkenheden().add(betrokkenheidVolledig);
                    relatieVolledig.getBetrokkenheden().add(betrokkenheidVolledig);
                }
            }
        }

        // ook de overige betrokkenen in de relaties toevoegen
        for (Map.Entry<Relatie, RelatieHisVolledigImpl> relatieEntry : relaties.entrySet()) {
            for (Betrokkenheid betrokkenheidModel : relatieEntry.getKey().getBetrokkenheden()) {
                if (betrokkenheidModel.getPersoon() != persoonModel) {
                    PersoonHisVolledigImpl andereBetrokkene =
                            zetPersoonOm((PersoonModel) betrokkenheidModel.getPersoon(), false);
                    BetrokkenheidHisVolledigImpl betrokkenheidVolledig =
                            zetBetrokkenheidOm((BetrokkenheidModel) betrokkenheidModel, andereBetrokkene,
                                    relatieEntry.getValue(), actie, historie);

                    if (betrokkenheidVolledig != null) {
                        andereBetrokkene.getBetrokkenheden().add(betrokkenheidVolledig);
                        relatieEntry.getValue().getBetrokkenheden().add(betrokkenheidVolledig);
                    }
                }
            }
        }
    }

    /**
     * Zet de opgegeven {@link nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel} instantie om naar een
     * {@link nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl} instantie.
     *
     * @param betrokkenheidModel de betrokkenheid model instantie die omgezet dient te worden.
     * @param persoonVolledig    de volledige persoon waarvoor de volledige betrokkenheid wordt aangemaakt.
     * @param relatieVolledig    de relatie waartoe de betrokkenheid behoort.
     * @param actie              de actie die gebruikt wordt voor de inhoud/verantwoording.
     * @param historie           de historie voor elke groep/his record.
     * @return de omgezette betrokkenheid.
     */
    private static BetrokkenheidHisVolledigImpl zetBetrokkenheidOm(final BetrokkenheidModel betrokkenheidModel,
            final PersoonHisVolledigImpl persoonVolledig, final RelatieHisVolledigImpl relatieVolledig,
            final ActieModel actie, final MaterieleHistorieImpl historie)
    {
        final BetrokkenheidHisVolledigImpl betrokkenheidVolledig;
        switch (betrokkenheidModel.getRol().getWaarde()) {
            case OUDER:
                betrokkenheidVolledig = new OuderHisVolledigImpl(relatieVolledig, persoonVolledig);
                if (((OuderModel) betrokkenheidModel).getOuderschap() != null) {
                    ((OuderHisVolledigImpl) betrokkenheidVolledig).getOuderOuderschapHistorie().voegToe(
                            new HisOuderOuderschapModel(betrokkenheidVolledig, ((OuderModel) betrokkenheidModel)
                                    .getOuderschap(), historie, actie));
                }
                break;
            case KIND:
                betrokkenheidVolledig = new KindHisVolledigImpl(relatieVolledig, persoonVolledig);
                break;
            default:
                betrokkenheidVolledig = null;
        }
        return betrokkenheidVolledig;
    }

    /**
     * Retourneert de {@link nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl} instantie behorende bij de
     * opgegeven {@link nl.bzk.brp.model.operationeel.kern.RelatieModel} instantie. Indien deze nog niet in de
     * <code>relaties</code> map zit, wordt deze eventueel aangemaakt.
     * <p>
     * Merk op dat het kan zijn dat er <code>null</code> geretourneerd wordt, indien er geen relatie gevonden is en ook,
     * geen nieuwe wordt aangemaakt (daar deze relatie nog niet wordt opgezet).
     * </p>
     *
     * @param relatieModel het relatie model waarvoor de volledige versie moet worden opgehaald.
     * @param relaties     de relaties map waarin de model varianten gemapt worden naar de volledige instanties.
     * @return de gevonden of opgebouwde (potentieel <code>null</code>) volledige relatie instantie.
     */
    private static RelatieHisVolledigImpl getRelatieUitRelatiesOfNieuw(final RelatieModel relatieModel,
            final Map<Relatie, RelatieHisVolledigImpl> relaties)
    {
        final RelatieHisVolledigImpl relatieVolledig;
        if (relaties.containsKey(relatieModel)) {
            relatieVolledig = relaties.get(relatieModel);
        } else {
            switch (relatieModel.getSoort().getWaarde()) {
                case FAMILIERECHTELIJKE_BETREKKING:
                    relatieVolledig = new FamilierechtelijkeBetrekkingHisVolledigImpl();
                    break;
                default:
                    relatieVolledig = null;
            }

            if (relatieVolledig != null) {
                ReflectionTestUtils.setField(relatieVolledig, "iD", relatieModel.getID());
                relaties.put(relatieModel, relatieVolledig);
            }
        }
        return relatieVolledig;
    }

}
