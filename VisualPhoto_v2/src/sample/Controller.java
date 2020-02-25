package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.FaceConceptsModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.FaceConcepts;

public class Controller {
    private final static String API_KEY = "5ddda45f308943e2809da45780dc50bb";

    Map<String, Float> list = new HashMap<String, Float>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView img;

    @FXML
    private Label person;

    @FXML
    private Label value;

    @FXML
    private TextField href;

    @FXML
    private Button btn;

    @FXML
    void initialize(){
        btn.setOnAction(event -> {
            System.out.print("Press");
            URL url = null;
            try {
                url = new URL(href.getText());
            } catch (MalformedURLException e2) {
                e2.printStackTrace();
            }

            img.setImage(new Image(url.toString()));

            read(href.getText(), list);

            for (Map.Entry pair: list.entrySet()) {
                person.setText((String) pair.getKey());
                value.setText((float) pair.getValue() + "%");
            }
        });
    }

    @FXML
    public static Map<String, Float> read(String url, Map<String, Float> list) {
        final ClarifaiClient client = new ClarifaiBuilder(API_KEY).buildSync(); // Клиент

        FaceConceptsModel celebrityModel = client.getDefaultModels().celebrityModel(); // Категория(celebrity)

        PredictRequest<FaceConcepts> request = celebrityModel.predict().withInputs(ClarifaiInput.forImage(url)); // Запрос

        //https://img.gazeta.ru/files3/1/12489001/RIAN_5941883.HR-pic905-895x505-87411.jpg

        List<ClarifaiOutput<FaceConcepts>> result = request.executeSync().get();

        String name = null;
        float value = 0;

        for(ClarifaiOutput<FaceConcepts> pair : result) {
            for(FaceConcepts faceConcepts : (List<FaceConcepts>) pair.data()) {
                name = faceConcepts.concepts().get(0).name();
                value = (float) faceConcepts.concepts().get(0).value();
            }
        }
        list.put(name, value);
        return list;
    }
}
