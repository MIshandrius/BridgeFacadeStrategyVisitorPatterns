# Art Exhibition — Design Patterns Final Project

Java application demonstrating six design patterns: **Bridge, Facade, Strategy, Visitor, Builder, and Observer**.

The application manages art exhibitions containing paintings and sculptures. It supports physical and virtual presentation formats, interchangeable pricing strategies, artwork operations, exhibition construction, and notifications when new artworks are added.

## Requirements

- JDK 25
- IntelliJ IDEA or Maven
- JUnit 5 for automated testing

## Running the Application

Open the Maven project located at:

`BridgeFacade/SDP3Bridge`

Run `org.example.Main` in IntelliJ IDEA.

Alternatively, open a terminal in the directory containing `pom.xml` and execute:

```bash
mvn clean compile
mvn exec:java
```

To run the automated tests:

```bash
mvn clean test
```

## Design Patterns

The application implements six design patterns from all three required categories.

| Pattern | Category | Purpose | Main Components |
|---|---|---|---|
| Bridge | Structural | Separates exhibition types from presentation formats | Exhibition, Gallery, Museum, IObjectFormat, Physical, Virtual |
| Facade | Structural | Simplifies exhibition and artwork collection operations | GalleryFacade, MuseumFacade, ArtCollectionFacade |
| Strategy | Behavioral | Provides interchangeable pricing algorithms | IPricingStrategy, RegularIPricing, DiscountIPricing, CommissionIPricing, FixedDiscountIPricing |
| Visitor | Behavioral | Separates artwork operations from artwork classes | IArtVisitor, PackingVisitor, CatalogVisitor, ConservationVisitor |
| Builder | Creational | Constructs exhibitions with required and optional settings | ExhibitionBuilder, Gallery |
| Observer | Behavioral | Notifies registered listeners about artwork additions | ArtworkObserver, ArtworkAddedEvent, ActivityLogObserver, DisplayCounterObserver |

## Project Structure

```text
src/main/java/org/example/
├── Bridge/
│   ├── Art/
│   ├── Exhibition/
│   ├── ObjectType/
│   └── Price/
├── Builder/
│   └── ExhibitionBuilder.java
├── Facade/
├── Observer/
│   ├── ArtworkAddedEvent.java
│   ├── ArtworkObserver.java
│   ├── ActivityLogObserver.java
│   └── DisplayCounterObserver.java
├── Strategy/
├── Visitor/
└── Main.java
```

## 1. Bridge Pattern

**Category:** Structural

Bridge separates an abstraction from its implementation, allowing both to change independently.

### Implementation

- `Exhibition` — abstraction.
- `Gallery` and `Museum` — refined abstractions.
- `IObjectFormat` — implementation interface.
- `Physical` and `Virtual` — concrete implementations.

An exhibition can change its presentation format without changing its type.

### Example

```java
Gallery gallery = new Gallery(
    new Painting("Starry Night"),
    new Physical("Canvas"),
    100,
    new PriceUsd()
);

gallery.show();

gallery.setFormat(
    new Virtual("3D artwork model")
);

gallery.show();
```

### Benefits

- Separates exhibition behavior from presentation details.
- Supports both physical and virtual formats.
- Avoids creating separate classes for every exhibition-format combination.
- Makes new formats easier to introduce.

## 2. Facade Pattern

**Category:** Structural

Facade provides a simplified interface to a more complex system.

### Implementation

The application contains three facade classes:

- `GalleryFacade` — simplifies gallery operations.
- `MuseumFacade` — simplifies museum operations.
- `ArtCollectionFacade` — provides artwork collection operations.

### Example

```java
ArtCollectionFacade collection =
    new ArtCollectionFacade();

collection.packAll();
collection.showCatalog();
collection.showConservationAdvice();
```

The client uses simple methods without manually managing artwork traversal or visitor objects.

### Benefits

- Simplifies interaction with the system.
- Hides internal implementation details.
- Reduces dependencies between client code and internal classes.
- Provides convenient commands for common operations.

## 3. Strategy Pattern

**Category:** Behavioral

Strategy allows the application to change pricing algorithms without modifying the gallery's main logic.

### Implementation

`IPricingStrategy` defines a common interface for pricing calculations.

Concrete implementations include:

