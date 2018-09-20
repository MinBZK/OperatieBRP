/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.GedeblokkeerdeMeldingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingGedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.GedeblokkeerdeMeldingModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Util klasse om snel verantwoordingsinformatie pojo's op te bouwen zoals acties, handelingen en bronnen.
 */
public final class VerantwoordingTestUtil {

    private static final String I_D = "iD";
    private static final String ACTIES = "acties";

    private VerantwoordingTestUtil() {
    }

    /**
     * Maak een administratieve handeling.
     *
     * @param soort                de soort
     * @param partij               de partij
     * @param ontleningstoelichting de toelichting ontlening
     * @param tijdstipRegistratie  tijdstip van registratie
     * @return een administratieve handeling
     */
    public static AdministratieveHandelingModel bouwAdministratieveHandeling(final SoortAdministratieveHandeling soort,
        final PartijAttribuut partij, final OntleningstoelichtingAttribuut ontleningstoelichting, final DatumTijdAttribuut tijdstipRegistratie)
    {
        return new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(soort), partij, ontleningstoelichting, tijdstipRegistratie);
    }

    /**
     * Voeg een gedeblokkeerde melding toe aan de administratieve handeling.
     *
     * @param admHnd  de administratieve handeling
     * @param regel   de regel voor de melding
     * @param admHandelingGedeblokkeerdeMeldingId
     *                een database is voor de gedeblokkeerde melding zodat deze gesorteerd kan worden
     * @return de toegevoegde gedeblokkeerde melding
     */
    public static GedeblokkeerdeMeldingModel voegGedeblokkeerdeMeldingToeAanHandeling(final AdministratieveHandelingModel admHnd, final Regel regel,
                                                                                      final Long admHandelingGedeblokkeerdeMeldingId)
    {
        final GedeblokkeerdeMeldingModel gedeblokkeerdeMeldingModel = maakGedeblokkeerdeMelding(regel);
        final AdministratieveHandelingGedeblokkeerdeMeldingModel amdHndGedeblokkeerdeMeldingModel =
            new AdministratieveHandelingGedeblokkeerdeMeldingModel(admHnd, gedeblokkeerdeMeldingModel);
        ReflectionTestUtils.setField(amdHndGedeblokkeerdeMeldingModel, I_D, admHandelingGedeblokkeerdeMeldingId);

        admHnd.getGedeblokkeerdeMeldingen().add(amdHndGedeblokkeerdeMeldingModel);
        return gedeblokkeerdeMeldingModel;
    }

    /**
     * Maak een gedeblokeerde melding.
     *
     * @param regel   de regel voor de melding
     * @return een gedeblokkeerde melding
     */
    public static GedeblokkeerdeMeldingModel maakGedeblokkeerdeMelding(final Regel regel) {
        return new GedeblokkeerdeMeldingModel(new RegelAttribuut(regel), new MeldingtekstAttribuut(
            regel.getOmschrijving()));
    }

    /**
     * Voeg een nieuwe actie toe aan een administratieve handeling.
     *
     * @param admHnd          de handeling
     * @param soort           de soort actie
     * @param partijAttribuut de partij
     * @param datumAanvangGel datum aanvang geldigheid actie
     * @param datumEindeGel   datum einde geldigheid actie
     * @param tijdstipReg     tijdstip registratie actie
     * @param datumOntlening  datum ontlening actie
     * @param actieId         de id van de actie zodat deze gesorteerd kan worden
     * @return de toegevoegde actie
     */
    public static ActieModel voegActieToeAanHandeling(final AdministratieveHandelingModel admHnd,
        final SoortActie soort, final PartijAttribuut partijAttribuut,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGel, final DatumEvtDeelsOnbekendAttribuut datumEindeGel,
        final DatumTijdAttribuut tijdstipReg, final DatumEvtDeelsOnbekendAttribuut datumOntlening,
        final Long actieId)
    {
        final ActieModel actie =
            maakActie(admHnd, soort, partijAttribuut, datumAanvangGel, datumEindeGel, tijdstipReg, datumOntlening,
                actieId);
        final Set<ActieModel> acties = (HashSet<ActieModel>) ReflectionTestUtils.getField(admHnd, ACTIES);
        acties.add(actie);
        ReflectionTestUtils.setField(admHnd, ACTIES, acties);
        return actie;
    }

    /**
     * Maak een actie en koppel deze aan een administratieve handeling.
     *
     * @param admHnd          de handeling waaraan de actie gekoppeld wordt
     * @param soort           de soort actie
     * @param partijAttribuut partij voor de actie
     * @param datumAanvangGel datun aanvang geldigheid actie
     * @param datumEindeGel   datun einde geldigheid actie
     * @param tijdstipReg     tijdstip registratie actie
     * @param datumOntlening  datun ontlening actie
     * @param actieID         database id voor de actie, benodigs voor sortering
     * @return de aangemaakte actie
     */
    public static ActieModel maakActie(final AdministratieveHandelingModel admHnd, final SoortActie soort,
        final PartijAttribuut partijAttribuut, final DatumEvtDeelsOnbekendAttribuut datumAanvangGel,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGel, final DatumTijdAttribuut tijdstipReg,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening, final Long actieID)
    {
        final ActieModel actieModel =
            new ActieModel(new SoortActieAttribuut(soort), admHnd, partijAttribuut, datumAanvangGel, datumEindeGel,
                tijdstipReg, datumOntlening);
        actieModel.setMagGeleverdWorden(true);
        actieModel.getSoort().setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(actieModel, I_D, actieID);
        return actieModel;
    }

    /**
     * Voeg een actiebron toe aan de actie met als bron een nieuw document.
     *
     * @param actie        de actie die gekoppeld wordt aan een bron
     * @param documentNaam naam van het document
     * @param documentId   database id van het document, benodigd voor sortering
     * @return het aangemaakte document
     */
    public static DocumentModel voegDocumentBronToeAanActie(final ActieModel actie, final String documentNaam,
        final Long documentId)
    {
        final DocumentModel document = maakDocument(documentNaam, documentId);
        final ActieBronModel actieBronModel = new ActieBronModel(actie, document, null, null);
        ReflectionTestUtils.setField(actieBronModel, I_D, documentId);
        actie.getBronnen().add(actieBronModel);
        return document;
    }

    /**
     * Koppel een bestaand document aan de actie via een actiebron.
     *
     * @param actie       de actie
     * @param eenDocument het document
     */
    public static void voegBestaandDocumentBronToeAanActie(final ActieModel actie, final DocumentModel eenDocument) {
        final ActieBronModel actieBronModel = new ActieBronModel(actie, eenDocument, null, null);
        ReflectionTestUtils.setField(actieBronModel, I_D, eenDocument.getID());
        actie.getBronnen().add(actieBronModel);
    }

    /**
     * Maak een nieuw document aan.
     *
     * @param documentNaam document naam
     * @param documentId   database is voor document, benodigd voor sortering
     * @return aangemaakte document.
     */
    public static DocumentModel maakDocument(final String documentNaam, final Long documentId) {
        final DocumentModel documentModel =
            new DocumentModel(StatischeObjecttypeBuilder.bouwSoortDocument(documentNaam));
        documentModel.setStandaard(new DocumentStandaardGroepModel(new DocumentIdentificatieAttribuut("id-attribuut"), new AktenummerAttribuut("aktenr1"),
            new DocumentOmschrijvingAttribuut("doc omschrijving"),
            new PartijAttribuut(TestPartijBuilder.maker().metCode(123).maak())));
        documentModel.getStandaard().getOmschrijving().setMagGeleverdWorden(true);
        documentModel.getStandaard().getIdentificatie().setMagGeleverdWorden(true);
        documentModel.getStandaard().getPartij().setMagGeleverdWorden(true);
        documentModel.getStandaard().getAktenummer().setMagGeleverdWorden(true);
        documentModel.getSoort().setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(documentModel, I_D, documentId);
        return documentModel;
    }

    /**
     * koppel een rechtsgrond als bron aan een actie middels een actiebron.
     *
     * @param actie                de actie
     * @param rechtsgrondAttribuut de rechtsgrond
     */
    public static void voegRechtsgrondBronToeAanActie(final ActieModel actie,
        final RechtsgrondAttribuut rechtsgrondAttribuut)
    {
        final ActieBronModel actieBronModel = new ActieBronModel(actie, null, rechtsgrondAttribuut, null);
        ReflectionTestUtils.setField(actieBronModel, I_D, rechtsgrondAttribuut.getWaarde().getID().longValue());
        actie.getBronnen().add(actieBronModel);
    }

    /**
     * Koppel een rechtsgrondomschijving aan een actie als bron middels een actiebron.
     *
     * @param actie                   de actie
     * @param rechtsgrondOmschrijving de rechtsgron omschrijving
     */
    public static void voegRechtsgrondOmschrijvingBronToeAanActie(final ActieModel actie,
        final String rechtsgrondOmschrijving)
    {
        final ActieBronModel actieBronModel =
            new ActieBronModel(actie, null, null,
                new OmschrijvingEnumeratiewaardeAttribuut(rechtsgrondOmschrijving));
        ReflectionTestUtils.setField(actieBronModel, I_D, (long) rechtsgrondOmschrijving.hashCode());
        actie.getBronnen().add(actieBronModel);
    }

    public static AdministratieveHandelingHisVolledigImpl converteer(final AdministratieveHandelingModel handeling) {

        final AdministratieveHandelingHisVolledigImpl handelingHisVolledig = new AdministratieveHandelingHisVolledigImpl(handeling.getSoort(),
            handeling.getPartij(), handeling.getToelichtingOntlening(), handeling.getTijdstipRegistratie());
        handelingHisVolledig.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(handelingHisVolledig, I_D, handeling.getID());

        for (final ActieModel actie : handeling.getActies()) {
            // LET OP: De attrbiuten van actie moeten gekopieerd worden! Geen references gebruikt ivm magGeleverdWorden vlag.
            SoortActieAttribuut srtActie = null;
            PartijAttribuut partij = null;
            DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid = null;
            DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = null;
            DatumEvtDeelsOnbekendAttribuut datumOntlening = null;
            if (AttribuutUtil.isNotBlank(actie.getSoort())) {
                srtActie = new SoortActieAttribuut(actie.getSoort().getWaarde());
                srtActie.setMagGeleverdWorden(actie.getSoort().isMagGeleverdWorden());
            }
            if (AttribuutUtil.isNotBlank(actie.getPartij())) {
                partij = new PartijAttribuut(actie.getPartij().getWaarde());
                partij.setMagGeleverdWorden(actie.getPartij().isMagGeleverdWorden());
            }
            if (AttribuutUtil.isNotBlank(actie.getDatumAanvangGeldigheid())) {
                datumAanvangGeldigheid = new DatumEvtDeelsOnbekendAttribuut(actie.getDatumAanvangGeldigheid().getWaarde());
                datumAanvangGeldigheid.setMagGeleverdWorden(actie.getDatumAanvangGeldigheid().isMagGeleverdWorden());
            }
            if (AttribuutUtil.isNotBlank(actie.getDatumEindeGeldigheid())) {
                datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(actie.getDatumEindeGeldigheid().getWaarde());
                datumEindeGeldigheid.setMagGeleverdWorden(actie.getDatumEindeGeldigheid().isMagGeleverdWorden());
            }
            if (AttribuutUtil.isNotBlank(actie.getDatumOntlening())) {
                datumOntlening = new DatumEvtDeelsOnbekendAttribuut(actie.getDatumOntlening().getWaarde());
                datumOntlening.setMagGeleverdWorden(actie.getDatumOntlening().isMagGeleverdWorden());
            }

            final ActieHisVolledigImpl actieHisVolledig = new ActieHisVolledigImpl(
                srtActie,
                handelingHisVolledig,
                partij,
                datumAanvangGeldigheid,
                datumEindeGeldigheid,
                new DatumTijdAttribuut(actie.getTijdstipRegistratie().getWaarde()),
                datumOntlening);

            ReflectionTestUtils.setField(actieHisVolledig, I_D, actie.getID());

            for (final ActieBronModel actieBron : actie.getBronnen()) {
                DocumentHisVolledigImpl documentHisVolledig = null;
                if (actieBron.getDocument() != null) {
                    documentHisVolledig = new DocumentHisVolledigImpl(actieBron.getDocument().getSoort());
                    documentHisVolledig.getDocumentHistorie().voegToe(
                        new HisDocumentModel(documentHisVolledig, actieBron.getDocument().getStandaard(), actie));
                    ReflectionTestUtils.setField(documentHisVolledig, I_D, actieBron.getDocument().getID());
                }
                final ActieBronHisVolledigImpl actieBronHisVolledig = new ActieBronHisVolledigImpl(actieHisVolledig, documentHisVolledig,
                    actieBron.getRechtsgrond(), actieBron.getRechtsgrondomschrijving());
                ReflectionTestUtils.setField(actieBronHisVolledig, I_D, actieBron.getID());
                actieHisVolledig.getBronnen().add(actieBronHisVolledig);
            }

            actieHisVolledig.setMagGeleverdWorden(actie.isMagGeleverdWorden());
            handelingHisVolledig.getActies().add(actieHisVolledig);
        }

        for (final AdministratieveHandelingGedeblokkeerdeMeldingModel gedeblokkeerdeMeldingModel : handeling.getGedeblokkeerdeMeldingen()) {
            final GedeblokkeerdeMeldingModel gedeblokkeerdeMelding = gedeblokkeerdeMeldingModel.getGedeblokkeerdeMelding();
            final GedeblokkeerdeMeldingHisVolledigImpl gedeblokkeerdeMeldingHisVolledig =
                new GedeblokkeerdeMeldingHisVolledigImpl(gedeblokkeerdeMelding.getRegel(), gedeblokkeerdeMelding.getMelding());
            final AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl administratieveHandelingGedeblokkeerdeMeldingHisVolledig =
                new AdministratieveHandelingGedeblokkeerdeMeldingHisVolledigImpl(handelingHisVolledig, gedeblokkeerdeMeldingHisVolledig);
            ReflectionTestUtils.setField(administratieveHandelingGedeblokkeerdeMeldingHisVolledig, I_D, gedeblokkeerdeMeldingModel.getID());

            handelingHisVolledig.getGedeblokkeerdeMeldingen().add(administratieveHandelingGedeblokkeerdeMeldingHisVolledig);
        }

        return handelingHisVolledig;
    }

    public static List<AdministratieveHandelingHisVolledigImpl> converteer(final List<AdministratieveHandelingModel> handelingen) {
        final List<AdministratieveHandelingHisVolledigImpl> handelingHisVolledigs = new ArrayList<>(handelingen.size());
        for (final AdministratieveHandelingModel handeling : handelingen) {
            handelingHisVolledigs.add(converteer(handeling));
        }

        return handelingHisVolledigs;
    }
}
