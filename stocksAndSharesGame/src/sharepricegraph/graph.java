package sharepricegraph;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class graph extends Application {

    private static final int MAX_DATA_POINTS = 50;
    private int xSeriesData = 0;
    
    private final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
    private final XYChart.Series<Number, Number> series4 = new XYChart.Series<>();
    private ExecutorService executor;
    private final ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ2 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ3 = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Number> dataQ4 = new ConcurrentLinkedQueue<>();
    
    public static Random rand = new Random();
    public static int[] previousStonks = new int[3];
    public static int[] previousChange = new int[3];
    public static int[] previousPreviousChange = new int[3];

    private NumberAxis xAxis;

    private void init(Stage primaryStage) {
        
        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(true);
        xAxis.setTickLabelsVisible(false);
        xAxis.setTickMarkVisible(false);
        xAxis.setMinorTickVisible(false);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("GBP");

        // Create a LineChart
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
            // Override to remove symbols on each data point
            @Override
            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
            }
        };

        lineChart.setAnimated(false);
        lineChart.setTitle("STONKS");
        lineChart.setHorizontalGridLinesVisible(true);

        // Set Name for Series
        series1.setName("APPOOL");
        series2.setName("MICROSOOFT");
        series3.setName("AMAZOON");
        series4.setName("Your Net Worth");

        // Add Chart Series
        lineChart.getData().addAll(series1, series2, series3, series4);

        primaryStage.setScene(new Scene(lineChart));
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("LIVE STONKS");
        init(stage);
        stage.show();


        executor = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });

        AddToQueue addToQueue = new AddToQueue();
        executor.execute(addToQueue);
        //-- Prepare Timeline
        prepareTimeline();
    }

    private class AddToQueue implements Runnable {
        @Override
        public void run() {
            try {
                // add a item of random data to queue
                int[] newValuesOfStocks = new int[3];
                int[] newStockChange = new int[3];
                
                for (int i = 0; i < previousStonks.length; i++) {
                    int rangeOfRandChange = 100;
                    if(previousChange[i] > 100){
                        rangeOfRandChange = previousChange[i]*1;
                    }
                    
                    newStockChange[i] = rand.nextInt(rangeOfRandChange + 1)-(rangeOfRandChange/2) + previousChange[i]/3 + previousPreviousChange[i]/5;
                    newValuesOfStocks[i] = newStockChange[i]+previousStonks[i];
                    if(newValuesOfStocks[i] < 0){
                        newValuesOfStocks[i] = 0;
                    }
                    gameScreen.stockValues[i] = newValuesOfStocks[i];
                    
                    previousStonks[i] = newValuesOfStocks[i];
                    previousPreviousChange[i] = previousChange[i];
                    previousChange[i] = newStockChange[i];
                }
                
                gameScreen.UpdateStockValues(newValuesOfStocks[0],newValuesOfStocks[1],newValuesOfStocks[2]);
                dataQ1.add(newValuesOfStocks[0]);
                dataQ2.add(newValuesOfStocks[1]);
                dataQ3.add(newValuesOfStocks[2]);
                dataQ4.add(gameScreen.totalValueOfOwnedStocks + gameScreen.totalMoney);
                
                
                Thread.sleep(5000);
                
                executor.execute(this);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //-- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                addDataToSeries();
            }
        }.start();
    }

    private void addDataToSeries() {
        for (int i = 0; i < 20; i++) { //-- add 20 numbers to the plot+
            if (dataQ1.isEmpty()) break;
            series1.getData().add(new XYChart.Data<>(xSeriesData++, dataQ1.remove()));
            series2.getData().add(new XYChart.Data<>(xSeriesData++, dataQ2.remove()));
            series3.getData().add(new XYChart.Data<>(xSeriesData++, dataQ3.remove()));
            series4.getData().add(new XYChart.Data<>(xSeriesData++, dataQ4.remove()));
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (series1.getData().size() > MAX_DATA_POINTS) {
            series1.getData().remove(0, series1.getData().size() - MAX_DATA_POINTS);
        }
        if (series2.getData().size() > MAX_DATA_POINTS) {
            series2.getData().remove(0, series2.getData().size() - MAX_DATA_POINTS);
        }
        if (series3.getData().size() > MAX_DATA_POINTS) {
            series3.getData().remove(0, series3.getData().size() - MAX_DATA_POINTS);
        }
        if (series4.getData().size() > MAX_DATA_POINTS) {
            series4.getData().remove(0, series4.getData().size() - MAX_DATA_POINTS);
        }
        // update
        xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
        xAxis.setUpperBound(xSeriesData - 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}