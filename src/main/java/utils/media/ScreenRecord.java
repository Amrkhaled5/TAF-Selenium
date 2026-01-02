package utils.media;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import org.apache.poi.hssf.record.RecordFactory;
import utils.dataManager.PropertyReader;
import utils.logs.LogsManager;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecord {
    public final static String SCREENRECORDPATH = "test-results/screenrecords/";
    private static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    public static void startRecording() {
        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            try {
                File recordingFile = new File(SCREENRECORDPATH);
                if (!recordingFile.exists()) {
                    recordingFile.mkdirs();
                }
                if (PropertyReader.getProperty("executionType").equalsIgnoreCase("local")) {
                    recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));
                    recorder.get().start();
                    LogsManager.info("Screen recording started.");
                }
            }
            catch (Exception e) {
                LogsManager.error("Failed to start screen recording: ", e.getMessage());
            }
        }
    }

    public static void stopRecording(String testName){
        try {
            if (recorder.get() != null) {
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testName));
                File viddeoFile = new File(videoFilePath);

                LogsManager.info("Screen recording stopped. Video saved at: " + viddeoFile.getAbsolutePath());

                File mp4File = encodeRecording(viddeoFile);
                LogsManager.info("Screen recording encoded to MP4. Video saved at: " + mp4File.getAbsolutePath());
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to stop screen recording: ",e.getMessage());
        }
        finally {
            recorder.remove();
        }
    }

    public static File encodeRecording(File videoFile){
        File targetFile=new File(videoFile.getParent(),videoFile.getName().replace(".avi",".mp4"));
        try{
            AudioAttributes audio=new AudioAttributes();
            audio.setCodec("aac");

            VideoAttributes video=new VideoAttributes();
            video.setCodec("libx264");

            EncodingAttributes encodingAttributes=new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

            if(targetFile.exists()){
                videoFile.delete();
                LogsManager.info("Deleted existing MP4 file: ",targetFile.getAbsolutePath());
            }
        }
        catch (Exception e){
            LogsManager.error("Failed to encode recording: ",e.getMessage());
        }
        return targetFile;
    }
}

