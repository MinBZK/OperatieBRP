/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht.publicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.service.algemeen.bericht.BerichtConstants;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.BerichtVrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBericht;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtParameters;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtVerwerkBericht;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.vrijbericht.MaakVerwerkVrijBerichtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * MaakPersoonBerichtServiceImpl.
 */
@Service
public final class MaakVerwerkVrijBerichtServiceImpl implements MaakVerwerkVrijBerichtService {

    @Override
    public String maakVerwerkVrijBericht(final VrijBerichtVerwerkBericht bericht) throws StapException {
        final BerichtWriterTemplate template = new BerichtWriterTemplate(SoortBericht.VRB_VRB_VERWERK_VRIJ_BERICHT.getIdentifier())
                .metInvullingDienstSpecifiekDeel(writer -> {
                    write(writer, bericht.getVrijBerichtParameters());
                    write(writer, bericht.getBerichtVrijBericht());
                });
        try {
            return template.toXML(bericht);
        } catch (final BerichtException e) {
            throw new StapException("Fout bij schrijven XML bericht:" + e.getMessage(), e);
        }
    }

    private static void write(BerichtWriter writer, final VrijBerichtParameters berichtParameters) {
        writer.startElement("parameters");
        writer.element("zenderVrijBericht", StringUtils
                .leftPad(String.valueOf(berichtParameters.getZenderVrijBericht().getCode()), BerichtConstants.PARTIJ_CODE_PADDING_POSITIES,
                        BerichtConstants.PADDING_WAARDE_0));
        writer.element("ontvangerVrijBericht", StringUtils
                .leftPad(String.valueOf(berichtParameters.getOntvangerVrijBericht().getCode()), BerichtConstants.PARTIJ_CODE_PADDING_POSITIES,
                        BerichtConstants.PADDING_WAARDE_0));
        writer.endElement();
    }

    private static void write(final BerichtWriter writer, final BerichtVrijBericht berichtVrijBericht) {
        writer.startElement("vrijBericht");
        final VrijBericht vrijBericht = berichtVrijBericht.getVrijBericht();
        writer.element("soortNaam", vrijBericht.getSoortVrijBericht().getNaam());
        writer.element("inhoud", vrijBericht.getInhoud());
        writer.endElement();
    }
}
