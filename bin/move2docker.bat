scp Dockerfile docker@192.168.59.103:.
scp target/sp-1.0-SNAPSHOT.war docker@192.168.59.103:target/
scp glassfish-config/mysql-connector-java-5.1.27-bin.jar docker@192.168.59.103:glassfish-config/
scp glassfish-config/domain.xml docker@192.168.59.103:glassfish-config/
