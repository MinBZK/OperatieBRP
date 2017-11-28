/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Util klasse voor conversie/formattering van de webservice vraag.
 */
public class AdhocWebserviceVraagBericht extends AbstractWebserviceVraagBericht {

    private static final Set<String> persoonIdentificerendeRubrieken = ImmutableSet.of(
            "010110", "010120", "010310", "010240", "081160");

    private static final Set<String> verplichteAdresRubrieken = ImmutableSet.of(
            "080910", "081110", "081115", "081160", "081180", "081190", "081210");

    private static final Set<String> optioneleAdresRubrieken = ImmutableSet.of(
            "081020", "081120", "081130", "081140", "081150", "081170");

    private static final Set<String> adresIdentificerendeRubrieken = Sets.union(verplichteAdresRubrieken, optioneleAdresRubrieken);

    private final Vraag vraag;
    private final List<String> gevraagdeRubrieken;
    private final List<String> zoekRubrieken;
    private List<Lo3CategorieWaarde> zoekCriteria;

    /**
     * Default constructor.
     * @param vraag de initiele vraag
     */
    public AdhocWebserviceVraagBericht(final Vraag vraag) {
        this.vraag = vraag;
        this.gevraagdeRubrieken = bepaalGevraagdeRubrieken(vraag.getMasker());
        this.zoekRubrieken = bepaalZoekRubrieken(vraag.getParameters());
    }

    @Override
    public SoortDienst getSoortDienst() {
        return Byte.valueOf((byte) 0).equals(this.vraag.getIndicatieAdresvraag()) ? SoortDienst.ZOEK_PERSOON : SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS;
    }

    /**
     * Zoeken in historie geeft de indicatie van zoeken in historie. Echter
     * mag er alleen gezocht worden in historie indien de soort dienst ZOEK_PERSOON is
     * @return indicatie of er in de historie gezocht moet worden.
     */
    public boolean isZoekenInHistorie() {
        return indicatieZoekenInHistorie() && (isPersoonsvraag() || (isAdresvraag() && isPersoonIdentificatie()));
    }

    @Override
    public List<String> getGevraagdeRubrieken() {
        return this.gevraagdeRubrieken;
    }

    @Override
    public List<String> getZoekRubrieken() {
        return this.zoekRubrieken;
    }

    @Override
    public List<Lo3CategorieWaarde> getZoekCriteria() {
        if (this.zoekCriteria == null) {
            this.zoekCriteria = bepaalZoekCriteria(vraag.getParameters());
        }
        return this.zoekCriteria;
    }

    /**
     * Geeft terug of er sprake is van persoonsidentificatie.
     * @return of er sprake is van persoonsidentificatie
     */
    public boolean isPersoonIdentificatie() {
        return !isAdresIdentificatie() && hasPersoonidentificerendeGegevens(this.getZoekRubrieken());
    }

    /**
     * Geeft terug of er sprake is van adresidentificatie.
     * @return of er sprake is van adresidentificatie
     */
    public boolean isAdresIdentificatie() {
        return isAdresvraag() && hasAdresidentificerendeGegevens(this.getZoekRubrieken());
    }

    private boolean isPersoonsvraag() {
        return !isAdresvraag();
    }

    public boolean isAdresvraag() {
        return Byte.valueOf((byte) 1).equals(vraag.getIndicatieAdresvraag());
    }

    private boolean indicatieZoekenInHistorie() {
        return Byte.valueOf((byte) 1).equals(vraag.getIndicatieZoekenInHistorie());
    }

    private boolean hasPersoonidentificerendeGegevens(final List<String> rubrieken) {
        return rubrieken.stream().anyMatch(persoonIdentificerendeRubrieken::contains);
    }

    private boolean hasAdresidentificerendeGegevens(final List<String> rubrieken) {
        return rubrieken.stream().anyMatch(verplichteAdresRubrieken::contains)
                && rubrieken.stream().allMatch(adresIdentificerendeRubrieken::contains);
    }
}
