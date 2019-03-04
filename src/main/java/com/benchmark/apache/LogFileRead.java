package com.benchmark.apache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.*;

public class LogFileRead {


    private static List<Session> loadSession(File file) throws IOException, IllegalAccessException, InstantiationException {
        List<Session> sessions = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        Session session = new Session();
        while (!Objects.isNull(line = bufferedReader.readLine())) {

            if (line.contains("SESSION_START")) {
                session = new Session();
                continue;
            }
            if (line.contains("SESSION_END")) {
                sessions.add(session);
                continue;
            }
            if (line.contains("Complete requests")) {
                session.setCompleteRequest(Double.valueOf(line.split(":")[1].trim()));
                continue;
            }
            if (line.contains("Failed requests")) {
                session.setFailedRequest(Double.valueOf(line.split(":")[1].trim()));
                continue;
            }
            if (line.contains("Requests per second")) {
                session.setRequestPerSecond(parseValue(line.split(":")[1].trim(), "["));
                continue;
            }
            if (line.contains("Time per request") && Objects.isNull(session.getTimePerRequest())) {
                session.setTimePerRequest(parseValue(line.split(":")[1].trim(), "["));
                continue;
            }
            if (line.contains("SESSION_TIME")) {
                session.setTakenTime(Double.valueOf(line.split(":")[1].trim()));
                continue;
            }
            if (line.contains("Total of ")) {
                session.setCompleteRequest(Double.valueOf(line.substring("Total of ".length(), line.indexOf(" requests")).trim()));
            }

        }

        return sessions;

    }

    private static Double parseValue(String value, String seperate) {
        value = value.trim();
        int index = value.indexOf(seperate);
        return Double.valueOf(value.substring(0, index));
    }

    private static File[] selectFiles() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setMultiSelectionEnabled(true);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFiles();
        }
        return null;
    }

    private static void drawGraphiscs(File logFile) throws Exception {

        List<Session> sessions = loadSession(logFile);
        XYSeries xyRequestPerSecond = new XYSeries("Request Per Second");
        XYSeries xyCompleteRequest = new XYSeries("Complete Request");
        XYSeries xyTimePerRequest = new XYSeries("Time Per Second");
        Double totalTime = 0.0;
        for (int i = 0; i < sessions.size(); ++i) {
            totalTime += sessions.get(i).getTakenTime();
            xyRequestPerSecond.add(totalTime, sessions.get(i).getRequestPerSecond());
            xyCompleteRequest.add(totalTime, sessions.get(i).getCompleteRequest());
            xyTimePerRequest.add(totalTime, sessions.get(i).getTimePerRequest());
        }

        sessions.forEach(e -> {
            try {
                System.out.println(new ObjectMapper().writeValueAsString(e));
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
        });



        DrawGraph requestPerSecond = new DrawGraph(logFile.getName(), xyRequestPerSecond);
        requestPerSecond.pack();
        RefineryUtilities.positionFrameOnScreen(requestPerSecond, 200.0, 300.0);
        requestPerSecond.setVisible(true);

        DrawGraph completeRequest = new DrawGraph(logFile.getName(), xyCompleteRequest);
        completeRequest.pack();
        RefineryUtilities.positionFrameOnScreen(completeRequest, 500.0, 300.0);
        completeRequest.setVisible(true);

        DrawGraph timePerRequest = new DrawGraph(logFile.getName(), xyTimePerRequest);
        timePerRequest.pack();
        RefineryUtilities.positionFrameOnScreen(timePerRequest, 500.0, 0.0);
        timePerRequest.setVisible(true);


    }


    public static void main(String[] args) throws Exception {

        File[] logFiles = selectFiles();
        if (Objects.isNull(logFiles)) {
            throw new Exception("File Not Selected");
        }

        Arrays.stream(logFiles).forEach(e -> {
            try {
                LogFileRead.drawGraphiscs(e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }


}
