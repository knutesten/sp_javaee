FROM tutum/glassfish

ADD target/sp-1.0-SNAPSHOT.war                           /opt/glassfish4/glassfish/domains/domain1/autodeploy/
ADD glassfish-config/domain.xml                          /opt/glassfish4/glassfish/domains/domain1/config/
ADD glassfish-config/mysql-connector-java-5.1.27-bin.jar /opt/glassfish4/glassfish/domains/domain1/lib/

CMD ["/run.sh"]
