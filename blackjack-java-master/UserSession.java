import java.io.*;
import java.util.ArrayList;

public class UserSession {
    private static String username;
    private static int highestStreak;

    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        UserSession.username = username;
    }

    public static int getHighestStreak() {
        return highestStreak;
    }

    public static void setHighestStreak(int highestStreak) {
        UserSession.highestStreak = highestStreak;
    }

    // Membaca user information dari users.csv
    public void readUserInfo() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    setHighestStreak(Integer.parseInt(parts[1]));
                    break;  // Stop jika user sudah ketemu
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUserInfo() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username)) {
                    // Update
                    line = username + "," + highestStreak;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!lines.stream().anyMatch(line -> line.split(",").length == 2 && username.equals(line.split(",")[0].trim()))) {
            lines.add(username + "," + highestStreak);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
