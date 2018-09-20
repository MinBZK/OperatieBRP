/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonIndicatieView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.apache.commons.collections.CollectionUtils;


/**
 * BRBY05111: Adreshouder moet bevoegd zijn als aangever ingeschrevene is.
 *
 * De Persoon in wiens Adres AangeverAdreshouding de waarde “I” (Ingeschrevene) heeft, moet op TijdstipRegistratie
 * van dat Adres minstens 16 jaar oud zijn (Geboorte.Datum + 16 jaar < TijdstipRegistratie) én
 * niet onder Curatele gesteld zijn.
 */
@Named("BRBY05111")
public class BRBY05111 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final AangeverAttribuut aangever = bepaalAangever(nieuweSituatie, actie);

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (isAangeverIngeschrevene(aangever) && isRegelOvertreden(huidigeSituatie, actie)) {
            objectenDieDeRegelOvertreden.add(nieuweSituatie);
        }

        return objectenDieDeRegelOvertreden;
    }

    private boolean isAangeverIngeschrevene(final AangeverAttribuut aangever) {
        return aangever != null && AangeverCodeAttribuut.AANGEVER_ADRES_HOUDING_INGESCHEVENE.equals(aangever.getWaarde().getCode().getWaarde());
    }

    private boolean isRegelOvertreden(final PersoonView huidigeSituatie, final Actie actie) {
        return !isPersoonZestienOfOuder(huidigeSituatie, new DatumAttribuut(actie.getDatumAanvangGeldigheid()))
            || isPersoonHeeftIndicatieOnderCuratele(huidigeSituatie);
    }

    private AangeverAttribuut bepaalAangever(final PersoonBericht nieuweSituatie, final Actie actie) {
        AangeverAttribuut result = null;

        switch (actie.getSoort().getWaarde()) {
            case REGISTRATIE_ADRES:
                if (null != nieuweSituatie && CollectionUtils.isNotEmpty(nieuweSituatie.getAdressen())) {
                    final PersoonAdresBericht nieuweAdres = nieuweSituatie.getAdressen().iterator().next();
                    if (nieuweAdres.getStandaard().getAangeverAdreshouding() != null) {
                        result = nieuweAdres.getStandaard().getAangeverAdreshouding();
                    }
                }
                break;
            case REGISTRATIE_MIGRATIE:
                if (nieuweSituatie != null && nieuweSituatie.getMigratie() != null && nieuweSituatie.getMigratie().getAangeverMigratie() != null) {
                    result = nieuweSituatie.getMigratie().getAangeverMigratie();
                }
                break;
            default:
                // regel is niet van toepassing op andere acties
                break;
        }
        return result;
    }

    /**
     * Controleer of de opgegeven persoon een indicatie 'onder curatele' heeft.
     *
     * @param persoon De persoon waarvan de indicaties gecontroleerd moeten worden
     * @return true als persoon een indicatie 'onder curatele' heeft
     */
    private boolean isPersoonHeeftIndicatieOnderCuratele(final PersoonView persoon) {
        boolean heeftIndicatieOnderCuratele = false;

        for (final PersoonIndicatieView indicatie : persoon.getIndicaties()) {
            if (SoortIndicatie.INDICATIE_ONDER_CURATELE == indicatie.getSoort().getWaarde()) {
                heeftIndicatieOnderCuratele = true;
                break;
            }
        }

        return heeftIndicatieOnderCuratele;
    }

    /**
     * Controleer of de opgegeven persoon 16 jaar of ouder is op de opgegeven datum.
     *
     * @param persoon De persoon wiens leeftijd gecontroleerd moet worden
     * @param datum De datum op basis waarvan de leeftijd gecontroleerd moet worden
     * @return true als persoon 16 jaar is of ouder op de opgegeven datum
     */
    private boolean isPersoonZestienOfOuder(final Persoon persoon, final DatumAttribuut datum) {
        return PersoonUtil.isLeeftijdOfOuderOpDatum(persoon, DatumEvtDeelsOnbekendAttribuut.ZESTIEN_JAAR, datum);
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY05111;
    }
}
