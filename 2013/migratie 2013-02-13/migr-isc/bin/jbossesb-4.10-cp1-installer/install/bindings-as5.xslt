<xsl:stylesheet version="1.0" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns="urn:jboss:bean-deployer:2.0"
    xmlns:sbm="urn:jboss:bean-deployer:2.0"
    exclude-result-prefixes="sbm">
    <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
    
    
    <xsl:template match="/sbm:deployment">
        <xsl:call-template name="add-xslt-beans">
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template match="/sbm:deployment/sbm:bean[@name='SystemPropertyBinder']/sbm:constructor/sbm:parameter/sbm:set">
        <xsl:call-template name="add-system-property">
        </xsl:call-template>
    </xsl:template>
    
    <xsl:template match="/sbm:deployment/sbm:bean/sbm:constructor/sbm:parameter/sbm:set[@elementClass='org.jboss.services.binding.ServiceBindingMetadata']">
        <xsl:call-template name="add-to-standard-bindings">
            <xsl:with-param name="jndiPort" select="1099"/>
            <xsl:with-param name="webPort" select="8080"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="add-xslt-beans">
        <xsl:copy xml:space="preserve"><xsl:apply-templates select="@*|node()"/>
   <xsl:text>
   </xsl:text>
   <xsl:comment>ESB XSL Transform to apply to jbossesb-properties.xml</xsl:comment><xsl:text>
   </xsl:text>
   <bean name="EsbPropertyServiceXSLTConfig" class="org.jboss.services.binding.impl.XSLTServiceBindingValueSourceConfig"><xsl:text>
       </xsl:text>
       <constructor><xsl:text>
           </xsl:text>
        <parameter><![CDATA[
                <xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>
                    <xsl:output method="xml"/>
                    <xsl:param name="esbHost"/>
                    <xsl:param name="port"/>

                    <xsl:template match="/">
                        <xsl:apply-templates/>
                    </xsl:template>

                    <xsl:template match="property[@name='org.jboss.soa.esb.jndi.server.url']">
                       <xsl:element name="property">
                          <xsl:attribute name="name">org.jboss.soa.esb.jndi.server.url</xsl:attribute>
                          <xsl:attribute name="value"><xsl:value-of select='$esbHost'/>:<xsl:value-of select='$port'/></xsl:attribute>
                       </xsl:element>
                    </xsl:template>

                    <xsl:template match="*|@*">
                       <xsl:copy>
                          <xsl:apply-templates select="@*|node()"/>
                       </xsl:copy>
                    </xsl:template>
                </xsl:stylesheet>
           ]]>
        </parameter><xsl:text>
           </xsl:text>
        <parameter><xsl:text>
               </xsl:text>
            <map keyClass="java.lang.String" valueClass="java.lang.String"><xsl:text>
                   </xsl:text>
                <entry><xsl:text>
                      </xsl:text>
                   <key>esbHost</key><xsl:text>
                      </xsl:text>
                   <value>${jboss.bind.address}</value><xsl:text>
                   </xsl:text>
                </entry><xsl:text>
               </xsl:text>
            </map><xsl:text>
           </xsl:text>
        </parameter><xsl:text>
      </xsl:text>
     </constructor><xsl:text>
   </xsl:text>
   </bean><xsl:text>
   
   </xsl:text>

   <xsl:comment> XSL Transform to apply to juddi-esb.xml </xsl:comment><xsl:text>
   </xsl:text>
   <bean name="EsbJuddiRMIXSLTConfig" class="org.jboss.services.binding.impl.XSLTServiceBindingValueSourceConfig"><xsl:text>
      </xsl:text>
      <constructor><xsl:text>
         </xsl:text>
         <parameter><![CDATA[
                <xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0'>

                   <xsl:output method="xml"/>
                   <xsl:param name="esbHost"/>
                   <xsl:param name="port"/>
                   <xsl:param name="webPort"/>

                   <xsl:template match="/">
                      <xsl:apply-templates/>
                   </xsl:template>

                   <xsl:template match="entry[@key='juddi.server.baseurl']">
                      <entry key="juddi.server.baseurl">http://<xsl:value-of select='$esbHost'/>:<xsl:value-of select="$webPort"/></entry>
                   </xsl:template>

                   <xsl:template match="*|@*">
                      <xsl:copy>
                         <xsl:apply-templates select="@*|node()"/>
                      </xsl:copy>
                   </xsl:template>
                </xsl:stylesheet>
         ]]>
       </parameter><xsl:text>
         </xsl:text>
       <parameter><xsl:text>
              </xsl:text>
              <map keyClass="java.lang.String" valueClass="java.lang.String"><xsl:text>
                  </xsl:text>
                  <entry><xsl:text>
                     </xsl:text>
                     <key>esbHost</key><xsl:text>
                     </xsl:text>
                     <value>${jboss.bind.address}</value><xsl:text>
                  </xsl:text>
                  </entry><xsl:text>
                  </xsl:text>
                  <entry><xsl:text>
                      </xsl:text>
                      <key>webPort</key><xsl:text>
                      </xsl:text>
                      <value>${org.jboss.esb.web.port}</value><xsl:text>
                   </xsl:text>
                  </entry><xsl:text>
              </xsl:text>
              </map><xsl:text>
         </xsl:text>
       </parameter><xsl:text>
      </xsl:text>
      </constructor><xsl:text>
   </xsl:text>
   </bean><xsl:text>

