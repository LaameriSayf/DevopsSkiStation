FROM openjdk:17-jdk-slim

# Mettre à jour les paquets système pour corriger les vulnérabilités
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
        dpkg=1.20.12 \
        libssl1.1=1.1.1n-0+deb11u5 \
        openssl=1.1.1n-0+deb11u5 \
        zlib1g=1:1.2.11.dfsg-2+deb11u2 \
        libtasn1-6=4.16.0-2+deb11u1 \
        libk5crypto3=1.18.3-6+deb11u5 \
        libkrb5-3=1.18.3-6+deb11u5 \
        libkrb5support0=1.18.3-6+deb11u5 \
        libgssapi-krb5-2=1.18.3-6+deb11u5 \
        libpcre2-8-0=10.36-2+deb11u1 \
        e2fsprogs=1.46.2-2+deb11u1 \
        libcom-err2=1.46.2-2+deb11u1 \
        libext2fs2=1.46.2-2+deb11u1 \
        gzip=1.10-4+deb11u1 \
        liblzma5=5.2.5-2.1~deb11u1 \
        libc6=2.31-13+deb11u10 \
        libc-bin=2.31-13+deb11u10 \
        libgnutls30=3.7.1-5+deb11u5 && \
    apt-get remove -y libdb5.3 bash && \
    rm -rf /var/lib/apt/lists/*

EXPOSE 8090

WORKDIR /app

ADD target/ouday_oueslati-0.1.8.jar oudayserv.jar

ENTRYPOINT ["java", "-jar", "oudayserv.jar"]