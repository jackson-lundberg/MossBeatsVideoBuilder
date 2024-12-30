package org.moss;


import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


 class FileCreator {
    public static void createFile(String filePath, String content) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(content);
            System.out.println("File created successfully at " + filePath);
        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }}
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        JFrame frame = new JFrame("MainUI");
        frame.setContentPane(new MainUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//        String audioFilePath = "C:/Users/jacks/Downloads/MB/TreeInstrumental.wav";
//
//        String audioFileName = getAudioFileName(audioFilePath);
//        String videoFilePath = "C:/Users/jacks/Downloads/b81_1.mp4";
//        String tempVideoPath = "C:/Users/jacks/Downloads/MB/80808file101010101.mp4";
//        String outputFilePath = "C:/Users/jacks/Downloads/MB/"+ audioFileName +"1.mp4";
//        System.out.println("completed Path Setup");
//        long audioLength = AudioLength.getAudioLength(audioFilePath);
//        System.out.println("Audio Length of " + audioFileName +": "+ audioLength);
//        long videoLength = VideoLength.getVideoLength(videoFilePath);
//        System.out.println("Video Length of: "+ videoFilePath+ ", length:"+ videoLength);
//        int loops = LoopCalculator.calculateLoops(audioLength, videoLength);
//        System.out.println("Acquired Loops: "+loops);
//
//        VideoConcatenator.concatenateVideo(videoFilePath, loops, tempVideoPath);
//        System.out.println("Video Created at" + tempVideoPath);
//        AudioVideoMerger.mergeAudioVideo(audioFilePath, tempVideoPath, outputFilePath);
//        System.out.println("File created successfully at " + outputFilePath);
//        File videoFile = new File(tempVideoPath);
//        videoFile.delete();
//        System.out.println("Complete!");
    }

    public static String getAudioFileName(String input){
        return new File(input).getName().split("\\.")[0];
//        String[] audioPathSplit = input.split("[/.]");
//        return audioPathSplit[audioPathSplit.length-2];
    }



}