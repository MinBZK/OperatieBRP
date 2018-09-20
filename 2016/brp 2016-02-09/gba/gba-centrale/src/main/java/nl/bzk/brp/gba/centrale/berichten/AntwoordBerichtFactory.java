/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.centrale.berichten;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenDocumentBericht;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBijgehoudenPersoonBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAGeboorteAntwoordBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.webservice.business.service.AbstractAntwoordBerichtFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import org.apache.commons.collections.MapUtils;

/**
 * Antwoord bericht factory.
 */
public final class AntwoordBerichtFactory extends AbstractAntwoordBerichtFactory {

    @Override
    protected BijhoudingAntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(final Bericht ingaandBericht) {
        final BijhoudingAntwoordBericht antwoordBericht;
        final SoortBericht soortInkomendBericht = ingaandBericht.getSoort().getWaarde();
        switch (soortInkomendBericht) {
            case ISC_MIG_REGISTREER_GEBOORTE:
                antwoordBericht = new RegistreerGBAGeboorteAntwoordBericht();
                break;
            case ISC_MIG_REGISTREER_HUWELIJK_GEREGISTREERD_PARTNERSCHAP:
                antwoordBericht = new RegistreerGBAHuwelijkGeregistreerdPartnerschapAntwoordBericht();
                break;
            case ISC_MIG_REGISTREER_NAAM_GESLACHT:
                // antwoordBericht = new RegistreerGBANaamGeslachtAntwoordBericht();
                // break;
            case ISC_MIG_REGISTREER_OUDERSCHAP:
                // antwoordBericht = new RegistreerGBAOuderschapAntwoordBericht();
                // break;
            case ISC_MIG_REGISTREER_OVERLIJDEN:
                // antwoordBericht = new RegistreerGBAOverlijdenAntwoordBericht();
                // break;
                throw new IllegalStateException("Nog geen antwoord bericht bekend voor: " + soortInkomendBericht);
            default:
                throw new IllegalStateException("Ongeldig inkomend bericht: " + soortInkomendBericht);
        }
        return antwoordBericht;
    }

