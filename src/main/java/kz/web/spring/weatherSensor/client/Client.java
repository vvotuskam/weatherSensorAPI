package kz.web.spring.weatherSensor.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.web.spring.weatherSensor.util.responces.measurement.RainyDaysResponse;
import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Client {

    private final static String url = "http://localhost:8080/";
    private final static RestTemplate restTemplate = new RestTemplate();
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static String sensorName = "New Sensor";

    public static void main(String[] args) {
        System.out.println(getMeasurements());
    }

    private static String getRainyDays() {
        String urlToRequest = url + "measurements/rainyDaysCount";
        return restTemplate.getForObject(urlToRequest, String.class);
    }

    private static String getMeasurements() {
        String urlToRequest = url + "measurements";
        return restTemplate.getForObject(urlToRequest, String.class);
    }

    private static void postMeasurement() {
//        String urlToRequest = url + "measurements/add";
//        Random random = new Random();
//        double sign = random.nextBoolean() ? 1 : -1;
//        double value = random.nextDouble(100);
//        boolean raining = random.nextBoolean();
//        String postTemplate = new MeasurementTemplate(sign * value, raining, sensorName).toString();
//        restTemplate.postForObject(urlToRequest, )
        /*
        "value": 45,
        "raining": true,
        "sensor": {
            "name": "New sensor1"
        }
         */

    }
}
