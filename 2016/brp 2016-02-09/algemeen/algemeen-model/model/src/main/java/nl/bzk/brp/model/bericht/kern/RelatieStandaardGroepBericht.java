/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;
import nl.bzk.brp.model.validatie.constraint.BRAL0102;
import nl.bzk.brp.model.validatie.constraint.BRBY0444;
import nl.bzk.brp.model.validatie.constraint.CustomSpEL;
import nl.bzk.brp.model.validatie.constraint.CustomSpELs;
import org.jibx.runtime.IUnmarshallingContext;

/**
 * Gegevens over de aanvang en einde van een Relatie
 *
 * 1. Niet van toepassing op de familierechtelijke betrekking. Zie ook overkoepelend memo over Relatie & Betrokkenheid.
 * Het lijkt erop dat de attributen waarmee de 'plaats' (woonplaats, gemeente, land etc etc) wordt aangeduid, alleen van
 * belang is voor huwelijk en geregistreerd partnerschap. Opnemen van de velden voor andere relaties is alleen reden
 * voor verwarring. We kiezen er daarom voor om 'plaats' velden alleen te vullen voor huwelijk en geregistreerd
 * partnerschap. 2. Vorm van historie: alleen formeel. Motivatie: alle (materi�le) tijdsaspecten zijn uitgemodelleerd
 * (met datum aanvang en datum einde), waardoor dus geen (extra) materi�le historie van toepassing is. Verder 'herleeft'
 * een Huwelijk niet, en wordt het ene Huwelijk niet door een ander Huwelijk be�indigd. Met andere woorden: twee
 * personen die eerst met elkaar Huwen, vervolgens scheiden, en vervolgens weer Huwen, hebben TWEE (verschillende)
 * exemplaren van Relatie: het eerste Huwelijk, en het tweede. Door deze zienswijze (die volgt uit de definitie van
 * Relatie) is er DUS geen sprake van materi�le historie, en volstaat dus de formele historie.
 *
 *
 *
 */
@CustomSpELs({
    @CustomSpEL(wanneerVeldVoldoetAanRegel = "landGebiedAanvangCode == #LandGebiedCodeAttribuut.NL_LAND_CODE_STRING",
        danMoetVeldVoldoenAanRegel = "datumAanvang.isGeldigeKalenderdatum()", code = Regel.BRAL2102,
        message = "BRAL2102", soortMelding = SoortMelding.FOUT),
    @CustomSpEL(wanneerVeldVoldoetAanRegel = "landGebiedEindeCode == #LandGebiedCodeAttribuut.NL_LAND_CODE_STRING",
        danMoetVeldVoldoenAanRegel = "datumEinde.isGeldigeKalenderdatum()", code = Regel.BRAL2103,
        message = "BRAL2103", soortMelding = SoortMelding.FOUT) })
public class RelatieStandaardGroepBericht extends AbstractRelatieStandaardGroepBericht implements Groep, RelatieStandaardGroep, MetaIdentificeerbaar {

    /**
     * Hook voor Jibx om de communicatieId van de encapsulerende object te zetten. De standaard groep wordt niet geregistreerd in de CommunicatieIdMap.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSetCommunicatieId(final IUnmarshallingContext ctx) {
        setCommunicatieID(((BerichtIdentificeerbaar) ctx.getStackObject(1)).getCommunicatieID());
    }

    @BRAL0102(dbObject = DatabaseObjectKern.RELATIE__DATUM_AANVANG)
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return super.getDatumAanvang();
    }

    @BRAL0102(dbObject = DatabaseObjectKern.RELATIE__DATUM_EINDE)
    @BRBY0444(dbObject = DatabaseObjectKern.RELATIE__DATUM_EINDE)
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return super.getDatumEinde();
    }

}