- `RegularIPricing`
- `DiscountIPricing`
- `CommissionIPricing`
- `FixedDiscountIPricing`

### Example

```java
gallery.setPricingStrategy(
    new DiscountIPricing(10)
);

gallery.show();
```

The gallery applies the selected strategy when calculating the final artwork price.

### Benefits

- Avoids large conditional statements for different pricing rules.
- Makes pricing algorithms interchangeable.
- Allows new pricing strategies to be added independently.
- Supports the Open/Closed Principle.

## 4. Visitor Pattern

**Category:** Behavioral

Visitor separates operations from the artwork classes on which they operate.

### Elements

`IArt` declares the method for accepting a visitor:

```java
void accept(IArtVisitor visitor);
```

The concrete elements are:

- `Painting`
- `Sculpture`

Each implements `accept()` by passing itself to the visitor:

```java
@Override
public void accept(IArtVisitor visitor) {
    visitor.visit(this);
}
```

### Visitor Interface

`IArtVisitor` declares an overloaded method for each concrete element type:

```java
public interface IArtVisitor {
    void visit(Painting painting);
    void visit(Sculpture sculpture);
}
```

### Concrete Visitors

| Visitor | Operation |
|---|---|
| PackingVisitor | Prints artwork-specific packing instructions |
| CatalogVisitor | Prints catalogue entries |
| ConservationVisitor | Prints conservation recommendations |

These operations print educational example information to the console.

### Object Structure

`ArtCollectionFacade` stores a collection containing both paintings and sculptures.

```java
private final List<IArt> artworks;
```

Its constructor creates the collection using `List.of()`.

A private traversal method applies a visitor to every element:

```java
private void applyVisitor(IArtVisitor visitor) {
    for (IArt artwork : artworks) {
        artwork.accept(visitor);
    }
}
```

The collection is separate from the artwork objects held by `GalleryFacade` and `MuseumFacade`.

The updated `Exhibition` class also supports applying a visitor to all artworks within an exhibition.

### Double Dispatch

Consider an `IArt` reference holding a `Painting`:

```java
IArt artwork = new Painting("Starry Night");
IArtVisitor visitor = new PackingVisitor();

artwork.accept(visitor);
```

The call proceeds as follows:

1. Java selects `Painting.accept()` using the runtime type of the artwork.
2. Inside `Painting.accept()`, `this` has the type `Painting`, so `visit(Painting)` is selected.
3. Java executes the corresponding implementation in `PackingVisitor`.

The operation therefore depends on both the concrete artwork type and the concrete visitor type.

No `instanceof` checks or casting chains are required.

### Benefits

- Separates artwork data from operations.
- Makes new operations easy to add.
- Reuses traversal logic.
- Avoids repeating type-checking conditions.

A limitation is that introducing a new artwork type requires updating the visitor interface and concrete visitors.

## 5. Builder Pattern

**Category:** Creational

Builder provides a structured way to create exhibitions with multiple artworks and optional configuration settings.

### Implementation

`ExhibitionBuilder` constructs `Gallery` objects using method chaining.

Required information:

- Exhibition title
- At least one artwork

Optional settings:

- Presentation format
- Currency formatter
- Pricing strategy
- Base price

Default values are provided for optional settings.

### Example

```java
Gallery exhibition = new ExhibitionBuilder()
    .title("Modern Masterpieces")
    .addArtwork(new Painting("Sunflowers"))
    .addArtwork(new Sculpture("The Thinker"))
    .format(new Virtual("Interactive 3D exhibition"))
    .currency(new PriceKzt())
    .basePriceUsd(2000)
    .pricingStrategy(new DiscountIPricing(10))
    .build();
```

### How It Works

1. A new `ExhibitionBuilder` object is created.
2. `title()` sets the exhibition title.
3. `addArtwork()` collects the artworks.
4. Optional methods configure presentation and pricing.
5. Each configuration method returns `this`, allowing method chaining.
6. `build()` validates the required fields and creates a `Gallery`.

### Validation

The Builder rejects:

- Missing or blank exhibition titles.
- Exhibitions without artworks.
- Null artwork objects.
- Negative or non-finite base prices.
- Null optional configuration objects.

The `build()` method prevents creating an exhibition without a valid title or at least one artwork.

### Independent Construction

