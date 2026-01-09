import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class EcoPointsRecyclingTracker {
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Household> households = new HashMap<>();

    public static void main (String[] args) {
        loadHouseholdsFromFile();
        boolean runnig = true;
        while(runnig) {
            System.out.println("\n=== Eco-Points Recycling Tracker ===");
            System.out.println("1. Register Household");
            System.out.println("2. Log Recycling Event");
            System.out.println("3. Display Households");
            System.out.println("4. Display Household Recycling Events");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save Data and Exit");
            System.out.println("Choose an option: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    registerHousehold();
                    break;
                case "2":
                    logRecyclingEvent();
                    break;
                case "3":
                    displayHouseholds();
                    break;
                case "4":
                    displayHouseholdEvents();
                    break;
                case "5":
                    generateReports();
                    break;
                case "6":
                    saveData();
                    runnig = false;
                    System.out.println("Data saved. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerHousehold() {
        System.out.println("Enter household ID: ");
        String id = scanner.nextLine().trim();

        if (households.containsKey(id)) {
            System.out.println("Error: HouseholdId already exists");
            return;
        }

        System.out.println("Enter household name: ");
        String name = scanner.nextLine().trim();

        System.out.println("Ener household address: ");
        String address = scanner.nextLine().trim();

        Household newHousehold = new Household(id, name, address);
        households.put(id, newHousehold);

        System.out.println("Household registered successfully!");
    }

    private static void logRecyclingEvent() {
        System.out.println("Enter household ID: ");
        String idHousehold = scanner.nextLine().trim();

        Household currentHousehold = households.get(idHousehold);

        if (currentHousehold == null) {
            System.out.println("Error: Household ID does not exists");
            return;
        }

        System.out.println("Enter Material type (plastic/glass/metal/paper): ");
        String materlialType = scanner.nextLine().trim();

        double weight = 0.0;
        
        while (true) {
            try {
                System.out.print("Enter weight in kilograms: ");
                weight = Double.parseDouble(scanner.nextLine());  
                if (weight <= 0) throw new IllegalArgumentException();
                break; 
            }  catch (NumberFormatException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            }  catch (IllegalArgumentException e) {
                System.out.println("Invalid weight. Must be a positive number.");
            }
        }

        RecyclingEvent recyclingEvent = new RecyclingEvent(materlialType, weight);
        currentHousehold.addEvent(recyclingEvent);
        System.out.println("Recycling event logged! Points earned: " + recyclingEvent.getEcoPoints());
    }

    private static void displayHouseholds() {

        if (households.isEmpty()) {
            System.out.println("No households registered");
            return;
        }
        System.out.println("\nRegistered Households:");
        for (Household household : households.values()) {
            System.out.println("ID: "+ household.getId() +
                            "\nName: " + household.getName()+
                            "\nAdress: " + household.getAddress()+
                            "\nJoined: "+ household.getJoinDate()+"\n-----------------------");
        }
    }

    private static void displayHouseholdEvents() {
        System.out.println("Select ID of the Household: ");
        displayHouseholds();
        String id = scanner.nextLine().trim();
        Household household = households.get(id);

        if (household == null) {
            System.out.println("Error: Household ID does not exists");
            return;
        }
        System.out.println("\nRecycling Events for " + household.getName() + ":");
        if (household.getEvents().isEmpty()) {
            System.out.println("No events logged.");
        } else {
            for (RecyclingEvent e : household.getEvents()) {
                System.out.println(e);
            }
        }

        System.out.println("Total weight: " + household.getTotalWeight() + "kg");
        System.out.println("Total EcoPoints: "+ household.getTotalPoints() + "pts");
    }

    private static void generateReports() {

        if (households.isEmpty()) {
            System.out.println("Empty!. No households registered.");
            return;
        }

        Household top = null;

        for (Household h : households.values()) {
            if (top == null || h.getTotalPoints() > top.getTotalPoints()) {
                top = h;    
            }
        }

        System.out.println("\nHousehold with Highest Points:");
        System.out.println("ID: " + top.getId() +
                           ", Name: " + top.getName() +
                           ", Points: " + top.getTotalPoints());

        double totalWeight = 0.0;

        for (Household h : households.values()) {
            totalWeight += h.getTotalWeight();
        }

        System.out.println("Total Community Recycling Weight: " + totalWeight + " kg");
    }

    private static void saveData() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("households.ser")
            );
            
            out.writeObject(households);
            
        } catch (IOException e) {
            // Task 8
            // If something goes wrong while saving, print an error message
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Suppresses unchecked cast warning when reading the object
    private static void loadHouseholdsFromFile() {
        // Use a try-with-resources block to automatically close the input stream
        try (
            // Open an ObjectInputStream to read from the file "households.ser"
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("households.ser"))
        ) {
            // Read the object from the file and cast it back to the correct type
            households = (HashMap<String, Household>) in.readObject();
            // Confirmation message to let the user know data was loaded
            System.out.println("Household data loaded.");
        } catch (FileNotFoundException e) {
            // Task 8
            // If the file doesn't exist yet, that's okay — start with empty data
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            // Handle other errors, like if the file is corrupted or unreadable
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}