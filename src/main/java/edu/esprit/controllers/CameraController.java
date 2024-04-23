package com.example.reclamation.controllers;


import ai.rev.speechtotext.RevAiWebSocketListener;
import ai.rev.speechtotext.StreamingClient;
import ai.rev.speechtotext.models.asynchronous.Element;
import ai.rev.speechtotext.models.streaming.*;
//import com.example.reclamation.test.testtranscriber;
import com.example.reclamation.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import okhttp3.Response;
import okio.ByteString;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.imgproc.Imgproc.*;

import com.assemblyai.api.RealtimeTranscriber;

public class CameraController implements Initializable {

    @FXML
    private Button button;
    @FXML
    private Button button2;

    @FXML
    private ImageView currentFrame;

    private VideoCapture capture;
    private VideoWriter writer;

    private AudioFormat audioFormat;
    private TargetDataLine line;
    Thread thread;
    ByteArrayInputStream inputStream;
    AudioInputStream audioInputStream;
    StreamingClient streamingClient;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        capture = new VideoCapture();
    }




        public void startCamera() {


//        String audioDevice = "default"; // Change to appropriate audio device
//        String audioFormat = "dshow"; // Change if necessary
//
//        String audioURL = "audio=" + audioDevice;
//        String formatURL = "format=" + audioFormat;
//
//        String audioOptions = audioURL + ":" + formatURL;
//
//        String url = "audio=" + audioDevice + ":video=" + 0;
//
//        String outputUrl = "output.mp4";
//
//        String[] grabberArgs = {"-f", audioFormat, "-i", audioDevice, "-f", "dshow", "-i", "video=" + 0, "-vcodec", "libx264", outputUrl};

            // Assign your access token to a String
            String accessToken = "028DshTeedyiQqbfXg3SElo9w1vRt__qrV1HXuCqxfA4kpiPcXRu0K0m1kXcI5uCjyJnAQcgZOUuNQ8t3LDmuUP6uyDYQ";
            String filePath = "C:\\Users\\gasso\\IdeaProjects\\reclamation\\audio.wav";
            // Configure the streaming content type
            StreamContentType streamContentType = new StreamContentType();
            streamContentType.setContentType("audio/x-raw"); // audio content type
            streamContentType.setLayout("interleaved"); // layout
            streamContentType.setFormat("F32LE"); // format
            streamContentType.setRate(48000); // sample rate
            streamContentType.setChannels(1); // channels

            // Setup the SessionConfig with any optional parameters
            SessionConfig sessionConfig = new SessionConfig();
            sessionConfig.setMetaData("Streaming from the Java SDK");
            sessionConfig.setFilterProfanity(true);
            sessionConfig.setRemoveDisfluencies(true);
            sessionConfig.setDeleteAfterSeconds(2592000); // 30 days in seconds

            // Initialize your client with your access token
            streamingClient = new StreamingClient(accessToken);

            // Initialize your WebSocket listener
            WebSocketListener webSocketListener = new WebSocketListener();

            // Begin the streaming session
            streamingClient.connect(webSocketListener, streamContentType, sessionConfig);

            if(line!=null && line.isOpen()) {
                System.out.println("open");
                line.close();
            }
            if (!capture.isOpened()) {
                capture.open(0); // Open the default camera (index 0)
                double frameWidth = capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
                double frameHeight = capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
                if (capture.isOpened()) {
                    // Start a background thread to continuously capture frames from the camera
                    writer = new VideoWriter("video.mp4", VideoWriter.fourcc('H', '2', '6', '4'), 20, new Size(frameWidth, frameHeight),true);
                    //                startAudioCapture();
                    try {
                        audioFormat = new AudioFormat(22050, 16, 2, true, false);
                        DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
                        line = (TargetDataLine) AudioSystem.getLine(info);
                        line.open(audioFormat);
                        line.start();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];



                    thread = new Thread(() -> {

                        while (!Thread.interrupted()) {



                            Mat frame = new Mat();
//                            String text = "Funny text inside the box";
//                            int fontFace = Core.COVAR_SCRAMBLED;
//                            double fontScale = 2;
//                            int thickness = 3;
//
//                            int[] baseline = new int[1]; // Array to store baseline
//                            Size textSize = getTextSize(text, fontFace, fontScale, thickness, baseline);
//                            // Center the text
//                            Point textOrg = new Point((frame.cols() - textSize.width) / 2, (frame.rows() + textSize.height) / 2);
//                            // Draw the box
//                            rectangle(frame, new Point(0, baseline[0]), new Point(textSize.width, -textSize.height), new Scalar(0, 0, 255), Core.FILLED);
//                            // Draw the baseline
//                            line(frame, new Point(0, baseline[0]), new Point(textSize.width, baseline[0]), new Scalar(0, 0, 255), thickness);
//                            // Put the text itself
//                            putText(frame, text, textOrg, fontFace, fontScale, new Scalar(255, 255, 255), thickness);

                            int count = line.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                            }

// Read file from disk
                            File file = new File(filePath);

                            // Convert file into byte array
                            byte[] fileByteArray = new byte[(int) file.length()];
                            try (final FileInputStream fileInputStream = new FileInputStream(file)) {
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                                try (final DataInputStream dataInputStream = new DataInputStream(bufferedInputStream)) {
                                    dataInputStream.readFully(fileByteArray, 0, fileByteArray.length);
                                } catch (IOException e) {
                                    throw new RuntimeException(e.getMessage());
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e.getMessage());
                            }
                            int chunk = 1000;

                            // Stream the file in the configured chunk size
                            //for (int start = 0; start < buffer.length; start += chunk) {
                                streamingClient.sendAudioData(
                                        ByteString.of(
                                                ByteBuffer.wrap(
                                                        Arrays.copyOfRange(
                                                                buffer, 0, Math.min(buffer.length, 0 + chunk)))));
                           // }

//                            int count = line.read(buffer, 0, buffer.length);
//                            if (count > 0) {
//                                try (FileOutputStream fos = new FileOutputStream("audio.wav", true)) {
//                                    fos.write(buffer, 0, count);
//                                } catch (IOException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }

                            byte[] audioData = out.toByteArray();
                            inputStream = new ByteArrayInputStream(audioData);
                            audioInputStream = new AudioInputStream(inputStream, audioFormat, audioData.length / audioFormat.getFrameSize());
                            try {
                                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File("audio.wav"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            if (capture.read(frame)) {
//                                int font = Core.COVAR_SCRAMBLED;
//
//
//                                putText(frame,
//                                        "TEXT ON VIDEO",
//                                        new Point (50, 400),
//                                        font, 1,
//                                        new Scalar(0, 255, 255),
//                                        2,
//                                        LINE_4);
                                Image imageToShow = matToImage(frame);

                                currentFrame.setImage(imageToShow);
                                writer.write(frame);
                            }
                        }
                        System.out.println("Stopping recording");
                        line.close();
                        streamingClient.close();
                        System.out.println("Closing real-time transcript connection");


                        try{
                            if (audioInputStream != null) {
                                audioInputStream.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.setDaemon(false);
                    thread.start();
//                    System.out.println("Press ENTER key to stop...");
//                    try {
//                        System.in.read();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    thread.interrupt();
//                    System.exit(0);
                }
            }
        }


    @FXML
    public void stopCamera(ActionEvent event) {
        if (capture.isOpened()) {
            capture.release(); // Release the camera when the application exits
            writer.release();
            thread.stop();
            streamingClient.close();

//            try{
//            if (audioInputStream != null) {
//                audioInputStream.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        }
        mergeAudioAndVideo("merged");

    }

    private void mergeAudioAndVideo(String outputFilename) {
        String cmd = "C:\\ffmpeg\\bin\\ffmpeg -i audio.wav -i video.mp4 -c:v copy -c:a aac -strict experimental -map 0:a:0 -map 1:v:0 " + outputFilename + ".avi" ;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            //process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //line.close();
//line.stop();
        // Clean up temporary files
//        new File("audio.wav").delete();
//        new File("video.mp4").delete();
    }

    public Image matToImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        return new Image(new ByteArrayInputStream(matOfByte.toArray()));
    }
    private static class WebSocketListener implements RevAiWebSocketListener {

        @Override
        public void onConnected(ConnectedMessage message) {
            System.out.println(message);
        }

        @Override
        public void onHypothesis(Hypothesis hypothesis) {
            System.out.println("hypooooooooo");
            if (hypothesis.getType() == MessageType.FINAL) {
                String textValue = "";
                for (Element element: hypothesis.getElements()) {
                    textValue = textValue + element.getValue();
                }

                System.out.println(" text transcribed " + textValue);

            }
        }


        @Override
        public void onError(Throwable t, Response response) {
            System.out.println("errror");
            System.out.println(response);
        }

        @Override
        public void onClose(int code, String reason) {
            System.out.println("closeeee");
            System.out.println(reason);
        }

        @Override
        public void onOpen(Response response) {
            System.out.println("opeeeeeeen");
            System.out.println(response.toString());
        }
    }
}

