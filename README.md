# jackson-datatype-hal
A Jackson module for handling HAL

## Configuration

This requires registering two modules in the `ObjectMapper`:

```java
objectMapper.registerModule(new HalModule());
objectMapper.registerModule(new UriTemplateModule());
```

## Usage

Serialization and deserialization are based on the `HalResource` class:

```java
HalResource resource = objectMapper.readValue(halJsonString, HalResource.class);

HalRel nextRel = resource.getRel("next");
HalLink nextLink = nextRel.getSingleLink();

List<HalLink> itemLinks = resource.getRel("items")
                            .getMultipleLinks();
```

