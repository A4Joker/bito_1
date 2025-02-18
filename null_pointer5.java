import java.util.*;
import java.util.stream.Collectors;

public class InventoryManagementSystem {
    // Uninitialized collections - NPE risks
    private List<Product> products;
    private Map<String, Supplier> suppliers;
    private Set<Order> pendingOrders;
    private Queue<Shipment> shipmentQueue;
    private List<Warehouse> warehouses;

    // No initialization in constructor - Setting up for NPE
    public InventoryManagementSystem() {
        // Collections intentionally left uninitialized
    }

    // Issue 1: Direct iteration without null check
    public void processProducts() {
        for (Product product : products) {
            updateInventory(product);
        }
    }

    // Issue 2: Nested iteration without null checks
    public void processOrders() {
        for (Order order : pendingOrders) {
            for (OrderItem item : order.getItems()) {
                processItem(item);
            }
        }
    }

    // Issue 3: Stream operation without null check
    public List<String> getProductNames() {
        return products.stream()
                      .map(Product::getName)
                      .collect(Collectors.toList());
    }

    // Issue 4: Multiple collection access without checks
    public void processSuppliers() {
        suppliers.values().forEach(supplier -> {
            supplier.getProducts().forEach(product -> {
                product.getCategories().forEach(this::updateCategory);
            });
        });
    }

    // Issue 5: Direct list access and iteration
    public void processWarehouses() {
        List<InventoryItem> items = warehouses.get(0).getInventory();
        for (InventoryItem item : items) {
            updateStock(item);
        }
    }

    // Issue 6: Queue operations without null check
    public void processShipments() {
        while (!shipmentQueue.isEmpty()) {
            Shipment shipment = shipmentQueue.poll();
            processShipmentItems(shipment.getItems());
        }
    }

    // Issue 7: Map iteration without null check
    public void updateSupplierInventory() {
        for (Map.Entry<String, Supplier> entry : suppliers.entrySet()) {
            List<Product> supplierProducts = entry.getValue().getProducts();
            for (Product product : supplierProducts) {
                updateSupplierProduct(product);
            }
        }
    }

    // Issue 8: Collection of collections without checks
    public void processWarehouseInventory() {
        for (Warehouse warehouse : warehouses) {
            List<InventoryItem> inventory = warehouse.getInventory();
            for (InventoryItem item : inventory) {
                List<StockUpdate> updates = item.getUpdates();
                for (StockUpdate update : updates) {
                    processUpdate(update);
                }
            }
        }
    }

    // Issue 9: Parallel stream without null check
    public void processOrdersParallel() {
        pendingOrders.parallelStream()
                    .map(Order::getItems)
                    .flatMap(List::stream)
                    .forEach(this::processItem);
    }

    // Issue 10: Multiple collection operations
    public void updateInventoryLevels() {
        warehouses.forEach(warehouse -> {
            warehouse.getInventory().forEach(item -> {
                item.getUpdates().forEach(update -> {
                    update.getLocations().forEach(this::updateLocation);
                });
            });
        });
    }

    // Issue 11: Set iteration without null check
    public void processPendingOrders() {
        for (Order order : pendingOrders) {
            Set<OrderItem> items = order.getItemSet();
            for (OrderItem item : items) {
                processOrderItem(item);
            }
        }
    }

    // Issue 12: Nested map operations
    public void processSupplierCategories() {
        suppliers.values().forEach(supplier -> {
            supplier.getCategoryMap().forEach((category, products) -> {
                products.forEach(this::updateProductCategory);
            });
        });
    }

    // Issue 13: Complex collection hierarchy
    public void processWarehouseLocations() {
        for (Warehouse warehouse : warehouses) {
            for (Section section : warehouse.getSections()) {
                for (Shelf shelf : section.getShelves()) {
                    for (Product product : shelf.getProducts()) {
                        updateProductLocation(product);
                    }
                }
            }
        }
    }

    // Issue 14: Collection filtering without null check
    public List<Product> getActiveProducts() {
        return products.stream()
                      .filter(Product::isActive)
                      .collect(Collectors.toList());
    }

    // Issue 15: Deque operations without null check
    public void processShipmentQueue() {
        Deque<Shipment> shipmentDeque = new ArrayDeque<>(shipmentQueue);
        while (!shipmentDeque.isEmpty()) {
            processShipment(shipmentDeque.poll());
        }
    }

    // Issue 16: Collection sorting without null check
    public List<Product> getSortedProducts() {
        return products.stream()
                      .sorted(Comparator.comparing(Product::getName))
                      .collect(Collectors.toList());
    }

    // Issue 17: Collection grouping without null check
    public Map<String, List<Product>> getProductsByCategory() {
        return products.stream()
                      .collect(Collectors.groupingBy(Product::getCategory));
    }

    // Issue 18: Collection reduction without null check
    public double getTotalInventoryValue() {
        return products.stream()
                      .mapToDouble(Product::getValue)
                      .sum();
    }

    // Issue 19: Collection transformation without null check
    public Set<String> getUniqueSuppliers() {
        return products.stream()
                      .map(Product::getSupplierName)
                      .collect(Collectors.toSet());
    }

    // Issue 20: Collection searching without null check
    public Optional<Product> findProductByName(String name) {
        return products.stream()
                      .filter(p -> p.getName().equals(name))
                      .findFirst();
    }

    // Supporting classes with minimal implementation
    private class Product {
        private String name;
        private String category;
        private String supplierName;
        private boolean active;
        private double value;
        private Set<String> categories;
        
        public String getName() { return name; }
        public String getCategory() { return category; }
        public String getSupplierName() { return supplierName; }
        public boolean isActive() { return active; }
        public double getValue() { return value; }
        public Set<String> getCategories() { return categories; }
    }

    private class Supplier {
        private List<Product> products;
        private Map<String, List<Product>> categoryMap;
        
        public List<Product> getProducts() { return products; }
        public Map<String, List<Product>> getCategoryMap() { return categoryMap; }
    }

    private class Order {
        private List<OrderItem> items;
        private Set<OrderItem> itemSet;
        
        public List<OrderItem> getItems() { return items; }
        public Set<OrderItem> getItemSet() { return itemSet; }
    }

    private class Warehouse {
        private List<InventoryItem> inventory;
        private List<Section> sections;
        
        public List<InventoryItem> getInventory() { return inventory; }
        public List<Section> getSections() { return sections; }
    }

    private class Section {
        private List<Shelf> shelves;
        public List<Shelf> getShelves() { return shelves; }
    }

    private class Shelf {
        private List<Product> products;
        public List<Product> getProducts() { return products; }
    }

    private class Shipment {
        private List<ShipmentItem> items;
        public List<ShipmentItem> getItems() { return items; }
    }

    // Additional supporting classes
    private class OrderItem {}
    private class InventoryItem {
        private List<StockUpdate> updates;
        public List<StockUpdate> getUpdates() { return updates; }
    }
    private class StockUpdate {
        private Set<Location> locations;
        public Set<Location> getLocations() { return locations; }
    }
    private class Location {}
    private class ShipmentItem {}

    // Helper methods
    private void updateInventory(Product product) {}
    private void processItem(OrderItem item) {}
    private void updateCategory(String category) {}
    private void updateStock(InventoryItem item) {}
    private void processShipmentItems(List<ShipmentItem> items) {}
    private void updateSupplierProduct(Product product) {}
    private void processUpdate(StockUpdate update) {}
    private void updateLocation(Location location) {}
    private void processOrderItem(OrderItem item) {}
    private void updateProductCategory(Product product) {}
    private void updateProductLocation(Product product) {}
    private void processShipment(Shipment shipment) {}
}