The Builder creates a copy of the artwork list when constructing an exhibition.

This prevents future additions to the Builder from automatically changing previously created exhibitions.

### Benefits

- Avoids long constructor calls in client code.
- Makes object creation easier to read.
- Centralizes construction logic and validation.
- Supports optional settings.
- Integrates with the existing Bridge and Strategy patterns.

## 6. Observer Pattern

**Category:** Behavioral

Observer establishes a one-to-many relationship between an exhibition and its listeners.

Whenever a new artwork is added, the exhibition notifies all registered observers.

### Implementation

The Observer package contains four components:

**ArtworkObserver**

Defines the common listener interface:

```java
public interface ArtworkObserver {
    void onArtworkAdded(ArtworkAddedEvent event);
}
```

**ArtworkAddedEvent**

Stores information about an artwork addition:

- Exhibition title
- Added artwork
- Updated artwork count

**ActivityLogObserver**

Records received artwork-added events in an internal list and prints notifications.

**DisplayCounterObserver**

Tracks the current number of artworks and updates its count when it receives an event.

The `Exhibition` class acts as the subject or publisher.

### Registering Observers

```java
ActivityLogObserver log =
    new ActivityLogObserver();

DisplayCounterObserver counter =
    new DisplayCounterObserver(
        exhibition.getArtworkCount()
    );

exhibition.registerObserver(log);
exhibition.registerObserver(counter);
```

Both observers are registered with the same exhibition.

### Publishing an Event

```java
exhibition.addArtwork(
    new Painting("Water Lilies")
);
```

When an artwork is added:

1. The exhibition validates the artwork.
2. The artwork is added to the collection.
3. An `ArtworkAddedEvent` is created.
4. The exhibition notifies each registered observer.
5. The activity log records the event.
6. The counter updates the artwork count.

### Removing an Observer

```java
exhibition.removeObserver(log);
```

After removal, the activity logger no longer receives notifications.

The counter remains subscribed and continues updating.

### Expected Output

```text
Activity log: artwork added to 'Modern Masterpieces'
Display counter: 3 artwork(s)
Artworks after addition: 3
Display counter: 4 artwork(s)
Log entries after unregistering logger: 1
```

### Benefits

- Reduces coupling between exhibitions and listeners.
- Supports multiple independent reactions to the same event.
- Allows observers to subscribe and unsubscribe.
- Makes additional observers easier to introduce.
- Supports the Dependency Inversion Principle.

## Pattern Integration

All six patterns are integrated into the same art exhibition application.

Builder creates a gallery exhibition using existing Bridge abstractions and a pricing Strategy.

The exhibition stores artworks that can be processed using Visitor.

Observer allows the exhibition to notify listeners when new artworks are added.

Facade provides simplified operations for the gallery, museum, and artwork collection.

`Main.java` demonstrates the patterns through a console application.

### Example Integration

```java
Gallery exhibition = new ExhibitionBuilder()
    .title("Modern Masterpieces")
    .addArtwork(new Painting("Sunflowers"))
    .addArtwork(new Sculpture("The Thinker"))
    .format(new Virtual("Interactive 3D exhibition"))
    .currency(new PriceKzt())
    .basePriceUsd(2000)
    .pricingStrategy(new DiscountIPricing(10))
    .build();

ActivityLogObserver log =
    new ActivityLogObserver();

DisplayCounterObserver counter =
    new DisplayCounterObserver(
        exhibition.getArtworkCount()
    );

exhibition.registerObserver(log);
exhibition.registerObserver(counter);

exhibition.show();

exhibition.addArtwork(
    new Painting("Water Lilies")
);

System.out.println(counter.getCount());

exhibition.removeObserver(log);
```

This demonstrates how the Builder, Bridge, Strategy, and Observer patterns cooperate within one exhibition.

## Clean Code and SOLID Principles

### 1. Single Responsibility Principle

Each class has a focused responsibility.

- `ExhibitionBuilder` constructs exhibitions.
- `ActivityLogObserver` records artwork additions.
- `DisplayCounterObserver` tracks artwork counts.
- Pricing Strategy implementations calculate prices.
- Visitor implementations perform specific artwork operations.

### 2. Open/Closed Principle

The system can be extended by introducing new pricing strategies, presentation formats, visitors, and observers without rewriting their existing client interfaces.

