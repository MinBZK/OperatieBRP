/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieTaalConstanten;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.SelectieLijst;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Component;

/**
 * Implementatie van de functie SELECTIELIJST.
 * <br>
 * De functie SELECTIE_LIJST() evalueert of een selectielijst een opgegeven identificatienummer bevat.
 * De selectielijst bevat ofwel uitsluitend burgerservicenummers, ofwel uitsluitend  administratienummers.
 * Indien voor de dienst geen selectielijst bestaat, evalueert functie altijd tot WAAR.
 *
 * Deze functie is alleen beschikbaar in de context van het uitvoeren van selecties.
 */
@Component
@FunctieKeyword("SELECTIE_LIJST")
public class SelectieLijstFunctie extends AbstractFunctie {

    public static final String EXPRESSIE = " SELECTIE_LIJST() ";

    SelectieLijstFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur()
        ));
    }

    @Override
    public Expressie evalueer(List<Expressie> argumenten, Context context) {
        final Persoonslijst persoonslijst = context.getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_PERSOONSLIJST);
        final SelectieLijst selectieLijst = context.getProperty(ExpressieTaalConstanten.CONTEXT_PROPERTY_SELECTIE_LIJST);
        //indien geen selectielijst aanwezig is, dan wordt geen extra selectiecriterium geevalueerd
        if (selectieLijst == null || selectieLijst.isLeeg()) {
            return BooleanLiteral.WAAR;
        } else {
            String identificatieNr = String.valueOf(persoonslijst.getActueleAttribuutWaarde(selectieLijst.getWaardeType()).orElse(null));
            return BooleanLiteral.valueOf(selectieLijst.getWaarden().contains(identificatieNr));
        }
    }

    @Override
    public ExpressieType getType(List<Expressie> argumenten, Context context) {
        return ExpressieType.BOOLEAN;
    }

}