    @Override
    protected void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat resultaat, final Bericht inkomend, final AntwoordBericht antwoord) {
        final BerichtStandaardGroepBericht berichtStandaardGroep = (BerichtStandaardGroepBericht) inkomend.getStandaard();
        final AdministratieveHandelingBericht inkomendAdmH = berichtStandaardGroep.getAdministratieveHandeling();

        final AdministratieveHandelingBericht antwoordAdmH = cloneAdministratieveHandelingBericht(inkomendAdmH);

        // BOLIE: BUG: tijdstipRegistratie KAN leeg / null zijn (is nooit zover
        // gekomen); dan vul deze NIET aan.
        // xsd is daarvoor al aangepast (optioneel).
        final BijhoudingResultaat bijhoudingResultaat = (BijhoudingResultaat) resultaat;
        if (null != bijhoudingResultaat.getTijdstipRegistratie()) {
            antwoordAdmH.setTijdstipRegistratie(new DatumTijdAttribuut(bijhoudingResultaat.getTijdstipRegistratie()));
        }
        // bolie: we testen nu stuk makkelijk; als er een 'admin.handeling is,
        // is deze verwerking
        // A) geslaagd en B) gecommit.
        if (antwoord.getResultaat() != null && bijhoudingResultaat.getAdministratieveHandeling() != null) {
            antwoordAdmH.setBijgehoudenPersonen(bepaalBijgehoudenPersonen(bijhoudingResultaat.getBijgehoudenPersonen()));
            antwoordAdmH.setBijgehoudenDocumenten(bepaalBijgehoudenDocumenten(bijhoudingResultaat.getBijgehoudenDocumenten()));
            antwoordAdmH.setObjectSleutel(inkomendAdmH.getObjectSleutel());
        }

        antwoord.getStandaard().setAdministratieveHandeling(antwoordAdmH);

        antwoord.getStuurgegevens().setDatumTijdVerzending(new DatumTijdAttribuut(new Date()));

    }

    @Override
    protected BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(
        final Bericht ingaandBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtResultaatGroepBericht resultaatGroep = new BerichtResultaatGroepBericht();

        if (!resultaat.bevatStoppendeFouten()) {
            resultaatGroep.setBijhouding(new BijhoudingsresultaatAttribuut(Bijhoudingsresultaat.VERWERKT));
        }

        return resultaatGroep;
    }

    /**
     * Bepaalt op basis van het ingaande administratieve handeling bericht het uitgaand administratieve handeling
     * antwoord bericht dat geretourneerd moet worden. Het bepalen gebeurt op basis van de naamgeving strategie en Class
     * reflectie. Daarbij wordt de minimale informatie ook gecopieerd.
     *
     * @param admHIngaand
     *            Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    protected AdministratieveHandelingBericht cloneAdministratieveHandelingBericht(final AdministratieveHandelingBericht admHIngaand) {
        try {
            final Class<?> clazz =
                    Class.forName(
                        String.format("nl.bzk.brp.model.bericht.kern.Handeling%sBericht", maakCamelCaseVanEnumNaam(admHIngaand.getSoort().getWaarde())));
            final AdministratieveHandelingBericht admHUit = (AdministratieveHandelingBericht) clazz.newInstance();
            // set alleen die velden die *altijd* geretourneerd moeten worden
            admHUit.setPartij(admHIngaand.getPartij());
            admHUit.setPartijCode(admHIngaand.getPartijCode());
            admHUit.setToelichtingOntlening(admHIngaand.getToelichtingOntlening());
            return admHUit;
        } catch (
            ClassNotFoundException
            | InstantiationException
            | IllegalAccessException e)
        {
            throw new IllegalArgumentException(
                "Exceptie tijdens bepalen administratieve handeling bericht voor "
                                               + "soort administratieve handeling: '"
                                               + admHIngaand.getSoort().getWaarde()
                                               + "'",
                e);
        }
    }

    /**
     * Maak camel case van enum naam.
     *
     * @param enumInstantie
     *            enum instantie
     * @return camel case string
     */
    private String maakCamelCaseVanEnumNaam(final Enum<?> enumInstantie) {
        final String enumNaam = enumInstantie.name();
        final StringBuilder camelCaseBuffer = new StringBuilder();
        boolean vorigeTekenIsWoordEinde = true;
        for (final char c : enumNaam.toCharArray()) {
            if (c == '_') {
                // Niet overnemen, wel woord einde signaleren
                vorigeTekenIsWoordEinde = true;
            } else {
                if (vorigeTekenIsWoordEinde) {
                    camelCaseBuffer.append(Character.toUpperCase(c));
                } else {
                    camelCaseBuffer.append(Character.toLowerCase(c));
                }
                vorigeTekenIsWoordEinde = false;
            }
        }
        return camelCaseBuffer.toString();
    }

    /**
     * Kopieert een lijst van bijgehouden personen ({@link Persoon} instanties) naar een lijst van bijgehouden personen
     * voor de administratieve handeling ({@link AdministratieveHandelingBijgehoudenPersoonBericht} instanties).
     *
     * @param bijgehoudenPersonen
     *            de lijst van bijgehouden personen.
     * @return een lijst met bijgehouden personen ( {@link AdministratieveHandelingBijgehoudenPersoonBericht}
     *         instanties).
     */
    private List<AdministratieveHandelingBijgehoudenPersoonBericht> bepaalBijgehoudenPersonen(final Set<Persoon> bijgehoudenPersonen) {
        List<AdministratieveHandelingBijgehoudenPersoonBericht> berichtBijgehoudenPersonen = null;
        if (!bijgehoudenPersonen.isEmpty()) {
            berichtBijgehoudenPersonen = new ArrayList<>();
            for (final Persoon persoon : bijgehoudenPersonen) {
                final PersoonBericht persoonBericht = new PersoonBericht();
                final AdministratieveHandelingBijgehoudenPersoonBericht bijgehoudenPersoonBericht =
                        new AdministratieveHandelingBijgehoudenPersoonBericht();
                bijgehoudenPersoonBericht.setPersoon(persoonBericht);
                berichtBijgehoudenPersonen.add(bijgehoudenPersoonBericht);

                // Bijgehouden persoon bevat enkel de BSN.
                persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
                if (persoon.getIdentificatienummers() == null) {
                    // Geen identificatienummers meegegeven bij bijhouding
                    persoonBericht.getIdentificatienummers().setBurgerservicenummer(new BurgerservicenummerAttribuut(""));
                } else {
                    persoonBericht.getIdentificatienummers().setBurgerservicenummer(persoon.getIdentificatienummers().getBurgerservicenummer());

                }
            }
        }
        return berichtBijgehoudenPersonen;
    }

    /**
     * Kopieert een lijst van bijgehouden documenten ({@link DocumentModel} instanties) naar een lijst van bijgehouden
     * documenten voor de administratieve handeling ( {@link AdministratieveHandelingBijgehoudenDocumentBericht}
     * instanties).
     *
     * @param bijgehoudenDocumenten
     *            de lijst van bijgehouden documenten.
     * @return een lijst met bijgehouden documenten ( {@link AdministratieveHandelingBijgehoudenDocumentBericht}
     *         instanties).
     */
    private List<AdministratieveHandelingBijgehoudenDocumentBericht> bepaalBijgehoudenDocumenten(final Map<String, DocumentModel> bijgehoudenDocumenten) {
        final List<AdministratieveHandelingBijgehoudenDocumentBericht> berichtBijgehoudenDocumenten;
        if (!bijgehoudenDocumenten.isEmpty()) {
            berichtBijgehoudenDocumenten = new ArrayList<>();

            /**
             * TODO: Map aanpassen (BijhoudingResultaat.bijgehoudenDocumenten), zodat later het referentieId kan worden
             * teruggevonden bij het DocumentModel, dus de DocumentModel dient als key te worden geimplementeerd ipv als
             * value. Zie TEAMBRP-2362.
             */
            final Map inverseBijgehoudenDocumenten = MapUtils.invertMap(bijgehoudenDocumenten);

            final DocumentComparator comparator = new DocumentComparator();
            final Set<DocumentModel> gesorteerdeDocumenten = new TreeSet<>(comparator);
            gesorteerdeDocumenten.addAll(bijgehoudenDocumenten.values());

            for (final DocumentModel documentModel : gesorteerdeDocumenten) {
                final DocumentStandaardGroepBericht documentStandaardGroepBericht = new DocumentStandaardGroepBericht();
                documentStandaardGroepBericht.setIdentificatie(documentModel.getStandaard().getIdentificatie());
                documentStandaardGroepBericht.setPartijCode(documentModel.getStandaard().getPartij().getWaarde().getCode().toString());

                final DocumentBericht documentBericht = new DocumentBericht();
                documentBericht.setSoortNaam(documentModel.getSoort().getWaarde().getNaam().getWaarde());
                documentBericht.setStandaard(documentStandaardGroepBericht);
                documentBericht.setObjectSleutel(documentModel.getID().toString());
                documentBericht.setReferentieID(String.valueOf(inverseBijgehoudenDocumenten.get(documentModel)));

                final AdministratieveHandelingBijgehoudenDocumentBericht bijgehoudenDocumentBericht =
                        new AdministratieveHandelingBijgehoudenDocumentBericht();
                bijgehoudenDocumentBericht.setDocument(documentBericht);
                berichtBijgehoudenDocumenten.add(bijgehoudenDocumentBericht);
            }
        } else {
            berichtBijgehoudenDocumenten = null;
        }
        return berichtBijgehoudenDocumenten;
    }

    /**
     * Comparator implementatie voor DocumentModel. Sortering vind plaats op basis van het soort document en id.
     */
    static class DocumentComparator implements Comparator<DocumentModel>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final DocumentModel document1, final DocumentModel document2) {

            int vergelijk = document1.getSoort().getWaarde().getNaam().compareTo(document2.getSoort().getWaarde().getNaam());
            if (vergelijk == 0) {
                vergelijk = document1.getID().compareTo(document2.getID());
            }

            return vergelijk;
        }
    }
}
