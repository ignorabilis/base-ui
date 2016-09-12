FROM ignorabilis/boot-openjdk-clj:alpine
MAINTAINER Irina Yaroslavova Stefanova

COPY . /opt/ignorabilis-src

RUN boot ignorabilis-build \
	&& cp /opt/ignorabilis-src/target/project.jar /opt/ignorabilis \
	&& rm -rf /opt/ignorabilis-src

WORKDIR /opt/ignorabilis
EXPOSE 80
CMD java -jar project.jar
