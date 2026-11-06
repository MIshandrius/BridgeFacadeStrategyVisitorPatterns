# Art Exhibition — Bridge, Facade, Strategy and Visitor

Java project demonstrating four design patterns in an art exhibition system.

The application presents paintings and sculptures, supports physical and virtual
presentation formats, applies different pricing algorithms, and performs operations
on an artwork collection using Visitor.

## Requirements

- JDK 25
- IntelliJ IDEA or Maven

## Running the Application

Open the project in IntelliJ IDEA and run:

```text
org.example.Main
```

Alternatively, open a terminal in the directory containing `pom.xml`:

```bash
mvn compile
mvn exec:java -Dexec.mainClass=org.example.Main
```

## Design Patterns

| Pattern | Purpose | Main components |
|---|---|---|
| Bridge | Separates exhibition abstractions from artwork and presentation implementations | Exhibition, Gallery, Museum, IArt, IObjectFormat |
| Facade | Provides simple commands for exhibition and collection operations | GalleryFacade, MuseumFacade, ArtCollectionFacade |
| Strategy | Provides interchangeable price calculation algorithms | PricingStrategy, RegularPricing, DiscountPricing, CommissionPricing, FixedDiscountPricing |
| Visitor | Separates operations from the artwork classes | ArtVisitor, PackingVisitor, CatalogVisitor, ConservationVisitor |

## Visitor Implementation

### Elements

`IArt` declares the method for accepting a visitor:

```java
void accept(ArtVisitor visitor);
```

The concrete elements are:

- `Painting`
- `Sculpture`

Each implements `accept()` by passing itself to the visitor:

```java
@Override
public void accept(ArtVisitor visitor) {
    visitor.visit(this);
}
```

### Visitor Interface

`ArtVisitor` declares an overloaded method for each concrete element type:

```java
public interface ArtVisitor {
    void visit(Painting painting);
    void visit(Sculpture sculpture);
}
```

### Concrete Visitors

Each visitor implements one operation across both artwork types.

| Visitor | Operation |
|---|---|
| PackingVisitor | Prints type-specific packing instructions |
| CatalogVisitor | Prints catalogue entries containing artwork type and title |
| ConservationVisitor | Prints type-specific conservation recommendations |

These operations print educational example information to the console.

### Object Structure

`ArtCollectionFacade` stores a collection containing both paintings and sculptures:

```java
private final List<IArt> artworks;
```

Its constructor creates the collection using `List.of()`.

The private traversal method applies a visitor to every element:

```java
private void applyVisitor(ArtVisitor visitor) {
    for (IArt artwork : artworks) {
        artwork.accept(visitor);
    }
}
```

The collection is separate from the artwork objects held by `GalleryFacade`
and `MuseumFacade`.

### Client Usage

`Main` uses the facade's public commands:

```java
ArtCollectionFacade collection = new ArtCollectionFacade();

collection.packAll();
collection.showCatalog();
collection.showConservationAdvice();
```

Object creation and collection traversal are encapsulated inside the facade.

## Double Dispatch

Consider an `IArt` reference holding a `Painting` object:

```java
IArt artwork = new Painting("Starry Night");
ArtVisitor visitor = new PackingVisitor();

artwork.accept(visitor);
```

The call proceeds as follows:

1. Runtime dispatch selects `Painting.accept()` based on the actual element object.
2. Inside `Painting.accept()`, `this` has the compile-time type `Painting`,
   so overload resolution selects `visit(Painting)`.
3. Runtime dispatch selects `PackingVisitor`'s implementation of that method.

The resulting operation therefore depends on both the concrete element type
and the concrete visitor type.

No `instanceof`, casts, or type-checking chains are needed to select the operation.

## Expected Visitor Output

```text
Painting: protect the frame and use a flat crate.
Sculpture: secure the base and use a rigid crate.

Catalog | Painting | Starry Night
Catalog | Sculpture | David

Starry Night: protect from direct sunlight.
David: check the stability of the base.
```

