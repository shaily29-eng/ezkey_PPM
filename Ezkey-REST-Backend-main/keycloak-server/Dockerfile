#FROM keycloak/keycloak
#ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--http-port=8080"]
FROM quay.io/keycloak/keycloak:latest as builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Configure a database vendor
ENV KC_DB=mssql

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# change these values to point to a running postgres instance
ENV KC_DB=mssql
ENV KC_DB_URL="jdbc:sqlserver://ezkeysb.database.windows.net:1433;database=keycloak;user=ezkeyadmin@ezkeysb;password=pacific@1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
#ENV KC_DB_USERNAME=ezkeyadmin
#ENV KC_DB_PASSWORD=<DBPASSWORD>
ENV KC_TRANSACTION_XA_ENABLED=false
ENV KC_HOSTNAME="ezkeyauth.azurewebsites.net"
ENV KC_PROXY=edge
ENV KEYCLOAK_ADMIN="admin"
ENV KEYCLOAK_ADMIN_PASSWORD="admin"
ENV PROXY_ADDRESS_FORWARDING=true
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--http-port=8080", "--https-port=8443"]