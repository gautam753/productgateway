FROM public.ecr.aws/amazoncorretto/amazoncorretto:17
ARG JAR_FILE=target/productgateway-1.0.0.jar
COPY ${JAR_FILE} productgateway-svc.jar
ENV JAVA_OPTS="-Xms512m -Xmx1024m"
ENTRYPOINT ["java","-jar","/productgateway-svc.jar"]
RUN mkdir -p /logs && chmod 777 /logs
EXPOSE 8060