</xsl:text>
        </xsl:copy>
    </xsl:template>
	
	<xsl:template name="add-to-standard-bindings">
        <xsl:copy xml:space="preserve"><xsl:apply-templates select="@*|node()"/>
            <xsl:text>
            </xsl:text>
            <xsl:comment> ************ ESB Standard Bindings configuration ************ </xsl:comment><xsl:text>
            </xsl:text>
            <bean name="NamingServiceBindingMetadata" class="org.jboss.services.binding.ServiceBindingMetadata"><xsl:text>
	       </xsl:text>
	       <property name="serviceName">jboss:service=Naming</property><xsl:text>
	       </xsl:text>
	       <property name="bindingName">Port</property><xsl:text>
	       </xsl:text>
	       <property name="port">1099</property><xsl:text>
	       </xsl:text>
	       <property name="description">The listening socket for the Naming service</property><xsl:text>
            </xsl:text>
            </bean><xsl:text>
            </xsl:text> 
            <xsl:text>
            </xsl:text>
            <bean class="org.jboss.services.binding.ServiceBindingMetadata"><xsl:text>
               </xsl:text>
               <property name="serviceName">EsbPropertyService</property><xsl:text>
               </xsl:text>
               <property name="port"><xsl:text>
                   </xsl:text>
                   <value-factory bean="NamingServiceBindingMetadata" method="getPort"/><xsl:text>
               </xsl:text>
               </property><xsl:text>
               </xsl:text>
               <property name="description">Will process jbossesb-properties using xslt to support substituion of configuration properties.</property><xsl:text>
               </xsl:text>
               <property name="serviceBindingValueSourceConfig"><xsl:text>
                   </xsl:text>
                   <inject bean="EsbPropertyServiceXSLTConfig"/><xsl:text>
               </xsl:text>
               </property><xsl:text>
            </xsl:text>
            </bean><xsl:text>
            </xsl:text>
            <xsl:text>
            </xsl:text>
            <bean class="org.jboss.services.binding.ServiceBindingMetadata"><xsl:text>
               </xsl:text>
                <property name="serviceName">EsbJuddiRMIPropertyService</property><xsl:text>
               </xsl:text>
                <property name="port"><xsl:text>
                </xsl:text>
                <value-factory bean="NamingServiceBindingMetadata" method="getPort"/><xsl:text>
                </xsl:text>
		</property><xsl:text>
               </xsl:text>
                <property name="description">Will process juddi-esb.xml using xslt to support substituion of configuration properties .</property><xsl:text>
               </xsl:text>
                <property name="serviceBindingValueSourceConfig"><xsl:text>
                   </xsl:text>
                    <inject bean="EsbJuddiRMIXSLTConfig"/><xsl:text>
               </xsl:text>
                </property><xsl:text>
            </xsl:text>
            </bean><xsl:text>
            
            </xsl:text>
	    </xsl:copy>
    </xsl:template>
    
    <xsl:template name="add-system-property">
        <xsl:copy xml:space="preserve"><xsl:apply-templates select="@*|node()"/>
            <xsl:text>
               </xsl:text>
            <xsl:comment>************ ESB System Properties ************ </xsl:comment><xsl:text>
               </xsl:text>
            <bean class="org.jboss.services.binding.SystemPropertyBinding"><xsl:text>
                   </xsl:text>
                <constructor><xsl:text>
                       </xsl:text>
                    <parameter>org.jboss.esb.web.port</parameter><xsl:text>
                       </xsl:text>
                    <parameter class="int"><xsl:text>
                           </xsl:text>
                        <value-factory bean="ServiceBindingManager" method="getIntBinding"><xsl:text>
                               </xsl:text>
                           <parameter>jboss.web:service=WebServer</parameter><xsl:text>
                           </xsl:text>
                       </value-factory><xsl:text>
                       </xsl:text>
                    </parameter><xsl:text>
                  </xsl:text>
                </constructor><xsl:text>
               </xsl:text>
            </bean><xsl:text>
            
            </xsl:text>
	    </xsl:copy>
    </xsl:template>
	
    
	<xsl:template name="identity" match="@*|node()">
	    <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    
</xsl:stylesheet>
