package org.moss;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ImageToMP4 {
    public static void createVideoFromImage(String imagePath, String outputPath, long durationInSeconds) throws Exception {
        // FFmpeg command
        String command = "ffmpeg -loop 1 -i " + imagePath +
                " -c:v libx264 -t " + durationInSeconds +
                " -pix_fmt yuv420p " + outputPath;

        System.out.println("Executing command: " + command);

        // Execute the command
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // Capture and print FFmpeg output for debugging
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Wait for the process to finish
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg process failed with exit code " + exitCode);
        }

        System.out.println("Video created successfully at: " + outputPath);
    }


}
