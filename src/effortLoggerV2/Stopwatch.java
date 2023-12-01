import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

class StopWatch extends BorderPane {
   private Text tTime;
   private int hours;
   private int minutes;
   private int seconds;
   private Timeline timeline;

   public StopWatch() {
      hours = 0;
      minutes = 0;
      seconds = 0;

      // set up the time display
      tTime = new Text();
      setTime();

      // lay out the UI
      setCenter(tTime);
      setMargin(tTime, new Insets(20, 50, 20, 50));
   }

   public void start() {
      KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {
         seconds++;
         setTime();
      });

      timeline = new Timeline(kf);
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
   }

   public void pause() {
      timeline.pause();
   }

   public void resume() {
      timeline.play();
   }

   public void stoptimer() {
      timeline.stop();
   }

   public void clear() {
      hours = 0;
      minutes = 0;
      seconds = 0;
      setTime();
   }

   public void setTime() {
      // flip 60 seconds over to a minute
      if (seconds == 60) {
         seconds = 0;
         minutes++;
      }

      // flip 60 minutes over to an hour
      if (minutes == 60) {
         minutes = 0;
         hours++;
      }

      // ensure that values < 10 are padded with a 0
      String h = hours >= 10 ? String.valueOf(hours) : "0" + String.valueOf(hours);
      String m = minutes >= 10 ? String.valueOf(minutes) : "0" + String.valueOf(minutes);
      String s = seconds >= 10 ? String.valueOf(seconds) : "0" + String.valueOf(seconds);

      tTime.setText(h + ":" + m + ":" + s);
   }

   public String getTime() {
        return tTime.getText();
   }
}
