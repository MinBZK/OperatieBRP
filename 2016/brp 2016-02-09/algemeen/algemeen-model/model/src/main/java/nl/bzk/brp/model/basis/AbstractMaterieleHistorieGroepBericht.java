/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * Basis klasse voor alle groepen in bericht met materiele historie.
 */
public abstract class AbstractMaterieleHistorieGroepBericht extends AbstractFormeleHistorieGroepBericht implements
    MaterieleHistorie
{

    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid) {
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    /**
     * Zet de materiele historie datum(s) aan de hand van een bovenliggende ten tijde van unmarshalling. Deze methode kan middels een JiBX post-set
     * configuratie op de groep worden aangeroepen om de datum aanvanggeldigheid en de datum eindegeldigheid ten tijde van unmarshalling te zetten naar de
     * datums uit de bijbehorende actie. Hiervoor doorloopt deze functie de stack van de {@link IUnmarshallingContext} tot er een actie wordt gevonden en
     * worden dan de juiste datums gekopieerd.
     *
     * @param context de unmarshalling context waarbinnen de unmarshalling en deze methode wordt aangeroepen.
     */
    public void zetMaterieleHistorieDatums(final IUnmarshallingContext context) {
        if (context != null) {
            for (int i = 1; i < context.getStackDepth(); i++) {
                if (context.getStackObject(i) instanceof Actie) {
                    final Actie actie = (Actie) context.getStackObject(i);
                    this.setDatumAanvangGeldigheid(actie.getDatumAanvangGeldigheid());
                    this.setDatumEindeGeldigheid(actie.getDatumEindeGeldigheid());
                    break;
                }
            }
        }
    }
}
