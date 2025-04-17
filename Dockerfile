FROM openjdk:17-jdk-slim

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y --no-install-recommends \
        zlib1g=1:1.2.11.dfsg-2+deb11u2 \
        libpcre2-8-0=10.36-2+deb11u1 \
        e2fsprogs=1.46.2-2+deb11u1 \
        libcom-err2=1.46.2-2+deb11u1 \
        libext2fs2=1.46.2-2+deb11u1 \
        gzip=1.10-4+deb11u1 \
        liblzma5=5.2.5-2.1~deb11u1 && \
    rm -rf /var/lib/apt/lists/*

EXPOSE 8090

WORKDIR /app

ADD target/ouday_oueslati-0.1.8.jar oudayserv.jar

# 👇 FIX: Add JVM option to allow reflective access
ENTRYPOINT ["java", "--add-opens", "java.base/java.io=ALL-UNNAMED", "-jar", "oudayserv.jar"]
