/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.dataaccess.repository.VerantwoordingRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Component voor het verwerken van de verantwoording van de bijhoudingen.
 */
@Service
public class VerantwoordingService {

    @Inject
    private VerantwoordingRepository verantwoordingRepository;

    /**
     * Sla van alle acties de verantwoording op, dit betekent dat documenten worden opgeslagen en dat er een koppeling
     * wordt gelegd tussen de acties en de bronnen.
     * De implementatie hier houdt er rekening mee dat de BerichtVerrijkingsStap de documenten die onder acties hangen
     * worden vervangen door de documenten die onder de administratieve handeling staan. In het bericht bevatten de
     * acties verwijzingen naar de documenten onder de administratieve handeling. Zie ook:
     * BerichtVerrijkingsStap.verrijkBronnen() voor meer informatie.
     * <p/>
     * Tevens bevat deze functie VR03002 en VR03001.
     * @param mapPersistenteActies            mapping tussen de opgeslagen acties in de database en de acties in het bericht
     * @param administratieveHandelingBericht de administratieve handeling die verwerkt wordt
     * @param context                         de bericht context voor dit bericht
     * @return meldingen                      de ResultaatMeldingen
     */
    public List<ResultaatMelding> slaActieVerantwoordingenOp(final Map<ActieBericht, ActieModel> mapPersistenteActies,
        final AdministratieveHandelingBericht administratieveHandelingBericht,
        final BijhoudingBerichtContext context)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        final Set<Map.Entry<ActieBericht, ActieModel>> entries = mapPersistenteActies.entrySet();
        for (final Map.Entry<ActieBericht, ActieModel> entry : entries) {
            final ActieBericht actieBericht = entry.getKey();
            final List<ActieBronBericht> bronnen = actieBericht.getBronnen();
            if (bronnen != null) {
                for (final ActieBronBericht actieBronBericht : bronnen) {
                    final DocumentBericht documentBericht = actieBronBericht.getDocument();
                    final DocumentModel bijhoudingDocument;
                    //Heeft het document een technische sleutel? @TODO Technische sleutel nog niet toegestaan in XSD.
                    if (StringUtils.isNotBlank(documentBericht.getObjectSleutel())) {
                        // @TODO Voorlopig aangenomen dat we het document moeten ophalen om de actie verantwoording op
                        // @TODO te slaan. Ook vanuit gegaan dat de technische sleutel gewoon een database id is.
                        bijhoudingDocument = verantwoordingRepository.haalDocumentOp(
                            Long.parseLong(documentBericht.getObjectSleutel())
                        );

                        if (bijhoudingDocument == null) {
                            meldingen.add(ResultaatMelding.builder()
                                .withSoort(SoortMelding.FOUT)
                                .withRegel(Regel.BRBY0014)
                                .withMeldingTekst(Regel.BRBY0014.getOmschrijving().replace("%s", documentBericht.getObjectSleutel()))
                                .withReferentieID(documentBericht.getCommunicatieID())
                                .withAttribuutNaam("document")
                                .build());
                        }
                    } else {

                        // Let op, het kan zijn dat 2 acties naar hetzelfde document wijzen in de
                        // AdministratieveHandelingBericht, het opslaan moet echter 1x gebeuren!
                        if (context.getBijgehoudenDocument(documentBericht.getCommunicatieID()) == null) {
                            //Maak een nieuw document aan met standaard groep en historie.
                            final DocumentHisVolledigImpl documentHisVolledig =
                                new DocumentHisVolledigImpl(documentBericht.getSoort());
                            documentHisVolledig.getDocumentHistorie().voegToe(
                                new HisDocumentModel(
                                    documentHisVolledig,
                                    documentBericht.getStandaard(),
                                    mapPersistenteActies.get(administratieveHandelingBericht.getHoofdActie()))
                            );
                            bijhoudingDocument = verantwoordingRepository.slaDocumentOp(documentHisVolledig);
                            context.voegBijgehoudenDocumentToe(documentBericht.getCommunicatieID(), bijhoudingDocument);
                        } else {
                            bijhoudingDocument =
                                context.getBijgehoudenDocument(documentBericht.getCommunicatieID());
                        }
                    }

                    if (bijhoudingDocument != null) {
                        // Sla de actie verantwoording op.
                        verantwoordingRepository.slaActieBronOp(entry.getValue(), bijhoudingDocument);
                    }
                }
            }
        }
        return meldingen;
    }
}
