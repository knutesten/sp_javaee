FROM tutum/glassfish

ADD target/ /opt

CMD asadmin start-domain domain1

