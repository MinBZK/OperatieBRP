/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.FamilierechtelijkeBetrekkingHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.HuwelijkGeregistreerdPartnerschapHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.KindHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OuderHisVolledigView;
import org.apache.commons.collections.Predicate;

/**
 * Dit predikaat zorgt ervoor dat uitsluitend gegevens van gerelateerde personen (ouders, kinderen partners)
 * worden geleverd die niet voor aanvang van de relatie zijn beëindigd of vervallen.
 * <p>
 *     Dit predikaat is enkel van toepassing op berichten geleverd aan afnemers (niet aan bijhouders)
 * </p>
 *
 * @brp.bedrijfsregel R1328
 */
@Regels(Regel.R1328)
public class PreRelatieGegevensNietTonenPredikaat implements Predicate {

    /**
     * Definieert de objecttypen welke relevant zijn om te filteren.
     */
    private final Set<ElementEnum> filterbareElementen = new HashSet<>(Arrays.asList(
        ElementEnum.GERELATEERDEKIND_PERSOON,
        ElementEnum.GERELATEERDEOUDER_PERSOON,
        ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON,
        ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON
    ));

    @Override
    public final boolean evaluate(final Object voorkomen) {

        if (!(voorkomen instanceof GerelateerdIdentificeerbaar && voorkomen instanceof MaterieleHistorie)) {
            return true;
        }

        final GerelateerdIdentificeerbaar gerelateerdIdentificeerbaar = (GerelateerdIdentificeerbaar) voorkomen;
        if (gerelateerdIdentificeerbaar.getGerelateerdeObjectType() == null
            || !filterbareElementen.contains(gerelateerdIdentificeerbaar.getGerelateerdeObjectType().getObjectType()))
        {
            return true;
        }

        final DatumEvtDeelsOnbekendAttribuut datumAanvang = bepaalDatumAanvang(gerelateerdIdentificeerbaar);
        final MaterieleHistorie record = (MaterieleHistorie) voorkomen;

        final boolean recordTonenObvDatumEindeGeldigheid = datumAanvang == null
            || record.getDatumEindeGeldigheid() == null
            || record.getDatumEindeGeldigheid().na(datumAanvang);
        final boolean recordTonenObvDatumTijdVerval =
            datumAanvang == null || record.getDatumTijdVerval() == null || record.getDatumTijdVerval().naarDatum().na(datumAanvang);

        return recordTonenObvDatumEindeGeldigheid && recordTonenObvDatumTijdVerval;
    }

    /**
     * Bepaalt datum aanvang.
     *
     * @param gerelateerdIdentificeerbaar gerelateerd identificeerbaar
     * @return datum evt deels onbekend attribuut
     */
    private DatumEvtDeelsOnbekendAttribuut bepaalDatumAanvang(final GerelateerdIdentificeerbaar gerelateerdIdentificeerbaar) {
        DatumEvtDeelsOnbekendAttribuut datumAanvang = null;
        final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheid = gerelateerdIdentificeerbaar.getBetrokkenPersoonBetrokkenheidView();
        final RelatieHisVolledig relatie = gerelateerdIdentificeerbaar.getBetrokkenPersoonBetrokkenheidView().getRelatie();
        if (relatie instanceof HuwelijkGeregistreerdPartnerschapHisVolledigView
            && (gerelateerdIdentificeerbaar.getGerelateerdeObjectType().getObjectType() == ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON
            || gerelateerdIdentificeerbaar.getGerelateerdeObjectType().getObjectType() == ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON))
        {
            if (relatie.getRelatieHistorie().getActueleRecord() != null) {
                datumAanvang = relatie.getRelatieHistorie().getActueleRecord().getDatumAanvang();
            }
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledigView) {
            OuderHisVolledig relevanteOuderBetrokkenheid = null;
            if (betrokkenPersoonBetrokkenheid instanceof KindHisVolledigView
                && gerelateerdIdentificeerbaar.getGerelateerdeObjectType().getObjectType() == ElementEnum.GERELATEERDEKIND_PERSOON)
            {
                // de betrokken persoon is kind
                for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                    // betrokkenheden zonder persoon niet meenemen
                    if (betrokkenheidHisVolledig instanceof OuderHisVolledig && betrokkenheidHisVolledig.getPersoon() != null) {
                        relevanteOuderBetrokkenheid = (OuderHisVolledig) betrokkenheidHisVolledig;
                        break;
                    }
                }
            } else if (betrokkenPersoonBetrokkenheid instanceof OuderHisVolledigView
                && gerelateerdIdentificeerbaar.getGerelateerdeObjectType().getObjectType() == ElementEnum.GERELATEERDEOUDER_PERSOON)
            {
                // de betrokken persoon is ouder
                relevanteOuderBetrokkenheid = (OuderHisVolledig) betrokkenPersoonBetrokkenheid;
            }
            if (relevanteOuderBetrokkenheid != null && relevanteOuderBetrokkenheid.getOuderOuderschapHistorie().getActueleRecord() != null) {
                datumAanvang = relevanteOuderBetrokkenheid.getOuderOuderschapHistorie().getActueleRecord().getDatumAanvangGeldigheid();
            }
        }
        return datumAanvang;
    }
}
