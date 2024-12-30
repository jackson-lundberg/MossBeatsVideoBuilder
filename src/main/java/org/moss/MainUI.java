package org.moss;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.DoubleUnaryOperator;

import static org.moss.Main.getAudioFileName;

public class MainUI {
    private JTextField audioFilePathField;
    private JTextField videoFilePathField;
    private JTextField imageFilePathField;
    private JButton processButton;
    private JTextArea outputTextArea;
    JPanel mainPanel;

    public MainUI() {
        // Initialize components
        audioFilePathField = new JTextField("Audio File",30);
        videoFilePathField = new JTextField("MP4 File",30);
        imageFilePathField = new JTextField("JPG File",30);
        processButton = new JButton("Process");
        outputTextArea = new JTextArea(15, 40);
        outputTextArea.setEditable(false);
        mainPanel = new JPanel();

        // Set colors
        Color backgroundColor = new Color(70, 130, 180); // Light blue
        Color foregroundColor = new Color(0, 50, 150);
        Color textAreaBackgroundColor = new Color(75, 0, 130); // Purple
        Color white = new Color(255, 255, 255);
        Color black = new Color(0, 0, 0);

        mainPanel.setBackground(backgroundColor);
        audioFilePathField.setBackground(backgroundColor);
        audioFilePathField.setForeground(foregroundColor);
        videoFilePathField.setBackground(backgroundColor);
        videoFilePathField.setForeground(foregroundColor);
        processButton.setBackground(white);
        processButton.setForeground(black);
        outputTextArea.setBackground(foregroundColor);
        outputTextArea.setForeground(white);

        Dimension textFieldDimension = new Dimension(400, 40); // Width, Height
        audioFilePathField.setPreferredSize(textFieldDimension);
        videoFilePathField.setPreferredSize(textFieldDimension);

        // Enable drag and drop for text fields
        new FileDrop(audioFilePathField);
        new FileDrop(videoFilePathField);
        new FileDrop(imageFilePathField);

        // Layout setup
        mainPanel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Audio File Path:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(audioFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Video File Path:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(videoFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Image File Path:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(imageFilePathField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(processButton, gbc);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        // Button action
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String audioFilePath = audioFilePathField.getText();
                String videoFilePath = videoFilePathField.getText();
                String imageFilePath = imageFilePathField.getText();

                if (audioFilePath.isEmpty() || (videoFilePath.isEmpty() && imageFilePath.isEmpty())) {
                    outputTextArea.append("Please provide both audio and video file paths.\n");
                    return;
                }
                //if image path is entered

                try {
                    String audioFileName = getAudioFileName(audioFilePath);
                    outputTextArea.append(audioFileName +"\n");
                    String tempVideoPath = "C:/Users/jacks/Downloads/MB/80808file101010101.mp4";
                    String outputFilePath = "C:/Users/jacks/Downloads/MB/" + audioFileName + "Video.mp4";
                    outputTextArea.append("Output File Path: "+ outputFilePath+"\n");
                    outputTextArea.append("Completed Path Setup\n");

                    long audioLength = AudioLength.getAudioLength(audioFilePath);
                    outputTextArea.append("Audio Length of " + audioFileName + ": " + audioLength + "\n");
                    System.out.println("we got here");
                    if (!imageFilePath.isEmpty()){
                        System.out.println(imageFilePath);
                        ImageToMP4.createVideoFromImage(imageFilePath,tempVideoPath,audioLength);
                    }
                    else {

                        long videoLength = VideoLength.getVideoLength(videoFilePath);
                        outputTextArea.append("Video Length of: " + videoFilePath + ", length: " + videoLength + "\n");

                        int loops = LoopCalculator.calculateLoops(audioLength, videoLength);
                        outputTextArea.append("Acquired Loops: " + loops + "\n");

                        VideoConcatenator.concatenateVideo(videoFilePath, loops, tempVideoPath);
                        outputTextArea.append("Video Created at " + tempVideoPath + "\n");
                    }
                    AudioVideoMerger.mergeAudioVideo(audioFilePath, tempVideoPath, outputFilePath);
                    outputTextArea.append("File created successfully at " + outputFilePath + "\n");

                    File videoFile = new File(tempVideoPath);
                    videoFile.delete();
                    outputTextArea.append("Complete!\n");

                } catch (Exception ex) {
                    outputTextArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });
    }

    private static class FileDrop extends DropTarget {
        private JTextField textField;

        public FileDrop(JTextField textField) {
            this.textField = textField;
            textField.setDropTarget(this);
        }

        @Override
        public synchronized void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = dtde.getTransferable();
                DataFlavor[] flavors = transferable.getTransferDataFlavors();
                for (DataFlavor flavor : flavors) {
                    if (flavor.isFlavorJavaFileListType()) {
                        java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(flavor);
                        if (files.size() > 0) {
                            textField.setText(files.get(0).getAbsolutePath());
                        }
                        break;
                    }
                }
                dtde.dropComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}