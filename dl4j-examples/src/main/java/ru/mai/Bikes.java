package ru.mai;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Класс нейросети c четырьмя полями <b>FEATURES_COUNT</b>, <b>CLASSES_COUNT</b>, <b>BATCHSIZE</b>, <b>SEED</b>
 * @author Евгений
 */
public class Bikes {

    /**
     * Поле для хранения количества входных параметров
     */
    static final int FEATURES_COUNT = 3;

    /**
     * Поле для хранения количества выходных классов
     */
    static final int CLASSES_COUNT = 2;

    /**
     * Поле для хранения количества входных данных (тренировочных и тестировочных сетов)
     */
    static final int BATCHSIZE = 140;

    /**
     * Скорость обучения нейронной сети
     */
    static final int SEED = 6;


    public static void main(String[] args) {
        FileGeneration file = new FileGeneration();
        try {
            file.gen();
            System.out.println();
            System.out.println("Файл создан");
        } catch (IOException e) {
            System.out.println("Error! Невозможно создать файл входных данных!");
            System.exit(0);
        }


        DataSet allData = new DataSet();
        try {
            allData = input(allData);
        } catch (IOException | InterruptedException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.exit(0);
        }

        normalizing(allData);

        SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65);
        DataSet trainingData = testAndTrain.getTrain();
        DataSet testData = testAndTrain.getTest();


        var configuration = neuralNetWorkBuilder();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();
        model.setListeners(new ScoreIterationListener(100));


        for(int i = 0; i < 1000; i++) {
            model.fit(trainingData);
        }

        results(model, testData);
    }

    /**
     * Функция считывания данных из файла
     * @param allData переменная, в которую будет считана информация из файла
     * @return считанные данные
     * @throws IOException в случае ошибки при открытии или считвания файла
     * @throws InterruptedException в случае ошибки при открытии или ситывания файла
     */
    private static DataSet input(DataSet allData) throws IOException, InterruptedException {
        RecordReader recordReader = new CSVRecordReader(0, ',');
        recordReader.initialize(new FileSplit(new File("output.txt")));

        DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, BATCHSIZE, FEATURES_COUNT, CLASSES_COUNT);
        allData = iterator.next();
        allData.shuffle(42);
        return allData;
    }

    /**
     * Функция нормализации считанных данных
     * @param allData cчитанные данные
     */
    private static void normalizing(DataSet allData) {
        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(allData);
        normalizer.transform(allData);
    }

    /**
     * Фкнция создания конфигурации нейронной сети
     * @return конфигураци нейросети
     */
    private static MultiLayerConfiguration neuralNetWorkBuilder() {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(SEED)
                .activation(Activation.TANH)
                .weightInit(WeightInit.XAVIER)
                .updater(new Sgd(0.1))
                .l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder().nIn(FEATURES_COUNT).nOut(3).build())
                .layer(1, new DenseLayer.Builder().nIn(3).nOut(3).build())
                .layer(2, new OutputLayer.Builder(
                        LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .activation(Activation.SOFTMAX)
                        .nIn(3).nOut(CLASSES_COUNT).build())
                .build();
        return configuration;
    }

    /**
     * Функция тестирования нейронной сети и вывода результатов
     * @param model модель нейронной сети
     * @param testData данные для тестирования
     */
    private static void results(MultiLayerNetwork model, DataSet testData) {
        Evaluation eval = new Evaluation(CLASSES_COUNT);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        System.out.println(eval.stats());
    }
}
