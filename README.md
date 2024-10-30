# consumer-ms

# Instalacion docker
- En la raiz del proyecto ejecutar para [provider-ms](https://github.com/faustospina/provider-ms) docker build -t "spring-boot-provider-ms" .
- En la raiz del proyecto ejecutar para [consumer-ms](https://github.com/faustospina/consumer-ms) docker build -t "spring-boot-consumer-ms" .
- Despues de verificar las imagenes cargadas en docker seguir con el siguiente item
- En la raiz del proyecto ejecutar para [provider-ms](https://github.com/faustospina/provider-ms) docker-compose up

# El proyecto esta dividido en en 2 micro servicios
- [provider-ms](https://github.com/faustospina/provider-ms)
- [consumer-ms](https://github.com/faustospina/consumer-ms)


# Ambos proyectos estan diseñados en java jdk 17, y Maven

- [provider-ms](https://github.com/faustospina/provider-ms) aca tenemos los endpoints:
    - gestionar cliente-persona
    - agregar movimientos a la cola

- [consumer-ms](https://github.com/faustospina/consumer-ms) aca tenemos los endpoints:
    - gestionar cuentas
    - gestionar movimientos
    - gestionar reporte