Other exhibition output may appear before these lines.

Packing and conservation instructions refer to the physical artwork,
independently of its current presentation format.

## Clean Code Principles

### 1. Meaningful Names

Names communicate responsibilities:

- `ArtVisitor` defines artwork operations.
- `PackingVisitor` contains packing instructions.
- `CatalogVisitor` produces catalogue entries.
- `ConservationVisitor` provides conservation advice.
- `ArtCollectionFacade` exposes collection commands.

The distinction between elements and visitors is visible in their names.

### 2. Single Responsibility Principle

Each concrete visitor represents one operation across all supported element types.

Packing rules belong to `PackingVisitor`, catalogue rules to `CatalogVisitor`,
and conservation rules to `ConservationVisitor`.

A change to packing instructions does not require changing catalogue logic.

### 3. Polymorphism Instead of Type Checks

The traversal calls:

```java
artwork.accept(visitor);
```

It does not inspect whether the element is a painting or sculpture.

The `accept()` and `visit()` methods route execution to the appropriate operation.

### 4. Encapsulation

Visitors obtain artwork titles through `getTitle()` methods.

No public fields or title setters were added for Visitor. Visitors do not directly
access the existing protected fields in the artwork classes.

The collection is stored in a private final field. `List.of()` prevents adding
or removing list elements, although it does not make the contained objects immutable.

### 5. Open/Closed Principle for New Operations

`ConservationVisitor` demonstrates adding a third operation without changing:

- `IArt`
- `Painting`
- `Sculpture`
- `ArtVisitor`
- The existing collection traversal

A convenience method was added to the facade and called from `Main`.

This extensibility applies to new operations. Adding a new element type requires
updating the visitor interface and every concrete visitor.

### 6. Avoiding Duplication

The private `applyVisitor()` method contains the collection traversal once.

All three public collection commands reuse this method rather than repeating
the loop.

## Assignment 6 Requirements Mapping

| Requirement | Implementation |
|---|---|
| Element interface declaring accept() | IArt |
| At least two concrete elements | Painting and Sculpture |
| Matching visitor call with the element itself | visitor.visit(this) in each concrete element |
| Visitor method for every concrete element type | ArtVisitor |
| At least two concrete visitors | PackingVisitor, CatalogVisitor and ConservationVisitor |
| Collection traversed using accept() | artworks collection and applyVisitor() in ArtCollectionFacade |
| At least five justified Clean Code principles | Clean Code section above |
| Demonstration of a third visitor | ConservationVisitor |

## Extending the Application

### Adding a New Operation

For example, to add a restoration report:

1. Create `RestorationVisitor implements ArtVisitor`.
2. Implement `visit(Painting)` and `visit(Sculpture)`.
3. Add a public facade command that passes it to `applyVisitor()`.
4. Call the command from the client.

The element classes and existing visitors remain unchanged.

### Adding a New Element Type

For example, to add `Photograph`:

1. Create `Photograph implements IArt`.
2. Implement its `accept()` method.
3. Add `visit(Photograph)` to `ArtVisitor`.
4. Implement that method in every concrete visitor.
5. Add photographs to the collection where required.

Updating all visitors is the main trade-off of the Visitor pattern.

## Verification Checklist

After applying the implementation:

- Compile and run `Main`.
- Confirm that both artwork types receive packing instructions.
- Confirm that catalogue entries contain the correct titles.
- Confirm that conservation advice differs by artwork type.
- Confirm that existing exhibition and pricing demonstrations still run.
- Confirm that Visitor uses no type-checking chains.

## Limitations

- Artwork data is created in memory.
- Visitor operations print results to the console.
- The application does not generate catalogue files or perform physical packing.
- Visitor is most suitable when artwork types are stable and new operations
  are added more frequently than new element types.

## Author

Zhasmina Ziyakhmetova  
Group: SE-2509
