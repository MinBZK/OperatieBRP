To create and run a jbossesb.war standalone installation:

1. Set the tomcat home

   org.jboss.esb.tomcat.home=/apache-tomcat-5.5.20

   in your deployment.properties file.

2. Update the database info in the context.xml file to point to your
   juddi and message store databases (you need to run the sql scripts in the
   jUDDI-registry and message-store directories first to populate them).

	<Resource name="jdbc/juddiDB" auth="Container" type="javax.sql.DataSource"
        maxActive="100" maxIdle="30" maxWait="10000" username="juddi"
        password="juddi" driverClassName="org.gjt.mm.mysql.Driver"
        url="jdbc:mysql://localhost:3306/juddi?autoReconnect=true" />

        <Resource name="jdbc/JBossesbDS" auth="Container" type="javax.sql.DataSource"
        maxActive="100" maxIdle="30" maxWait="10000" username="jbossesb"
        password="jbossesb" driverClassName="org.gjt.mm.mysql.Driver"
        url="jdbc:mysql://localhost:3306/jbossesb?autoReconnect=true" />

        
   This file will be copied into the META-INF directory of the web app.

3. Edit jboss-esb.xml to configure the JMS properties for the jms-provider.
   (configurations for JBossMQ and JBoss Messaging are provided in DMQ)

4. Start your JMS server.  (You may need to check port assignments if both are
   running on the same machine.)

5. Add the database driver and JMS libraries to the tomcat.home/common/lib/ directory.

6. Run the ant tomcat task. This creates a jbossesb.war and the configration for tomcat.

7. Launch tomcat.