### 3. Liskov Substitution Principle

Gallery and Museum objects can be used through the shared `Exhibition` abstraction while retaining their specialized behavior.

Concrete pricing strategies can be used through `IPricingStrategy`.

### 4. Interface Segregation Principle

Small interfaces define specific responsibilities:

- `IArt`
- `IObjectFormat`
- `IPrice`
- `IPricingStrategy`
- `IArtVisitor`
- `ArtworkObserver`

Classes do not need to implement unrelated operations.

### 5. Dependency Inversion Principle

High-level components communicate through abstractions.

For example, `Exhibition` stores `ArtworkObserver` references instead of depending directly on `ActivityLogObserver` or `DisplayCounterObserver`.

Similarly, `Gallery` uses `IPricingStrategy` instead of depending on one pricing algorithm.

### 6. DRY — Don't Repeat Yourself

Common artwork traversal is implemented in reusable methods.

Pricing algorithms are separated into Strategy classes instead of repeated throughout the gallery code.

Observer notification logic is centralized in `Exhibition.addArtwork()`.

### 7. KISS — Keep It Simple

The application uses small classes and straightforward interfaces.

Builder simplifies exhibition creation, while Facade simplifies client operations.

The application remains a console demonstration without unnecessary graphical-interface complexity.

### 8. Encapsulation

Exhibition data is stored in private fields.

`getArtworks()` returns a read-only list copy.

Observer event collections are also returned through read-only copies.

This prevents callers from directly modifying internal collections.

## Automated Testing

JUnit 5 tests are implemented in:

`src/test/java/org/example/PatternIntegrationTest.java`

Six tests cover the following behaviors:

| Test | Purpose |
|---|---|
| builderRequiresTitle | Rejects exhibitions without titles |
| builderRequiresArtwork | Rejects exhibitions without artworks |
| builderRejectsInvalidPrice | Rejects negative and NaN prices |
| builderCreatesIndependentExhibitions | Ensures built exhibitions remain independent |
| observersReceiveEventsAndCanUnsubscribe | Tests registration, notifications, and removal |
| rejectedArtworkDoesNotCreateEvent | Ensures null artwork additions do not publish events |

Run the tests using:

```bash
mvn clean test
```

A successful run should report zero test failures and errors.

## Verification Checklist

Verify the following before submission:

- All six design patterns are implemented.
- All three required pattern categories are represented.
- Java source code compiles successfully.
- Existing Bridge, Facade, Strategy, and Visitor operations work.
- Builder creates exhibitions containing multiple artworks.
- Builder validates required information.
- Optional Builder settings work.
- Observer notifications are delivered.
- Removed observers stop receiving notifications.
- Artwork counters update correctly.
- Automated tests pass.
- Main demonstrates the integrated application.
- Git commits record the implemented changes.

## Limitations

- Artwork information is stored in memory rather than a database.
- Exhibitions are created programmatically.
- The application uses console output rather than a graphical interface.
- The Observer implementation currently handles artwork-added events only.
- Artwork sales statuses such as sold or reserved are not implemented.
- Visitor operations provide example instructions rather than performing physical operations.
- Exhibition information is not saved between application runs.

## Future Improvements

Possible extensions include:

- An interactive CLI menu for creating and managing exhibitions.
- Saving exhibitions and artworks in a database.
- Supporting artwork availability states such as available, reserved, and sold.
- Publishing additional Observer events when artwork statuses change.
- Implementing a graphical interface.
- Exporting exhibition catalogues and activity logs.
- Supporting additional artwork types and pricing rules.

## Conclusion

The project demonstrates the practical use of six design patterns in a single Java art exhibition application.

Bridge separates exhibitions from presentation formats. Facade simplifies system operations. Strategy enables interchangeable pricing algorithms. Visitor supports different artwork operations. Builder simplifies exhibition construction, and Observer provides event-based communication.

Together, these patterns improve modularity, extensibility, code organization, and maintainability.

## Contributors

### Original Project
Zhasmina Ziyakhmetova — Bridge, Facade,
Strategy, and Visitor patterns.

### Final Examination Contributions
Aknur — Builder and Observer patterns,
multi-artwork exhibition support,
pattern integration, and JUnit tests.