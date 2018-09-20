/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.cfg.reveng.DelegatingReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.cfg.reveng.TableIdentifier;
import org.hibernate.metamodel.binding.MetaAttribute;
import org.springframework.util.StringUtils;

/**
 * Syn db gen reverse engineering strategy.
 */
public class SynDbGenReverseEngineeringStrategy extends DelegatingReverseEngineeringStrategy
{

    private final Properties properties = new Properties();

    /**
     * Instantieert Syn db gen reverse engineering strategy.
     * 
     * @param delegate
     *            delegate
     * @throws IOException 
     */
    public SynDbGenReverseEngineeringStrategy(final ReverseEngineeringStrategy delegate) throws IOException
    {
        super(delegate);
        
        final Properties localProps = new Properties();
        
        localProps.load(getClass().getResourceAsStream("revengHelper.properties"));
        
        //convert keys to lowercase
        
        for (Object key : localProps.keySet()) {
            properties.put(((String)key).toLowerCase(), localProps.get(key));
        }
        
    }

    @Override
    public boolean excludeForeignKeyAsCollection(final String keyname, final TableIdentifier fromTable,
            @SuppressWarnings("rawtypes") final List fromColumns, final TableIdentifier referencedTable,
            @SuppressWarnings("rawtypes") final List referencedColumns)
    {
        return true;
    }

    @Override
    public List getPrimaryKeyColumnNames(final TableIdentifier identifier) {
        return Arrays.asList(new String[]{"id"});
    }

    @Override
    public Map tableToMetaAttributes(TableIdentifier tableIdentifier) {
        
        Map<String, MetaAttribute> metaAttributes = super.tableToMetaAttributes(tableIdentifier);
        
        if (metaAttributes == null) {
            metaAttributes = new HashMap<>();
        }
       
        String implementsAttributeValue = properties.getProperty("table." + tableIdentifier.getName().toLowerCase() + ".implements"); 
        if (StringUtils.hasText(implementsAttributeValue)) {
            MetaAttribute implementsAttribute = new MetaAttribute("implements");
            implementsAttribute.addValue(implementsAttributeValue);
            metaAttributes.put("implements", implementsAttribute);
        }

        String classcodeAttributeValue = properties.getProperty("table." + tableIdentifier.getName().toLowerCase() + ".class-code"); 
        if (StringUtils.hasText(classcodeAttributeValue)) {
            MetaAttribute classcodeAttribute = new MetaAttribute("class-code");
            classcodeAttribute.addValue(classcodeAttributeValue);
            metaAttributes.put("class-code", classcodeAttribute);
        }

        return metaAttributes;
    }
    
}
