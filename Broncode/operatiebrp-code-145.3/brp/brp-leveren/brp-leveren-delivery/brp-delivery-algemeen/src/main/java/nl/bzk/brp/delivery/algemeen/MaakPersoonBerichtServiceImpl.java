/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.algemeen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.service.algemeen.bericht.BerichtConstants;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtPersoonslijstWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.DefaultPersoonComparator;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * MaakPersoonBerichtServiceImpl.
 */
@Service(value = "maakPersoonBerichtService")
public final class MaakPersoonBerichtServiceImpl implements MaakPersoonBerichtService {
    @Override
    public String maakPersoonBericht(final VerwerkPersoonBericht bericht) throws StapException {
        final BerichtWriterTemplate template = new BerichtWriterTemplate(SoortBericht.LVG_SYN_VERWERK_PERSOON.getIdentifier())
                .metInvullingDienstSpecifiekDeel(writer -> writeDienstSpecifiekElement(writer, bericht));
        try {
            return template.toXML(bericht);
        } catch (final BerichtException e) {
            throw new StapException("Fout bij schrijven XML bericht:" + e.getMessage(), e);
        }
    }

    private void writeDienstSpecifiekElement(final BerichtWriter writer, final VerwerkPersoonBericht bericht) {
        writer.startElement("synchronisatie");
        writer.attribute(BerichtConstants.OBJECTTYPE, ElementHelper.getObjectElement(Element.ADMINISTRATIEVEHANDELING.getId()).getXmlNaam());

        if (bericht.getBasisBerichtGegevens().getAdministratieveHandelingId() != null) {
            writer.attribute(BerichtConstants.OBJECT_SLEUTEL, bericht.getBasisBerichtGegevens().getAdministratieveHandelingId().toString());
        }

        if (bericht.getBasisBerichtGegevens().getParameters() != null
                && bericht.getBasisBerichtGegevens().getParameters().getSoortSynchronisatie() == SoortSynchronisatie.MUTATIE_BERICHT) {
            writer.attribute(BerichtConstants.VERWERKINGSSOORT, Verwerkingssoort.TOEVOEGING.getNaam());
        }

        if (bericht.getBasisBerichtGegevens().getSoortNaam() != null) {
            writer.element("soortNaam", bericht.getBasisBerichtGegevens().getSoortNaam());
        }
        if (bericht.getBasisBerichtGegevens().getCategorieNaam() != null) {
            writer.element("categorieNaam", bericht.getBasisBerichtGegevens().getCategorieNaam());
        }
        writer.element("partijCode", StringUtils
                .leftPad(String.valueOf(bericht.getBasisBerichtGegevens().getPartijCode()), BerichtConstants.PARTIJ_CODE_PADDING_POSITIES,
                        BerichtConstants.PADDING_WAARDE_0));
        if (bericht.getBasisBerichtGegevens().getTijdstipRegistratie() != null) {
            writer.element("tijdstipRegistratie",
                    DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(bericht.getBasisBerichtGegevens().getTijdstipRegistratie()));
        }
        writer.startElement("bijgehoudenPersonen");
        final List<BijgehoudenPersoon> gesorteerdePersonen = new ArrayList<>(bericht.getBijgehoudenPersonen());
        gesorteerdePersonen.sort(DefaultPersoonComparator.INSTANCE);
        for (final BijgehoudenPersoon persoon : gesorteerdePersonen) {
            BerichtPersoonslijstWriter.write(persoon.getBerichtElement(), writer);
        }
        if (!bericht.getBijgehoudenPersonen().isEmpty()) {
            writer.endElement();
        }

        writer.endElement();
    }
}
