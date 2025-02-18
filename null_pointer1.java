import java.util.*;
import java.util.stream.Collectors;

public class OrderProcessingService {
    // Uninitialized collections - NPE risks
    private List<Order> pendingOrders;
    private Map<String, Customer> customerCache;
    private Set<String> processedOrderIds;
    private Queue<NotificationRequest> notificationQueue;
    private List<PaymentTransaction> transactions;

    // Missing initialization in constructor
    public OrderProcessingService() {
        // Collections not initialized - NPE risk
    }

    public void processOrders(String customerId) {
        // Direct null access on uninitialized collection
        for (Order order : pendingOrders) {  // NPE risk: pendingOrders is null
            processOrder(order);
        }

        // Multiple nested collection iterations without null checks
        customerCache.get(customerId)
                    .getOrders()
                    .forEach(order -> {
                        order.getItems()
                             .forEach(item -> {
                                 item.getOptions()
                                     .forEach(option -> {
                                         processOption(option);
                                     });
                             });
                    });

        // Unsafe collection access in streams
        pendingOrders.stream()
                    .filter(Objects::nonNull)
                    .map(Order::getItems)        // Items might be null
                    .flatMap(List::stream)       // NPE if items is null
                    .collect(Collectors.toList());

        // Multiple collection operations without null checks
        for (Order order : getCustomerOrders(customerId)) {  // Might return null
            for (OrderItem item : order.getItems()) {        // Items might be null
                for (ItemOption option : item.getOptions()) { // Options might be null
                    processItemOption(option);
                }
            }
        }
    }

    public void processCustomerData(String customerId) {
        // Unsafe map operations
        Customer customer = customerCache.get(customerId);  // Map might be null
        
        // Multiple collection accesses without checks
        List<Address> addresses = customer.getAddresses();  // Might be null
        for (Address address : addresses) {
            List<Contact> contacts = address.getContacts(); // Might be null
            for (Contact contact : contacts) {
                Set<String> phones = contact.getPhones();   // Might be null
                for (String phone : phones) {
                    sendNotification(phone);
                }
            }
        }
    }

    public void processTransactions() {
        // Multiple opportunities for NPE in collection processing
        transactions.stream()
                   .filter(t -> t.getStatus().equals("PENDING"))  // NPE if transactions is null
                   .map(PaymentTransaction::getOrderItems)        // Might return null
                   .flatMap(List::stream)                        // NPE if items is null
                   .map(OrderItem::getProductOptions)            // Might return null
                   .flatMap(Set::stream)                         // NPE if options is null
                   .forEach(this::processOption);

        // Unsafe parallel stream operations
        transactions.parallelStream()
                   .map(PaymentTransaction::getCustomer)         // Might return null
                   .map(Customer::getPreferences)                // Might return null
                   .flatMap(prefs -> prefs.stream())            // NPE if preferences is null
                   .forEach(this::processPreference);
    }

    public void processNotifications() {
        // Queue operations without null checks
        while (!notificationQueue.isEmpty()) {  // NPE if queue is null
            NotificationRequest request = notificationQueue.poll();
            List<String> recipients = request.getRecipients();  // Might be null
            
            // Nested collection operations
            for (String recipient : recipients) {
                Set<NotificationType> types = request.getNotificationTypes();  // Might be null
                for (NotificationType type : types) {
                    List<String> messages = type.getMessages();  // Might be null
                    for (String message : messages) {
                        sendMessage(recipient, message);
                    }
                }
            }
        }
    }

    public Set<String> getActiveCustomerIds() {
        // Multiple collection operations with NPE risks
        return customerCache.values()                        // Map might be null
                          .stream()
                          .filter(c -> c.getStatus().equals("ACTIVE"))
                          .map(Customer::getOrders)         // Might return null
                          .flatMap(List::stream)            // NPE if orders is null
                          .map(Order::getCustomerId)        // Might return null
                          .collect(Collectors.toSet());
    }

    public void updateInventory() {
        // Nested collection iterations with multiple NPE risks
        for (Order order : pendingOrders) {                 // pendingOrders might be null
            for (OrderItem item : order.getItems()) {       // getItems might return null
                List<InventoryUpdate> updates = item.getInventoryUpdates();  // Might return null
                for (InventoryUpdate update : updates) {
                    Set<WarehouseLocation> locations = update.getLocations();  // Might return null
                    for (WarehouseLocation location : locations) {
                        processInventoryUpdate(location, update);
                    }
                }
            }
        }
    }

    // Supporting classes with minimal implementation
    private class Order {
        private List<OrderItem> items;
        private String customerId;
        public List<OrderItem> getItems() { return items; }
        public String getCustomerId() { return customerId; }
    }

    private class OrderItem {
        private List<ItemOption> options;
        private Set<ProductOption> productOptions;
        private List<InventoryUpdate> inventoryUpdates;
        public List<ItemOption> getOptions() { return options; }
        public Set<ProductOption> getProductOptions() { return productOptions; }
        public List<InventoryUpdate> getInventoryUpdates() { return inventoryUpdates; }
    }

    private class Customer {
        private List<Order> orders;
        private List<Address> addresses;
        private String status;
        private List<Preference> preferences;
        public List<Order> getOrders() { return orders; }
        public List<Address> getAddresses() { return addresses; }
        public String getStatus() { return status; }
        public List<Preference> getPreferences() { return preferences; }
    }

    private class Address {
        private List<Contact> contacts;
        public List<Contact> getContacts() { return contacts; }
    }

    private class Contact {
        private Set<String> phones;
        public Set<String> getPhones() { return phones; }
    }

    private class NotificationRequest {
        private List<String> recipients;
        private Set<NotificationType> notificationTypes;
        public List<String> getRecipients() { return recipients; }
        public Set<NotificationType> getNotificationTypes() { return notificationTypes; }
    }

    private class NotificationType {
        private List<String> messages;
        public List<String> getMessages() { return messages; }
    }

    private class PaymentTransaction {
        private String status;
        private List<OrderItem> orderItems;
        private Customer customer;
        public String getStatus() { return status; }
        public List<OrderItem> getOrderItems() { return orderItems; }
        public Customer getCustomer() { return customer; }
    }

    // Additional supporting classes
    private class ItemOption {}
    private class ProductOption {}
    private class Preference {}
    private class InventoryUpdate {
        private Set<WarehouseLocation> locations;
        public Set<WarehouseLocation> getLocations() { return locations; }
    }
    private class WarehouseLocation {}

    // Helper methods
    private void processOrder(Order order) {}
    private void processOption(ItemOption option) {}
    private void processOption(ProductOption option) {}
    private void processItemOption(ItemOption option) {}
    private void sendNotification(String phone) {}
    private void processPreference(Preference pref) {}
    private void sendMessage(String recipient, String message) {}
    private void processInventoryUpdate(WarehouseLocation location, InventoryUpdate update) {}
    private List<Order> getCustomerOrders(String customerId) { return null; }
}
